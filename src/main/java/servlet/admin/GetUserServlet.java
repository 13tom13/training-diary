package servlet.admin;

import entities.dto.UserDTO;
import entities.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.mappers.UserMapper;

import java.io.IOException;
import java.util.Optional;

import static servlet.utils.ServletUtils.writeJsonResponse;

@WebServlet("/admin/user")
public class GetUserServlet extends AdminServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userEmailJson = request.getParameter("userEmail");

        String userEmail = objectMapper.readValue(userEmailJson, String.class);

        Optional<User> userByEmail = userRepository.getUserByEmail(userEmail);
        // Проверка, найден ли пользователь
        if (userByEmail.isPresent()) {
            // Если пользователь найден, мапим его в UserDTO и возвращаем
            UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(userByEmail.get());
            // Преобразуем данные в JSON и отправляем как ответ
            writeJsonResponse(response, userDTO, HttpServletResponse.SC_OK);
        } else {
            // Если пользователь не найден, выбрасываем исключение UserNotFoundException
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}

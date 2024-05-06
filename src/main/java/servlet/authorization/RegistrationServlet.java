package servlet.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.RegistrationDTO;
import exceptions.RepositoryException;
import in.service.users.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static config.initializer.ServiceFactory.getUserService;
import static servlet.utils.ServletUtils.getObjectMapper;
import static servlet.utils.ServletUtils.getRequestBody;

public class RegistrationServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper objectMapper = getObjectMapper();

    public RegistrationServlet() {
        try {
            Class.forName("org.postgresql.Driver");
            this.userService = getUserService();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Получаем данные из запроса
        String requestBody = getRequestBody(request);
        RegistrationDTO registrationDTO = objectMapper.readValue(requestBody, RegistrationDTO.class);
        try {
            // Сохраняем нового пользователя
            userService.saveUser(registrationDTO);
            response.setStatus(HttpServletResponse.SC_CREATED); // Устанавливаем статус ответа как 201 Created
            response.getWriter().write("Пользователь успешно создан");
//        } catch (ValidationException e) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Устанавливаем статус ответа как 400 Bad Request
//            response.getWriter().write("Ошибка валидации: " + e.getMessage());
        } catch (RepositoryException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT); // Устанавливаем статус ответа как 409 Conflict
            response.getWriter().write("Пользователь с таким email уже существует");
        }
    }
}

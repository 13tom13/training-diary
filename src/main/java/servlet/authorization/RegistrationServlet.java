package servlet.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.RegistrationDTO;
import exceptions.RepositoryException;
import in.service.users.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static servlet.utils.ServletUtils.getObjectMapper;
import static servlet.utils.ServletUtils.getRequestBody;

@Component
public class RegistrationServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper objectMapper = getObjectMapper();

    @Autowired
    public RegistrationServlet(UserService userService) {
        this.userService = userService;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Получаем данные из запроса
        String requestBody = getRequestBody(request);
        RegistrationDTO registrationDTO = objectMapper.readValue(requestBody, RegistrationDTO.class);
            // Сохраняем нового пользователя
        try {
            userService.saveUser(registrationDTO);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
        response.setStatus(HttpServletResponse.SC_CREATED); // Устанавливаем статус ответа как 201 Created
            response.getWriter().write("Пользователь успешно создан");
    }
}

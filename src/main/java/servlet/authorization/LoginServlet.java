package servlet.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.AuthorizationDTO;
import entities.dto.UserDTO;
import exceptions.security.AuthorizationException;
import in.service.users.AuthorizationService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import static config.initializer.in.ServiceFactory.getAuthorizationService;

public class LoginServlet extends HttpServlet {

    private final AuthorizationService authorizationService;
    private final ObjectMapper objectMapper;

    public LoginServlet() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            this.authorizationService = getAuthorizationService();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.objectMapper = new ObjectMapper();
        // Замените на ваш реальный сервис
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Получаем данные из запроса
        String requestBody = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));
        AuthorizationDTO authorizationDTO = objectMapper.readValue(requestBody, AuthorizationDTO.class);
        String email = authorizationDTO.getEmail();
        String password = authorizationDTO.getPassword();
        try {
            // Пытаемся выполнить аутентификацию
            UserDTO userDTO = authorizationService.login(email, password);
            String jsonResponse = objectMapper.writeValueAsString(userDTO);
            request.getSession().setAttribute("user", jsonResponse);// Преобразуем UserDTO в JSON
            response.setContentType("application/json"); // Устанавливаем тип контента как JSON
            response.setCharacterEncoding("UTF-8"); // Устанавливаем кодировку
            response.getWriter().write(jsonResponse); // Отправляем JSON-строку как ответ
            response.setStatus(HttpServletResponse.SC_OK); // Устанавливаем статус ответа как 200 OK
        } catch (AuthorizationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Устанавливаем статус ответа как 401 Unauthorized
            response.getWriter().write("Authorization failed"); // Отправляем сообщение об ошибке как ответ
        }
    }
}


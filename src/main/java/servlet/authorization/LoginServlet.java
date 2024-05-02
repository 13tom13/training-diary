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
import static servlet.utils.ServletUtils.getRequestBody;
import static servlet.utils.ServletUtils.writeJsonResponse;
import static utils.Utils.getObjectMapper;

public class LoginServlet extends HttpServlet {

    private final AuthorizationService authorizationService;
    private final ObjectMapper objectMapper = getObjectMapper();

    public LoginServlet() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            this.authorizationService = getAuthorizationService();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Получаем данные из запроса
        String requestBody = getRequestBody(request);
        AuthorizationDTO authorizationDTO = objectMapper.readValue(requestBody, AuthorizationDTO.class);
        String email = authorizationDTO.getEmail();
        String password = authorizationDTO.getPassword();
        try {
            // Пытаемся выполнить аутентификацию
            UserDTO userDTO = authorizationService.login(email, password);
            writeJsonResponse(response,userDTO, HttpServletResponse.SC_OK);
        } catch (AuthorizationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Устанавливаем статус ответа как 401 Unauthorized
            response.getWriter().write("Authorization failed"); // Отправляем сообщение об ошибке как ответ
        }
    }

}


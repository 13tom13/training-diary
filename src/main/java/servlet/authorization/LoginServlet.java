package servlet.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.AuthorizationDTO;
import entity.dto.UserDTO;
import exceptions.security.AuthorizationException;
import in.service.users.AuthorizationService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import servlet.utils.annotations.Loggable;

import java.io.IOException;
import java.sql.SQLException;

import static servlet.utils.ServletUtils.*;

@Loggable
@Component
public class LoginServlet extends HttpServlet {


    private AuthorizationService authorizationService;
    private ObjectMapper objectMapper = getObjectMapper();

    public LoginServlet() {
    }

    @Autowired
    public LoginServlet(AuthorizationService authorizationService) throws SQLException {
        this.authorizationService = authorizationService;
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
        AuthorizationDTO authorizationDTO = objectMapper.readValue(requestBody, AuthorizationDTO.class);
        String email = authorizationDTO.getEmail();
        String password = authorizationDTO.getPassword();
        try {
            // Пытаемся выполнить аутентификацию
            UserDTO userDTO = authorizationService.login(email, password);
            writeJsonResponse(response, userDTO, HttpServletResponse.SC_OK);
        } catch (AuthorizationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Устанавливаем статус ответа как 401 Unauthorized
            response.getWriter().write("Authorization failed"); // Отправляем сообщение об ошибке как ответ
        }
    }

}


package servlet.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.AuthorizationDTO;
import entities.dto.UserDTO;
import exceptions.security.AuthorizationException;
import in.repository.user.implementation.UserRepositoryJDBC;
import in.service.users.AuthorizationService;
import in.service.users.implementation.AuthorizationServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class LoginServlet extends HttpServlet {

    private final AuthorizationService authorizationService;
    private final ObjectMapper objectMapper;

    public LoginServlet() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/training-diary", "user", "password");
            this.authorizationService = new AuthorizationServiceImpl(new UserRepositoryJDBC(connection));
            this.objectMapper = new ObjectMapper();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
            HttpSession session = request.getSession(true);
            String jsonUserDTO = objectMapper.writeValueAsString(userDTO);
            session.setAttribute("user", jsonUserDTO);
            System.out.println("session user: " + session.getAttribute("user"));
            response.setStatus(HttpServletResponse.SC_FOUND); // Код 302 - Found (перенаправление)
            response.setHeader("Location", "/training-diary/templates/account/account.jsp");
        } catch (AuthorizationException e) {
            throw new RuntimeException(e);
        }
    }
}

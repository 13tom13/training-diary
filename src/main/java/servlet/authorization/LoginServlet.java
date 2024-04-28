package servlet.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.AuthorizationDTO;
import exceptions.security.AuthorizationException;
import in.repository.user.implementation.UserRepositoryJDBC;
import in.service.users.AuthorizationService;
import in.service.users.implementation.AuthorizationServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import entities.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    private final AuthorizationService authorizationService;

    public LoginServlet() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/training-diary", "user", "password");
            this.authorizationService = new AuthorizationServiceImpl(new UserRepositoryJDBC(connection));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Замените на ваш реальный сервис
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Создаем объект ObjectMapper для преобразования JSON в объект Java
        ObjectMapper mapper = new ObjectMapper();
        // Получаем входной поток из запроса
        InputStream inputStream = request.getInputStream();
        System.out.println();
        System.out.println(request.getContentType());
        // Читаем данные JSON из входного потока и преобразуем их в объект AuthorizationDTO
        AuthorizationDTO authorizationDTO = mapper.readValue(inputStream, AuthorizationDTO.class);
        // Теперь у вас есть объект authorizationDTO с полями email и password, которые были отправлены в запросе
        String email = authorizationDTO.getEmail();
        String password = authorizationDTO.getPassword();

        try {
            User user = authorizationService.login(email, password);
            // Если авторизация прошла успешно, устанавливаем атрибут сессии для сохранения информации о пользователе
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);


            // Перенаправляем пользователя на указанную страницу при успешной авторизации
            String url = request.getContextPath() + "/account/";
            System.out.println("Context path: " + request.getContextPath());
            System.out.println("Servlet path: " + request.getServletPath());
            System.out.println("Path info: " + request.getPathInfo());
            System.out.println("User form service: " + user);
            System.out.println("User from session: " + session.getAttribute("user"));
            System.out.println(url);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"redirectUrl\": \"" + url + "\"}");
        } catch (AuthorizationException e) {
            String errorMessage = "Authorization failed: " + e.getMessage();
            response.getWriter().write(errorMessage);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        System.out.println();

    }


}
package servlet.admin;

import entity.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static servlet.utils.ServletUtils.writeJsonResponse;

public class GetAllUsersServlet extends AdminServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> allUsers = userRepository.getAllUsers();
        // Преобразуем данные в JSON и отправляем как ответ
        writeJsonResponse(response, allUsers, HttpServletResponse.SC_OK);
    }
}

package servlet.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import entities.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

@WebServlet("/admin/user/delete")
public class DeleteUserServlet extends AdminServlet {

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String userEmailJson = request.getParameter("userEmail");
        try {
            String userEmail = objectMapper.readValue(userEmailJson, String.class);
            Optional<User> userByEmail = userRepository.getUserByEmail(userEmail);
            if (userByEmail.isPresent()) {
                User user = userByEmail.get();
                userRepository.deleteUser(user);
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (JsonProcessingException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

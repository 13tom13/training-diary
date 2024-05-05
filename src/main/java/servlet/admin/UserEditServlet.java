package servlet.admin;

import entities.dto.UserDTO;
import entities.model.User;
import exceptions.RepositoryException;
import exceptions.security.rights.NoEditRightsException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import servlet.utils.mappers.UserMapper;

import java.io.IOException;
import java.util.Arrays;

import static servlet.utils.ServletUtils.getRequestBody;
import static servlet.utils.ServletUtils.writeJsonResponse;

@WebServlet(name = "UserEditServlet", urlPatterns = {"/admin/user/change/*"})
public class UserEditServlet extends AdminServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String requestBody = getRequestBody(request);
            JSONObject jsonRequest = new JSONObject(requestBody);
            JSONObject userJsonObject = jsonRequest.getJSONObject("userDTO");

            UserDTO userDTO = objectMapper.readValue(userJsonObject.toString(), UserDTO.class);
            User user = userRepository.getUserByEmail(userDTO.getEmail()).get();
            System.out.println("user from repository: " + user);

            String[] pathParts = request.getPathInfo().split("/");
            if (pathParts.length >= 2) {
                String editType = pathParts[1];
                switch (editType) {
                    case "username":
                        String newName = jsonRequest.getString("newName");
                        user.setFirstName(newName);
                        break;
                    case "lastname":
                        String newLastName = jsonRequest.getString("newLastName");
                        user.setLastName(newLastName);
                        break;
                    case "password":
                        String newPassword = jsonRequest.getString("newPassword");
                        user.setPassword(newPassword);
                        break;
                    case "active":
                        System.out.println("user active before changed: " + user.isActive());
                        user.setActive(!user.isActive());
                        System.out.println("user active after changed: " + user.isActive());
                        break;
                    default:
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("Не поддерживаемый тип изменения");
                        return;
                }
                userRepository.updateUser(user);
                User changedUser = userRepository.getUserByEmail(user.getEmail()).get();
                System.out.println("User changed: " + changedUser);
                UserDTO changedUserDTO = UserMapper.INSTANCE.userToUserDTO(changedUser);
                writeJsonResponse(response, changedUserDTO, HttpServletResponse.SC_OK);
            } else {
                // Если не указан тип изменения, отправляем ошибку
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Не указан тип изменения");
            }

        } catch (JSONException e) {
            int statusCode = HttpServletResponse.SC_BAD_REQUEST;
            response.setStatus(statusCode);
            response.getWriter().write("Ошибка при обработке запроса: " + e.getMessage());
        }
    }
}


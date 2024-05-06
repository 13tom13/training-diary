package servlet.admin;

import entities.dto.UserDTO;
import entities.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import utils.mappers.UserMapper;
import utils.Logger;

import java.io.IOException;

import static servlet.utils.ServletUtils.getRequestBody;
import static servlet.utils.ServletUtils.writeJsonResponse;

@WebServlet(name = "UserEditServlet", urlPatterns = {"/admin/user/change/*"})
public class UserEditServlet extends AdminServlet {

    private final Logger logger;

    public UserEditServlet() {
        logger = Logger.getInstance();
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String requestBody = getRequestBody(request);
            JSONObject jsonRequest = new JSONObject(requestBody);
            JSONObject userJsonObject = jsonRequest.getJSONObject("userDTO");

            UserDTO userDTO = objectMapper.readValue(userJsonObject.toString(), UserDTO.class);
            User user = userRepository.getUserByEmail(userDTO.getEmail()).get();

            String[] pathParts = request.getPathInfo().split("/");
            StringBuilder builder = new StringBuilder();
            builder.append("изменение: ");
            if (pathParts.length >= 2) {
                String editType = pathParts[1];
                switch (editType) {
                    case "username":
                        String newName = jsonRequest.getString("newName");
                        user.setFirstName(newName);
                        builder.append(String.format("имени пользователя (старое: %s, новое: %s)", user.getFirstName(), newName));
                        break;
                    case "lastname":
                        String newLastName = jsonRequest.getString("newLastName");
                        user.setLastName(newLastName);
                        builder.append(String.format("фамилии пользователя (старое: %s, новое: %s)", user.getLastName(), newLastName));
                        break;
                    case "password":
                        String newPassword = jsonRequest.getString("newPassword");
                        user.setPassword(newPassword);
                        builder.append("пароля пользователя");
                        break;
                    case "active":
                        boolean oldActive = user.isActive();
                        user.setActive(!oldActive);
                        builder.append("пользователь ").append(user.isActive() ? "активирован" : "деактивирован");
                        break;
                    default:
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("Не поддерживаемый тип изменения");
                        return;
                }
                userRepository.updateUser(user);
                User changedUser = userRepository.getUserByEmail(user.getEmail()).get();
                UserDTO changedUserDTO = UserMapper.INSTANCE.userToUserDTO(changedUser);
                logger.logAction(user.getEmail(), builder.toString());
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


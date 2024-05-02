package servlet.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

public class ServletUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getJsonParamFromRequest(HttpServletRequest request, String param) throws IOException {
        String userJson = request.getParameter(param);
        if (userJson == null) {
            throw new IOException("Пользователь не аутентифицирован");
        }
        return userJson;
    }

    public static void writeJsonResponse(HttpServletResponse response, Object responseObject, int status) throws IOException {
        String jsonResponse = objectMapper.writeValueAsString(responseObject);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().write(jsonResponse);
    }


    public static String getRequestBody(HttpServletRequest request) throws IOException {
        return request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }
}

package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class ServletUtils {

    public static final ObjectMapper objectMapper = getObjectMapper();
//
//    public static String getJsonParamFromRequest(HttpServletRequest request, String param) throws IOException {
//        String userJson = request.getParameter(param);
//        if (userJson == null) {
//            throw new IOException("Пользователь не аутентифицирован");
//        }
//        return userJson;
//    }
//
//    public static void writeJsonResponse(HttpServletResponse response, Object responseObject, int status) throws IOException {
//        String jsonResponse = objectMapper.writeValueAsString(responseObject);
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.setStatus(status);
//        response.getWriter().write(jsonResponse);
//    }
//
//
//    public static String getRequestBody(HttpServletRequest request) throws IOException {
//        return request.getReader().lines()
//                .collect(Collectors.joining(System.lineSeparator()));
//    }

    public static String encodeToUrlJson(Object object) throws JsonProcessingException {
        return URLEncoder.encode(objectMapper.writeValueAsString(object), StandardCharsets.UTF_8);
    }

    @Bean
    public static ObjectMapper getObjectMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }
}

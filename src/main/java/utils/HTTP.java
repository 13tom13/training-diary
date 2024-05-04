package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static utils.Utils.getObjectMapper;

public class HTTP {

    private static final ObjectMapper OBJECT_MAPPER = getObjectMapper();

    public static String sendPostRequest(String servletUrl, String jsonRequestBody) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(servletUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            sendRequest(connection, jsonRequestBody);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                return sendResponse(connection);
            } else {
                throw new IOException("HTTP error code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    /**
     * Отправляет GET-запрос по указанному URL и возвращает ответ в виде строки.
     *
     * @param urlWithParams URL с параметрами запроса.
     * @return Ответ на GET-запрос в виде строки.
     * @throws IOException если возникает ошибка ввода-вывода при отправке запроса или получении ответа.
     */
    public static String sendGetRequest(String urlWithParams) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlWithParams);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                return sendResponse(connection);
            } else {
                throw new IOException("Ошибка при получении данных: " + statusCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

        public static Integer sendDeleteRequest(String urlWithParams) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlWithParams);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_NO_CONTENT) {
                return statusCode;
            } else {
                throw new IOException("Ошибка при получении данных: " + statusCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    private static void sendRequest(HttpURLConnection connection, String jsonRequestBody) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            writer.write(jsonRequestBody);
        }
    }

    private static String sendResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        } finally {
            connection.disconnect();
        }
    }
}

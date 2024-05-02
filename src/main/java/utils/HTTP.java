package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HTTP {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String sendPostRequest(String servletUrl, Object requestBody) throws IOException {
        URL url = new URL(servletUrl);
        String jsonRequestBody = OBJECT_MAPPER.writeValueAsString(requestBody);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonRequestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }finally {
                connection.disconnect();
            }
        } else {
            throw new IOException("HTTP error code: " + responseCode);
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
        URL url = new URL(urlWithParams);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int statusCode = connection.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            } finally {
                connection.disconnect();
            }
        } else {
            throw new IOException("Ошибка при получении данных: " + statusCode);

        }
    }
}

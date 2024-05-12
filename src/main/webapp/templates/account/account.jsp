<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="entity.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Training diary</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<%
    // Получение JSON-строки из атрибута сессии
    String userJson = (String) session.getAttribute("user");
    // Преобразование JSON-строки в объект User
    ObjectMapper objectMapper = new ObjectMapper();
    UserDTO user = objectMapper.readValue(userJson, UserDTO.class);
%>
<h2>Меню аккаунта пользователя</h2>
<h3>Добро пожаловать, <span id="userName"><%= user.getFirstName() %></span>!</h3>
<a href="${pageContext.request.contextPath}/training-diary/test" class="btn btn-primary">Просмотреть все тренировки</a><br><br>
<button onclick="addTraining()">Добавить тренировку</button>
<br><br>
<button onclick="deleteTraining()">Удалить тренировку</button>
<br><br>
<button onclick="editTraining()">Изменить тренировку</button>
<br><br>
<button onclick="viewStatistics()">Просмотреть статистику тренировок</button>
<br><br>
<button onclick="logout()">Выйти</button>
</body>
</html>


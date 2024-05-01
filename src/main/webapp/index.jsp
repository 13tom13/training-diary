<%--
Created by IntelliJ IDEA.
User: 13tom
Date: 30.04.2024
Time: 15:30
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Training diary</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
              integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">@Gurbanovksy</a>
            <button class="navbar-toggler"
                    type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent"
                    aria-expanded="false"
                    aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page"
                           href="https://github.com/13tom13">GitHub</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page"
                           href="https://t.me/gurbanovksy">Telegram</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="px-4 py-5 my-5 text-center">
        <h1 class="display-3 fw-bold">Тренировочный дневник</h1>
        <div class="col-lg-6 mx-auto">
            <p class="display-6">Добро пожаловать в тренировочный дневник!</p>
            <p class="lead">(развертывания с помощью сервера Tomcat 10)</p>
            <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                <a type="button" class="btn btn-primary btn-lg px-4 gap-3" href="templates/authorization/authorization.html">Авторизация</a>
                <a type="button" class="btn btn-outline-secondary btn-lg px-4" href="/training-diary/registration">Регистрация</a>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    </body>
    </html>

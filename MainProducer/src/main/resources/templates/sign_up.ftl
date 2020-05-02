<#ftl encoding='UTF-8'>
<html lang="ru">
<head>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet"
              id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
                integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
                crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
                integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
                crossorigin="anonymous"></script>
        <link href="/css/login.css" rel="stylesheet">

        <title>Вход в систему</title>
    </head>
</head>
<body>
<div class="wrapper fadeInDown">
    <div class="fadeIn second" id="formContent">
        <div class="fadeIn first">
            <h3>Регистрация</h3>
        </div>
        <form action="/sign_up" method="post">
            <input type="text" id="login" class="fadeIn second" name="login" placeholder="Логин">
            <input type="password" id="password" class="fadeIn third" name="password" placeholder="Пароль">
            <input type="text" id="facilityName" class="fadeIn second" name="Наименование производства" placeholder="Наименование производства">
            <input type="text" id="facilityDescription" class="fadeIn second" name="Описание производства" placeholder="Описание производсвта">
            <input type="submit" class="fadeIn fourth" value="Вход">
        </form>

        <div id="formFooter">
            <a class="underlineHover" href="/login">Вход в систему</a>
        </div>

    </div>
</div>
</body>

<style>
    input[type=password] {

        background-color: #f6f6f6;

        border: none;

        color: #0d0d0d;

        padding: 15px 32px;

        text-align: center;

        text-decoration: none;

        display: inline-block;

        font-size: 16px;

        margin: 5px;

        width: 85%;

        border: 2px solid #f6f6f6;

        -webkit-transition: all 0.5s ease-in-out;

        -moz-transition: all 0.5s ease-in-out;

        -ms-transition: all 0.5s ease-in-out;

        -o-transition: all 0.5s ease-in-out;

        transition: all 0.5s ease-in-out;

        -webkit-border-radius: 5px 5px 5px 5px;

        border-radius: 5px 5px 5px 5px;

    }

    input[type=password]:focus {

        background-color: #fff;

        border-bottom: 2px solid #5fbae9;

    }

    input[type=password]:placeholder {

        color: #cccccc;

    }
</style>

</html>



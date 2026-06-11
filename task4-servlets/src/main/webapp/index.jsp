<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>F1 Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
</head>
<body>
<div class="container">
    <div class="card">
        <h1 class="title">F1 Shop</h1>
        <p class="subtitle">Добро пожаловать в мир скорости!</p>
        <div class="actions">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a href="${pageContext.request.contextPath}/controller?command=get_all_items" class="btn btn-primary">Каталог</a>
                    <a href="${pageContext.request.contextPath}/controller?command=logout" class="btn btn-secondary">Выйти</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/controller?command=go_to_login" class="btn btn-primary">Войти</a>
                    <a href="${pageContext.request.contextPath}/controller?command=go_to_register" class="btn btn-secondary">Зарегистрироваться</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
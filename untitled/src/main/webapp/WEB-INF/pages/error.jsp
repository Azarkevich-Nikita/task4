<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Ошибка - F1 Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
</head>
<body>
<div class="container">
    <div class="card">
        <h2>Что-то пошло не так</h2>
        <p class="error">Произошла ошибка при обработке запроса.</p>
        <a href="${pageContext.request.contextPath}/index.jsp" class="link">На главную</a>
    </div>
</div>
</body>
</html>

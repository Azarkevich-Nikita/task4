<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Пользователи - F1 Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/items.css">
</head>
<body>
<div class="container">
    <header>
        <h1>Пользователи</h1>
        <div class="header-actions">
            <a href="${pageContext.request.contextPath}/controller?command=get_all_items" class="btn btn-secondary">К каталогу</a>
            <a href="${pageContext.request.contextPath}/controller?command=logout" class="btn btn-secondary">Выйти</a>
        </div>
    </header>
    <c:if test="${not empty message}">
        <p class="success">${message}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>
    <div class="items-grid">
        <c:forEach var="u" items="${users}">
            <div class="item-card">
                <h3>${u.username}</h3>
                <p class="team">${u.email}</p>
                <p class="desc">Роль: ${u.role}</p>
                <c:if test="${u.id != user.id}">
                    <form method="post" action="${pageContext.request.contextPath}/controller?command=delete_user">
                        <input type="hidden" name="userId" value="${u.id}">
                        <button type="submit" class="btn btn-small btn-danger">Удалить</button>
                    </form>
                </c:if>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>

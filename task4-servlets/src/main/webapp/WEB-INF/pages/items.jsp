<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Каталог - F1 Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/items.css">
</head>
<body>
<div class="container">
    <header>
        <h1>Каталог F1</h1>
        <p class="subtitle">Пользователь: ${user.username}</p>
        <div class="header-actions">
            <a href="${pageContext.request.contextPath}/controller?command=go_to_add_item" class="btn btn-primary">Добавить товар</a>
            <c:if test="${user.role == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/controller?command=get_all_users" class="btn btn-secondary">Пользователи</a>
            </c:if>
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
        <c:forEach var="item" items="${items}">
            <div class="item-card">
                <h3>${item.title}</h3>
                <p class="team">${item.manufactureTeam}</p>
                <p class="price">${item.price} ₽</p>
                <p class="desc">${item.description}</p>
                <c:if test="${user.role == 'ADMIN' || user.id == item.ownerId}">
                    <div class="item-actions">
                        <a href="${pageContext.request.contextPath}/controller?command=go_to_edit_item&id=${item.id}" class="btn btn-small">Изменить</a>
                        <form method="post" action="${pageContext.request.contextPath}/controller?command=delete_item">
                            <input type="hidden" name="id" value="${item.id}">
                            <button type="submit" class="btn btn-small btn-danger">Удалить</button>
                        </form>
                    </div>
                </c:if>
            </div>
        </c:forEach>
        <c:if test="${empty items}">
            <p class="empty">Товаров пока нет</p>
        </c:if>
    </div>
</div>
</body>
</html>

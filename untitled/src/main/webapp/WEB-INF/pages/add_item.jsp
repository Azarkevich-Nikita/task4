<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Добавить товар - F1 Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/items.css">
</head>
<body>
<div class="container">
    <div class="card">
        <h2>Новый товар</h2>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}/controller?command=add_item">
            <input type="text" name="title" placeholder="Название" required>
            <input type="number" name="price" placeholder="Цена" min="1" required>
            <input type="text" name="manufactureTeam" placeholder="Команда (Ferrari, Mercedes...)" required>
            <textarea name="description" placeholder="Описание" rows="4"></textarea>
            <button type="submit" class="btn btn-primary">Сохранить</button>
        </form>
        <a href="${pageContext.request.contextPath}/controller?command=get_all_items" class="link">Назад к каталогу</a>
    </div>
</div>
</body>
</html>
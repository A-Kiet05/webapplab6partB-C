<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Change Password</title>
    <style>
        /* CSS tối thiểu để hiển thị thông báo lỗi rõ ràng */
        .container { width: 400px; margin: 50px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
        .error-message { color: red; font-size: 0.9em; margin-top: 5px; display: block; }
        .message.success { color: green; border: 1px solid green; padding: 10px; margin-bottom: 10px; }
        .message.error { color: red; border: 1px solid red; padding: 10px; margin-bottom: 10px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; }
        input[type="password"] { width: 100%; padding: 8px; box-sizing: border-box; }
        button { padding: 10px 15px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Change Password</h1>

        <c:if test="${not empty requestScope.successMessage}">
            <div class="message success">${requestScope.successMessage}</div>
        </c:if>
        <c:if test="${not empty requestScope.errorMessage}">
            <div class="message error">${requestScope.errorMessage}</div>
        </c:if>

        <form action="change-password" method="POST">
            
            <div class="form-group">
                <label for="currentPassword">Current Password:</label>
                <input type="password" id="currentPassword" name="currentPassword" placeholder="Current Password" required>
                <c:if test="${not empty requestScope.errorCurrent}">
                    <span class="error-message">${requestScope.errorCurrent}</span>
                </c:if>
            </div>

            <div class="form-group">
                <label for="newPassword">New Password (Min 8 chars):</label>
                <input type="password" id="newPassword" name="newPassword" placeholder="New Password" required>
                <c:if test="${not empty requestScope.errorNew}">
                    <span class="error-message">${requestScope.errorNew}</span>
                </c:if>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm New Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" required>
                <c:if test="${not empty requestScope.errorConfirm}">
                    <span class="error-message">${requestScope.errorConfirm}</span>
                </c:if>
            </div>

            <button type="submit">Change Password</button>
        </form>
        
        <p><a href="dashboard">← Back to Dashboard</a></p>
    </div>
</body>
</html>
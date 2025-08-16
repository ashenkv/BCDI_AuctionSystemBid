<%--
  Created by IntelliJ IDEA.
  User: DELL USER
  Date: 6/7/2025
  Time: 4:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register - Auction System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(135deg, #3a3a3a, #1e1e1e);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #fff;
        }
        .form-container {
            background: #ffffff;
            color: #333;
            padding: 30px;
            box-shadow: 0 0 30px rgba(0,0,0,0.2);
            width: 100%;
            max-width: 400px;
            text-align: center;
            animation: fadeIn 0.8s ease;
        }
        h2 {
            margin-bottom: 20px;
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ccc;
        }
        input[type="submit"] {
            background-color: #1e1e1e;
            color: white;
            padding: 12px 25px;
            border: none;
            cursor: pointer;
            transition: background 0.3s ease;
            width: 100%;
            margin-top: 10px;
        }
        input[type="submit"]:hover {
            background-color: #121212;
        }
        .link {
            margin-top: 15px;
        }
        .link a {
            color: #1e1e1e;
            text-decoration: none;
            font-weight: bold;
        }
        .error { color: red; margin-top: 10px; }
        .success { color: green; margin-top: 10px; }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @media (max-width: 480px) {
            .form-container {
                margin: 10px;
                padding: 20px;
            }
        }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Register for Auction System</h2>
    <form action="register" method="post">
        <input type="text" name="username" placeholder="Username" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="submit" value="Register">
    </form>
    <div class="link">
        <p>Already login? <a href="${pageContext.request.contextPath}/login">Login here</a></p>
    </div>
    <% if (request.getAttribute("error") != null) { %>
    <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>
    <% if (request.getAttribute("success") != null) { %>
    <p class="success"><%= request.getAttribute("success") %></p>
    <% } %>
</div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: DELL USER
  Date: 6/5/2025
  Time: 9:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Online Sale System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <style>
        :root {
            --warning: #FB9E3A;
            --orange: #E6521F;
            --light: #f7f9fc;
            --gray: #3a3a3a;
            --text-dark: #333;
            --text-light: #eee;
            --danger: #dc3545;
            --bg-dark: #121212;
            --card-bg: #1e1e1e;
        }

        * {
            box-sizing: border-box;
            padding: 0;
            margin: 0;
        }

        body {
            font-family: 'Segoe UI', sans-serif;
            background: var(--bg-dark);
            color: var(--text-light);
            padding: 20px;
        }

        .container {
            max-width: 1200px;
            margin: auto;
        }

        header {
            background: var(--orange);
            color: white;
            padding: 20px;
            margin-bottom: 20px;
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            align-items: center;
        }

        header h1 {
            font-size: 1.8rem;
        }

        header p {
            font-size: 1rem;
        }

        header a {
            color: white;
            text-decoration: underline;
        }

        .grid {
            display: grid;
            gap: 20px;
        }

        .section {
            background: var(--card-bg);
            color: var(--text-light);
            padding: 25px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }

        .section h2 {
            font-size: 1.4rem;
            color: var(--orange);
            margin-bottom: 20px;
            border-bottom: 2px solid var(--gray);
            padding-bottom: 5px;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        label {
            font-weight: bold;
        }

        input[type="text"],
        input[type="number"],
        input[type="datetime-local"] {
            padding: 10px;
            border: 1px solid #555;
            width: 100%;
            font-size: 1rem;
            background: #2a2a2a;
            color: white;
        }

        input[type="submit"] {
            background: var(--orange);
            color: white;
            border: none;
            padding: 12px;
            font-size: 1rem;
            cursor: pointer;
            transition: 0.3s;
        }

        input[type="submit"]:hover {
            background: var(--light);
            color: var(--orange);
        }

        btn[type="submit"] {
            width: 20px;
        }

        .error {
            background: #ffe6e6;
            color: var(--danger);
            padding: 12px;
            margin-bottom: 20px;
        }

        .sale-card {
            background: #2a2a2a;
            padding: 15px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
            transition: transform 0.2s ease;
        }

        .sale-card:hover {
            transform: translateY(-2px);
        }

        .sale-card h3 {
            color: var(--orange);
            margin-bottom: 10px;
        }

        .sale-card p {
            margin-bottom: 8px;
        }

        @media (max-width: 992px) {
            .grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>

<div class="container">
    <header>
        <h1>Online Auction System</h1>
        <p> <strong>${loggedInUser}</strong> | <a href="${pageContext.request.contextPath}/logout">Logout</a></p>
    </header>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <div class="grid">
        <!-- Create Sale Section -->
        <div class="section">
            <h2>Create New Sale</h2>
            <form action="${pageContext.request.contextPath}/sale" method="post">
                <input type="hidden" name="action" value="create">

                <label for="itemName">Item Name:</label>
                <input type="text" name="itemName" id="itemName" required>

                <label for="startBid">Starting Price:</label>
                <input type="number" name="startBid" id="startBid" step="0.01" required>

                <label for="endTime">End Time:</label>
                <input type="datetime-local" name="endTime" id="endTime" required>

                <input class="btn" type="submit" value="Create Sale">
            </form>
        </div>

        <!-- Active Sales Section -->
        <div class="section">
            <h2>Active Sales</h2>
            <c:if test="${empty sales}">
                <p>No active sales available.</p>
            </c:if>

            <c:forEach var="sale" items="${sales}">
                <div class="sale-card">
                    <h3>${sale.itemName}</h3>
                    <p><strong>Current Price:</strong> ${sale.currentPrice}
                        <c:if test="${not empty sale.currentBidder}">
                            by ${sale.currentBidder}
                        </c:if>
                    </p>
                    <p><strong>End Time:</strong> ${sale.endTime}</p>

                    <form action="${pageContext.request.contextPath}/sale" method="post">
                        <input type="hidden" name="action" value="offer">
                        <input type="hidden" name="saleId" value="${sale.id}">

                        <label for="amount-${sale.id}">Offer Amount:</label>
                        <input type="number" name="amount" id="amount-${sale.id}" step="0.01" required>

                        <input type="submit" value="Place Offer">
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>
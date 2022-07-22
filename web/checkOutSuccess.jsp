<%-- 
    Document   : checkOutSuccess
    Created on : Oct 18, 2020, 4:25:50 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout Success</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <c:if test="${empty requestScope.yourCode}">
            <c:redirect url="searchProduct"/>
        </c:if>
        <h1>This is your code to view your order</h1>
        ${requestScope.yourCode}</br>
        <a href="searchProduct">Back to Shopping</a>
    </body>
</html>

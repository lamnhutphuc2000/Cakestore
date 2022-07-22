<%-- 
    Document   : removeProductFromCart
    Created on : Oct 18, 2020, 3:03:04 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Do you really want</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <c:set var="user" value="${sessionScope.user}"/>
        <c:set var="roleIDOfAdmin" value="${sessionScope.roleIDOfAdmin}"/>
        <c:if test="${not empty user}">
            <c:if test="${user.roleID eq roleIDOfAdmin}">
                <c:redirect url="searchProduct"/>
            </c:if>
        </c:if>
        <h1>Do you really want to delete this item from cart ?</h1>
        <form action="showCartPage">
            <input type="submit" value="Cancel" />
        </form>
        <form action="removeProductFromCart">
            <input type="hidden" name="txtProductID" value="${param.txtProductID}" /> 
            <input type="submit" value="Yes, I do" />
        </form>
    </body>
</html>

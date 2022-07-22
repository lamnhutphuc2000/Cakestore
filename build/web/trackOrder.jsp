<%-- 
    Document   : trackOrder
    Created on : Oct 18, 2020, 7:15:57 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tracking your Order</title>
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
        <c:if test="${  empty user}"> 
            <c:redirect url="searchProduct"/> 
        </c:if>

        <h1>This is your Order</h1>
        <c:set var="order" value="${requestScope.order}"/> 
        <c:set var="paymentMethod" value="${requestScope.paymentMethod}"/>
        <c:set var="orderDetail" value="${requestScope.orderDetail}"/>
        <c:if test="${not empty order}">
            Username : ${user.name} </br>
            Order ID: ${order.orderID} </br>
            Order Date: ${order.orderDate} </br>
            Shipping address: ${order.shippingAddress} </br>
            Phone number: ${order.phoneNumber} </br>
            Payment status: ${order.paymentStatus} </br>
            Payment method: ${paymentMethod} </br>
            <table border="1">
                <thead>
                    <tr> 
                        <th>Name</th>
                        <th>Quantity</th> 
                        <th>Sum</th> 
                    </tr>
                </thead>
                <c:forEach var="items" items="${orderDetail}">
                    <tr> 
                        <th>${items.productName}</th>
                        <th>${items.quantity}</th> 
                        <th>${items.price}</th> 
                    </tr>
                </c:forEach>
            </c:if>
        </table>
        <a href="searchProduct">Return to Shopping Online</a>
    </body>
</html>

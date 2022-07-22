<%-- 
    Document   : checkOut
    Created on : Oct 18, 2020, 3:24:55 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check Out Page</title>
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
        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:if test="${ empty cart}">
            <c:redirect url="searchProduct"/>
        </c:if>
        <c:if test="${not empty cart}">
            <c:set var="mycart" value="${cart.cart}"/>
            <c:if test="${empty mycart}">
                <c:redirect url="searchProduct"/>
            </c:if>
        </c:if>

        <c:if test="${not empty user}">
            <h1>Checkout Page</h1>
            <form action="checkOut">
                <input type="hidden" name="txtUserID" value="${user.userID}" />
                Enter your shipping address: <input type="text" name="txtShippingAddress" value="${user.address}" />
                <c:if test="${not empty requestScope.shippingAddressError}">
                    <div class="text-danger">${requestScope.shippingAddressError}</div>
                </c:if>
                Enter your phone number: <input type="text" name="txtPhoneNumber" value="${user.phone}" />
                <c:if test="${not empty requestScope.phoneNumberError}">
                    <div class="text-danger">${requestScope.phoneNumberError}</div>
                </c:if>
                Total price: ${param.txtTotalPrice}
                <input type="hidden" name="txtTotalPrice" value="${param.txtTotalPrice}" />
                <c:if test="${not empty requestScope.productOutOfOrder}">
                    <div class="text-danger">${requestScope.productOutOfOrder}</div>
                </c:if>
                <input type="submit" value="Confirm" />
            </form>
        </c:if>
        <c:if test="${ empty user}">
            <h1>Checkout Page</h1>
            <form action="checkOut"> 
                Enter your shipping address: <input type="text" name="txtShippingAddress" value="" />
                <c:if test="${not empty requestScope.shippingAddressError}">
                    <div class="text-danger">${requestScope.shippingAddressError}</div>
                </c:if>
                Enter your phone number: <input type="text" name="txtPhoneNumber" value="" />
                <c:if test="${not empty requestScope.phoneNumberError}">
                    <div class="text-danger">${requestScope.phoneNumberError}</div>
                </c:if>
                Total price: ${param.txtTotalPrice}
                <input type="hidden" name="txtTotalPrice" value="${param.txtTotalPrice}" />
                <c:if test="${not empty requestScope.productOutOfOrder}">
                    <div class="text-danger">${requestScope.productOutOfOrder}</div>
                </c:if>
                <input type="submit" value="Confirm" />
            </form>
        </c:if>
        <a href="showCartPage">Back to cart</a>
    </body>
</html>

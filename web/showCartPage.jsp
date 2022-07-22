<%-- 
    Document   : showCartPage
    Created on : Oct 18, 2020, 2:23:09 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your cart detail</title>
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
        <c:set var="updateQuantityInCartError" value="${requestScope.updateQuantityInCartError}"/>
        <c:if test="${not empty cart}">
            <c:set var="mycart" value="${cart.cart}"/>
            <c:if test="${not empty mycart}">
                <h1>Your Cart</h1>
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Prices</th>
                            <th>Sum</th>
                            <th>Update quantity</th>
                            <th>Removes</th>
                        </tr>
                    </thead>
                    <c:set var="sumOfMoney" value="0"/>

                    <tbody>
                        <c:forEach var="item" items="${mycart}" varStatus="counter">
                        <form action="updateQuantityOfRoomInCart">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${item.name}</td>
                                <td><input type="text" name="txtQuantity" value="${item.quantity}" /></td>
                                    <c:if test="${not empty updateQuantityInCartError.formatQuantityError}">
                                        <div class="text-danger">${updateQuantityInCartError.formatQuantityError} </div>
                                    </c:if>
                                    <c:if test="${not empty updateQuantityInCartError.negativeQuantityError}">
                                        <div class="text-danger">${updateQuantityInCartError.negativeQuantityError} </div>
                                    </c:if>
                                <td>${item.price}</td>
                                <td>${item.price * item.quantity}</td>
                                <c:set var="sumOfMoney" value="${sumOfMoney + item.price * item.quantity}"/>
                            <input type="hidden" name="txtProductID" value="${item.productID}" />

                            <td> <input type="submit" value="Update quantity" /> </td>
                            <td>
                                <c:url var="removeLink" value="removeProductFromCartPage">
                                    <c:param name="txtProductID" value="${item.productID}"/>
                                </c:url>
                                <a href="${removeLink}">remove this item</a>
                            </td>
                            </tr> 
                        </form>
                    </c:forEach> 
                </tbody> 

            </table>
            <h3>Total: ${sumOfMoney}</h3>
            <form action="searchProduct">
                <input type="submit" value="Back to Shopping" />

            </form>
            <form action="checkOutPage">
                <input type="hidden" name="txtTotalPrice" value="${sumOfMoney}" />
                <input type="submit" value="Check out" />
            </form> 
        </c:if>
    </c:if>
    <c:if test="${ empty mycart}">
        <h2>No cart is existed</h2>
        <form action="">
            <input type="submit" value="Back to Shopping" />
        </form>
    </c:if>
</body>
</html>

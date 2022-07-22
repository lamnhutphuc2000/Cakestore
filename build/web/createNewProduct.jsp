<%-- 
    Document   : createNewProduct
    Created on : Oct 18, 2020, 8:37:29 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Product Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <c:set var="user" value="${sessionScope.user}"/>
        <c:if test="${empty user}">
            <c:redirect url="searchProduct"/>
        </c:if>
        <c:if test="${not empty user}">
            <c:set var="roleIDOfAdmin" value="${sessionScope.roleIDOfAdmin}"/>
            <c:if test="${user.roleID ne roleIDOfAdmin}">
                <c:redirect url="searchProduct"/>
            </c:if>
        </c:if>
        <c:set var="createNewError" value="${requestScope.createNewProductError}"/>
        <h1>Create New Product</h1>

        <form action="createNewProduct">
            Product ID: <input type="text" name="txtCreateNewProductID" value="${param.txtCreateNewProductID}" /> </br>
            <c:if test="${not empty createNewError.duplicateProductID}">
                ${createNewError.duplicateProductID} 
            </c:if> 
            Name: <input type="text" name="txtCreateNewName" value="${param.txtCreateNewName}" /></br>
            <c:if test="${not empty createNewError.emptyName}">
                ${createNewError.emptyName} 
            </c:if> 
            Image: <input type="file" name="txtCreateNewImage" /></br>
            <c:if test="${not empty createNewError.emptyImage}">
                ${createNewError.emptyImage} 
            </c:if> 
            Description: <input type="text" name="txtCreateNewDescription" value="${param.txtCreateNewDescription}" /></br>
            <c:if test="${not empty createNewError.emptyDescription}">
                ${createNewError.emptyDescription} 
            </c:if> 
            Price: <input type="text" name="txtCreateNewPrice" value="${param.txtCreateNewPrice}" /></br>
            <c:if test="${not empty createNewError.negativePrice}">
                ${createNewError.negativePrice} 
            </c:if> 
            <c:if test="${not empty createNewError.emptyPrice}">
                ${createNewError.emptyPrice} 
            </c:if> 
            <c:if test="${not empty createNewError.formatPriceError}">
                ${createNewError.formatPriceError} 
            </c:if> 
            Create date: <input type="text" name="txtCreateNewCreateDate" value="${param.txtCreateNewCreateDate}" /></br>
            <c:if test="${not empty createNewError.formatCreateDateError}">
                ${createNewError.formatCreateDateError} 
            </c:if> 
            Expiration date: <input type="text" name="txtCreateNewExpirationDate" value="${param.txtCreateNewExpirationDate}" /></br>
            <c:if test="${not empty createNewError.formatExpirationDateError}">
                ${createNewError.formatExpirationDateError} 
            </c:if> 
            <c:if test="${not empty createNewError.expirationDateBeforeCreateDateError}">
                ${createNewError.expirationDateBeforeCreateDateError} 
            </c:if> 
            <c:if test="${not empty createNewError.expirationDateBeforeNowError}">
                ${createNewError.expirationDateBeforeNowError} 
            </c:if> 
            Quantity: <input type="text" name="txtCreateNewQuantity" value="${param.txtCreateNewQuantity}" /></br>
            <c:if test="${not empty createNewError.negativeQuantity}">
                ${createNewError.negativeQuantity} 
            </c:if> 
            <c:if test="${not empty createNewError.formatQuantityError}">
                ${createNewError.formatQuantityError} 
            </c:if> 
            Category: 
            <select name="txtCreateNewCategory" >
                <c:set var="listCategory" value="${requestScope.listCategory}"/>
                <c:if test="${not empty listCategory}">
                    <c:forEach var="category" step="1" items="${listCategory}">
                        <option>${category.name}</option>
                    </c:forEach>
                </c:if>
            </select>
            <input type="submit" value="Create new" /> 
        </form>
        <form action="searchProduct">
            <input type="submit" value="Back to Shop" />
        </form>  
    </body>
</html>

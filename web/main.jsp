<%-- 
    Document   : main
    Created on : Oct 13, 2020, 7:53:16 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>The Chop Chep Store</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>  

        <c:set var="roleIDOfAdmin" value="${sessionScope.roleIDOfAdmin}"/>
        <c:set var="user" value="${sessionScope.user}"/>
        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:if test="${not empty user}">
            Welcome, ${user.name}
            <form action="logout">
                <input type="submit" value="Logout"/>
            </form>
        </c:if>
        <c:if test="${empty user}">
            <a href="loginPage">Login</a>
        </c:if>

        <c:if test="${not empty cart}">
            <form action="showCartPage">
                <input type="submit" value="See my cart" />
            </form> 
        </c:if> 
            
        <c:if test="${not empty user}">
            <c:if test="${user.roleID eq roleIDOfAdmin}"> 
                <form action="ShowActiveUser">
                    <input type="submit" value="ShowUser" />
                </form>
            </c:if>
        </c:if>
            
            
        <h3>MENU</h3>
        <c:set var="listCategory" value="${requestScope.listCategory}"/>
        <c:set var="error" value="${requestScope.searchError}"/>
        <form action="searchProduct">
            Cake's name: <input type="text" name="txtSearchName" value="${param.txtSearchName}" />
            Price from: <input type="text" name="txtPriceFrom" value="${param.txtPriceFrom}" />
            Price to: <input type="text" name="txtPriceTo" value="${param.txtPriceTo}" />
            Category:
            <select name="txtCategoryName" >
                <option>All</option>

                <c:if test="${not empty listCategory}">
                    <c:forEach var="category" step="1" items="${listCategory}">
                        <option>${category.name}</option>
                    </c:forEach>
                </c:if>
                <input type="submit" value="Search" />
            </select>
            </br>
            <c:if test="${not empty error.negativePriceTo}">
                <div class="text-danger">${error.negativePriceTo}</div>
            </c:if>
            <c:if test="${not empty error.negativePriceFrom}">
                <div class="text-danger">${error.negativePriceFrom}</div>
            </c:if>
            <c:if test="${not empty error.formatPriceToError}">
                <div class="text-danger">${error.formatPriceToError}</div>
            </c:if>
            <c:if test="${not empty error.formatPriceFromError}">
                <div class="text-danger">${error.formatPriceFromError}</div>
            </c:if>
        </form>

        <c:if test="${not empty user}">
            <c:if test="${user.roleID eq roleIDOfAdmin}"> 
                <form action="createNewProduct">
                    <input type="submit" value="Create new product" />
                </form>
                <c:set var="listProducts" value="${requestScope.listProduct}"/>
                <c:if test="${not empty listProducts}">
                    <c:set var="updateError" value="${requestScope.updateError}"/>
                    <c:forEach var="product" items="${listProducts}" varStatus="counter"> 
                        <c:if test="${updateError.productID == product.productID}">  
                            <form action="updateProduct">
                                Product ID: <div>${product.productID}</div>
                                <input type="hidden" name="txtProductID" value="${product.productID}" />
                                Name: 
                                <input type="text" name="txtUpdateName" value="${product.name}" /> </br>
                                <c:if test="${not empty updateError.emptyName}">
                                    <div class="text-danger">${updateError.emptyName}</div> 
                                </c:if> 
                                <img src="${product.image}"/></br>
                                <input type="hidden" name="txtProductImage" value="${product.image}" />
                                Choose another image: <input type="file" name="txtUpdateImage" /></br>
                                </br>
                                Description: 
                                <input type="text" name="txtUpdateDescription" value="${product.description}" />
                                <c:if test="${not empty updateError.emptyDescription}">
                                    <div class="text-danger">${updateError.emptyDescription}</div>
                                </c:if>
                                Price: 
                                <input type="text" name="txtUpdatePrice" value="${product.price}" />
                                <c:if test="${not empty updateError.negativePrice}">
                                    <div class="text-danger">${updateError.negativePrice}</div>
                                </c:if>
                                <c:if test="${not empty updateError.emptyPrice}">
                                    <div class="text-danger">${updateError.emptyPrice}</div>
                                </c:if>
                                <c:if test="${not empty updateError.formatPriceError}">
                                    <div class="text-danger">${updateError.formatPriceError}</div>
                                </c:if>
                                </br>
                                Create Date: 
                                <input type="text" name="txtUpdateCreateDate" value="${product.createDate}" />
                                <c:if test="${not empty updateError.formatCreateDateError}">
                                    <div class="text-danger">${updateError.formatCreateDateError}</div>
                                </c:if>
                                Expiration Date: 
                                <input type="text" name="txtUpdateExpirationDate" value="${product.expirationDate}" />
                                <c:if test="${not empty updateError.formatExpirationDateError}">
                                    <div class="text-danger">${updateError.formatExpirationDateError}</div>
                                </c:if>
                                <c:if test="${not empty updateError.expirationDateBeforeCreateDateError}">
                                    <div class="text-danger">${updateError.expirationDateBeforeCreateDateError}</div>
                                </c:if>
                                <c:if test="${not empty updateError.expirationDateBeforeNowError}">
                                    <div class="text-danger">${updateError.expirationDateBeforeNowError}</div>
                                </c:if>
                                </br>    
                                Quantity: 
                                <input type="text" name="txtUpdateQuantity" value="${product.quantity}" />
                                <c:if test="${not empty updateError.negativeQuantity}">
                                    <div class="text-danger">${updateError.negativeQuantity}</div>
                                </c:if>
                                <c:if test="${not empty updateError.formatQuantityError}">
                                    <div class="text-danger">${updateError.formatQuantityError}</div>
                                </c:if>
                                </br>    
                                Category:  
                                <select name="txtUpdateCategory"> 
                                    <c:forEach var="category" step="1" items="${listCategory}"> 
                                        <c:if test="${product.categoryID != category.id}">
                                            <option>${category.name}</option> 
                                        </c:if>  
                                    </c:forEach> 
                                    <c:forEach var="category" step="1" items="${listCategory}"> 
                                        <c:if test="${product.categoryID == category.id}">
                                            <option selected>${category.name}</option> 
                                        </c:if>  
                                    </c:forEach> 
                                </select> 
                                Status: 
                                <select name="txtUpdateStatus"> 
                                    <c:if test="${product.status eq 'Active'}">
                                        <option selected>Active</option> 
                                        <option>Deleted</option> 
                                    </c:if>
                                    <c:if test="${product.status eq 'Deleted'}">
                                        <option>Active</option> 
                                        <option selected>Deleted</option> 
                                    </c:if>
                                </select>
                                <input type="hidden" name="txtSearchName" value="${param.txtSearchName}" />
                                <input type="hidden" name="txtPriceFrom" value="${param.txtPriceFrom}" />
                                <input type="hidden" name="txtPriceTo" value="${param.txtPriceTo}" />
                                <input type="hidden" name="txtCategoryName" value="${param.txtCategoryName}" /> 
                                <input type="submit" value="Update" />
                            </form>
                        </c:if> 


                        <c:if test="${updateError.productID != product.productID}">  
                            <form action="updateProduct">
                                Product ID: <div>${product.productID}</div>
                                <input type="hidden" name="txtProductID" value="${product.productID}" /> </br> 
                                Name: 
                                <input type="text" name="txtUpdateName" value="${product.name}" /> </br> 

                                <img src="${product.image}"/></br>
                                <input type="hidden" name="txtProductImage" value="${product.image}" />
                                Choose another image: <input type="file" name="txtUpdateImage" /></br>
                                </br> 
                                Description: 
                                <input type="text" name="txtUpdateDescription" value="${product.description}" />

                                Price: 
                                <input type="text" name="txtUpdatePrice" value="${product.price}" />
                                </br> 
                                Create Date: 
                                <input type="text" name="txtUpdateCreateDate" value="${product.createDate}" />

                                Expiration Date: 
                                <input type="text" name="txtUpdateExpirationDate" value="${product.expirationDate}" />
                                </br> 
                                Quantity: 
                                <input type="text" name="txtUpdateQuantity" value="${product.quantity}" />

                                Category:  
                                <select name="txtUpdateCategory"> 
                                    <c:forEach var="category" step="1" items="${listCategory}"> 
                                        <c:if test="${product.categoryID != category.id}">
                                            <option>${category.name}</option> 
                                        </c:if>  
                                    </c:forEach> 
                                    <c:forEach var="category" step="1" items="${listCategory}"> 
                                        <c:if test="${product.categoryID == category.id}">
                                            <option selected>${category.name}</option> 
                                        </c:if>  
                                    </c:forEach> 
                                </select> 
                                </br> 
                                Status: 
                                <select name="txtUpdateStatus"> 
                                    <c:if test="${product.status == 'Active'}">
                                        <option selected>Active</option>
                                        <option>Deleted</option> 
                                    </c:if>
                                    <c:if test="${product.status == 'Deleted'}">
                                        <option>Active</option>
                                        <option selected>Deleted</option> 
                                    </c:if>
                                </select>
                                <input type="hidden" name="txtSearchName" value="${param.txtSearchName}" />
                                <input type="hidden" name="txtPriceFrom" value="${param.txtPriceFrom}" />
                                <input type="hidden" name="txtPriceTo" value="${param.txtPriceTo}" />
                                <input type="hidden" name="txtCategoryName" value="${param.txtCategoryName}" /> 
                                <input type="submit" value="Update" />
                            </form>
                        </c:if> 



                        </br></br></br> 
                    </c:forEach> 

                    <c:set var="numberOfPage" value="${requestScope.numberOfPage}"/> 
                    <c:if test="${numberOfPage gt 1}">
                        <c:forEach begin="1" step="1" end="${numberOfPage}" var="page">
                            <c:url var="loadLink" value="searchProduct">
                                <c:param name="curPage" value="${page}"/>
                                <c:param name="txtSearchName" value="${param.txtSearchName}"/>
                                <c:param name="txtPriceFrom" value="${param.txtPriceFrom}"/>
                                <c:param name="txtPriceTo" value="${param.txtPriceTo}"/>
                                <c:param name="txtCategoryName" value="${param.txtCategoryName}"/>
                            </c:url>
                            <a href="${loadLink}">${page}</a>
                        </c:forEach>
                    </c:if> 
                </c:if>
            </c:if> 


            <c:if test="${user.roleID ne roleIDOfAdmin}"> 
                <form action="trackOrder">
                    Enter your order ID to track: <input type="text" name="txtOrderID" value="" /> 
                    <input type="submit" value="Track your order" />
                </form>
                <c:set var="listProducts" value="${requestScope.listProduct}"/>
                <c:if test="${not empty listProducts}">
                    <c:forEach var="product" items="${listProducts}" varStatus="counter">
                        <form action="addProductToCart">   
                            Name: ${product.name} </br>
                            <img src="${product.image} "/></br> 
                            Description: ${product.description} </br>
                            Price: ${product.price} </br>
                            Create Date: ${product.createDate} </br>
                            Expiration Date: ${product.expirationDate} </br>
                            Quantity: ${product.quantity} </br>
                            Category:  
                            <c:forEach var="category" items="${listCategory}">
                                <c:if test="${category.id eq product.categoryID}">
                                    ${category.name}</br>
                                </c:if>
                            </c:forEach>
                            <input type="hidden" name="txtSearchName" value="${param.txtSearchName}" />
                            <input type="hidden" name="txtCategoryName" value="${param.txtCategoryName}" />
                            <input type="hidden" name="txtPriceFrom" value="${param.txtPriceFrom}" />
                            <input type="hidden" name="txtPriceTo" value="${param.txtPriceTo}" />
                            <input type="hidden" name="txtProductID" value="${product.productID}" />
                            <input type="hidden" name="txtProductName" value="${product.name}" />
                            <input type="hidden" name="txtProductPrice" value="${product.price}" />
                            <input type="submit" value="Buy this" />
                        </form>

                        </br></br></br> 
                    </c:forEach> 

                    <c:set var="numberOfPage" value="${requestScope.numberOfPage}"/> 
                    <c:if test="${numberOfPage gt 1}">
                        <c:forEach begin="1" step="1" end="${numberOfPage}" var="page">
                            <c:url var="loadLink" value="searchProduct">
                                <c:param name="curPage" value="${page}"/>
                                <c:param name="txtSearchName" value="${param.txtSearchName}"/>
                                <c:param name="txtPriceFrom" value="${param.txtPriceFrom}"/>
                                <c:param name="txtPriceTo" value="${param.txtPriceTo}"/>
                                <c:param name="txtCategoryName" value="${param.txtCategoryName}"/>
                            </c:url>
                            <a href="${loadLink}">${page}</a>
                        </c:forEach>
                    </c:if> 
                </c:if>
            </c:if>
        </c:if>        

        <c:if test="${empty user}"> 
            <c:set var="listProducts" value="${requestScope.listProduct}"/>
            <c:if test="${not empty listProducts}">
                <c:forEach var="product" items="${listProducts}" varStatus="counter">
                    <form action="addProductToCart">   
                        Name: ${product.name} </br>
                        <img src="${product.image} "/></br> 
                        Description: ${product.description} </br>
                        Price: ${product.price} </br>
                        Create Date: ${product.createDate} </br>
                        Expiration Date: ${product.expirationDate} </br>
                        Quantity: ${product.quantity} </br>
                        Category:  
                        <c:forEach var="category" items="${listCategory}">
                            <c:if test="${category.id eq product.categoryID}">
                                ${category.name}</br>
                            </c:if>
                        </c:forEach>
                        <input type="hidden" name="txtSearchName" value="${param.txtSearchName}" />
                        <input type="hidden" name="txtCategoryName" value="${param.txtCategoryName}" />
                        <input type="hidden" name="txtPriceFrom" value="${param.txtPriceFrom}" />
                        <input type="hidden" name="txtPriceTo" value="${param.txtPriceTo}" />
                        <input type="hidden" name="txtProductID" value="${product.productID}" />
                        <input type="hidden" name="txtProductName" value="${product.name}" />
                        <input type="hidden" name="txtProductPrice" value="${product.price}" />
                        <input type="submit" value="Buy this" />
                    </form>

                    </br></br></br> 
                </c:forEach> 

                <c:set var="numberOfPage" value="${requestScope.numberOfPage}"/> 
                <c:if test="${numberOfPage gt 1}">
                    <c:forEach begin="1" step="1" end="${numberOfPage}" var="page">
                        <c:url var="loadLink" value="searchProduct">
                            <c:param name="curPage" value="${page}"/>
                            <c:param name="txtSearchName" value="${param.txtSearchName}"/>
                            <c:param name="txtPriceFrom" value="${param.txtPriceFrom}"/>
                            <c:param name="txtPriceTo" value="${param.txtPriceTo}"/>
                            <c:param name="txtCategoryName" value="${param.txtCategoryName}"/>
                        </c:url>
                        <a href="${loadLink}">${page}</a>
                    </c:forEach>
                </c:if> 
            </c:if>
        </c:if> 
        <c:if test="${empty listProducts}">
            Can not find any cake!
        </c:if>
    </body>
</html>

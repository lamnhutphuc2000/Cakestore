<%-- 
    Document   : showActiveUser
    Created on : Nov 3, 2020, 7:33:20 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body> 
        <c:set var="roleIDOfAdmin" value="${sessionScope.roleIDOfAdmin}"/>
        <c:set var="user" value="${sessionScope.user}"/>
        <c:if test="${not empty user}">
            <c:if test="${user.roleID eq roleIDOfAdmin}"> 

                <c:set var="listUser" value="${requestScope.listUser}"/> 
                <c:if test="${not empty listUser}">
                    <table border="1">
                        <thead>
                            <tr> 
                                <th>UserID</th>
                                <th>Name</th>
                                <th>Phone</th> 
                                <th>Address</th> 
                                <th>Status</th> 
                                <th>roleID</th> 
                            </tr>
                        </thead>
                        <c:forEach var="user" items="${listUser}">
                            <tr> 
                                <th>${user.userID}</th>
                                <th>${user.name}</th>
                                <th>${user.phone}</th>
                                <th>${user.address}</th>
                                <th>${user.status}</th>
                                <th>${user.roleID}</th>  
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>

            </c:if>
        </c:if>
        <c:if test="${not empty user}">
            <c:if test="${user.roleID ne roleIDOfAdmin}">
                <c:redirect url="searchProduct"/>
            </c:if>
        </c:if>
        <c:if test="${  empty user}"> 
            <c:redirect url="searchProduct"/> 
        </c:if>
        <form action="searchProduct">
            <input type="submit" value="Return" />
        </form>
    </body>
</html>

<%-- 
    Document   : index
    Created on : Apr 23, 2013, 1:22:29 PM
    Author     : mulan
--%>

<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<c:if test="${not empty chyba}">
    <div style="border: solid 1px red; background-color: yellow; padding: 10px">
        <c:out value="${chyba}"/>
    </div>
</c:if>
<table border="1">
    <thead>
    <tr>
        <th>Názov</th>
        <th>Monžstvo</th>
        <th>Jednotka</th>
        <th colspan ="2">ahoj</th>
    </tr>
    </thead>
    <c:forEach items="${ingredients}" var="ingredient">
        <c:choose>
            <c:when test="${(not empty idedit) and (idedit eq ingredient.id)}">
                <form action="${pageContext.request.contextPath}/ingredients/edit" method="post">
                    <tr>
                        <td><input type="text" name="name" value="<c:out value='${ingredient.name}'/>"/></td>


                        <td><input type="text" name="amount" value="<c:out value='${ingredient.amount}'/>"/></td>


                        <td><input type="text" name="unit" value="<c:out value='${ingredient.unit}'/>"/></td>

                        <td colspan ="2" style="text-align: center;"><input name="id" type="hidden" value="${ingredient.id}"><input type="Submit" value="Uprav" /></td>
                    </tr>
                </form>
            </c:when>
            <c:otherwise>
                <tr>
                    <td><c:out value="${ingredient.name}"/></td>
                    <td><c:out value="${ingredient.amount}"/></td>
                    <td><c:out value="${ingredient.unit}"/></td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/ingredients/delete?id=${ingredient.id}" style="margin-bottom: 0;">
                            <input type="submit" value="Odstrániť">
                        </form>
                    </td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}/ingredients?idedit=${ingredient.id}" style="margin-bottom: 0;">
                            <input type="submit" value="Upraviť">
                            <input type="hidden" name="idedit" value="${ingredient.id}">
                        </form>
                    </td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
        <form action="${pageContext.request.contextPath}/ingredients/add" method="post">
            <tr>
                <td><input type="text" name="name" value="<c:out value=''/>"/></td>


                <td><input type="text" name="amount" value="<c:out value=''/>"/></td>


                <td><input type="text" name="unit" value="<c:out value=''/>"/></td>

                <td colspan ="2" style="text-align: center;"><input type="Submit" value="Pridať" /></td>
            </tr>
        </form>
</table>
    

</body>
</html>

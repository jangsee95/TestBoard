<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>테스트 페이지</title>
</head>
<body>
    <h1>Servlet -> JSP 데이터 전달 테스트</h1>
    
    <h3>전달받은 메시지 목록:</h3>
    
    <ul>
        <c:forEach var="msg" items="${messageList}">
            <li>${msg}</li>
        </c:forEach>
    </ul>
    
    <c:if test="${empty messageList}">
        <p style="color: red;">오류: 서블릿으로부터 messageList를 받지 못했습니다.</p>
    </c:if>

</body>
</html>
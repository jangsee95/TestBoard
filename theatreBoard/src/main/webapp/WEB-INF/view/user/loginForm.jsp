<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
    <h2>로그인</h2>
    <form action="${pageContext.request.contextPath}/user?act=login" method="post">
        <label for="userId">아이디:</label><br>
        <input type="text" id="userId" name="userId"><br>
        <label for="password">비밀번호:</label><br>
        <input type="password" id="password" name="password"><br><br>
        <input type="submit" value="로그인">
    </form>
</body>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
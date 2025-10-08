<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/header.jsp" %>

<h2>비밀번호 변경</h2>

<c:if test="${not empty sessionScope.loginUser}">
    <form action="${pageContext.request.contextPath}/user?act=changePassword" method="post">
        <label for="currentPassword">현재 비밀번호:</label><br>
        <input type="password" id="currentPassword" name="currentPassword" required><br>
        
        <label for="newPassword">새 비밀번호:</label><br>
        <input type="password" id="newPassword" name="newPassword" required><br>
        
        <label for="confirmNewPassword">새 비밀번호 확인:</label><br>
        <input type="password" id="confirmNewPassword" name="confirmNewPassword" required><br><br>
        
        <input type="submit" value="비밀번호 변경">
    </form>
</c:if>

<c:if test="${empty sessionScope.loginUser}">
    <p>로그인이 필요한 서비스입니다.</p>
</c:if>

<%@ include file="/WEB-INF/view/common/footer.jsp" %>
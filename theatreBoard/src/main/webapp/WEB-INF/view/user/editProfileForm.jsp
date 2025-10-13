<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/header.jsp" %>

<h2>회원정보 수정</h2>
    <script>
        var msg = "${sessionScope.msg}";
        if (msg && msg.trim() !== "") {
            alert(msg);
            <% session.removeAttribute("msg"); %>
        }
    </script>

<c:if test="${not empty sessionScope.loginUser}">
    <form action="${pageContext.request.contextPath}/user?act=updateUser" method="post">
        <label for="userId">아이디:</label><br>
        <input type.text" id="userId" name="userId" value="${sessionScope.loginUser.userId}" readonly><br>
        
        <label for="userName">이름:</label><br>
        <input type="text" id="userName" name="userName" value="${sessionScope.loginUser.userName}"><br>
        
        <label for="email">이메일:</label><br>
        <input type="email" id="email" name="email" value="${sessionScope.loginUser.email}"><br><br>
        <input type="submit" value="수정">
    </form>
    <form action="${pageContext.request.contextPath}/user?act=deleteUser" method="post">
    	<input type="hidden" name="userId" value="${sessionScope.loginUser.userId}">
    	<input type="submit" value="삭제">
    </form>
</c:if>

<c:if test="${empty sessionScope.loginUser}">
    <p>로그인이 필요한 서비스입니다.</p>
</c:if>

<%@ include file="/WEB-INF/view/common/footer.jsp" %>
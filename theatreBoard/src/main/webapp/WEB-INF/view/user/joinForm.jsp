<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/header.jsp" %>
<body>
    <h2>회원가입</h2>
    <form action="${pageContext.request.contextPath}/user?act=join" method="post">
        <label for="userId">아이디:</label><br>
        <input type="text" id="userId" name="userId"><br>
        <label for="password">비밀번호:</label><br>
        <input type="password" id="password" name="password"><br>
        <label for="userName">이름:</label><br>
        <input type="text" id="userName" name="userName"><br>
        <label for="email">이메일:</label><br>
        <input type="email" id="email" name="email"><br><br>
        <input type="submit" value="회원가입">
    </form>
        <a href="${pageContext.request.contextPath}/user?act=loginForm"><button>로그인</button></a>
</body>

<%@ include file="/WEB-INF/view/common/footer.jsp" %>
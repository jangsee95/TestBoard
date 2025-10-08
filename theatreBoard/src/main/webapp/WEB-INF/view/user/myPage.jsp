<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/header.jsp" %>

<h2>마이페이지</h2>
    <script>
        var msg = "${sessionScope.msg}";
        if (msg && msg.trim() !== "") {
            alert(msg);
            <% session.removeAttribute("msg"); %>
        }
    </script>

<c:if test="${not empty sessionScope.loginUser}">
    <p>아이디: ${sessionScope.loginUser.userId}</p>
    <p>이름: ${sessionScope.loginUser.userName}</p>
    <p>이메일: ${sessionScope.loginUser.email}</p>
    <a href="${pageContext.request.contextPath}/user?act=editProfileForm">회원정보 수정</a>
    <a href="${pageContext.request.contextPath}/user?act=changePasswordForm">비밀번호 변경</a>
</c:if>

<c:if test="${empty sessionScope.loginUser}">
    <p>로그인이 필요한 서비스입니다.</p>
</c:if>

<%@ include file="/WEB-INF/view/common/footer.jsp" %>
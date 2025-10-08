<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/header.jsp" %>

<h2>게시글 작성</h2>
<form action="${pageContext.request.contextPath}/board?act=write" method="post">
	<label for="title">제목</label>
	<input type="text" id="title" name="title"><br>
	<label for="author">작성자</label>
	<input type="text" id="author" name="author" value="${sessionScope.loginUser.userName}" readonly="readonly"><br>
	<label for="content">내용</label>
	<textarea rows="30" cols="30" id="content" name="content"></textarea>
	<input type="submit" value="게시">
</form>

<%@ include file="/WEB-INF/view/common/footer.jsp" %>
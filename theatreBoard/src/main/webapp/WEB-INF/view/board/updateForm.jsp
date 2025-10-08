<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/header.jsp" %>

<h2>게시글 수정</h2>
<form action="${pageContext.request.contextPath}/board?act=update" method="post">
	<input type="hidden" name="boardId" value="${board.boardId}">
	<label for="title">제목</label>
	<input type="text" id="title" name="title" value="${board.title}"><br>
	<label for="content">내용</label>
	<textarea rows="20" cols="30" id="content" name="content">${board.content}</textarea>
	<input type="submit" value="수정">
</form>

<%@ include file="/WEB-INF/view/common/footer.jsp" %>
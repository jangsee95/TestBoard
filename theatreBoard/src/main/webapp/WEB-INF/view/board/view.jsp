<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/view/common/header.jsp" %>

<div class="container">
    <h2>게시글 상세</h2>
    <c:if test="${not empty board}">
        <div>
            <h3>${board.title}</h3>
            <p><strong>작성자:</strong> ${board.author.userName}</p>
            <hr>
            <div>
                <p>${board.content}</p>
            </div>
        </div>
    </c:if>
    <c:if test="${empty board}">
        <p>게시글을 찾을 수 없습니다.</p>
    </c:if>
    <br>
    <a href="${pageContext.request.contextPath}/board?act=list"><button>목록</button></a>
    <c:if test="${not empty sessionScope.loginUser && board.author.userId == sessionScope.loginUser.userId}">
	<a href="${pageContext.request.contextPath}/board?act=updateForm&boardId=${board.boardId}"><button>수정</button></a>
	<a href="${pageContext.request.contextPath}/board?act=remove&boardId=${board.boardId}"><button>삭제</button></a>
	</c:if>
</div>
<hr>
<div class="comment_container">
	<c:if test="${empty commentList}">
		<h4>댓글이 없습니다.</h4>
	</c:if>
	<c:if test="${not empty commentList}">
	<c:forEach var="comment" items="${commentList}">
		<div class ="commentBox">
			<p>${comment.content}</p>
			<div><strong>작성자</strong> ${comment.author.userName}</div>
			<div><strong>작성일시</strong> ${comment.createdAt }</div>
			<hr>
		</div>
	</c:forEach>
	</c:if>
	<form action="${pageContext.request.contextPath}/comment?act=add" method="post" onsubmit="return disableSubmitButton(this)">
		<input type="hidden" name="board_id" value="${board.boardId}">
		<input type="hidden" name="commentToken" value="${commentToken}">
		<p>댓글 작성하기</p>
		<input type="text" id="content" name="content">
		<input type="submit" id="submitBtn" value="작성">
	</form>
</div>
<script>
	function disableSubmitButton(form) {
		var submitButton = form.querySelector('#submitBtn');
		submitButton.disabled = true;
		submitButton.value = "처리 중...";
		return true;
	}
</script>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
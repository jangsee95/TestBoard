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
	</c:if>
</div>

<%@ include file="/WEB-INF/view/common/footer.jsp" %>
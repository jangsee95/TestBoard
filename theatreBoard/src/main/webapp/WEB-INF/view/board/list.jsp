<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/header.jsp" %>
<div class="container">
    <h2>게시판</h2>
    <table border="1" style="width:100%; text-align: center;">
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="board" items="${boardList}">
                <tr>
                    <td>${board.boardId}</td>
                    <td><a href="${pageContext.request.contextPath}/board?act=view&boardId=${board.boardId}">${board.title}</a></td>
                    <td>${board.author.userName}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty boardList}">
                <tr>
                    <td colspan="3">게시글이 없습니다.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
    <br>
    <a href="${pageContext.request.contextPath}/board?act=writeForm">글쓰기</a>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
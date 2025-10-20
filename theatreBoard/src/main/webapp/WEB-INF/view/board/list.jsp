<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                    <td>
           			</td>
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
    <%-- 페이지 네비게이션 추가 --%>
    <div class="pagination" style="text-align: center;">
        <%-- 이전 페이지 링크 --%>
        <c:if test="${pageInfo.currentPage > 1}">
            <a href="${pageContext.request.contextPath}/board?act=list&page=${pageInfo.currentPage - 1}">[이전]</a>
        </c:if>

        <%-- 페이지 번호 링크 (예: 1 2 3 4 5) --%>
        <%-- 간단하게 전체 페이지 번호를 보여주는 방식 --%>
        <c:forEach var="pageNum" begin="1" end="${pageInfo.totalPages}">
            <c:choose>
                <c:when test="${pageNum == pageInfo.currentPage}">
                    <%-- 현재 페이지는 링크 없이 굵게 표시 --%>
                    <strong>[${pageNum}]</strong>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/board?act=list&page=${pageNum}">[${pageNum}]</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <%-- 다음 페이지 링크 --%>
        <c:if test="${pageInfo.currentPage < pageInfo.totalPages}">
            <a href="${pageContext.request.contextPath}/board?act=list&page=${pageInfo.currentPage + 1}">[다음]</a>
        </c:if>
    </div>
    <br>
    <a href="${pageContext.request.contextPath}/board?act=writeForm">글쓰기</a>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
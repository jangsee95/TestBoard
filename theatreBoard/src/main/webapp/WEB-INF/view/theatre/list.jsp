<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/header.jsp" %>
<div class="container">
    <h2>공연 리스트</h2>
    <table border="1" style="width:100%; text-align: center;">
        <thead>
            <tr>
                <th>번호</th>
                <th>공연 제목</th>
                <th>장르</th>
                <th>상영시간</th>
                <th>포스터</th>
                <th>공연일시</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="theatre" items="${theatreList}">
                <tr>
                    <td>${theatre.theatreId}</td>
                    <td><a href="${pageContext.request.contextPath}/theatre?act=view&theatreId=${theatre.theatreId}">${theatre.title}</a></td>
                    <td>${theatre.genre}</td>
                    <td>${theatre.playTime}</td>
                    <td>${theatre.posterUri}</td>
                    <td>${theatre.performanceDateTime}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty theatreList}">
                <tr>
                    <td colspan="3">공연정보가 없습니다.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
    <br>
    <a href="${pageContext.request.contextPath}/theatre?act=writeForm">공연 등록</a>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
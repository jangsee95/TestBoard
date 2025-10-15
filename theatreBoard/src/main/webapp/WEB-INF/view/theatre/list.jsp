<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/header.jsp" %>
<div class="container">
    <h2>공연 리스트</h2>
    <div class="card-container">
        <c:forEach var="theatre" items="${theatreList}">
            <a href="${pageContext.request.contextPath}/theatre?act=view&theatreId=${theatre.theatreId}" class="card-link">
                <div class="card">
                    <img src="${theatre.posterUrl}" alt="${theatre.title} 포스터">
                    <div class="card-content">
                        <h4>${theatre.title}</h4>
                        <p>${theatre.genre}</p>
                        <p>${theatre.playTime}분</p>
                        <p>${theatre.performanceDateTime}</p>
                    </div>
                </div>
            </a>
        </c:forEach>
        <c:if test="${empty theatreList}">
            <p>공연정보가 없습니다.</p>
        </c:if>
    </div>
    <br>
    <a href="${pageContext.request.contextPath}/theatre?act=writeForm">공연 등록</a>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
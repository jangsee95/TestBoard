<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/view/common/header.jsp" %>

<div class="container">
    <h2>공연 상세 정보</h2>
    <c:if test="${not empty theatre}">
        <div class="theatre-detail">
            <h3>${theatre.title}</h3>
            <p><strong>장르:</strong> ${theatre.genre}</p>
            <p><strong>공연 시간:</strong> ${theatre.playTime}분</p>
            <p><strong>공연 일시:</strong> ${theatre.performanceDateTime}</p>
            <img src="${theatre.posterUrl}" alt="${theatre.title} 포스터" style="max-width: 100%; height: auto; margin-top: 10px;">
            <hr>
            <h4>상세 내용</h4>
            <p>${theatre.content}</p>
            <p><strong>등록일:</strong> ${theatre.createdAt}</p>
        </div>
    </c:if>
    <c:if test="${empty theatre}">
        <p>공연 정보를 찾을 수 없습니다.</p>
    </c:if>
    <br>
    <a href="${pageContext.request.contextPath}/theatre?act=list"><button>목록으로</button></a>
</div>

<hr>
<div class="review_container">
    <h3>리뷰</h3>
    <c:if test="${empty reviewList}">
        <p>등록된 리뷰가 없습니다.</p>
    </c:if>
    <c:if test="${not empty reviewList}">
        <c:forEach var="review" items="${reviewList}">
            <div class="review-item">
                <p><strong>작성자:</strong> ${review.author.userName} | 
                   <strong>평점:</strong> ${review.rating}점 | 
                   <strong>작성일:</strong> ${review.createdAt}</p>
                <p>${review.content}</p>
            </div>
            <hr>
        </c:forEach>
    </c:if>

    <h4>리뷰 작성</h4>
    <form action="${pageContext.request.contextPath}/review?act=add" method="post">
        <input type="hidden" name="theatre_id" value="${theatre.theatreId}">
        <%-- CSRF 토큰을 사용할 경우 (ReviewServlet에서 구현 필요) --%>
        <%-- <input type="hidden" name="reviewToken" value="${reviewFormToken}"> --%>
        
        <label for="reviewTitle">제목:</label><br>
        <input type="text" id="reviewTitle" name="title" required><br>
        
        <label for="reviewContent">내용:</label><br>
        <textarea id="reviewContent" name="content" rows="5" required></textarea><br>
        
        <label for="reviewRating">평점 (0.0 ~ 5.0):</label><br>
        <input type="number" id="reviewRating" name="rating" step="0.5" min="0" max="5" required><br><br>
        
        <input type="submit" value="리뷰 등록">
    </form>
</div>

<%@ include file="/WEB-INF/view/common/footer.jsp" %>
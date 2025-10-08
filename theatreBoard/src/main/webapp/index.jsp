<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/view/common/header.jsp" %>

<h2>안녕하세요 대문입니다.</h2>
<a href="${pageContext.request.contextPath}/board?act=list">게시판 보기</a>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
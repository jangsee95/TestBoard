<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Theatre Board</title>
</head>
<body>
    <header>
        <nav>
            <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
            <c:choose>
                <c:when test="${empty sessionScope.loginUser}">
                    <a href="${pageContext.request.contextPath}/user?act=loginForm">로그인</a>
                    <a href="${pageContext.request.contextPath}/user?act=joinForm">회원가입</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/user?act=myPage">프로필</a>
                    <a href="${pageContext.request.contextPath}/user?act=logout">로그아웃</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>
    <hr>
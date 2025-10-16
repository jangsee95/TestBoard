<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="https://jakarta.ee/taglibs/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Theatre Board</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css?v=1.1">
</head>
<body>
    <header>
        <nav class="navbar">
            <div class="nav-left">
                <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
            </div>
            <div class="nav-center">
                <a href="${pageContext.request.contextPath}/index.jsp">Theatre Board</a>
            </div>
            <div class="nav-right">
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
            </div>
        </nav>
    </header>
    <hr>
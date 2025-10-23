<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<hr>
<footer>
    <p>&copy; 2025 Theatre Board</p>
</footer>
<c:if test="${not empty fn:trim(requestScope.msg)}">
    <script>
        alert("${requestScope.msg}");
    </script>
       <c:remove var="msg" scope="request"/>
</c:if>
</body>
</html>
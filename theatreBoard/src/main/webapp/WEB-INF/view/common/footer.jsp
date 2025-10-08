<hr>
<footer>
    <p>&copy; 2025 Theatre Board</p>
</footer>
<c:if test="${not empty sessionScope.msg}">
    <script>
        alert("${sessionScope.msg}");
        <% session.removeAttribute("msg"); %>
    </script>
</c:if>
</body>
</html>
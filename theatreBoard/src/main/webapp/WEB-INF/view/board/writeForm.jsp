<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/header.jsp"%>

<h2>게시글 작성</h2>
<form action="${pageContext.request.contextPath}/board?act=write"
	method="post" onsubmit="return disableSubmitButton(this)">
	<input type="hidden" name="token" value="${token}">
	<label for="title">제목</label> <input type="text" id="title"
		name="title"><br> <label for="author">작성자</label> <input
		type="text" id="author" name="author"
		value="${sessionScope.loginUser.userName}" readonly="readonly"><br>
	<label for="content">내용</label>
	<textarea rows="30" cols="30" id="content" name="content"></textarea>
	<input type="submit" value="게시" id="submitBtn">
</form>
<script>
	function disableSubmitButton(form) {
		// id가 'submitBtn'인 버튼을 찾습니다.
		var submitButton = form.querySelector('#submitBtn');

		// 1. 버튼을 즉시 비활성화하여 추가 클릭을 막습니다.
		submitButton.disabled = true;

		// 2. 사용자에게 처리 중임을 알려줍니다. (선택사항)
		submitButton.value = "처리 중...";

		// 3. 폼 전송을 계속 진행합니다.
		return true;
	}
</script>
<%@ include file="/WEB-INF/view/common/footer.jsp"%>
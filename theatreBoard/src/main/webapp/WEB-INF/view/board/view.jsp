<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/view/common/header.jsp" %>

<div class="container">
    <h2>게시글 상세</h2>
    <c:if test="${not empty board}">
        <div>
            <h3>${board.title}</h3>
            <p><strong>작성자:</strong> ${board.author.userName}</p>
            <hr>
            <div>
                <p>${board.content}</p>
            </div>
        </div>
    </c:if>
    <c:if test="${empty board}">
        <p>게시글을 찾을 수 없습니다.</p>
    </c:if>
    <br>
    <a href="${pageContext.request.contextPath}/board?act=list"><button>목록</button></a>
    <c:if test="${not empty sessionScope.loginUser && board.author.userId == sessionScope.loginUser.userId}">
	<a href="${pageContext.request.contextPath}/board?act=updateForm&boardId=${board.boardId}"><button>수정</button></a>
	<a href="${pageContext.request.contextPath}/board?act=remove&boardId=${board.boardId}"><button>삭제</button></a>
	</c:if>
</div>
<hr>
<div class="comment_container">
	<c:if test="${empty commentList}">
		<h4>댓글이 없습니다.</h4>
	</c:if>
	<c:if test="${not empty commentList}">
	<c:forEach var="comment" items="${commentList}">
		<div class ="commentBox" id="comment-box-${comment.commentId}">
			<p id="comment-content-${comment.commentId}">${comment.content}</p>
			<div><strong>작성자</strong> ${comment.author.userName}</div>
			<div><strong>작성일시</strong> ${comment.createdAt}</div>
			<c:if test="${not empty sessionScope.loginUser && comment.author.userId == sessionScope.loginUser.userId}">
				<form action="${pageContext.request.contextPath}/comment?act=remove" method="post" onsubmit="return disableSubmitButton(this)" >
					<input type="hidden" name="board_id" value="${board.boardId}">
					<input type="hidden" name="comment_id" value="${comment.commentId}">
					<input type="submit" id="submitBtn" value="삭제">
				</form>
					<button class="updateBtn" data-comment-id="${comment.commentId}">수정</button>
			</c:if>
			<hr>
		</div>
	</c:forEach>
	</c:if>
	<form action="${pageContext.request.contextPath}/comment?act=add" method="post" onsubmit="return disableSubmitButton(this)">
		<input type="hidden" name="board_id" value="${board.boardId}">
		<input type="hidden" name="commentToken" value="${commentToken}">
		<input type="text" id="content" name="content" placeholder="댓글을 입력하세요">
		<input type="submit" id="submitBtn" value="작성">
	</form>
</div>
<div id="updateModal" class="modal">
    <div class="modal-content">
        <span class="close-button">&times;</span>
        <h3>댓글 수정</h3>
        <textarea id="modalTextarea" rows="5" cols="60"></textarea>
        <button id="modalSaveBtn">저장</button>
    </div>
</div>
<script>
	function disableSubmitButton(form) {
		var submitButton = form.querySelector('#submitBtn');
		submitButton.disabled = true;
		submitButton.value = "처리 중...";
		return true;
	}
	
	// 1. 모달 관련 요소들을 가져옵니다.
    const modal = document.getElementById('updateModal');
    const modalTextarea = document.getElementById('modalTextarea');
    const saveBtn = document.getElementById('modalSaveBtn');
    const closeBtn = document.querySelector('.close-button');
    let currentCommentId = null; // 수정할 댓글의 ID를 저장할 변수

    // 2. 모든 '수정' 버튼에 클릭 이벤트를 추가합니다.
    document.querySelectorAll('.updateBtn').forEach(button => {
        button.addEventListener('click', function() {
            // 클릭된 버튼에서 commentId를 가져옵니다.
            currentCommentId = this.dataset.commentId;
            
            // 해당 댓글의 현재 내용을 가져와서 textarea에 넣습니다.
            const currentContent = document.getElementById('comment-content-' + currentCommentId).innerText;
            modalTextarea.value = currentContent;
            
            // 모달 창을 보여줍니다.
            modal.style.display = 'flex';
        });
    });

    // 3. 모달의 '닫기' 버튼을 누르면 모달을 숨깁니다.
    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    
    // 4. '저장' 버튼을 누르면 서버로 수정 내용을 전송합니다. (AJAX)
    saveBtn.addEventListener('click', function() {
        const newContent = modalTextarea.value;
        
        // 서버로 보낼 데이터를 준비합니다.
        const formData = new URLSearchParams();
        formData.append('act', 'update');
        formData.append('comment_id', currentCommentId);
        formData.append('content', newContent);

        // fetch API를 사용해 서버에 데이터를 보냅니다.
        fetch('${pageContext.request.contextPath}/comment', {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (response.ok) {
                // 성공적으로 응답을 받으면, 화면의 댓글 내용을 바로 수정합니다.
                document.getElementById('comment-content-' + currentCommentId).innerText = newContent;
                // 모달을 닫습니다.
                modal.style.display = 'none';
            } else {
                alert('댓글 수정에 실패했습니다.');
            }
        });
    });
</script>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
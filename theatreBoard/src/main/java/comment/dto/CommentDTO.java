package comment.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import user.dto.UserDTO;

public class CommentDTO {
	private int commentId;
	private int boardId;
	private UserDTO author;
	private String content;
	private LocalDateTime createdAt;
	
	
	public CommentDTO() {
	}
	
	public CommentDTO(int commentId, int boardId, UserDTO author, String content, LocalDateTime regDate) {
		this.commentId = commentId;
		this.boardId = boardId;
		this.author = author;
		this.content = content;
		this.createdAt = regDate;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public UserDTO getAuthor() {
		return author;
	}

	public void setAuthor(UserDTO author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime regDate) {
		this.createdAt = regDate;
	}

	@Override
	public String toString() {
		return "CommentDTO [commentId=" + commentId + ", boardId=" + boardId + ", author=" + author + ", content="
				+ content + ", regDate=" + createdAt + "]";
	}
	
	
	
	
}

					

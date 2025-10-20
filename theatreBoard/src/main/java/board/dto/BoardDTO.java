package board.dto;

import java.time.LocalDateTime;

import user.dto.UserDTO;

public class BoardDTO {
	private int boardId;
	private String title;
	private String content;
	private UserDTO author;
	private LocalDateTime createdAt;
	
	
	public BoardDTO() {
	}


	public BoardDTO(int boardId, String title, String content, UserDTO author, LocalDateTime createdAt) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.author = author;
		this.createdAt = createdAt;
	}


	public int getBoardId() {
		return boardId;
	}


	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public UserDTO getAuthor() {
		return author;
	}


	public void setAuthor(UserDTO author) {
		this.author = author;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	@Override
	public String toString() {
		return "BoardDTO [boardId=" + boardId + ", title=" + title + ", content=" + content + ", author=" + author
				+ ", createdAt=" + createdAt + "]";
	}
}

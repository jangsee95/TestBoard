package board.dto;

import user.dto.UserDTO;

public class BoardDTO {
	private int boardId;
	private String title;
	private String content;
	private UserDTO author;
	
	
	public BoardDTO() {
	}


	public BoardDTO(int boardId, String title, String content, UserDTO author) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.author = author;
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


	@Override
	public String toString() {
		return "BoardDTO [boardId=" + boardId + ", title=" + title + ", content=" + content + ", author=" + author
				+ "]";
	}
	
	
	
}

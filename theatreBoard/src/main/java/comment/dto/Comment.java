package comment.dto;

import user.dto.UserDTO;

public class Comment {
	private int commentId;
	private UserDTO author;
	private String content;
	private int boardId;
}

package comment.service;

import java.sql.SQLException;
import java.util.List;

import comment.dao.CommentDAO;
import comment.dto.CommentDTO;

public class CommentService {
	private CommentDAO commentDAO = CommentDAO.getinstance();
	
	private static CommentService instacne = new CommentService();
	
	private CommentService() {
	}
	
	public static CommentService getInstance() {
		return instacne;
	}
	
	public boolean writeComment(CommentDTO comment) {
		try {
			commentDAO.insert(comment);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<CommentDTO> getList(int boardId) {
		try {
			return commentDAO.selectListByBoardId(boardId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean updateComment(CommentDTO comment) {
		try {
			commentDAO.updateComment(comment);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteComment(int commentId) {
		try {
			commentDAO.deleteOneByCommentId(commentId);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteCommentList(int boardId) {
		try {
			commentDAO.deleteBoardCommentByBoardId(boardId);
			return true;
		} catch (SQLException e) {
			e.addSuppressed(e);
			return false;
		}
	}
	
}

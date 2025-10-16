package comment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comment.dto.CommentDTO;
import common.util.DBUtil;
import user.dao.UserDAO;
import user.dto.UserDTO;

public class CommentDAO {
	private DBUtil util = DBUtil.getInstance();
	
	private static CommentDAO instance = new CommentDAO();
	
	private CommentDAO() {}
	
	public static CommentDAO getinstance() {
		return instance;
	}
	
	public List<CommentDTO> selectListByBoardId(int boardId) throws SQLException {
		List<CommentDTO> list = new ArrayList<>();
		String sql = "SELECT c.comment_id, c.board_id, c.content, c.created_at, " +
                "u.user_id, u.user_name " +
                "FROM comments c JOIN users u ON c.user_id = u.user_id " +
                "WHERE c.board_id = ? ORDER BY c.created_at ASC";
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, boardId);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					CommentDTO comment = new CommentDTO();
					comment.setCommentId(rs.getInt("comment_id"));
					comment.setBoardId(rs.getInt("board_id"));
					comment.setContent(rs.getString("content"));
					comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
					UserDTO author = new UserDTO();
					author.setUserId(rs.getString("user_id"));
					author.setUserName(rs.getString("user_name"));
					comment.setAuthor(author);
					list.add(comment);
				}
			}
			return list;
		} 
	}
	
	
	public void insert(CommentDTO comment) throws SQLException {
		String sql = "INSERT INTO comments (board_id, user_id, content) VALUES (?, ?, ?)";
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, comment.getBoardId());
			pstmt.setString(2, comment.getAuthor().getUserId());
			pstmt.setString(3, comment.getContent());
			pstmt.executeUpdate();
		}
	}
	
	public void deleteOneByCommentId(int commentId) throws SQLException {
		String sql = "DELETE FROM comments WHERE comment_id = ?";
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, commentId);
			
			pstmt.executeUpdate();
		}
	}
	
	public void deleteBoardCommentByBoardId(int boardId) throws SQLException {
		String sql = "DELETE FROM comments WHERE board_id = ?";
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, boardId);
			
			pstmt.executeUpdate();
		}
	}
	
	public void updateComment(CommentDTO comment) throws SQLException {
		String sql = "UPDATE comments SET content = ? WHERE comment_id = ?";
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, comment.getContent());
			pstmt.setInt(2, comment.getCommentId());
			
			pstmt.executeUpdate();
		}
	}
}

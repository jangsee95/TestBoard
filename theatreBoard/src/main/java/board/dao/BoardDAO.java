package board.dao;

import board.dto.BoardDTO;
import common.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import user.dao.UserDAO;
import user.dto.UserDTO;

public class BoardDAO {
	private DBUtil util = DBUtil.getInstance();
	private UserDAO userDAO = UserDAO.getInstance();
	
	private static BoardDAO intance = new BoardDAO();
	
	private BoardDAO() {}
	
	public static BoardDAO getInstance () {
		return intance;
	}
	
	public int getTotalCount() throws SQLException {
	    String sql = "SELECT COUNT(*) FROM boards";
	    try (Connection conn = util.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {
	        if (rs.next()) {
	            return rs.getInt(1); // COUNT(*) 결과는 첫 번째 컬럼
	        }
	    }
	    return 0; // 게시글이 없을 경우 0 반환
	}
	
	public List<BoardDTO> selectPage(int offset, int limit) throws SQLException {
	    // SQL 수정: LIMIT와 OFFSET 추가
	    String sql = "SELECT b.board_id, b.title, u.user_name, b.board_regdate " +
	                 "FROM boards b JOIN users u ON b.user_id = u.user_id " +
	                 "ORDER BY b.board_id DESC " +
	                 "LIMIT ? OFFSET ?"; // LIMIT와 OFFSET 추가
	    List<BoardDTO> boards = new ArrayList<>();
	    try (Connection conn = util.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) { // try-with-resources 안으로 이동

	        pstmt.setInt(1, limit);    // LIMIT 설정
	        pstmt.setInt(2, offset);   // OFFSET 설정

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                BoardDTO board = new BoardDTO();
	                board.setBoardId(rs.getInt("board_id"));
	                board.setTitle(rs.getString("title"));
	                UserDTO author = new UserDTO();
	                // UserDAO를 통해 UserDTO 전체를 가져올 수도 있지만,
	                // 목록에서는 이름만 필요하므로 직접 설정
	                author.setUserName(rs.getString("user_name"));
	                board.setAuthor(author);
	                board.setCreatedAt(rs.getTimestamp("board_regdate").toLocalDateTime());
	                boards.add(board);
	            }
	        }
	    } // Connection, PreparedStatement 자동 close
	    return boards;
	}

	public List<BoardDTO> selectAll() throws SQLException {
		String sql = "SELECT b.board_id, b.title, u.user_name FROM boards b JOIN users u ON b.user_id = u.user_id ORDER BY b.board_id DESC";
		List<BoardDTO> boards = new ArrayList<>();
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					BoardDTO board = new BoardDTO();
					board.setBoardId(rs.getInt("board_id"));
					board.setTitle(rs.getString("title"));
					UserDTO author = new UserDTO();
					author.setUserName(rs.getString("user_name"));
					board.setAuthor(author);
					boards.add(board);
				}
			}
		}
		return boards;
	}
	
	public BoardDTO selectOne(int boardId) throws SQLException {
		String sql = "SELECT \r\n"
				+ "    b.board_id, b.title, b.content, b.board_regdate,\r\n"
				+ "    u.user_id, u.user_name, u.user_email\r\n"
				+ "FROM \r\n"
				+ "    boards b\r\n"
				+ "JOIN \r\n"
				+ "    users u ON b.user_id = u.user_id\r\n"
				+ "WHERE \r\n"
				+ "    b.board_id = ?;";
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, boardId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					BoardDTO board = new BoardDTO();
					board.setBoardId(rs.getInt("board_id"));
					board.setTitle(rs.getString("title"));
					board.setContent(rs.getString("content"));
					UserDTO author = new UserDTO();
	                author.setUserId(rs.getString("user_id"));
	                author.setUserName(rs.getString("user_name"));
	                author.setEmail(rs.getString("user_email")); // 필요하다면 이메일도 설정
	                board.setAuthor(author);
	                board.setCreatedAt(rs.getTimestamp("board_regdate").toLocalDateTime()); // 추가
					return board;
				}
			}
		}
		return null;
	}
	
	public boolean insert(BoardDTO board) throws SQLException {
		String sql = "INSERT INTO boards (title, content, user_id) VALUES (?, ? ,?);";
		
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getAuthor().getUserId());
			
			return pstmt.executeUpdate() == 1;
		}
	}
	
	public boolean delete(int boardId) throws SQLException {
		String sql = "DELETE FROM boards WHERE board_id = ?";
		
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, boardId);
			
			return pstmt.executeUpdate() == 1;
		}
	}
	
	public boolean update(BoardDTO board) throws SQLException {
		String sql = "UPDATE boards SET title = ?, content = ? WHERE board_id = ?";
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getBoardId());
			
			return pstmt.executeUpdate() == 1;
		}
	}
	
	public boolean deleteByUserId(String userId) throws SQLException {
		String sql = "DELETE FROM boards WHERE user_id = ?";
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, userId);
			return pstmt.executeUpdate() >= 1;
		}
	}
}

package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.util.DBUtil;
import user.dto.UserAuthDTO;
import user.dto.UserDTO;

public class UserDAO {

	private DBUtil util = DBUtil.getInstance();

	private static UserDAO instance = new UserDAO();

	private UserDAO() {
	}

	public static UserDAO getInstance() {
		return instance;
	}

	public boolean insert(UserDTO user, String hashedPassword) {
		String sql = "INSERT INTO users (user_id, user_password, user_name, user_email, user_regdate) VALUES (?, ?, ?, ?, now());";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, hashedPassword);
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getEmail());

			int result = pstmt.executeUpdate();
			return result > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(pstmt, conn);
		}

		return false;
	}

	public UserAuthDTO selectOneForAuth(String userId) throws SQLException {
		String sql = "SELECT user_id, user_password FROM users WHERE user_id = ?";

		try (Connection conn = util.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					UserAuthDTO user = new UserAuthDTO();
					user.setUserId(rs.getString("user_id"));
					user.setHashedPassword(rs.getString("user_password"));
					return user;
				}
			}
		}
		return null;
	}

	public UserDTO selectOne(String userId) throws SQLException {
		String sql = "SELECT * FROM users WHERE user_id = ?";

		try (Connection conn = util.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					UserDTO user = new UserDTO();
					user.setUserId(rs.getString("user_id"));
					user.setUserName(rs.getString("user_name"));
					user.setEmail(rs.getString("user_email"));
					user.setRegDate(rs.getString("user_regdate"));
					return user;
				}
			}
		}

		return null;
	}

	public boolean update(UserDTO user) throws SQLException {
		String sql = "UPDATE users SET user_name = ?, user_email = ? WHERE user_id = ?";

		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getUserId());

			int result = pstmt.executeUpdate();
			return result == 1;
		}
	}

	public boolean update(UserDTO user, String hashedPassword) {
		String sql = "UPDATE users SET user_password = ?, user_name = ?, user_email = ? WHERE user_id = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hashedPassword);
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getUserId());

			int result = pstmt.executeUpdate();
			return result == 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(pstmt, conn);
		}

		return false;
	}

	public boolean delete(String userId) throws SQLException {
		String sql = "DELETE FROM users WHERE user_id = ?";
		
		try (
				Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				) {
			pstmt.setString(1, userId);
			return pstmt.executeUpdate() == 1;
		}
	}
}

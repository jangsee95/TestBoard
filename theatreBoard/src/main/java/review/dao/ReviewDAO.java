package review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.util.DBUtil;
import review.dto.ReviewDTO;
import user.dto.UserDTO;

public class ReviewDAO {
	private DBUtil util = DBUtil.getInstance();
	
	private static ReviewDAO instance = new ReviewDAO();
	
	private ReviewDAO() {}
	
	public static ReviewDAO getInstance() {
		return instance;
	}
	
	public ReviewDTO getOne(int reviewId) throws SQLException {
		String sql = "select c.review_id, c.theatre_id, c.content, c.rating, c.created_at, u.user_id, u.user_name\r\n"
				+ "from reviews r join users u on r.user_id = u.user_id\r\n"
				+ "where r.review_id = ?";
		ReviewDTO review = new ReviewDTO();
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				) {
			pstmt.setInt(1, reviewId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					review.setReviewId(rs.getInt("review_id"));
	                review.setTheatreId(rs.getInt("theatre_id")); // theatre_id 설정
	                review.setContent(rs.getString("content"));
	                review.setRating(rs.getFloat("rating"));
	                review.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

	                // Author 정보 설정
	                UserDTO author = new UserDTO();
	                author.setUserId(rs.getString("user_id"));
	                author.setUserName(rs.getString("user_name"));
	                review.setAuthor(author);
				}
			}
		}
		
		return review;
	}
	
	public List<ReviewDTO> getList(int theatreId) throws SQLException {
		String sql = "select c.review_id, c.theatre_id, c.content, c.rating, c.created_at, u.user_id, u.user_name\r\n"
				+ "from reviews r join users u on r.user_id = u.user_id\r\n"
				+ "where r.theatre_id = ?\r\n"
				+ "order by r.created_at asc;";
		List<ReviewDTO> list = new ArrayList<>();
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, theatreId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ReviewDTO review = new ReviewDTO();
					review.setReviewId(rs.getInt("review_id"));
	                review.setTheatreId(rs.getInt("theatre_id")); // theatre_id 설정
	                review.setContent(rs.getString("content"));
	                review.setRating(rs.getFloat("rating"));
	                review.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

	                // Author 정보 설정
	                UserDTO author = new UserDTO();
	                author.setUserId(rs.getString("user_id"));
	                author.setUserName(rs.getString("user_name"));
	                review.setAuthor(author);
	                list.add(review);
				}
			}
		}
		return list;
	}
	
	public void insert (ReviewDTO review) {
		
	}
	
	public void update (ReviewDTO review) {
		
	}
	
	public void delete (int reviewId) {
		
	}
}

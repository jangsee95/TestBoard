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
	
	/* 하나 조회 메서드 */
	public ReviewDTO getOne(int reviewId) throws SQLException {
		String sql = "select r.review_id, r.theatre_id, r.title, r.content, r.rating, r.created_at, u.user_id, u.user_name\r\n"
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
	                review.setTheatreId(rs.getInt("theatre_id"));
	                review.setTitle(rs.getString("title"));
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

	/* 리스트 조회 메서드 */
	public List<ReviewDTO> getList(int theatreId) throws SQLException {
		String sql = "select r.review_id, r.theatre_id, r.title, r.content, r.rating, r.created_at, u.user_id, u.user_name\r\n"
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
	                review.setTheatreId(rs.getInt("theatre_id"));
	                review.setTitle(rs.getString("title"));
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
	
	public void insert (ReviewDTO review) throws SQLException {
		String sql = "insert into reviews (theatre_id, user_id, title, content, rating)"
				+ "VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, review.getTheatreId());
			pstmt.setString(2, review.getAuthor().getUserId());
			pstmt.setString(3, review.getTitle());
			pstmt.setString(4, review.getContent());
			pstmt.setFloat(5, review.getRating());
			
			pstmt.executeUpdate();
		}
	}
	
	public void update (ReviewDTO review) throws SQLException {
		String sql ="update reviews set title = ?, content = ?, rating = ?";
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, review.getTitle());
			pstmt.setString(2, review.getContent());
			pstmt.setFloat(3, review.getRating());
			
		}
	}
	
	public void delete (int reviewId) throws SQLException {
		String sql = "DELETE FROM reviews WHERE review_id = ?";
		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, reviewId);
			pstmt.executeUpdate();
		}
	}
	public float getAVGRating(int theatreId) throws SQLException {
		String sql = "SELECT AVG(rating) FROM reviews WHERE theatre_id = ?";
		float avgRating = 0;
		try (Connection conn = util.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {
		        pstmt.setInt(1, theatreId);
		        try (ResultSet rs = pstmt.executeQuery()) {
		            if (rs.next()) {
		                avgRating = rs.getFloat(1);
		                if (rs.wasNull()) {
		                    avgRating = 0; // 또는 null을 반환하거나 다른 기본값 설정
		                }
		            }
		        }
		    }
		return (float) (Math.round(avgRating * 100.0) / 100.0);
	}
}

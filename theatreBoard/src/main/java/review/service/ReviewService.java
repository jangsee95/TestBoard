package review.service;

import java.sql.SQLException;
import java.util.List;

import review.dao.ReviewDAO;
import review.dto.ReviewDTO;

public class ReviewService {
	ReviewDAO reviewDAO = ReviewDAO.getInstance();
	
	private static ReviewService instance = new ReviewService();
	
	private ReviewService() {}
	
	public static ReviewService getInstance() {
		return instance;
	}
	
	public boolean writeReview(ReviewDTO review) {
		try {
			reviewDAO.insert(review);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<ReviewDTO> getList(int theatreId) {
		try {
			return reviewDAO.getList(theatreId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ReviewDTO getOne(int reviewId) {
		try {
			return reviewDAO.getOne(reviewId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean updateReview(ReviewDTO review) {
		try {
			reviewDAO.update(review);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteReview(int reviewId) {
		try {
			reviewDAO.delete(reviewId);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}

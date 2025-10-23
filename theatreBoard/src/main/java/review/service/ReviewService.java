package review.service;

import java.sql.SQLException;
import java.util.List;

import review.dao.ReviewDAO;
import review.dto.ReviewDTO;
import theatre.dao.TheatreDAO;

public class ReviewService {
	private ReviewDAO reviewDAO = ReviewDAO.getInstance();
	private TheatreDAO theatreDAO = TheatreDAO.getInstance();
	
	private static ReviewService instance = new ReviewService();
	
	private ReviewService() {}
	
	public static ReviewService getInstance() {
		return instance;
	}
	
	public boolean writeReview(ReviewDTO review) {
		try {
			reviewDAO.insert(review);
			updateTheatreAverageRating(review.getTheatreId());
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
			int theatreId = reviewDAO.getOne(reviewId).getTheatreId();
			reviewDAO.delete(reviewId);
			updateTheatreAverageRating(theatreId);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateTheatreAverageRating(int theatreId) {
	    try {
	        float averageRating = reviewDAO.getAVGRating(theatreId);
	        theatreDAO.updateRating(theatreId, averageRating);
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
}

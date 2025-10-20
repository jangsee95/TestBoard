package review.dto;

import java.time.LocalDateTime;

import theatre.dto.TheatreDTO;
import user.dto.UserDTO;

public class ReviewDTO {

	private int reviewId;
	private int theatreId;
	private UserDTO author;
	private String title;
	private String content;
	private float rating;
	private LocalDateTime createdAt;
	
	public ReviewDTO() {
	}

	public ReviewDTO(int reviewId, int theatreId, UserDTO author, String title, String content, float rating,
			LocalDateTime createdAt) {
		this.reviewId = reviewId;
		this.theatreId = theatreId;
		this.author = author;
		this.title = title;
		this.content = content;
		this.rating = rating;
		this.createdAt = createdAt;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getTheatreId() {
		return theatreId;
	}

	public void setTheatreId(int theatreId) {
		this.theatreId = theatreId;
	}

	public UserDTO getAuthor() {
		return author;
	}

	public void setAuthor(UserDTO author) {
		this.author = author;
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

	public float getRating() {
		return rating;
	}


	public void setRating(float rating) {
		this.rating = rating;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "ReviewDTO [reviewId=" + reviewId + ", theatreId=" + theatreId + ", author=" + author + ", title="
				+ title + ", content=" + content + ", rating=" + rating + ", createdAt=" + createdAt + "]";
	}

}

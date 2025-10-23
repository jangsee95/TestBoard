package theatre.dto;

import java.time.LocalDateTime;

public class TheatreDTO {
	private int theatreId;
	private String title;
	private String genre;
	private String content;
	private int playTime;
	private float rating;
	private String posterUrl;
	private LocalDateTime performanceDateTime;
	private LocalDateTime createdAt;
	
	
	 public TheatreDTO() {
	}

	 public TheatreDTO(int theatreId, String title, String genre, String content, int playTime, float rating,
			String posterUrl, LocalDateTime performanceDateTime, LocalDateTime createdAt) {
		this.theatreId = theatreId;
		this.title = title;
		this.genre = genre;
		this.content = content;
		this.playTime = playTime;
		this.rating = rating;
		this.posterUrl = posterUrl;
		this.performanceDateTime = performanceDateTime;
		this.createdAt = createdAt;
	}


	 public int getTheatreId() {
		 return theatreId;
	 }


	 public String getContent() {
		return content;
	}


	 public void setContent(String content) {
		 this.content = content;
	 }


	 public void setTheatreId(int theatreId) {
		 this.theatreId = theatreId;
	 }


	 public String getTitle() {
		 return title;
	 }


	 public void setTitle(String title) {
		 this.title = title;
	 }


	 public String getGenre() {
		 return genre;
	 }


	 public void setGenre(String genre) {
		 this.genre = genre;
	 }


	 public int getPlayTime() {
		 return playTime;
	 }


	 public void setPlayTime(int playTime) {
		 this.playTime = playTime;
	 }
	 
	 public float getRating() {
		return rating;
	}

	 public void setRating(float rating) {
		 this.rating = rating;
	 }

	 public String getPosterUrl() {
		 return posterUrl;
	 }


	 public void setPosterUrl(String posterUrl) {
		 this.posterUrl = posterUrl;
	 }


	 public LocalDateTime getPerformanceDateTime() {
		 return performanceDateTime;
	 }


	 public void setPerformanceDateTime(LocalDateTime performanceDateTime) {
		 this.performanceDateTime = performanceDateTime;
	 }


	 public LocalDateTime getCreatedAt() {
		 return createdAt;
	 }


	 public void setCreatedAt(LocalDateTime createdAt) {
		 this.createdAt = createdAt;
	 }

	 @Override
	 public String toString() {
		return "TheatreDTO [theatreId=" + theatreId + ", title=" + title + ", genre=" + genre + ", content=" + content
				+ ", playTime=" + playTime + ", rating=" + rating + ", posterUrl=" + posterUrl
				+ ", performanceDateTime=" + performanceDateTime + ", createdAt=" + createdAt + "]";
	 }
	 
}

package theatre.dto;

import java.time.LocalDateTime;

public class TheatreDTO {
	private int theatreId;
	private String title;
	private String genre;
	private int playTime;
	private String posterUri;
	private LocalDateTime performanceDateTime;
	private LocalDateTime createdAt;
	
	
	 public TheatreDTO() {
	}


	 public TheatreDTO(int theatreId, String title, String genre, int playTime, String posterUrl,
			LocalDateTime performanceDateTime, LocalDateTime createdAt) {
		super();
		this.theatreId = theatreId;
		this.title = title;
		this.genre = genre;
		this.playTime = playTime;
		this.posterUri = posterUrl;
		this.performanceDateTime = performanceDateTime;
		this.createdAt = createdAt;
	 }


	 public int getTheatreId() {
		 return theatreId;
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


	 public String getPosterUri() {
		 return posterUri;
	 }


	 public void setPosterUri(String posterUrl) {
		 this.posterUri = posterUrl;
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
		return "TheatreDTO [theatreId=" + theatreId + ", title=" + title + ", genre=" + genre + ", playTime=" + playTime
				+ ", posterUrl=" + posterUri + ", performanceDateTime=" + performanceDateTime + ", createdAt="
				+ createdAt + "]";
	 }

	 
	 
	 
}

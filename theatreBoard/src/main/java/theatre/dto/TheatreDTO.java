package theatre.dto;

public class TheatreDTO {
	private int theatreId;
	private String title;
	private String genre;
	private int playTime;
	private String posterUrl;
	
	 public TheatreDTO() {
	}

	 public TheatreDTO(int theatreId, String title, String genre, int playTime, String posterUrl) {
		this.theatreId = theatreId;
		this.title = title;
		this.genre = genre;
		this.playTime = playTime;
		this.posterUrl = posterUrl;
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

	 public String getPosterUrl() {
		 return posterUrl;
	 }

	 public void setPosterUrl(String posterUrl) {
		 this.posterUrl = posterUrl;
	 }

	 @Override
	 public String toString() {
		return "TheatreDTO [theatreId=" + theatreId + ", title=" + title + ", genre=" + genre + ", playTime=" + playTime
				+ ", posterUrl=" + posterUrl + "]";
	 }
	 
}

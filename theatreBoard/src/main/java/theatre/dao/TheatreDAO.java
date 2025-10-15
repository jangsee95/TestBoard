package theatre.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.util.DBUtil;
import theatre.dto.TheatreDTO;

public class TheatreDAO {

	private DBUtil util = DBUtil.getInstance();

	private static TheatreDAO instance = new TheatreDAO();

	private TheatreDAO() {
	}

	public static TheatreDAO getInstance() {
		return instance;
	}

	public boolean insert(TheatreDTO theatre) throws SQLException {
		String sql = "INSERT INTO theatres (title, genre, content, playtime, poster_url, performance_datetime)"
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = util.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, theatre.getTitle());
			pstmt.setString(2, theatre.getGenre());
			pstmt.setString(3, theatre.getContent());
			pstmt.setInt(4, theatre.getPlayTime());
			pstmt.setString(5, theatre.getPosterUrl());
			pstmt.setObject(6, theatre.getPerformanceDateTime());

			return pstmt.executeUpdate() == 1;
		}
	}

	public List<TheatreDTO> selectAll() throws SQLException {
		List<TheatreDTO> theatres = new ArrayList<>();
		String sql = "SELECT * FROM theatres";

		try (Connection conn = util.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {
			while (rs.next()) {
				TheatreDTO theatre = new TheatreDTO();
				theatre.setTheatreId(rs.getInt("theatre_id"));
				theatre.setTitle(rs.getString("title"));
				theatre.setGenre(rs.getString("genre"));
				theatre.setContent(rs.getString("content"));
				theatre.setPlayTime(rs.getInt("playtime"));
				theatre.setPosterUrl(rs.getString("poster_url"));
				theatre.setPerformanceDateTime(rs.getTimestamp("performance_datetime").toLocalDateTime());
				theatre.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
				theatres.add(theatre);
			}
		}
		return theatres;

	}

	public TheatreDTO selectOne(int theatreId) throws SQLException {
		String sql = "SELECT * FROM theatres WHERE theatre_id = ?";
		try (Connection conn = util.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, theatreId);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					TheatreDTO theatre = new TheatreDTO();
					theatre.setTheatreId(rs.getInt("theatre_id"));
					theatre.setTitle(rs.getString("title"));
					theatre.setGenre(rs.getString("genre"));
					theatre.setContent(rs.getString("content"));
					theatre.setPlayTime(rs.getInt("playtime"));
					theatre.setPosterUrl(rs.getString("poster_url"));
					theatre.setPerformanceDateTime(rs.getTimestamp("performance_datetime").toLocalDateTime());
					theatre.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
					return theatre;
				}
			}
		}
		return null;
	}

	public boolean update(TheatreDTO theatre) throws SQLException {
		String sql = "UPDATE theatres SET title = ?, genre = ?, content = ?, playtime = ?, poster_url = ?, performance_datetime = ? WHERE theatre_id = ?";
		try (Connection conn = util.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, theatre.getTitle());
			pstmt.setString(2, theatre.getGenre());
			pstmt.setString(3, theatre.getContent());
			pstmt.setInt(4, theatre.getPlayTime());
			pstmt.setString(5, theatre.getPosterUrl());
			pstmt.setObject(6, theatre.getPerformanceDateTime());
			pstmt.setInt(7, theatre.getTheatreId());

			return pstmt.executeUpdate() == 1;
		}
	}

	public boolean delete(int theatreId) throws SQLException {
		String sql = "DELETE FROM theatres WHERE theatre_id = ?";

		try (Connection conn = util.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, theatreId);

			return pstmt.executeUpdate() == 1;
		}
	}
}

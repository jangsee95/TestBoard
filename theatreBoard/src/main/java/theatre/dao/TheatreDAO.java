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
		String sql = "INSERT INTO theatres (title, genre, playtime, poster_uri, performance_datetime)"
				+ "VALUES (? , ? ,? ,? , ?)";

		try (Connection conn = util.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, theatre.getTitle());
			pstmt.setString(2, theatre.getGenre());
			pstmt.setInt(3, theatre.getPlayTime());
			pstmt.setString(4, theatre.getPosterUri());
			pstmt.setObject(5, theatre.getPerformanceDateTime());

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
				theatre.setPlayTime(rs.getInt("playtime"));
				theatre.setPosterUri(rs.getString("poster_uri"));
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
					theatre.setPlayTime(rs.getInt("playtime"));
					theatre.setPosterUri(rs.getString("poster_uri"));
					theatre.setPerformanceDateTime(rs.getTimestamp("performance_datetime").toLocalDateTime());
					theatre.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
					return theatre;
				}
			}
		}
		return null;
	}

	public boolean update(TheatreDTO theatre) throws SQLException {
		String sql = "UPDATE theatres SET title = ?, genre = ?, playtime = ?, poster_uri = ?, performance_datetime = ? WHERE theatre_id = ?";
		try (Connection conn = util.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, theatre.getTitle());
			pstmt.setString(2, theatre.getGenre());
			pstmt.setInt(3, theatre.getPlayTime());
			pstmt.setString(4, theatre.getPosterUri());
			pstmt.setObject(5, theatre.getPerformanceDateTime());
			pstmt.setInt(6, theatre.getTheatreId());

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

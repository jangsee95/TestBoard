package theatre.service;

import java.sql.SQLException;
import java.util.List;

import theatre.dao.TheatreDAO;
import theatre.dto.TheatreDTO;

public class TheatreService {
	private TheatreDAO theatreDAO = TheatreDAO.getInstance();

	private static TheatreService instance = new TheatreService();

	private TheatreService() {
	}

	public static TheatreService getInstance() {
		return instance;
	}

	public List<TheatreDTO> getList() {
		try {
			return theatreDAO.selectAll();
		} catch (SQLException e) {
			throw new RuntimeException("Theatre 목록을 가져오는 중 DB 오류 발생", e);
		}
	}

	public TheatreDTO getTheatre(int theatreId) {
		try {
			return theatreDAO.selectOne(theatreId); 
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean writeTheatre(TheatreDTO theatre) {
		try {
			return theatreDAO.insert(theatre);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateTheatre(TheatreDTO theatre) {
		try {
			return theatreDAO.update(theatre);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteTheatre(int theatreId) {
		try {
			return theatreDAO.delete(theatreId);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}

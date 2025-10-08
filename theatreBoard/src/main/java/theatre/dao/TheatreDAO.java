package theatre.dao;

import common.util.DBUtil;

public class TheatreDAO {
	
	private DBUtil util = DBUtil.getInstance();
	
	private static TheatreDAO instance = new TheatreDAO();
	
	private TheatreDAO() {}
	
	public static TheatreDAO getInstance() {
		return instance;
	}
	
	
}

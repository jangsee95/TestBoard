package user.service;

import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import user.dao.UserDAO;
import user.dto.UserAuthDTO;
import user.dto.UserDTO;

public class UserService {
	private UserDAO userDAO = UserDAO.getInstance();

	private static UserService instance = new UserService();

	private UserService() {
	}

	public static UserService getInstance() {
		return instance;
	}

	public UserDTO login(String id, String rawPassword) {
		UserDTO loginUser = null;
		try {
			UserAuthDTO authUser = userDAO.selectOneForAuth(id);

			if (authUser != null) {
				String hashedPasswordFromDB = authUser.getHashedPassword();

				if (BCrypt.checkpw(rawPassword, hashedPasswordFromDB)) {
					loginUser = userDAO.selectOne(id);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loginUser;
	}
	
	public boolean join (UserDTO user, String rawPassword) {
		try {
			UserDTO existingUser = userDAO.selectOne(user.getUserId());
			if (existingUser != null) {
				System.out.println("이미 존재하는 아이디입니다.");
				return false;
			}
			
			String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
			
			return userDAO.insert(user, hashedPassword);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateUser(UserDTO user) {
		try {
			return userDAO.update(user);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean changePassword(String userId, String currentRawPassword, String newRawPassword) {
		try {
			UserAuthDTO authUser = userDAO.selectOneForAuth(userId);
			if (authUser != null && BCrypt.checkpw(currentRawPassword, authUser.getHashedPassword())) {
				String newHashedPassword = BCrypt.hashpw(newRawPassword, BCrypt.gensalt());
				UserDTO user = userDAO.selectOne(userId);
				return userDAO.update(user, newHashedPassword);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

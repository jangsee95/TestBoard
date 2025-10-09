package board.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import board.dao.BoardDAO;
import board.dto.BoardDTO;
import user.dto.UserDTO;

public class BoardService {
	private static BoardService instance = new BoardService();
	private BoardDAO boardDAO = BoardDAO.getInstance();

	private BoardService() {}

	public static BoardService getInstance() {
		return instance;
	}

	public List<BoardDTO> getBoardList() {
		try {
			return boardDAO.selectAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public BoardDTO getBoard(int boardId) {
		try {
			return boardDAO.selectOne(boardId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean writeBoard(BoardDTO board) {
		try {
			return boardDAO.insert(board);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateBoard(BoardDTO board) {
		try {
			return boardDAO.update(board);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteBoard(int boardId) {
		try {
			return boardDAO.delete(boardId);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
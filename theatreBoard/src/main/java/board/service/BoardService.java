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
//		// === Start of Temporary Debugging Code ===
//		List<BoardDTO> dummyList = new ArrayList<>();
//
//		// Create a dummy author
//		UserDTO dummyAuthor = new UserDTO();
//		dummyAuthor.setUserName("테스터");
//
//		// Create a dummy board post
//		BoardDTO dummyBoard = new BoardDTO();
//		dummyBoard.setBoardId(999);
//		dummyBoard.setTitle("이것은 가짜 테스트 게시물입니다.");
//		dummyBoard.setAuthor(dummyAuthor);
//
//		dummyList.add(dummyBoard);
//		return dummyList;
//		// === End of Temporary Debugging Code ===

		
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
}
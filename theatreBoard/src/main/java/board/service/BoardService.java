package board.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import board.dao.BoardDAO;
import board.dto.BoardDTO;

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
	
	public Map<String, Object> getBoardPage(int pageNum, int pageSize) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Integer> pageInfo = new HashMap<>();

        try {
            // 1. 전체 게시글 수 조회
            int totalCount = boardDAO.getTotalCount();

            // 2. 페이징 계산
            int offset = (pageNum - 1) * pageSize;
            int totalPages = (int) Math.ceil((double) totalCount / pageSize);
            if (totalPages == 0) totalPages = 1; // 게시글이 없어도 1페이지는 표시

            // 3. 현재 페이지의 게시글 목록 조회
            List<BoardDTO> boardList = boardDAO.selectPage(offset, pageSize);

            // 4. 페이지 정보 맵에 저장
            pageInfo.put("currentPage", pageNum);
            pageInfo.put("pageSize", pageSize);
            pageInfo.put("totalCount", totalCount);
            pageInfo.put("totalPages", totalPages);

            // 5. 결과 맵에 저장
            result.put("boardList", boardList);
            result.put("pageInfo", pageInfo);

        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 발생 시 빈 목록과 기본 페이지 정보 반환 또는 예외 throw
            result.put("boardList", Collections.emptyList());
            pageInfo.put("currentPage", 1);
            pageInfo.put("pageSize", pageSize);
            pageInfo.put("totalCount", 0);
            pageInfo.put("totalPages", 1);
            result.put("pageInfo", pageInfo);
        }
        return result;
    }
}
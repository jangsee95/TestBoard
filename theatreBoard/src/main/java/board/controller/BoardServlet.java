package board.controller;

import java.io.IOException;
import java.util.List;

import board.dto.BoardDTO;
import board.service.BoardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import user.dto.UserDTO;

/**
 * Servlet implementation class BoardServlet
 */
@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BoardService boardService = BoardService.getInstance();
       
	@Override
		protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String act = req.getParameter("act");
			
			switch (act) {
			case "list" :
				doList(req, resp);
				break;
			case "view" :
				doView(req, resp);
				break;
			case "writeForm" :
				doWriteForm(req, resp);
				break;
			case "write" :
				doWrite(req, resp);
				break;
			case "updateForm" :
				doUpdateForm(req, resp);
				break;
			case "update" :
				doUpdate(req, resp);
				break;
			case "remove" :
				doRemove(req, resp);
				break;
			}
		}
	
	private void doRemove(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int boardId = Integer.parseInt(req.getParameter("boardId"));
		String msg = "게시글이 삭제되었습니다.";
		if (boardService.deleteBoard(boardId)) {
			req.getSession().setAttribute("msg", msg);
		} else {
			msg = "삭제에 실패했습니다.";
			req.getSession().setAttribute("msg", msg);
		}
		
		
		resp.sendRedirect(req.getContextPath() + "/board?act=list");
	}

	private void doUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int boardId = Integer.parseInt(	req.getParameter("boardId"));
		
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		BoardDTO board = new BoardDTO();
		board.setBoardId(boardId);
		board.setTitle(title);
		board.setContent(content);
		if (boardService.updateBoard(board)) {
			req.getSession().setAttribute("msg", "수정이 완료 되었습니다.");
			resp.sendRedirect(req.getContextPath() + "/board?act=view&boardId=" + boardId);
		} else {
			req.getSession().setAttribute("msg", "수정에 실패했습니다.");
			resp.sendRedirect(req.getContextPath() + "/board?act=updateForm&boardId=" + boardId);
		}
	}

	private void doUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int boardId = Integer.parseInt(req.getParameter("boardId"));
		BoardDTO board = boardService.getBoard(boardId);
		req.setAttribute("board", board);
		req.getRequestDispatcher("/WEB-INF/view/board/updateForm.jsp").forward(req, resp);
	}

	private void doWrite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		
		if (session == null || session.getAttribute("loginUser") == null) {
			req.getSession().setAttribute("msg", "로그인이 필요한 서비스입니다.");
			resp.sendRedirect(req.getContextPath() + "/user?act=loginForm");
			return;
		}
		
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		BoardDTO board = new BoardDTO();
		board.setTitle(title);
		board.setContent(content);
		board.setAuthor(loginUser);
		if (boardService.writeBoard(board)) {
			req.getSession().setAttribute("msg", "등록에 성공했습니다.");
			resp.sendRedirect(req.getContextPath() + "/board?act=list");
		} else {
			req.getSession().setAttribute("msg", "등록에 실패했습니다.");
			resp.sendRedirect(req.getContextPath() + "/board?act=writeForm");
		}
	}

	private void doWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		
		if (session == null || session.getAttribute("loginUser") == null) {
			req.getSession().setAttribute("msg", "로그인이 필요한 서비스입니다.");
			resp.sendRedirect(req.getContextPath() + "/user?act=loginForm");
			return;
		}
		
		req.getRequestDispatcher("/WEB-INF/view/board/writeForm.jsp").forward(req, resp);
	}

	private void doView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int boardId = Integer.parseInt(req.getParameter("boardId"));
		
		BoardDTO board = boardService.getBoard(boardId);
		
		req.setAttribute("board", board);
		
		req.getRequestDispatcher("/WEB-INF/view/board/view.jsp").forward(req, resp);
	}

	private void doList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BoardDTO> boardList = boardService.getBoardList();
		
		req.setAttribute("boardList", boardList);
		
		req.getRequestDispatcher("/WEB-INF/view/board/list.jsp").forward(req, resp);
	}
   
}

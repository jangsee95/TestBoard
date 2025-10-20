 package comment.controller;

import java.io.IOException;

import comment.dto.CommentDTO;
import comment.service.CommentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import user.dto.UserDTO;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CommentService commentService = CommentService.getInstance();
       
	@Override
		protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String act = req.getParameter("act");
			
			switch (act) {
			case "add" :
				doAdd(req, resp);
				break;
			case "remove":
				doRemove(req, resp);
				break;
			case "update":
				doUpdate(req, resp);
				break;
			}
		}

	private void doRemove(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		
		int commentId = Integer.parseInt(req.getParameter("comment_id"));
		int boardId = Integer.parseInt(req.getParameter("board_id"));
		CommentDTO comment = commentService.getOne(commentId);
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		String msg = "";
		
		if (loginUser == null || !loginUser.getUserId().equals(comment.getAuthor().getUserId())) {
			msg = "잘못된 접근입니다.";
			session.setAttribute("msg", msg);
			return;
		}
		
		if (commentService.deleteComment(commentId)) {
			msg = "삭제 되었습니다.";
			session.setAttribute("msg", msg);
		}
		
		resp.sendRedirect(req.getContextPath() + "/board?act=view&boardId=" + boardId);
	}

	private void doAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
			req.getSession().setAttribute("msg", "로그인이 필요한 서비스입니다.");
			resp.sendRedirect(req.getContextPath() + "/user?act=loginForm");
			return;
		}
		
		String formToken = req.getParameter("commentToken");
		String sessionToken = (String) session.getAttribute("commentFormToken");
		
		int boardId = Integer.parseInt(req.getParameter("board_id"));
		
		if (session != null) {
			session.removeAttribute("commentFormToken");
		}
		
		if (sessionToken == null ||formToken == null || !sessionToken.equals(formToken)) {
			req.getSession().setAttribute("msg", "비상적인 요청이거나 이미 처리된 요청입니다.");
			resp.sendRedirect(req.getContextPath() + "/board?act=view&boardId=" + boardId);
			return;
		}
		
		
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		String content = req.getParameter("content");
		
		CommentDTO comment = new CommentDTO();
		comment.setAuthor(loginUser);
		comment.setContent(content);
		comment.setBoardId(boardId);
		
		
		if (!commentService.writeComment(comment)) {
			req.getSession().setAttribute("msg", "등록에 실패했습니다.");
		}
		resp.sendRedirect(req.getContextPath() + "/board?act=view&boardId=" + boardId);
		
	}
	
	private void doUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    HttpSession session = req.getSession(false);
	    UserDTO loginUser = (session != null) ? (UserDTO) session.getAttribute("loginUser") : null;

	    if (loginUser == null) {
	        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
	        return;
	    }

	    int commentId = Integer.parseInt(req.getParameter("comment_id"));
	    String content = req.getParameter("content");

	    CommentDTO comment = commentService.getOne(commentId);

	    // 권한 확인: 댓글이 존재하고, 로그인한 사용자가 작성자인지 확인
	    if (comment == null || !comment.getAuthor().getUserId().equals(loginUser.getUserId())) {
	        resp.sendError(HttpServletResponse.SC_FORBIDDEN, "수정 권한이 없습니다.");
	        return;
	    }

	    comment.setContent(content);
	    boolean isSuccess = commentService.updateComment(comment);

	    if (!isSuccess) {
	        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "댓글 수정에 실패했습니다.");
	    }
	    // 성공 시에는 별도의 응답 본문 없이 200 OK 상태 코드만 보냅니다.
	}
	
}

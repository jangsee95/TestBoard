 package comment.servlet;

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
			}
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
		
		
		if (commentService.writeComment(comment)) {
			req.getSession().setAttribute("msg", "등록에 성공했습니다.");
		} else {
			req.getSession().setAttribute("msg", "등록에 실패했습니다.");
		}
		resp.sendRedirect(req.getContextPath() + "/board?act=view&boardId=" + boardId);
		
	}
}

package theatre.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import theatre.dto.TheatreDTO;
import theatre.service.TheatreService;

/**
 * Servlet implementation class TheatreListServlet
 */
@WebServlet("/theatre")
public class TheatreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TheatreService theatreService = TheatreService.getInstance();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String act = req.getParameter("act");

		switch (act) {
		case "list":
			doList(req, resp);
			break;
		case "writeForm":
			doWriteForm(req, resp);
			break;
		case "write":
			doWrite(req, resp);
			break;
		}
	}
	
	private void doWrite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    // --- 아래 로그인 체크 블록을 맨 위에 추가 ---
	    HttpSession session = req.getSession(false);

	    // 1. 로그인 상태인지 먼저 확인합니다.
	    if (session == null || session.getAttribute("loginUser") == null) {
	        // 로그인 되어 있지 않으면, 더 이상 진행하지 않고 로그인 페이지로 보냅니다.
	        // 이때 req.getSession()은 새로운 세션을 만들어 메시지를 담습니다.
	        req.getSession().setAttribute("msg", "로그인이 필요한 서비스입니다.");
	        resp.sendRedirect(req.getContextPath() + "/user?act=loginForm");
	        return; // 메서드 즉시 종료
	    }
	    // --- 추가 끝 ---

	    // 이제 이 아래 코드부터는 'session'이 절대 null이 아니라고 보장할 수 있습니다.
	    
	    String formToken = req.getParameter("token");
	    String sessionToken = (String) session.getAttribute("formToken");

	    session.removeAttribute("formToken");

	    if (sessionToken == null || formToken == null || !sessionToken.equals(formToken)) {
	        req.getSession().setAttribute("msg", "비정상적인 요청이거나 이미 처리된 요청입니다.");
	        resp.sendRedirect(req.getContextPath() + "/theatre?act=list");
	        return;
	    }
	    
	    // --- 기존 폼 데이터 처리 로직 (이하 동일) ---
	    String title = req.getParameter("title");
	    String genre = req.getParameter("genre");
	    String content = req.getParameter("content");
	    int playTime = Integer.parseInt(req.getParameter("playTime"));
	    String posterUrl = req.getParameter("poster_url");
	    String performanceDateTimeStr = req.getParameter("performanceDateTime");
	    
	    LocalDateTime performanceDateTime = LocalDateTime.parse(performanceDateTimeStr);

	    TheatreDTO theatre = new TheatreDTO();
	    theatre.setTitle(title);
	    theatre.setGenre(genre);
	    theatre.setContent(content);
	    theatre.setPlayTime(playTime);
	    theatre.setPosterUrl(posterUrl);
	    theatre.setPerformanceDateTime(performanceDateTime);
	    
	    if (theatreService.writeTheatre(theatre)) {
	        req.getSession().setAttribute("msg", "공연 정보가 등록되었습니다.");
	        resp.sendRedirect(req.getContextPath() + "/theatre?act=list");
	    } else {
	        req.getSession().setAttribute("msg", "등록에 실패했습니다.");
	        resp.sendRedirect(req.getContextPath() + "/theatre?act=writeForm");
	    }
	}
	private void doWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		
		if (session == null || session.getAttribute("loginUser") == null) {
			req.getSession().setAttribute("msg", "로그인이 필요한 서비스입니다.");
			resp.sendRedirect(req.getContextPath() + "/user?act=loginForm");
			return;
		}
		
		String token = UUID.randomUUID().toString();
		session.setAttribute("formToken", token);
		req.setAttribute("token", token);
		
		
		req.getRequestDispatcher("/WEB-INF/view/theatre/writeForm.jsp").forward(req, resp);
	}

	private void doList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<TheatreDTO> theatreList = theatreService.getList();
		
		req.setAttribute("theatreList", theatreList);
		
		req.getRequestDispatcher("WEB-INF/view/theatre/list.jsp").forward(req, resp);
	}
}

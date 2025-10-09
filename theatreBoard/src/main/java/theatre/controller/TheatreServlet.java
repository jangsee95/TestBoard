package theatre.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
		}
	}

	private void doList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<TheatreDTO> theatreList = theatreService.getList();
		
		req.setAttribute("theatreList", theatreList);
		
		req.getRequestDispatcher("WEB-INF/view/theatre/list.jsp").forward(req, resp);
	}

}

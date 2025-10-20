package review.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import review.dto.ReviewDTO;
import review.service.ReviewService;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ReviewService reviewService = ReviewService.getInstance();
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String act = req.getParameter("act");
		
		switch (act) {
		case "add" :
			doAdd(req,resp);
			break;
		case "remove" :
			doRemove(req, resp);
			break;
		case "update" :
			doUpdate(req,resp);
			break;
		}
	}

	private void doUpdate(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
		
	}

	private void doRemove(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void doAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession(false);
		String msg;
		
		if (session == null || session.getAttribute("loginUser") == null) {
			msg = "로그인이 필요한 서비스입니다.";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect(req.getContextPath() + "/user?act=loginForm");
			return;
		}
		
		int theatreId = Integer.parseInt(req.getParameter("theatre_id"));
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		float rating = Float.parseFloat(req.getParameter("rating"));
		
		ReviewDTO review = new ReviewDTO();
		review.setTheatreId(theatreId);
		review.setTitle(title);
		review.setContent(content);
		review.setRating(rating);
		
		if (reviewService.writeReview(review)) {
			msg = "리뷰가 성공적으로 작성되었습니다.";
			req.getSession().setAttribute("msg", msg);
		} else {
			msg = "리뷰 작성에 실패 했습니다.";
			req.getSession().setAttribute("msg", msg);
		}
		
	}

}

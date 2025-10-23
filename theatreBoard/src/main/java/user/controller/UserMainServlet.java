package user.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import user.dto.UserDTO;
import user.service.UserService;

@WebServlet("/user")
public class UserMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService = UserService.getInstance();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String act = req.getParameter("act");

		if (act == null) {
			resp.sendRedirect(req.getContextPath());
			return;
		}

		switch (act) {
		case "join":
			doJoin(req, resp);
			break;
		case "joinForm":
			doShowJoinForm(req, resp);
			break;
		case "login" :
			doLogin(req, resp);
			break;
		case "loginForm":
			doLoginForm(req, resp);
			break;
		case "myPage":
			doMyPage(req, resp);
			break;
		case "logout":
			doLogout(req, resp);
			break;
		case "editProfileForm":
			doEditProfileForm(req, resp);
			break;
		case "updateUser":
			doUpdateUser(req, resp);
			break;
		case "changePasswordForm":
			doChangePasswordForm(req, resp);
			break;
		case "changePassword":
			doChangePassword(req, resp);
			break;
		case "deleteUser":
			doDeleteUser(req,resp);
			break;
		}

	}

	private void doDeleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String userId = req.getParameter("userId");
		String msg = "회원 탈퇴 되었습니다.";
		HttpSession session = req.getSession();
		
		if (userService.deleteUser(userId)) {
			session.invalidate();
			
			req.getSession().setAttribute("msg", msg);
			
		} else {
			msg = "회원탈퇴에 실패했습니다.";
			req.getSession().setAttribute("msg", msg);
		}
		
		resp.sendRedirect(req.getContextPath() + "/index.jsp");
	}

	private void doChangePassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String currentPassword = req.getParameter("currentPassword");
		String newPassword = req.getParameter("newPassword");
		String confirmNewPassword = req.getParameter("confirmNewPassword");
		
		HttpSession session = req.getSession();
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		
		if (!newPassword.equals(confirmNewPassword)) {
			session.setAttribute("msg", "새 비밀번호가 일치하지 않습니다.");
			resp.sendRedirect(req.getContextPath() + "/user?act=changePasswordForm");
			return;
		}
		
		boolean isSuccess = userService.changePassword(loginUser.getUserId(), currentPassword, newPassword);
		
		if (isSuccess) {
			session.setAttribute("msg", "비밀번호가 성공적으로 변경되었습니다.");
			resp.sendRedirect(req.getContextPath() + "/user?act=myPage");
		} else {
			session.setAttribute("msg", "현재 비밀번호가 올바르지 않습니다.");
			resp.sendRedirect(req.getContextPath() + "/user?act=changePasswordForm");
		}
	}

	private void doChangePasswordForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String msg = (String) session.getAttribute("msg");
		if (msg != null) {
			req.setAttribute("msg", msg);
			session.removeAttribute("msg");
		}
		req.getRequestDispatcher("/WEB-INF/view/user/changePasswordForm.jsp").forward(req, resp);
	}

	private void doUpdateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String userId = req.getParameter("userId");
		String userName = req.getParameter("userName");
		String email = req.getParameter("email");
		
		UserDTO user = new UserDTO(userId, userName, email);
		
		boolean isSuccess = userService.updateUser(user);
		
		if(isSuccess) {
			req.getSession().setAttribute("loginUser", user);
			resp.sendRedirect(req.getContextPath() + "/user?act=myPage");
		} else {
			req.getSession().setAttribute("msg", "회원정보 수정에 실패했습니다.");
			resp.sendRedirect(req.getContextPath() + "/user?act=editProfileForm");
		}
	}

	private void doEditProfileForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String msg = (String) session.getAttribute("msg");
		if (msg != null) {
			req.setAttribute("msg", msg);
			session.removeAttribute("msg");
		}
		req.getRequestDispatcher("/WEB-INF/view/user/editProfileForm.jsp").forward(req, resp);
	}

	private void doLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.getSession().invalidate();
		resp.sendRedirect(req.getContextPath() + "/index.jsp");
	}

	private void doMyPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String msg = (String) session.getAttribute("msg");
		if (msg != null) {
			req.setAttribute("msg", msg);
			session.removeAttribute("msg");
		}
		req.getRequestDispatcher("/WEB-INF/view/user/myPage.jsp").forward(req, resp);
	}

	private void doLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String userId = req.getParameter("userId");
		String rawPassword = req.getParameter("password");
		
		HttpSession session = req.getSession();
		
		UserDTO user = userService.login(userId, rawPassword);
		if (user != null) {
			session.setAttribute("loginUser", user);
			resp.sendRedirect(req.getContextPath() + "/index.jsp");
		} else {
			session.setAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
			resp.sendRedirect(req.getContextPath() + "/user?act=loginForm");
		}
		
	}

	private void doLoginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String msg = (String) session.getAttribute("msg");
		if (msg != null) {
			req.setAttribute("msg", msg);
			session.removeAttribute("msg");
		}
		req.getRequestDispatcher("/WEB-INF/view/user/loginForm.jsp").forward(req, resp);
	}

	private void doShowJoinForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String msg = (String) session.getAttribute("msg");
		if (msg != null) {
			req.setAttribute("msg", msg);
			session.removeAttribute("msg");
		}
		req.getRequestDispatcher("/WEB-INF/view/user/joinForm.jsp").forward(req, resp);
	}

	private void doJoin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String userId = req.getParameter("userId");
		String rawPassword = req.getParameter("password");
		String userName = req.getParameter("userName");
		String email = req.getParameter("email");

		UserDTO user = new UserDTO(userId, userName, email);

		boolean isSuccess = userService.join(user, rawPassword);

		if (isSuccess) {
			req.getSession().setAttribute("msg", "회원가입에 성공했습니다. 로그인 해주세요.");
			resp.sendRedirect(req.getContextPath() + "/user?act=loginForm");
		} else {
			req.getSession().setAttribute("msg", "회원가입에 실패했습니다. 아이디가 중복되었거나 입력값을 확인해주세요.");
			resp.sendRedirect(req.getContextPath() + "/user?act=joinForm");
		}
	}

}
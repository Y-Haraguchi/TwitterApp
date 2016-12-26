package twitterapp.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitterapp.beans.User;
import twitterapp.service.UserService;

@WebServlet(urlPatterns = {"/settings"})
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");

		if(session.getAttribute("editUser") == null) {
			User editUser = new UserService().getUser(loginUser.getId());
			session.setAttribute("editUser", editUser);
		}
		request.getRequestDispatcher("setting.jsp").forward(request, response);

	}

}

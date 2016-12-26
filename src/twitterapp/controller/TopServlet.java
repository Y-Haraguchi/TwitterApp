package twitterapp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitterapp.beans.User;
import twitterapp.beans.UserMessage;
import twitterapp.service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		//Userオブジェクトにセッションで取得したログインユーザー情報を取得
		User user = (User)request.getSession().getAttribute("loginUser");
		boolean isShowMessageForm;
		if(user != null) {
			isShowMessageForm = true;
		} else {
			isShowMessageForm = false;
		}

		List<UserMessage> messages = new MessageService().getMessage(user.getId());

		request.setAttribute("messages", messages);
		request.setAttribute("isShowMessageForm", isShowMessageForm);

		// リクエストパラメーターを受け取り、jsp.topに渡す
		request.getRequestDispatcher("/top.jsp").forward(request, response);

	}

}

package twitterapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import twitterapp.beans.User;
import twitterapp.service.UserService;

@WebServlet(urlPatterns = {"/signup"})
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		//リクエストパラメーターを受け取り、signup.jspに渡す
		request.getRequestDispatcher("signup.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		//メッセージを保持するリストの生成
		List<String> messages = new ArrayList<String>();

		//
		HttpSession session = request.getSession();
		if(isValid(request, messages) != true) {
			/*入力値に対してバリデーションを行い、入力値が不正の場合は
			*エラーメッセージを出して、signup画面を再度表示
			*/
			session.setAttribute("erroMessages", messages);
			response.sendRedirect("signup");
		} else {
			//入力値が正常の場合はユーザー登録を行う
			//Userクラスのインスタンス生成
			User user = new User();
			user.setName(request.getParameter("name"));
			user.setAccount(request.getParameter("account"));
			user.setPassword(request.getParameter("password"));
			user.setEmail(request.getParameter("email"));
			user.setDescription(request.getParameter("description"));

			new UserService().register(user);

			response.sendRedirect("./");
		}
	}

	//isValidクラス
	private boolean isValid(HttpServletRequest request, List<String> message) {
		String account = request.getParameter("account");
		String password = request.getParameter("password");

		if(StringUtils.isEmpty(account) == true) {
			message.add("アカウントを入力してください");
		}
		if(StringUtils.isEmpty(password) == true) {
			message.add("パスワードを入力してください");
		}


		//TODOアカウントがすでに利用されてないか、メールアドレスがすでに登録されてないかなどの確認も必要
		if(message.size() != 0) {
			return false;
		} else {
			return true;
		}
	}

}

package twitterapp.service;

import static twitterapp.utils.CloseableUtil.*;
import static twitterapp.utils.DBUtil.*;

import java.sql.Connection;

import twitterapp.beans.User;
import twitterapp.dao.UserDao;
import twitterapp.utils.CipherUtil;

public class LoginService {

	public User login(String accountOrEmail, String password) {

		Connection connection = null;
		try {
			connection = getConnection();

			//パスワードの暗号化
			UserDao userDao = new UserDao();
			String encPassword = CipherUtil.encrypt(password);
			User user = userDao.getUser(connection, accountOrEmail, encPassword);

			commit(connection);
			return user;
		} catch(RuntimeException e) {
			rollback(connection);
			throw e;
		} catch(Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}


	}

}

package twitterapp.service;

import static twitterapp.utils.CloseableUtil.*;
import static twitterapp.utils.DBUtil.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;

import twitterapp.beans.User;
import twitterapp.dao.UserDao;
import twitterapp.utils.CipherUtil;
import twitterapp.utils.StreamUtil;

public class UserService {

	public void register(User user) {

		Connection connection = null;
		try {
			connection = getConnection();

			String encPassward = CipherUtil.encrypt(user.getPassward());
			user.setPassward(encPassward);

			setDefaultIcon(user);

			UserDao userDao = new UserDao();
			userDao.insert(connection, user);

			commit(connection);
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

	private void setDefaultIcon(User user) {

		InputStream is = null;
		try {

			long randomNum = System.currentTimeMillis() % 5;

			String filePath = "/duke_" + randomNum + ".jpg";
			is = UserService.class.getResourceAsStream(filePath);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			StreamUtil.copy(is, baos);
			user.setIcon(baos.toByteArray());
		} finally {
			close(is);
		}
	}

}

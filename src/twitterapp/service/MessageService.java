package twitterapp.service;

import static twitterapp.utils.CloseableUtil.*;
import static twitterapp.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import twitterapp.beans.Message;
import twitterapp.beans.UserMessage;
import twitterapp.dao.MessageDao;
import twitterapp.dao.UserMessageDao;

public class MessageService {

	private static final int LIMIT_NUM = 1000;

	public void register(Message message) {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			messageDao.insert(connection, message);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public List<UserMessage> getMessage(Integer userId) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			List<UserMessage> ret = messageDao.getUserMessages(connection,
					userId, LIMIT_NUM);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

}

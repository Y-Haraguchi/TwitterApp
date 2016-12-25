package twitterapp.dao;

import static twitterapp.utils.CloseableUtil.*;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import twitterapp.beans.User;
import twitterapp.exception.SQLRuntimeException;

public class UserDao {

	public void insert(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO twitterdb.user ( ");
			sql.append("id");
			sql.append(", account");
			sql.append(", name");
			sql.append(", email");
			sql.append(", passward");
			sql.append(", description");
			sql.append(", icon");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append("?");					//id
			sql.append(", ?");					//account
			sql.append(", ?");					//name
			sql.append(", ?");					//email
			sql.append(", ?");					//passward
			sql.append(", ?");					//description
			sql.append(", ?");					//icon
			sql.append(", CURRENT_TIMESTAMP");	//insert_date
			sql.append(", CURRENT_TIMESTAMP");	//update_date

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getAccount());
			ps.setString(2, user.getName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassward());
			ps.setString(5, user.getDescription());

			if(user.getIcon() != null) {
				ps.setBinaryStream(6, new ByteArrayInputStream(user.getIcon()));
			} else {
				ps.setObject(6, null);
			}
			//sql文実行
			ps.executeUpdate();

		} catch(SQLException e) {
			throw new SQLRuntimeException(e);
		}finally {
			close(ps);
		}



	}

}
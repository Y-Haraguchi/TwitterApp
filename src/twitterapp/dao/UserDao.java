package twitterapp.dao;

import static twitterapp.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import twitterapp.beans.User;
import twitterapp.exception.SQLRuntimeException;

public class UserDao {

	//ログイン時に使用するgetUserメソッド
	public User getUser(Connection connection, String accountOrEmail, String password) {

		PreparedStatement ps = null;
		try {
			//すでにDBに登録してあるデータの参照
			String sql = "SELECT * FROM twitterdb.user WHERE (account = ? OR email = ?) AND password = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, accountOrEmail);
			ps.setString(2, accountOrEmail);
			ps.setString(3, password);

			//SQLの実行
			ResultSet rs = ps.executeQuery();

			//User型のリストを作成してtoUserListメソッドで取得したデータを格納
			List<User> userList = toUserList(rs);

			//リストの中身がnullだったらnullを返す
			if(userList.isEmpty() == true) {
				return null;
			} else if(2 <= userList.size()) {	//userListの要素数が2以上だったら下記の処理を実行
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	//SQL実行後に各データをListに格納するメソッド
	public List<User> toUserList(ResultSet rs) throws SQLException {

		//データを格納するためのリストを生成
		List<User> ret = new ArrayList<User>();
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String account = rs.getString("account");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String description = rs.getString("description");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Timestamp updateDate = rs.getTimestamp("update_date");

				//Userオブジェクトを生成してセッターでデータを各種セットしていく
				User user = new User();
				user.setId(id);
				user.setAccount(account);
				user.setName(name);
				user.setEmail(email);
				user.setPassword(password);
				user.setDescription(description);
				user.setInsertDate(insertDate);
				user.setUpdateDate(updateDate);

				//Listにセットされたデータを格納
				ret.add(user);
			}
			//retに入っているListに格納されたデータ群を返す
			return ret;
		} finally {
			close(rs);

		}




	}


	//INSERTメソッド
	public void insert(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			//オートインクリメントを使用するのでIDの指定はしない
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO twitterdb.user ( ");
			sql.append("account");
			sql.append(", name");
			sql.append(", email");
			sql.append(", password");
			sql.append(", description");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append("?");					//account
			sql.append(", ?");					//name
			sql.append(", ?");					//email
			sql.append(", ?");					//passward
			sql.append(", ?");					//description
			sql.append(", CURRENT_TIMESTAMP");	//insert_date
			sql.append(", CURRENT_TIMESTAMP");	//update_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getAccount());
			ps.setString(2, user.getName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getDescription());
			//sql文実行
			ps.executeUpdate();

		} catch(SQLException e) {
			throw new SQLRuntimeException(e);
		}finally {
			close(ps);
		}



	}

}

package question;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {

	PreparedStatement psmt = null;
	Connection conn = null;
	ResultSet rs = null;
	int row = 0;

	private void getConn() {
		try {
			String url = "jdbc:oracle:thin:@project-db-cgi.smhrd.com:1524:xe";
			String username = "cgi_23IS_CLOUD1_mini_1";
			String password = "smhrd1";
			conn = DriverManager.getConnection(url, username, password);
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void getClose() {
		try {
			if (rs != null)
				rs.close();
			if (psmt != null)
				psmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			System.out.println("자원반납 문제 발생");
		}

	}

	public int join(MemberDTO dto) {
		int row = 0;
		try {

			getConn();

			String sql = "INSERT INTO USER_INFO(ID,PW,NICKNAME,BIRTH) VALUES(?,?,?,?)";

			psmt = conn.prepareStatement(sql);

			psmt.setString(1, dto.getMyId());
			psmt.setString(2, dto.getMyPw());
			psmt.setString(3, dto.getMyNickname());
			psmt.setInt(4, dto.getMyBirth());

			row = psmt.executeUpdate();

			String sql2 = "INSERT INTO RANK_INFO(ID) VALUES(?)";

			psmt = conn.prepareStatement(sql2);

			psmt.setString(1, dto.getMyId());

			row = psmt.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			getClose();
		}

		return row;

	}

	public MemberDTO login(MemberDTO dto) {

		MemberDTO result = null;

		try {

			getConn();

			String sql = "SELECT ID, NICKNAME FROM USER_INFO WHERE ID = ? AND PW = ?";

			psmt = conn.prepareStatement(sql);

			psmt.setString(1, dto.getMyId());
			psmt.setString(2, dto.getMyPw());

			rs = psmt.executeQuery();

			if (rs.next()) {

				result = new MemberDTO();
				result.setMyId(rs.getString("id"));
				result.setMyNickname(rs.getString("nickname"));

			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			getClose();
		}
		return result;

	}

	public int delete(MemberDTO dto) {
		try {
			getConn();

			String sql2 = "DELETE FROM RANK_INFO WHERE ID = ?";

			psmt = conn.prepareStatement(sql2);

			psmt.setString(1, dto.getMyId());

			row = psmt.executeUpdate();

			String sql = "DELETE FROM USER_INFO WHERE ID = ? AND PW = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getMyId());
			psmt.setString(2, dto.getMyPw());

			row = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		return row;
	}

	public boolean getBirth(MemberDTO dto) {

		boolean result = false;

		getConn();

		String sql = "SELECT BIRTH FROM USER_INFO WHERE ID = ?";

		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, dto.getMyId());
			rs = psmt.executeQuery();

			if (rs.next()) {
				if (rs.getInt("BIRTH") < 2004) {
					result = true;
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			getClose();
		}

		return result;
	}

	public int updateWant(MemberDTO dto, String wantDate) {

		getConn();

		String sql = "UPDATE USER_INFO SET WANT = ? WHERE ID = ?";

		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, wantDate);
			psmt.setString(2, dto.getMyId());

			int row = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		return row;
	}

}

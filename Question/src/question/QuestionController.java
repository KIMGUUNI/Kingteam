package question;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class QuestionController {

	public static final String RESET = "\u001B[0m";
	public static final String BACKGROUND_YELLOW = "\u001B[43m";
	public static final String FONT_RED = "\u001B[31m";
	public static final String FONT_YELLOW = "\u001B[33m";

	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	Scanner sc = new Scanner(System.in);
	Random rd = new Random();
	Question q = new Question();

	int[] kim = new int[40];
	int[] park = new int[40];
	int[] gun = new int[40];
	int[] lee = new int[40];
	int[] jo = new int[40];
	int[] bum = new int[40];
	int[] user = new int[40];
	int[] Score = new int[6];

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

	// 사용자가 선택한 답 업데이트 메소드
	public void updateAns(MemberDTO dto, int num, int ans) {
		try {
			// db연결
			getConn();
			String ansNum = "u_q" + num;
			String sql = "UPDATE USER_INFO SET " + ansNum + " = ? WHERE ID = ?";

			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, ans);
			psmt.setString(2, dto.getMyId());

			psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("죄송합니다. 다시한번 실행해주세요.");
		} finally {
			getClose();
		}
	}

	// 문제 출력 메소드
	public void printQ(ArrayList<Question> qList, MemberDTO dto) {

		int qNum = 40; // 문제 개수
		int ranNum = 15; // 랜덤으로 뽑는 개수
		int[] arr = new int[qNum]; // 사용자 입력한 답 저장 배열
		int[] sum = new int[6]; // NPC와 겹치는 갯수
		QuestionController qC = new QuestionController();

		for (int i = 0; i < ranNum; i++) {
			int num = rd.nextInt(qNum);

			if (arr[num] > 0) {
				i--;
			} else {
				System.out.println(BACKGROUND_YELLOW + FONT_RED + "[문제]" + RESET);
				System.out.println((i + 1) + ".  " + qList.get(num).getQuestion() + " (" + num + "번 문제)");
				System.out.println();
				q.run();

				while (true) {
					System.out.print(FONT_YELLOW + "선택 " + RESET + ">> ");
					int ans = sc.nextInt();
					System.out.println();

					if (ans == 1 || ans == 2) {
						arr[num] = ans;
						qC.updateAns(dto, num + 1, ans); // 답 update
						break;
					} else {
						System.out.println("다시 입력해주세요!");
					}
				}
			}

		}
		System.out.println();
	}

	// 전체문제 본인 답
	public int[] answer(MemberDTO dto) {
		int[] allAnswer = new int[40];

		getConn();
		try {
			String sql = "SELECT U_Q1,U_Q2,U_Q3,U_Q4,U_Q5,U_Q6,U_Q7,U_Q8,U_Q9,U_Q10,U_Q11,U_Q12,U_Q13,U_Q14,U_Q15,U_Q16,U_Q17,U_Q18,U_Q19,U_Q20,U_Q21,U_Q22,U_Q23,U_Q24,U_Q25,U_Q26,U_Q27,U_Q28,U_Q29,U_Q30,U_Q31,U_Q32,U_Q33,U_Q34,U_Q35,U_Q36,U_Q37,U_Q38,U_Q39,U_Q40 FROM USER_INFO WHERE ID = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getMyId());
			rs = psmt.executeQuery();
			if (rs.next()) {
				dto = new MemberDTO();
				for (int i = 0; i < allAnswer.length; i++) {
					allAnswer[i] = rs.getInt(i + 1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		return allAnswer;

	}

	public void initDB(MemberDTO dto) {
		try {
			// db연결
			getConn();
			String sql = "UPDATE USER_INFO SET U_Q1 = ?, U_Q2 = ?, U_Q3 = ?, U_Q4 = ?, U_Q5 = ?, U_Q6 = ?, U_Q7 = ?, U_Q8 = ?, U_Q9 = ?, U_Q10 = ?, U_Q11 = ?, U_Q12 = ?, U_Q13 = ?, U_Q14 = ?, U_Q15 = ?, U_Q16 = ?, U_Q17 = ?, U_Q18 = ?, U_Q19 = ?, U_Q20 = ?, U_Q21 = ?, U_Q22 = ?, U_Q23 = ?, U_Q24 = ?, U_Q25 = ?, U_Q26 = ?, U_Q27 = ?, U_Q28 = ?, U_Q29 = ?, U_Q30 = ?, U_Q31 = ?, U_Q32 = ?,  U_Q33 = ?, U_Q34 = ?, U_Q35 = ?, U_Q36 = ?, U_Q37 = ?, U_Q38 = ?, U_Q39 = ?, U_Q40 = ? WHERE ID = ?";

			psmt = conn.prepareStatement(sql);

			for (int i = 1; i < 41; i++) {
				psmt.setInt(i, 0);
			}
			psmt.setString(41, dto.getMyId());

			psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("죄송합니다. 다시한번 실행해주세요.");
		} finally {
			getClose();
		}
	}

	public ArrayList<String> rankSave(MemberDTO dto) {

		getConn();
		ArrayList<String> res = new ArrayList<String>();

		MemberDTO result = null;
		int row = 0;

		try {
			getConn();
			String sql = "SELECT U_Q1,U_Q2,U_Q3,U_Q4,U_Q5,U_Q6,U_Q7,U_Q8,U_Q9,U_Q10,U_Q11,U_Q12,U_Q13,U_Q14,U_Q15,U_Q16,U_Q17,U_Q18,U_Q19,U_Q20,U_Q21,U_Q22,U_Q23,U_Q24,U_Q25,U_Q26,U_Q27,U_Q28,U_Q29,U_Q30,U_Q31,U_Q32,U_Q33,U_Q34,U_Q35,U_Q36,U_Q37,U_Q38,U_Q39,U_Q40 FROM USER_INFO WHERE ID = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getMyId());
			rs = psmt.executeQuery();

			int count = 1;
			if (rs.next()) {
				while (true) {
					for (int i = 0; i < 40; i++) {
						user[i] = rs.getInt(count++);
					}
					if (count == 41) {
						count = 1;
						break;
					}
				}
			}

			String sql1 = "SELECT N_Q1,N_Q2,N_Q3,N_Q4,N_Q5,N_Q6,N_Q7,N_Q8,N_Q9,N_Q10,N_Q11,N_Q12,N_Q13,N_Q14,N_Q15,N_Q16,N_Q17,N_Q18,N_Q19,N_Q20,N_Q21,N_Q22,N_Q23,N_Q24,N_Q25,N_Q26,N_Q27,N_Q28,N_Q29,N_Q30,N_Q31,N_Q32,N_Q33,N_Q34,N_Q35,N_Q36,N_Q37,N_Q38,N_Q39,N_Q40 FROM NPC_INFO WHERE NAME = '박범석'";
			psmt = conn.prepareStatement(sql1);
			rs = psmt.executeQuery();

			if (rs.next()) {
				while (true) {
					for (int i = 0; i < 40; i++) {
						bum[i] = rs.getInt(count++);
					}
					if (count == 41) {
						count = 1;
						break;
					}
				}
			}

			String sql2 = "SELECT N_Q1,N_Q2,N_Q3,N_Q4,N_Q5,N_Q6,N_Q7,N_Q8,N_Q9,N_Q10,N_Q11,N_Q12,N_Q13,N_Q14,N_Q15,N_Q16,N_Q17,N_Q18,N_Q19,N_Q20,N_Q21,N_Q22,N_Q23,N_Q24,N_Q25,N_Q26,N_Q27,N_Q28,N_Q29,N_Q30,N_Q31,N_Q32,N_Q33,N_Q34,N_Q35,N_Q36,N_Q37,N_Q38,N_Q39,N_Q40 FROM NPC_INFO WHERE NAME = '박형찬'";
			psmt = conn.prepareStatement(sql2);

			rs = psmt.executeQuery();

			if (rs.next()) {
				while (true) {
					for (int i = 0; i < 40; i++) {
						park[i] = rs.getInt(count++);
					}
					if (count == 41) {
						count = 1;
						break;
					}
				}
			}

			String sql3 = "SELECT N_Q1,N_Q2,N_Q3,N_Q4,N_Q5,N_Q6,N_Q7,N_Q8,N_Q9,N_Q10,N_Q11,N_Q12,N_Q13,N_Q14,N_Q15,N_Q16,N_Q17,N_Q18,N_Q19,N_Q20,N_Q21,N_Q22,N_Q23,N_Q24,N_Q25,N_Q26,N_Q27,N_Q28,N_Q29,N_Q30,N_Q31,N_Q32,N_Q33,N_Q34,N_Q35,N_Q36,N_Q37,N_Q38,N_Q39,N_Q40 FROM NPC_INFO WHERE NAME = '이하연'";
			psmt = conn.prepareStatement(sql3);

			rs = psmt.executeQuery();

			if (rs.next()) {
				while (true) {
					for (int i = 0; i < 40; i++) {
						lee[i] = rs.getInt(count++);
					}
					if (count == 41) {
						count = 1;
						break;
					}
				}
			}

			String sql4 = "SELECT N_Q1,N_Q2,N_Q3,N_Q4,N_Q5,N_Q6,N_Q7,N_Q8,N_Q9,N_Q10,N_Q11,N_Q12,N_Q13,N_Q14,N_Q15,N_Q16,N_Q17,N_Q18,N_Q19,N_Q20,N_Q21,N_Q22,N_Q23,N_Q24,N_Q25,N_Q26,N_Q27,N_Q28,N_Q29,N_Q30,N_Q31,N_Q32,N_Q33,N_Q34,N_Q35,N_Q36,N_Q37,N_Q38,N_Q39,N_Q40 FROM NPC_INFO WHERE NAME = '조원제'";
			psmt = conn.prepareStatement(sql4);

			rs = psmt.executeQuery();

			if (rs.next()) {
				while (true) {
					for (int i = 0; i < 40; i++) {
						jo[i] = rs.getInt(count++);
					}
					if (count == 41) {
						count = 1;
						break;
					}
				}
			}

			String sql5 = "SELECT N_Q1,N_Q2,N_Q3,N_Q4,N_Q5,N_Q6,N_Q7,N_Q8,N_Q9,N_Q10,N_Q11,N_Q12,N_Q13,N_Q14,N_Q15,N_Q16,N_Q17,N_Q18,N_Q19,N_Q20,N_Q21,N_Q22,N_Q23,N_Q24,N_Q25,N_Q26,N_Q27,N_Q28,N_Q29,N_Q30,N_Q31,N_Q32,N_Q33,N_Q34,N_Q35,N_Q36,N_Q37,N_Q38,N_Q39,N_Q40 FROM NPC_INFO WHERE NAME = '김건휘'";
			psmt = conn.prepareStatement(sql5);

			rs = psmt.executeQuery();

			if (rs.next()) {
				while (true) {
					for (int i = 0; i < 40; i++) {
						gun[i] = rs.getInt(count++);
					}
					if (count == 41) {
						count = 1;
						break;
					}
				}
			}

			String sql6 = "SELECT N_Q1,N_Q2,N_Q3,N_Q4,N_Q5,N_Q6,N_Q7,N_Q8,N_Q9,N_Q10,N_Q11,N_Q12,N_Q13,N_Q14,N_Q15,N_Q16,N_Q17,N_Q18,N_Q19,N_Q20,N_Q21,N_Q22,N_Q23,N_Q24,N_Q25,N_Q26,N_Q27,N_Q28,N_Q29,N_Q30,N_Q31,N_Q32,N_Q33,N_Q34,N_Q35,N_Q36,N_Q37,N_Q38,N_Q39,N_Q40 FROM NPC_INFO WHERE NAME = '김찬혁'";
			psmt = conn.prepareStatement(sql6);

			rs = psmt.executeQuery();

			if (rs.next()) {
				while (true) {
					for (int i = 0; i < 40; i++) {
						kim[i] = rs.getInt(count++);
					}
					if (count == 41) {
						count = 1;
						break;
					}
				}
			}

			int length = 0;
			for (int i = 0; i < 40; i++) {
				if (user[i] != 0) {
					length++;

				}
			}

			Map<Integer, String> nameMap = new HashMap<>();

			nameMap.put(1, "김건휘");
			nameMap.put(2, "김찬혁");
			nameMap.put(3, "박범석");
			nameMap.put(4, "박형찬");
			nameMap.put(5, "이하연");
			nameMap.put(6, "조원제");

			Score[0] = nameArray(user, gun);
			Score[1] = nameArray(user, kim);
			Score[2] = nameArray(user, bum);
			Score[3] = nameArray(user, park);
			Score[4] = nameArray(user, lee);
			Score[5] = nameArray(user, jo);

			String Lank = "UPDATE RANK_INFO SET 김건휘 = ?, 김찬혁 = ?, 박범석 = ?, 박형찬 = ?, 이하연 = ?, 조원제 = ? WHERE ID = ?";

			psmt = conn.prepareStatement(Lank);

			psmt.setInt(1, Math.round(Score[0] * 100 / length));
			psmt.setInt(2, Math.round(Score[1] * 100 / length));
			psmt.setInt(3, Math.round(Score[2] * 100 / length));
			psmt.setInt(4, Math.round(Score[3] * 100 / length));
			psmt.setInt(5, Math.round(Score[4] * 100 / length));
			psmt.setInt(6, Math.round(Score[5] * 100 / length));
			psmt.setString(7, dto.getMyId());

			row = psmt.executeUpdate();

			int temp = 0;

			for (int i = 0; i < Score.length - 1; i++) {
				for (int j = i + 1; j < Score.length; j++) {
					if (Score[i] < Score[j]) {
						int tempScore = Score[i];
						Score[i] = Score[j];
						Score[j] = tempScore;

						String tempName = nameMap.get(i + 1);
						nameMap.put(i + 1, nameMap.get(j + 1));
						nameMap.put(j + 1, tempName);
					}
				}

			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			getClose();
		}
		return res;

	}

	public ArrayList<String> rankSearch(MemberDTO dto) {
		getConn();
		ArrayList<String> res = new ArrayList<String>();
		int[] matchResult = new int[6];
		int max = 0, min = 0;
		int maxInd = 0, minInd = 0;
		boolean minOver = false, maxOver = false;

		try {
			String sql1 = "SELECT * FROM RANK_INFO WHERE ID = ? AND 김건휘 IS NOT NULL";

			psmt = conn.prepareStatement(sql1);
			psmt.setString(1, dto.getMyId());

			rs = psmt.executeQuery();

			if (rs.next()) {

				String sql7 = "SELECT * FROM RANK_INFO WHERE ID = ?";
				psmt = conn.prepareStatement(sql7);
				psmt.setString(1, dto.getMyId());
				// psmt.setString(1, dto.getId~~~);

				String[] name = { "김건휘", "김찬혁", "박범석", "박형찬", "이하연", "조원제" };

				rs = psmt.executeQuery();
				int i = 0;
				if (rs.next()) {
					while (true) {
						matchResult[i] = rs.getInt(name[i]);
						i++;
						if (i >= 6)
							break;
					}
				}

				max = matchResult[0];
				min = matchResult[0];

				for (int j = 1; j < 6; j++) {
					if (matchResult[j] > max) {
						max = matchResult[j];
						maxInd = j;
					} else if (matchResult[j] < min) {
						min = matchResult[j];
						minInd = j;
					}
				}
				for (int l = 0; l < 6; l++) {
					if (maxInd != l) {
						if (matchResult[l] == max) {
							maxOver = true;
						}

					}
					if (minInd != l) {
						if (matchResult[l] == min) {
							minOver = true;
						}
					}
				}

				System.out.println("=============================");
				System.out.println();
				if (maxOver) {
					System.out.println(" 공동 1등은 !!! ");
					for (int k = 0; k < 6; k++) {
						if (matchResult[k] == max) {
							System.out.println(name[k] + "님과 " + matchResult[k] + "% 의 매칭률 !!");
							res.add(name[k]);
						}
					}
				} else {
					System.out.println(" 1등은 !!! ");
					System.out.println(name[maxInd] + "님과 " + max + "% 의 매칭률 !!");
					res.add(name[maxInd]);
				}
				System.out.println();
				System.out.println("=============================");
				System.out.println();
				if (minOver) {
					System.out.println(" 공동 꼴등은 !!! ");
					for (int k = 0; k < 6; k++) {
						if (matchResult[k] == min) {
							System.out.println(name[k] + "님과 " + matchResult[k] + "% 의 매칭률 ㅠㅠ");
						}
					}
				} else {
					System.out.println(" 꼴등은 !!! ");
					System.out.println(name[minInd] + "님과 " + min + "% 의 매칭률 !ㅠㅠ");
				}
				System.out.println();
			} else {
				System.out.println("문제를 풀어주세요!");
				System.out.println();
			}
		} catch (

		SQLException e) {

			e.printStackTrace();
		} finally {
			getClose();
		}
		return res;
	}

	public static int nameArray(int[] user, int[] person) {
		int score = 0;
		for (int i = 1; i < 40; i++) {
			if (user[i] == person[i]) {
				score++;
			}
		}
		return score;
	}

	public MemberDTO checkTutorial(MemberDTO dto) {
		MemberDTO tutoResult = null;
		try {
			getConn();

			String sql = "SELECT TUTO FROM USER_INFO WHERE ID = ? ";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getMyId());

			rs = psmt.executeQuery();
			if (rs.next()) {
				tutoResult = new MemberDTO();
				tutoResult.setMyTuto(rs.getInt(1));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		return tutoResult;

	}

	public void updateTutorial(MemberDTO dto) {

		try {
			getConn();
			String sql = "UPDATE USER_INFO SET TUTO  = ? WHERE ID = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, 1);
			psmt.setString(2, dto.getMyId());

			psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			getClose();
		}

	}

	public void wonJae() {
		ArrayList<String> arr = new ArrayList<>();
		int[] arrAns = { 1, 2, 2, 2, 2, 2 };

		int sum = 0;
		arr.add(0, "1. (1) 켜고 VS (2) 끄고");
		arr.add(1, "2. (1) 30초 VS (2) 3시간");
		arr.add(2, "3. (1) 길이 VS (2) 두께");
		arr.add(3, "4. (1) 실내 VS (2) 야외");
		arr.add(4, "5. (1) 손 VS (2) 입");
		arr.add(5, "6. (1) 앞 VS (2) 뒤");

		System.out.println(FONT_RED + " 상상만 해도 달아오르는 원제와의 야릇한 데이트에 초대된 당신 " + RESET);
		System.out.println();
		System.out.println(FONT_RED + " 원제와의 화끈한 데이트전 서로의 취향을 알아가는 타임 " + RESET);
		System.out.println();
		System.out.println(FONT_RED + " 원제와 함께하는 상상과 함께 원하는 것을 선택해주세요 (* ￣3)(ε￣ *) " + RESET);
		System.out.println();

		for (int i = 0; i < 6; i++) {
			System.out.println(arr.get(i));
			System.out.print("당신의 선택은?? ");

			int ans = sc.nextInt();
			System.out.println();
			if (ans == arrAns[i]) {
				sum++;
			}
		}
		System.out.println();
		System.out.println("=================================");
		if (sum >= 5) {
			System.out.println("  정확도 : " + sum * 100 / 6 + "% !!!");
			System.out.println("진짜 다 맞는지 확인해볼까요?!");
		} else if (sum >= 3) {
			System.out.println("  정확도 : " + sum * 100 / 6 + "% !!!");
			System.out.println("조금씩 알아가 볼까요?");
		} else {
			System.out.println("  정확도 : " + sum * 100 / 6 + "% !!!");
			System.out.println("데이트만 권장 ..");
		}
		System.out.println("=================================");
		System.out.println();
	}

	public boolean match(MemberDTO dto) {
		boolean result = false;

		getConn();

		String want = null;
		int[] array = new int[6];
		String[] arrayU = { "D", "C", "F", "B", "E", "A" };
		try {

			String sql1 = "SELECT WANT FROM USER_INFO WHERE ID = ?";

			psmt = conn.prepareStatement(sql1);

			psmt.setString(1, dto.getMyId());

			rs = psmt.executeQuery();

			if (rs.next()) {
				want = rs.getString("WANT");

				String sql2 = "SELECT 김건휘, 김찬혁, 박범석, 박형찬, 이하연, 조원제 FROM RANK_INFO WHERE ID = ? ";
				psmt = conn.prepareStatement(sql2);
				psmt.setString(1, dto.getMyId());

				rs = psmt.executeQuery();

				if (rs.next()) {
					for (int i = 0; i < 6; i++) {

						array[i] = rs.getInt(i + 1);
					}

				}

				int temp = 0;
				String tempS = null;
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < i; j++) {
						if (array[i] > array[j]) {
							temp = array[j];
							array[j] = array[i];
							array[i] = temp;

							tempS = arrayU[j];
							arrayU[j] = arrayU[i];
							arrayU[i] = tempS;
						}
					}

				}

				if (want.charAt(0) == arrayU[0].charAt(0)) {
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

}

package question;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javazoom.jl.player.MP3Player;

public class Main {
	public static final String BACKGROUND_PURPLE = "\u001B[45m";
	public static final String FONT_WHITE = "\u001B[37m";
	public static final String RESET = "\u001B[0m";

	public static void main(String[] args) {

		MP3Player Mp3 = new MP3Player();
		mp3Player mp3 = new mp3Player();
		Music m1 = new Music("파라다이스");
		Music m2 = new Music("두근두근");
		Music m3 = new Music("찾았다내사랑");
		Music m4 = new Music("스트로베리 문");
		Music m5 = new Music("원제게임");

		Scanner sc = new Scanner(System.in);
		MemberDAO dao = new MemberDAO();
		QuestionController qC = new QuestionController();
		Question q = new Question();
		ArrayList<Question> qList = new ArrayList<>();
		DateGet dg = new DateGet();

		q.insertQ(qList);
		q.run2();
		try {
			dg.dateGet2();
			Thread.sleep(600);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mp3.stop();
		while (true) {
			mp3.stop();
			mp3.play(m1);

			System.out.print("[1]로그인 [2]회원가입 [3]회원탈퇴 [4]종료 >> ");
			int input = sc.nextInt();

			int[] array = new int[qList.size()];
			if (input == 1) {

				System.out.println("==로그인==");
				System.out.print("아이디 입력 : ");
				String myId = sc.next();
				System.out.print("비밀번호 입력 : ");
				String myPw = sc.next();
				MemberDTO dto = new MemberDTO();
				dto.setMyId(myId);
				dto.setMyPw(myPw);
				MemberDTO result = dao.login(dto);

				if (result != null) {
					System.out.println();
					System.out.println("로그인 성공");
					System.out.println(result.getMyNickname() + "님 환영합니다.");
					System.out.println();
					while (true) {

						System.out.print("[1] 게임 시작 [2]문제 조회 [3]문제 초기화 [4]랭킹 조회 [5]로그아웃 >> ");
						int input2 = sc.nextInt();
						System.out.println();

						if (input2 == 1) {
							System.out.println("======게임시작======");
							mp3.stop();
							mp3.play(m2);
							if (qC.checkTutorial(dto).getMyTuto() == 1) {
							} else {
								System.out.println("튜토리얼을 보시겠습니까?????????진짜로???");
								System.out.print("[1] 빨리 보여줘라 당장 [2] 절대 다시는 안본다  >> ");
								int tuto = sc.nextInt();
								if (tuto == 1) {
									q.showTutorial();
									System.out.println();
									System.out.println("(❤´艸｀❤)원하는 데이트를 선택해주세요(❤´艸｀❤)");
									System.out.println();
									System.out.print(result.getMyNickname() + "님이 원하는 데이트는 바로바로 (●'◡'●) 👉👉 ");
									String wantDate = sc.next();
									dao.updateWant(dto, wantDate);

								} else if (tuto == 2) {
									qC.updateTutorial(dto);
								} else {
									System.out.println("1번이랑 2번중에 골라라 썅");
								}
							}

							System.out.println("밸런스 게임 시작!!");
							System.out.println();
							// 문제 시작
							qC.printQ(qList, dto);

							qC.rankSave(dto);

							ArrayList<String> results = qC.rankSearch(dto);

							if (qC.match(dto)) {
								System.out.println("원하던 데이트!!");
								mp3.stop();
								mp3.play(m3);
							} else {
								System.out.println("원하던 데이트를 못맞추셨네요 ㅠ");
								mp3.stop();
								mp3.play(m4);
							}

							for (String a : results) {
								if (a.contains("조원제")) {
									System.out.println("히든 NPC 원제와 특별한 질문 시작하시겠습니까?? (1.시작 2.취소) >>");
									mp3.play(m5);
									int input3 = sc.nextInt();
									System.out.println();
									if (input3 == 1) {
										try {
											System.out.print("성인인증 중..");
											Thread.sleep(500);
											System.out.print(".");
											Thread.sleep(500);
											System.out.print(".");
											Thread.sleep(500);
											System.out.print("\b\b  ");
											System.out.print("\b\b");
											Thread.sleep(500);
											System.out.print(".");
											Thread.sleep(500);
											System.out.print(".");
											Thread.sleep(500);
											System.out.println();

										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										System.out.println();
										if (dao.getBirth(dto)) {
											System.out.println("성인인증이 완료되었습니다.");
											System.out.println();
											qC.wonJae();
											mp3.play(m4);

										} else {
											System.out.println("성인인증이 실패했습니다");
											mp3.play(m4);
										}

									} else if (input3 == 2) {
										System.out.println("종료");
										mp3.play(m4);
										break;
									}

								}
							}

						} else if (input2 == 2) {
							System.out.println("======문제조회======");
							array = qC.answer(dto);
							// 푼문제
							for (int i = 0; i < qList.size(); i++) {
								if (array[i] != 0) {
									System.out.println((i + 1) + "." + qList.get(i).getQuestion());
									System.out.println("   " + array[i]);
									System.out.println();

								}
							}
							// 안푼문제
							System.out.println();
							System.out.println("안푼 문제");
							for (int i = 0; i < qList.size(); i++) {
								if (array[i] == 0) {
									System.out.println((i + 1) + "." + qList.get(i).getQuestion());
									System.out.println();
								}
							}
						} else if (input2 == 3) {
							System.out.println("======문제초기화======");
							System.out.println("초기화 되었습니다");
							qC.initDB(dto);

						} else if (input2 == 4) {
							System.out.println("======랭킹조회======");
							ArrayList<String> results = qC.rankSearch(dto);
						} else if (input2 == 5) {
							try {
								mp3.stop();
								dg.dateGet2();
								Thread.sleep(600);
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;

						}
					}

				} else {
					System.out.println("로그인이 실패했습니다.");
				}
			}
			if (input == 2) {
				System.out.println("==회원가입==");
				System.out.print("아이디를 입력 하세요 : ");
				String myId = sc.next();
				System.out.print("비밀번호를 입력하세요 : ");
				String myPw = sc.next();
				System.out.print("애칭을 입력하세요 : ");
				String myNickname = sc.next();
				System.out.println("생년월일을 입력하세요 ");
				System.out.print("ex) 1997 >> ");
				int myBirth = sc.nextInt();

				MemberDTO dto = new MemberDTO(myId, myPw, myNickname, myBirth);

				int row = dao.join(dto);

				if (row > 0) {
					System.out.println("회원가입 성공");
				} else {
					System.out.println("회원가입 실패");
				}

			} else if (input == 3) {

				System.out.println("회원 탈퇴");
				System.out.print("아이디를 입력 하세요 : ");
				String myId = sc.next();
				System.out.print("비밀번호를 입력하세요 : ");
				String myPw = sc.next();
				MemberDTO dto = new MemberDTO(myId, myPw, null, 0);
				int row = dao.delete(dto);
				if (row > 0) {
					System.out.println("회원탈퇴에 성공했습니다");
				} else {
					System.out.println("회원탈퇴에 실패했습니다");
				}

			} else if (input == 4) {
				System.out.println();
				System.out.println("종료합니다!");
				mp3.stop();
				break;
			}
		}

	}
}

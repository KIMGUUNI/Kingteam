# Kingteam

# :pushpin: MiniProject
> 일일 데이트권을 따내라 
>
> 밸런스게임

</br>

## 1. 제작 기간 & 참여 인원
- 2023년 09월 18일 ~ 09월 22일
- Mini team project

</br>

## 2. 사용 기술
#### `Back-end`

<div>
  <img src="https://img.shields.io/badge/Java-%23008080?logo=Java&logoColor=gold"/>
  </br>
  <img src="https://img.shields.io/badge/Oracle-%23F80000?logo=Oracle"/>
</div>

#### `Front-end`
  - launch4j

</br>

## 3. ERD 설계
![ER다이어그램](https://github.com/KIMGUUNI/Kingteam/assets/142488092/c9e60a75-8172-435c-ab31-6cf3c12854b2)



## 4. 주요 기능
이 서비스의 주요 기능은 랜덤 밸런스 질문 출력입니다.
사용자는 회원가입과 로그인을 마치고 게임을 시작하면 저장 되어있는 문제들을 랜덤으로 뽑아 질문을 하고,
그 답을 DB에 저장합니다.
DB에 저장된 답은 미리 저장된 NPC의 답과 비교하여 가장 비슷한 취향의 사람의 순위를 메깁니다.

<details>
<summary><b>핵심 기능 설명 펼치기</b></summary>
<div markdown="1">

### 4.1. 전체 흐름
![image](https://github.com/KIMGUUNI/Kingteam/assets/142488092/e50ef0de-3876-4fcd-b2b2-a6aafecff394)


### 4.2. 시작화면

<p align="center"><img src="https://github.com/KIMGUUNI/Kingteam/assets/142488092/33a61329-592c-4fdd-bec8-c607840d1d6d" width="300" height="450"/></p>


- **그림문자 출력** 
  - Thread를 이용하여 문자를 순서대로 출력하여 그림처럼 구현하였습니다.
  

- **mp3파일 출력** :pushpin: [코드 확인]()
  - 그림문자가 끝나면 배경음악이 나오게 mp3파일을 재생합니다.
 
    </br>

### 4.3. 메인화면

![image](https://github.com/KIMGUUNI/Kingteam/assets/142488092/0782d1a6-f798-4e47-b980-a6f6c27412cd)


- **회원 정보 DB저장** :pushpin: [코드 확인]
  - 회원가입과 로그인 회원탈퇴 등 회원 정보들을 DB에 저장하고 관리합니다.

</br>

### 4.4. Service

![image](https://github.com/KIMGUUNI/Kingteam/assets/142488092/9cb846fb-aa26-46e0-886b-8e97b3b6edb1)


- **랜덤 질문 출력** :pushpin: [코드 확인]()
  - 사용자가 40개의 질문 중 일부를 랜덤으로 추출해 질문을 하고 답을 DB에 저장합니다
 
    </br>

  ![image](https://github.com/KIMGUUNI/Kingteam/assets/142488092/a97b255c-74da-437a-8a0d-d0be031028eb)



- **랭킹 조회** :pushpin: [코드 확인]()
  - 모든 질문이 끝나면 미리 저장되어있는 NPC의 답을 비교하고 가장 비슷한 답을 한 사람의 순위를 보여줍니다.

 </br>

 ![image](https://github.com/KIMGUUNI/Kingteam/assets/142488092/d2d37032-4aa6-406d-8466-0739af209f7c)


- **문제 조회** :pushpin: [코드 확인]()
  - 사용자가 풀었던 문제와 풀지 않은 문제를 구분하여 출력합니다.

- **질문 초기화** :pushpin: [코드 확인]()
  - 사용자의 질문 정보를 모두 초기화합니다.



</div>
</details>

</br>

## 5. 핵심 트러블 슈팅
### 5.1. 랭킹조회 기능 구현
- 비교해야할 칼럼의 수가 많아 데이터를 가져와 비교하고 저장하는 방법에 어려움을 느꼈습니다.
  
- 사용자가 풀었던 모든 문제들을 배열에 저장하고 DB에 보내면 모든 문제를 풀어야 하는 상황이 생겼습니다.

- 이를 보완하고자 테이블에 있는 모든 문제들을 컬럼화 시켰고 랜덤으로 뽑은 질문에 답했을 때 정보를 쉽게 관리 할 수 있었습니다.


<details>
<summary><b>개선된 코드</b></summary>
<div markdown="1">

~~~java
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
~~~

</div>
</details>

</br>

### 5.2. 배열에 다른 type 담기
- 배열에 있는 숫자 값과, String타입의 이름을 같이 반환해야 했는데 일반 배열로 값을 담아서 해결에 어려움을 느꼈습니다.

- Map 함수를 통해 인덱스마다 이름을 넣었고, 그 결과 정수와 String형 변수를 함께 반환할 수 있었습니다.

<details>
<summary><b>개선된 코드</b></summary>
<div markdown="1">

~~~java
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
~~~

</div>
</details>


## 6. 회고 / 느낀점
>프로젝트의 팀장 포지션에서 팀원들이 생각하는 코드가 다르기 때문에 연결하는 과정에서 팀원들과 소통이 필요하다는 것을 알았습니다.
>

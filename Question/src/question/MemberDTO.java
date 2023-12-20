package question;

public class MemberDTO {

	protected String myId;
	protected String myPw;
	protected String myNickname;
	protected String[] history;
	protected int myTuto;
	protected int myBirth;
	protected String want;

	public MemberDTO(String myId, String myPw, String myNickname, int myBirth) {
		this.myId = myId;
		this.myPw = myPw;
		this.myNickname = myNickname;
		this.myBirth = myBirth;
	}

	public MemberDTO() {
	}

	public int getMyBirth() {
		return myBirth;
	}

	public void setMyBirth(int myBirth) {
		this.myBirth = myBirth;
	}

	public int getMyTuto() {
		return myTuto;
	}

	public void setMyTuto(int myTuto) {
		this.myTuto = myTuto;
	}

	public String getMyId() {
		return myId;
	}

	public void setMyId(String myId) {
		this.myId = myId;
	}

	public String getMyPw() {
		return myPw;
	}

	public void setMyPw(String myPw) {
		this.myPw = myPw;
	}

	public String getMyNickname() {
		return myNickname;
	}

	public void setMyNickname(String myNickname) {
		this.myNickname = myNickname;
	}

	public String getWant() {
		return want;
	}

	public void setWant(String want) {
		this.want = want;
	}

}

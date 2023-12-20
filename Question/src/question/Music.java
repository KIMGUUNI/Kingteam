package question;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Music {

	private String title;
//	private String path = "..\\Question\\src\\player\\";
	
	String path = System.getProperty("user.dir");
//	System.out.println(path);

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Music(String title) {
		this.title = title;
	}

}

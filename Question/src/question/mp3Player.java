package question;

import java.nio.file.Path;
import java.nio.file.Paths;

import javazoom.jl.player.MP3Player;

public class mp3Player {

	private MP3Player mp3 = new MP3Player();
	Music m = new Music(null);
	Path currentPath = Paths.get(m.getPath());

	public mp3Player() {

		// 뮤직 플레이리스트 추가하는 작업

	}

	public boolean stop() {
		boolean result = false;
		if (mp3.isPlaying()) {
			mp3.stop();
			result = true;
		}
		return result;
	}

	public void play(Music m) {
		stop();
		mp3.play(m.getPath() + "\\src\\player\\" + m.getTitle() + ".mp3");

	}

}

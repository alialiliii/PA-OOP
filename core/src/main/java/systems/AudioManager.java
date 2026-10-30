package systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private static AudioManager instance;


    private Map<String, Sound> soundMap;
    private Music currentBGM;

    private AudioManager() {
        soundMap = new HashMap<>();
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }


    public void playBGM(String filename) {
        if (currentBGM != null) {
            currentBGM.stop();
        }
        if (Gdx.files.internal(filename).exists()) {
            currentBGM = Gdx.audio.newMusic(Gdx.files.internal(filename));
            currentBGM.setLooping(true);
            currentBGM.setVolume(0.5f);
            currentBGM.play();
        } else {
            System.out.println("File BGM nowhere to be found: " + filename);
        }
    }


    public void playSFX(String filename) {

        if (soundMap.containsKey(filename)) {
            soundMap.get(filename).play(1.0f); // Play volume 100%
            return;
        }


        if (Gdx.files.internal(filename).exists()) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal(filename));
            soundMap.put(filename, sound);
            sound.play(1.0f);
        } else {
            System.out.println("File SFX nowhere to be found: " + filename);
        }
    }

    public void dispose() {
        if (currentBGM != null) currentBGM.dispose();
        for (Sound s : soundMap.values()) {
            s.dispose();
        }
        soundMap.clear();
    }
}

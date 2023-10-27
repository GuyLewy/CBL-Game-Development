import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Class sound used to store a sound effect or music.
 * 
 * @author Guy Lewy
 * @author Antoni Nowaczyk
 */
public class Sound {
    Clip clip;
    String[] soundEffectPath = new String[10];
    FloatControl fc;

    /**
     * Give an index number to every sound.
     */
    public Sound() {
        soundEffectPath[0] = "./sound/shot.wav";
        soundEffectPath[1] = "./sound/enemyHit.wav";
        soundEffectPath[2] = "./sound/playerHit.wav";
        soundEffectPath[3] = "./sound/Soundtrack.wav";
        soundEffectPath[4] = "./sound/startMenu.wav";
        soundEffectPath[5] = "./sound/startGame.wav";
        soundEffectPath[6] = "./sound/powerUp.wav";
    }

    /**
     * Assign a particular sound effect to sound object.
     * 
     * @param i - index of the effect.
     */
    public void setSoundEffect(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(
                    getClass().getResource(soundEffectPath[i]));
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            ;
        }
    }

    public void play() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void setVolume(float volume) {
        fc.setValue(volume);
    }
}

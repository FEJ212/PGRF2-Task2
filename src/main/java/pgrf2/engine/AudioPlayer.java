package pgrf2.engine;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioPlayer {
    private Clip clip;
    private FloatControl volumeControl;

    public AudioPlayer() {
    }

    public void playMusic(String filePath) {
        try {
            URL url = getClass().getClassLoader().getResource(filePath);
            if (url == null) {
                throw new RuntimeException("Audio file not found: " + filePath);
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            AudioFormat baseFormat = audioInputStream.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream decodedAudioInputStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);
            DataLine.Info info = new DataLine.Info(Clip.class, decodedFormat);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(decodedAudioInputStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            volumeControl.setValue(20f * (float) Math.log10(volume));
        }
    }

    public void setPlaybackSpeed(float speed) {
        // Změna rychlosti přehrávání není přímo podporována MP3SPI.
        // Můžete zkusit manipulovat s audio daty nebo použít jinou knihovnu pro tuto funkci.
    }

    public void stopMusic() {
        if (clip != null) {
            clip.stop();
        }
    }

    public void cleanup() {
        if (clip != null) {
            clip.close();
        }
    }
}

package pgrf2.engine;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AudioPlayer {
    private Clip clip;
    private FloatControl volumeControl;

    public AudioPlayer() {
    }

    public void playMusic(String filePath) {
        clip = loadMusic(filePath);
        if (clip != null) {
            clip.start();
        }
    }

    public Clip loadMusic(String filePath) {
        Clip clip = null;
        try {
            InputStream musicStream = getClass().getClassLoader().getResourceAsStream(filePath);
            if (musicStream == null) {
                throw new IOException("File not found: " + filePath);
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(musicStream));
            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream decodedAudioStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);
            clip = AudioSystem.getClip();
            clip.open(decodedAudioStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return clip;
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            float dB = (float) (Math.log10(volume) * 20);
            volumeControl.setValue(dB);
        }
    }


    public void setPlaybackSpeed(float speed) {
        // Nepovedlo se mi zprovoznit
    }

    public void stopMusic() {
        if (clip != null) {
            clip.stop();
        }
    }
}

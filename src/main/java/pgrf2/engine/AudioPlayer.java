package pgrf2.engine;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class AudioPlayer {
    private Long device;
    private Long context;
    private int source;

    public AudioPlayer() {
        // Otevření výchozího zařízení
        device = ALC10.alcOpenDevice((ByteBuffer) null);
        if (device == null) {
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        }

        // Vytvoření kontextu
        ALCCapabilities deviceCapabilities = ALC.createCapabilities(device);
        context = ALC10.alcCreateContext(device, (IntBuffer) null);
        if (context == null) {
            throw new IllegalStateException("Failed to create OpenAL context.");
        }

        // Nastavení kontextu jako aktuálního
        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCapabilities);

        // Vytvoření zdroje zvuku
        source = AL10.alGenSources();
    }

    public void playMusic(int buffer) {
        AL10.alSourcei(source, AL10.AL_BUFFER, buffer);
        AL10.alSourcePlay(source);
    }

    public void setVolume(float volume) {
        AL10.alSourcef(source, AL10.AL_GAIN, volume);
    }

    public void setPlaybackSpeed(float speed) {
        AL10.alSourcef(source, AL10.AL_PITCH, speed);
    }

    public void stopMusic() {
        AL10.alSourceStop(source);
    }

    public void cleanup() {
        AL10.alDeleteSources(source);
        ALC10.alcDestroyContext(context);
        ALC10.alcCloseDevice(device);
    }
}

package pgrf2.models.gramophone;

import pgrf2.engine.AudioPlayer;
import pgrf2.models.Scene;
import pgrf2.models.gramophone.parts.Knob;
import pgrf2.models.gramophone.parts.Disk;
import pgrf2.models.gramophone.parts.GramophoneBase;
import pgrf2.models.gramophone.parts.Leg;
import static org.lwjgl.opengl.GL11.*;

public class GramophoneScene extends Scene {
    private GramophoneBase base;
    private Disk disk;
    private Leg leg;
    private Knob knob;
    private float speed, volume;
    private float angle, diskAngle, leftKnobAngle, rightKnobAngle;
    private AudioPlayer audioPlayer;

    public GramophoneScene(GramophoneBase base, Disk disk, Leg leg, Knob knob, AudioPlayer audioPlayer) {
        this.base = base;
        this.disk = disk;
        this.leg = leg;
        this.knob = knob;
        this.audioPlayer = audioPlayer;
        this.speed = 1.f;
        this.volume = 1.f;
        angle = 1.f;
        diskAngle = 0;
    }

    public void render() {
        diskAngle = diskAngle % 360;

        base.render();

        // Posun a vykreslení disku
        glPushMatrix();
        glScalef(1.5f, 1.5f, 1.5f);
        glTranslatef(0.4f, 0.12f, 0.4f); // Posun disku na vrchní stranu gramofonu
        glRotatef(90, 1, 0, 0); // Otočení disku pro správnou orientaci
        if (speed>0.f&&speed<2.2f) {
            glRotatef(diskAngle * speed * 1.5f, 0, 0, 1);
        }
        disk.render();
        glPopMatrix();

        // Posun a vykreslení nohou
        float legOffset = 0.1f; // Odstup nohou od okrajů
        glPushMatrix();
        glTranslatef(-1.f+legOffset, 1.2f, 0.f+legOffset); // Levý přední roh
        glRotatef(90, 1, 0, 0); // Otočení nohy
        leg.render();
        glPopMatrix();

        glPushMatrix();
        glTranslatef(1.f-legOffset, 1.2f, 0.f+legOffset); // Pravý přední roh
        glRotatef(90, 1, 0, 0); // Otočení nohy
        leg.render();
        glPopMatrix();

        glPushMatrix();
        glTranslatef(-1.f+legOffset, 1.2f, 1.f-legOffset); // Levý zadní roh
        glRotatef(90, 1, 0, 0); // Otočení nohy
        leg.render();
        glPopMatrix();

        glPushMatrix();
        glTranslatef(1.f-legOffset, 1.2f, 1.f-legOffset); // Pravý zadní roh
        glRotatef(90, 1, 0, 0); // Otočení nohy
        leg.render();
        glPopMatrix();

        // Posun a vykreslení knoflíků
        glPushMatrix();
        glTranslatef(-0.5f, 0.8f, 0.f); // pravý knoflík na přední straně
        glRotatef(180, 1, 0, 0);
        glRotatef(-(rightKnobAngle), 0, 0, 1);
        knob.render();
        glPopMatrix();

        glPushMatrix();
        glTranslatef(-0.8f, 0.8f, 0.f); // levý knoflík na přední straně
        glRotatef(180, 1, 0, 0);
        glRotatef(-(leftKnobAngle), 0, 0, 1);
        knob.render();
        glPopMatrix();

        diskAngle += (speed*angle);
        leftKnobAngle = (speed * 180);
        rightKnobAngle = (volume * 180);
    }

    public void onKeyPress(int key) {
        switch (key){
            case 265:
                //šipka nahoru
                if (volume<=1.9f) {
                    volume += 0.1f;
                }else if(volume>1.9f) {
                    volume = 2.f;
                }
                audioPlayer.setVolume(volume);
                break;
            case 264:
                //šipka dolů
                if (volume>=0.1f) {
                    volume -= 0.1f;
                }else if(volume<0.1f) {
                    volume = 0.f;
                }
                audioPlayer.setVolume(volume);
                break;
            case 263:
                //šipka doleva
                if (speed>=0.1f) {
                    speed -= 0.1f;
                }else if(speed<0.1f) {
                    speed = 0.f;
                }
                audioPlayer.setPlaybackSpeed(speed);
                break;
            case 262:
                //šipka doprava
                if (speed<=1.9f) {
                    speed += 0.1f;
                }else if(speed>1.9f) {
                    speed = 2.f;
                }
                audioPlayer.setPlaybackSpeed(speed);
                break;
        }
        System.out.println("Key pressed in GramophoneScene: " + key);
    }

    public void setAudioPlayer(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }
}

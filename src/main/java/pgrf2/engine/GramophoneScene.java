package pgrf2.engine;

import pgrf2.models.Knob;
import pgrf2.models.Disk;
import pgrf2.models.GramophoneBase;
import pgrf2.models.Leg;
import static org.lwjgl.opengl.GL11.*;

public class GramophoneScene {
    private GramophoneBase base;
    private Disk disk;
    private Leg leg;
    private Knob knob;

    public GramophoneScene(GramophoneBase base, Disk disk, Leg leg, Knob knob) {
        this.base = base;
        this.disk = disk;
        this.leg = leg;
        this.knob = knob;
    }

    public void render() {
        base.render();

        // Posun a vykreslení disku
        glPushMatrix();
        glScalef(1.5f, 1.5f, 1.5f);
        glTranslatef(0.4f, 0.7f, 0.4f); // Posun disku na vrchní stranu gramofonu
        glRotatef(90, 1, 0, 0); // Otočení disku pro správnou orientaci
        disk.render();
        glPopMatrix();

        // Posun a vykreslení nohou
        float legOffset = 0.1f; // Odstup nohou od okrajů
        glPushMatrix();
        glTranslatef(-0.9f, 0.0f, 0.1f); // Levý přední roh
        glRotatef(270, 1, 0, 0); // Otočení nohy
        leg.render();
        glPopMatrix();

        glPushMatrix();
        glTranslatef(0.9f, 0.0f, 0.1f); // Pravý přední roh
        glRotatef(270, 1, 0, 0); // Otočení nohy
        leg.render();
        glPopMatrix();

        glPushMatrix();
        glTranslatef(-0.9f, 0.0f, 0.9f); // Levý zadní roh
        glRotatef(270, 1, 0, 0); // Otočení nohy
        leg.render();
        glPopMatrix();

        glPushMatrix();
        glTranslatef(0.9f, 0.0f, 0.9f); // Pravý zadní roh
        glRotatef(270, 1, 0, 0); // Otočení nohy
        leg.render();
        glPopMatrix();

        // Posun a vykreslení knoflíků
        glPushMatrix();
        glTranslatef(1.0f, 0.3f, 0.05f); // První knoflík na přední straně
        glRotatef(90, 1, 0, 0);
        knob.render();
        glPopMatrix();

        glPushMatrix();
        glTranslatef(1.0f, 0.5f, 0.05f); // Druhý knoflík na přední straně
        glRotatef(90, 1, 0, 0);
        knob.render();
        glPopMatrix();
    }
}

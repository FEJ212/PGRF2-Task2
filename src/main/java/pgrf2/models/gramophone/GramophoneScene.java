package pgrf2.models.gramophone;

import pgrf2.models.gramophone.parts.Knob;
import pgrf2.models.gramophone.parts.Disk;
import pgrf2.models.gramophone.parts.GramophoneBase;
import pgrf2.models.gramophone.parts.Leg;
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
        glTranslatef(0.4f, 0.12f, 0.4f); // Posun disku na vrchní stranu gramofonu
        glRotatef(90, 1, 0, 0); // Otočení disku pro správnou orientaci
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
        glTranslatef(-0.5f, 0.8f, 0.f); // První knoflík na přední straně
        glRotatef(180, 1, 0, 0);
        knob.render();
        glPopMatrix();

        glPushMatrix();
        glTranslatef(-0.8f, 0.8f, 0.f); // Druhý knoflík na přední straně
        glRotatef(180, 1, 0, 0);
        knob.render();
        glPopMatrix();
    }
}

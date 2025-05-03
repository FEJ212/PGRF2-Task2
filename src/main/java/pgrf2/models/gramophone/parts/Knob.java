package pgrf2.models.gramophone.parts;

import pgrf2.lwjglutils.OGLTexture2D;
import static org.lwjgl.opengl.GL11.*;

public class Knob {
    private final OGLTexture2D mainTexture, detailTexture;

    public Knob(OGLTexture2D mainTexture, OGLTexture2D detailTexture) {
        this.mainTexture = mainTexture;
        this.detailTexture = detailTexture;
    }

    public void render() {
        mainTexture.bind();
        setMaterial();
        generateKnob(0.1f, 0.09f, 0.1f, 0.02f);
        drawTopCircle(0.1f, 0.1f); // Draw the top circle to close the knob
        detailTexture.bind();
        drawWhiteLine(0.01f, 0.1f, 0.02f); // Draw the white line indicator
    }

    private void setMaterial() {
        float[] materialAmbient = {0.1f, 0.1f, 0.1f, 1.0f};
        float[] materialDiffuse = {0.3f, 0.3f, 0.3f, 1.0f};
        float[] materialSpecular = {0.6f, 0.6f, 0.6f, 1.0f};
        float materialShininess = 50.f;

        glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, materialAmbient);
        glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, materialDiffuse);
        glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, materialSpecular);
        glMateriali(GL_FRONT_AND_BACK, GL_SHININESS, (int) materialShininess);
    }

    private void generateKnob(float bottomRadius, float topRadius, float height, float notchDepth) {
        int slices = 32;
        float angleStep = (float) (Math.PI * 2 / slices);

        // Generate the side of the knob with ridges
        glBegin(GL_QUAD_STRIP);
        for (int i = 0; i <= slices; i++) {
            float angle = i * angleStep;

            float xBottom = bottomRadius * (float) Math.cos(angle);
            float yBottom = bottomRadius * (float) Math.sin(angle);
            float xTop = topRadius * (float) Math.cos(angle);
            float yTop = topRadius * (float) Math.sin(angle);

            glVertex3f(xBottom, yBottom, 0);
            glVertex3f(xTop, yTop, height);

            // Add ridges
            if (i % 2 == 0) {
                glVertex3f(xBottom * 1.1f, yBottom * 1.1f, 0);
                glVertex3f(xTop * 1.1f, yTop * 1.1f, height);
            }
        }
        glEnd();
    }

    private void drawTopCircle(float radius, float height) {
        int slices = 32;
        float angleStep = (float) (Math.PI * 2 / slices);

        glBegin(GL_TRIANGLE_FAN);
        glVertex3f(0, 0, height);
        for (int i = 0; i <= slices; i++) {
            float angle = i * angleStep;

            float x = radius * (float) Math.cos(angle);
            float y = radius * (float) Math.sin(angle);

            glVertex3f(x, y, height);
        }
        glEnd();
    }

    private void drawWhiteLine(float width, float height, float depth) {
        float length = 0.1f; // Délka od středu k okraji
        float offset = 0.01f; // Posunutí kvádru nad vršek drawTopCircle

        glBegin(GL_QUADS);

        // Dolní strana kvádru
        glVertex3f(-width / 2, 0, 0.1f - depth + offset); // Levý dolní roh
        glVertex3f(width / 2, 0, 0.1f - depth + offset);  // Pravý dolní roh
        glVertex3f(width / 2, length, 0.1f - depth + offset); // Pravý horní roh
        glVertex3f(-width / 2, length, 0.1f - depth + offset); // Levý horní roh

        // Horní strana kvádru
        glVertex3f(-width / 2, 0, 0.1f + offset); // Levý dolní roh
        glVertex3f(width / 2, 0, 0.1f + offset);  // Pravý dolní roh
        glVertex3f(width / 2, length, 0.1f + offset); // Pravý horní roh
        glVertex3f(-width / 2, length, 0.1f + offset); // Levý horní roh

        // Levá strana kvádru
        glVertex3f(-width / 2, 0, 0.1f - depth + offset); // Levý dolní roh
        glVertex3f(-width / 2, 0, 0.1f + offset); // Levý dolní roh
        glVertex3f(-width / 2, length, 0.1f + offset); // Levý horní roh
        glVertex3f(-width / 2, length, 0.1f - depth + offset); // Levý horní roh

        // Pravá strana kvádru
        glVertex3f(width / 2, 0, 0.1f - depth + offset); // Pravý dolní roh
        glVertex3f(width / 2, 0, 0.1f + offset); // Pravý dolní roh
        glVertex3f(width / 2, length, 0.1f + offset); // Pravý horní roh
        glVertex3f(width / 2, length, 0.1f - depth + offset); // Pravý horní roh

        glEnd();
    }
}

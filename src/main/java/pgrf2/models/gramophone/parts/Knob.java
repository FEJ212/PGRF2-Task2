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
        generateKnob(0.1f, 0.09f, 0.1f);
        drawTopCircle(0.1f, 0.1f);
        detailTexture.bind();
        drawWhiteLine(0.0f, 0.0f, 0.1f, 0.01f, 0.02f);
    }

    private void setMaterial() {
        float[] materialAmbient = {0.2f, 0.2f, 0.2f, 1.0f};
        float[] materialDiffuse = {0.4f, 0.4f, 0.4f, 1.0f};
        float[] materialSpecular = {0.1f, 0.1f, 0.1f, 1.0f};
        float materialShininess = 10.0f;

        glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, materialAmbient);
        glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, materialDiffuse);
        glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, materialSpecular);
        glMateriali(GL_FRONT_AND_BACK, GL_SHININESS, (int) materialShininess);
    }

    private void generateKnob(float bottomRadius, float topRadius, float height) {
        int slices = 32;
        float angleStep = (float) (Math.PI * 2 / slices);

        glBegin(GL_QUAD_STRIP);
        for (int i = 0; i <= slices; i++) {
            float angle = i * angleStep;

            float xBottom = bottomRadius * (float) Math.cos(angle);
            float yBottom = bottomRadius * (float) Math.sin(angle);
            float xTop = topRadius * (float) Math.cos(angle);
            float yTop = topRadius * (float) Math.sin(angle);

            float texCoordX = (float) i / slices;

            glNormal3f(xBottom, yBottom, 0);
            glTexCoord2f(texCoordX, 0.0f);
            glVertex3f(xBottom, yBottom, 0);
            glNormal3f(xTop, yTop, 0);
            glTexCoord2f(texCoordX, 1.0f);
            glVertex3f(xTop, yTop, height);

            if (i % 2 == 0) {
                glNormal3f(xBottom * 1.1f, yBottom * 1.1f, 0);
                glTexCoord2f(texCoordX, 0.0f);
                glVertex3f(xBottom * 1.1f, yBottom * 1.1f, 0);
                glNormal3f(xTop * 1.1f, yTop * 1.1f, 0);
                glTexCoord2f(texCoordX, 1.0f);
                glVertex3f(xTop * 1.1f, yTop * 1.1f, height);
            }
        }
        glEnd();
    }

    private void drawTopCircle(float radius, float height) {
        int slices = 32;
        float angleStep = (float) (Math.PI * 2 / slices);

        glBegin(GL_TRIANGLE_FAN);
        glNormal3f(0, 0, 1);
        glTexCoord2f(0.5f, 0.5f);
        glVertex3f(0, 0, height);
        for (int i = 0; i <= slices; i++) {
            float angle = i * angleStep;

            float x = radius * (float) Math.cos(angle);
            float y = radius * (float) Math.sin(angle);

            glTexCoord2f((x / radius + 1) / 2, (y / radius + 1) / 2);
            glVertex3f(x, y, height);
        }
        glEnd();
    }

    private void drawWhiteLine(float centerX, float centerY, float radius, float height, float depth) {
        float length = radius;
        float width = radius * 0.1f;

        glBegin(GL_QUADS);

        glNormal3f(0, 0, -1);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(centerX - width / 2, centerY, 0.1f - depth + height);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(centerX + width / 2, centerY, 0.1f - depth + height);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(centerX + width / 2, centerY + length, 0.1f - depth + height);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(centerX - width / 2, centerY + length, 0.1f - depth + height);

        glNormal3f(0, 0, 1);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(centerX - width / 2, centerY, 0.1f + height);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(centerX + width / 2, centerY, 0.1f + height);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(centerX + width / 2, centerY + length, 0.1f + height);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(centerX - width / 2, centerY + length, 0.1f + height);

        glNormal3f(-1, 0, 0);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(centerX - width / 2, centerY, 0.1f - depth + height);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(centerX - width / 2, centerY, 0.1f + height);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(centerX - width / 2, centerY + length, 0.1f + height);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(centerX - width / 2, centerY + length, 0.1f - depth + height);

        glNormal3f(1, 0, 0);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(centerX + width / 2, centerY, 0.1f - depth + height);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(centerX + width / 2, centerY, 0.1f + height);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(centerX + width / 2, centerY + length, 0.1f + height);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(centerX + width / 2, centerY + length, 0.1f - depth + height);

        glEnd();
    }
}

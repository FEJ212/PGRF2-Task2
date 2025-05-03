package pgrf2.models;

import pgrf2.lwjglutils.OGLTexture2D;
import static org.lwjgl.opengl.GL11.*;

public class Disk {
    private final OGLTexture2D texture;

    public Disk(OGLTexture2D texture) {
        this.texture = texture;
    }

    public void render() {
        texture.bind();
        setMaterialVinyl();
        drawDiskWithHole(0.5f, 0.05f, 0.0f, 0.1f);
    }

    private void setMaterialVinyl() {
        float[] materialAmbient = {0.05f, 0.05f, 0.05f, 1.0f};
        float[] materialDiffuse = {0.1f, 0.1f, 0.1f, 1.0f};
        float[] materialSpecular = {0.5f, 0.5f, 0.5f, 1.0f};
        float materialShininess = 100.0f;

        glMaterialfv(GL_FRONT, GL_AMBIENT, materialAmbient);
        glMaterialfv(GL_FRONT, GL_DIFFUSE, materialDiffuse);
        glMaterialfv(GL_FRONT, GL_SPECULAR, materialSpecular);
        glMaterialf(GL_FRONT, GL_SHININESS, materialShininess);
    }

    private void drawDiskWithHole(float outerRadius, float innerRadius, float zStart, float zEnd) {
        int numSegments = 100;
        float angleStep = (float) (2.0f * Math.PI / numSegments);
        glBegin(GL_TRIANGLE_FAN);
        for (int i = 0; i <= numSegments; ++i) {
            float angle = i * angleStep;
            float x = outerRadius * (float) Math.cos(angle);
            float y = outerRadius * (float) Math.sin(angle);
            glTexCoord2f((x + 1) / 2, (y + 1) / 2);
            glVertex3f(x, y, zStart);
        }
        glEnd();
        glBegin(GL_TRIANGLE_FAN);
        for (int i = 0; i <= numSegments; ++i) {
            float angle = i * angleStep;
            float x = innerRadius * (float) Math.cos(angle);
            float y = innerRadius * (float) Math.sin(angle);
            glTexCoord2f((x + 1) / 2, (y + 1) / 2);
            glVertex3f(x, y, zEnd);
        }
        glEnd();
    }
}

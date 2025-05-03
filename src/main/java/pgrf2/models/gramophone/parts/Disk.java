package pgrf2.models.gramophone.parts;

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
        drawDisk(0.5f, 0.0f);
    }

    private void setMaterialVinyl() {
        float[] materialAmbient = {0.05f, 0.05f, 0.05f, 1.0f};
        float[] materialDiffuse = {0.1f, 0.1f, 0.1f, 1.0f};
        float[] materialSpecular = {0.9f, 0.9f, 0.9f, 1.0f}; // Increased specular reflection
        float materialShininess = 128.0f; // Higher shininess value for a more glossy effect

        glMaterialfv(GL_FRONT, GL_AMBIENT, materialAmbient);
        glMaterialfv(GL_FRONT, GL_DIFFUSE, materialDiffuse);
        glMaterialfv(GL_FRONT, GL_SPECULAR, materialSpecular);
        glMaterialf(GL_FRONT, GL_SHININESS, materialShininess);
    }


    private void drawDisk(float outerRadius, float zStart) {
        int numSegments = 100;
        float angleStep = (float) (2.0f * Math.PI / numSegments);

        glBegin(GL_TRIANGLE_FAN);
        for (int i = 0; i <= numSegments; ++i) {
            float angle = i * angleStep;
            float x = outerRadius * (float) Math.cos(angle);
            float y = outerRadius * (float) Math.sin(angle);
            glTexCoord2f((x + 1) / 2, (y + 1) / 2);
            glNormal3f(0.0f, 0.0f, 1.0f); // Normála pro horní stranu disku
            glVertex3f(x, y, zStart);
        }
        glEnd();
    }

}

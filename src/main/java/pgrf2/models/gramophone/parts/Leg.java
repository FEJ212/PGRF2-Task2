package pgrf2.models.gramophone.parts;

import pgrf2.lwjglutils.OGLTexture2D;
import static org.lwjgl.opengl.GL11.*;

public class Leg {
    private final OGLTexture2D texture;

    public Leg(OGLTexture2D texture) {
        this.texture = texture;
    }

    public void render() {
        texture.bind();
        setMaterialRubber();
        drawLeg(0.05f, 0.0425f, 0.2f);
        drawBottomCircle(0.0425f);
    }

    private void setMaterialRubber() {
        float[] materialAmbient = {0.02f, 0.02f, 0.02f, 1.0f};
        float[] materialDiffuse = {0.05f, 0.05f, 0.05f, 1.0f};
        float[] materialSpecular = {0.1f, 0.1f, 0.1f, 1.0f};
        float materialShininess = 10.0f;

        glMaterialfv(GL_FRONT, GL_AMBIENT, materialAmbient);
        glMaterialfv(GL_FRONT, GL_DIFFUSE, materialDiffuse);
        glMaterialfv(GL_FRONT, GL_SPECULAR, materialSpecular);
        glMaterialf(GL_FRONT, GL_SHININESS, materialShininess);
    }

    private void drawLeg(float topRadius, float bottomRadius, float height) {
        int numSegments = 100;
        float angleStep = (float) (2.0f * Math.PI / numSegments);
        glBegin(GL_TRIANGLE_STRIP);
        for (int i = 0; i <= numSegments; ++i) {
            float angle = i * angleStep;
            float xTop = topRadius * (float) Math.cos(angle);
            float yTop = topRadius * (float) Math.sin(angle);
            float xBottom = bottomRadius * (float) Math.cos(angle);
            float yBottom = bottomRadius * (float) Math.sin(angle);
            float texCoordX = (float) i / numSegments;
            glNormal3f(xTop, yTop, 0);
            glTexCoord2f(texCoordX, 1.0f);
            glVertex3f(xTop, yTop, height);
            glNormal3f(xBottom, yBottom, 0);
            glTexCoord2f(texCoordX, 0.0f);
            glVertex3f(xBottom, yBottom, 0.0f);
        }
        glEnd();
    }

    private void drawBottomCircle(float radius) {
        int numSegments = 100;
        float angleStep = (float) (2.0f * Math.PI / numSegments);
        glBegin(GL_TRIANGLE_FAN);
        glNormal3f(0, 0, -1);
        glTexCoord2f(0.5f, 0.5f);
        glVertex3f(0.0f, 0.0f, 0.0f);
        for (int i = 0; i <= numSegments; ++i) {
            float angle = i * angleStep;
            float x = radius * (float) Math.cos(angle);
            float y = radius * (float) Math.sin(angle);
            glTexCoord2f((x / radius + 1) / 2, (y / radius + 1) / 2);
            glVertex3f(x, y, 0.0f);
        }
        glEnd();
    }
}

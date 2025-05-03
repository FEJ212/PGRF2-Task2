package pgrf2.models;

import pgrf2.lwjglutils.OGLTexture2D;
import static org.lwjgl.opengl.GL11.*;

public class Leg {
    private final OGLTexture2D texture;

    public Leg(OGLTexture2D texture) {
        this.texture = texture;
    }

    public void render() {
        texture.bind();
        drawLeg(0.05f, 0.0425f, 0.2f);
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

            glVertex3f(xTop, yTop, height);
            glVertex3f(xBottom, yBottom, 0.0f);
        }
        glEnd();
    }
}

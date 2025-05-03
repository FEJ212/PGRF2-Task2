package pgrf2.models;

import pgrf2.lwjglutils.OGLTexture2D;
import static org.lwjgl.opengl.GL11.*;

public class Knob {
    private final OGLTexture2D texture;

    public Knob(OGLTexture2D texture) {
        this.texture = texture;
    }

    public void render() {
        texture.bind();
        generateKnob(0.1f, 0.05f, 0.02f);
    }

    private void generateKnob(float radius, float height, float notchDepth) {
        int numSegments = 100;
        int numNotches = 20;
        float angleStep = (float) (2.0f * Math.PI / numSegments);
        float notchAngleStep = (float) (2.0f * Math.PI / numNotches);

        glBegin(GL_TRIANGLE_STRIP);
        for (int i = 0; i <= numSegments; ++i) {
            float angle = i * angleStep;
            float x = radius * (float) Math.cos(angle);
            float y = radius * (float) Math.sin(angle);

            glVertex3f(x, y, height);
            glVertex3f(x, y, 0.0f);
        }
        glEnd();

        glBegin(GL_TRIANGLE_STRIP);
        for (int i = 0; i <= numNotches; ++i) {
            float angle = i * notchAngleStep;
            float xOuter = radius * (float) Math.cos(angle);
            float yOuter = radius * (float) Math.sin(angle);
            float xInner = (radius - notchDepth) * (float) Math.cos(angle);
            float yInner = (radius - notchDepth) * (float) Math.sin(angle);

            glVertex3f(xOuter, yOuter, height);
            glVertex3f(xInner, yInner, height);
            glVertex3f(xOuter, yOuter, 0.0f);
            glVertex3f(xInner, yInner, 0.0f);
        }
        glEnd();
    }
}

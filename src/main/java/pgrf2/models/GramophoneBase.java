package pgrf2.models;

import static org.lwjgl.opengl.GL33.*;
import pgrf2.lwjglutils.OGLTexture2D;

public class GramophoneBase {
    private final OGLTexture2D texture; // Textura dřeva na většinu báze

    public GramophoneBase(OGLTexture2D texture) {
        this.texture = texture;
    }

    public void render() {
        texture.bind();
        setMaterialWood();
        generateGramophoneBase();
    }

    private void setMaterialWood() {
        float[] materialAmbient = {0.2f, 0.1f, 0.05f, 1.0f};
        float[] materialDiffuse = {0.6f, 0.3f, 0.1f, 1.0f};
        float[] materialSpecular = {0.2f, 0.2f, 0.2f, 1.0f};
        float materialShininess = 30.0f;

        glMaterialfv(GL_FRONT, GL_AMBIENT, materialAmbient);
        glMaterialfv(GL_FRONT, GL_DIFFUSE, materialDiffuse);
        glMaterialfv(GL_FRONT, GL_SPECULAR, materialSpecular);
        glMaterialf(GL_FRONT, GL_SHININESS, materialShininess);
    }

    private void generateGramophoneBase() {
        glBegin(GL_TRIANGLE_FAN);
        glVertex3f(-1, 0.2f, 0);
        glVertex3f(1, 0.2f, 0);
        glVertex3f(1, 0.2f, 1);
        glVertex3f(-1, 0.2f, 1);
        glVertex3f(1, 1, 1);
        glVertex3f(-1, 1, 0);
        glVertex3f(1, 1, 0);
        glVertex3f(1, 0.2f, 0);
        glEnd();
        glBegin(GL_TRIANGLE_FAN);
        glVertex3f(1, 1, 1);
        glVertex3f(1, 0.2f, 1);
        glVertex3f(-1, 0.2f, 1);
        glVertex3f(-1, 1, 1);
        glVertex3f(-1, 1, 0);
        glVertex3f(1, 1, 0);
        glVertex3f(1, 0.2f, 0);
        glVertex3f(1, 0.2f, 1);
        glEnd();
    }
}

package pgrf2.models.other;

import pgrf2.lwjglutils.OGLTexture2D;
import static org.lwjgl.opengl.GL11.*;

public class SkyBox {
    private final OGLTexture2D texture;

    public SkyBox(OGLTexture2D texture) {
        this.texture = texture;
    }

    public void render() {
        texture.bind();
        float size = 50.0f;

        glBegin(GL_QUADS);

        // Přední strana
        glTexCoord2f(0.5f, 0.33f); glVertex3f(-size, -size, size);
        glTexCoord2f(0.25f, 0.33f); glVertex3f(size, -size, size);
        glTexCoord2f(0.25f, 0.66f); glVertex3f(size, size, size);
        glTexCoord2f(0.5f, 0.66f); glVertex3f(-size, size, size);

        // Zadní strana
        glTexCoord2f(1.0f, 0.33f); glVertex3f(-size, -size, -size);
        glTexCoord2f(0.75f, 0.33f); glVertex3f(size, -size, -size);
        glTexCoord2f(0.75f, 0.66f); glVertex3f(size, size, -size);
        glTexCoord2f(1.0f, 0.66f); glVertex3f(-size, size, -size);

        // Vrchní strana
        glTexCoord2f(0.5f, 0.0f); glVertex3f(-size, -size, -size);
        glTexCoord2f(0.25f, 0.0f); glVertex3f(size, -size, -size);
        glTexCoord2f(0.25f, 0.33f); glVertex3f(size, -size, size);
        glTexCoord2f(0.5f, 0.33f); glVertex3f(-size, -size, size);

        // Spodní strana
        glTexCoord2f(0.5f, 0.66f); glVertex3f(-size, size, -size);
        glTexCoord2f(0.25f, 0.66f); glVertex3f(size, size, -size);
        glTexCoord2f(0.25f, 1.0f); glVertex3f(size, size, size);
        glTexCoord2f(0.5f, 1.0f); glVertex3f(-size, size, size);

        // Pravá strana
        glTexCoord2f(0.75f, 0.33f); glVertex3f(size, -size, -size);
        glTexCoord2f(0.5f, 0.33f); glVertex3f(size, -size, size);
        glTexCoord2f(0.5f, 0.66f); glVertex3f(size, size, size);
        glTexCoord2f(0.75f, 0.66f); glVertex3f(size, size, -size);

        // Levá strana
        glTexCoord2f(0.25f, 0.33f); glVertex3f(-size, -size, -size);
        glTexCoord2f(0.0f, 0.33f); glVertex3f(-size, -size, size);
        glTexCoord2f(0.0f, 0.66f); glVertex3f(-size, size, size);
        glTexCoord2f(0.25f, 0.66f); glVertex3f(-size, size, -size);

        glEnd();
    }
}

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
        float size = 50.0f; // Zvětšení skyboxu

        glBegin(GL_QUADS);

        // Front face
        glTexCoord2f(0.5f, 0.33f); glVertex3f(-size, -size, size);
        glTexCoord2f(0.25f, 0.33f); glVertex3f(size, -size, size);
        glTexCoord2f(0.25f, 0.66f); glVertex3f(size, size, size);
        glTexCoord2f(0.5f, 0.66f); glVertex3f(-size, size, size);

        // Back face
        glTexCoord2f(1.0f, 0.33f); glVertex3f(-size, -size, -size);
        glTexCoord2f(0.75f, 0.33f); glVertex3f(size, -size, -size);
        glTexCoord2f(0.75f, 0.66f); glVertex3f(size, size, -size);
        glTexCoord2f(1.0f, 0.66f); glVertex3f(-size, size, -size);

        // Top face
        glTexCoord2f(0.5f, 0.0f); glVertex3f(-size, size, -size);
        glTexCoord2f(0.25f, 0.0f); glVertex3f(size, size, -size);
        glTexCoord2f(0.25f, 0.33f); glVertex3f(size, size, size);
        glTexCoord2f(0.5f, 0.33f); glVertex3f(-size, size, size);

        // Bottom face
        glTexCoord2f(0.5f, 0.66f); glVertex3f(-size, -size, -size);
        glTexCoord2f(0.25f, 0.66f); glVertex3f(size, -size, -size);
        glTexCoord2f(0.25f, 1.0f); glVertex3f(size, -size, size);
        glTexCoord2f(0.5f, 1.0f); glVertex3f(-size, -size, size);

        // Right face
        glTexCoord2f(0.75f, 0.33f); glVertex3f(size, -size, -size);
        glTexCoord2f(0.5f, 0.33f); glVertex3f(size, -size, size);
        glTexCoord2f(0.5f, 0.66f); glVertex3f(size, size, size);
        glTexCoord2f(0.75f, 0.66f); glVertex3f(size, size, -size);

        // Left face
        glTexCoord2f(0.25f, 0.33f); glVertex3f(-size, -size, -size);
        glTexCoord2f(0.0f, 0.33f); glVertex3f(-size, -size, size);
        glTexCoord2f(0.0f, 0.66f); glVertex3f(-size, size, size);
        glTexCoord2f(0.25f, 0.66f); glVertex3f(-size, size, -size);

        glEnd();
    }
}

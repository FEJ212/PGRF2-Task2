package pgrf2.models;

import static org.lwjgl.opengl.GL33.*;
import pgrf2.lwjglutils.OGLTexture2D;

public class GramophoneBase {
    private final OGLTexture2D texture; //Textura dřeva na většinu báze
    private final OGLTexture2D detailTexture; //Kovová textura pro detaily
    //konstruktor
    public GramophoneBase(OGLTexture2D texture, OGLTexture2D detailTexture) {
        this.texture = texture;
        this.detailTexture = detailTexture;
    }
    //renderovací metoda
    public void render() {
        generateGramophoneBase();
    }
    //vykreslení objektu pomocí vertexů
    private void generateGramophoneBase() {
        texture.bind();
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

        texture.bind();
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

        // Přidání jemných detailů
        detailTexture.bind();
        glBegin(GL_LINES);
        // Horní hrana
        glVertex3f(-1, 1, 0);
        glVertex3f(1, 1, 0);
        glVertex3f(1, 1, 1);
        glVertex3f(-1, 1, 1);
        // Spodní hrana
        glVertex3f(-1, 0.2f, 0);
        glVertex3f(1, 0.2f, 0);
        glVertex3f(1, 0.2f, 1);
        glVertex3f(-1, 0.2f, 1);
        glEnd();

        detailTexture.bind();
        glBegin(GL_TRIANGLE_FAN);
        // Přední strana s knoflíky
        glVertex3f(1, 0.2f, 0);
        glVertex3f(1, 0.2f, 0.2f);
        glVertex3f(1, 0.4f, 0.2f);
        glVertex3f(1, 0.4f, 0);
        glEnd();
    }
}

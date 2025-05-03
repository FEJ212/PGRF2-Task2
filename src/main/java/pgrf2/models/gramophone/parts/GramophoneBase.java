package pgrf2.models.gramophone.parts;

import static org.lwjgl.opengl.GL33.*;
import pgrf2.lwjglutils.OGLTexture2D;

public class GramophoneBase {
    private final OGLTexture2D mainTexture, detailTexture; // Textura dřeva na většinu báze

    public GramophoneBase(OGLTexture2D mainTexture, OGLTexture2D detailTexture) {
        this.mainTexture = mainTexture;
        this.detailTexture = detailTexture;
    }

    public void render() {
        generateBody();
    }

    private void setMaterialWood() {
        float[] materialAmbient = {0.3f, 0.2f, 0.1f, 1.0f}; // Vyšší ambientní složka
        float[] materialDiffuse = {0.7f, 0.5f, 0.3f, 1.0f}; // Vyšší difuzní složka
        float[] materialSpecular = {0.4f, 0.4f, 0.4f, 1.0f}; // Vyšší speculární složka pro lesklý vzhled
        float materialShininess = 50.0f; // Vyšší lesk

        glMaterialfv(GL_FRONT, GL_AMBIENT, materialAmbient);
        glMaterialfv(GL_FRONT, GL_DIFFUSE, materialDiffuse);
        glMaterialfv(GL_FRONT, GL_SPECULAR, materialSpecular);
        glMaterialf(GL_FRONT, GL_SHININESS, materialShininess);
    }

    private void setMaterialMetal() {
        float[] materialAmbient = {0.3f, 0.3f, 0.3f, 1.0f}; // Vyšší ambientní složka
        float[] materialDiffuse = {0.6f, 0.6f, 0.6f, 1.0f}; // Vyšší difuzní složka
        float[] materialSpecular = {1.0f, 1.0f, 1.0f, 1.0f}; // Vysoká speculární složka pro lesklý vzhled
        float materialShininess = 100.0f; // Vysoký lesk

        glMaterialfv(GL_FRONT, GL_AMBIENT, materialAmbient);
        glMaterialfv(GL_FRONT, GL_DIFFUSE, materialDiffuse);
        glMaterialfv(GL_FRONT, GL_SPECULAR, materialSpecular);
        glMaterialf(GL_FRONT, GL_SHININESS, materialShininess);
    }

    private void generateBody() {
        setMaterialWood();
        mainTexture.bind();

        // Vykreslení základního těla gramofonu
        glBegin(GL_QUADS);

        // Přední strana
        glNormal3f(0, 0, 1);
        glTexCoord2f(0, 0); glVertex3f(-1, 0.2f, 0);
        glTexCoord2f(1, 0); glVertex3f(1, 0.2f, 0);
        glTexCoord2f(1, 1); glVertex3f(1, 1, 0);
        glTexCoord2f(0, 1); glVertex3f(-1, 1, 0);

        // Zadní strana
        glNormal3f(0, 0, -1);
        glTexCoord2f(0, 0); glVertex3f(-1, 0.2f, 1);
        glTexCoord2f(1, 0); glVertex3f(1, 0.2f, 1);
        glTexCoord2f(1, 1); glVertex3f(1, 1, 1);
        glTexCoord2f(0, 1); glVertex3f(-1, 1, 1);

        // Levá strana
        glNormal3f(-1, 0, 0);
        glTexCoord2f(0, 0); glVertex3f(-1, 0.2f, 0);
        glTexCoord2f(1, 0); glVertex3f(-1, 0.2f, 1);
        glTexCoord2f(1, 1); glVertex3f(-1, 1, 1);
        glTexCoord2f(0, 1); glVertex3f(-1, 1, 0);

        // Pravá strana
        glNormal3f(1, 0, 0);
        glTexCoord2f(0, 0); glVertex3f(1, 0.2f, 0);
        glTexCoord2f(1, 0); glVertex3f(1, 0.2f, 1);
        glTexCoord2f(1, 1); glVertex3f(1, 1, 1);
        glTexCoord2f(0, 1); glVertex3f(1, 1, 0);

        // Spodní strana
        glNormal3f(0, -1, 0);
        glTexCoord2f(0, 0); glVertex3f(-1, 0.2f, 0);
        glTexCoord2f(1, 0); glVertex3f(1, 0.2f, 0);
        glTexCoord2f(1, 1); glVertex3f(1, 0.2f, 1);
        glTexCoord2f(0, 1); glVertex3f(-1, 0.2f, 1);

        // Horní strana
        glNormal3f(0, 1, 0);
        glTexCoord2f(0, 0); glVertex3f(-1, 1, 0);
        glTexCoord2f(1, 0); glVertex3f(1, 1, 0);
        glTexCoord2f(1, 1); glVertex3f(1, 1, 1);
        glTexCoord2f(0, 1); glVertex3f(-1, 1, 1);

        glEnd();
    }

    private void generateDetail(){
        setMaterialMetal();
        detailTexture.bind();
        glBegin(GL_QUADS);
    }
}

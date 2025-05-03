package pgrf2.engine;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import pgrf2.lwjglutils.OGLTexture2D;
import pgrf2.models.Knob;
import pgrf2.models.Disk;
import pgrf2.models.GramophoneBase;
import pgrf2.models.Leg;
import pgrf2.transforms.Vec3D;

import static org.lwjgl.opengl.GL11.*;
import static pgrf2.engine.utils.GluUtils.gluLookAt;
import static pgrf2.engine.utils.GluUtils.gluPerspective;

public class Renderer extends AbstractRenderer {
    private OGLTexture2D textureBase, textureDisk, textureLeg, textureKnob, textureDetail;
    private Camera camera;
    private GramophoneScene gramophoneScene;
    private SkyBox skyBox;
    private int angle;
    private double lastX, lastY;

    public Renderer() {
        super();

        glfwWindowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                if (w > 0 && h > 0) {
                    width = w;
                    height = h;
                }
            }
        };

        /*used default glfwKeyCallback */

        glfwMouseButtonCallback = null; //glfwMouseButtonCallback do nothing

        glfwScrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double dx, double dy) {
                //do nothing
            }
        };

        glfwCursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                double deltaX = xpos - lastX;
                double deltaY = ypos - lastY;
                lastX = xpos;
                lastY = ypos;

                camera.addAzimuth(Math.toRadians(deltaX * 0.1));
                camera.addZenith(Math.toRadians(-deltaY * 0.1));
            }
        };
    }

    @Override
    public void init() {
        super.init();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        camera = new Camera();
        camera.setCentre(new Vec3D(0, 0.5, 0.5)); // Nastavení středu kamery na gramofon
        camera.setRadius(5.0); // Nastavení konstantní vzdálenosti od gramofonu
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D); // Povolení textur
        glDisable(GL_CULL_FACE);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        try {
            textureBase = new OGLTexture2D("textures/4K-anzem_basecolor.png");
            textureDisk = new OGLTexture2D("textures/vinyl_gpt.png");
            textureLeg = new OGLTexture2D("textures/Poliigon_PlasticMoldDryBlast_7495_BaseColor.jpg");
            textureKnob = new OGLTexture2D("textures/MateBlackMetal027_4K_Color.jpg");
            textureDetail = new OGLTexture2D("textures/SilverMetal010_4K_Color.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        gramophoneScene = new GramophoneScene(new GramophoneBase(textureBase, textureDetail), new Disk(textureDisk), new Leg(textureLeg), new Knob(textureKnob));
    }

    @Override
    public void display() {
        glViewport(0, 0, width, height);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Přidáno vymazání obrazovky

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(45, width / (float) height, 0.1f, 200.0f);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        camera.setMatrix(); // Nastavení kamery

        glPushMatrix();
        gramophoneScene.render();
        glPopMatrix();
    }
}

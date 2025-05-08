package pgrf2.engine;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import pgrf2.lwjglutils.OGLTexture2D;
import pgrf2.models.gramophone.parts.Knob;
import pgrf2.models.gramophone.parts.Disk;
import pgrf2.models.gramophone.parts.GramophoneBase;
import pgrf2.models.gramophone.parts.Leg;
import pgrf2.models.gramophone.GramophoneScene;
import pgrf2.models.other.SkyBox;
import pgrf2.transforms.Vec3D;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static pgrf2.engine.utils.GluUtils.gluPerspective;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

public class Renderer extends AbstractRenderer {
    private OGLTexture2D textureBase, textureDisk, textureLeg, textureKnob, textureDetail, textureSkyBox;
    private Camera camera;
    private GramophoneScene gramophoneScene;
    private SkyBox skyBox;
    private double lastX, lastY;
    private AmbientLight ambientLight;
    private DirectionalLight directionalLight;
    private AudioPlayer audioPlayer;

    public Renderer() {
        super();
        glfwWindowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                if (w > 0 && h > 0) {
                    width = w;
                    height = h;
                    glViewport(0, 0, width, height);
                }
            }
        };
        glfwMouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                // Handle mouse button events
            }
        };
        glfwScrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double dx, double dy) {
                // Handle scroll events
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
                Vec3D lightDirection = new Vec3D(
                        Math.sin(camera.getAzimuth()) * Math.cos(camera.getZenith()),
                        Math.sin(camera.getZenith()),
                        -(Math.cos(camera.getAzimuth()) * Math.cos(camera.getZenith()))
                );
                directionalLight.setDirection(lightDirection);
                directionalLight.setPosition(camera.getPosition());
                audioPlayer = new AudioPlayer();
            }
        };
    }

    @Override
    public void init() {
        super.init();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        camera = new Camera();
        camera.setCentre(new Vec3D(0, 0.5, 0.5));
        camera.setRadius(5.0);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_CULL_FACE);
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glEnable(GL_COLOR_MATERIAL);
        glEnable(GL_NORMALIZE);
        glfwWindowHint(GLFW_SAMPLES, 4);
        glEnable(GL_MULTISAMPLE);
        glColor3f(1.0f, 1.0f, 1.0f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        try {
            textureBase = new OGLTexture2D("textures/4K-anzem_basecolor.png");
            textureDisk = new OGLTexture2D("textures/vinyl_gpt.png");
            textureLeg = new OGLTexture2D("textures/Poliigon_PlasticMoldDryBlast_7495_BaseColor.jpg");
            textureKnob = new OGLTexture2D("textures/MateBlackMetal027_4K_Color.jpg");
            textureDetail = new OGLTexture2D("textures/SilverMetal010_4K_Color.jpg");
            textureSkyBox = new OGLTexture2D("textures/skybox_gpt.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        audioPlayer = new AudioPlayer();
        audioPlayer.playMusic("music/SampleMusic.mp3");
        gramophoneScene = new GramophoneScene(new GramophoneBase(textureBase, textureDetail), new Disk(textureDisk), new Leg(textureLeg), new Knob(textureKnob, textureDetail), audioPlayer);
        skyBox = new SkyBox(textureSkyBox); // Vytvoření instance třídy SkyBox
        ambientLight = new AmbientLight(0.6f, 0.6f, 0.6f);
        directionalLight = new DirectionalLight(new Vec3D(0, 0, -1), 1.0f, 1.0f, 1.0f);
    }


    @Override
    public void display() {
        glViewport(0, 0, width, height);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(45, width / (float) height, 0.1f, 200.0f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        camera.setMatrix();
        ambientLight.apply();
        directionalLight.apply(GL_LIGHT0);
        glPushMatrix();
        skyBox.render();
        gramophoneScene.render();
        glPopMatrix();
    }

    @Override
    public GramophoneScene getGramophoneScene() {
        return gramophoneScene;
    }
}

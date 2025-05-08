package pgrf2.engine;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.opengl.GL11;

public class UIPanel {
    private int width;
    private int height;
    private boolean isMouseOver;

    public UIPanel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void init() {
        //Prostor pro initializi
    }

    public void render() {
        // Render the UI panel
        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        // Render elementů
    }

    public boolean isMouseOver(double xpos, double ypos) {
        return ypos < height;
    }

    public GLFWKeyCallback getKeyCallback() {
        return new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                // Handle key events for the UI panel
            }
        };
    }

    public GLFWMouseButtonCallback getMouseButtonCallback() {
        return new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                // Handle mouse button events for the UI panel
            }
        };
    }

    public GLFWCursorPosCallback getCursorPosCallback() {
        return new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                isMouseOver = isMouseOver(xpos, ypos);
                // Handle cursor position events for the UI panel
            }
        };
    }
}

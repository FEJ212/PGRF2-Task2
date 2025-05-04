package pgrf2.engine;

import org.lwjgl.glfw.GLFWKeyCallback;
import pgrf2.models.gramophone.GramophoneScene;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class Controller extends GLFWKeyCallback {
    private GramophoneScene gramophoneScene;

    public Controller(GramophoneScene gramophoneScene) {
        this.gramophoneScene = gramophoneScene;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            gramophoneScene.onKeyPress(key);
        }
    }
}

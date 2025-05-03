package pgrf2.engine;

import static org.lwjgl.opengl.GL11.*;

public class AmbientLight {
    private float[] color;

    public AmbientLight(float r, float g, float b) {
        color = new float[]{r, g, b, 1.0f};
    }

    public void apply() {
        glLightModelfv(GL_LIGHT_MODEL_AMBIENT, color);
    }
}

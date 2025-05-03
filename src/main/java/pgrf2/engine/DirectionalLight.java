package pgrf2.engine;

import pgrf2.transforms.Vec3D;
import static org.lwjgl.opengl.GL11.*;

public class DirectionalLight {
    private Vec3D direction;
    private Vec3D position;
    private float[] color;

    public DirectionalLight(Vec3D direction, float r, float g, float b) {
        this.direction = direction;
        this.position = new Vec3D(0, 0, 0); // Výchozí pozice světla
        color = new float[]{r, g, b, 1.0f};
    }

    public void apply(int lightId) {
        glEnable(lightId);
        glLightfv(lightId, GL_DIFFUSE, color);
        glLightfv(lightId, GL_SPECULAR, color);
        glLightfv(lightId, GL_POSITION, new float[]{(float) position.getX(), (float) position.getY(), (float) position.getZ(), 1.0f});
        glLightfv(lightId, GL_SPOT_DIRECTION, new float[]{(float) direction.getX(), (float) direction.getY(), (float) direction.getZ(), 0.0f});
    }

    public void setDirection(Vec3D direction) {
        this.direction = direction;
    }

    public void setPosition(Vec3D position) {
        this.position = position;
    }

    public void setIntensity(float intensity) {
        color[0] *= intensity;
        color[1] *= intensity;
        color[2] *= intensity;
    }
}

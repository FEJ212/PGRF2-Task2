package pgrf2.engine;

import pgrf2.transforms.Vec3D;

import java.util.Locale;

import static pgrf2.engine.utils.GluUtils.gluLookAt;

public class Camera {

    private double azimuth, radius, zenith;

    private boolean valid;

    private Vec3D eye, eye_vector, up, centre;

    private void compute_matrix() {
        eye_vector = new Vec3D(
                Math.sin(azimuth) * Math.cos(zenith),
                Math.sin(zenith),
                -(Math.cos(azimuth) * Math.cos(zenith))
        );
        up = new Vec3D(
                Math.sin(azimuth) * Math.cos(zenith + Math.PI / 2),
                Math.sin(zenith + Math.PI / 2),
                -(Math.cos(azimuth) * Math.cos(zenith + Math.PI / 2))
        );
        eye = centre.add(eye_vector.mul(radius));
        valid = true;
    }

    public Camera() {
        azimuth = zenith = 0.0f;
        radius = 5.0f;
        centre = new Vec3D(0.0f, 0.0f, 0.0f);
        valid = false;
    }

    public Camera(Camera camera) {
        azimuth = camera.getAzimuth();
        zenith = camera.getZenith();
        radius = camera.getRadius();
        centre = new Vec3D(camera.getCentre());
        valid = false;
    }

    public void addAzimuth(double ang) {
        azimuth += ang*5;
        valid = false;
    }

    public void addRadius(double dist) {
        if (radius + dist < 0.1f)
            return;
        radius += dist;
        valid = false;
    }

    public void mulRadius(double scale) {
        if (radius * scale < 0.1f)
            return;
        radius *= scale;
        valid = false;
    }

    public void addZenith(double ang) {
        //if (Math.abs(zenith + ang) <= Math.PI / 2) {
            zenith += ang*5;
            valid = false;
        //}
    }

    public void setAzimuth(double ang) {
        azimuth = ang;
        valid = false;
    }

    public double getAzimuth() {
        return azimuth;
    }

    public void setRadius(double dist) {
        radius = dist;
        valid = false;
    }

    public void setZenith(double ang) {
        zenith = ang;
        valid = false;
    }

    public double getZenith() {
        return zenith;
    }

    public double getRadius() {
        return radius;
    }

    public void setCentre(Vec3D centre) {
        this.centre = centre;
        valid = false;
    }

    public Vec3D getCentre() {
        return centre;
    }

    public void setMatrix() {
        if (!valid)
            compute_matrix();
        gluLookAt(
                eye.getX(), eye.getY(), eye.getZ(),
                centre.getX(), centre.getY(), centre.getZ(),
                up.getX(), up.getY(), up.getZ()
        );
    }

    public String toString(final String format) {
        return String.format(Locale.US,
                "Camera()\n" +
                        ".withCentre(new Vec3D"+ getCentre().toString(format) + ")\n" +
                        ".withAzimuth("+ format + ")\n" +
                        ".withZenith("+ format + ")\n" +
                        ".withRadius("+ format + ")",
                getAzimuth(), getZenith(), getRadius()
        );
    }

    public String toString() {
        return toString("%4.2f");
    }
}

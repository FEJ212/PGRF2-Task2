package pgrf2;

import pgrf2.engine.Renderer;
import pgrf2.engine.Window;

public class Main {
    public static void main(String[] args) {
        new Window(new Renderer(), false);
    }
}
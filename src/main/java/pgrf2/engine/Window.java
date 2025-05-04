package pgrf2.engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.MemoryStack;
import pgrf2.models.gramophone.GramophoneScene;

import java.nio.IntBuffer;
import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    public static int WIDTH = 600;
    public static int HEIGHT = 400;

    // The window handle
    private long window;
    private AbstractRenderer renderer;
    private UIPanel uiPanel;

    private static boolean DEBUG = false;

    static {
        if (DEBUG) {
            System.setProperty("org.lwjgl.util.Debug", "true");
            System.setProperty("org.lwjgl.util.NoChecks", "false");
            System.setProperty("org.lwjgl.util.DebugLoader", "true");
            System.setProperty("org.lwjgl.util.DebugAllocator", "true");
            System.setProperty("org.lwjgl.util.DebugStack", "true");
            Configuration.DEBUG_MEMORY_ALLOCATOR.set(true);
        }
    }

    public Window(AbstractRenderer renderer) {
        this(WIDTH, HEIGHT, renderer, false);
    }

    public Window(AbstractRenderer renderer, boolean debug) {
        this(WIDTH, HEIGHT, renderer, debug);
    }

    public Window(int width, int height, AbstractRenderer renderer, boolean debug) {
        this.renderer = renderer;
        DEBUG = debug;
        WIDTH = width;
        HEIGHT = height;
        uiPanel = new UIPanel(width, 50); // Example height for the UI panel
        if (DEBUG)
            System.err.println("Run in debugging mode");
        run();
    }

    public void run() {
        init();

        loop();

        renderer.dispose();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        String text = renderer.getClass().getName();
        text = text.substring(0, text.lastIndexOf('.'));
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, text, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (uiPanel.isMouseOver(glfwGetCursorPosX(window), glfwGetCursorPosY(window))) {
                uiPanel.getKeyCallback().invoke(window, key, scancode, action, mods);
            } else {
                renderer.getGlfwKeyCallback().invoke(window, key, scancode, action, mods);
            }
        });

        glfwSetWindowSizeCallback(window, renderer.getGlfwWindowSizeCallback());
        glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            if (uiPanel.isMouseOver(glfwGetCursorPosX(window), glfwGetCursorPosY(window))) {
                uiPanel.getMouseButtonCallback().invoke(window, button, action, mods);
            } else {
                renderer.getGlfwMouseButtonCallback().invoke(window, button, action, mods);
            }
        });

        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
            uiPanel.getCursorPosCallback().invoke(window, xpos, ypos);
            if (!uiPanel.isMouseOver(xpos, ypos)) {
                renderer.getGlfwCursorPosCallback().invoke(window, xpos, ypos);
            }
        });

        glfwSetScrollCallback(window, renderer.getGlfwScrollCallback());

        if (DEBUG)
            glfwSetErrorCallback(new GLFWErrorCallback() {
                GLFWErrorCallback delegate = GLFWErrorCallback.createPrint(System.err);

                @Override
                public void invoke(int error, long description) {
                    if (error == GLFW_VERSION_UNAVAILABLE)
                        System.err.println("GLFW_VERSION_UNAVAILABLE: This demo requires OpenGL 2.0 or higher.");
                    if (error == GLFW_NOT_INITIALIZED)
                        System.err.println("GLFW_NOT_INITIALIZED");
                    if (error == GLFW_NO_CURRENT_CONTEXT)
                        System.err.println("GLFW_NO_CURRENT_CONTEXT");
                    if (error == GLFW_INVALID_ENUM)
                        System.err.println("GLFW_INVALID_ENUM");
                    if (error == GLFW_INVALID_VALUE)
                        System.err.println("GLFW_INVALID_VALUE");
                    if (error == GLFW_OUT_OF_MEMORY)
                        System.err.println("GLFW_OUT_OF_MEMORY");
                    if (error == GLFW_API_UNAVAILABLE)
                        System.err.println("GLFW_API_UNAVAILABLE");
                    if (error == GLFW_VERSION_UNAVAILABLE)
                        System.err.println("GLFW_VERSION_UNAVAILABLE");
                    if (error == GLFW_PLATFORM_ERROR)
                        System.err.println("GLFW_PLATFORM_ERROR");
                    if (error == GLFW_FORMAT_UNAVAILABLE)
                        System.err.println("GLFW_FORMAT_UNAVAILABLE");

                    delegate.invoke(error, description);
                }

                @Override
                public void free() {
                    delegate.free();
                }

            });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        // Initialize the UI panel
        uiPanel.init();
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW
        // creates the GLCapabilities instance and makes the OpenGL bindings available for use.
        GL.createCapabilities();

        if (DEBUG)
            GLUtil.setupDebugMessageCallback();

        renderer.init();

        // Nastavení KeyCallback
        GramophoneScene gramophoneScene = renderer.getGramophoneScene();
        if (gramophoneScene != null) {
            GLFWKeyCallback keyCallback = new Controller(gramophoneScene);
            glfwSetKeyCallback(window, keyCallback);
        } else {
            System.err.println("GramophoneScene is null. KeyCallback not set.");
        }

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();
            // Render the UI panel
            uiPanel.render();

            // Render the main content
            renderer.display();

            glfwSwapBuffers(window); // swap the color buffers
        }
    }


    private double glfwGetCursorPosX(long window) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            DoubleBuffer xpos = stack.mallocDouble(1);
            DoubleBuffer ypos = stack.mallocDouble(1);
            glfwGetCursorPos(window, xpos, ypos);
            return xpos.get(0);
        }
    }

    private double glfwGetCursorPosY(long window) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            DoubleBuffer xpos = stack.mallocDouble(1);
            DoubleBuffer ypos = stack.mallocDouble(1);
            glfwGetCursorPos(window, xpos, ypos);
            return ypos.get(0);
        }
    }
}

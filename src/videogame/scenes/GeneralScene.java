package videogame.scenes;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashSet;
import java.util.Set;

public abstract class GeneralScene extends Scene {
    public static final int GAME_WIDTH = 816;
    public static final int GAME_HEIGHT = 480;

    private StackPane root = new StackPane();
    protected GraphicsContext gc;
    protected Set<KeyCode> activeKeys;
    protected Set<KeyCode> releaseKeys;
    protected MediaPlayer mediaPlayer;
    protected Media sound;

    public GeneralScene() {
        // Call to Scene constructor to initialize it
        super(new StackPane(), GAME_WIDTH, GAME_HEIGHT);

        //Change scene's root to our own stack pane
        root = new StackPane();
        this.setRoot(root);

        //Initialize canvas and graphic context
        Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();

        //Initialize set of currently pressed and released keys
        activeKeys = new HashSet<>();
        releaseKeys = new HashSet<>();
        this.setOnKeyPressed(e -> {
            activeKeys.add(e.getCode());
        });
        this.setOnKeyReleased(e -> {
            activeKeys.remove(e.getCode());
            releaseKeys.add(e.getCode());
        });
    }

    public abstract void draw();
}

package videogame.scenes;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import videogame.BearFruitChallenge;
import videogame.sprites.MainCharacter;

import java.nio.file.Files;
import java.nio.file.Paths;

public class GameScene extends GeneralScene {
    private static final String BACKGROUND_IMAGE = "assets/background.png";

    private Image background;
    private MainCharacter bear;

    public GameScene() {

        super();
        try {
            background = new Image(Files.newInputStream(Paths.get(BACKGROUND_IMAGE)));
            bear = new MainCharacter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw() {
        activeKeys.clear();
        bear.moveTo(380,375);
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // Black background
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

                gc.drawImage(background, 0,0);
                bear.draw(gc);

                if (activeKeys.contains(KeyCode.ESCAPE)) {
                    this.stop();
                    BearFruitChallenge.setScene(BearFruitChallenge.WELCOME_SCENE);
                } else if (activeKeys.contains(KeyCode.ENTER)) {
                    this.stop();
                    BearFruitChallenge.setScene(BearFruitChallenge.CREDITS_SCENE);
                } else if (activeKeys.contains(KeyCode.LEFT)) {
                    bear.move(MainCharacter.LEFT);
                } else if (activeKeys.contains(KeyCode.RIGHT)) {
                    bear.move(MainCharacter.RIGHT);
                }
            }
        }.start();
    }

}

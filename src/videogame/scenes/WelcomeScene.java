package videogame.scenes;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import videogame.BearFruitChallenge;

public class WelcomeScene extends GeneralScene {
    public WelcomeScene() {
        super();
        showWelcomeMessage();
    }

    private void showWelcomeMessage() {
        Font myFont = Font.font("Arial", FontWeight.NORMAL, 32);
        gc.setFont(myFont);
        gc.setFill(Color.RED);
        gc.fillText("Bear Fruit Challenge", 275, 200);

        myFont = Font.font("Arial", FontWeight.NORMAL, 20);
        gc.setFont(myFont);
        gc.setFill(Color.WHITE);
        gc.fillText("Press SPACEBAR to play", 325, 275);
    }

    @Override
    public void draw() {
        activeKeys.clear();
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // Black background
                gc.setFill((Color.BLACK));
                gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

                showWelcomeMessage();

                if (activeKeys.contains(KeyCode.SPACE)) {
                    this.stop();
                    BearFruitChallenge.setScene(BearFruitChallenge.GAME_SCENE);
                } else if (activeKeys.contains(KeyCode.ESCAPE)) {
                    this.stop();
                    BearFruitChallenge.exit();
                }
            }
        }.start();
    }
}

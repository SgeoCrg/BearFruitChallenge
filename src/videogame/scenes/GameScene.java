package videogame.scenes;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import videogame.BearFruitChallenge;
import videogame.sprites.Fruit;
import videogame.sprites.MainCharacter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameScene extends GeneralScene {
    private static final String BACKGROUND_IMAGE = "assets/background.png";
    public static final String BACKGROUND_SONG = "assets/autumn-leaves.wav";
    public static final String SOUND_EFFECT = "assets/quick-jump.wav";

    private Image background;
    private MainCharacter bear;
    private Fruit fruit = null;
    private MediaPlayer mediaPlayerEffects;
    private Media effect;
    public static int points = 0;
    private int lives = 3;

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
        reset();

        sound = new Media(new File(BACKGROUND_SONG).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        activeKeys.clear();
        bear.moveTo(380,375);
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // Black background
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

                gc.drawImage(background, 0,0);
                bear.draw(gc);
                if(fruit != null) {
                    fruit.draw(gc);
                }
                updateHUD();

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
                } else {
                    bear.move(MainCharacter.CENTER);
                }

                //Generate or move fruit
                if (fruit == null) {
                    fruit = new Fruit();
                    fruit.moveTo((int)(Math.random() * (GeneralScene.GAME_WIDTH - Fruit.FRUIT_WIDTH)), 0);
                } else {
                    fruit.move();
                    if(fruit.collidesWith(bear)) {
                        points += 100;
                        fruit.incrementDifficulty();
                        playEffects(SOUND_EFFECT);
                        fruit = null;
                    } else if (fruit.getY() > GeneralScene.GAME_HEIGHT) {
                        lives --;
                        fruit = null;
                    }
                }
                if (lives == 0) {
                    this.stop();
                    mediaPlayer.stop();
                    BearFruitChallenge.setScene(BearFruitChallenge.CREDITS_SCENE);
                }
            }
        }.start();
    }

    private void playEffects(String path) {
        effect = new Media(new File(path).toURI().toString());
        mediaPlayerEffects = new MediaPlayer(effect);
        mediaPlayerEffects.play();
    }

    private void reset() {
        bear.resetPosition();
        lives = 3;
        points = 0;
        Fruit.STEP_INCREMENT = 0f;
    }

    private void updateHUD() {
        Font myFont = Font.font("Arial", FontWeight.NORMAL, 10);
        gc.setFont(myFont);
        gc.setFill(Color.BLUE);
        gc.fillText("Score: " + points, 20, GeneralScene.GAME_HEIGHT - 15);

        gc.setFill(Color.YELLOW);
        gc.fillText("Lives: " + lives, GeneralScene.GAME_WIDTH - 100, GeneralScene.GAME_HEIGHT - 15);
    }

}

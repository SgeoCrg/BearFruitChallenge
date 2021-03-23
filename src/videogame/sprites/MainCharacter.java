package videogame.sprites;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;

public class MainCharacter extends AnimatedSprite {
    public static final int MAIN_CHARACTER_WIDTH = 96;
    public static final int MAIN_CHARACTER_HEIGHT = 96;
    private static final String IMAGE_PATH = "assets/bear.png";

    public MainCharacter() {
        super(MAIN_CHARACTER_WIDTH, MAIN_CHARACTER_HEIGHT);
        try {
            spriteImage = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        spriteXCoordinates[RIGHT] = new int[] {480, 576, 672, 576 };
        spriteYCoordinates[RIGHT] = new int[] {0, 0, 0, 0};
        spriteXCoordinates[LEFT] = new int[] {1248, 1344, 1440, 1344 };
        spriteYCoordinates[LEFT] = new int[] {0, 0, 0, 0};

        updateSpriteCoordinates();
    }

    public void move(int movement) {
        int newX = x;
        if (movement == LEFT) {
            newX -= 1;
        } else if (movement == RIGHT) {
            newX += 1;
        }
        moveTo(newX, y);
        animate(movement);
    }
}

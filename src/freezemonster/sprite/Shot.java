package freezemonster.sprite;

import freezemonster.Commons;
import spriteframework.sprite.BadSprite;

import javax.swing.*;
import java.awt.*;


public class Shot extends BadSprite {

    // ADDED: Fields to store the shot's autonomous movement vector
    private int shotDx;
    private int shotDy;

    public Shot() {
        setVisible(false);
    }

    // ADDED: New constructor to capture the player's direction AT THE MOMENT OF FIRING
    public Shot(int x, int y, int playerDx, int playerDy) {

        initShot(x, y);

        // Set shot speed (e.g., 2x player speed) and lock its direction.
        // This fixes Requirement 3, as the direction is now set only once upon creation.
        this.shotDx = playerDx * 2;
        this.shotDy = playerDy * 2;
    }

    private void initShot(int x, int y) {

        String shotImg = "images/ray.png";
        ImageIcon ii = new ImageIcon(shotImg);
        setImage(ii.getImage().getScaledInstance(Commons.SHOT_WIDTH, Commons.SHOT_HEIGHT, Image.SCALE_SMOOTH));

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }

    // ADDED: The act() method override.
    // This allows the board to polymorphically update the shot just like any other sprite,
    // and the shot itself handles its own movement logic (Fixes SRP).
    @Override
    public void act() {
        this.x += shotDx;
        this.y += shotDy;
    }

    @Override
    public int getMonsterImageIndice() {
        return 0;
    }
}
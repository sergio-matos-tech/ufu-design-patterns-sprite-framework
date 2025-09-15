package freezemonster.sprite;

import freezemonster.Commons;
import spriteframework.sprite.BadSprite;

import javax.swing.*;
import java.awt.*;


public class Shot extends BadSprite {

    private int shotDx;
    private int shotDy;

    public Shot() {
        setVisible(false);
    }

    public Shot(int x, int y, int playerDx, int playerDy) {

        initShot(x, y);

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
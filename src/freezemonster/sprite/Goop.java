package freezemonster.sprite;

import freezemonster.Commons;
import spriteframework.sprite.BadSprite;

import javax.swing.*;
import java.awt.*;

public class Goop extends BadSprite {

    private boolean destroyed;
    private int direction = 0;

    public Goop(int x, int y) {

        initGoop(x, y);
    }

    private void initGoop(int x, int y) {

        setDestroyed(true);

        this.x = x;
        this.y = y;

        String goopImage = "images/goop.png";
        ImageIcon ii = new ImageIcon(goopImage);
        setImage(ii.getImage().getScaledInstance(Commons.GOOP_WIDTH, Commons.GOOP_HEIGHT, Image.SCALE_SMOOTH));
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public int getMonsterImageIndice() {
        return 0;
    }

    public int isDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

}

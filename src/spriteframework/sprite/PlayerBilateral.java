package spriteframework.sprite;

import spriteframework.Commons;

import java.awt.event.KeyEvent;

public class PlayerBilateral extends Player{

    public PlayerBilateral(String image) {
        super(image);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {

            dy = 2;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {

            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {

            dy = -0;
        }
    }
}

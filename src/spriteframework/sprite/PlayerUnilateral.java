package spriteframework.sprite;

import spriteframework.Commons;

import java.awt.event.KeyEvent;

public class PlayerUnilateral extends Player{

    public PlayerUnilateral(String image) {
        super(image);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
            dx = -2;

        if (key == KeyEvent.VK_RIGHT)
            dx = 2;
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
            dx = 0;

        if (key == KeyEvent.VK_RIGHT)
            dx = 0;

    }
}

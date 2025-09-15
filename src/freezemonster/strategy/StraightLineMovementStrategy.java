package freezemonster.strategy;

import spriteframework.sprite.Sprite;
import spriteframework.strategy.IMovementStrategy;

public class StraightLineMovementStrategy implements IMovementStrategy {

    @Override
    public void move(Sprite sprite) {
        // Read the direction that is stored on the sprite itself
        int direction = sprite.getMonsterDirection();

        // This is the 8-way movement logic, copied from FreezeMonsterBoard
        // and adapted to use the sprite's moveX/moveY methods.
        if (direction == 1) {
            sprite.moveY(-1); // Up
            sprite.moveX(-1); // Left
        }
        if (direction == 2) {
            sprite.moveY(1);  // Down
            sprite.moveX(-1); // Left
        }
        if (direction == 3) {
            sprite.moveY(-1); // Up
            sprite.moveX(1);  // Right
        }
        if (direction == 4) {
            sprite.moveY(1);  // Down
            sprite.moveX(1);  // Right
        }
        if (direction == 5) {
            sprite.moveX(-1); // Left
        }
        if (direction == 6) {
            sprite.moveY(-1); // Up
        }
        if (direction == 7) {
            sprite.moveX(1);  // Right
        }
        if (direction == 8) {
            sprite.moveY(1);  // Down
        }
    }
}
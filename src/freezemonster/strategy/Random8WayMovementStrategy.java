package freezemonster.strategy;

import spriteframework.Commons;
import spriteframework.sprite.Sprite;
import spriteframework.strategy.IMovementStrategy;

import java.util.Random;

public class Random8WayMovementStrategy implements IMovementStrategy {

    @Override
    public void move(Sprite monster) {
        int x = monster.getX();
        int y = monster.getY();

        if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && !monster.isDyingVisible()) {
            int temp = new Random().nextInt(3);
            if(temp == 0) { monster.setMonsterDirection(2); }
            if(temp == 1) { monster.setMonsterDirection(7); }
            if(temp == 2) { monster.setMonsterDirection(3); }
        }
        if (x <= Commons.BORDER_LEFT && !monster.isDyingVisible()) {
            int temp = new Random().nextInt(3);
            if(temp == 0) { monster.setMonsterDirection(4); }
            if(temp == 1) { monster.setMonsterDirection(5); }
            if(temp == 2) { monster.setMonsterDirection(1); }
        }
        if (y >= Commons.BOARD_HEIGHT - 50 && !monster.isDyingVisible()) {
            int temp = new Random().nextInt(3);
            if(temp == 0) { monster.setMonsterDirection(2); }
            if(temp == 1) { monster.setMonsterDirection(8); }
            if(temp == 2) { monster.setMonsterDirection(4); }
        }
        if (y <= 0 && !monster.isDyingVisible()) {
            int temp = new Random().nextInt(3);
            if(temp == 0) { monster.setMonsterDirection(3); }
            if(temp == 1) { monster.setMonsterDirection(6); }
            if(temp == 2) { monster.setMonsterDirection(1); }
        }

        // This is the second loop logic, also from FreezeMonsterBoard
        if (monster.getMonsterDirection() == 0) {
            monster.setMonsterDirection(new Random().nextInt(8) + 1);
        }
        if (!monster.isDyingVisible()) {
            if (monster.getMonsterDirection() == 1) { monster.moveX(1); monster.moveY(1); }
            if (monster.getMonsterDirection() == 2) { monster.moveX(-1); monster.moveY(-1); }
            if (monster.getMonsterDirection() == 3) { monster.moveX(-1); monster.moveY(1); }
            if (monster.getMonsterDirection() == 4) { monster.moveX(1); monster.moveY(-1); }
            if (monster.getMonsterDirection() == 5) { monster.moveX(1); }
            if (monster.getMonsterDirection() == 6) { monster.moveY(1); }
            if (monster.getMonsterDirection() == 7) { monster.moveX(-1); }
            if (monster.getMonsterDirection() == 8) { monster.moveY(-1); }
        }
    }
}
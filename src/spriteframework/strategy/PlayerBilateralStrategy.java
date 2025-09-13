package spriteframework.strategy;

import spriteframework.Commons;
import spriteframework.sprite.Player;
import spriteframework.sprite.Sprite;

public class PlayerBilateralStrategy implements IMovementStrategy {
    @Override
    public void move(Sprite sprite) {
        Player player = (Player) sprite;

        player.moveX(player.getDx());
        player.moveY(player.getDy());

        if (player.getX() <= 2) {
            player.setX(2);
        }
        if (player.getX() >= Commons.BOARD_WIDTH - 2 * player.getImageWidth()) {
            player.setX(Commons.BOARD_WIDTH - 2 * player.getImageWidth());
        }
        if (player.getY() >= Commons.BOARD_HEIGHT - 2 * player.getImageHeight()) {
            player.setY(Commons.BOARD_HEIGHT - 2 * player.getImageHeight());
        }
        if (player.getY() <= 2) {
            player.setY(2);
        }
    }
}
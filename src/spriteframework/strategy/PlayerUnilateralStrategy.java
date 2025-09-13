package spriteframework.strategy;

import spriteframework.Commons;
import spriteframework.sprite.Player;
import spriteframework.sprite.Sprite;

public class PlayerUnilateralStrategy implements IMovementStrategy {
    @Override
    public void move(Sprite sprite) {
        Player player = (Player) sprite;

        player.moveX(player.getDx());

        if (player.getX() <= 2)
            player.setX(2);

        if (player.getX() >= Commons.BOARD_WIDTH - 2 * player.getImageWidth())
            player.setX(Commons.BOARD_WIDTH - 2 * player.getImageWidth());

    }
}

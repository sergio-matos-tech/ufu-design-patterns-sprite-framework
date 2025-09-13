package freezemonster;


import freezemonster.strategy.Random8WayMovementStrategy;
import spriteframework.strategy.PlayerBilateralStrategy;


import freezemonster.sprite.Goop;
import freezemonster.sprite.MonsterSprite;
import freezemonster.sprite.Shot;
import spriteframework.AbstractBoard;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.PlayerBilateral;
import spriteframework.sprite.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class FreezeMonsterBoard extends AbstractBoard {

    private Shot shot;

    private int direction = -1;
    private int direc = 0;
    private int deaths = 0;


    public FreezeMonsterBoard(String image) {
        super(image);
    }

    /**
     * FACTORY METHOD (Player):
     * This method now injects the correct movement strategy into the player object.
     */
    @Override
    protected Player createPlayer(String player) {
        Player p = new PlayerBilateral(player);
        p.setMovementStrategy(new PlayerBilateralStrategy());
        return p;
    }

    /**
     * This method now injects the random movement strategy into each monster created.
     */
    protected void createBadSprites() {
        for (int i = 0; i <= Commons.NUMBER_OF_MONSTERS_TO_DESTROY; i++) {

            MonsterSprite monster = new MonsterSprite(Commons.MONSTER_INIT_X + 18 * (int) (Math.random() * 10),
                    Commons.MONSTER_INIT_Y + 18 * (int) (Math.random() * 10), i);

            monster.setMovementStrategy(new Random8WayMovementStrategy());

            badSprites.add(monster);
        }
    }

    protected void createOtherSprites() {
        shot = new Shot();
    }

    private void drawShot(Graphics g) {

        if (shot.isVisible()) {

            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    protected void drawOtherSprites(Graphics g) {
        drawShot(g);
    }

    protected void processOtherSprites(Player player, KeyEvent e) {
        int x = player.getX();
        int y = player.getY();

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {

            if (inGame) {

                if (!shot.isVisible()) {

                    shot = new Shot(x, y);
                }
            }
        }
    }

    /**
     * UPDATE METHOD - REFACTORED
     * All monster movement logic has been removed from this method and
     * delegated to the injected Strategy objects.
     */
    protected void update() {
        if (deaths == Commons.NUMBER_OF_MONSTERS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Congratulations, you have own the game!";
        }

        // This call now executes the injected PlayerBilateralStrategy
        for (Player player : players)
            player.act();

        // Player Shot logic (unchanged)
        if (shot.isVisible()) {

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (BadSprite monster : badSprites) {

                int monsterX = monster.getX();
                int monsterY = monster.getY();

                if (!monster.isDyingVisible() && shot.isVisible()) {
                    if (shotX >= (monsterX) && shotX <= (monsterX + Commons.MONSTER_WIDTH) && shotY >= (monsterY) && shotY <= (monsterY + Commons.MONSTER_HEIGHT)) {

                        ImageIcon ii = new ImageIcon("images/monster" + monster.getMonsterImageIndice() + "bg.png");
                        monster.setImage(ii.getImage().getScaledInstance(Commons.MONSTER_WIDTH, Commons.MONSTER_HEIGHT, Image.SCALE_DEFAULT));
                        monster.setDyingVisible(true);
                        monster.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }
            }

            int position_y = shot.getY();
            int position_x = shot.getX();

            if ((players.get(0).getDy() == -2 && players.get(0).getDx() == 0 && direc == 0) || direc == 1) {
                position_y -= 4;
                direc = 1;
            }
            if ((players.get(0).getDy() == 2 && players.get(0).getDx() == 0 && direc == 0) || direc == 2) {
                position_y += 4;
                direc = 2;
            }
            if ((players.get(0).getDy() == 0 && players.get(0).getDx() == 2 && direc == 0) || direc == 3) {
                position_x += 4;
                direc = 3;
            }
            if ((players.get(0).getDy() == 0 && players.get(0).getDx() == -2 && direc == 0) || direc == 4) {
                position_x -= 4;
                direc = 4;
            }

            if (position_y < 0 || position_y >= Commons.BOARD_HEIGHT || position_x < 0 || position_x >= Commons.BOARD_WIDTH) {
                shot.die();
                direc = 0;
            } else {
                shot.setY(position_y);
                shot.setX(position_x);
            }
        }

        for (BadSprite monster : badSprites) {
            monster.act();
        }
        // Goop logic is still handled here (potential next refactor!)
        updateOtherSprites();
    }

    protected void updateOtherSprites() {
        Random generator = new Random();

        for (BadSprite monster : badSprites) {

            int shot = generator.nextInt(15);
            Goop goop = ((MonsterSprite) monster).getGoop();

            if (goop.isDirection() == 0) {
                goop.setDirection(new Random().nextInt(8) + 1);
            }

            if (shot == Commons.CHANCE && monster.isVisible() && goop.isDestroyed()) {

                goop.setDestroyed(false);
                goop.setX(monster.getX());
                goop.setY(monster.getY());
            }

            int goopX = goop.getX();
            int goopY = goop.getY();

            int monsterX = monster.getX();
            int monsterY = monster.getY();

            int playerX = players.get(0).getX();
            int playerY = players.get(0).getY();

            if (players.get(0).isVisible() && !goop.isDestroyed() && !monster.isDestroyed()) {

                if (
                        ((goopX >= (playerX))
                                && (goopX <= (playerX + Commons.PLAYER_WIDTH))
                                && (goopY >= (playerY))
                                && (goopY <= (playerY + Commons.PLAYER_HEIGHT)))
                                || ((monsterX >= (playerX))
                                && (monsterX <= (playerX + Commons.PLAYER_WIDTH))
                                && (monsterY >= (playerY))
                                && (monsterY <= (playerY + Commons.PLAYER_HEIGHT)))
                ) {

                    String deadImage = "images/explosion.png";
                    ImageIcon ii = new ImageIcon(deadImage);
                    players.get(0).setImage(ii.getImage());
                    players.get(0).setDying(true);
                    goop.setDestroyed(true);
                }
            }

            if (!goop.isDestroyed()) {

                if (goop.isDirection() == 1) {
                    goop.setY(goop.getY() - 1);
                    goop.setX(goop.getX() - 1);
                }
                if (goop.isDirection() == 2) {
                    goop.setY(goop.getY() + 1);
                    goop.setX(goop.getX() - 1);
                }
                if (goop.isDirection() == 3) {
                    goop.setY(goop.getY() - 1);
                    goop.setX(goop.getX() + 1);
                }
                if (goop.isDirection() == 4) {
                    goop.setY(goop.getY() + 1);
                    goop.setX(goop.getX() + 1);
                }
                if (goop.isDirection() == 5) {
                    goop.setY(goop.getY());
                    goop.setX(goop.getX() - 1);
                }
                if (goop.isDirection() == 6) {
                    goop.setY(goop.getY() - 1);
                    goop.setX(goop.getX());
                }
                if (goop.isDirection() == 7) {
                    goop.setY(goop.getY());
                    goop.setX(goop.getX() + 1);
                }
                if (goop.isDirection() == 8) {
                    goop.setY(goop.getY() + 1);
                    goop.setX(goop.getX());
                }


                if (goop.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT || goop.getY() <= 0 || goop.getX() <= 0 || goop.getX() >= Commons.BOARD_WIDTH) {
                    goop.setDestroyed(true);
                }
            }
        }
    }

    /**
     * This method overrides the Template Method from AbstractBoard to provide
     * the specific look and feel for Freeze Monster.
     */
    @Override
    public void doDrawing(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(new Color(87, 161, 112)); // <-- Custom green background
        g.fillRect(0, 0, d.width, d.height);

        if (inGame) {

            // This game does not have the "ground" line from Space Invaders
            // g.drawLine(0, spriteframework.Commons.GROUND,
            //         spriteframework.Commons.BOARD_WIDTH, spriteframework.Commons.GROUND);

            drawBadSprites(g);
            drawBadSprite(g);
            drawPlayers(g);
            drawOtherSprites(g);

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    protected void drawBadSprites(Graphics g) {

        for (BadSprite bad : badSprites) {

            if (bad.isVisible()) {
                g.drawImage(bad.getImage(), bad.getX(), bad.getY(), this);
            }

            // THIS IS THE FIX:
            // The original method in AbstractBoard calls bad.die() if bad.isDying() is true.
            // For FreezeMonster, "dying" means "frozen," so we must NOT call bad.die().
            // We intentionally leave that logic out of this overridden method.

            if (bad.getBadnesses() != null) {
                for (BadSprite badness : bad.getBadnesses()) {
                    if (!badness.isDestroyed()) {
                        g.drawImage(badness.getImage(), badness.getX(), badness.getY(), this);
                    }
                }
            }
        }
    }
}
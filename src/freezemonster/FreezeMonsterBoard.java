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
    private int deaths = 0;

    private int lastPlayerDx = 0;
    private int lastPlayerDy = -2; // Default to firing "up" if player hasn't moved yet


    // --- CONCRETE CLASS PARA O TEMPLATE METHOD ---
    // FreezeMonsterBoard estende AbstractBoard e implementa os métodos abstratos
    // (hotspots) para definir a lógica específica do jogo Freeze Monster.
    public FreezeMonsterBoard(String image) {
        super(image);
    }


    /**
     * --- IMPLEMENTAÇÃO DO FACTORY METHOD ---
     * Sobrescreve createPlayer para instanciar um PlayerBilateral e injetar
     * a estratégia de movimento correta (PlayerBilateralStrategy), que permite
     * movimento em 8 direções.
     */
    @Override
    protected Player createPlayer(String player) {
        Player p = new PlayerBilateral(player);
        p.setMovementStrategy(new PlayerBilateralStrategy());
        return p;
    }


    // --- IMPLEMENTAÇÃO DO HOTSPOT: createBadSprites ---
    // Cria os monstros do jogo, cada um com sua própria estratégia
    // de movimento aleatório em 8 direções.
    protected void createBadSprites() {
        for (int i = 0; i < Commons.NUMBER_OF_MONSTERS_TO_DESTROY; i++) {

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
                    shot = new Shot(x, y, lastPlayerDx, lastPlayerDy);
                }
            }
        }
    }

    // --- IMPLEMENTAÇÃO DO HOTSPOT: update ---
    // Contém a lógica principal do jogo: verifica colisões,
    // atualiza posições dos sprites e o estado do jogo.
    protected void update() {
        if (deaths == Commons.NUMBER_OF_MONSTERS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Congratulations, you have own the game!";
        }

        for (Player player : players) {
            player.act();

            // This ensures the shot fires correctly even if the player stops before firing.
            if (player.getDx() != 0 || player.getDy() != 0) {
                lastPlayerDx = player.getDx();
                lastPlayerDy = player.getDy();
            }
        }

        if (shot.isVisible()) {

            shot.act();

            int shotX = shot.getX();
            int shotY = shot.getY();

            if (shotY < 0 || shotY >= Commons.BOARD_HEIGHT || shotX < 0 || shotX >= Commons.BOARD_WIDTH) {
                shot.die();
            }

            for (BadSprite monster : badSprites) {

                int monsterX = monster.getX();
                int monsterY = monster.getY();

                if (!monster.isDyingVisible() && shot.isVisible()) {
                    if (shotX >= (monsterX) && shotX <= (monsterX + Commons.MONSTER_WIDTH)
                            && shotY >= (monsterY) && shotY <= (monsterY + Commons.MONSTER_HEIGHT)) {

                        ImageIcon ii = new ImageIcon("images/monster" + monster.getMonsterImageIndice() + "bg.png");
                        monster.setImage(ii.getImage().getScaledInstance(Commons.MONSTER_WIDTH, Commons.MONSTER_HEIGHT, Image.SCALE_DEFAULT));
                        monster.setDyingVisible(true);
                        monster.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }

                Goop goop = ((MonsterSprite) monster).getGoop();
                if (!goop.isDestroyed() && shot.isVisible()) {
                    int goopX = goop.getX();
                    int goopY = goop.getY();

                    if (shotX >= (goopX) && shotX <= (goopX + Commons.GOOP_WIDTH) &&
                            shotY >= (goopY) && shotY <= (goopY + Commons.GOOP_HEIGHT)) {

                        shot.die(); // Shot dies
                        goop.setDestroyed(true); // Goop also dies
                    }
                }
            }
        }


        for (BadSprite monster : badSprites) {
            monster.act();
        }
        updateOtherSprites();
    }

    protected void updateOtherSprites() {
        Random generator = new Random();

        for (BadSprite monster : badSprites) {

            int shot = generator.nextInt(15);
            Goop goop = ((MonsterSprite) monster).getGoop();

            if (goop.getDirection() == 0) {
                goop.setDirection(new Random().nextInt(8) + 1);
            }

            if (shot == Commons.CHANCE && !monster.isDying() && goop.isDestroyed()) {

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

                goop.act();

                if (goop.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT || goop.getY() <= 0 || goop.getX() <= 0 || goop.getX() >= Commons.BOARD_WIDTH) {
                    goop.setDestroyed(true);
                }
            }
        }
    }

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
    protected void handleDyingSprite(BadSprite bad) {
        // Intentionally left empty. Do not call bad.die() when isDying() is true.
    }
}
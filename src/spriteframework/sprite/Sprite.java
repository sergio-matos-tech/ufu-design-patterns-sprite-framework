package spriteframework.sprite;

import spriteframework.strategy.IMovementStrategy;

import java.awt.*;

public class Sprite {

    private boolean visible;
    protected Image image;
    private boolean dying;
    private boolean dyingVisible;

    protected int x;
    protected int y;
    protected int imageWidth;
    protected int imageHeight;
    protected int dx;
    protected int dy;
    protected int monsterDirection = 0;

    // --- CONTEXTO DO STRATEGY PATTERN ---
    // A referência para a estratégia de movimento.
    // O Sprite (contexto) não sabe como o movimento é implementado,
    // ele apenas delega a execução para o objeto strategy.
    protected IMovementStrategy movementStrategy;

    public Sprite() {

        visible = true;
    }

    // --- INJEÇÃO DA ESTRATÉGIA ---
    // Este método permite que a estratégia de movimento seja configurada em tempo de execução.
    // Isso desacopla o Sprite do algoritmo de movimento específico.
    public void setMovementStrategy(IMovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    // --- DELEGAÇÃO PARA A ESTRATÉGIA ---
    // O método performMove() delega a lógica de movimento para o objeto
    // da estratégia, se uma tiver sido definida.
    public void performMove() {
        if (movementStrategy != null) {
            movementStrategy.move(this);
        }
    }

    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }



    public void setDyingVisible(boolean dying) {

        this.dyingVisible = dying;
    }

    public boolean isDyingVisible() {
        return this.dyingVisible;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }
    
    public Rectangle getRect() {
        return new Rectangle(x, y,
                image.getWidth(null), image.getHeight(null));
    }

    public void getImageDimensions() {
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }
    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public boolean isDying() {
        return this.dying;
    }
    
    public void moveX(int direction) {
        this.x += direction;
    }
    
    public void moveY(int direction) {
        this.y += direction;
    }

    public int getMonsterDirection() {
        return monsterDirection;
    }

    public void setMonsterDirection(int monsterDirection) {
        this.monsterDirection = monsterDirection;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}

package maxwell;


/**
 * Metodo que define la particula efimera
 * 
 * @author Daniel Patino & Daniel Useche
 * @version 2.0
 */
public class Ephemeral extends Particle{
    /**
     * Constructor de Ephemeral
     */
    public Ephemeral(String color, boolean isRed, int x, int y, int vx, int vy) throws MaxwellException{
        super(color, isRed, x, y, vx, vy);
    }
    
    @Override
    public void move(int dt, int width, int height) {
        for (int i = 0; i < dt; i++) {
            checkCollisions();
            x += vx;
            y += vy;
            smoothMove();
        }
    }
    
    @Override
    public void checkCollisions() {
        if (atWallX()) {
            handleWallXCollision();
        }
        if (atWallY()) {
            vy = -vy;
            if(vy > 0) vy -= 1;
            else if(vy < 0) vy +=1;
            
            y = clamp(y, 10, MaxwellContainer.h - 10);
        }
    }
    
    @Override
    public void handleWallXCollision() {
        if (isNearMiddle() && isWrongSide()) {
            if (!centerBlock()) return;
           }
        vx = -vx;
        if(vx > 0) vx -= 1;
        else if(vx < 0) vx +=1;
        x = clamp(x, 10, MaxwellContainer.w * 2 - 10);
    }
}

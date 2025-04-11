package maxwell;
import shapes.*;

/**
 * Contiene creacion y caracteristicas del agujero movil
 * 
 * @author Daniel Patino & Daniel Useche
 * @version 2.0
 */
public class Movil extends Hole implements ParticleBehavior{
    private int vx ; 
    private int vy;
    
    /**
     * Constructor for objects of class Movil
     */
    public Movil(int x, int y, int maxParticles) throws MaxwellException{
        super(x, y, maxParticles);
        this.vx = 1;
        this.vy = 1;
        
        if (isOutOfBounds()) {
            throw new MaxwellException(MaxwellException.OUTOFRANGE + " " + this);
        }
        setupCircle(10);
        makeVisible();
    }
    
    private boolean isOutOfBounds() {
        return x < 11 || x > MaxwellContainer.w * 2 + 10 || y < 11 || y > MaxwellContainer.h + 10;
    }
    
    private void setupCircle(int diameter) {
        circle.changeSize(diameter);
        circle.changeColor("black");
        circle.movetoX(x);
        circle.movetoY(y);
    }
    
    public void move(int dt, int width, int height) {
        for (int i = 0; i < dt; i++) {
            checkCollisions();
            x += vx;
            y += vy;
            smoothMove();
        }
    }
    
    public void smoothMove() {
        int newX = limitStep(circle.getX(), x, 5);
        int newY = limitStep(circle.getY(), y, 5);
        
        updateCircle(newX, newY);
    }
    
    private int limitStep(int current, int target, int step) {
        return Math.abs(current - target) > step ? current + Integer.signum(target - current) * step : target;
    }
    
    public void checkCollisions() {
        if (atWallX()) {
            handleWallXCollision();
        }
        if (atWallY()) {
            vy = -vy;
            y = clamp(y, 10, MaxwellContainer.h - 10);
        }
    }
    
    public void handleWallXCollision() {
        if (isNearMiddle()) {
            if (!centerBlock()) return;
           }
        vx = -vx;
        x = clamp(x, 10, MaxwellContainer.w * 2 - 10);
    }
    
    public boolean isNearMiddle() {
        return x >= MaxwellContainer.middle - 2 && x <= MaxwellContainer.middle + 2;
    }
    
    public boolean centerBlock() {
        return Math.abs(x - MaxwellContainer.middle) <= 9;
    }
    
    public boolean atWallX() {
        return x <= 10 || x >= MaxwellContainer.w * 2 - 11 || isNearMiddle();
    }
    
    public boolean atWallY() {
        return y <= 10 || y >= MaxwellContainer.h - 10;
    }
    
    public int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
    
    private void updateCircle() {
        updateCircle(x, y);
    }
    
    private void updateCircle(int newX, int newY) {
        if (MaxwellContainer.isVisible) {
            circle.makeInvisible();
        }
        circle.movetoX(newX);
        circle.movetoY(newY);
        if (MaxwellContainer.isVisible) {
            circle.makeVisible();
        }
    }
    @Override
    public boolean canAbsorb() {
        return absorbedParticleColors.size() <= maxParticles || this.atWallX() || this.atWallY();
    }
        
}

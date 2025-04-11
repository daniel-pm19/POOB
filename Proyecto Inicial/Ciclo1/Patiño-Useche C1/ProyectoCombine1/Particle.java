public class Particle {
    private int x, y; 
    private int vx, vy;
    private boolean isRed; 
    private Circle circle;
    private MaxwellContainer tablero;
    
    public Particle(String color, boolean isRed, int x, int y, int vx, int vy, int diameter,MaxwellContainer tablero) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.isRed = isRed;
        this.tablero = tablero;
        this.circle = new Circle();
        this.settings(x,y,diameter);
    }
    private void settings(int x, int y, int diameter){
        this.circle.changeSize(diameter);
        this.circle.movetoX(x);
        this.circle.movetoY(y);
        this.circle.changeColor(isRed ? "red" : "blue");
        this.circle.makeVisible();
    }
    public void move(int dt,int width, int height) {
        for(int i = 0;i < dt;i++){
            this.crash(width,height);
            x += vx;
            y += vy;
            this.softMove(width,height);
        }
    }
    public void softMove(int w,int h){
        this.circle.makeInvisible();
        this.circle.moveHorizontal(vx);

        this.circle.moveVertical(vy);
        this.circle.makeVisible();
    }
    public void crash(int width, int height){
        boolean crashedX = atWallX(width);
        boolean crashedY = atWallY(height);
        if(crashedX){
            this.vx = -vx;
        }
        if(crashedY){
            this.vy = -vy;
        }
    }
    public void demonAccess(){
        return tablero.demonAccess(x,y);
    }

    public boolean centerBlock() {
        int centerX = tablero.getCenterX();
        
        boolean nearDemon = Math.abs(x - centerX) <= circle.getRadius();
        if (nearDemon) {
            return !demonAccess(); 
        }
    
        // Caso en el que la partícula intenta cruzar de la caja izquierda a la derecha
        if ((!wheremI() && x + vx > centerX)) {
            x = centerX - circle.getRadius(); 
            updateCircle(x, y);
            return true; 
        }
        
        // Caso en el que la partícula intenta cruzar de la caja derecha a la izquierda
        if ((wheremI() && x + vx < centerX)) {
            x = centerX + circle.getRadius(); 
            updateCircle(x, y);
            return true; 
        }
        return false;
    }

    public boolean atWallX(int width) {
        return x <= circle.getRadius() + 5 || x >= width - circle.getRadius() - 5 || centerBlock();
     }
    public boolean atWallY(int height) {
        return y <= circle.getRadius() + 5 || y >= height - circle.getRadius() - 5;
    }

    public void atWallXDown() {
        if (x < circle.getRadius()+5) {
            x = circle.getRadius()+5;
            updateCircle(x, y);
        }
    }

    public void atWallXUp(int width) {
        if (x > width - circle.getRadius() - 5) {
            x = width - circle.getRadius() - 5;
            updateCircle(x, y);
        }
    }

    public void atWallYDown() {
        if (y < circle.getRadius() + 5) {
            y = circle.getRadius() + 5;
            updateCircle(x, y);
        }
    }

    public void atWallYUp(int height) {
        if (y > height - circle.getRadius() + 5) {
            y = height - circle.getRadius() + 5;
            updateCircle(x, y);
        }
    }
    private void updateCircle(int newX, int newY) {
        circle.makeInvisible();
        circle.movetoX(newX);
        circle.movetoY(newY);
        circle.makeVisible();
    }
    public boolean wheremI(){
        return x > tablero.getCenterX() + circle.getRadius();
    }
    @Override
    public String toString() {
        return String.format("Particle(x=%d, y=%d, vx=%d, vy=%d)", x, y, vx, vy);
    }
    public int getPositionX() {
        return x;
    }
    public int getPositionY() {
        return y;
    }
    public String getColor() {
        return isRed ? "red" : "blue";
    }
    public void makeVisible(){
        circle.makeVisible();       
    }
    public boolean isRed(){
        return isRed;
    }
    public void makeInvisible(){
        circle.makeInvisible();
    }
    public void setXPosition(int x){
        this.x = x;
    }
}

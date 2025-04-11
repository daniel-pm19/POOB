/**
 * Esta clase contiene la creacion y caracteristicas de las particulas.
 * 
 * @author Daniel Patiño & Daniel Useche
 * @version Version 1.1
 */

public class Particle {
    private int x, y; 
    private int vx, vy;
    private boolean isRed; 
    private Circle circle;
    
    public Particle(String color, boolean isRed, int x, int y, int vx, int vy) {
        this.x = x+MaxwellContainer.w;
        this.y = MaxwellContainer.h-y;
        this.vx = vx;
        this.vy = vy;
        this.isRed = isRed;
        this.circle = new Circle();
        this.settings(x,y,8);
    }
    
    /*
     * Metodo que define las caracteristicas de las particulas.
     * 
     * @param x  Define la posicion en x de la particula.
     * @param y  Define la posicion en y de la particula.
     * @param diameter  Define el diametro de la particula(el circulo).
     */
    private void settings(int x, int y, int diameter){
        this.circle.changeSize(diameter);
        this.circle.movetoX(x+MaxwellContainer.w);
        this.circle.movetoY(MaxwellContainer.h-y);
        this.circle.changeColor(isRed ? "red" : "blue");

    }
    
    /**
     * Metodo que define el movimiento de la particula
     * 
     * @param dt  Define un delta de tiempo de movimiento.
     * @param width  Define los limites de movimiento de la particula en base al ancho del contenedor
     * @param height  Define los limites de movimiento de la particula en base a la altura del contenedor.
     */
    public void move(int dt,int width, int height) {
        for(int i = 0;i < dt;i++){
            this.crash(width,height);
            x += vx;
            y += vy;
            this.softMove(width,height);
        }
    }
    
    /**
     * Metodo el cual sirve para que el movimiento de la particula sea mas fluido
     * 
     * @param  w  Indica el ancho del contenedor.
     * @param  h  Indica el alto del contenedor.
     */
    public void softMove(int w,int h){
        if (MaxwellContainer.isVisible){
            this.circle.makeInvisible();
            this.circle.moveHorizontal(vx);
            this.circle.moveVertical(vy);
            this.circle.makeVisible();
        }else{
            this.circle.moveHorizontal(vx);
            this.circle.moveVertical(vy);
        }
    }
    
    /**
     * Metodo para que las particulas reboten en el contenedor.
     * 
     * @param width Indica el ancho del contenedor.
     * @param height Indica el alto del contenedor.
     */
    public void crash(int width, int height) {
        boolean crashedX = atWallX();
        boolean crashedY = atWallY();
        if (crashedX) {
            this.vx = -vx;
            atWallXDown();
            atWallXUp();
        }
        if (crashedY) {
            this.vy = -vy;
            atWallYDown();
            atWallYUp();
        }
    }

    /**
     * Metodo para verificar si la particula se encuentra cerca del demonio
     * 
     * @return En caso de estarlo retornara true o si no false
     */
    
    public boolean centerBlock() {
        int centerX = MaxwellContainer.middle;
    
        boolean nearDemon = Math.abs(x - centerX) <= 9;
        if (nearDemon) {
            return true; 
        }
    
        if ((!wheremI() && x + vx > centerX) || (wheremI() && x + vx < centerX)) {
            vx = -vx;  
            return true; 
        }

        return false;
    }

    /**
     * Metodo que determina si existe colision con los limites en x del contenedor
     * 
     * @return Booleano el cual si se encuentra cerca de los limites o de la pared en la mitad retorna true, caso contrario retornara false.
     */
    public boolean atWallX() {
        return x <= 10 || x >= MaxwellContainer.w * 2 - 10 || centerBlock();
    }

    /**
     * Metodo que determina si existe colision con los limites en y del contenedor
     * 
     * @return Booleano el cual si se encuentra cerca de los limites retorna true, caso contrario retornara false.
     */
    public boolean atWallY() {
        return y <= 10 || y >= MaxwellContainer.h - 10;
    }
    
    /**
     * Metodo para reajustar la posicion de la particula si esta supera los limites en x hacia la izquierda.
     */
    public void atWallXDown() {
        if (x < -MaxwellContainer.w + 10) {
            x = 10;
            updateCircle(x, y);
        }
    }

    /**
     * Metodo para reajustar la posicion de la particula si esta supera los limites en x hacia la derecha.
     */
    public void atWallXUp() {
        if (x > MaxwellContainer.w * 2 - 10) {
            x = MaxwellContainer.w * 2 - 10;
            updateCircle(x, y);
        }
    }

    /**
     * Metodo para reajustar la posicion de la particula si esta supera los limites en y hacia arriba.
     */
    public void atWallYDown() {
        if (y < 0) {
            y = 0;
            updateCircle(x, y);
        }
    }
    
    /**
     * Metodo para reajustar la posicion de la particula si esta supera los limites en y hacia abajo.
     */
    public void atWallYUp() {
        if (y > MaxwellContainer.h - 10) {
            y = MaxwellContainer.h - 29;
            updateCircle(x, y);
        }
    }
    
    /**
     * Metodo para actualizar la posicion del circulo.
     * 
     * @param newX Define la nueva posicion en x.
     * @param newY Define la nueva posicion en y.
     */

    private void updateCircle(int newX, int newY) {
        if(MaxwellContainer.isVisible){
            circle.makeInvisible();
            circle.movetoX(newX);
            circle.movetoY(newY);
            circle.makeVisible();
        }else{
            circle.movetoX(newX);
            circle.movetoY(newY);
        }
        
    }
    
    public boolean wheremI(){
        return x > MaxwellContainer.middle;
    }
    @Override
    public String toString() {
        return String.format("Particle(x=%d, y=%d, vx=%d, vy=%d)", x, y, vx, vy);
    }
    
    /**
     * Metodo para obtener la posicion en x de la particula.
     */
    public int getPositionX() {
        return x;
    }
    
    /**
     * Metodo para obtener la posicion en y de la particula.
     */
    public int getPositionY() {
        return y;
    }
    
    /**
     * Metodo para actualizar la posicion en x de la particula.
     */
    public void setXPosition(int x){
        this.x = x;
    }
    
    /**
     * Metodo para obtener el color de la particula(Si es azul o es roja).
     */
    public String getColor() {
        return isRed ? "red" : "blue";
    }
    
    /**
     * Metodo para hacer visible la particula.
     */
    public void makeVisible(){
        circle.makeVisible();       
    }
    
    /**
     * Metodo para determinar si la particula es roja o azul.
     * 
     * @return isRed valor booleano el cual determina si la particula es roja(true) o azul(false).
     */
    public boolean isRed(){
        return isRed;
    }
    
    /**
     * Metodo para hacer invisible la particula.
     */
    public void makeInvisible(){
        circle.makeInvisible();
    }
}

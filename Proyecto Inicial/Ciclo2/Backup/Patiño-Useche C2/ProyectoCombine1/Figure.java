

/**
 * Write a description of class Figure here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Figure{
    
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;
    
    /**
     * Make this rectangle visible. If it was already visible, do nothing.
     */
    protected void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this rectangle invisible. If it was already invisible, do nothing.
     */
    protected void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Move the rectangle a few pixels to the right.
     */
    protected void moveRight(){
        moveHorizontal(20);
    }
    
    /**
     * Move the rectangle a few pixels to the left.
     */
    protected void moveLeft(){
        moveHorizontal(-20);
    }
    
    protected void movetoY(int y){
        yPosition = y;
    }
    
    protected void movetoX(int x){
        xPosition = x;
    }

    /**
     * Move the rectangle a few pixels up.
     */
    protected void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the rectangle a few pixels down.
     */
    protected void moveDown(){
        moveVertical(20);
    }
    
    /**
     * Move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    protected void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the circle vertically.
     * @param distance the desired distance in pixels
     */
    protected void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }
    
    /**
     * Slowly move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    protected void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }
    
    /**
     * Slowly move the circle vertically
     * @param distance the desired distance in pixels
     */
    protected void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        }else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }
    
    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    protected void changeColor(String newColor){
        color = newColor;
        draw();
    }

    /*
     * Erase the rectangle on screen.
     */
    protected void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    protected void draw(){
        
    }
}

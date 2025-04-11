import java.util.Random;
public class Deamon {
    private Rectangle rectangle; 
    public Deamon(int deamonX, int deamonY, int newH, int newW) {
        rectangle = new Rectangle();
        this.settings(deamonX,deamonY, newH, newW);
    }
    private void settings(int deamonX, int deamonY, int newH, int newW){
        rectangle.changeColor("black");
        rectangle.movetoX(deamonX); 
        rectangle.movetoY(deamonY);
        rectangle.changeSize(newH, newW);
        rectangle.makeVisible();
    }
    public void makeVisible(){
        rectangle.makeVisible();
    }
    public void makeInvisible(){
        rectangle.makeInvisible();
    }
    public int getPositionX(){
        return rectangle.getPositionX();
    }
    public int getPositionY(){
        return rectangle.getPositionY();
    }
    public void getAccess(Particle p, int w){
        if(p.getPositionX() > 10 && p.getPositionX() < 90){
            if(Math.abs(p.getPositionX() - getPositionX()) < 30){
                p.setXPosition(w/2 + 10);
            } 
        } else{
            if(Math.abs(p.getPositionX() - getPositionX()) < 30){
                p.setXPosition(w/2 - 10);
            }
        }
    }
}

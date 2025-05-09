import java.util.Random;

/**
 * Esta clase contiene la creacion y caracteristicas del demonio.
 * 
 * @author Daniel Patiño & Daniel Useche
 * @version Version 1.1
 */

public class Deamon {
    private Rectangle rectangle; 
    
    public Deamon(int deamonX, int deamonY, int newH, int newW) {
        rectangle = new Rectangle();
        this.settings(deamonX,deamonY,newH, newW);
    }
    
    /*
     * Metodo que define las caracteristicas del demonio.
     * 
     * @param deamonX  Define la posicion en x.
     * @param deamonY  Define la posicion en y.
     * @param newH  Define la altura del demonio(el rectangulo)
     * @param newW  Define la anchura del demonio(el rectangulo)
     */
    private void settings(int deamonX, int deamonY, int newH, int newW){
        rectangle.changeColor("black");
        rectangle.movetoX(deamonX); 
        rectangle.movetoY(deamonY);
        rectangle.changeSize(newH, newW);
    }
    
    /**
     * Metodo para hacer visible el demonio.
     */
    public void makeVisible(){
        rectangle.makeVisible();
    }
    
    /**
     * Metodo para hacer invisible el demonio.
     */
    public void makeInvisible(){
        rectangle.makeInvisible();
    }
    
    /**
     * Metodo para obtener la posicion en x del demonio.
     */
    public int getPositionX(){
        return rectangle.getPositionX();
    }
    
    /**
     * Metodo para obtener la posicion en y del demonio.
     */
    public int getPositionY(){
        return rectangle.getPositionY();
    }
    
    /**
     * Metodo para determinar si el demonio deja pasar o no las particulas a travez de el.
     * 
     * @param p  Inidica la particula a la cual se determinara si pasa o no pasa.
     * @param w  Indica el ancho del contenedor.
     */
    public void getAccess(Particle p, int w){
        if(Math.abs(p.getPositionX() - getPositionX()) < 20 && Math.abs(p.getPositionY()- getPositionY()) < 20){
            if(p.getPositionX() < w/2 && p.getColor().equals("blue")){
                    p.setXPosition(w/2 + 5);
            } else if(p.getPositionX() > w/2 && p.getColor().equals("red")){
                p.setXPosition(w/2 - 5);
            }
        }
    }
}


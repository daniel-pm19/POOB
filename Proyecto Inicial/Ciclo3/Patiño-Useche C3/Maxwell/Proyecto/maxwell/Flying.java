package maxwell;


/**
 * Metodo que define a la particula voladora.
 * 
 * @author Daniel Patino & Daniel Useche 
 * @version 2.0
 */
public class Flying extends Particle{
    public Flying(String color, boolean isRed, int x, int y, int vx, int vy) throws MaxwellException{
        super(color, isRed, x, y, vx, vy);
    }
}

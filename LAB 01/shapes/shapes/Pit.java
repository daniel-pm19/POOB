import java.util.ArrayList;
/**
 * Write a description of class Pit here.
 * 
 * @author (Daniel Patiño y Daniel Useche) 
 * @version (a version number or a date)
 */
public class Pit{
    private Rectangle rectangulo1;
    private Rectangle rectangulo2;
    private ArrayList <Rectangle> Seeds= new ArrayList<>();
    private int x=80;
    private int y=25;
    private int j=0;
    private int k=0;
    private int seedxPosition;
    private int seedyPosition;

    
    /**
     * Se crea el constructor Pit
     */
    
    public Pit (boolean Big){
        rectangulo1 = new Rectangle();
        rectangulo2 = new Rectangle();
        rectangulo1.changeColor("black");
        rectangulo2.changeColor("blue");
        rectangulo1.changeSize(100, 100);
        rectangulo2.changeSize(80, 80);
        rectangulo2.setxPosition(x);
        rectangulo2.setyPosition(y);
  
    }
    
    public void putSeeds(int seeds){    
        seedxPosition=x;
        seedyPosition=y;
        for(int i=0; i<seeds;i++){
            if(k > 72){
                return;
            }
            Rectangle rectangulo3 = new Rectangle();
            rectangulo3.changeColor("green");
            rectangulo3.changeSize(15, 15);
            Seeds.add(rectangulo3);
            rectangulo3.makeVisible();
            rectangulo3.setxPosition(seedxPosition + j);
            rectangulo3.setyPosition(seedyPosition + k);
            j+=20;
            if (j > 72){
                j=0;
                k+=20;
            }
        }
        
    }

    public void removeSeeds(int seeds){
        int eliminadas=0;
        while (!Seeds.isEmpty() && eliminadas < seeds) {
            Rectangle lastSeed = Seeds.remove(Seeds.size() - 1);
            lastSeed.makeInvisible();
            eliminadas+=1;
        }
        
        j = (Seeds.size() % 4) * 20;
        k = (Seeds.size() / 4) * 20;
    }

    public int seeds(){
        return Seeds.size();
    }
    
    public void changeColor(String newColor, String seedsColor){
        rectangulo2.makeInvisible();
        rectangulo1.changeColor(newColor);
        rectangulo2.makeVisible();
        
        for(Rectangle seed: Seeds){
            seed.makeInvisible();
            seed.changeColor(seedsColor); 
            seed.makeVisible();
        }
        
    }
    
    public void moveTo(int xPosition, int yPosition){
        rectangulo1.setxPosition(xPosition);
        rectangulo1.setyPosition(yPosition);
        rectangulo2.setxPosition(xPosition + 10);
        rectangulo2.setyPosition(yPosition + 10);
        
        x= xPosition + 10;
        y= yPosition + 10;
        
        int j=0;
        int k=0;
        for(Rectangle seed: Seeds){
            seed.setxPosition(xPosition + j + 10);
            seed.setyPosition(yPosition + k + 10);
            j+=20;
            if(j>72){
                j=0;
                k+=20;
            }
        }
    }
    
        public void makeVisible(){
        rectangulo1.makeVisible();
        rectangulo2.makeVisible();
    }
    
    public void makeInvisible(){
        rectangulo1.makeInvisible();
        rectangulo2.makeInvisible();
    }
    
}

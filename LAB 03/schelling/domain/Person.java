package domain;

import java.awt.Color;

/**Information about a person<br>
<b>(city,row,column,color)</b><br>
<br>
 */
public class Person extends Agent implements Item{
    protected City city;
    protected int row,column;    
    protected Color color;
    
    /**Create a new person (<b>row,column</b>) in the city <b>ac</b>..
     * @param city 
     * @param row 
     * @param column 
     */
    public Person(City city,int row, int column){
        this.city=city;
        this.row=row;
        this.column=column;
        this.city.setItem(row,column,(Item)this);    
        color=Color.blue;
    }
    

    /**Returns the row
    @return 
     */
    public final int getRow(){
        return row;
    }

    /**Returns the column
    @return 
     */
    public final int getColumn(){
        return column;
    }

    
    /**Returns the color
    @return 
     */
    public final Color getColor(){
        return color;
    }
    

    /**Act
     */
    public void decide(){
         int totalNeighbors = city.neighborsEquals(this.row, this.column);
         int diffNeighbors = city.countDifferentNeighbors(this.row, this.column);
         
         if((double) diffNeighbors / totalNeighbors > 1/3){
             state = DISSATISFIED;
             move();
         } else if((double)diffNeighbors/ totalNeighbors < 1/3 && diffNeighbors != totalNeighbors) {
             state = HAPPY;
         } else{
             state = INDIFFERENT;
         }
    }

    /**Change its actual state
     */
    public  void change(){
        step();
    }
    
    public void move(){
        city.move(this,"n");
    }
}


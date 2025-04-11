package domain;

import java.awt.Color;
/**
 * La clase Beast representa a una bestia en la ciudad.
 * Este objeto es estatico por lo que las personas que pasan cerca de el los elimina de la ciudad
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Beast implements Item{
    private Color color;
    private int row, column;
    private City city;

    /**
     * Constructor de la clase Beast.
     * Inicializa el objeto con un color naranja, establece su posición en la ciudad y lo coloca en la matriz de ubicaciones de la ciudad.
     * @param city La ciudad donde se coloca la bestia.
     * @param row La fila de la posición de la bestia.
     * @param column La columna de la posición de la bestia.
     */
    public Beast(City city, int row, int column){
        color = Color.orange;  // El color del perro es naranja.
        this.row = row;
        this.column = column;
        this.city = city;
        this.city.setItem(row, column, this);
        // Coloca el edificio en la ciudad en la posición especificada.
    }
    
    @Override
    public int shape() {
        return SQUARE;  // Forma de la clase Building (en este caso, un cuadrado).
    }
    
    /**
     * Método para cambiar el estado de la bestia.
     */
    public void change() {
    }
    
    /**
     * Método para tomar decisiones relacionadas con la bestia.
     */
    public void decide() {
        if(city.neighborsEquals(this.row,this.column)>0){
            deleteItem();
        }
    }
    
    public void deleteItem(){
        for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr != 0 || dc != 0) {
                        int nr = this.row + dr;
                        int nc = this.column + dc;
                        if (city.inLocations(nr, nc) && city.getItem(nr, nc) != null){
                            city.setItem(nr,nc,null);
                        }
                    }
                }
            }
    }
    
    public Color getColor(){
      return color;
   }
}

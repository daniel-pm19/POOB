package domain;

import java.awt.Color;

/**
 * La clase Building representa un edificio en la ciudad.
 * Cada edificio tiene una posición específica (fila y columna) y está ubicado dentro de una ciudad.
 * La clase implementa la interfaz `Item` para integrarse con otros elementos de la ciudad.
 */
public class Building implements Item {
    
    /** El color del edificio (actualmente no utilizado). */
    private Color color;
    
    /** La fila y la columna donde se encuentra el edificio en la ciudad. */
    private int row, column;
    
    /** La ciudad donde se encuentra el edificio. */
    private City city;

    /**
     * Constructor de la clase Building.
     * Inicializa el edificio con un color negro, establece su posición en la ciudad
     * y lo coloca en la matriz de ubicaciones de la ciudad.
     * 
     * @param city La ciudad donde se coloca el edificio.
     * @param row La fila de la posición del edificio.
     * @param column La columna de la posición del edificio.
     */
    public Building(City city, int row, int column) {
        this.color = Color.black;  // El color inicial del edificio es negro.
        this.row = row;
        this.column = column;
        this.city = city; 
        this.city.setItem(row, column, this);  // Coloca el edificio en la ciudad en la posición especificada.
    }
    
    @Override
    public int shape() {
        return SQUARE;  // Forma de la clase Building (en este caso, un cuadrado).
    }
    
    /**
     * Método para cambiar el estado del edificio (aún no implementado).
     */
    public void change() {
        // Implementar la lógica para cambiar el estado del edificio si es necesario.
    }

    /**
     * Método para tomar decisiones relacionadas con el edificio (aún no implementado).
     */
    public void decide() {
        // Implementar la lógica para decidir acciones o eventos relacionados con el edificio.
    }
}

package domain;

import java.awt.Color;

/**
 * La clase Tree representa un arbol en la ciudad.
 * Cada edificio tiene una posición específica (fila y columna) y está ubicado dentro de una ciudad.
 * La clase implementa la interfaz `Item` para integrarse con otros elementos de la ciudad.
 */
public class Tree implements Item {

    private Color color;
    private int row, column;
    private City city;

    /**
     * Constructor de la clase Tree.
     * Inicializa el arbol con un color verde, establece su posición en la ciudad
     * y lo coloca en la matriz de ubicaciones de la ciudad.
     *
     * @param city La ciudad donde se coloca el edificio.
     * @param row La fila de la posición del edificio.
     * @param column La columna de la posición del edificio.
     */
    public Tree(City city, int row, int column) {
        color = Color.green;  // El color inicial del arbol es lightGray.
        this.row = row;
        this.column = column;
        this.city = city;
        this.city.setItem(row, column, this);
    }

    @Override
    public int shape() {
        return ROUND;  // Forma de la clase Tree (en este caso, un cuadrado).
    }

    public void decide(){}

    public Color getColor(){
        return color;
    }
}
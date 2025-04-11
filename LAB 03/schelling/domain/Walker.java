package domain;

import java.awt.Color;

/**
 * La clase Walker representa a una persona que se comporta de una manera específica en la ciudad.
 * Un "Walker" es un tipo de persona que tiene un color verde y puede moverse hacia el norte,
 * además de cambiar su estado basado en la cantidad de vecinos en su vecindad.
 * 
 * Esta clase extiende la clase `Person` y sobrescribe los métodos para decidir y moverse.
 */
public class Walker extends Person {
    
    /**
     * Constructor de la clase Walker.
     * Inicializa un objeto `Walker` en la ciudad especificada, en la fila y columna proporcionadas.
     * El color del Walker es verde y su estado inicial es "indiferente".
     * 
     * @param city La ciudad donde se coloca el Walker.
     * @param row La fila donde se coloca el Walker.
     * @param column La columna donde se coloca el Walker.
     */
    public Walker(City city, int row, int column) {   
        super(city, row, column);
        color = Color.green;  // El color del Walker es verde.
        state = INDIFFERENT;  // El estado inicial es "indiferente".
    }

    /**
     * Devuelve la forma del Walker, representada como un valor constante.
     * En este caso, la forma del Walker es cuadrada.
     * 
     * @return Un valor entero que representa la forma del Walker (constante SQUARE).
     */
    @Override
    public int shape() {
        return SQUARE;
    }

    /**
     * Toma una decisión basada en la cantidad de vecinos en las casillas adyacentes.
     * Si el Walker tiene al menos un vecino en las casillas adyacentes, su estado cambia a "feliz".
     * Si no tiene vecinos, su estado es "indiferente".
     */
    @Override
    public void decide() {
        if (this.city.neighborsEquals(this.getRow(), this.getColumn()) > 0) {
            state = Agent.HAPPY;  // Si tiene vecinos, cambia a estado feliz.
        } else {
            state = Agent.INDIFFERENT;  // Si no tiene vecinos, sigue siendo indiferente.
        }
    }

    /**
     * Cambia el estado del Walker basado en las condiciones de su vecindad.
     * Si el espacio hacia el norte está ocupado, el Walker cambia su estado a "insatisfecho".
     * Si el espacio hacia el norte está vacío, el Walker se mueve hacia allí.
     */
    @Override
    public void change() {
        if (!city.isEmpty(this.getRow() - 1, this.getColumn())) {
            state = Agent.DISSATISFIED;  // Si no está vacío el espacio hacia el norte, se vuelve insatisfecho.
        } else {
            this.move();  // Si está vacío, se mueve hacia el norte.
        }
    }

    /**
     * Mueve el Walker hacia el norte (hacia la fila anterior en la ciudad).
     * Este método es llamado dentro de `change()` cuando el Walker puede moverse.
     */
    @Override
    public void move() {
        city.move(this, "n");  // Mueve al Walker hacia el norte.
    }
}

package domain;

import java.awt.Color;

/**
 * La clase Wallflower representa a una persona tipo "Wallflower" en la ciudad.
 * Un "Wallflower" tiene un color amarillo y se mueve hacia el sur. 
 * Además, su estado cambia dependiendo de la cantidad de vecinos adyacentes.
 * 
 * Esta clase extiende de la clase `Person` y sobrescribe los métodos para decidir y moverse.
 */
public class Wallflower extends Person {
    
    /**
     * Constructor de la clase Wallflower.
     * Inicializa un objeto `Wallflower` en la ciudad especificada, en la fila y columna proporcionadas.
     * El color del Wallflower es amarillo y su estado inicial es "indiferente".
     * 
     * @param city La ciudad donde se coloca el Wallflower.
     * @param row La fila donde se coloca el Wallflower.
     * @param column La columna donde se coloca el Wallflower.
     */
    public Wallflower(City city, int row, int column) {   
        super(city, row, column);
        color = Color.yellow;  // El color del Wallflower es amarillo.
        state = INDIFFERENT;  // El estado inicial es "indiferente".
    }

    /**
     * Devuelve la forma del Wallflower, representada como un valor constante.
     * En este caso, la forma del Wallflower es cuadrada.
     * 
     * @return Un valor entero que representa la forma del Wallflower (constante SQUARE).
     */
    @Override
    public int shape() {
        return SQUARE;
    }

    /**
     * Toma una decisión basada en la cantidad de vecinos en las casillas adyacentes.
     * Si el Wallflower tiene al menos un vecino en las casillas adyacentes, su estado cambia a "insatisfecho".
     * Si no tiene vecinos, su estado cambia a "feliz".
     */
    @Override
    public void decide() {
        if (this.city.neighborsEquals(this.getRow(), this.getColumn()) > 0) {
            state = Agent.DISSATISFIED;  // Si tiene vecinos, cambia a estado insatisfecho.
        } else {
            state = Agent.HAPPY;  // Si no tiene vecinos, cambia a estado feliz.
        }
    }

    /**
     * Cambia el estado del Wallflower basado en las condiciones de su vecindad.
     * Si el espacio hacia el sur está ocupado, el Wallflower cambia su estado a "insatisfecho".
     * Si el espacio hacia el sur está vacío, el Wallflower se mueve hacia allí.
     */
    @Override
    public void change() {
        if (!city.isEmpty(this.getRow() + 1, this.getColumn())) {
            state = Agent.DISSATISFIED;  // Si el espacio hacia el sur está ocupado, cambia a "insatisfecho".
        } else {
            this.move();  // Si el espacio está vacío, se mueve hacia el sur.
        }
    }

    /**
     * Mueve el Wallflower hacia el sur (hacia la fila siguiente en la ciudad).
     * Este método es llamado dentro de `change()` cuando el Wallflower puede moverse.
     */
    @Override
    public void move() {
        city.move(this, "s");  // Mueve el Wallflower hacia el sur.
    }
}

package domain;

import java.awt.Color;

/**
 * La clase TrafficLight representa un semáforo en la ciudad.
 * El semáforo tiene un color que cambia periódicamente de rojo a verde y amarillo en un ciclo de 3 tiempos.
 * La clase implementa la interfaz `Item` para integrarse con otros elementos de la ciudad.
 */
public class TrafficLight implements Item {
    
    /** El color actual del semáforo. */
    private Color color;
    
    /** La fila y la columna donde se encuentra el semáforo en la ciudad. */
    private int row, column;
    
    /** El tiempo que ha pasado desde que el semáforo se activó. */
    private int time;
    
    /** La ciudad donde se encuentra el semáforo. */
    private City city;

    /**
     * Constructor de la clase TrafficLight.
     * Inicializa el semáforo con un color rojo, establece la posición en la ciudad
     * y coloca el semáforo en la matriz de ubicaciones de la ciudad.
     * 
     * @param city La ciudad donde se coloca el semáforo.
     * @param row La fila de la posición del semáforo.
     * @param column La columna de la posición del semáforo.
     */
    public TrafficLight(City city, int row, int column) {
        this.color = Color.red;  // El color inicial del semáforo es rojo.
        this.row = row;
        this.column = column;
        this.city = city;
        this.time = 0;  // El tiempo inicia en 0.
        this.city.setItem(row, column, this);  // Coloca el semáforo en la ciudad en la posición especificada.
    }

    /**
     * Incrementa el tiempo del semáforo. Este método es llamado en cada ciclo de tiempo para actualizar el estado del semáforo.
     */
    public void change() {
        time++;  // Incrementa el contador de tiempo.
    }

    /**
     * Decide el color del semáforo basado en el tiempo transcurrido.
     * El semáforo sigue un ciclo: cada 3 pasos el color cambia de la siguiente manera:
     * - 0: Amarillo
     * - 1: Verde
     * - 2: Rojo
     */
    public void decide() {
        color = (time % 3 == 0) ? Color.yellow : (time % 3 == 1 ? Color.green : Color.red);
    }

    /**
     * Obtiene el color actual del semáforo.
     * 
     * @return El color actual del semáforo.
     */
    public Color getColor() {
        return color;
    }
}

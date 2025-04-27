package domain;

/**
 * Clase que representa un demonio (demon) en el juego Maxwell's Demon.
 * Los demonios son entidades estáticas que actúan como barreras o puertas en el tablero,
 * controlando el flujo de partículas entre las dos mitades del tablero.
 */
public class Demon {
    private int x;  // Coordenada horizontal (columna) del demonio en el tablero
    private int y;  // Coordenada vertical (fila) del demonio en el tablero

    /**
     * Constructor que crea un nuevo demonio en la posición especificada.
     *
     * @param w Coordenada horizontal (columna) donde se ubicará el demonio.
     *          Representa la posición x en el tablero.
     * @param h Coordenada vertical (fila) donde se ubicará el demonio.
     *          Representa la posición y en el tablero.
     */
    public Demon(int w, int h) {
        this.x = w;
        this.y = h;
    }

    /**
     * Obtiene la posición actual del demonio en el tablero.
     *
     * @return Arreglo de dos enteros con las coordenadas [x, y] del demonio,
     *         donde x es la posición horizontal y y es la posición vertical.
     */
    public int[] getPosicion() {
        return new int[]{this.x, this.y};
    }
}

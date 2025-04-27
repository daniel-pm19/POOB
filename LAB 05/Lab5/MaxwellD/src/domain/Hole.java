package domain;

/**
 * Clase que representa un agujero (hole) en el tablero del juego Maxwell's Demon.
 * Los agujeros pueden absorber partículas y llevar un registro de cuántas han absorbido.
 */
public class Hole {
    private int x;          // Coordenada x (horizontal) del agujero en el tablero
    private int y;          // Coordenada y (vertical) del agujero en el tablero
    private int absorbed;   // Contador de partículas absorbidas por este agujero

    /**
     * Constructor que crea un nuevo agujero en la posición especificada.
     * @param x Coordenada horizontal (columna) donde se ubicará el agujero
     * @param y Coordenada vertical (fila) donde se ubicará el agujero
     */
    public Hole(int x, int y) {
        this.x = x;
        this.y = y;
        this.absorbed = 0;  // Inicializa el contador de partículas absorbidas
    }

    /**
     * Obtiene la posición actual del agujero en el tablero.
     * @return Arreglo de dos enteros con las coordenadas [x, y] del agujero
     */
    public int[] getPosicion() {
        return new int[]{this.x, this.y};
    }

    /**
     * Obtiene el número de partículas que este agujero ha absorbido.
     * @return Cantidad total de partículas absorbidas
     */
    public int particlesAbsorbed() {
        return this.absorbed;
    }

    /**
     * Incrementa el contador de partículas absorbidas por este agujero.
     * Se debe llamar este método cada vez que el agujero absorbe una partícula.
     */
    public void adsobParticle() {
        this.absorbed += 1;
    }
}

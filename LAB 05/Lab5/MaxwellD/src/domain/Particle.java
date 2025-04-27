package domain;

/**
 * Clase que representa una partícula en el juego Maxwell's Demon.
 * Las partículas pueden moverse por el tablero y pertenecen a un equipo específico (rojo/azul).
 * Pueden ser destruidas al caer en un agujero o alcanzar su lado objetivo del tablero.
 */
public class Particle {
    private int x;          // Coordenada x (horizontal) actual de la partícula
    private int y;          // Coordenada y (vertical) actual de la partícula
    private int team;       // Equipo al que pertenece la partícula (0 = rojo, 1 = azul)
    private MaxwellD maxwellD; // Referencia al juego principal para verificar reglas
    private boolean destroy; // Flag que indica si la partícula ha sido destruida

    /**
     * Constructor que crea una nueva partícula.
     * @param x Posición horizontal inicial
     * @param y Posición vertical inicial
     * @param team Equipo al que pertenece (0 = rojo, 1 = azul)
     * @param maxwellD Referencia al juego principal
     */
    public Particle(int x, int y, int team, MaxwellD maxwellD) {
        this.x = x;
        this.y = y;
        this.team = team;
        this.maxwellD = maxwellD;
        this.destroy = false;
    }

    /**
     * Obtiene la posición actual de la partícula.
     * @return Arreglo con coordenadas [x, y]
     */
    public int[] getPosicion() {
        return new int[]{this.x, this.y};
    }

    /**
     * Obtiene información completa de la partícula.
     * @return Arreglo con [x, y, team]
     */
    public int[] getInfo() {
        return new int[]{this.x, this.y, this.team};
    }

    /**
     * Mueve la partícula en la dirección especificada.
     * Verifica colisiones con agujeros y límites del tablero.
     * @param direccion Dirección del movimiento ("Up", "Down", "Left", "Right")
     */
    public void move(String direccion) {
        // Verificar si ya está en un agujero
        if(this.maxwellD.inHole(this.getPosicion())) {
            this.destroy = true;
            return;
        }

        // Calcular nueva posición
        int x = this.x;
        int y = this.y;

        switch(direccion) {
            case "Up":
                y -= 1;
                break;
            case "Down":
                y += 1;
                break;
            case "Left":
                x -= 1;
                break;
            case "Right":
                x += 1;
                break;
        }

        int[] pos = {x, y};

        // Verificar movimiento válido
        if(this.maxwellD.isPosible(x, y)) {
            this.x = x;
            this.y = y;
        }
        // Verificar si cayó en agujero
        else if(this.maxwellD.inHole(pos)) {
            this.destroy = true;
        }
    }

    /**
     * Verifica si la partícula está en su lado correcto del tablero.
     * @return true si está en el lado que le corresponde según su equipo
     */
    public boolean rightSpace() {
        int middle = this.maxwellD.getW() / 2;
        return (team == 0 && this.x < middle) ||
                (team == 1 && this.x >= middle + 1);
    }

    /**
     * Obtiene el equipo de la partícula.
     * @return 0 para rojo, 1 para azul
     */
    public int getTeam() {
        return this.team;
    }

    /**
     * Verifica si la partícula ha sido destruida.
     * @return true si la partícula fue destruida
     */
    public boolean isDestroy() {
        return this.destroy;
    }
}
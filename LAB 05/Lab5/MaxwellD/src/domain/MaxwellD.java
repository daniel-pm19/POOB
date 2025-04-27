package domain;

import javax.management.remote.JMXPrincipal;
import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase principal que implementa la lógica del juego Maxwell's Demon.
 * Controla el tablero, las partículas, demonios y agujeros, así como las reglas del juego.
 */
public class MaxwellD {
    // Dimensiones del tablero
    private int w;  // Ancho del tablero
    private int h;  // Alto del tablero

    // Listas de elementos del juego
    private ArrayList<Particle> particles;  // Lista de partículas
    private ArrayList<Demon> demons;       // Lista de demonios
    private ArrayList<Hole> holes;         // Lista de agujeros
    private ArrayList<String> moves;       // Historial de movimientos

    // Contadores iniciales
    private int r;   // Cantidad inicial de partículas rojas
    private int b;   // Cantidad inicial de partículas azules
    private int hu;  // Cantidad inicial de agujeros

    /**
     * Constructor principal del juego.
     * @param w Ancho del tablero
     * @param h Alto del tablero
     * @param r Cantidad de partículas rojas iniciales
     * @param b Cantidad de partículas azules iniciales
     * @param hu Cantidad de agujeros iniciales
     */
    public MaxwellD(int w, int h, int r, int b, int hu) {
        try {
            this.w = w;
            this.h = h;
            this.r = r;
            this.b = b;
            this.hu = hu;

            this.particles = new ArrayList<>();
            this.demons = new ArrayList<>();
            this.holes = new ArrayList<>();
            this.moves = new ArrayList<>();

            if (r + b + hu + this.h > w * h) {
                throw new MaxweeDException(MaxweeDException.TOOMANYPARTICLES);
            }

            // Inicializar elementos del juego
            this.createdemon();
            this.createparticles(r, b);
            this.createholes(hu);

        } catch (MaxweeDException e) {
            // Mensaje de error mejorado
            JOptionPane.showMessageDialog(
                    null,
                    "Error: Demasiadas partículas.\n" +
                            "Se intentó colocar " + (r + b + hu) + " en un espacio de " + (w * h) + ".\n" +
                            "Se usarán valores por defecto (No podrá ganar hasta no hacer un Nuevo Juego).",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            int defaultParticles = (w * h) / 8;
            this.r = defaultParticles;
            this.b = defaultParticles;
            this.hu = 0;

            // Reinicializar con valores por defecto
            this.createdemon();
            this.createparticles(this.r, this.b);
            this.createholes(this.hu);
        }
    }

    /**
     * Crea los demonios en la línea central del tablero.
     * Los demonios se colocan en todas las posiciones centrales excepto en el centro exacto.
     */
    private void createdemon() {
        for(int i = 0; i < h; i++) {
            if(i != h/2) {  // Dejar espacio libre en el centro
                Demon demon = new Demon(this.w / 2, i);
                this.demons.add(demon);
            }
        }
    }

    /**
     * Crea las partículas iniciales del juego con posiciones aleatorias.
     * @param r Cantidad de partículas rojas a crear
     * @param b Cantidad de partículas azules a crear
     */
    private void createparticles(int r, int b) {
        for(int i = 0; i < (r + b); i++) {
            int team = (i < r) ? 0 : 1;  // 0 para rojas, 1 para azules
            int x = ThreadLocalRandom.current().nextInt(0, w);
            int y = ThreadLocalRandom.current().nextInt(0, h);

            // Buscar una posición válida
            while (!this.isPosible(x, y)) {
                x = ThreadLocalRandom.current().nextInt(0, w);
                y = ThreadLocalRandom.current().nextInt(0, h);
            }

            Particle particle = new Particle(x, y, team, this);
            this.particles.add(particle);
        }
    }

    /**
     * Crea los agujeros en el tablero con posiciones aleatorias.
     * @param hu Cantidad de agujeros a crear
     */
    private void createholes(int hu) {
        for(int i = 0; i < hu; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, w);
            int y = ThreadLocalRandom.current().nextInt(0, h);

            // Buscar una posición válida
            while (!this.isPosible(x, y)) {
                x = ThreadLocalRandom.current().nextInt(0, w);
                y = ThreadLocalRandom.current().nextInt(0, h);
            }

            Hole hole = new Hole(x, y);
            this.holes.add(hole);
        }
    }

    /**
     * Verifica si una posición en el tablero está disponible.
     * @param x Coordenada x (horizontal)
     * @param y Coordenada y (vertical)
     * @return true si la posición está disponible, false si está ocupada o es inválida
     */
    public boolean isPosible(int x, int y) {
        // Verificar límites del tablero
        if(x < 0 || x >= w || y < 0 || y >= h) {
            return false;
        }

        // Verificar colisión con partículas
        for (Particle p : particles) {
            int[] pos = p.getPosicion();
            if (pos[0] == x && pos[1] == y) return false;
        }

        // Verificar colisión con demonios (línea central)
        if (x == w/2 && y != h/2) {
            return false;
        }

        // Verificar colisión con agujeros
        for (Hole h : holes) {
            int[] pos = h.getPosicion();
            if (pos[0] == x && pos[1] == y) return false;
        }

        return true;
    }

    /**
     * Obtiene información completa de todas las partículas.
     * @return Matriz con información [x, y, team] de cada partícula
     */
    public int[][] particleInfo() {
        int[][] info = new int[particles.size()][3];
        for(int i = 0; i < particles.size(); i++) {
            info[i] = particles.get(i).getInfo();
        }
        return info;
    }

    /**
     * Obtiene las posiciones de todas las partículas.
     * @return Matriz con posiciones [x, y] de cada partícula
     */
    public int[][] posicionesP() {
        int[][] posiciones = new int[particles.size()][2];
        for (int i = 0; i < particles.size(); i++) {
            posiciones[i] = particles.get(i).getPosicion();
        }
        return posiciones;
    }

    /**
     * Obtiene las posiciones de todos los demonios.
     * @return Matriz con posiciones [x, y] de cada demonio
     */
    public int[][] posicionesD() {
        int[][] posiciones = new int[demons.size()][2];
        for (int i = 0; i < demons.size(); i++) {
            posiciones[i] = demons.get(i).getPosicion();
        }
        return posiciones;
    }

    /**
     * Obtiene las posiciones de todos los agujeros.
     * @return Matriz con posiciones [x, y] de cada agujero
     */
    public int[][] posicionesH() {
        int[][] posiciones = new int[holes.size()][2];
        for (int i = 0; i < holes.size(); i++) {
            posiciones[i] = holes.get(i).getPosicion();
        }
        return posiciones;
    }

    /**
     * Mueve todas las partículas en la dirección especificada.
     * @param direccion Dirección del movimiento ("Up", "Down", "Left", "Right")
     */
    public void move(String direccion) {
        for (Particle p : particles) {
            p.move(direccion);
        }
        this.delete();  // Eliminar partículas destruidas
        this.moves.add(direccion);  // Registrar movimiento
    }

    /**
     * Elimina las partículas marcadas para destrucción.
     */
    private void delete() {
        int i = 0;
        while (i < particles.size()) {
            if(particles.get(i).isDestroy()) {
                particles.remove(i);
            } else {
                i++;
            }
        }
    }

    /**
     * Verifica si se ha alcanzado la condición de victoria.
     * @return true si todas las partículas están en su lado correcto y no hay absorciones
     */
    public boolean isGoal() {
        if(this.absorbed() > 0) return false;
        for (Particle p : particles) {
            if(!p.rightSpace()) return false;
        }
        return true;
    }

    /**
     * Calcula el total de partículas absorbidas por los agujeros.
     * @return Número total de partículas absorbidas
     */
    public int absorbed() {
        int count = 0;
        for (Hole h : holes) {
            count += h.particlesAbsorbed();
        }
        return count;
    }

    /**
     * Cuenta las partículas que están en su lado correcto del tablero.
     * @return Arreglo con [partículas rojas correctas, partículas azules correctas]
     */
    public int[] rightParticle() {
        int countRed = 0;
        int countBlue = 0;
        for (Particle p : particles) {
            if(p.rightSpace()) {
                if(p.getTeam() == 1) {
                    countBlue++;
                } else {
                    countRed++;
                }
            }
        }
        return new int[] {countRed, countBlue};
    }

    /**
     * Verifica si una posición contiene un agujero.
     * @param posicion Arreglo con coordenadas [x, y] a verificar
     * @return true si la posición contiene un agujero
     */
    public boolean inHole(int[] posicion) {
        for(Hole h : holes) {
            int[] pos = h.getPosicion();
            if(pos[0] == posicion[0] && pos[1] == posicion[1]) {
                h.adsobParticle();
                return true;
            }
        }
        return false;
    }

    // Métodos de acceso (getters, para GUI y Test)

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public ArrayList<String> getMoves() {
        return this.moves;
    }

    public ArrayList<Hole> getHoles() {
        return holes;
    }
    public ArrayList<Particle> getParticles() { return particles;}

    public int getInitialRedCount() {
        return this.r;
    }

    public int getInitialBlueCount() {
        return this.b;
    }
}
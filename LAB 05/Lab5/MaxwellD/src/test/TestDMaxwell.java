package test;


import domain.Hole;
import domain.MaxweeDException;
import domain.MaxwellD;
import domain.Particle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class TestDMaxwell {
    private MaxwellD game;
    private final int WIDTH = 10;
    private final int HEIGHT = 10;
    private final int RED_PARTICLES = 3;
    private final int BLUE_PARTICLES = 3;
    private final int HOLES = 0;

    @BeforeEach
    void setUp() {
        game = new MaxwellD(WIDTH, HEIGHT, RED_PARTICLES, BLUE_PARTICLES, HOLES);
    }

    @Test
    void ShouldInitialization() {
        assertNotNull(game);
        assertEquals(WIDTH, game.getW());
        assertEquals(HEIGHT, game.getH());
        assertEquals(RED_PARTICLES, game.getInitialRedCount());
        assertEquals(BLUE_PARTICLES, game.getInitialBlueCount());
    }

    @Test
    void ShouldCreatedemon() {
        // Debería haber demonios en la columna central (w/2) excepto en el centro exacto (h/2)
        int[][] demonsPos = game.posicionesD();
        assertEquals(HEIGHT - 1, demonsPos.length); // Todos menos el centro

        for (int[] pos : demonsPos) {
            assertEquals(WIDTH / 2, pos[0]); // Todos en la columna central
            assertNotEquals(HEIGHT / 2, pos[1]); // Ninguno en el centro exacto
        }
    }

    @Test
    void ShouldCreateparticles() {
        int[][] particles = game.particleInfo();
        assertEquals(RED_PARTICLES + BLUE_PARTICLES, particles.length);

        int redCount = 0;
        int blueCount = 0;
        for (int[] p : particles) {
            if (p[2] == 0) redCount++;
            else if (p[2] == 1) blueCount++;
        }
        assertEquals(RED_PARTICLES, redCount);
        assertEquals(BLUE_PARTICLES, blueCount);
    }


    @Test
    void ShouldCreateholes() {
        int[][] holes = game.posicionesH();
        assertEquals(HOLES, holes.length);
    }

    @Test
    void ShouldIsPosible() {
        // Verificar que la posición central (donde está el demonio) no es posible
        assertFalse(game.isPosible(WIDTH / 2, HEIGHT / 2 - 1));

        // Verificar que una posición fuera de los límites no es posible
        assertFalse(game.isPosible(-1, 0));
        assertFalse(game.isPosible(0, -1));
        assertFalse(game.isPosible(WIDTH, 0));
        assertFalse(game.isPosible(0, HEIGHT));

        // Verificar que una posición aleatoria dentro de los límites es posible (probabilidad alta)
        ArrayList<Hole> holes = game.getHoles();
        ArrayList<Particle> particles = game.getParticles();
        holes.clear();
        particles.clear();
        assertTrue(game.isPosible(0, 0));
    }

    @Test
    void ShouldMoveParticles() {
        ArrayList<Particle> particles = game.getParticles();
        particles.clear();
        particles.add(new Particle(0,1,0,game));

        // Guardar posiciones iniciales
        int[][] initialPositions = game.posicionesP();

        // Mover todas las partículas a la derecha
        game.move("Right");

        int[][] newPositions = game.posicionesP();

        // Verificar que todas las partículas se movieron a la derecha (x+1)
        for (int i = 0; i < initialPositions.length; i++) {
            // Solo verificar si no estaba en el borde derecho
            if (initialPositions[i][0] < WIDTH - 1 && initialPositions[i][0] != WIDTH/2-1) {
                assertEquals(initialPositions[i][0]+1, newPositions[i][0]);
                assertEquals(initialPositions[i][1], newPositions[i][1]);
            }
        }

        // Verificar que el movimiento se registró
        assertEquals(1, game.getMoves().size());
        assertEquals("Right", game.getMoves().get(0));
    }

    @Test
    void ShouldParticleAbsorption() {
        // Colocar un agujero en (1,1)
        ArrayList<Hole> holes = game.getHoles();
        holes.clear();
        holes.add(new Hole(1, 1));

        ArrayList<Particle> particles = game.getParticles();
        particles.clear();
        particles.add(new Particle(0,1,0,game));

        // Colocar una partícula en (0,1)
        game.move("Right"); // Se moverá a (1,1) y debería ser absorbida

        // Verificar que la partícula fue absorbida
        assertTrue(game.absorbed() > 0);
    }

    @Test
    void ShouldRightParticle() {
        int[] rightParticles = game.rightParticle();

        // En el estado inicial, algunas partículas deberían estar en el lado correcto
        assertTrue(rightParticles[0] >= 0); // Rojas correctas
        assertTrue(rightParticles[1] >= 0); // Azules correctas
    }

    @Test
    void ShouldIsGoal() {
        // En el estado inicial no debería haber victoria
        assertFalse(game.isGoal());
        ArrayList<Particle> particles = game.getParticles();
        particles.clear();
        particles.add(new Particle(0,1,0,game));
        assertTrue(game.isGoal());
    }

    @Test
    void ShouldEdgeCases() {
        // Prueba con el mínimo tablero posible
        MaxwellD smallGame = new MaxwellD(1, 1, 0, 0, 0);
        assertEquals(0, smallGame.posicionesD().length); // No debería haber demonios

        // Prueba sin partículas
        MaxwellD noParticlesGame = new MaxwellD(5, 5, 0, 0, 1);
        assertEquals(0, noParticlesGame.particleInfo().length);
    }

    @Test
    void ShouldTooManyParticles() {

        game = new MaxwellD(5, 5, 81, 81, 81);

        ArrayList<Particle> particles = game.getParticles();
            int correctParticles = (5 * 5) / 4;
            assertEquals(correctParticles, particles.size());
    }
}
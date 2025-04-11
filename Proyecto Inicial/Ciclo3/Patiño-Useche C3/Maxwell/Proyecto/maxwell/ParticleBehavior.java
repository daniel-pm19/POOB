package maxwell;

/**
 * Interfaz que define el comportamiento básico de una partícula con movimiento y colisiones
 */
public interface ParticleBehavior {
    
    /**
     * Mueve la partícula durante un intervalo de tiempo
     * @param dt Intervalo de tiempo para el movimiento
     * @param width Ancho del contenedor
     * @param height Alto del contenedor
     */
    void move(int dt, int width, int height);
    
    /**
     * Maneja el movimiento suavizado de la partícula
     */
    void smoothMove();
    
    /**
     * Verifica y maneja colisiones con los bordes y otras partículas
     */
    void checkCollisions();
    
    /**
     * Maneja colisiones con las paredes verticales (lados)
     */
    void handleWallXCollision();
    
    /**
     * Determina si la partícula está cerca del centro del contenedor
     * @return true si está cerca del centro
     */
    boolean isNearMiddle();
    
    /**
     * Verifica si hay bloqueo en el centro
     * @return true si la partícula está bloqueada en el centro
     */
    boolean centerBlock();
    
    /**
     * Determina si la partícula está colisionando con una pared vertical
     * @return true si está en un borde vertical
     */
    boolean atWallX();
    
    /**
     * Determina si la partícula está colisionando con una pared horizontal
     * @return true si está en un borde horizontal
     */
    boolean atWallY();
    
    /**
     * Limita un valor dentro de un rango
     * @param value Valor a limitar
     * @param min Límite inferior
     * @param max Límite superior
     * @return Valor dentro del rango [min, max]
     */
    int clamp(int value, int min, int max);
}
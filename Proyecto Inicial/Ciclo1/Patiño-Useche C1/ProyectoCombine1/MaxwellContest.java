import java.util.*;

/**
 * Write a description of class MaxwellContest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MaxwellContest{
    static final double EPS = 1e-9;
    static final int MAX_ITER = 10000; // Iteraciones máximas para buscar candidatos

    // Función para reflejar la posición en el eje y en un intervalo [0, h]
    static double reflect(double u, double h) {
        double mod = u % (2 * h);
        if (mod < 0) mod += 2 * h;
        if (mod <= h + EPS) return mod;
        else return 2 * h - mod;
    }

    // Para una partícula que necesita cambiar de lado, retorna el mínimo tiempo t >= 0 tal que:
    //   t = (2*w*k - px) / vx   para algún entero k (respetando las condiciones de signo)
    // y además se cumple la condición en y: reflect(py + vy*t, h) == d (dentro de una tolerancia).
    // Si no se encuentra ningún tiempo válido en MAX_ITER iteraciones se retorna Double.POSITIVE_INFINITY.
    static double findMinFlipTime(double px, double py, double vx, double vy, double w, double h, double d) {
        double best = Double.POSITIVE_INFINITY;
        // La condición en x se expresa de forma unificada:
        //    t = (2*w*k - px) / vx.
        // Este mismo cálculo funciona tanto para partículas de la derecha (px > 0) como para las de la izquierda (px < 0)
        if (vx > 0) {
            // Para t >= 0 se requiere: 2*w*k - px >= 0  =>  k >= ceil(px / (2*w))
            int kStart = (int) Math.ceil(px / (2 * w));
            for (int k = kStart; k < kStart + MAX_ITER; k++) {
                double t = (2 * w * k - px) / vx;
                if (t < 0) continue; // verificación de seguridad
                // Verificar la condición en y
                if (Math.abs(vy) < EPS) {
                    // Si vy es 0, la posición y es constante (py)
                    if (Math.abs(py - d) < EPS) {
                        best = Math.min(best, t);
                        break; // se encontró el menor candidato
                    }
                } else {
                    double y = reflect(py + vy * t, h);
                    if (Math.abs(y - d) < EPS) {
                        best = Math.min(best, t);
                        break; // al aumentar k, t aumenta, por lo que se puede salir
                    }
                }
            }
        } else { // vx < 0
            // Para vx < 0, se requiere: 2*w*k - px <= 0
            int kStart = (int) Math.floor(px / (2 * w));
            for (int k = kStart; k > kStart - MAX_ITER; k--) {
                double t = (2 * w * k - px) / vx; // vx es negativo
                if (t < 0) continue;
                if (Math.abs(vy) < EPS) {
                    if (Math.abs(py - d) < EPS) {
                        best = Math.min(best, t);
                        break;
                    }
                } else {
                    double y = reflect(py + vy * t, h);
                    if (Math.abs(y - d) < EPS) {
                        best = Math.min(best, t);
                        break;
                    }
                }
            }
        }
        return best;
    }

    public static String solve(int w, int h, double d, int r, int b, double[][] particles) {
        int total = r + b;

        // Se calculará, para cada partícula que deba cambiar de lado, el mínimo tiempo en el que puede hacerlo.
        // Recordar: las partículas rojas deben quedar en la izquierda (x < 0) y las azules en la derecha (x > 0).
        double overallTime = 0.0;
        boolean possible = true;
        for (int i = 0; i < total; i++) {
            double px = particles[i][0];
            double py = particles[i][1];
            double vx = particles[i][2];
            double vy = particles[i][3];
            // Determinar en qué cámara inicia la partícula: si px < 0 está en la izquierda, si px > 0 en la derecha.
            boolean inLeft = (px < 0);
            boolean needFlip = false;
            // Las primeras r partículas son rojas (desean estar en la izquierda) y las siguientes b son azules (desean estar en la derecha).
            if (i < r) { // roja
                if (!inLeft) needFlip = true;
            } else { // azul
                if (inLeft) needFlip = true;
            }
            if (needFlip) {
                double tCandidate = findMinFlipTime(px, py, vx, vy, w, h, d);
                if (tCandidate == Double.POSITIVE_INFINITY) {
                    possible = false;
                } else {
                    // El demonio puede posponer una intervención temprana para esperar el tiempo tCandidate.
                    // Por ello, esta partícula impone un tiempo mínimo tCandidate.
                    overallTime = Math.max(overallTime, tCandidate);
                }
            }
            // Las partículas ya correctamente ubicadas no imponen restricción en el tiempo.
        }
        if (!possible) {
            return "impossible";
        } else {
            // Se imprime el resultado con error absoluto o relativo como máximo 1e-6.
            return String.format("%.6f", overallTime);
        }
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void simulate(int h, int w, int d, int b, int r, int[][] particles){
       MaxwellContainer container = new MaxwellContainer(h, w, d, b, r, particles);
       container.start(1000);
    }
}

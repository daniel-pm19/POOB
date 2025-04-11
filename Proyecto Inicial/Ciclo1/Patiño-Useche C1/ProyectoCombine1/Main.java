import javax.swing.JOptionPane;
public class Main {
    public static void main(String[] args) {
        int[][] particlesData = {
            {0, 83,121, 1, 0},
            {1, 223, 124, -9, 7},
            {1, 32,110, -12, 8},
            {0, 100, 40, 10, 10},
            {0, 200, 120, -20, 10}
        };

        MaxwellContainer container = new MaxwellContainer(200, 200, 100, 110, 100, particlesData);
        container.addHole(82,121,1);
        JOptionPane.showMessageDialog(null, "¡Juego Iniciado!");
        container.start(700);
    }
}

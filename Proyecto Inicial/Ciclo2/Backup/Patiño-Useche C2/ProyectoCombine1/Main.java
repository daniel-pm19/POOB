import javax.swing.JOptionPane;
public class Main {
    public static void main(String[] args) {
        int[][] particlesData = {
            {1, -15, 10, 2, -4},
            {1, 223, 124, -9, 7},
            {1, 32,110, -12, 8},
            {0, 100, 40, 10, 10},
            {0, 200, 120, -20, 10},
            {0, 150, 25, 20, 10},
            
            
        };

        MaxwellContainer container = new MaxwellContainer(200, 200, 100, 3, 3, particlesData);
        JOptionPane.showMessageDialog(null, "¡Juego Iniciado!");
        container.makeVisible();
        container.start(1000);
        
    }
}

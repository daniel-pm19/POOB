package maxwell;
public class Main {
    public static void main(String[] args) throws MaxwellException {
        int[][] particlesData = {
            { 15, 10, 1, -2}
            ,{ 35, 10, -3, 2},
        };

        MaxwellContainer container = new MaxwellContainer(150, 150, 75, 1, 2, particlesData);
        container.addParticle("normal", "Green", true, 30, 60, -3, 3);
        container.addHole("normal", -50, 50, 0);
        container.addHole("movil", 50, 50, 1);
        
        //public MaxwellContainer           (int h, int w, int d, int b, int r, int[][] particlesData)
        container.makeVisible();
        container.start(10000);
        
    }
}


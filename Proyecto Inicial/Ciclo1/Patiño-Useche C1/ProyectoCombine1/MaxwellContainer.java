import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Collections;
import javax.swing.JOptionPane;
import java.util.Map;


public class MaxwellContainer {
    private Rectangle recta1, recta2, recta3;
    private ArrayList<Deamon> demons;
    private TreeMap<Double, TreeMap<Boolean, List<Particle>>> sortedParticles;
    private ArrayList<Hole> holes;
    private static Canvas canvas;
    private boolean isVisible;
    private boolean isOk;
    
    public MaxwellContainer(int h, int w) {
        isOk = validateDimensions(h, w);
        if (!isOk) return;
    
        canvas = new Canvas(w * 2 + 20, h + 20);
        recta1 = new Rectangle();
        recta2 = new Rectangle();
        recta3 = new Rectangle();
        this.settings(h,w);
        
    
        demons = new ArrayList<>();
        sortedParticles = new TreeMap<>(Collections.reverseOrder());
        holes = new ArrayList<>();
        isVisible = true;
    }
    public MaxwellContainer(int h, int w, int d, int b, int r, int[][] particlesData) {
        this(h, w); 
        if(isOk){
            for (int[] p : particlesData) {
                addParticle(p[0] == 1 ? "red" : "blue", p[0] == 1, p[1], p[2], p[3], p[4]);
            }
            this.addDeamon(d);
        }else{
             JOptionPane.showMessageDialog(null, "Juego no Iniciado debido a un Error.");
        }
    }
    private void settings(int h, int w){
        canvas.getCanvas(w * 2 + 20, h + 20);
        recta1.changeSize(h + 20, 2 * w + 20);
        recta1.changeColor("red");
        recta1.makeVisible();
        recta2.changeSize(h, 2 * w);
        recta2.changeColor("white");
        recta2.makeVisible();
        recta2.moveHorizontal(10);
        recta2.moveVertical(10);
        recta3.changeSize(h, w / 20);
        recta3.changeColor("blue");
        recta3.moveHorizontal((2 * w) / 2 - (w / 64) / 2 + 10);
        recta3.moveVertical(10);
        recta3.makeVisible();
    }
    private boolean validateDimensions(int h, int w) {
        if (h < 20 || w < 20) {
            JOptionPane.showMessageDialog(null, "Tamaño mínimo 20x20.");
            return false;
        } 
        if (h < (w / 40) + 10) {
            JOptionPane.showMessageDialog(null, "Juego Imposible, establezca otro tamaño.");
            return false;
        } 
        if (h > 2000 || w > 2000) {
            JOptionPane.showMessageDialog(null, "Tamaño máximo 2000x2000.");
            return false;
        }
        return true;
    }
    
    public void addDeamon(int d) {
        int demonX = this.getCenterX();
        int demonY =  d;
        int newH = 20;
        int newW = 10;
        
        if(demonY >= 10 && demonY < recta3.getHeight()){
            Deamon deamon = new Deamon(demonX - (newW/2), demonY, newH, newW);
            demons.add(deamon);            
        } else {
            JOptionPane.showMessageDialog(null, "Posicion invalida del demonio, establezca otra posicion.");
        }
     
    }
    
    public void delDemon(int d){
        if (demons.isEmpty()){
            return;
        }
    
        for(int i = 0; i < demons.size(); i++){
            Deamon elDemon = demons.get(i);
            if (elDemon.getPositionY() == d){
                elDemon.makeInvisible();
                demons.remove(i);
            }
        }
    }
    
    public boolean hasDemons(int d){
        for(Deamon demon : demons){
            if(demon.getPositionY() == d){
                return true;
            }
        }
        return false;
    }
        
    public void addParticle(String color, boolean isRed, int px, int py, int vx, int vy) {
        Particle p = new Particle(color, isRed, px, py, vx, vy, recta2.getWidth() / 40,this);
        sortedParticles.putIfAbsent((double) py, new TreeMap<>());
        sortedParticles.get((double) py).putIfAbsent(isRed, new ArrayList<>());
        sortedParticles.get((double) py).get(isRed).add(p);
    }

    public boolean delParticleAt(int x, int y) {
        boolean status = false;
        for (var entry : sortedParticles.values()) {
            for (var list : entry.values()) {
                status = list.removeIf(p -> {
                    if (p.getPositionX() == x && p.getPositionY() == y) {
                        p.makeInvisible(); 
                        return true;
                    }
                return false;
                });
            }
        }
        return status;
    }

    public void addHole(int px, int py, int maxParticles) {
        Hole h = new Hole(px, py, maxParticles,this);
        holes.add(h);
    }
            
    public void start(int ticks) {
        for (int i = 0; i < ticks; i++) {
            if(this.isGoal()){
                JOptionPane.showMessageDialog(null, "¡Juego Terminado!");
                return;
            }
            else{
            for (Hole h: holes){
                h.absoParticles();
            }
            List<Particle> toMove = new ArrayList<>();
            for (var entry : sortedParticles.values()) { 
                for (var list : entry.values()) { 
                    toMove.addAll(list); 
                }
            }
            for (Particle p : toMove) {
                p.move(1, recta2.getWidth(), recta2.getHeight());
            }
            }
        }
    }

    public boolean isGoal() {
            for (var entry : sortedParticles.values()) { 
                for (var list : entry.values()) { 
                    for(Particle p: list){
                        if(p.wheremI() && !p.isRed() || !p.wheremI() && p.isRed()){
                            return false;
                        }
                    } 
                }
            }
            return true;
    }
    
    public int[][] particles() {
        List<int[]> dataList = new ArrayList<>();
    
        for (var entry : sortedParticles.entrySet()) { 
            for (var list : entry.getValue().values()) { 
                for (Particle p : list) {
                    dataList.add(new int[]{p.getPositionX(), p.getPositionY()}); 
                }
            }
        }
    
        return dataList.toArray(new int[0][0]);
    }

    public void demonAccess() {
        for(Map.Entry<Integer, String> entry : sortedParticles.entrySet()){
            String p = entry.getValue();
            for (Deamon d: demons) {
                d.getAccess(p,recta2.getWidth());
            }     
        }
    }   
   
    public int[] demons() {
        int[] data = new int[demons.size()];
        for (int i = 0; i < demons.size(); i++) {
            data[i] = demons.get(i).getPositionX();
        }
        return data;
        }

    public int[][] holes() {
        int[][] data = new int[holes.size()][2];
        for (int i = 0; i < holes.size(); i++) {
            data[i][0] = holes.get(i).getX();
            data[i][1] = holes.get(i).getY();
        }
        return data;
    }

    public void makeVisible() {
        isVisible = true;
        recta1.makeVisible();
        recta2.makeVisible();
        recta3.makeVisible();
        for (var entry : sortedParticles.values()) { 
            for (var list : entry.values()) { 
                for (Particle p : list) {
                    p.makeVisible(); 
                }
            }
        }
        for (Deamon d : demons) {
            d.makeVisible();
        }
        for (Hole h : holes) {
            h.makeVisible(); 
        }
    }
    
    public void makeInvisible() {
        isVisible = true;
        recta1.makeInvisible();
        recta2.makeInvisible();
        recta3.makeInvisible();
        for (var entry : sortedParticles.values()) { 
            for (var list : entry.values()) { 
                for (Particle p : list) {
                    p.makeInvisible(); 
                }
            }
        }
        for (Deamon d : demons) {
            d.makeInvisible();      
        }
        for (Hole h : holes) {
            h.makeInvisible(); 
        }
    }

    public void finish() {
        sortedParticles.clear();
        demons.clear();
        holes.clear();
    }

    public int getCenterX() {
        return recta3.getPositionX() + recta3.getWidth() / 2 ;
    }
    
    public int getCenterY() {
        return recta3.getPositionY() + recta3.getHeight() / 2;
    }


    public boolean ok() {
        return isOk;
    }
}

    public class Hole {
    private Circle circle;
    private int x, y;
    private int maxParticles;
    private MaxwellContainer tablero;
    private int absortionRadius = 5;
    
    public Hole(int x, int y, int maxParticles, MaxwellContainer tablero) {
        this.x = x;
        this.y = y;
        this.maxParticles = maxParticles;
        circle = new Circle();
        this.tablero = tablero;
        this.settings(y,x);
    }
    private void settings(int y, int x){
        circle.changeColor("black");
        circle.moveHorizontal(x);
        circle.moveVertical(y);
        circle.changeSize(5); 
        circle.makeVisible();
    }
    public boolean canAbsorb() {
        return maxParticles >= 1;
    }
    public void makeVisible(){
        circle.makeVisible();
    }
    public void makeInvisible(){
        circle.makeInvisible();
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public int getRemainingCapacity() {
        return maxParticles;
    }
    public int counterHC(){
        if(this.canAbsorb()){
            this.maxParticles += -1;
            return this.maxParticles;
        }else{
            return 0;
        }
    } 
    public void absoParticles(){
        if(this.canAbsorb()){
            if(tablero.delParticleAt(this.getX(),this.getY())){
                this.getRemainingCapacity();
                this.counterHC();
                
            }
        }else{
            this.makeInvisible();
        }
    }
}


package test;
import java.awt.Color;
import domain.City;
import domain.Walker;
import domain.Person;
import domain.Item;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
public class WalkerTest {

    private City city;
    private Walker walker;

    @Before
    public void setUp() {
        // Crear una instancia de City antes de cada prueba
        city = new City();  // Ciudad con el tamaño predeterminado de 25x25
        walker = new Walker(city, 2, 2); // Ubicamos al Walker en una posición (2, 2)
    }

    @Test
    public void testWalkerInitialization() {
        // Verificar que el estado inicial del Walker sea INDIFFERENT
        assertTrue("El estado inicial del Walker debería ser INDIFFERENT", walker.isIndifferent());
        assertEquals("El color del Walker debería ser verde", Color.green, walker.getColor());
    }
    @Test
    public void testWalkerShape() {
        // Verificar que la forma del Walker sea SQUARE
        assertEquals("La forma del Walker debería ser SQUARE", Walker.SQUARE, walker.shape());
    }
    
        @Test
    public void testWalkerDecideHappy() {
        city.setItem(1, 1, new Person(city, 1, 1));
        city.setItem(3, 3, new Person(city, 3, 3)); 
        
        walker.decide(); 
        assertTrue("El estado del Walker debería ser HAPPY si tiene vecinos", walker.isHappy());
    }
    
    @Test
    public void testWalkerDecideIndifferent() {
      
        city.setItem(1, 1, null);  
        walker.decide(); 
        assertTrue("El estado del Walker debería ser INDIFFERENT si no tiene vecinos", walker.isIndifferent());
    }
    
    @Test
    public void testWalkerChangeDissatisfied() {
       
        city.setItem(walker.getRow() - 1, walker.getColumn(), new Person(city, walker.getRow() - 1, walker.getColumn()));
    
        walker.change(); 
    
        assertTrue("El estado del Walker debería ser DISSATISFIED si la celda superior está ocupada", walker.isDissatisfied());
    }

    @Test
    public void testWalkerMove() {
        city.setItem(walker.getRow() - 1, walker.getColumn(), null); 
        walker.change();
        assertEquals("El Walker debería haberse movido hacia arriba", walker.getRow(), 1); // Verifica que la fila se ha reducido
    }

    
}


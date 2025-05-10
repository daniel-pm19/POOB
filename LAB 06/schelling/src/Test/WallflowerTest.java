package test;

import java.awt.Color;
import domain.City;
import domain.Walker;
import domain.Wallflower;
import domain.Person;
import domain.Item;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.*;

/**
 * The test class cityTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class WallflowerTest{
    //WallflowerTest
    private City city;
    private Wallflower wallflower;
    private Walker walker;

    @Before
    public void setUp() {
        city = new City(); 
        wallflower = new Wallflower(city, 2, 2); 
    }

    @Test
    public void testWallflowerInitialization() {
       
        assertTrue("El estado inicial del Wallflower debería ser INDIFFERENT", wallflower.isIndifferent());
        assertEquals("El color del Wallflower debería ser amarillo", Color.yellow, wallflower.getColor());
    }

    @Test
    public void testWallflowerShape() {
        
        assertEquals("La forma del Wallflower debería ser SQUARE", Wallflower.SQUARE, wallflower.shape());
    }

    @Test
    public void testWallflowerDecideDissatisfied() {
        city.setItem(1, 1, new Person(city, 1, 1));
        city.setItem(3, 3, new Person(city, 3, 3));
        
        wallflower.decide(); 

        assertTrue("El estado del Wallflower debería ser DISSATISFIED si tiene vecinos", wallflower.isDissatisfied());
    }

    @Test
    public void testWallflowerDecideHappy() {
        city.setItem(1, 1, null);  
        wallflower.decide(); 
        assertTrue("El estado del Wallflower debería ser HAPPY si no tiene vecinos", wallflower.isHappy());
    }

    @Test
    public void testWallflowerChangeDissatisfied() {
        city.setItem(wallflower.getRow() + 1, wallflower.getColumn(), new Person(city, wallflower.getRow() + 1, wallflower.getColumn()));
        wallflower.change(); 
        assertTrue("El estado del Wallflower debería ser DISSATISFIED si la celda inferior está ocupada", wallflower.isDissatisfied());
    }

    @Test
    public void testWallflowerMove() {
        city.setItem(wallflower.getRow() + 1, wallflower.getColumn(), null); 

        wallflower.change(); 

        assertEquals("El Wallflower debería haberse movido hacia abajo", wallflower.getRow(), 3); // Verifica que la fila se ha incrementado
    }
}

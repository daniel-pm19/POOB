package test;


import domain.Beast;
import domain.City;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class BeastTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class BeastTest
{
    private City city;
    private Beast beast;
    
    @BeforeEach
    public void setUp(){
        city = new City();
        beast = new Beast(city, 10,10);
    }
    
    @Test
    public void testInitialColor(){
        assertEquals(Color.orange, beast.getColor());
    }
    
    @Test
    public void shouldCreateBeast(){
        assertFalse(city.isEmpty(10,10));
    }
    
    //Si sobra tiempo shouldDelete
}

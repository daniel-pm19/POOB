package test;

import domain.City;
import java.awt.Color;
import domain.Building;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class BuildingTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class BuildingTest
{
    private City city;
    private Building building;
    
    @BeforeEach
    public void setUp(){
        city = new City();
        building = new Building(city,8,5);

    }
    
    @Test
    public void TestInitialColor(){
        assertEquals(Color.black, building.getColor(), "El color inicial debería ser negro.");
    }
    
    @Test
    public void shouldCreateBuilding(){
        assertFalse(city.isEmpty(8,5));
    }
}

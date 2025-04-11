package test;
import domain.City;
import domain.TrafficLight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;

public class TrafficLightTest {

    private City city;
    private TrafficLight trafficLight;

    @BeforeEach
    public void setUp() {
        city = new City(); 
        trafficLight = new TrafficLight(city, 1, 1);
    }

    @Test
    public void testInitialColor() {
        assertEquals(Color.red, trafficLight.getColor(), "El color inicial debería ser rojo.");
    }

    @Test
    public void testChangeColorYellow() {
        trafficLight.decide();
        trafficLight.change();
      
        assertEquals(Color.yellow, trafficLight.getColor(), "Después de un cambio, el color debería ser amarillo.");
    }

    @Test
    public void testChangeColorGreen() {
        trafficLight.decide();
        trafficLight.change(); 
        trafficLight.decide();
        trafficLight.change(); 
        
        assertEquals(Color.green, trafficLight.getColor(), "Después de dos cambios, el color debería ser verde.");
    }

    @Test
    public void testColorCycle() {
        trafficLight.decide();
        trafficLight.change();
        assertEquals(Color.yellow, trafficLight.getColor(), "El color debería ser amarillo en el primer cambio.");

        trafficLight.decide();
        trafficLight.change();
        assertEquals(Color.green, trafficLight.getColor(), "El color debería ser verde en el segundo cambio.");

        trafficLight.decide();
        trafficLight.change();
        assertEquals(Color.red, trafficLight.getColor(), "El color debería volver a rojo en el tercer cambio.");
    }
}


import javax.swing.JOptionPane;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class MaxwellContainerTest.
 *
 * @author  Daniel Patiño & Daniel Useche
 * @version 1.0
 */
public class MaxwellContainerTest
{
    /**
     * Default constructor for test class MaxwellContainerTest
     */
    public MaxwellContainerTest(){
        
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp(){       
    }
    
    MaxwellContainer container = new MaxwellContainer(200, 200, 100, 110, 100, new int[0][0]); 
    
    @Test
    public void accordingPUshouldAddDemon(){
        container.addDeamon(100);
        assertTrue(container.hasDemons(100));
    }
    
    @Test
    public void accordingPUshouldNotAddDemon(){
        container.addDeamon(-200);
        assertFalse(container.hasDemons(-200));     
    }
    
    @Test
    public void accordingPUshoulddelDemon(){
        container.addDeamon(10);
        container.delDemon(10);
       assertFalse(container.hasDemons(10));  
    }
    
    @Test
    public void accordingPUshouldNotdelDemon(){
        container.addDeamon(1);
        container.delDemon(201); 
        assertTrue(container.hasDemons(1));//Refactorizar
    }
    
    @Test
    public void accordingPUshouldAddParticle(){
        container.addParticle("red", true, 83, 121, 0, 0);
        assertTrue(container.delParticleAt(83,121));
    }
    
    /*
    @Test
    public void accordingPUshouldNotAddParticle(){
        container.addParticle("blue", false, 10000, 121000, 122, -10);
        assertFalse(container.delParticleAt(10000, 121000));
    }
    */
    
    @Test
    public void accordingPUshouldDelParticleAt(){
    container.addParticle("red", true, 83, 121, 1, 0);
    assertTrue(container.delParticleAt(83, 121)); 
    assertFalse(container.delParticleAt(83, 121));
    }
    
    @Test
    public void accordingPUshouldNotDelParticleAt(){
        assertFalse(container.delParticleAt(10, 20));
    }
    
    @Test
    public void accordingPUshouldAddHole(){
        container.addHole(100, 50, 10);
        assertTrue(container.hasHoles(100,50));
    }
    
    /*
    @Test
    public void accordingPUshouldNotAddHole(){
        container.addHole(300, 300, 10);
        assertFalse(container.hasHoles(300,300));
    }
    */
    
    @Test
    public void accordingPUshouldStart(){
        
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown(){
        
    }   
}



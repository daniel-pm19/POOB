package Tests;
import domain.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CoreTest{
   
 
    @Test
    public void shouldCalculateTheCreditsOfACoreCostume(){
        Core c = new Core("NFCC", "Nucleo de Formacion Comun por Campo", 50);
        c.addCourse(new Course("PRI1","Proyecto Integrador 1", 3, 3));
        c.addCourse(new Course("DDYA", "Diseño de Datos y Algoritmos", 4, 4));
        c.addCourse(new Course("MPIN", "Matematicas para Informatica", 3, 4));
        try {
           assertEquals(10,c.credits());
        } catch (Plan15Exception e){
            fail("Threw a exception");
        }    
    }    
    
    
    @Test
    public void shouldThrowExceptionIfCoreHasNoCourse(){
           Core c = new Core("NFCC", "Nucleo de Formacion Comun por Campo", 50);
        try { 
           int price=c.credits();
           fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.IMPOSSIBLE,e.getMessage());
        }    
    }    
    
    
   @Test
    public void shouldThrowExceptionIfThereIsErrorInCredits(){
        Core c = new Core("NFCC", "Nucleo de Formacion Comun por Campo", 50);
        c.addCourse(new Course("PRI1","Proyecto Integrador 1", -3, 3));
        c.addCourse(new Course("DDYA", "Diseño de Datos y Algoritmos", 4, 4));
        c.addCourse(new Course("MPIN", "Matematicas para Informatica", 3, 4));
        try { 
           int price=c.credits();
           fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.CREDITS_ERROR,e.getMessage());
        }    
    }     
    
   @Test
    public void shouldThrowExceptionIfCreditsIsNotKnown(){
        Core c = new Core("NFCC", "Nucleo de Formacion Comun por Campo", 50);
        c.addCourse(new Course("PRI1","Proyecto Integrador 1", 1, 3));
        c.addCourse(new Course("DDYA", "Diseño de Datos y Algoritmos", 4, 4));
        c.addCourse(new Course("MPIN", "Matematicas para Informatica"));
        try { 
           int price=c.credits();
           fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.CREDITS_UNKNOWN,e.getMessage());
        }    
    } 
    @Test
    public void shoulTrowExceptionIfCreditIsEqualZero(){
        Core c = new Core("NFCC", "Nucleo de Formacion Comun por Campo", 50);
        c.addCourse(new Course("PRI1","Proyecto Integrador 1", 0, 0));
        c.addCourse(new Course("DDYA", "Diseño de Datos y Algoritmos", 0, 0));
        c.addCourse(new Course("MPIN", "Matematicas para Informatica", 0, 0));
        try { 
           int price=c.creditsEstimated();
           fail("Did not throw exception");
        } catch (Plan15Exception e) {
            assertEquals(Plan15Exception.IMPOSSIBLE,e.getMessage());
        }
    }
    @Test
    public void shoulGiveTheStimatedCredits(){
        Core c = new Core("NFCC", "Nucleo de Formacion Comun por Campo", 50);
        c.addCourse(new Course("PRI1","Proyecto Integrador 1", 1, 1));
        c.addCourse(new Course("DDYA", "Diseño de Datos y Algoritmos"));
        c.addCourse(new Course("MPIN", "Matematicas para Informatica"));
        try{int price=c.creditsEstimated();
            assertEquals(7,price);}
        catch(Plan15Exception e){
            System.out.println(e);
        }
    }
    

}

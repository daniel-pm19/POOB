package Tests;
import domain.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class Plan15Test.
 *
 * @author  Daniel Useche & Daniel Patiño
 */
public class Plan15Test{
        @Test
    public void shouldAddCourseWithValidValues() throws domain.Plan15Exception{
        Plan15 plan = new Plan15();
        plan.addCourse("MABA", "Matematicas Basicas", "3", "3");
        Unit course = plan.consult("MABA");
        
        assertNotNull(course);
        assertEquals("MABA", course.code());
        assertEquals("Matematicas Basicas", course.name());
        assertEquals(3, course.credits());
        assertEquals(3, course.inPerson());
    }
    
    @Test
    public void shouldAddCoreWithValidValues() throws Plan15Exception{
        Plan15 plan = new Plan15();
        plan.addCore("NFBP", "Nucleo de Formacion Basico Profesional", "20", "12");
        Unit core = plan.consult("NFBP");
        
        assertNotNull(core);
        assertEquals("NFBP", core.code());
        assertEquals("Nucleo de Formacion Basico Profesional", core.name());
    }
    
    @Test
    public void addCourseWithExistingCore() throws Plan15Exception{
        Plan15 plan = new Plan15();
        plan.addCourse("MATD", "Matemáticas Discretas", "4", "3");
        plan.addCore("ISIS", "Ingenieria de sistemas", "40", "MATD");
    
        Unit core = plan.consult("ISIS");
    
        assertNotNull(core);
        assertEquals("ISIS", core.code());
        assertEquals("Ingenieria de sistemas", core.name());
    }  
    
    @Test
    public void shouldAddCoreAndCourseSuccessfully(){
        try{
            Plan15 plan = new Plan15();
            plan.addCourse("COURSE1", "Curso 1", "4", "3");
            plan.addCourse("COURSE2", "Curso 2", "3", "2");
            plan.addCore("NFE", "Nucleo De Formacion Electivo", "40", "COURSE1\nCOURSE2");
            Unit addedCore = plan.consult("NFE");
            assertEquals("Nucleo De Formacion Electivo", addedCore.name()); 
        }catch(Plan15Exception e){
            
        }
    }

    @Test
    public void shouldAddSecondCoreAndCourseSuccessfully(){
        try{
            Plan15 plan = new Plan15();
            plan.addCourse("POOB", "Programacion Orientada A Objetos", "4", "3");
            plan.addCourse("ECDI", "Ecuaciones Diferenciales", "3", "2");
            plan.addCore("PLF", "PUENTES LOPEZ FORMACION", "40", "POOB\nECDI");
            Unit addedCore = plan.consult("PLF");
            assertEquals("PUENTES LOPEZ FORMACION", addedCore.name());
        }catch(Plan15Exception e){
            
        }
    }   
    /*
    @Test
    public void shouldDoToString() {
        try{
            Plan15 plan = new Plan15();
            plan.addCourse("IPRO", "Introduccion a la programacion", "4", "4");
    
            //System.out.println("Unidades actuales: " + plan.numberUnits());
            //System.out.println("Salida real:\n" + plan.toString());
    
            assertEquals("7 unidades\n" +
                        ">PRI1: Proyecto Integrador. Creditos:9[3+24]\n" +
                        ">DDYA: Diseño de Datos y Algoritmos. Creditos:4[4+8]\n" +
                        ">MPIN: Matematicas para Informatica. Creditos:3[4+5]\n" +
                        ">DOSW: Desarrollo y Operaciones Software. Creditos:4[4+8]\n" +
                        ">NFCC: Nucleo formacion por comun por campo. [50%]\n" +
                        "\t>PRI1: Proyecto Integrador. Creditos:9[3+24]\n" +
                        "\t>DDYA: Diseño de Datos y Algoritmos. Creditos:4[4+8]\n" +
                        "\t>MPIN: Matematicas para Informatica. Creditos:3[4+5]\n" +
                        ">NFPE: Nucleo de formacion especifica. [100%]\n" +
                        "\t>DOSW: Desarrollo y Operaciones Software. Creditos:4[4+8]\n" +
                        ">IPRO: Introduccion a la programacion. Creditos:4[4+8]\n", 
                        plan.toString());
        }catch(Plan15Exception e){
            
        }
        
    }
    /*
    @Test
    public void shouldDoToStringAddingTwoCourses() {
       try{
           Plan15 plan = new Plan15();
            
           plan.addCourse("DOPO", "Desarrollo Orientado a Objetos", "4", "4");
           plan.addCourse("FDSI", "Fundamentos de Seguridad de la Información", "3", "4");
    
            //System.out.println("Unidades actuales: " + plan.numberUnits());
            //System.out.println("Salida real:\n" + plan.toString());
            
           assertEquals("8 unidades\n" +
                        ">PRI1: Proyecto Integrador. Creditos:9[3+24]\n" +
                        ">DDYA: Diseño de Datos y Algoritmos. Creditos:4[4+8]\n" +
                        ">MPIN: Matematicas para Informatica. Creditos:3[4+5]\n" +
                        ">DOSW: Desarrollo y Operaciones Software. Creditos:4[4+8]\n" +
                        ">NFCC: Nucleo formacion por comun por campo. [50%]\n" +
                        "\t>PRI1: Proyecto Integrador. Creditos:9[3+24]\n" +
                        "\t>DDYA: Diseño de Datos y Algoritmos. Creditos:4[4+8]\n" +
                        "\t>MPIN: Matematicas para Informatica. Creditos:3[4+5]\n" +
                        ">NFPE: Nucleo de formacion especifica. [100%]\n" +
                        "\t>DOSW: Desarrollo y Operaciones Software. Creditos:4[4+8]\n" +
                        ">DOPO: Desarrollo Orientado a Objetos. Creditos:4[4+8]\n" +
                        ">FDSI: Fundamentos de Seguridad de la Información. Creditos:3[4+5]\n",  
                        plan.toString());
        }catch(Plan15Exception e){
            
        }
    }
    */
    
    @Test
    public void shouldSearch() {
        try{
            Plan15 plan = new Plan15();
            System.out.println("Unidades actuales: " + plan.numberUnits());
            String searching = plan.search("PRI").stripTrailing(); 
            System.out.println("Salida obtenida:\n" + searching);
            assertEquals("4 unidades\n" + 
                        ">PRI1: Proyecto Integrador. Creditos:9[3+24]", 
                        searching);
        }catch(Plan15Exception e){
            
        }
    }

    @Test
    public void shouldFailWheniSEmptyName() {
        try {
            Plan15 plan = new Plan15();
            try {
                plan.addCourse("CCC1", null, "3", "2");
            } catch (Plan15Exception e) {
                assertEquals(Plan15Exception.INVALID_NAME, e.getMessage());
            }
        } catch (Plan15Exception f) {
        }
    }

    @Test
    public void shouldFailWhenNonIntegerCredits() {
        try {
            Plan15 plan = new Plan15();
            try {
                plan.addCourse("C1", "COURSE", "abc", "2");
            } catch (Plan15Exception e) {
                assertEquals(Plan15Exception.CREDITS_ERROR, e.getMessage());
            } 
        } catch (Plan15Exception e) {
            
        }
    }
    
    @Test
    public void shouldFailWhenPortageIsNotBetween0And100() {
        try {
            Plan15 plan = new Plan15();
            try {
                plan.addCore("CCC1", "COURSE", "101", "PRYE");
            } catch (Plan15Exception e) {
                assertEquals(Plan15Exception.PORTAGE_ERROR, e.getMessage());
            } 
        } catch (Plan15Exception f) {
            
        }
    }
    
    @Test
    public void shouldFailWhenIfNameAndInitialsAreTheSame() {
        try {
            Plan15 plan = new Plan15();
            try {
                plan.addCourse("CCC1", "CCC1", "3", "2");
            } catch (Plan15Exception e) {
                assertEquals(Plan15Exception.INVALID_NAMES, e.getMessage());
            } 
        } catch (Plan15Exception f) {
            
        }
    }
    
    
    @Test
    public void shouldFailNameDoesNotExist(){
        try{
            Plan15 plan = new Plan15();
            plan.addCourse("MBDA", null, "4", "4");
        } catch(Plan15Exception e){
            assertEquals(Plan15Exception.INVALID_NAME, e.getMessage());
        }
    }
    
    @Test
    public void shouldFailNumberNotInteger(){
        try{
            Plan15 plan = new Plan15();
            plan.addCourse("POOB", "Programacion Orientada a Objetos", "4.5", "4");
        }catch(Plan15Exception e){
            assertEquals(Plan15Exception.CREDITS_ERROR, e.getMessage());
        }
    }
    
    @Test
    public void shouldFailIfOutOfRange(){
        try{
            Plan15 plan = new Plan15();
            plan.addCore("CBI", "Ciclo Basico Inicial", "-50", "POOB");
        }catch(Plan15Exception e){
            assertEquals(Plan15Exception.PORTAGE_ERROR, e.getMessage());
        }
    }
    
    @Test
    public void shouldFailCodeDoesNotExist(){
        try{
            Plan15 plan = new Plan15();
            plan.addCourse(null,"Algoritmos y programacion", "2", "14");
        } catch(Plan15Exception e){
            assertEquals(Plan15Exception.INVALID_CODE, e.getMessage());
        }
    }
    
    @Test   
    public void shouldFailSearch() throws Plan15Exception {
        Plan15 plan = new Plan15();
    
      
        plan.addCourse("MABA", "Matemáticas Básicas", "3", "48");
        plan.addCourse("IPRO", "Introducción a la programación", "4", "64");
        plan.addCourse("ISIS", "Introduccion a Sistemas", "3", "50");
    
        String resultado = plan.search("i");  
    }

}
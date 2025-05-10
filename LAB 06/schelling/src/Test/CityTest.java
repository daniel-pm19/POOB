package Test;

import domain.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CityTest {

    private City city;
    private File testFile;

    @Before
    public void setUp(){
        city = new City();
        testFile = new File("test.dat");
    }

    @Test
    public void shouldSaveFileAndLoadFile() throws CityException{
        city.save00(testFile);
        assertTrue(testFile.exists());

        City cityLoaded = city.open00(testFile);
        assertNotNull("City loaded should not be null", cityLoaded);
        assertEquals("The city size should be the same in both cities", city.getSize(), cityLoaded.getSize());
    }

    @Test
    public void shouldFailWithFileNotFound() {
        File file = new File("nonExistentFile.dat");
        City cityLoaded;
        try {
            cityLoaded = city.open00(file);
            assertNull("City loaded should be null", cityLoaded);
        } catch (CityException e) {
            assertEquals(CityException.FILE_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void shouldImportCorrectFile() throws CityException, IOException {

        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Person 5 5\n");
            writer.write("Beast 2 19\n");
        }

        city.import00(testFile);

        assertNotNull("Should exist a person in (5,5)", city.getItem(5, 5));
        assertTrue("The item in (5,5) should be a Person", city.getItem(5, 5) instanceof Person);

        assertNotNull("Should exist a beast in (2,19)", city.getItem(10, 10));
        assertTrue("The item in (2,19) should be a beast", city.getItem(10, 10) instanceof Beast);
    }

    @Test
    public void shouldExportAndThenImport() throws CityException {
        new Person(city, 1, 1);
        new Building(city, 2, 2);

        city.export00(testFile);

        City newCity = new City();
        newCity.import00(testFile);

        assertNotNull("Should exist a type in (1,1)", newCity.getItem(1, 1));
        assertTrue("(1,1) element should be a Person", newCity.getItem(1, 1) instanceof Person);

        assertNotNull("Should exist a type en (2,2)", newCity.getItem(2, 2));
        assertTrue("El element should be a Building in (2,2) ", newCity.getItem(2, 2) instanceof Building);
    }

    @Test
    public void shouldImport01CorrectFile() throws CityException, IOException {
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Person 5 5\n");
            writer.write("Building 10 10\n");
        }
        city.import01(testFile);

        assertNotNull(" Should exist a person in (5,5)", city.getItem(5, 5));
        assertTrue("The element in (5,5) should be a person", city.getItem(5, 5) instanceof Person);

        assertNotNull("Should exist a building in (10,10)", city.getItem(10, 10));
        assertTrue("The element in (10,10) should be a Building", city.getItem(10, 10) instanceof Building);
    }

    @Test
    public void shouldImportAndThenExport01() throws CityException {
        new Person(city, 1, 1);
        new Building(city, 2, 2);

        city.export01(testFile);

        City newCity = new City();
        newCity.import01(testFile);

        assertNotNull("Should exist an element in (1,1)", newCity.getItem(1, 1));
        assertTrue("The element in (1,1) should be a Person", newCity.getItem(1, 1) instanceof Person);

        assertNotNull("Should exist an element in (2,2)", newCity.getItem(2, 2));
        assertTrue("The element in (2,2) should be a Building", newCity.getItem(2, 2) instanceof Building);
    }

    @Test
    public void shouldSaveAndLoadFile01() throws CityException {
        new Person(city, 1, 1);
        new Building(city, 2, 2);

        city.save01(testFile);
        assertTrue("File should exist", testFile.exists());

        City newCity = city.open01(testFile);

        assertNotNull("The city loaded should not be null", newCity);
        assertEquals("The size of both cities should be the same", city.getSize(), newCity.getSize());

        assertTrue("Should exist a Person in (1,1)", newCity.getItem(1, 1) instanceof Person);
        assertTrue("Should exist a Building in (2,2)", newCity.getItem(2, 2) instanceof Building);
    }

    @Test
    public void shouldThrowExceptionNonExistentFile01() throws CityException {
        File nonExistentFile = new File("nonExist.dat");
        assertThrows(CityException.class, () -> city.save01(nonExistentFile));
    }


    @Test
    public void shouldThrowExceptionOccupiedPosition03() throws CityException, IOException {
        new Person(city, 5, 5);
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Building 5 5\n");
        }
        assertThrows(CityException.class, () -> city.import03(testFile));
    }

    @Test
    public void shouldThrowExceptionInvalidType03() throws CityException, IOException {
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("InvalidType 5 5\n");
        }
        assertThrows(CityException.class, () -> city.import03(testFile));
    }
}

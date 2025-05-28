package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BagPackTest {

    private POOBkemon game;
    private ArrayList<String> trainers;
    private HashMap<String, ArrayList<Integer>> pokemons;
    private HashMap<String, String[][]> items;
    private HashMap<String, ArrayList<Integer>> attacks;

    @BeforeEach
    void setUp() {
        game.resetInstance();
        game = POOBkemon.getInstance();

        // Datos básicos de prueba
        trainers = new ArrayList<>();
        trainers.add("Player1");
        trainers.add("Player2");

        pokemons = new HashMap<>();
        pokemons.put("Player1", new ArrayList<>(List.of(1, 2, 56, 4,5,28)));
        pokemons.put("Player2", new ArrayList<>(List.of(3, 4, 45, 3,203,301)));

        items = new HashMap<>();            //"Tipo","cantidad","SaludQueRecupera"
        items.put("Player1", new String[][]{{"Potion", "5","54"},{"Potion", "2","20"}});
        items.put("Player2", new String[][]{{"Potion", "2","5"},{"Potion", "1","20"}});

        attacks = new HashMap<>();
        attacks.put("Player1", new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,13,14,15,16,17,17,19,20,21,22,23,24)));
        attacks.put("Player2", new ArrayList<>(List.of(9, 10, 11, 12, 13, 14, 15, 16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,45)));
        try {
            game.initGame(trainers, pokemons, items, attacks, false);
        }catch (POOBkemonException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getItem() {
        for(Team t:game.teams){
            assertDoesNotThrow(()->t.getTrainer().getBagPack().getItems());
        }
    }

    @Test
    void getItems() {
        assertDoesNotThrow(()->game.teams.get(0).getTrainer().getBagPack().getItem("potion"));
    }
}
package domain;

import java.util.*;

/**
 * La clase City representa una ciudad de tamaño fijo en la que se pueden colocar diferentes objetos (como personas, semáforos, etc.).
 * La ciudad tiene un tamaño definido y una matriz que almacena los objetos en diferentes ubicaciones dentro de la ciudad.
 */
public class City {
    
    /** El tamaño de la ciudad (tamaño de la matriz que contiene los objetos). */
    static private int SIZE = 25;

    /** La matriz que contiene los elementos en las ubicaciones de la ciudad. */
    private Item[][] locations;

    /**
     * Constructor de la clase City.
     * Inicializa la matriz de ubicaciones con un tamaño de 25x25 y coloca algunos elementos predeterminados en la ciudad.
     */
    public City() {
        locations = new Item[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                locations[r][c] = null;  // Inicializa todas las ubicaciones como vacías.
            }
        }
        someItems();  // Agrega algunos elementos predeterminados a la ciudad.
    }

    /**
     * Obtiene el tamaño de la ciudad (tamaño de la matriz de ubicaciones).
     * 
     * @return El tamaño de la ciudad (un valor fijo de 25).
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Obtiene el elemento en una ubicación específica de la ciudad.
     * 
     * @param r La fila de la ubicación.
     * @param c La columna de la ubicación.
     * @return El objeto almacenado en la ubicación (puede ser null si está vacío).
     */
    public Item getItem(int r, int c) {
        return locations[r][c];
    }

    /**
     * Establece un elemento en una ubicación específica de la ciudad.
     * 
     * @param r La fila de la ubicación.
     * @param c La columna de la ubicación.
     * @param e El objeto que se quiere colocar en la ubicación.
     */
    public void setItem(int r, int c, Item e) {
        locations[r][c] = e;
    }

    /**
     * Coloca algunos elementos predeterminados (Personas, Caminantes, Semáforos, etc.) en la ciudad.
     */
    public void someItems() {
        Person Adan = new Person(this, 10, 10);
        Person Eva = new Person(this, 15, 15);
        Walker nombreraro1 = new Walker(this, 12, 14);
        Walker nombreraro2 = new Walker(this, 19, 19);
        TrafficLight alerta = new TrafficLight(this, 0, 0);
        TrafficLight emergencia = new TrafficLight(this, 0, 24);
        Wallflower Useche = new Wallflower(this, 5, 5);
        Wallflower Patiño = new Wallflower(this, 20, 20);
        Building Colpatria = new Building(this,7,15);
        Walker nombreraro3 = new Walker(this, 2, 24);
        Walker nombreraro4 = new Walker(this, 9, 6);
        Walker nombreraro5 = new Walker(this, 19, 4);
        Walker nombreraro6 = new Walker(this, 9, 21);
        Building Bancolombia = new Building(this,8,5);
        Person HijoAdan = new Person(this, 7, 3);
        Person HijoEva = new Person(this, 11, 21);
        Beast Godzilla = new Beast(this, 19,19);
        Beast KingKing = new Beast(this, 3, 20);
    }

    /**
     * Cuenta la cantidad de vecinos no vacíos en una ubicación específica.
     * Los vecinos son las casillas adyacentes en las direcciones: norte, sur, este, oeste y diagonales.
     * 
     * @param r La fila de la ubicación.
     * @param c La columna de la ubicación.
     * @return El número de vecinos no vacíos.
     */
    public int neighborsEquals(int r, int c) {
        int num = 0;
        if (inLocations(r, c) && locations[r][c] != null) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr != 0 || dc != 0) {
                        int nr = r + dr;
                        int nc = c + dc;
                        if (inLocations(nr, nc) && locations[nr][nc] != null) {
                            num++;
                        }
                    }
                }
            }
        }
        return num;
    }
    
    public int countDifferentNeighbors(int r, int c){
        int cont = 0;
        if (inLocations(r, c) && locations[r][c] != null) {
            Item currentItem = locations[r][c];
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr != 0 || dc != 0) {
                        int nr = r + dr;
                        int nc = c + dc;
                        if (inLocations(nr, nc) && locations[nr][nc] != null && !(locations[nr][nc].getColor().equals(currentItem.getColor()))) {
                            cont++;
                        }
                    }
                }
            }
        }
        return cont;
    }

    /**
     * Mueve una persona en la ciudad hacia una dirección específica (norte o sur).
     * Si la dirección es válida y el espacio está vacío, se actualiza la posición de la persona.
     * 
     * @param p La persona que se desea mover.
     * @param direction La dirección en la que la persona se mueve: "n" para norte o "s" para sur.
     */
    public void move(Person p, String direction) {
        int rowP = p.getRow();
        int columnP = p.getColumn();
        int rowNew = rowP;
        int columnNew = columnP;

        switch (direction) {
            case "n":
                if (inLocations(rowP - 1, columnP) && isEmpty(rowP - 1, columnP)) {
                    rowNew -= 1;
                    alterPosition(rowP, columnP, rowNew, columnNew, p);
                    p.row -= 1;
                }
                break;
            case "s":
                if (inLocations(rowP + 1, columnP) && isEmpty(rowP + 1, columnP)) {
                    rowNew += 1;
                    alterPosition(rowP, columnP, rowNew, columnNew, p);
                    p.row += 1;
                }
                break;
        }
    }

    /**
     * Alterar la posición de una persona en la ciudad, moviéndola de una ubicación a otra.
     * 
     * @param row La fila de la ubicación actual de la persona.
     * @param column La columna de la ubicación actual de la persona.
     * @param rowN La nueva fila de la persona.
     * @param columnN La nueva columna de la persona.
     * @param p La persona que se mueve.
     */
    public void alterPosition(int row, int column, int rowN, int columnN, Person p) {
        this.setItem(rowN, columnN, p);
        this.setItem(row, column, null);
    }

    /**
     * Verifica si una ubicación en la ciudad está vacía.
     * 
     * @param r La fila de la ubicación.
     * @param c La columna de la ubicación.
     * @return true si la ubicación está vacía, false si contiene algún objeto.
     */
    public boolean isEmpty(int r, int c) {
        return (inLocations(r, c) && locations[r][c] == null);
    }

    /**
     * Verifica si las coordenadas proporcionadas están dentro de los límites válidos de la ciudad.
     * 
     * @param r La fila a verificar.
     * @param c La columna a verificar.
     * @return true si las coordenadas están dentro de los límites, false si están fuera de los límites.
     */
    public boolean inLocations(int r, int c) {
        return (0 <= r && r < SIZE && 0 <= c && c < SIZE);
    }

    /**
     * Realiza una acción de actualización sobre todos los elementos en la ciudad.
     * Llama a los métodos `decide()` y `change()` de cada objeto en la ciudad.
     * Este método puede ser utilizado para avanzar el estado de la ciudad en el tiempo (como cambiar el color de los semáforos).
     */
    public void ticTac() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (locations[i][j] != null) {
                    locations[i][j].decide();
                    locations[i][j].change();
                }
            }
        }
    }
}

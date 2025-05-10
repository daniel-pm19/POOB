package domain;

import java.io.*;

/**
 * La clase City representa una ciudad de tamaño fijo en la que se pueden colocar diferentes objetos (como personas, semáforos, etc.).
 * La ciudad tiene un tamaño definido y una matriz que almacena los objetos en diferentes ubicaciones dentro de la ciudad.
 */
public class City implements Serializable {
    
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

    //--------------------------------CREANDO LA MAQUETA (MODELO)------------------------------
    public City openFile(File fileName) throws CityException {
        throw new CityException("Opcion openFile en construccion. Archivo: " + fileName.getName());
    }

    public void saveFile(File fileName) throws CityException {
        throw new CityException("Opcion saveFile en construccion. Archivo: " + fileName.getName());
    }

    public void importFile(File fileName) throws CityException {
        throw new CityException("Opcion importFile en construccion. Archivo: " + fileName.getName());
    }

    public void exportFile(File fileName) throws CityException {
        throw new CityException("Opcion exportFile en construccion. Archivo: " + fileName.getName());
    }

    //-------------------------------IMPLEMENTANDO IMPORTAR Y EXPORTAR------------------------------

    public City open00(File fileName) throws CityException {
        if(!fileName.exists()) {
            System.out.println("File not found");
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Object deserialize = in.readObject();
            if (!(deserialize instanceof City)) {
                System.out.println("Error al deserializar el archivo");
                return null;
            }
            City newCity = (City) deserialize;
            return newCity;
        } catch (IOException | ClassNotFoundException e) {
            throw new CityException("Error al abrir el archivo " + fileName.getName());
        }
    }

    public void save00(File fileName) throws CityException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))){
            out.writeObject(this);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo");
        }
    }
    public void import00(File fileName) throws CityException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length != 3) continue;
                String type = parts[0];
                int row = Integer.parseInt(parts[1]);
                int col = Integer.parseInt(parts[2]);
                Item item = null;
                switch (type) {
                    case "Person":
                        item = new Person(this, row, col);
                        break;
                    case "Walker":
                        item = new Walker(this, row, col);
                        break;
                    case "Wallflower":
                        item = new Wallflower(this, row, col);
                        break;
                    case "Beast":
                        item = new Beast(this, row, col);
                        break;
                    case "Building":
                        item = new Building(this, row, col);
                        break;
                    case "TraffficLight":
                        item = new TrafficLight(this, row, col);
                        break;
                    default:
                        System.out.println("Unknown type: " + type);
                }
                if (item != null) {
                    locations[row][col] = item;
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new CityException("Error importing file " + fileName.getName());
        }
    }

    public void export00(File fileName) throws CityException {
        try(FileWriter fw = new FileWriter(fileName)) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Item item = locations[r][c];
                    if (item != null) {
                        String type = item.getClass().getSimpleName();
                        fw.write(type + " " + r + " " + c + "\n");
                    }
                }
            }
        } catch (IOException e) {
            throw new CityException("Error exporting file: " + fileName.getName());
        }
    }

    //-------------------------------PERFECCIONANDO SALVAR Y SALIR--------------------------------

    public City open01(File fileName) throws CityException {
        if(fileName == null) {
            throw new CityException("File cannot be null");
        }
        if(!fileName.exists()) {
            throw new CityException("File not found");
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Object deserialize = in.readObject();
            if (!(deserialize instanceof City)) {
                throw new CityException("Error deserializing the file");
            }
            City newCity = (City) deserialize;
            return newCity;
        } catch (IOException | ClassNotFoundException e) {
            throw new CityException("An error occurred trying to open the file: " + fileName.getName());
        }
    }

    public void save01(File fileName) throws CityException {
        if(fileName == null) {
            throw new CityException("File cannot be null");
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))){
            out.writeObject(this);
        } catch (IOException e) {
            throw new CityException(CityException.FILE_NOT_SAVED + fileName.getName());
        }
    }

    //-------------------------------PERFECCIONANDO IMPORTAR Y EXPORTAR--------------------------------

    public void import01(File fileName) throws CityException {
        if(fileName == null) {
            throw new CityException(CityException.FILE_NOT_FOUND + ": File cannot be null");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length != 3) continue;
                String type = parts[0];
                int row = Integer.parseInt(parts[1]);
                int col = Integer.parseInt(parts[2]);
                Item item = switch (type) {
                    case "Person" -> new Person(this, row, col);
                    case "Walker" -> new Walker(this, row, col);
                    case "Wallflower" -> new Wallflower(this, row, col);
                    case "Beast" -> new Beast(this, row, col);
                    case "Building" -> new Building(this, row, col);
                    case "TrafficLight" -> new TrafficLight(this, row, col);
                    default -> throw new CityException("Unknown type: " + type);
                };
                locations[row][col] = item;
            }
        } catch (IOException | NumberFormatException e) {
            throw new CityException(CityException.IMPORTING_FILE_ERROR + ": " + fileName.getName());
        }
    }

    public void export01(File fileName) throws CityException {
        if(fileName == null) {
            throw new CityException(CityException.NULL_FILE);
        }
        try(FileWriter fw = new FileWriter(fileName)) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Item item = locations[r][c];
                    if (item != null) {
                        String type = item.getClass().getSimpleName();
                        fw.write(type + " " + r + " " + c + "\n");
                    }
                }
            }
        } catch (IOException e) {
            throw new CityException(CityException.EXPORTING_FILE_ERROR + ": " + fileName.getName());
        }
    }

    //-------------------------------PERFECCIONANDO IMPORTATR Y EXPORTAR (MINICOMPILADOR)--------------------------------


    public void import02(File fileName) throws CityException {
        if(fileName == null) {
            throw new CityException(CityException.FILE_NOT_FOUND + ": File cannot be null");
        }
        StringBuilder errorLog = new StringBuilder();
        int errorLine = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                errorLine++;
                line = line.trim();
                if (line.trim().isEmpty()) continue;

                String[] parts = line.trim().split(" ");

                if (parts.length != 3){
                    String errorMessage = String.format("Error in line %d: invalid format '%s'%n", errorLine, line);
                    errorLog.append(errorMessage);
                    throw new CityException(errorMessage);
                }

                String type = parts[0];

                if(!isValidType(type)) {
                    String errorMessage = String.format("Error in line %d: invalid type '%s'%n", errorLine, type);
                    errorLog.append(errorMessage);
                    throw new CityException(errorMessage);
                }
                int row, col;

                try {
                    row = Integer.parseInt(parts[1]);
                    col = Integer.parseInt(parts[2]);
                }catch (NumberFormatException e) {
                    String errorMessage = String.format("Error in line %d: invalid number format '%s %s' '%n", errorLine, parts[1], parts[2]);
                    errorLog.append(errorMessage);
                    throw new CityException(errorMessage);
                }

                if(!inLocations(row, col)) {
                    errorLog.append(String.format("Error in line %d: Coordinates out of bounds: row %d, column %d%n", errorLine, row, col));
                    throw new CityException("Coordinates out of bounds: row " + row + ", column " + col);
                }

                if (locations[row][col] != null) {
                    errorLog.append(String.format("Error in line %d: the position (%d, %d) is occupied%n", errorLine, row, col));
                    throw new CityException("Position already occupied: row " + row + ", column " + col);
                }

                try{
                    Item item = switch (type) {
                        case "Person" -> new Person(this, row, col);
                        case "Walker" -> new Walker(this, row, col);
                        case "Wallflower" -> new Wallflower(this, row, col);
                        case "Beast" -> new Beast(this, row, col);
                        case "Building" -> new Building(this, row, col);
                        case "TrafficLight" -> new TrafficLight(this, row, col);
                        default -> throw new CityException("Unknown type: " + type);
                    };
                    locations[row][col] = item;
                } catch (NumberFormatException e) {
                    errorLog.append(String.format("Error in line %d: %s%n", errorLine, e.getMessage()));
                    throw new CityException("Invalid number format in line: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new CityException(CityException.IMPORTING_FILE_ERROR + ": " + fileName.getName());
        }
    }

    private boolean isValidType(String type) {
        return switch (type){
            case "Person", "Walker", "Wallflower", "Beast", "Building", "TrafficLight" -> true;
            default -> false;
        };
    }

    public void export02(File fileName) throws CityException {
        if(fileName == null) {
            throw new CityException(CityException.NULL_FILE);
        }
        try(FileWriter fw = new FileWriter(fileName)) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Item item = locations[r][c];
                    if (item != null) {
                        String type = item.getClass().getSimpleName();
                        fw.write(type + " " + r + " " + c + "\n");
                    }
                }
            }
        } catch (IOException e) {
            throw new CityException(CityException.EXPORTING_FILE_ERROR + ": " + fileName.getName());
        }
    }

    //-------------------------------PERFECCIONANDO IMPORTATR Y EXPORTAR (BONO)--------------------------------

    public void import03(File fileName) throws CityException {
        if(fileName == null) {
            throw new CityException(CityException.FILE_NOT_FOUND + ": File cannot be null");
        }
        StringBuilder errorLog = new StringBuilder();
        int errorLine = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                errorLine++;
                line = line.trim();
                if (line.trim().isEmpty()) continue;

                String[] parts = line.trim().split(" ");

                if (parts.length != 3){
                    String errorMessage = String.format("Error in line %d: invalid format '%s'%n", errorLine, line);
                    errorLog.append(errorMessage);
                    throw new CityException(errorMessage);
                }

                String type = parts[0];
                int row, col;

                try {
                    row = Integer.parseInt(parts[1]);
                    col = Integer.parseInt(parts[2]);
                }catch (NumberFormatException e) {
                    String errorMessage = String.format("Error in line %d: invalid number format '%s %s' '%n", errorLine, parts[1], parts[2]);
                    errorLog.append(errorMessage);
                    throw new CityException(errorMessage);
                }

                if(!inLocations(row, col)) {
                    errorLog.append(String.format("Error in line %d: Coordinates out of bounds: row %d, column %d%n", errorLine, row, col));
                    throw new CityException("Coordinates out of bounds: row " + row + ", column " + col);
                }

                if (locations[row][col] != null) {
                    errorLog.append(String.format("Error in line %d: the position (%d, %d) is occupied%n", errorLine, row, col));
                    throw new CityException("Position already occupied: row " + row + ", column " + col);
                }

                try{
                    Class<?> itemClass = Class.forName("domain." + type);
                    Item item = (Item) itemClass.getDeclaredConstructor(City.class, int.class, int.class).newInstance(this, row, col);
                    locations[row][col] = item;

                } catch (ClassNotFoundException e) {
                    errorLog.append(String.format("Error in line %d: %s%n", errorLine, e.getMessage()));
                    throw new CityException("Invalid number format in line: " + line);
                } catch (ReflectiveOperationException e) {
                    String errorMessage = String.format("Error en línea %d: no se pudo crear objeto de tipo '%s'", errorLine, type);
                    errorLog.append(errorMessage).append("\n");
                    throw new CityException(errorMessage);
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new CityException(CityException.IMPORTING_FILE_ERROR + ": " + fileName.getName());
        }
    }

    public void export03(File fileName) throws CityException {
        if(fileName == null) {
            throw new CityException(CityException.NULL_FILE);
        }
        try(FileWriter fw = new FileWriter(fileName)) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Item item = locations[r][c];
                    if (item != null) {
                        String type = item.getClass().getSimpleName();
                        fw.write(type + " " + r + " " + c + "\n");
                    }
                }
            }
        } catch (IOException e) {
            throw new CityException(CityException.EXPORTING_FILE_ERROR + ": " + fileName.getName());
        }
    }
}

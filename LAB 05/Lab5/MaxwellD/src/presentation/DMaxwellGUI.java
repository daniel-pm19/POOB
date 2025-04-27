package presentation;

import domain.MaxweeDException;
import domain.MaxwellD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
//en esta GUI comentamos todo porque es la primera vez que lo hacemos y es algo molesto hacerlo =)
/**
 * Clase principal que representa la interfaz gráfica del juego Maxwell's Demon
 * Implementa toda la lógica de visualización y control de la interfaz
 */
public class DMaxwellGUI extends JFrame {
    // Variables para el manejo de las pantallas (menú y juego)
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JPanel gamePanel;

    // Botones del menú principal
    private JButton playButton;
    private JButton exitButton;

    // Componentes del menú superior
    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenu menuAjustes;
    private JMenu menuAyuda;

    // Items del menú
    private JMenuItem itemNuevo;    // Opción "Nuevo Juego"
    private JMenuItem itemAbrir;    // Opción "Abrir Partida"
    private JMenuItem itemSalvar;   // Opción "Guardar Partida"
    private JMenuItem itemSalir;    // Opción "Salir"
    private JMenuItem itemOpcion;   // Opción "Configuración"
    private JMenuItem itemAyuda;    // Opción "Acerca de"

    // Lógica del juego
    private MaxwellD simulation;    // Instancia del modelo del juego (el dominio)

    // Componentes gráficos del juego
    private BoardPanel boardPanel;  // Panel personalizado que dibuja el tablero
    private JPanel infoPanel;       // Panel que muestra información del juego
    private JLabel movesLabel;      // Etiqueta para mostrar movimientos
    private JLabel particlesLabel;  // Etiqueta para mostrar partículas
    private JLabel absorbLabel;
    private JLabel statusLabel;     // Etiqueta para mostrar estado del juego

    // Configuración del juego
    private int selectedSize = 5;           // Tamaño seleccionado del tablero (por defecto 5x5)
    private int lastRedParticles = 2;       // Última cantidad de partículas rojas usadas
    private int lastBlueParticles = 2;      // Última cantidad de partículas azules usadas
    private int lastHoles = 1;              // Última cantidad de huecos usados
        private JButton restartButton;          // Botón para reiniciar el juego
    private Map<Integer, Color> particleColors = new HashMap<>();  // Mapa para colores personalizados de partículas

    // Botones para controlar el movimiento
    private JButton upButton, downButton, leftButton, rightButton;  // Botones de flecha para mover partículas

    /**
     * Constructor principal de la interfaz gráfica
     * Inicializa todos los componentes y prepara la ventana
     */
    public DMaxwellGUI() {
        prepareElements();        // Prepara los elementos visuales principales
        prepareElementsMenu();   // Prepara la barra de menú superior
        prepareActions();        // Configura las acciones de los botones principales
        prepareActionsMenu();    // Configura las acciones del menú superior
    }

    /**
     * Método para preparar los elementos visuales principales de la ventana
     * Configura el tamaño, posición y paneles principales
     */
    public void prepareElements() {
        setTitle("Maxwell Demon");  // Título de la ventana

        // Configuramos el tamaño de la ventana como la mitad del tamaño de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = (int)(screenSize.height * 0.5); //lo multiplicamos por un medio para que de un cuarto (aun no lo capto)
        int screenWidth = (int)(screenSize.width * 0.5);
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla

        // Configuramos el sistema de tarjetas para cambiar entre pantallas
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Preparamos los paneles del menú y del juego
        prepareMenuPanel();
        prepareGamePanel();

        // Añadimos el panel principal a la ventana
        add(mainPanel);

        // Configuramos el comportamiento al cerrar la ventana
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /**
     * Método para preparar la barra de menú superior
     * Configura todos los menús y sus opciones
     */
    public void prepareElementsMenu() {
        menuBar = new JMenuBar();  // Creamos la barra de menú

        // Creamos los menús principales
        menuArchivo = new JMenu("MaxwellD");
        menuAjustes = new JMenu("Ajustes");
        menuAyuda = new JMenu("Ayuda");

        // Creamos los items del menú
        itemNuevo = new JMenuItem("Nuevo Juego");
        itemAbrir = new JMenuItem("Abrir Partida");
        itemSalvar = new JMenuItem("Guardar Partida");
        itemSalir = new JMenuItem("Salir");
        itemOpcion = new JMenuItem("Configuración");
        itemAyuda = new JMenuItem("Acerca de...");

        // Añadimos los items al menú Archivo
        menuArchivo.add(itemNuevo);
        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemSalvar);
        menuArchivo.addSeparator();  // Línea separadora
        menuArchivo.add(itemSalir);

        // Añadimos items a los otros menús
        menuAjustes.add(itemOpcion);
        menuAyuda.add(itemAyuda);

        // Añadimos los menús a la barra de menú
            menuBar.add(menuArchivo);
        menuBar.add(menuAjustes);
        menuBar.add(menuAyuda);

        // Establecemos la barra de menú en la ventana
        setJMenuBar(menuBar);
    }

    /**
     * Método para preparar el panel del menú principal
     * Contiene el título, botones de juego y salir
     */
    private void prepareMenuPanel() {
        // Configuramos el panel principal con BorderLayout
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(Color.WHITE);

        // Panel superior con el título
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 11, 175));
        topPanel.setPreferredSize(new Dimension(getWidth(), (int)(getHeight()*0.2)));

        // Creamos y configuramos el título
        JLabel title = new JLabel("MAXWELL DEMONS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        topPanel.add(title, BorderLayout.CENTER);

        // Añadimos el panel superior al menú principal
        menuPanel.add(topPanel, BorderLayout.NORTH);

        // Panel central que contendrá los botones
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        // Configuración para posicionar los elementos
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);  // Márgenes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Panel para los botones
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 20));
        buttonPanel.setOpaque(false);  // Hacemos el panel transparente

        // Creamos los botones
        playButton = createRedButton("Jugar");
        exitButton = createRedButton("Salir");

        // Añadimos los botones al panel
        buttonPanel.add(playButton);
        buttonPanel.add(exitButton);

        // Posicionamos el panel de botones en el centro
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(buttonPanel, gbc);

        // Añadimos el panel central al menú principal
        menuPanel.add(centerPanel, BorderLayout.CENTER);

        // Panel inferior decorativo
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(186, 0, 0));
        bottomPanel.setPreferredSize(new Dimension(getWidth(), (int)(getHeight()*0.2)));
        menuPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Añadimos el panel del menú al panel principal
        mainPanel.add(menuPanel, "menu");
    }

    /**
     * Método para crear botones rojos estilizados (usados en el menú principal)
     * @param text Texto que mostrará el botón
     * @return JButton configurado con el estilo rojo
     */
    private JButton createRedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(250, 60));
        button.setBackground(new Color(200, 0, 0));  // Fondo rojo

        // Configuramos un borde compuesto para mejor apariencia
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 0, 0), 2),  // Borde exterior
                BorderFactory.createEmptyBorder(10, 25, 10, 25)           // Margen interior
        ));

        button.setFocusPainted(false);  // Quitamos el efecto de foco (sino no funciona bien)
        return button;
    }

    /**
     * Método para crear botones de flecha estilizados (usados para los controles de movimiento)
     * @param text Símbolo de flecha (↑, ↓, ←, →)
     * @return JButton configurado con el estilo de flecha
     */
    private JButton createArrowButton(String text) {
        // Creamos un botón personalizado con bordes redondeados
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                // Cambiamos el color según el estado del botón
                if (getModel().isArmed()) {
                    g.setColor(new Color(255, 255, 255));  // Color cuando se presiona
                } else if (getModel().isRollover()) {
                    g.setColor(new Color(240, 240, 240));   // Color cuando el mouse pasa por encima
                } else {
                    g.setColor(getBackground());
                }

                // Configuramos gráficos suavizados
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Dibujamos el fondo con bordes redondeados
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Dibujamos el texto
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Dibujamos un borde personalizado
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0));  // Color del borde
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                g2.dispose();
            }
        };

        // Configuramos propiedades del botón
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(80, 80));
        button.setBackground(new Color(255, 255, 255));  // Fondo claro
        button.setForeground(new Color(0, 0, 0));        // Texto negro
        button.setFocusPainted(false);                   // Sin efecto de foco
        button.setBorderPainted(false);                  // Sin borde por defecto
        button.setContentAreaFilled(false);              // Importante para nuestro diseño personalizado

        // Añadimos efectos de hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Cambia el cursor a mano
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  // Vuelve al cursor normal
            }
        });

        return button;
    }

    /**
     * Método para preparar el panel del juego
     * Contiene el tablero, información del juego y controles
     */
    private void prepareGamePanel() {
        // Configuramos el panel principal del juego
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(new Color(255, 255, 255));

        // Panel de información (parte superior)
        infoPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setBackground(new Color(0, 0, 0));

        // Configuramos las etiquetas de información
        movesLabel = new JLabel("Movimientos: 0", SwingConstants.CENTER);
        movesLabel.setForeground(Color.WHITE);
        movesLabel.setFont(new Font("Arial", Font.BOLD, 14));

        particlesLabel = new JLabel("Partículas: 🔴2 🔵2", SwingConstants.CENTER);
        particlesLabel.setForeground(Color.WHITE);
        particlesLabel.setFont(new Font("Arial", Font.BOLD, 14));

        absorbLabel = new JLabel("Sin registro", SwingConstants.CENTER);
        absorbLabel.setForeground(Color.WHITE);

        statusLabel = new JLabel("Estado: Iniciado", SwingConstants.CENTER);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Añadimos las etiquetas al panel de información
        infoPanel.add(movesLabel);
        infoPanel.add(particlesLabel);
        infoPanel.add(absorbLabel);
        infoPanel.add(statusLabel);

        // Añadimos el panel de información al juego
        gamePanel.add(infoPanel, BorderLayout.NORTH);

        // Panel principal que contiene el tablero y los botones de movimiento
        JPanel gameBoardPanel = new JPanel(new BorderLayout());

        // Creamos el panel del tablero y lo envolvemos en un JScrollPane
        boardPanel = new BoardPanel();
        JScrollPane scrollPane = new JScrollPane(boardPanel);

        // Configuramos el scrollPane para que se adapte al tamaño del tablero
        scrollPane.setPreferredSize(new Dimension(selectedSize * 90 + 20, selectedSize * 70 + 30));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Panel para los botones de movimiento (derecha del tablero)
        JPanel movementPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        movementPanel.setPreferredSize(new Dimension(180, 150));
        movementPanel.setBackground(new Color(255, 255, 255));

        // Creamos los botones de flecha
        upButton = createArrowButton("↑");
        downButton = createArrowButton("↓");
        leftButton = createArrowButton("←");
        rightButton = createArrowButton("→");

        // Organizamos los botones en forma de cruz
        movementPanel.add(new JLabel());  // Espacio vacío
        movementPanel.add(upButton);      // Botón arriba
        movementPanel.add(new JLabel());  // Espacio vacío

        movementPanel.add(leftButton);    // Botón izquierda
        movementPanel.add(new JLabel());  // Espacio vacío (centro)
        movementPanel.add(rightButton);   // Botón derecha

        movementPanel.add(new JLabel());  // Espacio vacío
        movementPanel.add(downButton);    // Botón abajo
        movementPanel.add(new JLabel());  // Espacio vacío

        // Panel contenedor para centrar el tablero y los botones
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(new Color(255, 255, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 20);  // Espacio entre tablero y botones
        centerWrapper.add(scrollPane, gbc);     // Añadimos el tablero

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        centerWrapper.add(movementPanel, gbc);  // Añadimos los botones de movimiento

        // Añadimos el contenedor al panel principal del juego
        gameBoardPanel.add(centerWrapper, BorderLayout.CENTER);

        // Añadimos el panel del juego al panel principal
        gamePanel.add(gameBoardPanel, BorderLayout.CENTER);

        // Panel de control inferior (reiniciar y volver al menú)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.setBackground(new Color(0, 0, 0));

        // Creamos los botones de control
        restartButton = new JButton("Reiniciar");
        JButton backButton = new JButton("Menú");

        // Añadimos los botones al panel
        controlPanel.add(restartButton);
        controlPanel.add(backButton);

        // Configuramos las acciones de los botones
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "menu"));  // Volver al menú
        restartButton.addActionListener(e -> {
            try {
                restartGame();
            } catch (MaxweeDException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Añadimos el panel de control al juego
        gamePanel.add(controlPanel, BorderLayout.SOUTH);

        // Añadimos el panel del juego al panel principaln (para cambiar entre uno y otro)
        mainPanel.add(gamePanel, "game");
    }

    /**
     * Método para configurar las acciones de los botones principales
     */
    public void prepareActions() {
        // Configuramos las acciones de los botones del menú
        playButton.addActionListener(e -> startNewGame());  // Iniciar nuevo juego
        exitButton.addActionListener(e -> confirmExit());   // Salir de la aplicación

        // Configuramos las acciones de los botones de movimiento
        upButton.addActionListener(e -> moveParticles("Up"));
        downButton.addActionListener(e -> moveParticles("Down"));
        leftButton.addActionListener(e -> moveParticles("Left"));
        rightButton.addActionListener(e -> moveParticles("Right"));

        // Configuramos el comportamiento al cerrar la ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                confirmExit();  // Mostramos confirmación al cerrar
            }
        });
    }

    /**
     * Método para mover las partículas según la dirección indicada
     * @param direction Dirección del movimiento ("Up", "Down", "Left", "Right") como lo requiere move en dominio
     */
    private void moveParticles(String direction) {
        simulation.move(direction);// Ejecutamos el movimiento en el modelo
        //Si se absorbe la primera partícula, lanzamos la advertencia.
        if(simulation.absorbed() == 1) {
            JOptionPane.showMessageDialog(
                    null,
                    "Una partícula fue absorbida, ya no es posible ganar (reiniciar para ganar).",
                    "Aviso Amistoso",
                    JOptionPane.WARNING_MESSAGE
            );
        }
        updateInfoPanel();              // Actualizamos la información mostrada
        boardPanel.repaint();           // Redibujamos el tablero

        // Comprobamos si se ha alcanzado la meta
        if (simulation.isGoal()) {
            JOptionPane.showMessageDialog(this,
                    "¡Meta alcanzada! Todas las partículas están en su lado correcto.",
                    "Juego Completado",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Método para configurar las acciones del menú superior
     */
    public void prepareActionsMenu() {
        // Configuramos las acciones de los items del menú Archivo
        itemNuevo.addActionListener(e -> startNewGame());
        itemAbrir.addActionListener(e -> openGame());
        itemSalvar.addActionListener(e -> saveGame());
        itemSalir.addActionListener(e -> confirmExit());

        // Configuramos la acción del item Configuración
        itemOpcion.addActionListener(e -> {
            // Opciones de tamaño de tablero
            String[] options = {"5x5", "7x7", "9x9", "11x11"};

            // Mostramos diálogo para seleccionar tamaño
            int selection = JOptionPane.showOptionDialog(this,
                    "Seleccione el tamaño del tablero:",
                    "Configuración",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);

            // Si se seleccionó una opción, actualizamos el tamaño
            if (selection >= 0) {
                selectedSize = 5 + (selection * 2);  // Calculamos el tamaño (5,7,9,11)
                JOptionPane.showMessageDialog(this,
                        "El nuevo tamaño (" + selectedSize + "x" + selectedSize + ") se aplicará en el próximo juego.",
                        "Configuración guardada",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Configuramos la acción del item Acerca de
        itemAyuda.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Maxwell Demons Discreto\n" +
                            "Versión 1.8\n" +
                            "Desarrollado por:\n" +
                            "- Daniel Patiño\n" +
                            "- Daniel Useche\n" +
                            "- Instagram: Demasiados fans sorry =)\n\n"+
                            "Universidad Escuela de Ingenieros",
                    "Acerca de Nosotros (POOB FANS)",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    /**
     * Método para iniciar un nuevo juego
     * Muestra un diálogo para configurar las partículas y huecos
     */
    private void startNewGame() {
        // Creamos un panel para la entrada de datos
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        // Campos de texto con los valores por defecto
        JTextField redField = new JTextField(String.valueOf(lastRedParticles));
        JTextField blueField = new JTextField(String.valueOf(lastBlueParticles));
        JTextField holesField = new JTextField(String.valueOf(lastHoles));

        // Añadimos las etiquetas y campos al panel
        inputPanel.add(new JLabel("Partículas Rojas:"));
        inputPanel.add(redField);
        inputPanel.add(new JLabel("Partículas Azules:"));
        inputPanel.add(blueField);
        inputPanel.add(new JLabel("Huecos:"));
        inputPanel.add(holesField);

        // Mostramos el diálogo de configuración
        int result = JOptionPane.showConfirmDialog(
                this,
                inputPanel,
                "Configurar Nuevo Juego",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        // Si el usuario hizo clic en OK
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Obtenemos los valores ingresados
                int red = Integer.parseInt(redField.getText());
                int blue = Integer.parseInt(blueField.getText());
                int holes = Integer.parseInt(holesField.getText());

                // Guardamos los valores para futuros juegos
                lastRedParticles = red;
                lastBlueParticles = blue;
                lastHoles = holes;

                // Creamos una nueva simulación con los parámetros dados

                simulation = new MaxwellD(selectedSize, selectedSize, red, blue, holes);

                particleColors.clear();  // Limpiamos los colores personalizados

                // Configuramos el panel del tablero
                boardPanel.setSimulation(simulation);

                // Ajustamos el tamaño preferido del tablero según el tamaño seleccionado
                int cellSize = calculateCellSize(selectedSize);  // Calculamos tamaño dinámico de celdas
                boardPanel.setPreferredSize(new Dimension(selectedSize * cellSize, selectedSize * cellSize));
                boardPanel.revalidate();  // Forzamos el recálculo del layout

                // Centramos la vista del scroll
                JScrollPane scrollPane = (JScrollPane)SwingUtilities.getAncestorOfClass(JScrollPane.class, boardPanel);
                if (scrollPane != null) {
                    scrollPane.getViewport().setViewPosition(new Point(0, 0));
                }

                updateInfoPanel();  // Actualizamos la información mostrada

                // Cambiamos a la pantalla de juego
                cardLayout.show(mainPanel, "game");
                boardPanel.requestFocusInWindow();

            } catch (NumberFormatException e) {
                // Mostramos error si los valores no son numéricos
                JOptionPane.showMessageDialog(this,
                        "Por favor ingrese valores numéricos válidos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Método auxiliar para calcular el tamaño de las celdas según el tamaño del tablero
     * @param size Tamaño del tablero (5, 7, 9, 11)
     * @return Tamaño en píxeles para cada celda
     */
    private int calculateCellSize(int size) {
        //segun nosotros va en dominio pero no sabemos integrarlo alli, asi que lo dejamos aqui
        // Para tableros grandes, reducimos el tamaño de las celdas
        if (size == 11) return 34;
        if (size >= 9) return 42;
        if (size >= 7) return 53;
        return 75;  // Tamaño por defecto para 5x5
    }

    /**
     * Método para reiniciar el juego con los mismos parámetros
     */
    private void restartGame() throws MaxweeDException {
        // Creamos una nueva simulación con los mismos parámetros
        simulation = new MaxwellD(selectedSize, selectedSize, lastRedParticles, lastBlueParticles, lastHoles);

        simulation = new MaxwellD(selectedSize, selectedSize, lastRedParticles, lastBlueParticles, lastHoles);


        // Configuramos el panel del tablero
        boardPanel.setSimulation(simulation);

        // Ajustamos el tamaño preferido del tablero
        int cellSize = calculateCellSize(selectedSize);
        boardPanel.setPreferredSize(new Dimension(selectedSize * cellSize, selectedSize * cellSize));
        boardPanel.revalidate();  // Forzamos el recálculo del layout

        // Centramos la vista del scroll
        JScrollPane scrollPane = (JScrollPane)SwingUtilities.getAncestorOfClass(JScrollPane.class, boardPanel);
        if (scrollPane != null) {
            scrollPane.getViewport().setViewPosition(new Point(0, 0));
        }

        updateInfoPanel();  // Actualizamos la información mostrada
        boardPanel.repaint();  // Redibujamos el tablero
        boardPanel.requestFocusInWindow();
    }

    /**
     * Método para actualizar el panel de información del juego
     * Muestra movimientos, partículas y estado actual
     */
    private void updateInfoPanel() {
        if (simulation == null) return;  // Si no hay simulación, no hacemos nada

        // Obtenemos las partículas en su posición correcta
        int[] correctParticles = simulation.rightParticle();
        int correctRed = correctParticles[0];
        int correctBlue = correctParticles[1];

        // Actualizamos las etiquetas
        movesLabel.setText("Movimientos: " + simulation.getMoves().size());
        //usamos emojis para que se vea (Aveces por formato solo se ven cuadros)
        particlesLabel.setText(String.format("Partículas: 🔴Red: %d/%d 🔵Blue: %d/%d",
                correctRed, lastRedParticles, correctBlue, lastBlueParticles));
        absorbLabel.setText("Absorbidas: "+ simulation.absorbed());
        // Comprobamos si se ha completado el juego
        if (simulation.isGoal()) {
            statusLabel.setText("Estado: COMPLETADO!");
            statusLabel.setForeground(new Color(184, 0, 213));
        } else {
            statusLabel.setText("Estado: Jugando");
            statusLabel.setForeground(Color.WHITE);
        }
    }

    /**
     * Método para confirmar la salida de la aplicación
     * Muestra un diálogo de confirmación
     */
    private void confirmExit() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que quieres salir?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // Si el usuario confirma, salimos
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Método para abrir una partida guardada (simulación)
     */
    private void openGame() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this,
                    "Partida cargada (simulación).\nArchivo: " + selectedFile.getName());
        }
    }

    /**
     * Método para guardar la partida actual (simulación)
     */
    private void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this,
                    "Partida guardada (simulación).\nArchivo: " + selectedFile.getName());
        }
    }

    /**
     * Clase interna que representa el panel del tablero de juego
     * Se encarga de dibujar todas las celdas, partículas, demonios y huecos
     */
    private class BoardPanel extends JPanel {
        private MaxwellD simulation;
        private int selectedParticle = -1; // Índice de la partícula seleccionada (-1 = ninguna)
        private boolean colorDialogOpen = false;  // Flag para evitar múltiples diálogos de color

        /**
         * Constructor del panel del tablero
         */
        public BoardPanel() {
            setFocusable(true);            // Permitimos que el panel reciba foco
            setRequestFocusEnabled(true);  // Permitimos que pueda ser enfocado

            // Añadimos un listener para clicks del ratón
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (simulation == null || colorDialogOpen) return;  // Si no hay simulación o ya hay diálogo abierto

                    // Calculamos la celda clickeada
                    int cellSize = calculateCellSize(simulation.getW());
                    int x = e.getX() / cellSize;
                    int y = e.getY() / cellSize;

                    // Buscamos si hay una partícula en esa posición
                    int[][] particles = simulation.particleInfo();
                    for (int i = 0; i < particles.length; i++) {
                        if (particles[i][0] == x && particles[i][1] == y) {
                            selectedParticle = i;  // Marcamos la partícula como seleccionada
                            colorDialogOpen = true; // Evitamos múltiples diálogos

                            // Mostramos el selector de color
                            Color newColor = JColorChooser.showDialog(
                                    DMaxwellGUI.this,
                                    "Seleccionar color para partícula",
                                    particleColors.getOrDefault(i,
                                            particles[i][2] == 0 ? new Color(255, 0, 0) :  // Rojo por defecto
                                                    new Color(0, 0, 255)));                // Azul por defecto

                            // Si se seleccionó un color, lo guardamos
                            if (newColor != null) {
                                particleColors.put(i, newColor);
                            }

                            colorDialogOpen = false;  // Permitimos nuevos diálogos
                            repaint();               // Redibujamos el tablero
                            requestFocusInWindow();  // Volvemos a tomar el foco
                        }
                    }
                }
            });
        }

        /**
         * Método para establecer la simulación actual
         * @param simulation Instancia de MaxwellD que representa el juego
         */
        public void setSimulation(MaxwellD simulation) {
            this.simulation = simulation;
        }

        /**
         * Método para dibujar el componente
         * @param g Objeto Graphics para dibujar
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (simulation == null) return;  // Si no hay simulación, no dibujamos nada

            int w = simulation.getW();  // Ancho del tablero
            int h = simulation.getH();  // Alto del tablero
            int cellSize = calculateCellSize(w);  // Tamaño dinámico de celdas

            // Dibujamos el fondo blanco
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, w * cellSize, h * cellSize);

            // Dibujamos las líneas de la cuadrícula
            g.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i <= w; i++) {
                g.drawLine(i * cellSize, 0, i * cellSize, h * cellSize);
            }
            for (int i = 0; i <= h; i++) {
                g.drawLine(0, i * cellSize, w * cellSize, i * cellSize);
            }

            // Dibujamos los demonios (cuadrados negros)
            g.setColor(Color.BLACK);
            int[][] demonPositions = simulation.posicionesD();
            for (int[] pos : demonPositions) {
                g.fillRect(pos[0] * cellSize, pos[1] * cellSize, cellSize, cellSize);
            }

            // Dibujamos los huecos (círculos grises)
            g.setColor(new Color(0, 14, 122));
            int[][] holePositions = simulation.posicionesH();
            for (int[] pos : holePositions) {
                g.fillOval(pos[0] * cellSize + 2, pos[1] * cellSize + 2, cellSize - 4, cellSize - 4);
            }

            // Dibujamos las partículas
            int[][] particlePositions = simulation.particleInfo();
            for (int i = 0; i < particlePositions.length; i++) {
                int[] pos = particlePositions[i];
                // Usamos color personalizado o el por defecto según el tipo
                Color particleColor = particleColors.getOrDefault(i,
                        pos[2] == 0 ? new Color(255, 100, 100) :  // Rojo por defecto
                                new Color(100, 100, 255));         // Azul por defecto

                // Si es la partícula seleccionada, dibujamos un borde morado
                if (i == selectedParticle) {
                    g.setColor(new Color(199, 0, 255));
                    g.fillOval(pos[0] * cellSize + 2, pos[1] * cellSize + 2, cellSize - 4, cellSize - 4);
                }

                // Dibujamos la partícula
                g.setColor(particleColor);
                g.fillOval(pos[0] * cellSize + 4, pos[1] * cellSize + 4, cellSize - 8, cellSize - 8);
            }

            // Dibujamos la línea divisoria central
            g.setColor(Color.BLACK);
            g.drawLine((w/2) * cellSize, 0, (w/2) * cellSize, h * cellSize);
        }

        /**
         * Método para obtener el tamaño preferido del panel
         * @return Dimension con el tamaño preferido basado en el tablero
         */
        @Override
        public Dimension getPreferredSize() {
            if (simulation == null) return new Dimension(0, 0);
            int cellSize = calculateCellSize(simulation.getW());
            return new Dimension(
                    simulation.getW() * cellSize,
                    simulation.getH() * cellSize
            );
        }
    }

    /**
     * Método principal para ejecutar la aplicación
     * @param args Argumentos de línea de comandos (no usados pero siempre se integran, no entendemos por qué)
     */
    public static void main(String[] args) {
        try {
            // Configuramos el look and feel del sistema para mejor integración (dice que es recomendado hacerlo)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ejecutamos la interfaz en el hilo de eventos de Swing (segun la teoria que averiguamos)
        SwingUtilities.invokeLater(() -> {
            DMaxwellGUI gui = new DMaxwellGUI();  // Creamos la interfaz
            gui.setVisible(true);                  // La hacemos visible
        });
    }
}
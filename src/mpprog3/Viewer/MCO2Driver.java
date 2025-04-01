package mpprog3.Viewer;

import javax.swing.*;


/**
 * The main driver class for the Jungle King game application.
 * <p>
 * This class serves as the entry point for the game and handles:
 * <ul>
 *   <li>Application startup and initialization</li>
 *   <li>Console management utilities</li>
 *   <li>Swing EDT (Event Dispatch Thread) setup</li>
 * </ul>
 * 
 * <p>The game follows a Model-View-Controller architecture with:
 * <ul>
 *   <li>{@code mpprog3.Model} - Game logic and state</li>
 *   <li>{@code mpprog3.Viewer} - GUI components (this package)</li>
 *   <li>{@code mpprog3.Controller} - Input handling and game flow</li>
 * </ul>
 * 
 * @see mpprog3.Viewer.AppFrame
 * @see javax.swing.SwingUtilities
 */
public class MCO2Driver {
    
    /**
     * The clearScreen function in Java clears the console screen by printing special escape sequences.
     */
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
  
    /**
     * The main entry point for the Jungle King game application.
     * <p>
     * Execution flow:
     * <ol>
     *   <li>Initializes Swing components on EDT</li>
     *   <li>Clears console and displays startup message</li>
     *   <li>Creates main application frame ({@link AppFrame})</li>
     *   <li>Starts event-driven game loop</li>
     * </ol>
     * 
     * @param args Command line arguments (unused in current implementation)
     * 
     * @throws IllegalStateException if Swing initialization fails
     * 
     * @see AppFrame
     * @see javax.swing.SwingUtilities#invokeLater(Runnable)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { // initialize the game on swing's event dispatch thread
            clearScreen();
            System.out.println("Starting Jungle King Game...");
            
            AppFrame appFrame = new AppFrame(); // create and show the main application window
            System.out.println("Main window initialized");
            
            // game automatically starts with menu screen
            // (actual game loop is event-driven through button clicks)
        });
    }
  }

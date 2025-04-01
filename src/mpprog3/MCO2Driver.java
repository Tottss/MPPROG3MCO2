package mpprog3;

import javax.swing.*;

public class MCO2Driver {
    /**
     * Clears the console screen using ANSI escape codes.
     * <p>
     * Works on most Unix terminals and Windows 10+ with ANSI support enabled.
     * </p>
     * 
     * @see <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">ANSI Escape Codes</a>
     */
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
  
    /**
     * The main entry point for the Jungle King game application.
     * <p>
     * Responsibilities include:
     * - Initializing the game on Swing's Event Dispatch Thread (EDT)
     * - Creating the main application frame
     * - Handling console output for debugging
     * </p>
     * 
     * @param args Command line arguments (not used in this application)
     * 
     * @see AppFrame
     * @see SwingUtilities#invokeLater(Runnable)
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

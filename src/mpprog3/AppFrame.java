package mpprog3;

import java.awt.*;
import javax.swing.*;
/**
 * The main application window for the Jungle King game.
 * <p>
 * Manages the application's views using a card layout to switch between:
 * <ul>
 *   <li>Main menu</li>
 *   <li>Animal selection screen</li>
 *   <li>Game board</li>
 * </ul>
 * 
 * <p>Responsible for:
 * <ul>
 *   <li>Initializing and displaying the application window</li>
 *   <li>Managing navigation between different views</li>
 *   <li>Maintaining game state (starting player)</li>
 *   <li>Handling game reset functionality</li>
 * </ul>
 * 
 * @see MenuView
 * @see AnimalSelectionGUI
 * @see JungleKingBoard
 */
public class AppFrame extends JFrame {
    /**
     * The fixed height of the application window in pixels.
     */
    static int FULL_HEIGHT = 600;
    
    /**
     * The fixed width of the application window in pixels.
     */
    static int FULL_WIDTH = 800;
    
    /**
     * The player number (1 or 2) who starts the game.
     */
    private int firstPlayer;
    
    /**
     * Card layout manager for view switching.
     */
    private CardLayout cardLayout;
    
    /**
     * Main container panel holding all views.
     */
    private JPanel mainPanel;
    
    /**
     * The main menu view component.
     */
    private MenuView menuView;
    
    /**
     * The animal selection view component.
     */
    private AnimalSelectionGUI animalSelectionView;
    
    /**
     * The game board view component (initialized when game starts).
     */
    private JungleKingBoard gameView;

    /**
     * Constructs the main application frame.
     * <p>
     * Initializes:
     * <ul>
     *   <li>Window properties (title, size, behavior)</li>
     *   <li>Card layout system</li>
     *   <li>Menu and animal selection views</li>
     * </ul>
     * The game view is initialized later when starting a game.
     */
    public AppFrame () {
        setTitle("Jungle King");
        setSize(1100, 735);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        menuView = new MenuView(this);
        animalSelectionView = new AnimalSelectionGUI(this);
        gameView = null; // will be initialized after selection
        
        mainPanel.add(menuView, "Menu");
        mainPanel.add(animalSelectionView, "AnimalSelection");
        
        add(mainPanel);
        setVisible(true);
    }
    /**
     * Returns to the main menu view.
     */
	public void returnToMenu () {
        cardLayout.show(mainPanel, "Menu");
    }
    /**
     * Switches to the animal selection view.
     */
    public void switchToAnimalSelection () {
        cardLayout.show(mainPanel, "AnimalSelection");
    }
    /**
     * Starts a new game with the specified starting player.
     * 
     * @param firstPlayer The player number (1 or 2) who will make the first move
     * @throws IllegalArgumentException if firstPlayer is not 1 or 2
     */
    public void startGameWithFirstPlayer (int firstPlayer) {
        if (gameView != null)
            mainPanel.remove(gameView);
        
		this.firstPlayer = firstPlayer;
        gameView = new JungleKingBoard(firstPlayer, this);
        mainPanel.add(gameView, "Game");
        cardLayout.show(mainPanel, "Game");
    }

    /**
     * Switches back to the main menu view.
     * Alias for {@link #returnToMenu()}.
     */
    public void switchToMenu () {
        cardLayout.show(mainPanel, "Menu");
    }

    /**
     * Resets the current game while maintaining the same starting player.
     * <p>
     * Creates a fresh game board instance while preserving:
     * <ul>
     *   <li>The original starting player</li>
     *   <li>All view configurations</li>
     * </ul>
     */
    public void resetGame () {
        if (gameView != null)
            mainPanel.remove(gameView);
        
        gameView = new JungleKingBoard(firstPlayer,this);
        mainPanel.add(gameView, "Game");
    }
}

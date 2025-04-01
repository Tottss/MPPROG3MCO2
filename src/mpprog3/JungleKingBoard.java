package mpprog3;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;


/**
 * The main game board for Jungle King, implementing the game's graphical interface and logic.
 * <p>
 * This class handles:
 * <ul>
 *   <li>Rendering the 7x9 game grid with all pieces and terrain</li>
 *   <li>Managing player turns and piece selection</li>
 *   <li>Handling mouse interactions for piece movement</li>
 *   <li>Enforcing game rules and win conditions</li>
 * </ul>
 * 
 * <p>The board layout consists of:
 * <ul>
 *   <li>Top panel: Player turn indicator</li>
 *   <li>Center: Game grid with alternating light/dark tiles</li>
 *   <li>Bottom panel: Control buttons (Main Menu, Exit)</li>
 * </ul>
 * 
 * @see Piece
 * @see AppFrame
 */
public class JungleKingBoard extends JPanel {
    
    /**
     * Number of rows in the game board.
     */
    public static final int ROWS = 7;
    
    /**
     * Number of columns in the game board.
     */
    public static final int COLS = 9;
    
    /**
     * Size of each tile in pixels.
     */
    public static final int TILE_SIZE = 100;
    
    /**
     * The game board model containing piece positions and terrain.
     */
    private board board;
    
    /**
     * List of all game pieces.
     */
    private ArrayList<Piece> pieces;
    
    /**
     * Currently selected piece, or null if no piece is selected.
     */
    public Piece selectedPiece = null;
    
    /**
     * Current player (1 or 2).
     */
    private int currentPlayer = -1;
    
    /**
     * Label displaying the current player's turn.
     */
    private JLabel turnLabel;
    
    /**
     * Images for special terrain tiles.
     */
    private Image lakeImage, trapImage, denBlueImage, denGreenImage;
    
    /**
     * Map of piece names to their corresponding images.
     */
    private java.util.Map<String, Image> pieceImages = new java.util.HashMap<>();
    
    /**
     * Reference to the main application frame for navigation.
     */
    private AppFrame appFrame;

    /**
     * Constructs a new game board for the specified starting player.
     * 
     * @param turn The starting player (1 or 2)
     * @param appFrame The main application frame for navigation
     * @throws IllegalArgumentException if turn is not 1 or 2
     */
    public JungleKingBoard (int turn, AppFrame appFrame) {
        this.appFrame = appFrame;
        setLayout(new BorderLayout());
        pieces = new ArrayList<>();
        board = new board();
        currentPlayer = turn;
        
        // create top panel with turn indicator on right
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false); // Transparent background
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        
        turnLabel = new JLabel("Player " + turn + "'s Turn", JLabel.RIGHT);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setForeground(turn == 1 ? Color.BLUE : Color.GREEN);
        topPanel.add(turnLabel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        
        loadImages();
        setPreferredSize(new Dimension(COLS * TILE_SIZE, ROWS * TILE_SIZE));
        
        // create bottom panel with buttons on right
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout()); // Use BorderLayout for main panel
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 20));

        // create a vertical box for the buttons
        Box buttonBox = Box.createVerticalBox();
        buttonBox.setOpaque(false);

        JButton menuButton = new JButton("Main Menu");
        styleButton(menuButton);
        menuButton.setAlignmentX(Component.RIGHT_ALIGNMENT); // right-align within box
        menuButton.addActionListener(e -> appFrame.returnToMenu());

        JButton exitButton = new JButton("Exit Game");
        styleButton(exitButton);
        exitButton.setAlignmentX(Component.RIGHT_ALIGNMENT); // right-align within box
        exitButton.addActionListener(e -> System.exit(0));

        // add buttons with spacing
        buttonBox.add(menuButton);
        buttonBox.add(Box.createRigidArea(new Dimension(0, 5))); // 5px vertical space
        buttonBox.add(exitButton);

        // add the button box to the EAST (right side) of the bottom panel
        bottomPanel.add(buttonBox, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
                
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = e.getX() / TILE_SIZE;
                int row = e.getY() / TILE_SIZE;
                handleTileClick(row, col);
            }
        });
    }

    /**
     * Styles a button with consistent appearance settings.
     * 
     * @param button The button to style
     */
    private void styleButton (JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(120, 30));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(new Color(220, 220, 220, 200)); // Semi-transparent
        button.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
    }

    /**
     * Loads all image resources required for the game.
     * <p>
     * Includes:
     * <ul>
     *   <li>Terrain images (lakes, traps, dens)</li>
     *   <li>Player-specific piece images</li>
     * </ul>
     */
    private void loadImages () {
        lakeImage = loadImage("lake");
        trapImage = loadImage("trap");
        denBlueImage = loadImage("den-blue");
        denGreenImage = loadImage("den-green");
        String key = null;
        
        String[] pieceTypes = {"rat", "cat", "dog", "wolf", "leopard", "tiger", "lion", "elephant"};
        for (String type : pieceTypes) {
            for (int player = 1; player <= 2; player++) {
                if (player == 1)
					key = type + "-blue";
				if (player == 2)
					key = type + "-green";
                pieceImages.put(key, loadImage(key));
            }
        }
    }
    
    /**
     * Loads and scales an individual image resource.
     * 
     * @param fileName The image filename (without path or extension)
     * @return The scaled Image object
     * @throws RuntimeException if image fails to load
     */
    private Image loadImage (String fileName) {
        String path = "img/" + fileName + ".png";
        ImageIcon icon = new ImageIcon(path);
        return icon.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH);
    }

    /**
     * Paints the game board and all components.
     * 
     * @param g The Graphics context for painting
     */
    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        
        drawBoardBackground(g); // background
        
        drawSpecialTiles(g); // lake, traps, dens
        
        drawPieces(g); // pieces
        
        if (selectedPiece != null) { 
            drawSelection(g, selectedPiece);
        }
    }
    
    /**
     * Draws the checkerboard background pattern.
     * 
     * @param g The Graphics context for painting
     */
    private void drawBoardBackground (Graphics g) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                
                if ((row + col) % 2 == 0) // alternate colors
                    g.setColor(Color.LIGHT_GRAY);
                
				else
                    g.setColor(Color.DARK_GRAY);
				
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }
    
    /**
     * Draws special terrain tiles (lakes, traps, dens).
     * 
     * @param g The Graphics context for painting
     */
    private void drawSpecialTiles (Graphics g) {
		int row, col;
		Object cell;
		
        for (row = 0; row < ROWS; row++) {
            for (col = 0; col < COLS; col++) {
                cell = board.getGrid(row, col);
                
                if (cell instanceof Character) {
                    char terrain = (Character) cell;
                    if (terrain == '~') // lake
                        g.drawImage(lakeImage, col * TILE_SIZE, row * TILE_SIZE, this);
                    
					else if (terrain == '#') // traps
                        g.drawImage(trapImage, col * TILE_SIZE, row * TILE_SIZE, this);
                    
                }
            }
        }
        // dens
        g.drawImage(denBlueImage, 0 * TILE_SIZE, 3 * TILE_SIZE, this);
        g.drawImage(denGreenImage, 8 * TILE_SIZE, 3 * TILE_SIZE, this);
    }
    
    /**
     * Draws all game pieces at their current positions.
     * 
     * @param g The Graphics context for painting
     */
    private void drawPieces (Graphics g) {
		int row, col;
		Object cell;
		Piece piece;
		Image pieceImage;
		
        for (row = 0; row < ROWS; row++) {
            for (col = 0; col < COLS; col++) {
                cell = board.getGrid(row, col);
                if (cell instanceof Piece) {
                    piece = (Piece) cell;
                    pieceImage = pieceImages.get(piece.getPieceName().toLowerCase());
					
                    if (pieceImage != null)
                        g.drawImage(pieceImage, col * TILE_SIZE, row * TILE_SIZE, this);
                }
            }
        }
    }
    
    /**
     * Draws selection highlight around the currently selected piece.
     * 
     * @param g The Graphics context for painting
     * @param piece The selected piece to highlight
     */
    private void drawSelection (Graphics g, Piece piece) {
        g.setColor(Color.YELLOW);
        g.drawRect(piece.getColumn() * TILE_SIZE, piece.getRow() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        g.drawRect(piece.getColumn() * TILE_SIZE + 1, piece.getRow() * TILE_SIZE + 1, TILE_SIZE - 2, TILE_SIZE - 2);
    }
    
    /**
     * Handles tile click events for piece selection and movement.
     * 
     * @param row The grid row clicked (0-6)
     * @param col The grid column clicked (0-8)
     */
    private void handleTileClick (int row, int col) {
		Object cell = board.getGrid(row, col);
		Piece clickedPiece;
		
		if (cell instanceof Piece) { // if clicking on a piece
			clickedPiece = (Piece) cell;
			
			if (selectedPiece == null) { // only allow selecting own pieces
				if (clickedPiece.getPlayerNumber() == currentPlayer)
					selectedPiece = clickedPiece;
			} 
			else {
				
				if (clickedPiece == selectedPiece) // deselect by clicking on the same piece
					selectedPiece = null;
				
				else if (isAdjacent(selectedPiece.getRow(), selectedPiece.getColumn(), row, col)) { // check if adjacent
					
					if (selectedPiece.getPlayerNumber() == clickedPiece.getPlayerNumber()) // attempt capture/move
						selectedPiece = clickedPiece; // clicked on own piece; change selection
					
					else { // attempt capture
						if (selectedPiece.capture(clickedPiece) && board.isValidMove(selectedPiece, row, col)) {
							board.movePiece(selectedPiece, row, col);
							board.trapped(selectedPiece);
                            board.unkillableRat(selectedPiece);
							endTurn();
						}
					}
				}
			}
		} 
		else { // clicking on empty space
			if (selectedPiece != null && isAdjacent(selectedPiece.getRow(), selectedPiece.getColumn(), row, col)) {
				if (board.isValidMove(selectedPiece, row, col)) {
					board.movePiece(selectedPiece, row, col);
					board.trapped(selectedPiece);
                    board.unkillableRat(selectedPiece);
					endTurn();
				}
			}
		}
		
		repaint();
	}
	
    /**
     * Checks if two grid positions are orthogonally adjacent.
     * 
     * @param row1 First position row
     * @param col1 First position column
     * @param row2 Second position row
     * @param col2 Second position column
     * @return true if positions are adjacent, false otherwise
     */
	private boolean isAdjacent(int row1, int col1, int row2, int col2) { // check if two positions are adjacent
		int rowDiff = Math.abs(row1 - row2);
		int colDiff = Math.abs(col1 - col2);
		
		// either same row, adjacent column, or same column, adjacent row
		return (rowDiff == 0 && colDiff == 1) || (rowDiff == 1 && colDiff == 0);
	}
    
    /**
     * Ends the current player's turn and switches to the next player.
     * Also checks for win conditions.
     */
    private void endTurn() {
        selectedPiece = null;
		if (checkWinCondition()) {
            JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
            resetGame();
        }
        currentPlayer = (currentPlayer == 1) ? 2 : 1; // switch player
        updateTurnLabel();
        
        // check for win condition
        
    }

    /**
     * Updates the turn indicator label with current player information.
     */
    private void updateTurnLabel() {
        turnLabel.setText("Player " + currentPlayer + "'s Turn");
        turnLabel.setForeground(currentPlayer == 1 ? Color.BLUE : Color.GREEN);
    }

    /**
     * Checks if either player has won by reaching the opponent's den.
     * 
     * @return true if a player has won, false otherwise
     */
    private boolean checkWinCondition() {
        // check if any player reached the opponent's den
        Piece blueDenPiece = getPiece(3, 0);
        Piece greenDenPiece = getPiece(3, 8);
        
        if (blueDenPiece instanceof Piece && blueDenPiece.getPlayerNumber() == 2) {
            return true; // player 2 reached blue den
        }
        if (greenDenPiece instanceof Piece && greenDenPiece.getPlayerNumber() == 1) {
            return true; // player 1 reached green den
        }
        return false;
    }
    
    /**
     * Resets the game to its initial state.
     */
    private void resetGame() { // reset game state
        board = new board();
        currentPlayer = 1;
        selectedPiece = null;
        updateTurnLabel();
        repaint();
    }
    /**
     * Gets the piece at a specific grid position.
     * 
     * @param row The grid row (0-6)
     * @param col The grid column (0-8)
     * @return The Piece at the position, or null if empty
     */
    public Piece getPiece(int row, int col) {
        Object cell = board.getGrid(row, col);
        return (cell instanceof Piece) ? (Piece) cell : null;
    }
}

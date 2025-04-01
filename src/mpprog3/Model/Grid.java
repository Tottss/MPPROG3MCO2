package mpprog3.Model;
/**
 * Represents a single cell in the game board grid for Jungle King.
 * <p>
 * Each grid cell can contain either:
 * <ul>
 *   <li>A game piece ({@link Piece})</li>
 *   <li>A terrain feature (lake '~', trap '#', den '@', or normal '.')</li>
 * </ul>
 * 
 * <p>The class provides methods to:
 * <ul>
 *   <li>Check cell occupancy</li>
 *   <li>Get/set the contained piece</li>
 *   <li>Get/set the terrain type</li>
 * </ul>
 */
public class Grid {
    /**
     * The game piece occupying this grid cell, or null if empty.
     */
    private Piece piece;
    
    /**
     * The terrain type of this grid cell:
     * <ul>
     *   <li>'~' - Lake</li>
     *   <li>'#' - Trap</li>
     *   <li>'@' - Den</li>
     *   <li>'.' - Normal terrain</li>
     * </ul>
     */
    private char terrain;
    
    /**
     * Constructs a new grid cell with the specified terrain type.
     * The cell is initially empty (contains no piece).
     *
     * @param terrain The terrain character representing the type of the tile
     * @throws IllegalArgumentException if terrain is not one of the valid types
     */
	public Grid (char terrain) {
		this.terrain = terrain;
	}
	
	/**
	 * Checks if the grid cell is empty (i.e., does not contain a piece).
	 *
	 * @return true if the grid cell has no piece, false otherwise.
	 */
	public boolean isEmpty () {
		if (piece == null)
			return true;
		return false;
	}
	
	/**
	 * Retrieves the object occupying the grid cell.
	 * If a piece is present, it returns the piece; otherwise, it returns the terrain type.
	 *
	 * @return The Piece object if present, otherwise the terrain character.
	 */
	public Object getObject () {
		if (piece != null)
			return piece;
		return terrain;
	}
	
	/**
	 * Retrieves the piece currently occupying the grid cell.
	 *
	 * @return The Piece object if present, otherwise null.
	 */
	public Piece getPiece () {
		return piece;
	}

	/**
	 * Removes the piece from the grid cell by setting it to null.
	 */
	public void setNull(){
		this.piece = null;
	}
	
	/**
	 * Retrieves the terrain type of the grid cell.
	 *
	 * @return A character representing the terrain type.
	 */
	public char getTerrain () {
		return terrain;
	}
	
	/**
	 * Sets a piece onto the grid cell and updates the piece's position.
	 *
	 * @param piece The Piece object to place on the grid.
	 * @param r The row position of the piece.
	 * @param c The column position of the piece.
	 */
	public void setPiece (Piece piece, int r, int c) { // sets a piece onto a specific grid in board
		this.piece = piece;
		this.piece.setPosition(r, c); // updates r and c of the piece
	}
	
	/**
	 * Updates the terrain type of the grid cell.
	 *
	 * @param terrain The new terrain character to set.
	 */
	public void setTerrain (char terrain) {
		this.terrain = terrain;
	}
}

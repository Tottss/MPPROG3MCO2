package mpprog3;


/**
 * The base class representing a game piece in the Jungle board game.
 * 
 * <p>This abstract class defines common properties and behaviors for all pieces:
 * <ul>
 *   <li>Basic movement and capture mechanics</li>
 *   <li>Strength comparisons</li>
 *   <li>Position tracking</li>
 *   <li>Special terrain interactions (lakes and traps)</li>
 *   <li>Win condition checking</li>
 * </ul>
 * 
 * <p>Subclasses implement specific piece behaviors through method overrides.
 * 
 * @version 1.0
 * @see rat
 * @see elephant
 * @see lion
 * @see tiger
 */
public class Piece {
    /** The name identifier of the piece (e.g., "rat-blue") */
    protected String pieceName;
    /** The strength value (1-8) determining combat outcomes */
    protected int strength;
    /** The player number (1 or 2) owning this piece */
    protected int playerNo;
    /** Tracks whether the piece is still in play */
    protected boolean alive;
    /** Indicates if the piece is weakened by a trap */
    protected boolean weak;
    /** Current row position on the board (0-6) */
    protected int r;
    /** Current column position on the board (0-8) */
    protected int c;

    /**
     * Constructs a new game piece with specified attributes.
     * 
     * @param pieceName the unique identifier for this piece type
     * @param strength the combat strength value (1-8)
     * @param playerNo the owning player number (1 or 2)
     * @throws IllegalArgumentException if strength is outside 1-8 range
     *                                  or playerNo is not 1 or 2
     */
	public Piece (String pieceName, int strength, int playerNo) {
		this.pieceName = pieceName;
		this.strength = strength;
		this.playerNo = playerNo;
		alive = true;
		weak = false;
	}

	/**
	 * Retrieves the name of the piece.
	 *
	 * @return The piece name as a string.
	 */
	public String getPieceName(){
		return pieceName;
	}
	
	/**
	 * Retrieves the strength of the piece.
	 *
	 * @return The strength value of the piece.
	 */
	public int getStrength(){
		return strength;
	}

	/**
	 * Retrieves the player number associated with this piece.
	 *
	 * @return The player number.
	 */
	public int getNumber(){
		return playerNo;
	}

	/**
	 * Retrieves the row position of the piece.
	 *
	 * @return The row index.
	 */
	public int getRow () {
		return r;
	}
	
	/**
	 * Retrieves the column position of the piece.
	 *
	 * @return The column index.
	 */
	public int getColumn () {
		return c;
	}
	 
	/**
	 * Checks if the piece is currently in a weakened state.
	 *
	 * @return true if the piece is weak, false otherwise.
	 */
	public boolean getWeak () {
		return weak;
	}
	
	/**
	 * Checks if the piece is still alive.
	 *
	 * @return true if the piece is alive, false otherwise.
	 */
	public boolean getAlive () {
		return alive;
	}
	
	/**
	 * Sets the position of the piece on the board.
	 *
	 * @param r The new row position.
	 * @param c The new column position.
	 */
	public void setPosition (int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	public void setStrength(int strength){
		this.strength = strength;
	}
	/**
	 * Determines if the current piece can capture another piece.
	 * 
	 * @param piece The target piece to be captured.
	 * @return true if the piece is captured, false otherwise.
	 */
	public boolean capture (Piece piece) { // only captures, doesn't update position
		// return true if piece gets captured, false if piece doesn't get captured
		
		if (piece.getWeak()) {
			piece.setDead();
			return true;
		}
		
		else
			if (this.isStronger(piece)) {
				piece.setDead();
				return true;
			}
			else
				return false;
	}
	
	/**
	 * Determines if this piece is stronger than the given piece.
	 *
	 * @param piece The piece to compare strength against.
	 * @return true if this piece is stronger or the other piece is weak, false otherwise.
	 */
	public boolean isStronger (Piece piece) {
		if (piece.getWeak())
			return true;
		else return strength >= piece.getStrength();
	}

	/**
	 * Sets the player number of this piece.
	 *
	 * @param playerNo The new player number.
	 */
	public void setPlayerNumber(int playerNo){
		this.playerNo = playerNo;
	}
	
	/**
	 * Marks the piece as dead.
	 */
	public void setDead () {
		alive = false;
	}
	
	/**
	 * Marks the piece as weak.
	 */
	public void setWeak () {
		weak = true;
	}
	

	/**
	 * Removes the weakened status of the piece.
	 */
	public void setNotWeak () {
		weak = false;
	}

	/**
	 * Determines if the piece can stay on a lake tile.
	 * 
	 * @return true if the piece can stay on a lake, false otherwise.
	 */
	public boolean canSwim(){
            // checking if animal can stay on lake tile :default false
            //return this.pieceName.equals("R1") || this.pieceName.equals("R2");
			return false;
	}
	
	/**
	 * Determines if the piece can cross a lake.
	 * 
	 * @return true if the piece can cross a lake, false otherwise.
	 */
	public boolean canCross() {
        
		//return this.pieceName.equals("LN1") || this.pieceName.equals("T1") || this.pieceName.equals("LN2") || this.pieceName.equals("T2"); // checking if animal can cross lake tile : default false
		return false;
    }
	
	/**
	 * Retrieves the player number associated with the piece.
	 *
	 * @return The player number.
	 */
	public int getPlayerNumber(){
		return playerNo;
	}

	/**
	 * Determines if the piece has reached the opponent's base and won the game.
	 *
	 * @return true if the piece has reached the opponent's base, false otherwise.
	 */
	public boolean didWin(){
		if (this.r == 3 && this.c == 0 && playerNo == 2){
			return true;
		}
		if (this.r == 3 && this.c == 8 && playerNo == 1){
			return true;
		}
		return false;
	}
	
	@Override
	public String toString () {
		return pieceName;
	}
}

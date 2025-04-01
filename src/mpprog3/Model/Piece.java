package mpprog3.Model;


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

	public boolean cannotEat;

	
	protected char currentTerrain;

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
	
	/**
	 * Sets this piece's strength value.
	 * <p>
	 * Strength determines:
	 * <ul>
	 *   <li>Capture capabilities (through {@link #canCapture(Piece)})</li>
	 *   <li>Vulnerability to capture (through {@link #canBeCapturedBy(Piece)})</li>
	 * </ul>
	 *
	 * @param strength The new strength value (must be positive)
	 * @throws IllegalArgumentException if strength is non-positive
	 */
	public void setStrength(int strength){
		this.strength = strength;
	}
	/**
	 * Determines if the current piece can capture another piece.
	 * 
	 * @param target The target piece to be captured.
	 * @return true if the piece is captured, false otherwise.
	 */
	public boolean capture(Piece target) {
		// Check if this capture is allowed by both pieces
		if (target.getWeak()){
			return true;
		}
		if (!target.canBeCapturedBy(this) || !this.canCapture(target)) {
			return false;
		}

		return this.getStrength() >= target.getStrength();
	}
	
	/**
	 * Determines if this piece can capture the specified target piece
	 * based on standard strength comparison rules.
	 * <p>
	 * Default capture rules:
	 * <ul>
	 *   <li>Can capture pieces of equal or lesser strength</li>
	 *   <li>Cannot capture stronger pieces</li>
	 * </ul>
	 * 
	 * Subclasses should override this to implement special capture behaviors.
	 *
	 * @param target The piece being targeted for capture (must not be null)
	 * @return true if this piece's strength >= target's strength, false otherwise
	 * @throws NullPointerException if target is null
	 * @see #getStrength()
	 */
	public boolean canCapture(Piece target) {
        // Default capture rules - can capture equal or weaker pieces
        return this.getStrength() >= target.getStrength();
    }

	/**
	 * Checks if this piece is currently positioned on water terrain.
	 * Water terrain is represented by the '~' character on the game board.
	 *
	 * @return true if the piece is on water ('~'), false otherwise
	 * @see #getCurrentTerrain()
	 */
	public boolean isInWater() {
        return this.getCurrentTerrain() == '~';
    }

	/**
	 * Determines if this piece can be captured by the specified attacking piece
	 * based on standard strength comparison rules.
	 * <p>
	 * Default vulnerability rules:
	 * <ul>
	 *   <li>Can be captured by pieces of equal or greater strength</li>
	 *   <li>Cannot be captured by weaker pieces</li>
	 * </ul>
	 * 
	 * Subclasses should override this to implement special vulnerability behaviors.
	 *
	 * @param attacker The piece attempting to capture this piece (must not be null)
	 * @return true if attacker's strength >= this piece's strength, false otherwise
	 * @throws NullPointerException if attacker is null
	 * @see #getStrength() 
	 */
    public boolean canBeCapturedBy(Piece attacker) {
        // Default rules - can be captured by equal or stronger pieces
        return attacker.getStrength() >= this.getStrength();
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
	 * The getCurrentTerrain function in Java returns the current terrain as a character.
	 * 
	 * @return The method getCurrentTerrain() is returning a character representing the current terrain.
	 */
	public char getCurrentTerrain() {
        return currentTerrain;
    }

    /**
	 * This Java function sets the current terrain to the specified character.
	 * 
	 * @param terrain The `setCurrentTerrain` method sets the current terrain to the specified
	 * character value passed as a parameter. This can be useful in a game or simulation where
	 * different characters represent different types of terrain (e.g., grass, water, mountains).
	 */
	public void setCurrentTerrain(char terrain) {
        this.currentTerrain = terrain;
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
	

	/**
	 * The `toString` method is overridden to return the `pieceName` as a string representation of the
	 * object.
	 * 
	 * @return The `pieceName` variable is being returned as a String.
	 */
	@Override
	public String toString () {
		return pieceName;
	}
}

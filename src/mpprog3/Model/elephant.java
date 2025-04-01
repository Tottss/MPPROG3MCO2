package mpprog3.Model;


/**
 * Represents the Elephant piece in the Jungle King game.
 * 
 * <p>The Elephant is the strongest regular piece (strength 8) with special combat rules:
 * <ul>
 *   <li>Cannot defeat Rats despite higher strength (Rock-Paper-Scissors dynamic)</li>
 *   <li>Defeats all other pieces except Rats when at full strength</li>
 *   <li>Can be defeated by any piece when weakened by traps</li>
 *   <li>Cannot swim or jump over rivers</li>
 * </ul>
 * 
 * 
 * 
 * 
 */
public class elephant extends Piece {

    /**
     * Constructs an Elephant piece for the specified player.
     * 
     * @param playerNo The player number (1 or 2) that owns this piece.
     *        Player 1 pieces are blue, Player 2 pieces are green.
     * @throws IllegalArgumentException if playerNo is not 1 or 2
     */
    public elephant(int playerNo) {
        super("elephant-" + (playerNo == 1 ? "blue" : "green"), 8,playerNo);
    }

    /**
     * Determines if this Elephant is stronger than another piece,
     * following special Jungle game rules for Elephants.
     * 
     * <p>Special case: Elephants cannot defeat Rats (returns false)
     * 
     * @param piece The opposing piece to compare against
     * @return true if this Elephant can defeat the other piece normally,
     *         false if opposing piece is a Rat or this Elephant is weaker
     */
    @Override
    public boolean isStronger (Piece piece) {
        if (piece instanceof rat){
            return false;
        }
        return strength >= piece.getStrength();
    }


    /**
     * Attempts to capture another piece following Elephant-specific rules.
     * 
     * <p>Capture succeeds when:
     * <ul>
     *   <li>The opposing piece is weakened by a trap, OR</li>
     *   <li>This Elephant is stronger (and target isn't a Rat)</li>
     * </ul>
     * 
     * @param piece The target piece to capture
     * @return true if capture succeeds, false otherwise
     * @see #isStronger(Piece)
     */
    @Override
    public boolean capture (Piece piece) { // only captures, doesn't update position
		// return true if piece gets captured, false if piece doesn't get captured
		
		if (piece.getWeak()) {
            System.err.println("weakrat");
			return true;
		}
		
		else
			if (strength >= piece.getStrength() && !(piece instanceof rat)) {
				piece.setDead();
				return true;
			}
			else
				return false;
	}
}

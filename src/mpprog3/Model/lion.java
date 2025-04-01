package mpprog3.Model;
/**
 * Represents the Lion piece in the Jungle King game.
 * 
 * <p>The Lion is a powerful piece (strength 7) with special movement capabilities:
 * <ul>
 *   <li>Can jump horizontally or vertically across rivers (lakes)</li>
 *   <li>Cannot jump over other pieces when crossing rivers</li>
 *   <li>Follows standard combat rules against other pieces</li>
 *   <li>Second strongest piece in the game after the Elephant</li>
 * </ul>
 * 
 * @see Piece
 * @see tiger
 * @see elephant
 */
public class lion extends Piece {
    /**
     * Constructs a Lion piece for the specified player.
     * 
     * @param playerNo the player number (1 or 2) that owns this piece.
     *        Player 1 pieces are blue, Player 2 pieces are green.
     * @throws IllegalArgumentException if playerNo is neither 1 nor 2
     */
    public lion(int playerNo){
        super("lion-" + (playerNo == 1 ? "blue" : "green"), 7, playerNo);
    }

	
     /**
     * Determines if this Lion can cross rivers (lake spaces).
     * 
     * <p>Lions have the special ability to jump over rivers in a straight line,
     * similar to Tigers. The jump must be either horizontal or vertical,
     * and the path must be clear of other pieces.
     * 
     * @return {@code true} always, as Lions can cross rivers
     * @see Piece#canCross()
     */
    @Override
    public boolean canCross() {
        return true; // Rat can cross if the space is a lake
    }

	
}

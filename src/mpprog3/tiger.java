package mpprog3;
/**
 * Represents the Tiger piece in the Jungle game.
 * The Tiger is a strong piece (strength 6) with special movement capabilities:
 * - Can jump horizontally or vertically across lakes
 * - Cannot jump over other pieces when crossing lakes
 * - Follows standard combat rules against other pieces
 */
public class tiger extends Piece {
    /**
     * Constructs a Tiger piece for the specified player.
     * 
     * @param playerNo The player number (1 or 2) that owns this piece.
     *                Player 1 pieces are blue, Player 2 pieces are green.
     * @throws IllegalArgumentException if playerNo is not 1 or 2
     */
    public tiger(int playerNo){
        super("tiger-" + (playerNo == 1 ? "blue" : "green"), 6, playerNo);
    }

	
    /**
     * Determines if this Tiger can cross lakes (river spaces).
     * Tigers have the special ability to jump over lakes in a straight line,
     * either horizontally or vertically, as long as the path is clear.
     * 
     * @return true always, as Tigers can cross lakes
     * @see Piece#canCross()
     */
    @Override
    public boolean canCross() {
        return true; // Rat can cross if the space is a lake
    }

	
}

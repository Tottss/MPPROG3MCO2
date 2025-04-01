package mpprog3.Model;
/**
 * Represents the Rat piece in the Jungle game.
 * The Rat is the weakest piece (strength 1) but has special abilities:
 * - Can defeat the Elephant (strength 8) despite lower strength
 * - Can swim in water tiles
 * - Is immune to traps when weakened
 */
public class rat extends Piece {
    // constructor
    

	/**
     * Constructs a Rat piece for the specified player.
     * 
     * @param playerNo The player number (1 or 2) that owns this piece.
     *                Player 1 pieces are blue, Player 2 pieces are green.
     */
    public rat(int playerNo){
        super("rat-" + (playerNo == 1 ? "blue" : "green"), 1, playerNo);
    }

    // checks if next grid is crossable


	/**
     * Determines if this Rat can defeat another piece based on special rules:
     * - Always defeats weakened pieces
     * - Can defeat Elephants (strength 8) despite lower strength
     * 
     * @param piece The opposing piece to compare against
     * @return true if this Rat can defeat the other piece, false otherwise
     */
    @Override
    public boolean isStronger (Piece piece) {
		if (piece.getWeak())
			return true;
    
		else return (piece.getStrength() == 8 || piece instanceof rat) ;
	}

    @Override
    public boolean canCapture(Piece target) {
        // Rats in water can't capture elephants on land
        if (this.isInWater() && target instanceof elephant && !target.isInWater()) {
            return false;
        }
        return super.canCapture(target);
    }

    @Override
    public boolean canBeCapturedBy(Piece attacker) {
        // Rats in water can't be captured by land animals
        if(this.getWeak())
            return true;
        if (this.isInWater() && !attacker.canSwim()) {
            return false;
        }
        return super.canBeCapturedBy(attacker);
    }

    @Override
    public boolean isInWater() {
        return this.getCurrentTerrain() == '~';
    }

    

	/**
     * Indicates whether this Rat can swim in water tiles.
     * Rats are the only pieces that can occupy water tiles.
     * 
     * @return true always, as Rats can swim
     */
	@Override
	public boolean canSwim(){
		return true;
	}

    
}

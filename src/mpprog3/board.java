package mpprog3;

import java.util.*;
/**
 * Represents the game board for Jungle King, managing the 7x9 grid and game state.
 * <p>
 * Responsibilities include:
 * <ul>
 *   <li>Initializing and maintaining the game grid with pieces and terrain</li>
 *   <li>Validating and executing piece movements</li>
 *   <li>Handling special terrain effects (lakes, traps, dens)</li>
 *   <li>Managing piece captures and win conditions</li>
 *   <li>Maintaining game state information</li>
 * </ul>
 * 
 * <p>The board layout features:
 * <ul>
 *   <li>7 rows × 9 columns grid</li>
 *   <li>Two 2×3 lake areas in the center</li>
 *   <li>Player bases at opposite ends (columns 0 and 8)</li>
 *   <li>Traps surrounding each base</li>
 * </ul>
 * 
 * @see Grid
 * @see Piece
 */
public class board {
    /**
     * The 7x9 game grid containing all cells and their contents.
     */
    private Grid[][] board;
    
    /**
     * List of all game pieces currently in play.
     */
    private ArrayList<Piece> pieces;

    /**
     * Constructs a new game board with initial setup.
     * <p>
     * Initialization sequence:
     * <ol>
     *   <li>Creates empty 7×9 grid</li>
     *   <li>Places lake terrain</li>
     *   <li>Sets up bases and traps</li>
     *   <li>Creates all game pieces</li>
     *   <li>Places pieces in starting positions</li>
     * </ol>
     */
	public board () {
		board = new Grid[7][9]; // initializes the 7x9 grid
		pieces = new ArrayList<>();
		
		setOnlyBoard();
		setLake();
		setBases();
		instantiatePieces();
		setPieces();
	}
	

	/**
	 * Initializes the game board with empty tiles.
	 * Each tile is set to '.' to indicate an open space.
	 */
	public void setOnlyBoard () { // initializes board to be '.' only
		int i, j;
		
		for (i = 0; i < board.length; i++) {
			for (j = 0; j < board[i].length; j++) {
				board[i][j] = new Grid ('.');
			}
		}
	}
	

	/**
	 * Sets the lake tiles on the game board.
	 * The lake is represented by the '~' symbol.
	 * The lake occupies two 2x3 regions in the middle of the board.
	 */
	public void setLake () { // sets the lake to be '~'
		int i, j;
		
		for (i = 1; i < 3; i++) {
			for (j = 3; j < 6; j++) {
				board[i][j].setTerrain('~');
			}
		}
		
		for (i = 4; i < 6; i++) {
			for (j = 3; j < 6; j++) {
				board[i][j].setTerrain('~');
			}
		}
	}
	
	/**
	 * Sets up the bases on the game board.
	 * The bases are represented by the '@' symbol.
	 * Commented-out sections indicate trap locations.
	 */
	public void setBases () {
		// left base
		board[2][0].setTerrain('#');
		board[3][0].setTerrain('@');
		board[3][1].setTerrain('#');
		board[4][0].setTerrain('#');
		
		// right base;
		board[2][8].setTerrain('#');
		board[3][8].setTerrain('@');
		board[3][7].setTerrain('#');
		board[4][8].setTerrain('#');
	}
	

	/**
	 * Instantiates the game pieces for both players.
	 * Each piece is assigned a name, strength, and player number.
	 * The pieces are stored in a list for easy access.
	 */
	public void instantiatePieces () { // create pieces for player 1 and 2
		int i, j;
		
		String[] pieceNames = {"cat-", "dog-", "wolf-", "leopard-"};
		int[] strengths = {2, 3, 4, 5};
		
		for (i = 1; i <= 2; i++) { // twice for both players
			pieces.add(new rat(i));
			pieces.add(new tiger(i));
			pieces.add(new lion(i));
			pieces.add(new elephant(i));
			
			for (j = 0; j < strengths.length; j++) {
				if (i == 1)
					pieces.add(new Piece(pieceNames[j] + "blue", strengths[j], i));
				else
					pieces.add(new Piece(pieceNames[j] + "green", strengths[j], i));
			}
		}
	}
	
	/**
	 * Initializes the board by placing pieces in their designated starting positions.
	 * The pieces are assigned based on their names retrieved from the piece list.
	 */
	public void setPieces () {
		int i;
		
		String[] pieceNames = {"rat", "cat", "dog", "wolf", "leopard", "tiger", "lion", "elephant"};
		
		int[][] positionsP1 = {{6, 2}, {1, 1}, {5, 1}, {2, 2}, {4, 2}, {0, 0}, {6, 0}, {0, 2}};
		int[][] positionsP2 = {{0, 6}, {5, 7}, {1, 7}, {4, 6}, {2, 6}, {6, 8}, {0, 8}, {6, 6}};
		
		for (i = 0; i < positionsP1.length; i++) {
			board[positionsP1[i][0]][positionsP1[i][1]].setPiece(findPiece(pieceNames[i] + "-blue"), positionsP1[i][0], positionsP1[i][1]);
			board[positionsP2[i][0]][positionsP2[i][1]].setPiece(findPiece(pieceNames[i] + "-green"), positionsP2[i][0], positionsP2[i][1]);
		}
	}
	

	/**
	 * Finds a piece by its name from the list of pieces.
	 *
	 * @param name The name of the piece to find.
	 * @return The Piece object if found, otherwise null.
	 */
	public Piece findPiece (String name) { // finds piece by its String name in array list
		for (Piece p: pieces) {
			if (p.getPieceName().equals(name))
				return p;
		}
		return null;
	}
	

	/**
	 * Displays the current state of the board by printing each tile's object.
	 * Each tile is separated by a tab space for better readability.
	 */
	public void displayBoard () {
		int i, j;
		
		for (i = 0; i < board.length; i++) {
			for (j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j].getObject()+"\t");
			}
			System.out.println();
			System.out.println();
		}
	}
	/**
	 * Moves a given piece in the specified direction if the move is valid.
	 * <p>
	 * The method ensures the following:
	 * <ul>
	 *     <li>The piece is alive before moving.</li>
	 *     <li>The move is validated using {@code isValidMove}.</li>
	 *     <li>The piece's position is updated upon a successful move.</li>
	 *     <li>If the piece enters an opposing trap, it becomes weakened.</li>
	 *     <li>If the piece moves into an opposing home base, the game ends.</li>
	 *     <li>If the piece captures an opponent's piece, the opponent's piece is removed.</li>
	 *     <li>For special movements (e.g., lions/tigers crossing lakes), the logic ensures they follow game rules.</li>
	 * </ul>
	 *
	 * @param piece The piece that is attempting to move.
	 * @param m The direction of the move ('W', 'S', 'A', 'D' for up, down, left, right).
	 * @return true if the move is successfully executed, false otherwise.
	 */	
	public boolean movePiece (Piece piece, int newR, int newC) { // updates position of piece; returns value of isValidMove()
		int oldR = piece.getRow(), oldC = piece.getColumn();
		boolean valid = false;
		int crossR = -1,CrossC = -1 ;
		
		if (!piece.getAlive()) 
			return false;
			
		if (!isValidMove(piece, newR, newC)){

			return false;
		}
		
		valid = true;
		
		Grid targetTile = board[newR][newC];
		String m = determineMove(oldR, oldC, newR, newC);
		
		if (targetTile.getObject().equals('~') && piece.canCross()) { // for lions and tigers only
			System.out.println("piece crosses lake");
			if ("W".equals(m)){
				targetTile = board[newR - 2][newC];
				crossR = newR - 2;
				CrossC = newC;
				System.out.println("go up");


			}
			else if ("S".equals(m)){
				targetTile = board[newR + 2][newC];
				crossR = newR + 2;
				CrossC = newC;
				System.out.println("go down");
			}
			else if ("A".equals(m)){
				targetTile = board[newR][newC - 3];
				crossR = newR;
				CrossC = newC - 3;
				System.out.println("go left");
			}
			else if ("D".equals(m)){
				targetTile = board[newR][newC + 3];
				crossR = newR;
				CrossC = newC + 3;
				System.out.println("go right");
			}
			if (targetTile.getObject() instanceof Piece) { // if resulting tile contains an opposing piece
				Piece targetPiece = targetTile.getPiece();
				
				if (piece.capture(targetPiece)) { // piece is stronger/as strong as
					piece.setPosition(crossR,CrossC);
					targetTile.setPiece(piece, crossR,CrossC);
					board[oldR][oldC].setNull();
					return true;
				}
				else { // piece is weaker
					piece.setPosition(oldR, oldC); // reverts to original position
					return false;
				}
			}
			
			else {
				if ("W".equals(m)){
					piece.setPosition(newR - 2, newC); 
					
					
					board[newR - 2][newC].setPiece(piece, newR - 2, newC); 
					board[oldR][oldC].setNull();
				}
				else if ("S".equals(m)){
					piece.setPosition(newR + 3, newC); // update positions
					// after moving, set the old position back to its original object
					
					board[newR + 2][newC].setPiece(piece, newR + 2, newC); // update object on board to its new position
					board[oldR][oldC].setNull();
				}	
				else if ("A".equals(m)){
					piece.setPosition(newR, newC - 3); // update positions
					// after moving, set the old position back to its original object
					
					board[newR][newC - 3].setPiece(piece, newR, newC - 3); // update object on board to its new position
					board[oldR][oldC].setNull();
				}
				else if ("D".equals(m)){
					piece.setPosition(newR, newC + 3); // update positions
					// after moving, set the old position back to its original object
				
					board[newR][newC + 3].setPiece(piece, newR, newC + 3); // update object on board to its new position
					board[oldR][oldC].setNull();
				}
				return true; // there's no piece on resulting tile
			}
		}
		
		if (targetTile.getObject() instanceof Piece) { // capturing opposing piece
			Piece targetPiece = targetTile.getPiece();
			
			if (piece.capture(targetPiece)) { // capture piece
				piece.setPosition(newR, newC); // update position of piece
				targetTile.setPiece(piece, newR, newC); // move piece on board
				board[oldR][oldC].setNull();
				System.out.println("piece moves here lake");
				return true;
			}
		}
		
		piece.setPosition(newR, newC); // update positions
		// after moving, set the old position back to its original object
		
		System.out.println("Moving piece from (" + oldR + "," + oldC + ") to (" + newR + "," + newC + ")");
		
		board[newR][newC].setPiece(piece, newR, newC); // update object on board to its new position
		board[oldR][oldC].setNull();
		
		return valid;
	}
	

	/**
 * Determines the move direction based on position changes, including regular moves and lake crossings.
 * 
 * <p>This method evaluates the positional difference between the original and new coordinates
 * to determine the movement direction, supporting both single-square moves and special lake jumps.
 * 
 * <p>Movement codes returned:
 * <ul>
 *   <li>"W" - Move/Jump Up (North)</li>
 *   <li>"A" - Move/Jump Left (West)</li>
 *   <li>"S" - Move/Jump Down (South)</li>
 *   <li>"D" - Move/Jump Right (East)</li>
 *   <li>"null" - Invalid move pattern</li>
 * </ul>
 * 
 * <p>Special lake jump cases (for Lion/Tiger):
 * <ul>
 *   <li>Vertical jumps: 3 rows (up/down)</li>
 *   <li>Horizontal jumps: 4 columns (left/right)</li>
 * </ul>
 * 
 * @param oldR Original row position (0-6)
 * @param oldC Original column position (0-8)
 * @param newR New row position (0-6)
 * @param newC New column position (0-8)
 * @return Movement direction code ("W","A","S","D") or "null" if invalid
 * @throws IllegalArgumentException if positions are outside board boundaries
 */
	public String determineMove (int oldR, int oldC, int newR, int newC) {
		// determines the move based on oldR, oldC and newR, newC (for regular movement and crossing lakes)
		
		if ((oldR - newR == 1 && oldC == newC) || (oldR - newR == 3 && oldC == newC))
			return "W";
		else if ((oldC - newC == 1 && oldR == newR) || (oldC - newC == 4 && oldR == newR))
			return "A";
		else if ((newR - oldR == 1 && oldC == newC) || (newR - oldR == 3 && oldC == newC))
			return "S";
		else if ((newC - oldC == 1 && oldR == newR) || (newC - oldC == 4 && oldR == newR))
			return "D";
		
		return "null";
	}
	

	/**
 * Validates whether a piece can move to the specified board coordinates.
 * <p>
 * Checks all game rules for valid movement including:
 * <ul>
 *   <li>Boundary checks</li>
 *   <li>Move pattern validation (single step or lake crossing)</li>
 *   <li>Terrain restrictions (lakes, traps, dens)</li>
 *   <li>Piece interaction rules (captures, blocking)</li>
 * </ul>
 *
 * <p>Specific validation rules:
 * <ol>
 *   <li>Move must be within board boundaries</li>
 *   <li>Must follow valid movement pattern (orthogonal or lake crossing)</li>
 *   <li>Cannot enter friendly den or traps</li>
 *   <li>Non-swimmers cannot enter lakes</li>
 *   <li>Lake crossers (Lion/Tiger) must have clear path</li>
 *   <li>Cannot capture own pieces</li>
 *   <li>Can only capture weaker/equal opponent pieces</li>
 * </ol>
 *
 * @param piece The piece attempting to move
 * @param newR The target row coordinate (0-6)
 * @param newC The target column coordinate (0-8)
 * @return true if the move is valid according to game rules, false otherwise
 * @see #isWithinBounds(int, int)
 * @see #determineMove(int, int, int, int)
 * @see #isRestrictedTile(Piece, int, int)
 * @see #isLakeRowEmpty(int)
 * @see #isLakeColEmpty(int, int)
 */
	public boolean isValidMove (Piece piece, int newR, int newC) { // checks if piece move is valid
		
		int currR = piece.getRow(), currC = piece.getColumn();
		String move;
		// switch (m) {
			// case "W":
				// newR--;
				// break;
			// case "S": 
				// newR++; 
				// break;
			// case "A": 
				// newC-- ; 
				// break;
			// case "D": 
				// newC++; 
				// break;
			// default: 
				// return false; // invalid move input
		// }
		
		if (!isWithinBounds(newR, newC)) // if out of bounds
			return false;
			
		if (determineMove(currR, currC, newR, newC).equals("null")) // if move is not single-tile, or crossing lake
			return false;
		move = determineMove(currR, currC, newR, newC);
		Grid targetTile = board[newR][newC];
		
		if (isRestrictedTile(piece, newR, newC)) // if targetTile is a friendly trap or home base
			return false;
		
		if (targetTile.getObject().equals('~') && !piece.canSwim() && !piece.canCross()){ // if piece wants to go to lake but can't swim
		System.out.println("piece cant cross lake");
			return false;
		}	

		if (targetTile.getObject().equals('~') && piece.canSwim() && !piece.canCross()){
			System.out.println("piece swims lake");
			return true;
		} // if piece wants to go to lake but can't swim
			
		
		// if piece can cross but lake row/col is occupied with ratR
		if (targetTile.getObject().equals('~') && piece.canCross() && !isLakeRowEmpty(newR) && (move.equalsIgnoreCase("a") || move.equalsIgnoreCase("d"))){
			System.out.println("rat in lake row");
			return false;
		}
		
		if (targetTile.getObject().equals('~') && piece.canCross() && !isLakeColEmpty(newR, currC) && (move.equalsIgnoreCase("s") || move.equalsIgnoreCase("w"))){
			System.out.println("rat in lake col");
			return false;
		}

		else if (targetTile.getObject() instanceof Piece) {
			Piece targetPiece = targetTile.getPiece();
			if (piece.getNumber() == targetPiece.getNumber()) // ensures it can only capture/move to opposing pieces
				return false;
			if (!piece.isStronger(targetPiece))
				return false;
		}
		return true;
	}
	/**
	 * Checks if the specified row and column are within the valid board boundaries.
	 *
	 * @param r The row index to check.
	 * @param c The column index to check.
	 * @return true if the coordinates are within bounds, false otherwise.
	 */
	public boolean isWithinBounds (int r, int c) {
		return r >= 0 && r <= 6 && c >= 0 && c <= 8;
	}
	

	/**
	 * Checks if a given row in the lake area is empty (i.e., contains no pieces).
	 *
	 * @param r The row to check (new row position).
	 * @return true if the specified lake row is empty, false if a piece is present.
	 */
	public boolean isRestrictedTile (Piece piece, int r, int c) { // ensures players don't go to their traps/bases
		
		if (piece.getNumber() == 1)
			return r == 3 && c == 0; // only home base
		else if (piece.getNumber() == 2)
			return r == 3 && c == 8;
		
		return false;
	}
	

	/**
 	* Checks if a given row in the lake area is empty (i.e., contains no pieces).
 	*
 	* @param r The row to check (new row position).
 	* @return true if the specified lake row is empty, false if a piece is present.
 	*/
	public boolean isLakeRowEmpty (int r) { // r = newR; checks if lakeRow is clear of rats
		int i;
		
		for (i = 3; i <= 5; i++) {
			if (board[r][i].getPiece() != null) // if there's a piece (rat) in the lake tile)
					return false;
		}
		
		return true;
	}
	

	/**
	* Checks if a given column in the lake area is empty (i.e., contains no pieces).
	*
	* @param r The row to check (new row position).
	* @param c The column to check (current column position).
	* @return true if the specified lake column is empty, false if a piece is present.
	*/
	public boolean isLakeColEmpty (int r, int c) { // c = currC, r = newR
		int i;
		
		if (r == 1 || r == 2) {
			for (i = 1; i <= 2; i++) {
				if (board[i][c].getPiece() != null) // if there's a piece (rat) in the lake tile)
						return false;
			}
		}
		
		else if (r == 4 || r == 5) {
			for (i = 4; i <= 5; i++) {
				if (board[i][c].getPiece() != null) // if there's a piece (rat) in the lake tile)
						return false;
			}
		}
		
		
		return true;
	}
	

	/**
	 * Searches for a piece on the board by its name and player number.
	 *
	 * @param pieceName The name of the piece to search for.
	 * @param playerNo  The player number who owns the piece.
	 * @return The Piece object if found; otherwise, returns null.
	 */
	public Piece searchforPiece(String pieceName, int playerNo){
		int row,col;

		for (row = 0; row < 7; row++){
			for (col = 0; col < 9; col++){
				if(board[row][col].getObject() instanceof Piece){
					if(board[row][col].getPiece().getNumber() == playerNo && board[row][col].getPiece().getPieceName().equals(pieceName)){
						if (board[row][col].getPiece() != null){
							return board[row][col].getPiece();
						
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Retrieves the content of a specific grid cell, either a Piece or terrain character.
	 * 
	 * <p>Returns in this priority:
	 * <ol>
	 *   <li>The Piece occupying the cell (if any)</li>
	 *   <li>The terrain character (if no piece present)</li>
	 * </ol>
	 * 
	 * @param row The row index (0-6) of the grid position
	 * @param col The column index (0-8) of the grid position
	 * @return The Piece object if present, otherwise the Character terrain value
	 * @throws ArrayIndexOutOfBoundsException if row or column is out of valid range
	 */
	public Object getGrid (int row, int col) {
		if (board[row][col].getPiece() != null)
			return board[row][col].getPiece();
		return board[row][col].getTerrain();
	}
	
	/**
	 * Applies or removes the weakened status from a piece based on trap positioning.
	 * 
	 * <p>A piece becomes weakened when:
	 * <ul>
	 *   <li>Standing on an opponent's trap (left traps for player 2, right traps for player 1)</li>
	 * </ul>
	 * 
	 * <p>The weakened status is removed when:
	 * <ul>
	 *   <li>Moving off a trap</li>
	 *   <li>Standing on one's own trap</li>
	 *   <li>Standing on normal terrain</li>
	 * </ul>
	 * 
	 * @param piece The piece to check and potentially weaken
	 * @see #isTrap(int, int)
	 */
	public void trapped (Piece piece) {
		if (isTrap(piece.getRow(), piece.getColumn()) == piece.getPlayerNumber()){
		piece.setWeak();
		}
		else if (isTrap(piece.getRow(), piece.getColumn()) == piece.getPlayerNumber()){
			piece.setWeak();
			}
		else{
			piece.setNotWeak();
		}
	 }
	
	 /**
	 * Determines if a grid position contains a trap belonging to a specific player.
	 * 
	 * <p>Trap ownership is determined by position:
	 * <ul>
	 *   <li>Left side traps (columns 0-2) belong to player 2</li>
	 *   <li>Right side traps (columns 6-8) belong to player 1</li>
	 * </ul>
	 * 
	 * @param row The row position to check
	 * @param col The column position to check
	 * @return 1 if trap belongs to player 1, 2 if belongs to player 2, -1 if not a trap
	 * @throws ArrayIndexOutOfBoundsException if row or column is out of valid range
	 */
	 public int isTrap(int row, int col){
		if (board[row][col].getTerrain() == '#' && col < 3)
			return 2;
		if (board[row][col].getTerrain() == '#' && col > 5)
			return 1;	
		return -1;
	 }

	 /**
	 * Modifies a rat's strength based on its position in water terrain.
	 * <p>
	 * Implements special game rules for rats:
	 * <ul>
	 *   <li>When in water ('~' terrain), rat becomes unkillable (strength = 9)</li>
	 *   <li>When not in water, rat returns to normal strength (strength = 1)</li>
	 * </ul>
	 * 
	 * <p>This ability allows rats to:
	 * <ul>
	 *   <li>Survive against stronger pieces while in water</li>
	 *   <li>Maintain normal vulnerability on land</li>
	 * </ul>
	 *
	 * @param piece The rat piece to modify (must be instance of {@link rat})
	 * @throws IllegalArgumentException if piece is not a rat instance
	 * @see rat
	 * @see Piece#setStrength(int)
	 */
	 public void unkillableRat(Piece piece){
		if (piece instanceof rat && board[piece.getRow()][piece.getColumn()].getTerrain() == '~'){
			piece.setStrength(9);
		}
		else if(piece instanceof rat && !(board[piece.getRow()][piece.getColumn()].getTerrain() == '~')){
			piece.setStrength(1);
		}
	 }
	
}

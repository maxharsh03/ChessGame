# ChessGame
## Creates a chess game that a user can play against an artificial intelligence player. Contains all basic rules of chess like castling, captures, en passant, and pawn promotion.  

# Chess
## Has 5 packages
    1. ui - has ChessGUI which is the user interface for the ChessGame
    2. board - has Board which implements the the chess board and how pieces interact with it
    3. piece - has Piece, Pawn, Rook, Queen, King, Bishop, and Knight. Piece contains common functionality for all other 
    pieces. Each piece implements its own unique functionality. 
    4. player - contains Player which stores info about a current player and their state in the game.
    5. manager - contains GameManager which manages the functionality between the board, pieces, and player.

/** 
 * MSGame.java 
 **/ 
  
import java.awt.BorderLayout; 
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent; 
import javax.swing.JPanel; 
import javax.swing.JLabel; 
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
  
/** 
 * MSGame is the main GUI panel 
 **/ 
public class MSGame extends JPanel 
{ 
 
    //INSTANCE PROPERTIES 
    //an array for the squares 
    private Square[][] board; 
    //show user instructions 
    private JLabel infoBar; 
    //constant for the number of mines 
    private int numMine; 
    //number of the squares that the user needs to open
    private int remainingSquares; 
    //the button for restarting game 
    private JButton restart; 
    // declare the length of the array
    int boardRow;
    // declare the width of the array
    int boardCol;
    //determine the digit inside the square
    int countMines;
    //declare a panel
    JPanel panel;
     
  
    /**  
     * Constructor. 
     **/ 
    public MSGame() 
    { 
	//initialize the GUI 
	initGUI();
    } 
 
    /**  
     * Methods 
     **/ 

    //initialize game display
    public void initGUI() 
    {
	//use BorderLayout 
	setLayout( new BorderLayout() );
	// add board of square to CENTER 
	add( createBoard(), BorderLayout.CENTER ); 

	//declare the smiling icon
	ImageIcon smiling = new ImageIcon("face.png");
	//initialize the restart button  
	restart = new JButton();
	//add image on the button
	restart.setIcon(smiling);
	//add infoBar to NORTH
	add(restart, BorderLayout.NORTH ); 
	// add an action listener for button's action (click) 
	restart.addActionListener(new ActionListener() 
	    { 
		/** 
		 * Invoked when associated action is performed. 
		 **/ 
		public void actionPerformed( ActionEvent e ) 
		{ 
		    //invoke playButton method 
		    restartGame(); 
		} 
	    } 
	    ); 
    }
    
    //Create a JPanel with a board of squares 
    public JPanel createBoard()  
    {	
	// set the length of the array
	boardRow = 8;
	// set the width of the array
	boardCol = 8;
 
	//initialize the board
	board = new Square[boardRow][boardCol];
	// initialize the contents of myArray
	for ( int i = 0; i < boardRow; i++){
	    for (int j = 0; j < boardCol; j++){
		//each index is a new instance of class Square
		board[i][j] = new Square();
		//set the number of adjacent mines to 0
		board[i][j].setNumMines(0);
		//add an action listener for button's action (click) 
		board[i][j].addActionListener(new ActionListener() 
		    { 
			/** 
			 * Invoked when associated action is performed. 
			 **/ 
			public void actionPerformed( ActionEvent e ) 
			{
			    //invoke playButton method 
			    squarePressed(e); 
			} 
		    } 
		    );
	    }
	}
	//place mines on board
	placeMines();

	//for each index of the board
	for ( int a = 0; a < boardRow; a++){
	    for (int b = 0; b < boardCol; b++){
		//set the number of adjacent mines on the square
		board[a][b].setNumMines(getNumAdjMines(a, b));
	    }
	}
	//create JPanel
	panel = new JPanel();
	//set layout of the panel
	panel.setLayout( new GridLayout(boardRow, boardCol) );
	//for each index of the array
	for ( int m = 0; m < boardRow; m++){
	    for (int n = 0; n < boardCol; n++){
		//add squares to the panel
		panel.add(board[m][n]);
	    }
	}
	//return the panel
	return panel;
    }
    
    //place the mine and have it remembered
    public void placeMines()
    {
	//set the number of mines
	numMine = (int)Math.floor(0.125 * boardRow * boardCol);
	//for each index in the array
	for (int i = 0; i < numMine; i++)
	    {
		//choose a random row
		int randomRow = (int)Math.floor(Math.random()*boardRow);
		//choose a random column
		int randomCol = (int)Math.floor(Math.random()*boardCol);
		//set the square at that random position to have mine
		board[randomRow][randomCol].hasMine = true;	
	    }
    }

    //get the number of mines from surrounding square
    public int getNumAdjMines(int x, int y)
    {
	//set mine number to 0
	countMines = 0;
	//run through the adjacent square array
	for ( int i = x - 1; i <= x + 1; i++){
	    for (int j = y - 1; j <= y + 1 ;j++){
		//for every square, count mines around
		//if the square doesn't has a mine and is in bound
		if ((!board[x][y].hasMine) && (inBound(i,j)) && (board[i][j].hasMine))
		    {
			//add the number of mines
			countMines++;
		    }
	    }
	}
	//return countMines
	return countMines;
    }

    
    //Invoked when a square is pressed; e is the ActionEvent associated with the press
    public void squarePressed(ActionEvent e)
    {
	//if game is lost
	if (gameLost(e))
	    {
		//reveal all mines
		revealMines();
		//pop up a message
		JOptionPane.showMessageDialog(this, "YOU LOST! Click the smiling face to try again");
		//disable all buttons
		disableBoard();
	    }
	
	//reveal the number of mines on the square
	revealSquare(e);

	//if game is won
	if (gameWon())
	    {
		//pop up a message
		JOptionPane.showMessageDialog(this, "YOU WON! Click the smiling face to play again");
		//disable all buttons
		disableBoard();
	    }
    }

    //to reveal the square*****
    public void revealSquare(ActionEvent e)
    {
	//for each index of the board
	for ( int i = 0; i < boardRow; i++){
	    for (int j = 0; j < boardCol; j++){
		//declare var to be the square got clicked
		Square s = (Square)e.getSource();
		if (s.equals( board[i][j]))
		    {   
			//reveal the square
			board[i][j].reveal();
			//if the square doesn't have any adjacent mine
			if ((getNumAdjMines(i, j) == 0) && (!board[i][j].hasMine))
			    {
				//invoke revealSquareRecursive method
				revealSquareRecursive(i, j);
				//disable the square
				board[i][j].setEnabled(false);
			    }
		    }
	    }
	}	
    }

    //to reveal the square
    public void revealSquareRecursive( int x, int y)
    {
	//run through the adjacent square array
	for ( int i = x - 1; i <= x + 1; i++){
	    for (int j = y - 1; j <= y + 1 ;j++){
		//if the square is inbound
		if((inBound(i, j)) && (!board[i][j].isRevealed()))
		    {
			//reveal the square
			board[i][j].reveal();
			//check to see if it's a 0-mine square
			if (getNumAdjMines(i, j) == 0)
			    //reveal its surrounding squares too
			    revealSquareRecursive(i, j);
		    }
	    }	    
	}
    }


    //to reveal the mine
    public void revealMines()
    {
	//for each index of the board, reveal all mines
	for ( int i = 0; i < boardRow; i++){
	    for (int j = 0; j < boardCol; j++){
		//if the square has mine
		if (board[i][j].hasMine)
		    //reveal it
		    board[i][j].reveal();
	    }
	}
    }
    
    //Check if the surrounding square is in bound
    public boolean inBound(int x, int y)
    {
	//if the square is out of row
	if ((x < 0) || (x >= boardRow))
	    //return false
	    return false;
	//if the square is out of column
	if ((y <0) || (y >= boardCol))
	    //return false
	    return false;
	//else
	else
	    //return true
	    return true;
    }
	
    //Check if the mine button was pressed. If so, the game is over. Otherwise, the game continues
    public boolean gameLost(ActionEvent e)
    {
	//if mine is revealed
	//for each index of the board
	for ( int i = 0; i < boardRow; i++){
	    for (int j = 0; j < boardCol; j++){
		//if player click on the square that has mine
		if ((e.getSource() == board[i][j]) && (board[i][j].hasMine))
		    //return true
		    return true;
	    }
	}
	//return false
	return false;
    }

    //check if the game is won
    public boolean gameWon()
    {
	//variable a to hold value
	int a = 0;
	//for each index of the board
	for ( int i = 0; i < boardRow; i++){
	    for (int j = 0; j < boardCol; j++){
		//if every non-mine is revealed
		if ((!board[i][j].hasMine) && (!board[i][j].isRevealed()))
		    //increment a
		    a++;
	    }
	}
	//if a hasn't been changed
	if (a == 0)
	    //return true
	    return true;
	else
	    //return false
	    return false;
    }

    //disable all buttons in board
    public void disableBoard(){
	//for each index of the board
	for ( int i = 0; i < boardRow; i++){
	    for (int j = 0; j < boardCol; j++){
		//disable all buttons
		board[i][j].setEnabled(false);}}
    }
    
    //Invoked when the play button is pressed
    public void restartGame()
    {
	//remove all components in panel
	panel.removeAll();
	//revalidate the panel
	panel.revalidate();
	//refresh the game
	initGUI();
    }

}

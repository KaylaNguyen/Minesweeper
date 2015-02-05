import javax.swing.JButton;
import javax.swing.ImageIcon;
/** 
 * Square is the class for individual square in board 
 **/ 
public class Square extends JButton
{
    //INSTANCE PROPERTIES
    //declare a variable to determine if the square is revealed
    private boolean isRevealed;
    //declare a variable to determine if the square has mine
    boolean hasMine;
    //declare a variable to hold the number of mines around
    private int numAdjMines;

    //CONSTRUCTOR
    public Square()
    {
	//set the square to be unrevealed
	isRevealed = false;
	//set the number of each square
	numAdjMines = 1000;
	//set not to have mine
	hasMine = false;
    }

    //return the boolean 
    public boolean isRevealed(){
	//if the square is revealed
	if (this.isRevealed)
	    //disable the button
	    this.setEnabled(false);
	//return isRevealed
	return isRevealed;
    }
    
    //reveal the square
    public void reveal(){
	//set isRevealed to true
	isRevealed = true;
	//if the square has mine
	if(hasMine)
	    {
		//declare the bomb icon
		ImageIcon bomb = new ImageIcon("bomb.png");
		//add image on the button
		this.setIcon(bomb);
	    }
	//else
	else
	    {
		//set text to the square
		this.setText(Integer.toString(numAdjMines));
	    }

    }

    //set number of mines
    public void setNumMines(int m){
	//set the number of mines
	numAdjMines = m;
    }
}


    
    
    

/** 
 * MSApplication wraps a MSPanel for the Minesweeper game. 
 **/

import javax.swing.JFrame; 

public class MSApplication  
{ 
    public static void main( String[] args ) 
    {
	JFrame msFrame = new JFrame( "Minesweeper" ); 
	msFrame.getContentPane().add( new MSGame() ); 
	msFrame.setSize( 700, 700 ); 
	msFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); 
	msFrame.setVisible( true ); 

    }
}

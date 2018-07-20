/* GameButton class
	Creates a smarter button that can remeber its original starting position
	on the game board and test to see if the button is in its
	starting position after a certain period of time
*/
import java.awt.*;
import javax.swing.*;

class GameButton extends JButton
{
	int posX, posY;

	public GameButton(Integer x, Integer y)
	{
		posX = x;
		posY = y;
	}

	public void testLocation(Integer x, Integer y)
	{
		if (posX == x && posY == y)
		{
			System.out.println("I'm in my right spot");
		}
	}

	public boolean testComplete(Integer x, Integer y)
	{
		if (posX == x && posY == y)
			return true;
		else
			return false;
	}
}
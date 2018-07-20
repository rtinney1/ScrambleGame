/* class ToggleButton()
	Creates a new kind of button that will toggle between two different
	labels and ActionCommands
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ToggleButton extends JButton implements ActionListener
{
	String  toggle1U, toggle2U, toggle1, toggle2;
	boolean toggleCheck;

	public ToggleButton( String one, String two)
	{
		toggle1 = one;
		toggle2 = two;
		toggle1U = toggle1.toUpperCase();
		System.out.println("My toggle1 is: " + toggle1);
		System.out.println("My toggle1U is: " + toggle1U);
		toggle2U = toggle2.toUpperCase();
		System.out.println("My toggle2 is: " + toggle2);
		System.out.println("My toggle2U is: " + toggle2U);

		addActionListener(this);
		setText(toggle1);
		setActionCommand(toggle1U);
		toggleCheck = true;
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals(toggle1U))
		{
			setText(toggle2);
			setActionCommand(toggle2U);
			System.out.println("I've changed toggles to " + toggle2);
			toggleCheck = false;
		}
		else if(ae.getActionCommand().equals(toggle2U))
		{
			setText(toggle1);
			setActionCommand(toggle1U);
			System.out.println("I've changed toggles to " + toggle1);
			toggleCheck = true;
		}
	}

	public void changeToggles()
	{
		if (getToggles() == 1)
		{
			setText(toggle2);
			setActionCommand(toggle2U);
			System.out.println("I've changed toggles to " + toggle2);
		}
		else
		{
			setText(toggle1);
			setActionCommand(toggle1U);
			System.out.println("I've changed toggles to " + toggle1);
		}
	}

	public int getToggles()
	{
		if (toggleCheck == true)
			return 1;
		else
			return 2;
	}
}//end ToggleButton class

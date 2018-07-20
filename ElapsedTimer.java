/* class ElapsedTimer
	creates a new label that works like a game timer. Counts each tenth of a second
	and displays the time. Allows for starting, stopping, and resetting of timer.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ElapsedTimer extends JLabel implements ActionListener
{
	Timer elapsedTimer;
	long  startTime, currTime, totMillis, currMillis, lapsedMilli, lapsedSec, lapsedMin;
	//float lapsedTimeSec;

	ElapsedTimer()
	{
		elapsedTimer = new Timer (25, this);
		elapsedTimer.setCoalesce(true);
		totMillis = 0;
	}

	public void actionPerformed(ActionEvent ae)
	{
		updateTimeLabel();
	}

	public void updateTimeLabel()//cant do pause time yet
	{
		currTime = System.currentTimeMillis();

		currMillis = currTime - startTime + totMillis;

		lapsedMilli = (currMillis % 1000) / 100;
		lapsedSec = (currMillis / 1000) % 60;
		lapsedMin = (currMillis / 1000) / 60;

		if (lapsedSec < 10)
			setText(" " + lapsedMin + ":0" + lapsedSec + "." + lapsedMilli);
		else
			setText(" " + lapsedMin + ":" + lapsedSec + "." + lapsedMilli);
	}

	public void start()
	{
		elapsedTimer.start();
		startTime = System.currentTimeMillis();
	}

	public void pause()
	{
		elapsedTimer.stop();
		totMillis = currMillis;
	}

	public void stop()
	{
		elapsedTimer.stop();
		totMillis = 0;
	}

}//end ElapsedTimer Class
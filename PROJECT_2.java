/* PROJECT_2 Picture Game
	Java game that allows the user to select an image and board dimension size
	3x3, 4x4, or 5x5. The image will the be cropped into the appropriate amount of
	pieces and displayed in JButtons. Once the start button is clicked, the pieces will
	shuffle randomly and the timer will start. The user will then have to rearrange the pieces
	to form the original picture.

	Programmer: Randi Tinney
	Date last modified: 11 Oct 2016
*/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.Random;

//main class
public class PROJECT_2
{
	public static void main (String args[])
	{
		System.out.println("Starting");
        new GameFrame();
    	System.out.println("Finished");
	}
}//end main class

//GameFrame()
class GameFrame extends JFrame implements ActionListener
{
	JButton 	  		 newgameButton, exitButton;
	JRadioButton  		 threeRButton, fourRButton, fiveRButton;
	BufferedImage 		 origImg;
	Container     		 maincp;
	Board          		 gameBoard;
	JLabel        		 movesTakenLBL, movesLBL, timeElapsedLBL, errorLabel, winLabel, correctLabel;
	ToggleButton   		 playButton, startButton;
	int 		  		 num_move;
	Integer 	  		 board_size;
	ElapsedTimer  		 myTimer;
	JFileChooser   		 chooser;
	File 	  	   		 inputFL;
	boolean        		 gameStarted;
	JPanel		   		 boardPL, imgPreviewPL;
	BuffImageComponent   imgPreview;
	boolean        		 imgThere;
	boolean              gameWon;
	//Image		   resizeOrigImg;

	public GameFrame()
	{
		JPanel textPanel, buttonPanel, radioPanel;
		JLabel sizeLabel, piecesLabel;

		movesTakenLBL = new JLabel();

		movesLBL = new JLabel("# of Moves");

		timeElapsedLBL = new JLabel("Time Elapsed: ");

		myTimer = new ElapsedTimer();

		imgThere = false;

		num_move = 0;

		board_size = 0;

		gameStarted = false;

		winLabel = new JLabel();

		imgPreviewPL = new JPanel();

		piecesLabel = new JLabel("# Pieces out of Place: ");
		correctLabel = new JLabel();

		textPanel = new JPanel();
		textPanel.add(winLabel);
		textPanel.add(piecesLabel);
		textPanel.add(correctLabel);
		textPanel.add(movesLBL);
		textPanel.add(movesTakenLBL);
		textPanel.add(timeElapsedLBL);
		textPanel.add(myTimer);

		ButtonGroup sizeGroup;

		chooser = new JFileChooser(".");
		inputFL = null;

		boardPL = new JPanel();

		sizeLabel = new JLabel("Choose size: ");

		threeRButton = new JRadioButton("3x3");
		threeRButton.setSelected(true);

		fourRButton = new JRadioButton("4x4");

		fiveRButton = new JRadioButton("5x5");

		sizeGroup = new ButtonGroup();
		sizeGroup.add(threeRButton);
		sizeGroup.add(fourRButton);
		sizeGroup.add(fiveRButton);

		newgameButton = new JButton("New picture");
		newgameButton.addActionListener(this);
		newgameButton.setActionCommand("new");

		playButton = new ToggleButton("Pause", "Play");
		playButton.addActionListener(this);
		playButton.setEnabled(false);

		startButton = new ToggleButton("Start", "Reset");
		startButton.addActionListener(this);
		startButton.setEnabled(false);

		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		exitButton.setActionCommand("EXIT");

		buttonPanel = new JPanel(new GridLayout (2, 4));
		buttonPanel.add(newgameButton);
		buttonPanel.add(playButton);
		buttonPanel.add(startButton);
		buttonPanel.add(exitButton);
		buttonPanel.add(sizeLabel);
		buttonPanel.add(threeRButton);
		buttonPanel.add(fourRButton);
		buttonPanel.add(fiveRButton);

		maincp = getContentPane();
		maincp.add(textPanel, BorderLayout.NORTH);
		maincp.add(buttonPanel, BorderLayout.SOUTH);

		setUpMainFrame();
	}//end constructor

	public void actionPerformed(ActionEvent ae)
	{
		FileNameExtensionFilter filter;
		int 					returnVal;

		if(ae.getActionCommand().equals("new"))
		{
			myTimer.pause();

			filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
			chooser.setFileFilter(filter);
			returnVal = chooser.showOpenDialog(null);

			startButton.setEnabled(false);

			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
				System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
				inputFL = chooser.getSelectedFile();
				if (startButton.getToggles() == 2)
					startButton.changeToggles();

				try
				{
					origImg = ImageIO.read(inputFL);
					if (gameStarted || gameWon)
					{
						myTimer.stop();
						gameBoard.removePieces();
						maincp.remove(boardPL);
						boardPL.remove(gameBoard);
						maincp.remove(imgPreviewPL);
						gameBoard.hideOImage();
						//gameBoard = null;
						revalidate();
						System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
						repaint();
						//pack();
						if (startButton.getToggles() == 2)
							startButton.changeToggles();
						if(playButton.getToggles() == 2)
							playButton.changeToggles();
						gameStarted = false;
						gameWon = false;
						playButton.setEnabled(false);
						startButton.setEnabled(false);
					}
					//resizeOrigImg = resizeOrigImg.getScaledInstance (350/board_size+2, 350/board_size+2, Image.SCALE_DEFAULT);
					if (origImg == null)
					{
						winLabel.setForeground(Color.RED);
						winLabel.setText("Not an Image!");
					}
					else
					{
							threeRButton.setEnabled(true);
							fourRButton.setEnabled(true);
							fiveRButton.setEnabled(true);
							startButton.setEnabled(true);
							winLabel.setText("");
							correctLabel.setText("");
							movesTakenLBL.setText("");
							if(imgThere)
							{
								imgPreviewPL.remove(imgPreview);
								imgPreview.setImage(origImg);
								imgPreviewPL.add(imgPreview);
							}
							else
							{
								imgPreview = new BuffImageComponent(origImg);
								imgPreviewPL.add(imgPreview);
								imgThere = true;
								//maincp.add (imgPreviewPL, BorderLayout.CENTER);
							}
							maincp.add (imgPreviewPL, BorderLayout.CENTER);
							revalidate();
							repaint();
					}
				}
				catch (IOException e)
				{
					winLabel.setForeground(Color.RED);
					winLabel.setText("Not a File!");
				}
			}//end main if statement
			else
			{
				if(origImg != null)
					startButton.setEnabled(true);
				else if (gameStarted)
					myTimer.start();
				else
					startButton.setEnabled(false);
			}
		}//end new action
		else if(ae.getActionCommand().equals("START"))
		{
			maincp.remove(imgPreviewPL);

			if(!gameStarted)
			{
				if(threeRButton.isSelected())
					board_size = 3;
				else if (fourRButton.isSelected())
					board_size = 4;
				else
					board_size = 5;
				System.out.println("" + board_size);
				gameBoard = new Board(board_size, origImg, movesTakenLBL, num_move, winLabel, maincp, myTimer, correctLabel, this);
				gameBoard.shuffle();
				gameStarted = true;
				boardPL.add(gameBoard);
				maincp.add(boardPL, BorderLayout.CENTER);
				System.out.println("***********************************************");
				threeRButton.setEnabled(false);
				fourRButton.setEnabled(false);
				fiveRButton.setEnabled(false);
			}
			else
			{
				gameBoard.shuffle();
			}

			revalidate();
			repaint();

			playButton.setEnabled(true);
			myTimer.start();
		}//end start button
		else if (ae.getActionCommand().equals("RESET"))
		{
			myTimer.stop();
			gameBoard.reset();
			playButton.setEnabled(false);
		}
		else if(ae.getActionCommand().equals("PAUSE"))
		{
			myTimer.pause();
			maincp.remove(boardPL);
			//maincp.add(imgPreviewPL);
			gameBoard.showOImage();
			revalidate();
			repaint();
			startButton.setEnabled(false);
		}
		else if (ae.getActionCommand().equals("PLAY"))
		{
			myTimer.start();
			//maincp.remove(imgPreviewPL);
			gameBoard.hideOImage();
			maincp.add(boardPL, BorderLayout.CENTER);
			revalidate();
			repaint();
			startButton.setEnabled(true);
		}
		else if(ae.getActionCommand().equals("EXIT"))
			System.exit(1);
	}//end actionPerformed

	void setUpMainFrame()
	{
		Toolkit tk;
	    Dimension d;

	    tk = Toolkit.getDefaultToolkit();
	    d = tk.getScreenSize();
	    setSize(d.width/3, (d.height - d.height/6));
	    setLocation(d.width/4, 0);

	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    setTitle("Project 2: Picture Game");

	    setVisible(true);
    }//end setupMainFrame()
}//end GameFrame()
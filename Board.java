/* class Board
	creates the game board using the image specified by the user. Also
	shuffles the board using a random number generator.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.Random;
//.py
class Board extends JPanel implements ActionListener
{
	Integer 	   board_size, num_moves;
	BufferedImage  origImg;
	int 		   xBlank, yBlank;
	GameButton[][] gamePiece;
	JLabel 		   mLBL, winLabel, correctLabel;
	Container 	   maincp;
	BuffImageComponent imgComponent;
	JPanel         imgPanel;
	ElapsedTimer   eTimer;
	GameFrame      gFrame;

	public Board(Integer x, BufferedImage i, JLabel movesLBL, Integer m, JLabel winLBL, Container cp, ElapsedTimer et, JLabel cLabel, GameFrame frame)
	{

		board_size = x;

		origImg = i;

		num_moves = m;

		mLBL = movesLBL;

		winLabel = winLBL;

		maincp = cp;

		gFrame = frame;

		correctLabel = cLabel;

		imgPanel = new JPanel();

		eTimer = et;

		gamePiece = new GameButton[board_size + 2][board_size + 2];

		setPreferredSize(new Dimension(600, 600));

		create();
	}

	public void create()
	{
		System.out.println("Creating game board");
		int 		  total_buttons, w, h, x, y, leng;
		ImageIcon[]   imgArray;
		BufferedImage cropImg, subBufimg;
		ImageIcon     imgConvert;
		Image         tempImg;

		this.setLayout(new GridLayout(board_size + 2, board_size + 2));

		total_buttons = board_size * board_size;

		imgArray = new ImageIcon[total_buttons];

		w = origImg.getWidth();
		h = origImg.getHeight();
		System.out.println("" + w + "and" + h);

		x = 0;
		y = 0;


		if (w > h)
		{
			leng = (w - h)/2;
			System.out.println("" + leng + " " + h);
			cropImg = origImg.getSubimage(leng, 0, h, h);
			for (int i = 0, l = 0; i < board_size; i++)
			{
				for (int j = 0; j < board_size; j++, l++)
				{
					subBufimg = cropImg.getSubimage(x, y, h/board_size, h/board_size);
					tempImg = subBufimg;
					tempImg = tempImg.getScaledInstance (350/board_size+2, 350/board_size+2, Image.SCALE_DEFAULT);
					imgConvert = new ImageIcon(tempImg);
					imgArray[l] = imgConvert;
					x += h/board_size;
				}
				y += h/board_size;
				x = 0;
			}
		}
		else if (w < h)
		{
			leng = (h - w)/2;
			System.out.println("" + leng + " " + w);
			cropImg = origImg.getSubimage(0, leng, w, w);
			for (int i = 0, l = 0; i < board_size; i++)
			{
				for (int j = 0; j < board_size; j++, l++)
				{
					subBufimg = cropImg.getSubimage(x, y, w/board_size, w/board_size);
					tempImg = subBufimg;
					tempImg = tempImg.getScaledInstance (350/board_size+2, 350/board_size+2, Image.SCALE_DEFAULT);
					imgConvert = new ImageIcon(tempImg);
					imgArray[l] = imgConvert;
					x += w/board_size;
				}
				y += w/board_size;
				x = 0;
			}
		}
		else
		{
			cropImg = origImg.getSubimage(0, 0, w, h);
			for (int i = 0, l = 0; i < board_size; i++)
			{
				for (int j = 0; j < board_size; j++, l++)
				{
					subBufimg = cropImg.getSubimage(x, y, w/board_size, w/board_size);
					tempImg = subBufimg;
					tempImg = tempImg.getScaledInstance (350/board_size+2, 350/board_size+2, Image.SCALE_DEFAULT);
					imgConvert = new ImageIcon(tempImg);
					imgArray[l] = imgConvert;
					x += w/board_size;
				}
				y += w/board_size;
				x = 0;
			}
		}

		if (imgComponent == null)
			imgComponent = new BuffImageComponent(cropImg);
		else
			imgComponent.setImage(cropImg);
		imgPanel.add(imgComponent);

		for(Integer i = 0, k = 0; i < board_size + 2; i++)
		{
			for (Integer j = 0; j < board_size + 2; j++)
			{
				gamePiece[i][j] = new GameButton(i, j);
				gamePiece[i][j].addActionListener(this);
				gamePiece[i][j].setFocusPainted(true);
				gamePiece[i][j].setContentAreaFilled(false);
				gamePiece[i][j].setEnabled(false);
				gamePiece[i][j].setMargin(new Insets(0, 0, 0, 0));

				if(i == 0 || i == board_size + 1 || j == 0 || j == board_size + 1)
					gamePiece[i][j].setVisible(false);
				else
				{
					if ( k + 1 < total_buttons)
					{
						gamePiece[i][j].setIcon(imgArray[k]);
						k++;
					}
					else
					{
						xBlank = i;
						yBlank = j;
						gamePiece[i][j].setEnabled(false);
					}
				}//end else
					this.add(gamePiece[i][j]);
			}//end inner for loop
		}//end outer for loop
	}//end create()

	public void shuffle()
	{
		System.out.println("shuffleBoard has been called on");
		Random rand;
		int n;
		//int numButtons;

		rand = new Random();

		//numButtons = (size*size) - 1;

		for(Integer i = 0, k = 0; i < board_size + 2; i++)
		{
			for (Integer j = 0; j < board_size + 2; j++)
			{
				if(i != 0 || i != board_size + 1 || j != 0 || j != board_size + 1)
					gamePiece[i][j].setEnabled(true);
			}
		}

		removePieces();

		for (int count = 0; count < 1000; count++)
		{
			n = rand.nextInt(6);

			if ((n == 0 || n == 1 )&& xBlank - 1 > 0)
			{
				movePiece(gamePiece, xBlank, yBlank, xBlank - 1, yBlank);
				xBlank -=1;
			}

			else if(n == 2 && xBlank + 1 < board_size + 1)
			{
				movePiece(gamePiece, xBlank, yBlank, xBlank + 1, yBlank);
				xBlank +=1;
			}
			else if((n == 3 || n == 4) && yBlank - 1 > 0)
			{
				movePiece(gamePiece, xBlank, yBlank, xBlank, yBlank - 1);
				yBlank -=1;
			}
			else if(n == 5 && yBlank + 1 < board_size + 1)
			{
				movePiece(gamePiece, xBlank, yBlank, xBlank, yBlank + 1);
				yBlank +=1;
			}
		}//end shuffle

		replacePieces();

		System.out.println("Board Shuffled");
		System.out.println("Blank is at (" + xBlank + " , " + yBlank + ")");
	}

	public void removePieces()
	{
		for(int i = 0, k = 0; i < board_size + 2; i++)
		{
			for (int j = 0; j < board_size + 2; j++, k++)
			{
				this.remove(gamePiece[i][j]);
			}
		}
	}

	public void replacePieces()
	{
		for(int i = 0, k = 0; i < board_size + 2; i++)
		{
			for (int j = 0; j < board_size + 2; j++, k++)
			{
				this.add(gamePiece[i][j]);
				gamePiece[i][j].setEnabled(true);
			}
		}

		revalidate();
		repaint();
	}

	public void reset()
	{
		removePieces();
		num_moves = 0;
		create();
	}

	public void movePiece(JButton button[][], Integer x, Integer y, Integer m, Integer n)
	{
		JButton tempButton;

		tempButton = button[x][y];
		button[x][y] = button[m][n];
		button[m][n] = tempButton;
	}

	public void actionPerformed(ActionEvent ae)
	{
		int count = 0;
		if (ae.getSource().equals(gamePiece[xBlank + 1][yBlank]))
		{
			removePieces();
			System.out.println("Moving Piece Up");
			movePiece(gamePiece, xBlank, yBlank, xBlank + 1, yBlank);
			xBlank += 1;
			replacePieces();
			System.out.println("Blank is at (" + xBlank + " , " + yBlank + ")");
			num_moves++;
			mLBL.setText(" " + num_moves);
			gamePiece[xBlank - 1][yBlank].testLocation(xBlank - 1, yBlank);
			test();

		}
		else if(ae.getSource().equals(gamePiece[xBlank - 1][yBlank]))
		{
			removePieces();
			System.out.println("Moving Piece Down");
			movePiece(gamePiece, xBlank, yBlank, xBlank - 1, yBlank);
			xBlank -= 1;
			replacePieces();
			System.out.println("Blank is at (" + xBlank + " , " + yBlank + ")");
			num_moves++;
			mLBL.setText(" " + num_moves);
			gamePiece[xBlank + 1][yBlank].testLocation(xBlank + 1, yBlank);
			test();
		}
		else if(ae.getSource().equals(gamePiece[xBlank][yBlank + 1]))
		{
			removePieces();
			System.out.println("Moving Piece Right");
			movePiece(gamePiece, xBlank, yBlank, xBlank, yBlank + 1);
			yBlank += 1;
			replacePieces();
			System.out.println("Blank is at (" + xBlank + " , " + yBlank + ")");
			num_moves++;
			mLBL.setText(" " + num_moves);
			gamePiece[xBlank][yBlank - 1].testLocation(xBlank, yBlank - 1);
			test();
		}
		else if(ae.getSource().equals(gamePiece[xBlank][yBlank - 1]))
		{
			removePieces();
			System.out.println("Moving Piece left");
			movePiece(gamePiece, xBlank, yBlank, xBlank, yBlank - 1);
			yBlank -= 1;
			replacePieces();
			System.out.println("Blank is at (" + xBlank + " , " + yBlank + ")");
			num_moves++;
			mLBL.setText(" " + num_moves);
			gamePiece[xBlank][yBlank + 1].testLocation(xBlank, yBlank + 1);
			test();
		}
	}//end actionPerformed

	public void test()
	{
		int count;

		count = 0;

		for(int i = 0; i < board_size + 2; i++)
		{
			for (int j = 0; j < board_size + 2; j++)
			{
				gamePiece[i][j].testComplete(i, j);

				if(gamePiece[i][j].testComplete(i, j) == true)
					count++;
			}
		}

		correctLabel.setText ("" + ((board_size+2)*(board_size+2) - count - 1));
		if(count == ((board_size+2)*(board_size+2)))
		{
			winLabel.setForeground(Color.RED);
			winLabel.setText("You Win!");
			gFrame.gameWon = true;
			gFrame.gameStarted = false;
			gFrame.origImg = null;
			maincp.remove(gFrame.gameBoard);
			this.setVisible(false);
			gFrame.startButton.setEnabled(false);
			gFrame.playButton.setEnabled(false);
			showOImage();
			eTimer.pause();
		}
	}

	public void showOImage()
	{
		System.out.println("I'm Visibile!");
		maincp.add(imgPanel, BorderLayout.WEST);
		revalidate();
		repaint();
	}

	public void hideOImage()
	{
		System.out.println("I'm Invisibile!");
		maincp.remove(imgPanel);
		revalidate();
		repaint();
	}
}//end Board class

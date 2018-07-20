import java.awt.image.*;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;

class BuffImageComponent extends JPanel
{
    BufferedImage buffimg;
    Image		  img;
    int           board_size;

    public BuffImageComponent(BufferedImage i)
    {
		System.out.println("painting pretty pics");
      	buffimg = i;
      	img = buffimg;
      	setPreferredSize(new Dimension(400, 400));
      	setSize();
    }

	public void setSize()
	{
	   int w, h;
	   ImageIcon newImg;

	   System.out.println("ChangingSize");

	   w = buffimg.getWidth();
	   h = buffimg.getHeight();

	   img = img.getScaledInstance(w/2, h/2, Image.SCALE_DEFAULT);

	   newImg = new ImageIcon(img);
 	}

   	public void setImage(BufferedImage i)
   	{
		img = i;

		setSize();

		repaint();
	}

    @Override
    public void paintComponent(Graphics g1)
    {
        super.paintComponent(g1);
        Graphics2D g2;
        g2 = (Graphics2D)g1;
        g2.drawImage(img, 0, 0, this);
    }
}
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;

public class View extends JPanel
{
	private Model model;
	private int scrollPos;

	public View(Controller c, Model m)
	{
		model = m;
		c.setView(this);
		scrollPos = 0;
	}

	public void paintComponent(Graphics g)
	{
		scrollPos = model.getPacY() - Game.WINDOWSIZE / 2;
		if (scrollPos < 0){
			scrollPos = 0;
		}
		if (scrollPos > 100){
			scrollPos = 100;
		}
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, 500, 500);
		for(int i = 0; i < model.sprites.size(); i++){
			model.sprites.get(i).drawYourself(g, scrollPos);
		}
	}

	public static BufferedImage loadImage(String filename)
	{
		BufferedImage image = null;
		try{
			image = ImageIO.read(new File(filename));
        }
		catch(Exception e){
			System.out.println("Could not load " + filename);
			e.printStackTrace(System.err);
			System.exit(1);
		}

		return image;
	}

	public int getScrollPos()
	{
		return scrollPos;
	}
}

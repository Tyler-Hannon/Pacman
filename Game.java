import javax.swing.JFrame;
import java.awt.Toolkit;
public class Game extends JFrame
{
	private Model model;
	private Controller controller;
	private View view;
	public static final int WINDOWSIZE = 500;
	
	public Game()
	{
        
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);

		this.setTitle("PACMAN!!!");
		this.setSize(WINDOWSIZE, WINDOWSIZE);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		view.addMouseListener(controller);
		this.addKeyListener(controller);
	}

	public void run()
	{
		try
        {
        	String filePath = "pacman_beginning.wav";
			Sound sound = new Sound(filePath);
        	sound.play();
	    }
        catch (Exception e){
        	e.printStackTrace();
		}
		while (true){
			controller.update();
			model.update();
			view.repaint(); // This will indirectly call View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 40 milliseconds
			try
			{
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}
}
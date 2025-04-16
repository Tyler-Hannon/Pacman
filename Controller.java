import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller implements ActionListener, MouseListener, KeyListener
{
	private View view;
	private Model model;
	private boolean keyUp;
	private boolean keyDown;
	private boolean keyQ;
	private boolean keyEsc;
	private boolean addWall;
	private boolean addFruit;
	private boolean addghost;
	private boolean addPellet;
	private boolean editMode;
	private boolean keyRight;
	private boolean keyLeft;


	public Controller(Model m)
	{
		model = m;
		addWall = false;
		editMode = false;
		addFruit = false;
		addghost = false;
		addPellet = false;

		//Load map at beginning
		Json mapload = Json.load("map.json");
		model.unmarshal(mapload);
		System.out.println("Your map has loaded");

	}

	public void actionPerformed(ActionEvent e)
	{
		//view.removeButton();
	}

	void setView(View v)
	{
		view = v;
	}

	public void setEverythingFalse()
	{
		addFruit = false;
		addWall = false;
		addghost = false;
		addPellet = false;
	}

	public void mousePressed(MouseEvent e)
	{
		if(editMode && addWall && (addFruit == false) && (addghost == false) && (addPellet == false)){
			model.startWall(e.getX(), e.getY() + view.getScrollPos());
		}
		else if (editMode && (addWall == false) && (addFruit == false) && (addghost == false) && (addPellet == false)){
			model.removeWall(e.getX(),e.getY() + view.getScrollPos());
		}
		else if(editMode && addFruit && (addWall == false) && (addghost == false) && (addPellet == false)){
			model.createFruit(e.getX(),e.getY() + view.getScrollPos());
		}
		else if(editMode && addghost && (addWall == false) && (addFruit == false) && (addPellet == false)){
			model.createGhost(e.getX(), e.getY() + view.getScrollPos());
		}
		else if(editMode && addPellet && (addWall == false) && (addFruit == false) && (addghost == false)){
			model.createPellet(e.getX(), e.getY() + view.getScrollPos());
		}

	}

	public void mouseReleased(MouseEvent e) {
		if(editMode && addWall){
			model.stopWall(e.getX(), e.getY() + view.getScrollPos());
		}
	}
	
	public void mouseEntered(MouseEvent e) {	}
	public void mouseExited(MouseEvent e) {		}
	public void mouseClicked(MouseEvent e) {	}

	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode()){
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_Q: keyQ = true; break;
			case KeyEvent.VK_ESCAPE: keyEsc = true; break;
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode()){
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_Q: keyQ = false; break;
			case KeyEvent.VK_ESCAPE: keyEsc = false; break;
			case KeyEvent.VK_E: editMode = !editMode; break;
			case KeyEvent.VK_RIGHT: keyRight = false;
			case KeyEvent.VK_LEFT: keyLeft = false;
		}

		char key = Character.toLowerCase(e.getKeyChar());

		//save map
		if (key == 's'){
			if (editMode == false){
				return;
			}
			Json mapsave = model.marshal();
			mapsave.save("map.json");
			System.out.println("Your map has been saved.");
		}

		//load map
		if (key == 'l'){
			if (editMode == false){
				return;
			}
			Json mapload = Json.load("map.json");
			model.unmarshal(mapload);
			System.out.println("Your map has loaded");
		}

		//clear map
		if (key == 'c'){
			if (editMode == false){
				return;
			}
			model.clearScreen();
		}

		//add walls
		if (key == 'a'){
			if (editMode == false){
				return;
			}
			setEverythingFalse();
			addWall = true;
		}

		//remove walls
		if (key == 'r'){
			if (editMode == false){
				return;
			}
			setEverythingFalse();
			addWall = false;
		}

		//add fruit
		if (key == 'f'){
			if (editMode == false){
				return;
			}
			setEverythingFalse();
			addFruit = true;
		}

		//add ghost
		if (key == 'g'){
			if (editMode == false){
				return;
			}
			setEverythingFalse();
			addghost = true;
		}

		//add pellet
		if (key == 'p'){
			if (editMode == false){
				return;
			}
			setEverythingFalse();
			addPellet = true;
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

	public void update()
	{
		model.savePrev();
		if(keyQ || keyEsc){
			System.exit(0);
		}
		if(keyUp){
			model.pacman1.moveUp();
		}
		else if(keyDown){
			model.pacman1.moveDown();
		}
		else if(keyRight){
			model.pacman1.moveRight();
		}
		else if(keyLeft){
			model.pacman1.moveLeft();
		}
	}
}

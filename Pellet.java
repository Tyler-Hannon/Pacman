import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Pellet extends Sprite{
    private BufferedImage pellet_image;
    private boolean isValid;

    public Pellet(int x, int y)
    {
        this.x = x;
        this.y = y;
        w = 15;;
        h = 15;
        isValid = true;

        if (pellet_image == null){
            pellet_image = View.loadImage("pellet.png");
        }
    }
      
    public void drawYourself(Graphics g, int scrollPos)
    {
        g.drawImage(pellet_image, x, y - scrollPos, w, h, null);
    }

    public boolean update()
    {
        return isValid;
    }

    Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }

    //Unmarshal pacman object
    public Pellet(Json ob)
    {   
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
        isValid = true;

        if (pellet_image == null){
            pellet_image = View.loadImage("pellet.png");
        }
    }

    @Override
    public boolean isMoving()
    {
        return true;
    }

    public void eatPellet()
    {
        isValid = false;
    }

    public boolean isPellet()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return "Pellet (x,y) = (" + x + ", " + y + ")";
    }
}
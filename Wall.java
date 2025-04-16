import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Wall extends Sprite{
    private static BufferedImage wall_image;

    //Constructor
    Wall(int x, int y, int w, int h)
    {
        if (wall_image == null){
            wall_image = View.loadImage("wall.png");
        }
        setX(x);
        setY(y);
        setW(w);
        setH(h);
    }

    public boolean amIClickingOnYou(int mouseX, int mouseY)
    {
        if((mouseX >= getX()) && (mouseX <= getX() + getW()) && (mouseY >= getY()) && (mouseY <= getY() + getH())){
            return true;
        }
        return false;
    }
    
    //Marshal wall object
    Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", getX());
        ob.add("y", getY());
        ob.add("w", getW());
        ob.add("h", getH());
        return ob;
    }

    //Unmarshal wall object
    Wall(Json ob)
    {
        if (wall_image == null){
            wall_image = View.loadImage("wall.png");
        }
        setH((int)ob.getLong("h"));
        setW((int)ob.getLong("w"));
        setX((int)ob.getLong("x"));
        setY((int)ob.getLong("y"));
    }

    public void drawYourself(Graphics g, int scrollPos)
    {
        g.drawImage(wall_image, getX(), getY() - scrollPos, getW(), getH(), null);
    }

    @Override 
    public String toString()
    {
        return "Wall (x,y) = (" + getX() + ", " + getY() + "), w = " + getW() + ", h = " + getH();
    }

    @Override
    boolean isWall()
    {
        return true;
    }
    
    public boolean update()
    {
        return true;
    }
}

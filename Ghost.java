import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Ghost extends Sprite{
    private BufferedImage ghost_image;
    private boolean isValid;
    private boolean hasBeenCollidedWith;
    private int ghostTimer;
    private Sound sound;

    public Ghost(int x, int y)
    {
        this.x = x;
        this.y = y;
        w = 25;
        h = 25;
        ghostTimer = 50;
        isValid = true;
        hasBeenCollidedWith = false;

        if (ghost_image == null){
            ghost_image = View.loadImage("sue5.png");
        }

        //Eat ghost sound
        try
        {
            String filePath = "pacman_eatghost.wav";
            sound = new Sound(filePath);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void drawYourself(Graphics g, int scrollPos)
    {
        if (hasBeenCollidedWith == false){
            g.drawImage(ghost_image, x, y - scrollPos, w, h, null);
            return;
        }
        if(ghostTimer > 25){
            ghost_image = View.loadImage("ghost2.png");
            g.drawImage(ghost_image, x, y - scrollPos, w, h, null);
        }
        if((ghostTimer <= 25) && (ghostTimer >= 0)){
            ghost_image = View.loadImage("ghost7.png");
            g.drawImage(ghost_image, x, y - scrollPos, w, h, null);
        }
        if(ghostTimer <= 0){
            isValid = false;
        }
        ghostTimer -= 0.1;
    }

    public boolean update()
    {
        return isValid;
    }

    public void startDeathSequence()
    {
        hasBeenCollidedWith = true;
        sound.play();
    }

    public boolean isGhost()
    {
        return true;
    }

    //Marshal ghost object
    Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }

    //Unmarshal ghost object
    Ghost(Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");

        if (ghost_image == null){
            ghost_image = View.loadImage("sue5.png");
        }
        isValid = true;
        hasBeenCollidedWith = false;
        ghostTimer = 50;

        //Eat ghost sound
        try
        {
            String filePath = "pacman_eatghost.wav";
            sound = new Sound(filePath);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String toString()
    {
        return "Ghost (x,y) = (" + x + ", " + y + ")";
    }
}

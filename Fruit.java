import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Fruit extends Sprite{
    private BufferedImage fruit_image;
    private int speed, ydir, xdir, prevX, prevY, imageNum;
    private boolean isValid;
    private Random rand = new Random();
    private Sound sound;

    public Fruit(int x, int y)
    {
        this.x = x;
        this.y = y;
        w = 15;;
        h = 15;
        speed = 3;

        //generate random number to decide which direction fruit moves in
        ydir = rand.nextInt(-1, 1);
        xdir = rand.nextInt(-1, 1);

        isValid = true;

        //generate random number to decide which fruit image to use
        imageNum = rand.nextInt(1, 7);

        if (fruit_image == null){
            fruit_image = View.loadImage("fruit" + imageNum + ".png");
        }

        //sound
        try
        {
            String filePath = "pacman_eatfruit.wav";
            sound = new Sound(filePath);
        }
        catch (Exception e){
            e.printStackTrace();;
        }
    }
      
    public void drawYourself(Graphics g, int scrollPos)
    {
        g.drawImage(fruit_image, x, y - scrollPos, w, h, null);
    }

    public boolean update()
    {
        savePrev();
        y += speed * ydir;
        x += speed * xdir;
        if (x < 0){
            x = 500 * xdir;
        }
        if (x > 500){
            x = 1 * xdir;
        }
        return isValid;
    }

    public void getOutOfWall(int wallX, int wallW, int wallY, int wallH)
    {
        if (((x + w) > wallX) && ((prevX + w) <= wallX)){
            setX(prevX);
        }
        if ((x < (wallX + wallW)) && (prevX >= (wallX + wallW))){
            setX(prevX);
        }
        if (((y + h) > wallY) && ((prevY + h) <= wallY)){
            setY(prevY);
        }
        if ((y < (wallY + wallH)) && (prevY >= (wallY + wallH))){
            setY(prevY);
        }
        ydir *= -1;
        xdir *= -1;

    }

    Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("h", h);
        ob.add("w", w);
        ob.add("xdir", xdir);
        ob.add("ydir", ydir);
        ob.add("imageNum", imageNum);
        return ob;
    }

    //Unmarshal pacman object
    public Fruit(Json ob)
    {   
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        h = (int)ob.getLong("h");
        w = (int)ob.getLong("w");
        xdir = (int)ob.getLong("xdir");
        ydir = (int)ob.getLong("ydir");
        imageNum = (int)ob.getLong("imageNum");
        isValid = true;
        speed = 3;

        if (fruit_image == null){
            fruit_image = View.loadImage("fruit" + imageNum + ".png");
        }

        //sound
        try
        {
            String filePath = "pacman_eatfruit.wav";
            sound = new Sound(filePath);
        }
        catch (Exception e){
            e.printStackTrace();;
        }
        
    }

    public void savePrev()
    {
        prevX = x;
        prevY = y;
    }

    @Override
    public boolean isMoving()
    {
        return true;
    }

    public void eatFruit()
    {
        sound.play();
        isValid = false;
    }

    public boolean isFruit()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return "Fruit (x,y) = (" + x + ", " + y + ")";
    }
}

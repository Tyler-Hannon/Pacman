import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Pacman extends Sprite{
    private int prevX, prevY;
    private static BufferedImage[] image;
    final int MAX_IMAGES_PER_DIRECTION = 3;
    private int currentImage = 1;
    private int speed;
    private int direction;
    private Sound sound;


    public Pacman()
    {
        this(0,0,0,0);
        direction = 0;
        speed = 5;
    }
    
    //Constructor
    public Pacman(int x, int y, int w, int h)
    {
        image = new BufferedImage[MAX_IMAGES_PER_DIRECTION * 4 + 1];
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;


        if (image[0] == null){
            image[0] = View.loadImage("pacman1.png");
            for (int i = 1; i < MAX_IMAGES_PER_DIRECTION * 4 + 1; i++){
                image[i] = View.loadImage("pacman" + i + ".png");
            }
        }

        //wakka wakka sound
        try
        {
            String filePath = "wakka.wav";
            sound = new Sound(filePath);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    //Marshal pacman object
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
    public Pacman(Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");

        direction = 0;
        speed = 5;

        image = new BufferedImage[MAX_IMAGES_PER_DIRECTION * 4 + 1];
        if (image[0] == null){
            image[0] = View.loadImage("pacman1.png");
            for (int i = 1; i < MAX_IMAGES_PER_DIRECTION * 4 + 1; i++){
                image[i] = View.loadImage("pacman" + i + ".png");
            }
        }

        //wakka wakka sound
        try
        {
        String filePath = "wakka.wav";
        sound = new Sound(filePath);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void drawYourself(Graphics g, int scrollY)
    {
        g.drawImage(image[currentImage + direction * MAX_IMAGES_PER_DIRECTION], x, y - scrollY, w, h, null);
    }

    public boolean update()
    {
        return true;
    }

    public void updateImageNum(int dir)
    {
        direction = dir;
        currentImage++;
        if(currentImage > MAX_IMAGES_PER_DIRECTION){
            currentImage = 1;
        }
    }

    public void getOutOfWall(int wallX, int wallW, int wallY, int wallH)
    {
        if (((x + w) > wallX) && ((prevX + w) <= wallX)){
            x = prevX;
        }
        if ((x < (wallX + wallW)) && (prevX >= (wallX + wallW))){
            x = prevX;
        }
        if (((y + h) > wallY) && ((prevY + h) <= wallY)){
            y = prevY;
        }
        if ((y < (wallY + wallH)) && (prevY >= (wallY + wallH))){
            y = prevY;
        }
    }

    @Override
    boolean isPacman()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return "Pacman (x,y) = (" + x + ", "+ y + "), w = " + w + ", h = " + h;
    }

    @Override
    public boolean isMoving()
    {
        return true;
    }

    public void moveUp() {
        y = y - speed;
        updateImageNum(1);
        sound.play();
    }

    public void moveLeft() {
        x = x - speed;
        if (x < 0){
            x = 500;
        }
        updateImageNum(0);
        sound.play();
    }

    public void moveDown() {
        y = y + speed;
        updateImageNum(3);
        sound.play();
    }

    public void moveRight() {
        x = x + speed;
        if (x > 500){
            x = 0;
        }
        updateImageNum(2);
        sound.play();
    }

    public void savePrev()
    {
        prevX = x;
        prevY = y;
    }
}

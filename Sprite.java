import java.awt.Graphics;

abstract class Sprite{
    protected int x, y, w, h;
    
    Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }

    public boolean doesCollide(Sprite a)
    {
        //not colliding from left
        if ((x + w) < a.x){
            return false;
        }
        //not colliding from right
        if (x > (a.x + a.w)){
            return false;
        }
        //not colliding from top
        if ((y + h) < a.y){
            return false;
        }
        //not colliding from bottom
        if (y > (a.y + a.h)){
            return false;
        }
        return true;
    }

    public boolean isMoving()
    {
        return false;
    }

    boolean isWall()
    {
        return false;
    }

    boolean isPacman()
    {
        return false;
    }

    boolean isFruit()
    {
        return false;
    }

    boolean isGhost()
    {
        return false;
    }

    boolean isPellet()
    {
        return false;
    }

    public abstract void drawYourself(Graphics g, int scrollPos);

    public abstract boolean update();

    public void setX(int X)
    {
        x = X;
    }

    public void setY(int Y)
    {
        y = Y;
    }

    public void setW(int W)
    {
        w = W;
    }

    public void setH(int H)
    {
        h = H;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getW()
    {
        return w;
    }

    public int getH()
    {
        return h;
    }
}

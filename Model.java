import java.util.ArrayList;
import java.util.Iterator;

public class Model{
    ArrayList<Sprite> sprites;
    ArrayList<Sprite> addSprites;
    private int pacWallstartX;
    private int pacWallstartY;
    public Pacman pacman1;

    Model()
    {
        sprites = new ArrayList<Sprite>();
        addSprites = new ArrayList<Sprite>();
        pacman1 = new Pacman();
    }

    Json marshal()
    {
        Json ob = Json.newObject();
        Json tmpListWalls = Json.newList();
        ob.add("walls", tmpListWalls);
        Json tmpListFruit = Json.newList();
        ob.add("fruit", tmpListFruit);
        Json tmpListGhost = Json.newList();
        ob.add("ghost", tmpListGhost);
        Json tmpListPacman = Json.newList();
        ob.add("pacman", tmpListPacman);
        Json tmpListPellet = Json.newList();
        ob.add("pellet", tmpListPellet);
        for(int i = 0; i < sprites.size(); i++){
            if(sprites.get(i).isWall()){
                tmpListWalls.add(sprites.get(i).marshal());
            }
            if(sprites.get(i).isFruit()){
                tmpListFruit.add(sprites.get(i).marshal());
            }
            if(sprites.get(i).isGhost()){
                tmpListGhost.add(sprites.get(i).marshal());
            }
            if(sprites.get(i).isPacman()){
                tmpListPacman.add(sprites.get(i).marshal());
            }
            if(sprites.get(i).isPellet()){
                tmpListPellet.add(sprites.get(i).marshal());
            }
        }
        return ob;
    }
    
    // Unmarshaling method
    public void unmarshal(Json ob)
    {
        sprites.clear();
        Json tmpList = ob.get("walls");
        for(int i = 0; i < tmpList.size(); i++){
            Wall w = new Wall(tmpList.get(i));
            sprites.add(w);
        }
        tmpList = ob.get("fruit");
        for(int i = 0; i < tmpList.size(); i++){
            Fruit f = new Fruit(tmpList.get(i));
            sprites.add(f);
        }
        tmpList = ob.get("ghost");
        for(int i = 0; i < tmpList.size(); i++){
            Ghost g = new Ghost(tmpList.get(i));
            sprites.add(g);
        }
        tmpList = ob.get("pacman");
        for(int i = 0; i < tmpList.size(); i++){
            Pacman p = new Pacman(tmpList.get(i));
            if (pacman1 != p){
                pacman1 = p;
            }
            sprites.add(pacman1);
        }
        tmpList = ob.get("pellet");
        for(int i = 0; i < tmpList.size(); i++){
            Pellet p = new Pellet(tmpList.get(i));
            sprites.add(p);
        }
    }

    public void update()
    {
        sprites.addAll(addSprites);
        addSprites.clear();
        Iterator<Sprite> iter1 = sprites.iterator();
        while (iter1.hasNext()){
            Sprite sprite1 = iter1.next();
            if (!sprite1.update()){
                iter1.remove();
                continue;
            }
            if (!sprite1.isMoving()){
                continue;
            }
            //collision detection
            Iterator<Sprite> iter2 = sprites.iterator();
            while (iter2.hasNext()){
                Sprite sprite2 = iter2.next();
                if ((sprite1 != sprite2) && (sprite1.doesCollide(sprite2))){
                    //check if pacman is eating fruit
                    if (sprite1.isPacman() && sprite2.isFruit()){
                        ((Fruit)sprite2).eatFruit();
                    }
                    //check if pacman is colliding wth wall
                    if (sprite1.isPacman() && sprite2.isWall()){
                        pacman1.getOutOfWall(sprite2.getX(), sprite2.getW(), sprite2.getY(), sprite2.getH());
                    }
                    //check if fruit is colliding with wall
                    if (sprite1.isFruit() && sprite2.isWall()){
                        ((Fruit)sprite1).getOutOfWall(sprite2.getX(), sprite2.getW(), sprite2.getY(), sprite2.getH());
                    }
                    //check if pacman is eating ghost
                    if (sprite1.isPacman() && sprite2.isGhost()){
                        ((Ghost)sprite2).startDeathSequence();
                    }
                    //check if pacmanis eating pellet
                    if (sprite1.isPacman() && sprite2.isPellet()){
                        ((Pellet)sprite2).eatPellet();
                    }
                }
            }
        }
    }

    public void createFruit(int x, int y)
    {
        Fruit f = new Fruit(x, y);
        addSprites.add(f);
    }

    public void createGhost(int x, int y)
    {
        Ghost g = new Ghost(x, y);
        addSprites.add(g);
    }

    public void createPellet(int x, int y)
    {
        Pellet p = new Pellet(x, y);
        addSprites.add(p);
        System.out.println(p);
    }

    public void startWall(int startX, int startY)
    {
        pacWallstartX = startX;
        pacWallstartY = startY;
    }

    public void stopWall(int stopX, int stopY)
    {
        int w = Math.abs(stopX - pacWallstartX);
        int h = Math.abs(stopY - pacWallstartY);
        if(pacWallstartX > stopX){
            pacWallstartX = stopX;
        }
        if(pacWallstartY > stopY){
            pacWallstartY = stopY;
        }
        addSprites.add(new Wall(pacWallstartX, pacWallstartY, w, h));
    }

    public void removeWall(int mouseX, int mouseY)
    {
        for (int i = 0; i < sprites.size(); i++){
            if (sprites.get(i).isWall()){
                if (((Wall)sprites.get(i)).amIClickingOnYou(mouseX, mouseY)){
                    sprites.remove(i);
                    break;
                }
            }

        }
    }

    public void savePrev()
    {
       pacman1.savePrev();
    }

    public int getPacY()
    {
        return pacman1.getY();
    }

    public void clearScreen()
    {
        sprites.clear();
    }
}

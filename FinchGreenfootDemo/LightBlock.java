import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A block in the world that gradually gets whiter as the 
 * finch's surroundings get lighter.
 * 
 * This class is based on the original 2010 LightBlock by Michael Berry
 */
public class LightBlock  extends FinchActor
{
    private GreenfootImage image;

    @Override
    public void addedToWorld(World world) {
        image = new GreenfootImage(50,50);
        setImage(image);
    }
    
    /**
     * Act - do whatever the LightBlock wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void setBlock(double level)
    {
        int val = (int) ((level/100.0) * 255.0);
        image.setColor(new greenfoot.Color(val,val,val));
        image.fillRect(0,0,image.getWidth(),image.getHeight());
    }    
}

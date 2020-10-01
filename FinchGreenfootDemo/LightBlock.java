import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A block in the world that gradually gets whiter as the 
 * finch's surroundings get lighter.
 * 
 * @author Michael Berry
 * @version 01/12/10
 */
public class LightBlock  extends Actor
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
    public void setBlock(int which) 
    {
        if(which == 0) {
            int val = (int)(GreenFinch.get().getLeftLightLevel()*7);
            image.setColor(new greenfoot.Color(val,val,val));
            image.fillRect(0,0,image.getWidth(),image.getHeight());
        }
        else if(which == 1) {
            int val = (int)(GreenFinch.get().getLightLevel()*7);
            image.setColor(new greenfoot.Color(val,val,val));
            image.fillRect(0,0,image.getWidth(),image.getHeight());
        }
        else if(which == 2) {
            int val = (int)(GreenFinch.get().getRightLightLevel()*7);
            image.setColor(new greenfoot.Color(val,val,val));
            image.fillRect(0,0,image.getWidth(),image.getHeight());
        }
    }    
}

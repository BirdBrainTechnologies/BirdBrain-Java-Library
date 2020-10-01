import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A label that displays some text. Nothing fancy :-)
 * 
 * @author Michael Berry
 * @version 01/12/10
 */
public class Label  extends Actor
{
    private String text;
    
    public Label(String text) {
        this.text = text;
    }
    public void addedToWorld(World world) {
        GreenfootImage image = new GreenfootImage(text.length()*7,30);
        image.drawString(text,10,10);
        setImage(image);
    }
}

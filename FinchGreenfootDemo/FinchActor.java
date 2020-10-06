import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Superclass for all classes that interact with the finch.
 */
public class FinchActor extends Actor
{
    private Finch finch;
    
    protected Finch getFinch(){
        if (finch == null) {
            finch = getWorldOfType(FinchWorld.class).getFinch();
        }
        return finch;
    } 
}

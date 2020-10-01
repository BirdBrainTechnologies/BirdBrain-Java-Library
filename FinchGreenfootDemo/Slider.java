import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A slider to allow a user to select a number in a given range.
 * 
 * Create a Slider by calling <code>new {@link Slider()}</code> (for the default size)
 * or <code>new {@link Slider(int, int)}</code> (for a custom size).
 * Set teh maximum value that the user can select by calling
 * {@link setMaximumValue(int)}, set the initial value by calling
 * {@link setValue(int)} and add it to the world by calling the
 * addObject(Actor, int, int) method on the World.
 * 
 * You can also set how the slider should display by calling the other methods.
 * This <i>should</i> be done before adding the slider to the world, but does not need to be
 * 
 * @author mBread
 * @version 29-01-2008
 */
public class Slider extends Actor
{
    //The background & foregreound images. See updateImage()
    private GreenfootImage background, foreground;
    
    //The height, width of the foreground and height of the foreground
    private int height, fgWidth = 9, fgHeight = 10;
    //The maximum value, width in pixels, width of the selectable area, width of the value label,
    //the padding either side of the selectable area, the x-coordinate of the selectable area
    //and the x-coordinate of the right-hand side of the selectable area
    private int maxValue, actualWidth, valueWidth, valueLabelWidth, padding, valueX, valueRight;
    //The number of major and minor sections
    private int majorSections, minorSections;
    //Flags meaning: display the value label, show the value as a percentage, re-draw the images
    private boolean showValue = false, showPercentage = true, imagesInvalid = true;
    //How wide each number is in the selectable value
    private float pixelsPerValue;
    //The current value is
    private int value;
    
    /**
     * Create a new slider with the default size. It will be 200 pixels wide by
     * 20 pixels high.
     */
    public Slider(){
        this(200, 20);
    }
    
    /**
     * Create a new slider.
     * 
     * @param width The width of the slider in pixels
     * @param height The height of th slider in pixels
     */
    public Slider(int width, int height){
        majorSections = 2;
        minorSections = 2;
        
        maxValue = 100;
        actualWidth = width;
        this.height = height;
        fgHeight = height/2;
        fgWidth = fgHeight;
        if(fgWidth % 2 == 0){
            fgWidth--;
        }
        
        setImage( new GreenfootImage(actualWidth, height) );
    }
    
    /**
     * This method is called by the World class when the slider is added to the
     * world. You will not need to call this method.
     * 
     * @param world The world
     */
    public void addedToWorld(World world){
        createImages();
        updateImage();
        imagesInvalid = false;
        valueX = getX()*getWorld().getCellSize() - actualWidth/2 + padding;
        valueRight = valueX + valueWidth;
    }
    
    /**
     * This method is called by Greenfoot, and updates the slider if the user
     * has dragged the slider.
     */
    public void act(){
        if(Greenfoot.mouseDragged(this) || Greenfoot.mousePressed(this)){
            MouseInfo mouseInfo = Greenfoot.getMouseInfo();
            int x = Math.max(valueX, mouseInfo.getX()*getWorld().getCellSize());
            x -= valueX;
            x = Math.min(x, valueRight);
            setValue(posToValue(x));
            imagesInvalid = true;
        }
        if(imagesInvalid){
            createImages();
            updateImage();
            imagesInvalid = false;
        }
    }
    
    /**
     * Set the current selected value of the slider. The display will change
     * to represent this new value.
     * 
     * @param value The new value
     */
    public void setValue(int value){
        if(value < 0){
            value = 0;
        }if(value > maxValue){
            value = maxValue;
        }
        this.value = value;
        imagesInvalid = true;
    }
    
    /**
     * Get the current value.
     * 
     * @return The current selected value
     */
    public int getValue(){
        return value;
    }
    
    /**
     * Set whether the value should be displayed next to the slider.
     * 
     * If this method is called with false as the parameter then it will
     * also set {@link shoePercentage(boolean)} will also be set to false.
     * 
     * @see showPercentage(boolean)
     * @param show  true if the value should be displayed as text,
     * false if it should not be
     */
    public void showValue(boolean show){
        if(showValue != show){
            imagesInvalid = true;
        }
        showValue = show;
        if(!show){
            if(showPercentage) imagesInvalid = true;
            showPercentage = false;
        }
    }
    
    /**
     * Check whether the slider is set to display the value.
     * 
     * @see showValue(boolean)
     * @see isShowingPercentage()
     * @return true if the value is being displayed, false if it is not
     */
    public boolean isShowingValue(){
        return showValue;
    }
    
    /**
     * Set whether the value will be displayed next to the slider as a percentage.
     * 
     * This method will take precedence over {@link showValue(boolean)}. If
     * both are set to true, the percentage is shown. If both are true and
     * showPercentage(false) is called then the value will be shown. If both
     * are true and showValue(false) is called then neither the value nor
     * percentage is shown.
     * 
     * @see showValue(boolean)
     * @see isShowingPercentage()
     * @param show true if the value should be displayed as a percentage of
     * the maximum value, false if it should not.
     */
    public void showPercentage(boolean show){
        if(showPercentage != show){
            imagesInvalid = true;
        }
        showPercentage = show;
    }
    
    /**
     * Check whether the slider is set to display the value as a percentage.
     * 
     * @see showPercentage(boolean)
     * @see isShowingValue()
     * @return true if the percentage is being displayed, false if it is not
     */
    public boolean isShowingPercentage(){
        return showPercentage;
    }
    
    /**
     * Set the number of major sections to be marked. Set the number of sections to
     * zero to not display any major markers.
     * 
     * There will be 1 more marker than sections as there is a
     * marker at both ends as well as between each section
     * 
     * @see setMinorSections(int)
     * @param sections The number of major sections to display
     */
    public void setMajorSections(int sections){
        majorSections = sections;
        imagesInvalid = true;
    }
    
    /**
     * Get the number of major sections that are marked.
     * 
     * @see setMajorSections(int)
     * @return The number of major sections that are marked
     */
    public int getMajorSections(){
        return majorSections;
    }
    
    /**
     * Set the number of minor sections to be marked per major secton. Set this
     * to zero or 1 to not display any minor markers.
     * 
     * There will be one less minor marker (per major section) than the number
     * of minor sections as there is not a marker at each end due to the major
     * markers being there.
     * 
     * @see setMajorSections(int)
     * @param sections The number of minor sections to mark per major section
     */
    public void setMinorSections(int sections){
        minorSections = sections;
        imagesInvalid = true;
    }
    
    /**
     * Get the number of minor sections that are marked.
     * 
     * @see setMinorSections(int)
     * @return The number of minor sections that are marked
     */
    public int getMinorSections(){
        return minorSections;
    }
    
    /**
     * Set the maximum value that the user can select.
     * 
     * @param value The new maximum value
     */
    public void setMaximumValue(int value){
        if(value <= 0) return;
        maxValue = value;
        pixelsPerValue = valueWidth/(float)maxValue;
        setValue(value);
    }
    
    /**
     * Get the current maximum value which the user can select
     * 
     * @return The maximum value
     */
    public int getMaximumValue(){
        return maxValue;
    }
    
    /**
     * Convert a pixel position (relitive to the left of the selectable area)
     * to a selected value.
     */
    private int posToValue(int x){
        int v = Math.round(x / pixelsPerValue);
        return v;
    }
    
    /**
     * Convert a selected value to a pixel position
     */
    private int valueToPos(int v){
        int p = Math.round(v * pixelsPerValue);
        return p;
    }
    
    /**
     * Create the background & foreground images
     */
    private void createImages(){
        if(showPercentage){
            valueLabelWidth = 4*10;
        }else if(showValue){
            valueLabelWidth = (int)(Math.floor(Math.log10(maxValue))+1)*10;
        }else{
            valueLabelWidth = 0;
        }
        valueWidth = actualWidth - fgWidth - valueLabelWidth;
        valueWidth -= valueWidth%2;
        padding = fgWidth/2;
        pixelsPerValue = valueWidth/(float)maxValue;
        assert padding >= 0;
        
        background = new GreenfootImage(actualWidth, height);
        foreground = new GreenfootImage(fgWidth, height);
        background.drawLine(padding, height/2, padding+valueWidth, height/2);
        if(majorSections > 0){
            float pixelsPerMarker = valueWidth/(float)majorSections;
            float pixelsPerMinor = 0;
            if(minorSections > 0) pixelsPerMinor = pixelsPerMarker/(float)minorSections;
            for(int i=0; i<=majorSections; i++){
                int x = Math.round(i*pixelsPerMarker + padding);
                background.drawLine(x, height, x, 2*height/3);
                for(int m=1; m<minorSections && i<majorSections; m++){
                    int mx = Math.round(x + m*pixelsPerMinor);
                    background.drawLine(mx, 5*height/6, mx, 4*height/6);
                }
            }
        }
        foreground.fillRect(0, 0, fgWidth, fgHeight);
        int pointHeight = Math.min(fgWidth/2, height-fgHeight);
        int[] pointerXPoints = {0, fgWidth/2, fgWidth-1};
        int[] pointerYPoints = {fgHeight, pointHeight+fgHeight, fgHeight};
        foreground.fillPolygon(pointerXPoints, pointerYPoints, 3);
        if(foreground.getWidth()%2 == 0){
            foreground.scale(foreground.getWidth()+1, foreground.getHeight());
        }
    }
    
    /**
     * Update the position of the foreground image, and the text of the
     * value label.
     */
    private void updateImage(){
        GreenfootImage image = new GreenfootImage(background);
        image.drawImage(foreground, valueToPos(value), 0);
        if(showPercentage){
            image.drawString((value*100)/maxValue+"%", valueWidth+padding*2, image.getHeight());
        }else if(showValue){
            image.drawString(String.valueOf(value), valueWidth+padding*2, image.getHeight());
        }
        setImage(image);
    }
}

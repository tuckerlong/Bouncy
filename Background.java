import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
/**
 * Write a description of class Background here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Background extends Actor
{
    int mod = 0;
    boolean flip;
    public void addedToWorld(World world) {
        setImage(new GreenfootImage(600,400));    
    }
    
    public void act() 
    {
        getImage().clear();
        
        
        
        mod+= 2;
        if(mod > 1200)
            mod = 0;
        for(int x = -1200; x < 1200; x+=25) {
            flip = !flip;
            for(int i = 0; i < 25; i++) {
                if(flip)
                    getImage().setColor(new Color(Color.HSBtoRGB(0.5f,1.0f,0.95f)));
                else
                    getImage().setColor(new Color(Color.HSBtoRGB(0.5f,1.0f,0.85f)));
                getImage().drawLine(0,i + x + mod,i + x + mod,0);
            }
            
        }
        
        getImage().setColor(new Color(Color.HSBtoRGB(0.5f,1.0f,0.85f)));
        getImage().fillRect(0,0,10,400);
        getImage().fillRect(590,0,10,400);
        getImage().fillRect(0,0,600,10);
        getImage().fillRect(0,390,600,10);
    }    
}

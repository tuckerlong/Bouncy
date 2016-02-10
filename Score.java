import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.awt.*;
import java.io.*;

/**
 * Write a description of class Text here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Score extends Actor
{
    private String text;
    private Font font;
    int hgt = 100, adv = 200, speed = 15, score = 0;
    boolean remove = false, fadeIn = false, fade = false;
    
    public Score() {
        text = "Score: " + score;   
        
        try {
            String path = Score.class.getProtectionDomain().getCodeSource().getLocation().getPath();  
            InputStream is = Score.class.getResourceAsStream("resources/Test.ttf");    
            font=Font.createFont(Font.TRUETYPE_FONT,is); 
            
            getImage().setFont(font);
            getImage().setFont(getImage().getFont().deriveFont(125.f));
        } catch (IOException e) {
            System.out.println("probs");
        } catch (FontFormatException e) {
            System.out.println("probs 2");
        }
    }
    
    protected void addedToWorld(World world) {
        text = "" + score; 
        FontMetrics metrics = getImage().getAwtImage().getGraphics().getFontMetrics(getImage().getFont());
        hgt = metrics.getHeight();
        adv = metrics.stringWidth(text);
        
        setImage(new GreenfootImage(adv, hgt));
        getImage().setFont(font);
        getImage().setFont(getImage().getFont().deriveFont(125.f));
        getImage().drawString(text, 0, getImage().getHeight()* 3/4);
        
        if(fadeIn)
            getImage().setTransparency(0);
        else
            getImage().setTransparency(200);
    }
    
    public void act() {
        if(fadeIn) {
            if(getImage().getTransparency() + 2 < 200)
                getImage().setTransparency(getImage().getTransparency() + 2);
            else {
                getImage().setTransparency(200);
                fadeIn = false;
            }
        }
        
        if(fade) {
            if(getImage().getTransparency() - 10 <= 0) {
                getWorld().removeObject(this);
            } else {
                getImage().setTransparency(getImage().getTransparency() - 10);
            } 
        }
    }
    
    public void update(int points) 
    {
        score += points;
        
        text = "" + score; 
        FontMetrics metrics = getImage().getAwtImage().getGraphics().getFontMetrics(getImage().getFont());
        hgt = metrics.getHeight();
        adv = metrics.stringWidth(text);
        
        setImage(new GreenfootImage(adv, hgt));
        getImage().setFont(font);
        getImage().setFont(getImage().getFont().deriveFont(125.f));
        getImage().drawString(text, 0, getImage().getHeight()* 3/4);
        getImage().setTransparency(200);
    }    
}


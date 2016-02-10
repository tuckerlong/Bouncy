import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
/**
 * Write a description of class Text here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Text extends Actor
{
    private String text;
    private Font font;
    int hgt = 100, adv = 200, fadeSpeed = 15;
    
    public boolean bounce = false;
    public int dx, dy, sy;
    private boolean fade = true, fadeIn = true;
    
    private float size = 75.f;
    
    Random rndm = new Random();
    
    public Text(String text) {
        this.text = text;  
        fade = false;
        
        try {
            String path = Text.class.getProtectionDomain().getCodeSource().getLocation().getPath();  
            InputStream is = Text.class.getResourceAsStream("resources/Test.ttf");    
            font=Font.createFont(Font.TRUETYPE_FONT,is); 
            

            getImage().setFont(font);
            getImage().setFont(getImage().getFont().deriveFont(size));
            FontMetrics metrics = getImage().getAwtImage().getGraphics().getFontMetrics(getImage().getFont());
            hgt = metrics.getHeight();
            adv = metrics.stringWidth(text);
        } catch (IOException e) {
            System.out.println("probs");
        } catch (FontFormatException e) {
            System.out.println("probs 2");
        }
    }
    
    public Text(String text, float size) {
        this.text = text;  
        fade = false;
        this.size = size;
        
        try {
            String path = Text.class.getProtectionDomain().getCodeSource().getLocation().getPath();  
            InputStream is = Text.class.getResourceAsStream("resources/Test.ttf");    
            font=Font.createFont(Font.TRUETYPE_FONT,is); 
            

            getImage().setFont(font);
            getImage().setFont(getImage().getFont().deriveFont(size));
            FontMetrics metrics = getImage().getAwtImage().getGraphics().getFontMetrics(getImage().getFont());
            hgt = metrics.getHeight();
            adv = metrics.stringWidth(text);
        } catch (IOException e) {
            System.out.println("probs");
        } catch (FontFormatException e) {
            System.out.println("probs 2");
        }
        
        getImage().setTransparency(0);
        fadeIn = true;
    }
    
    protected void addedToWorld(World world) {
        setImage(new GreenfootImage(adv, hgt));
        getImage().setFont(font);
        getImage().setFont(getImage().getFont().deriveFont(size));
        getImage().drawString(text, 0, getImage().getHeight()* 3/4);
        
        getImage().setColor(new Color(rndm.nextInt(255),rndm.nextInt(255),rndm.nextInt(255)));
        getImage().setTransparency(0);
        /*Color c = new Color(rndm.nextInt(255),rndm.nextInt(255),rndm.nextInt(255));
        BufferedImage b = getImage().getAwtImage();
        for(int h = 0; h < getImage().getHeight(); h++) {
            for(int w = 0; w < getImage().getWidth(); w++) {
                if(b.getRGB(w,h) == Color.WHITE.getRGB()) {
                    b.setRGB(w,h,c.getRGB());
                }
            }
        }*/
        //getImage().drawRect(0,0,getImage().getWidth() - 1, getImage().getHeight() - 1);
        sy = dy - 100;
    }
    
    boolean bup = false;
    double ymod = 0;
    int bcount = 0;
    public void act() 
    {
        if(fadeIn) {
            if(getImage().getTransparency() + 2 < 200)
                getImage().setTransparency(getImage().getTransparency() + 2);
            else {
                getImage().setTransparency(200);
                fadeIn = false;
            }
        }
        
        if(this.bounce) {
            if(getY() < dy && !bup && bcount < 3) {
                ymod += 0.5;  
                if(ymod > 10.5) ymod = 10.5;
            } else if(getY() >= dy && !bup) {
                bcount++;
                ymod *= -0.8;
            } else {
                bounce = false;
            }
            
            setLocation(getX(), getY() + (int)ymod);
        }
        
        if(Greenfoot.mouseClicked(this) && text.equalsIgnoreCase("Credits")) {
            ((Screen)getWorld()).makeCredits();
        }
        
        if(this.fade) {
            if(getImage().getTransparency() - fadeSpeed <= 0) {
                getWorld().removeObject(this);
            } else {
                getImage().setTransparency(getImage().getTransparency() - fadeSpeed);
            }  
        }
    }    
    
    public void fade() {
        this.fade = true;
    }
    
    public void fadeAtSpeed(int newSpeed) {
        this.fade = true;
        fadeSpeed = newSpeed;
    }
}

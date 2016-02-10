import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
import java.util.*;
import java.io.*;
/**
 * Write a description of class LevelIcon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LevelIcon extends Actor
{
    boolean animate = true, flip = false, remove = false, fall = false;
    double x = 1, y = 1;
    int width = 39, height = 39;
    double percent = 1.3, speed = 11.0;
    Color c;
    
    int hgt = 1, adv = 1, level, stars = 0;
    
    private Font font;
    
    static int test = 0;
    
    public LevelIcon(int level, int stars) {
        this.level = level;
        this.stars = stars;
        try {
            String path = LevelIcon.class.getProtectionDomain().getCodeSource().getLocation().getPath();  
            InputStream is = LevelIcon.class.getResourceAsStream("resources/Test.ttf");    
            font=Font.createFont(Font.TRUETYPE_FONT,is); 
            
            getImage().setFont(font);
            getImage().setFont(getImage().getFont().deriveFont(55.f));
            FontMetrics metrics = getImage().getAwtImage().getGraphics().getFontMetrics(getImage().getFont());
            hgt = metrics.getHeight();
            adv = metrics.stringWidth("" + level);
        } catch (IOException e) {
            System.out.println("probs");
        } catch (FontFormatException e) {
            System.out.println("probs 2");
        }
        test++;
    }
    
    protected void addedToWorld(World world) {
        Random r = new Random();
        c = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
        float[] hsbvals = new float[3];
        c.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbvals);
        c = new Color(Color.HSBtoRGB(test/12.0f,1.0f, 1.0f));
    }
    
    float fsize = 1.0f;
    int delayCount = 0, delay = 0;
    double ymod = 0;
    public void act() {
        if(animate && !fall) {
            setImage(new GreenfootImage((int)x,(int)y));
            Graphics g = getImage().getAwtImage().getGraphics();
            g.setColor(java.awt.Color.BLACK);
            g.fillOval(0, 0, getImage().getWidth() - 1, getImage().getHeight() - 1);
            
            g.setColor(c);
            g.fillOval(2, 2, getImage().getWidth() - 5, getImage().getHeight() - 5);
            
            getImage().setFont(font);
            getImage().setFont(getImage().getFont().deriveFont(fsize));
            getImage().drawString("" + level, getImage().getWidth()*1/2 - adv/3, getImage().getHeight()* 3/4);
            
            fsize = (float)y;
            //g.drawRoundRect(1, 1, getImage().getWidth() - 3, getImage().getHeight() - 3, 4, 4);
            
            /*g.setColor(c);
            g.fillRoundRect(2, 2, getImage().getWidth() - 4, getImage().getHeight() - 4, 0, 0);
            
            g.setColor(c.brighter());
            g.fillRoundRect(2, 2, getImage().getWidth() - 4, getImage().getHeight()/5, 0, 0);*/
            
            if(!flip) {
                x += 20.0/speed;
                y += 20.0/speed;
                
                if(x >= width * percent && y >= height * percent)
                    flip = true;
            } else {
                x -= 20.0/speed;
                y -= 20.0/speed;
                
                if(x < width && y < height) {
                    animate = false;
                    if(stars == 3) {
                        getWorld().addObject(new Star(true), getX(), getY() - 20);
                        getWorld().addObject(new Star(true), getX() - 15, getY() - 15);
                        getWorld().addObject(new Star(true), getX() + 15, getY() - 15);
                    }
                    
                    if(stars == 2) {
                        getWorld().addObject(new Star(true), getX() - 12, getY() - 17);
                        getWorld().addObject(new Star(true), getX() + 12, getY() - 17);
                    }
                    
                    if(stars == 1) {
                        getWorld().addObject(new Star(true), getX(), getY() - 20);
                    }
                }
            }
        }
        
        if(Greenfoot.mouseClicked(this)) {
            ((Screen)getWorld()).makeLevel(level);
        }
        
        if(fall) {
            delayCount++;
            if(delayCount >= delay) {
                ymod += 0.4;
                setLocation(getX(), getY() + (int)ymod);
                if(getImage().getTransparency() - 10 > 0)
                    getImage().setTransparency(getImage().getTransparency() - 10);
            }
        }
        
        if(getY() > 450) {
            getWorld().removeObject(this);
        }
    }  
    
    public void fall() {
        this.fall = true;
        this.delay = Greenfoot.getRandomNumber(25);
    }
}

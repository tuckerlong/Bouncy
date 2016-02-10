import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
import java.util.*;
/**
 * Write a description of class Box here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Box extends Actor
{
    boolean animate = true, flip = false, remove = false;
    double x = 1, y = 1;
    int width = 45, height = 30;
    double percent = 1.3, speed = 11.0;
    Color c;
    int delayc = 0, delay = 0;
    

    protected void addedToWorld(World world) {
        setImage(new GreenfootImage(1,1));
        Random r = new Random();
        c = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
        delay = r.nextInt(35);
    }
    
    public void act() {
        delayc++;
        if(animate && delayc > delay) {
            setImage(new GreenfootImage((int)x,(int)y));
            Graphics g = getImage().getAwtImage().getGraphics();
            g.setColor(java.awt.Color.BLACK);
            g.drawRoundRect(0, 0, getImage().getWidth() - 1, getImage().getHeight() - 1, 6, 6);
            g.drawRoundRect(1, 1, getImage().getWidth() - 3, getImage().getHeight() - 3, 4, 4);
            
            g.setColor(c);
            g.fillRoundRect(2, 2, getImage().getWidth() - 4, getImage().getHeight() - 4, 0, 0);
            
            g.setColor(c.brighter());
            g.fillRoundRect(2, 2, getImage().getWidth() - 4, getImage().getHeight()/5, 0, 0);
            
            if(!flip) {
                x += 30.0/speed;
                y += 20.0/speed;
                
                if(x >= width * percent && y >= height * percent)
                    flip = true;
            } else {
                x -= 30.0/speed;
                y -= 20.0/speed;
                
                if(x < width && y < height)
                    animate = false;
            }
        }
        
        if(remove) {
            if(((Screen)getWorld()).musicOn) {
                GreenfootSound music = new GreenfootSound("sounds/Powerup.wav");
                music.setVolume(75);
                music.play();
            }
            for(int h = 0; h < height/15; h++) {
                for(int i = 0; i < width/15; i++) {
                    getWorld().addObject(new Particle(), getX() - width/2 + 7 + i * 15, getY() + height/2 - 7 - h * 15);
                }
            }
            ((Screen)getWorld()).increaseScore(10);
            getWorld().removeObject(this);
        }
    }
    
    public class Particle extends Actor {
        Color color;
        int dx, dy;
        double xmod, ymod, speed = 3, x, y;
        
        protected void addedToWorld(World world) {
            color = c;
            
            setImage(new GreenfootImage(15,15));
            Graphics g = getImage().getAwtImage().getGraphics();
            g.setColor(java.awt.Color.BLACK);
            g.drawRoundRect(0, 0, getImage().getWidth() - 1, getImage().getHeight() - 1, 6, 6);
            g.drawRoundRect(1, 1, getImage().getWidth() - 3, getImage().getHeight() - 3, 4, 4);
            
            g.setColor(color);
            g.fillRoundRect(2, 2, getImage().getWidth() - 4, getImage().getHeight() - 4, 0, 0);
            
            g.setColor(color.brighter());
            g.fillRoundRect(2, 2, getImage().getWidth() - 4, getImage().getHeight()/5, 0, 0);
            
            Random r = new Random();
            
            dx = r.nextInt(600);
            dy = r.nextInt(400);
            
            double adj = dx - getX();
            double opp = dy - getY();
            double hyp = Math.sqrt(Math.pow(adj,2) + Math.pow(opp, 2));
            double ang = Math.acos(adj/hyp);
            
            if(dy < getY())
                ang *= -1;
            
            xmod = Math.cos(ang) * speed;
            ymod = Math.sin(ang) * speed;
            
            x = getX();
            y = getY();
        }
        
        public void act() {
            x += xmod;
            y += ymod;
            setLocation((int)(x), (int)(y));
            
            if(getImage().getTransparency() - 10 <= 0) {
                /*getWorld().getBackground().setColor(color);
                getWorld().getBackground().fillRect(getX() - 7,getY() - 7, 15, 15);
                
                getWorld().getBackground().setColor(color.brighter());
                getWorld().getBackground().fillRect(getX() - 7,getY() - 7, 15, 3);*/
                
                getWorld().removeObject(this);
            } else {
                getImage().setTransparency(getImage().getTransparency() - 10);
            } 
        }
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
/**
 * Write a description of class Paddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Paddle extends Actor
{
    int mx = 0, my = 0;
    boolean held = false, vert = false, fade = false;
    
    private Ball ball;
    
    public Paddle() {
        
    }
    
    public Paddle(boolean vert) {
        this.vert = vert;
    }
    
    protected void addedToWorld(World world) {
        setImage(new GreenfootImage(50,15));
        
        if(vert)
            setImage(new GreenfootImage(15,50));
            
        Graphics g = getImage().getAwtImage().getGraphics();
        g.setColor(java.awt.Color.BLACK);
        g.drawRoundRect(0, 0, getImage().getWidth() - 1, getImage().getHeight() - 1, 6, 6);
        g.drawRoundRect(1, 1, getImage().getWidth() - 3, getImage().getHeight() - 3, 4, 4);
        
        g.setColor(Color.DARK_GRAY);
        g.fillRoundRect(2, 2, getImage().getWidth() - 4, getImage().getHeight() - 4, 0, 0);
        
        g.setColor(Color.DARK_GRAY.brighter());
        if(vert) {
            g.fillRoundRect(2, 2, getImage().getWidth()/4, getImage().getHeight() - 4, 0, 0);
            if(getX() < 300)
                getImage().mirrorHorizontally();
        } else {
            g.fillRoundRect(2, 2, getImage().getWidth() - 4, getImage().getHeight()/5, 0, 0);
            if(getY() < 200)
                getImage().mirrorVertically();
        }
        
        if(Greenfoot.getMouseInfo() != null) {
            mx = Greenfoot.getMouseInfo().getX();
            my = Greenfoot.getMouseInfo().getY();
        }
    }
    
    public void act() {
        if(Greenfoot.mouseMoved(null) && !fade) {
            int dx = Greenfoot.getMouseInfo().getX();
            int dy = Greenfoot.getMouseInfo().getY();
            
            if(vert) {
                setLocation(getX(), dy);
            } else 
                setLocation(dx, getY());
            
            if(held) {
                ball.setLocation(dx, ball.getY());
            }
            
            mx = dx;
            my = dy;
        }
        
        if(Greenfoot.mouseClicked(null) && held) {
            if(((Screen)getWorld()).checkBoxes()) {
                ball.canMove = true;
                held = false;
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
    
    public void setBall(Ball ball) {
        this.ball = ball;
    }
}

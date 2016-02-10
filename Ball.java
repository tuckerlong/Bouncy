import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.*;
/**
 * Write a description of class Ball here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ball extends Actor
{
    int xspeed = 4;
    int yspeed = 4;
    
    int[] walls = {1,1,1,1};
    
    boolean canMove = false, moveVert = false, fade = false;
    
    public Ball() {
        
    }
    
    public void addedToWorld(World world) {
        canMove = false;
        moveVert = false;
        fade = false;
        
        xspeed = yspeed = 4;
        
        if(getY() < world.getHeight()/2)
            yspeed = xspeed *= -1;
        
        setImage( new GreenfootImage(10,10) );
        getImage().setColor(Color.BLACK);
        getImage().fillOval(0,0,getImage().getWidth() - 1, getImage().getHeight() - 1);
        getImage().setColor(Color.YELLOW);
        getImage().fillOval(2,2,getImage().getWidth() - 5, getImage().getHeight() - 5);
        getImage().setTransparency(255);
    }
    
    public void act() {
        if(canMove) {
            ArrayList<Actor> objs = (ArrayList<Actor>)getWorld().getObjects(Box.class);
            objs.addAll((ArrayList<Actor>)getWorld().getObjects(Paddle.class));

            for(int i = 0; i < objs.size(); i++) {
                Actor a = objs.get(i);

                if( a.getX() + a.getImage().getWidth()/2 >= getX() - getImage().getWidth()/2 + xspeed &&
                    a.getX() - a.getImage().getWidth()/2 <= getX() + getImage().getWidth()/2 + xspeed &&
                    a.getY() + a.getImage().getHeight()/2 >= getY() - getImage().getHeight()/2 + yspeed &&
                    a.getY() - a.getImage().getHeight()/2 <= getY() + getImage().getHeight()/2 + yspeed ) {
                        
                    if( a.getX() + a.getImage().getWidth()/2 <= getX() - getImage().getWidth()/2 ||
                        a.getX() - a.getImage().getWidth()/2 >= getX() + getImage().getWidth()/2 )
                        xspeed *= -1;
                    else
                        yspeed *= -1;
                        
                    if(a.getClass() == Box.class)
                        ((Box)a).remove = true;
                        
                    break;
                }
            }
            
            if(getY() + yspeed >= getWorld().getHeight() - 10 || getY() + yspeed <= 10) {
                if(getY() + yspeed >= getWorld().getHeight() - 10 && !fade) {
                    ((Screen)getWorld()).increaseScore(-5);
                }
                    
                if(getY() + xspeed <= 10 && !fade)
                    ((Screen)getWorld()).increaseScore(-5);
                    
                yspeed *= -1;
            }
            
            if(getX() + xspeed >= getWorld().getWidth() - 10 || getX() + xspeed <= 10) {
                if(getX() + xspeed >= getWorld().getWidth() - 10 && !fade)
                    ((Screen)getWorld()).increaseScore(-5);
                    
                if(getX() + xspeed <= 10 && !fade)
                    ((Screen)getWorld()).increaseScore(-5);
                xspeed *= -1;
            }
            
            setLocation(getX() + xspeed, getY() + yspeed);
        }
        
        if(moveVert) {
            setLocation(getX(), getY() - yspeed);
            
            if(getY() >= getWorld().getHeight() - 10 || getY() <= 10) {
                yspeed *= -1;
            }
            
            ArrayList<Box> objs = (ArrayList<Box>)getIntersectingObjects(Box.class);
            
            if(objs.size() > 0) {
                Box b = objs.get(0);
                
                if(b.getY() < getY() || b.getY() > getY())
                    yspeed *= -1;
                    
                b.remove = true;
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
}

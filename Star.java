import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Star here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Star extends Actor
{
    int width = 32, height = 32, delay = 0, delayCount = 0;
    int w = 1, h = 1;
    double ymod= 0;
    boolean icon = false, fade = false, hide = false, remove = false, fall = false;
    
    public Star(boolean icon) {
        this.icon = icon;
        getImage().scale(1,1);
    }
    
    public Star() {
        icon = false;
        getImage().scale(1,1);
    }
    
    public void addedToWorld(World world) {
        if(hide)
            getImage().setTransparency(0);
    }
    
    public void act() 
    {
        if(w < 34 && h < 34 && !icon && !hide) {
            setImage(new GreenfootImage("Star.png"));
            getImage().scale(w,h);
            w+= 2;
            h+= 2;
        }
        
        if(w < 18 && h < 18 && icon && !hide) {
            setImage(new GreenfootImage("Star.png"));
            getImage().scale(w,h);
            w+= 2;
            h+= 2;
        }
        
        if(Greenfoot.mouseClicked(null) && !fade && !icon) {
            ((Screen)getWorld()).menuSetup();
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
            remove = true;
        }
        
        if(fade) {
            if(getImage().getTransparency() - 10 <= 0) {
                remove = true;
            } else {
                getImage().setTransparency(getImage().getTransparency() - 10);
            } 
        }
        
        if(remove) {
            getWorld().removeObject(this);
        }
    }    
    
    public void fall() {
        this.fall = true;
        this.delay = Greenfoot.getRandomNumber(25);
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Sound here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sound extends Actor
{

    private boolean soundOn = true;
    private boolean musicOn = true;
    boolean fade = false;
    public Sound( boolean musicOn ) {
        this.musicOn = musicOn; 
    }
    
    protected void addedToWorld(World world) {
        getImage().setTransparency(200);
        fade = false;
        if( soundOn )
            setImage( new GreenfootImage("SoundOn.png") );
        else
            setImage( new GreenfootImage("SoundOff.png") );
    }
    
    public void act() 
    {
        if( Greenfoot.mouseClicked(this) ) {
            soundOn = !soundOn;
            ((Screen)getWorld()).soundSet( soundOn );
            
            if( soundOn )
                setImage( new GreenfootImage("SoundOn.png") );
            else
                setImage( new GreenfootImage("SoundOff.png") );
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

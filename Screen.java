import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.awt.*;

/**
 * Write a description of class Screen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Screen extends World
{
    int state = 0, i = 0, level = 0, max = 0;
    boolean first = true, shoot = true, stop = false, wait = false, credits = false;
    Score score;
    
    Text text;
    Paddle paddle;
    Ball ball;
    
    ArrayList<Ball> balls;
    ArrayList<Paddle> paddles;
    
    int[] stars;
    
    private boolean setup = true, boxSetup = true;
    boolean musicOn = true;
    Sound sound;
    public GreenfootSound music;
    
    public Screen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1, false); 
        Greenfoot.setSpeed(50);
        
        setPaintOrder(Ball.class, Box.class, Score.class);
        
        balls = new ArrayList<Ball>();        
        paddles = new ArrayList<Paddle>();
        stars = new int[21];
        
        addObject(new Background(), 300, 200);
        music = new GreenfootSound("sounds/BgMusic.mp3");
        music.playLoop();
        music.setVolume( 15 );
        sound = new Sound( musicOn );
        
        
        Greenfoot.start();
    }
    
    public void soundSet( boolean sound ) {
        musicOn = sound;
        if( sound )
            music.playLoop();
        else
            music.pause();
    }
    
    public void increaseScore(int points) {
        score.update(points);
    }
    
    public void increaseState() {
        state++;
        first = true;
        shoot = true;
        setup = boxSetup = true;
        i = 0;
    }
    int tick = 0;
    
    public boolean checkBoxes() {
        ArrayList<Box> boxes = (ArrayList<Box>)getObjects(Box.class);
        
        for(int i = 0; i < boxes.size();i++) {
            if(boxes.get(i).animate)
                return false;
        }
        return true;
    }
    
    public void act() {
        if(wait) {
            if( getObjects(LevelIcon.class).size() == 0 && getObjects(Text.class).size() == 0 
                && getObjects(Star.class).size() == 0) {
                wait = false;
                setupLevel(level);
            }
        }
        
        if(credits) {
            if(Greenfoot.mouseClicked(null)) {
                credits = false;
                state = 2;
                
                ArrayList<Text> text = (ArrayList<Text>)getObjects(Text.class);
        
                for(int i = 0; i < text.size();i++) {
                    text.get(i).fade();
                }
            }
        }
        
        if(state == 0 && !stop) {
            tick++;
            if(tick == 1) {
                text = new Text("B");
                text.bounce = true;
                text.dx = 250;
                text.dy = 150;
                addObject(text, 235, -100);
                addObject(sound, 590, 10);
                addObject(new Text("Credits", 24.f), 570, 390);
            }
            
            if(tick == 10) {
                text = new Text("o");
                text.bounce = true;
                text.dy = 150;
                addObject(text, 263, -100);
            }
            
            if(tick == 20) {
                text = new Text("u");
                text.bounce = true;
                text.dy = 150;
                addObject(text, 290, -100);
            }
            
            if(tick == 30) {
                text = new Text("n");
                text.bounce = true;
                text.dy = 150;
                addObject(text, 317, -100);
            }
            
            if(tick == 40) {
                text = new Text("c");
                text.bounce = true;
                text.dy = 150;
                addObject(text, 345, -100);
            }
            
            if(tick == 50) {
                text = new Text("y");
                text.bounce = true;
                text.dy = 150;
                addObject(text, 370, -100);
            }
            
            if(tick == 60) {
                text = new Text("!");
                text.bounce = true;
                text.dy = 150;
                addObject(text, 392, -100);
            }
            
            if(tick > 0 && tick % 7 == 0) {
                i++;
                if(i < 7)
                    addObject(new LevelIcon(i, stars[i]), 40 + i * 75, 240);
                else if(i < 13)
                    addObject(new LevelIcon(i, stars[i]), 40 + (i - 6) * 75, 300);
            }
        } else if(state == 1) {
            if(getObjects(Box.class).size() == 0 && first) {
                ArrayList<Ball> balls = (ArrayList<Ball>)getObjects(Ball.class);
        
                for(int i = 0; i < balls.size();i++) {
                    balls.get(i).fade = true;
                }
                
                ArrayList<Paddle> paddles = (ArrayList<Paddle>)getObjects(Paddle.class);
        
                for(int i = 0; i < paddles.size();i++) {
                    paddles.get(i).fade = true;
                }
                
                first = false;
            }
            
            if(!first) {
                if( getObjects(Box.class).size() == 0 && getObjects(Ball.class).size() == 0 &&
                    getObjects(Paddle.class).size() == 0  && getObjects(Text.class).size() == 0) {
                    addObject(new Text("Click to continue."), 300, 130);
                    if(score.score == max) {
                        addObject(new Star(), 300, 250);
                        addObject(new Star(), 280, 270);
                        addObject(new Star(), 320, 270);
                        stars[level] = 3;
                    } else if(score.score >= max * 0.8) {
                        addObject(new Star(), 282, 250);
                        addObject(new Star(), 312, 250);
                        stars[level] = Math.max(2, stars[level]);
                    } else if(score.score >= max * 0.6) {
                        addObject(new Star(), 300, 250);
                        stars[level] = Math.max(1, stars[level]);
                    } else {
                        Star s = new Star();
                        s.hide = true;
                        addObject(s, 300, 250);
                        stars[level] = Math.max(0, stars[level]);
                    }
                }
            }
        } else if(state == 2) {
            if( getObjects(Text.class).size() == 0 && getObjects(Star.class).size() == 0 && 
                getObjects(Score.class).size() == 0) {
                
                tick = 0;
                i = 0;
                state = 0;
                stop = false;
            }
        }
    }
    
    public void menuSetup() {
        state = 2;
        
        ArrayList<Text> text = (ArrayList<Text>)getObjects(Text.class);
        
        for(int i = 0; i < text.size();i++) {
            text.get(i).fade();
        }
        
        ArrayList<Star> stars = (ArrayList<Star>)getObjects(Star.class);
        
        for(int i = 0; i < stars.size();i++) {
            stars.get(i).fade = true;
        }
        
        score.fade = true;
    }
    
    public void makeCredits() {
        stop = true;
        sound.fade = true;
        ArrayList<LevelIcon> icons = (ArrayList<LevelIcon>)getObjects(LevelIcon.class);
        
        for(int i = 0; i < icons.size();i++) {
            icons.get(i).fall();
        }
               
        ArrayList<Star> stars = (ArrayList<Star>)getObjects(Star.class);
        
        for(int i = 0; i < stars.size();i++) {
            stars.get(i).fall();
        }
        
        ArrayList<Text> text = (ArrayList<Text>)getObjects(Text.class);
        
        for(int i = 0; i < text.size();i++) {
            text.get(i).fade();
        }
        
        addObject(new Text("Programming, design, and art by ", 42.f), 300, 50);
        addObject(new Text("Tucker Long",42.f), 300, 80);
        addObject(new Text("tuckerlong.net", 32.f), 300, 110);
        
        addObject(new Text("Music by eatmeatleet", 42.f), 300, 170);
        addObject(new Text("at newgrounds.com",42.f), 300, 200);
        addObject(new Text("http://www.newgrounds.com/audio/listen/575899", 32.f), 300, 230);
        
        addObject(new Text("Font by Louis Fineberg", 42.f), 300, 290);
        addObject(new Text("at 1001freefonts.com", 42.f), 300, 320);
        addObject(new Text("http://www.1001freefonts.com/life_is_goofy.font", 32.f), 300, 350);
        
        credits = true;
    }
    
    public void makeLevel(int level) {
        stop = true;
        sound.fade = true;
        this.level = level;
        wait = true;
        ArrayList<LevelIcon> icons = (ArrayList<LevelIcon>)getObjects(LevelIcon.class);
        
        for(int i = 0; i < icons.size();i++) {
            icons.get(i).fall();
        }
               
        ArrayList<Star> stars = (ArrayList<Star>)getObjects(Star.class);
        
        for(int i = 0; i < stars.size();i++) {
            stars.get(i).fall();
        }
        
        ArrayList<Text> text = (ArrayList<Text>)getObjects(Text.class);
        
        for(int i = 0; i < text.size();i++) {
            text.get(i).fade();
        }
    }
    
    public void setupLevel(int level) {
        state = 1;
        first = true;
        
        score = new Score();
        score.fadeIn = true;
        addObject(score, 300, 200);
        
        if(level == 1) {
            addObject(new Box(), 300, 200);
        }
        
        if(level == 2) {
            addObject(new Box(), 150, 150);
            addObject(new Box(), 150, 250);
            addObject(new Box(), 450, 150);
            addObject(new Box(), 450, 250);
        }
        
        if(level == 3) {
            for(int i = 0; i < 3; i++) {
                addObject(new Box(), 150 + i * 150, 100);
                addObject(new Box(), 150 + i * 150, 200);
                addObject(new Box(), 150 + i * 150, 300);
            }
        }
        
        if(level == 4) {
            for(int i = 0; i < 7; i++) {
                if(i != 3)
                    addObject(new Box(), 300, 50 + i * 50);
                addObject(new Box(), 150 + i * 50, 200);
            }
        }
        
        if(level == 5) {
            for(int i = 0; i < 5; i++) {
                if(i != 4)
                    addObject(new Box(), 100 + i * 50, 100 + i * 50);
                addObject(new Box(), 500 - i * 50, 100 + i * 50);   
            }
        }
        
        if(level == 6) {
            for(int i = 0; i < 5; i++) {
                addObject(new Box(), 150 + i * 75, 100);
                addObject(new Box(), 150 + i * 75, 200);
                addObject(new Box(), 150 + i * 75, 300);
            }
        }
        
        if(level == 7) {
            for(int i = 0; i < 5; i++) {
                if(i < 3) {
                    addObject(new Box(), 175 + i * 50, 100 + i * 50);
                    addObject(new Box(), 425 - i * 50, 100 + i * 50);
                }
                if(i != 4)
                    addObject(new Box(), 100 + i * 50, 100 + i * 50);
                addObject(new Box(), 500 - i * 50, 100 + i * 50);   
            }
        }
        
        if(level == 8) {
            for(int i = 0; i < 5; i++) {
                addObject(new Box(), 150, 100 + i * 50);
                addObject(new Box(), 300, 100 + i * 50);
                addObject(new Box(), 450, 100 + i * 50);
            }
        }
        
        if(level == 9) {
            for(int i = 0; i < 5; i++) {
                addObject(new Box(), 150 + i * 75, 80);
                addObject(new Box(), 150 + i * 75, 140);
                addObject(new Box(), 150 + i * 75, 260);
                addObject(new Box(), 150 + i * 75, 320);
            }
        }
        
        if(level == 10) {
            for(int i = 0; i < 11; i++) {
                addObject(new Box(), 50 + i * 50, 50);
                if(i < 4) {
                    addObject(new Box(), 100 + i * 50, 100 + i * 25);
                    addObject(new Box(), 100 + i * 50, 300 - i * 25);
                    
                    addObject(new Box(), 500 - i * 50, 100 + i * 25);
                    addObject(new Box(), 500 - i * 50, 300 - i * 25);
                }
                addObject(new Box(), 50 + i * 50, 350);
            }
            addObject(new Box(), 300, 200);
        }
        
        if(level == 11) {
            for(int i = 0; i < 11; i++) {
                addObject(new Box(), 50 + i * 50, 50);
                if(i < 3) {
                    addObject(new Box(), 50, 85 + i * 35);
                    addObject(new Box(), 50, 315 - i * 35);
                    
                    addObject(new Box(), 550, 85 + i * 35);
                    addObject(new Box(), 550, 315 - i * 35);
                }
                addObject(new Box(), 50 + i * 50, 350);
            }
        }
        
        if(level == 12) {
            for(int i = 0; i < 11; i++) {
                addObject(new Box(), 50 + i * 50, 50);
                if(i < 3) {
                    addObject(new Box(), 50, 85 + i * 35);
                    addObject(new Box(), 50, 315 - i * 35);
                    
                    addObject(new Box(), 550, 85 + i * 35);
                    addObject(new Box(), 550, 315 - i * 35);
                }
                addObject(new Box(), 50 + i * 50, 350);
            }
            
            for(int i = 0; i < 9; i++) {
                addObject(new Box(), 100 + i * 50, 100);
                if(i < 2) {
                    addObject(new Box(), 100, 135 + i * 35);
                    addObject(new Box(), 100, 265 - i * 35);
                    
                    addObject(new Box(), 500, 135 + i * 35);
                    addObject(new Box(), 500, 265 - i * 35);
                }
                addObject(new Box(), 100 + i * 50, 300);
            }
        }
        
        max = getObjects(Box.class).size() * 10;
            
        Paddle p = new Paddle();
        Ball b = new Ball();
        
        p.setBall(b);
        p.held = true;
        
        if(Greenfoot.getMouseInfo() == null) {
            addObject(p, 300, 393);
            addObject(b, 300, 380);
            
            addObject(new Paddle(), 300, 7);
            addObject(new Paddle(true), 7, 200);
            addObject(new Paddle(true), 593, 200);
        } else {
            addObject(b, Greenfoot.getMouseInfo().getX(), 380);
            addObject(p, Greenfoot.getMouseInfo().getX(), 393);
            
            addObject(new Paddle(), Greenfoot.getMouseInfo().getX(), 7);
            addObject(new Paddle(true), 7, Greenfoot.getMouseInfo().getY());
            addObject(new Paddle(true), 593, Greenfoot.getMouseInfo().getY());
        } 
    }
}

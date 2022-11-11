import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Boid here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Boid extends SmoothMover
{
    private double angle,speed;
    
    public void Boid(double speed, double angle){
        this.angle = angle;
        this.speed = speed;
        
    }
    public void addedToWorld(World world){
        this.angle = Greenfoot.getRandomNumber(360);
        setRotation((int)this.angle);
    }
    public void act()
    {
        if(getX() > 50 && getX() < 550 && getY() > 50 && getY() < 550)
            cohesion();
            speed();
            alignment();
            seperation();
        borderless();
        accumMove();
    }
    public void cohesion(){
        List nearBoids = getObjectsInRange(150,Boid.class);
        if(!nearBoids.isEmpty()){
            int aX = 0,aY = 0;
            for(int i = 0; i < nearBoids.size(); i++){
                aX += (int)((Boid)nearBoids.get(i)).getX();
            }
            for(int i = 0; i < nearBoids.size(); i++){
                aY += (int)((Boid)nearBoids.get(i)).getY();
            }
            aX /= nearBoids.size();
            aY /= nearBoids.size();
            
        }
    }
    public void speed(){
    
    }
    public void alignment(){
        List nearBoids = getObjectsInRange(125,Boid.class);
        if(!nearBoids.isEmpty()){
            double aAngle = 0.0;
            for(int i = 0; i < nearBoids.size(); i++){
                aAngle += ((Boid)nearBoids.get(i)).getAngle();
            }
            aAngle /= nearBoids.size();
            aAngle -= getRotation();
            aAngle = aAngle > 0 ? (aAngle > 5 ? 5 : aAngle) : (aAngle < -5 ? -5 : aAngle);
            
            setRotation(getRotation() + (int)aAngle);
        }
    }
    public void seperation(){
        List nearBoids = getObjectsInRange(25,Boid.class);
        if(!nearBoids.isEmpty()){
            int aX = 0,aY = 0;
            double m;
            for(int i = 0; i < nearBoids.size(); i++){
                aX += (int)((Boid)nearBoids.get(i)).getX();
            }
            for(int i = 0; i < nearBoids.size(); i++){
                aY += (int)((Boid)nearBoids.get(i)).getY();
            }
            aX /= nearBoids.size();
            aY /= nearBoids.size();
            
            try{
                m = (getExactY() - aY)/(getExactX() - aX);
            }catch(ArithmeticException e){
                m = 0;
            }
            double dX =(getX()-aX)/2;
            dX = dX > 0 ? (dX > 1 ? 1 : dX) : (dX < -1 ? -1 : dX);
            double dY = (getY()-aY)/2;
            dY = dY > 0 ? (dY > 1 ? 1 : dY) : (dY < -1 ? -1 : dY);
            setLocation(getExactX() + dX,getExactY() + dY);
            
        }
    }
    public void borderless(){
        if(getX() < 25){
            setLocation(574,getExactY());
        }else if(getX() > 575){
            setLocation(26,getExactY());
        }
        if(getY() < 25){
            setLocation(getExactX(),574);
        }else if(getY() > 575){
            setLocation(getExactX(),26);
        }
        
    }
    public void accumMove(){
        move(2.5);
    }
    public double getSpeed(){
        return speed;
    }
    public double getAngle(){
        return angle;
    }
}

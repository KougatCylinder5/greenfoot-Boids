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
    private double angle,speed = 5;
    
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
        if(getX() > 100 && getX() < 900 && getY() > 100 && getY() < 900){
            cohesion();
            speed();
            accum(seperation(),alignment());
        }else{
            accumMove();
        }
        borderless();
        
    }
    public void accum(List sep, double ali){
        move((double)(sep.get(0)));
        this.speed = (double)sep.get(0);
        turn((int)(double)sep.get(1) + (int)ali);
        System.out.println((double)sep.get(1));
    }
    public void cohesion(){
        List nearBoids = getObjectsInRange(25,Boid.class);
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
    public double alignment(){
        List nearBoids = getObjectsInRange(150,Boid.class);
        if(!nearBoids.isEmpty()){
            double aAngle = 0.0;
            for(int i = 0; i < nearBoids.size(); i++){
                aAngle += ((Boid)nearBoids.get(i)).getAngle();
            }
            aAngle /= nearBoids.size();
            aAngle -= getRotation();
            aAngle = aAngle > 0 ? (aAngle > 5 ? 5 : aAngle) : (aAngle < -5 ? -5 : aAngle);
            //setRotation(getRotation() + (int)aAngle);
            return aAngle + (Greenfoot.getRandomNumber(3)-2) * 0.5;
        }
        return Greenfoot.getRandomNumber(3)-2;
    }
    public List seperation(){
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
            
            int curAngle = getRotation();
            turnTowards(aX,aY);
            int rotation = getRotation();       
            // double dX =(getX()-aX)/2;
            // dX = dX > 0 ? (dX > 1 ? 1 : dX) : (dX < -1 ? -1 : dX);
            // double dY = (getY()-aY)/2;
            // dY = dY > 0 ? (dY > 1 ? 1 : dY) : (dY < -1 ? -1 : dY);
            // setLocation(getExactX() + dX,getExactY() + dY);
            //speed,angle
            setRotation(curAngle);
            return Arrays.asList(Math.abs(curAngle-rotation) > 90 ? 2.7 : 2.3,curAngle-rotation > 0 ? curAngle - rotation < 5 ? curAngle - rotation : 5.0 : curAngle - rotation > -5 ? curAngle - rotation : -5.0);
        }
        return Arrays.asList(this.speed,0.0);
    }
    public void borderless(){
        if(getX() < 25){
            setLocation(1074,getExactY());
        }else if(getX() > 1075){
            setLocation(26,getExactY());
        }
        if(getY() < 25){
            setLocation(getExactX(),974);
        }else if(getY() > 975){
            setLocation(getExactX(),26);
        }
        
    }
    public void accumMove(){
        move(this.speed);
    }
    public double getSpeed(){
        return speed;
    }
    public double getAngle(){
        return angle;
    }
}

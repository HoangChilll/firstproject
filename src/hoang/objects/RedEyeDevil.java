package hoang.objects;

import hoang.effect.Animation;
import hoang.effect.CacheDataLoader;

import java.awt.*;

public class RedEyeDevil extends ParticularObject{
    private Animation backAnim,forwardAnim;
    private long startTimeToshoot;
    public  RedEyeDevil(float x,float y,GameWorld gameWorld){
        super(x,y,127,89,0,100,gameWorld);
        backAnim= CacheDataLoader.getInstance().getAnimation("redeye");
        forwardAnim= CacheDataLoader.getInstance().getAnimation("redeye");
        forwardAnim.flipAllImages();
        startTimeToshoot=0;
        setDamage(10);
        setTimeForNoBeHust(300000000);
    }

    @Override
    public void draw(Graphics2D g2) {
        if(!isObjectOutOfCameraView()){
            if(getState()==NOBEHURT&&(System.nanoTime()/10000000)%2!=1){

            }
            else{
                if(getDirection()==LEFT_DIR){
                    backAnim.update(System.nanoTime());
                    backAnim.draw((int)(getPosX()-getGameWorld().camera.getPosX()),(int)(getPosY()-getGameWorld().camera.getPosY()),g2);;
                }
                else{
                    forwardAnim.update(System.nanoTime());
                    forwardAnim.draw((int)(getPosX()-getGameWorld().camera.getPosX()),(int)(getPosY()-getGameWorld().camera.getPosY()),g2);
                }
            }
        }
    }

    @Override
    public Rectangle getBoundForCollsionWithEnemy() {
        Rectangle rect=getBoundCollisonWithMap();
        rect.x+=20;
        rect.width-=40;
        return rect;
    }

    @Override
    public void attack() {
        Bullet bullet =new RedEyeBullet(getPosX(),getPosY(),getGameWorld());
        if(getDirection()==LEFT_DIR) bullet.setSpeedX(-8);
        else bullet.setSpeedX(8);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);
    }
    @Override
    public void update(){
        super.update();
        if(System.nanoTime()-startTimeToshoot>1000*10000000){
            attack();
            System.out.println("Red Eye Attack");
            startTimeToshoot=System.nanoTime();
        }
    }
}

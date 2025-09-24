package hoang.objects;

import hoang.effect.Animation;
import hoang.effect.CacheDataLoader;

import java.awt.*;

public class RedEyeBullet extends Bullet{
    private Animation forwardBulletAnim,backbulletAnim;
    public  RedEyeBullet(float x,float y,GameWorld gameWorld){
        super(x,y,30,30,1.0f,10,gameWorld);
        forwardBulletAnim= CacheDataLoader.instance.getAnimation("redeyebullet");
        backbulletAnim= CacheDataLoader.instance.getAnimation("redeyebullet");
        backbulletAnim.flipAllImages();
    }


    @Override
    public void draw(Graphics2D g2) {
        // TODO Auto-generated method stub
        if(getSpeedX() > 0){
            forwardBulletAnim.update(System.nanoTime());
            forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }else{
            backbulletAnim.update(System.nanoTime());
            backbulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
        //drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public Rectangle getBoundForCollsionWithEnemy() {
        return getBoundCollisonWithMap();
    }

    @Override
    public void attack() {

    }
    @Override
    public void update(){
        super.update();
    }
}

package hoang.objects;

import hoang.effect.Animation;
import hoang.effect.CacheDataLoader;

import java.awt.*;

public class RocketBullet extends Bullet{

    private Animation forwardBulletAnimUp, forwardBulletAnimDown, forwardBulletAnim;
    private Animation backBulletAnimUp, backBulletAnimDown, backBulletAnim;

    private long startTimeForChangeSpeedY;

    public RocketBullet(float x, float y, GameWorld gameWorld) {

        super(x, y, 30, 30, 1.0f, 10, gameWorld);

        backBulletAnimUp = CacheDataLoader.getInstance().getAnimation("rocketUp");
        backBulletAnimDown = CacheDataLoader.getInstance().getAnimation("rocketDown");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("rocket");

        forwardBulletAnimUp = CacheDataLoader.getInstance().getAnimation("rocketUp");
        forwardBulletAnimUp.flipAllImages();
        forwardBulletAnimDown = CacheDataLoader.getInstance().getAnimation("rocketDown");
        forwardBulletAnimDown.flipAllImages();
        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("rocket");
        forwardBulletAnim.flipAllImages();

    }

    @Override
    public void draw(Graphics2D g2) {
        // TODO Auto-generated method stub
        if(getSpeedX() > 0){
            if(getSpeedY() > 0){
                forwardBulletAnimDown.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else if(getSpeedY() < 0){
                forwardBulletAnimUp.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else
                forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }else{
            if(getSpeedY() > 0){
                backBulletAnimDown.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else if(getSpeedY() < 0){
                backBulletAnimUp.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else
                backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
        //drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public Rectangle getBoundForCollsionWithEnemy() {
        return getBoundCollisonWithMap();
    }

    private void changeSpeedY(){
        if(System.currentTimeMillis() % 3 == 0){
            setSpeedY(getSpeedX());
        }else if(System.currentTimeMillis() % 3 == 1){
            setSpeedY(-getSpeedX());
        }else {
            setSpeedY(0);

        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        super.update();

        if(System.nanoTime() - startTimeForChangeSpeedY > 500*1000000){
            startTimeForChangeSpeedY = System.nanoTime();
            changeSpeedY();
        }
    }

    @Override
    public void attack() {}

}
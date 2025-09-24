package hoang.objects;

import hoang.effect.Animation;
import hoang.effect.CacheDataLoader;

import java.awt.*;

public class kakarot extends Human{
    private Animation walkanim;
    public kakarot(float x,float y,GameWorld gameWorld) {
        super(x, y, 70, 90, 0.1f, 100, gameWorld);
        setTeamType(LEAGUE_TEAM);
        setTimeForNoBeHust(2000 * 1000000);
        walkanim= CacheDataLoader.getInstance().getAnimation("walk");
    }

    @Override
    public void run() {

    }

    @Override
    public void jump() {

    }

    @Override
    public void dick() {

    }

    @Override
    public void standUp() {

    }

    @Override
    public void stopRun() {

    }

    @Override
    public Rectangle getBoundForCollsionWithEnemy() {

        Rectangle rect=getBoundCollisonWithMap();
        if(isDicking()){
            rect.x=(int)getPosX()-22;
            rect.y=(int)getPosY()-20;
            rect.width=44;
            rect.height=65;
        }
        else{
            rect.x=(int)getPosX()-22;
            rect.y=(int)getPosY()-40;
            rect.width=44;
            rect.height=80;
        }
        return rect;
    }

    @Override
    public void attack() {

    }
    @Override
    public void update(){
        super.update();
    }
    @Override
    public void draw(Graphics2D g2){
       walkanim.update(System.nanoTime());
        walkanim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundCollisonWithMap().height/2 - walkanim.getCurrentImage().getHeight()/2),
                g2);
    }
}

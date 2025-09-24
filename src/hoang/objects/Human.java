package hoang.objects;

import java.awt.*;

public abstract class Human extends ParticularObject{
    private boolean isJumping;//đang bay
    private boolean isDicking;// đang ngồi
    private boolean isLanding;// đang rơi xuống đất
    public Human(float x, float y, float width, float height, float mass, int blood, GameWorld gameWorld) {
        super(x, y, width, height, mass, blood, gameWorld);
        setState(ALIVE);
    }
    public abstract void run();
    public abstract void jump();
    public abstract void dick();
    public abstract void standUp();
    public abstract void stopRun();

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public boolean isDicking() {
        return isDicking;
    }

    public void setDicking(boolean dicking) {
        isDicking = dicking;
    }

    public boolean isLanding() {
        return isLanding;
    }

    public void setLanding(boolean landing) {
        isLanding = landing;
    }

    @Override
    public void update(){
        super.update();
        if(getState()==ALIVE||getState()==NOBEHURT){
            if(!isLanding){
                setPosX(getPosX()+getSpeedX());
                if(getDirection()==LEFT_DIR&&getGameWorld().physicalMap.haveCollsionWithLeftWall(getBoundCollisonWithMap())!=null){
                    Rectangle rectLeftWall=getGameWorld().physicalMap.haveCollsionWithLeftWall(getBoundCollisonWithMap());
                    setPosX(rectLeftWall.x+rectLeftWall.width+getWidth()/2);
                }
                if(getDirection()==RIGHT_DIR&&getGameWorld().physicalMap.haveCollsionWithRightWall(getBoundCollisonWithMap())!=null){
                    Rectangle rectRightWall=getGameWorld().physicalMap.haveCollsionWithRightWall(getBoundCollisonWithMap());
                    setPosX(rectRightWall.x-+getWidth()/2);
                }
            }
            Rectangle boundForCollsionWithMapFuture=getBoundCollisonWithMap();
            boundForCollsionWithMapFuture.y+=(getSpeedY()!=0?getSpeedY():2);
            Rectangle rectLand=getGameWorld().physicalMap.haveCollsionWithland(boundForCollsionWithMapFuture);
            Rectangle rectTop=getGameWorld().physicalMap.haveCollsionWithTop(boundForCollsionWithMapFuture);
            if(rectTop!=null){
                setSpeedY(0);
                setPosY(rectTop.y+getGameWorld().physicalMap.getTitleSize()+getHeight()/2);
            }
            else if(rectLand!=null){
                setJumping(false);
                if(getSpeedY()>0) setLanding(true);
                setSpeedY(0);
                setPosY(rectLand.y-getHeight()/2-1);
            }
            else{
                setJumping(true);
                setSpeedY(getSpeedY()+getMass());
                setPosY(getPosY()+getSpeedY());
            }
        }
    }

    public abstract Rectangle getBoundForCollsionWithEnemy();
}

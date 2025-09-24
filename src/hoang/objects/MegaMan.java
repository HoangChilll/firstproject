package hoang.objects;

import hoang.effect.Animation;
import hoang.effect.CacheDataLoader;

import java.awt.*;

public class MegaMan extends Human{
    public static final int RUNSPEED=3;
    private long lastShootingTime;
    private boolean isShooting=false;

  private Animation runForwardAnim,runBackAnim,runShootingForwardAnim,runShootingBackAnim;
    private Animation idleForwardAnim,idleBackAnim,idleShootingForwardAnim,idleShootingBackAnim;
    private Animation dickForwardAnim,dickBackAnim;
    private Animation flyForwardAnim,flyBackAnim,flyShootingForwardAnim,flyShootingBackAnim;
    private Animation landingForwardAnim,landingBackAnim;
    private Animation climWallForward,climWallBack;
    private Animation behurtForwardAnim,behurtBackAnim;
    //private AudioClip hurtingSound;
    //private  AudioClip shooting1;

    public MegaMan(float x,float y,GameWorld gameWorld){
           super(x,y,70,90,0.1f,100,gameWorld);
           setTeamType(LEAGUE_TEAM);
           setTimeForNoBeHust(2000*1000000);
           //shooting1= CacheDataLoader.getInstance().getSound("bluefireshooting");
           //hurtingSound=CacheDataLoader.getInstance().getSound("megamanhurt");
           runForwardAnim=CacheDataLoader.getInstance().getAnimation("run");
           runBackAnim=CacheDataLoader.getInstance().getAnimation("run");
           runBackAnim.flipAllImages();
           idleForwardAnim=CacheDataLoader.getInstance().getAnimation("idle");
           idleBackAnim=CacheDataLoader.getInstance().getAnimation("idle");
           idleBackAnim.flipAllImages();
           dickForwardAnim=CacheDataLoader.getInstance().getAnimation("dick");
           dickBackAnim=CacheDataLoader.getInstance().getAnimation("dick");
           dickBackAnim.flipAllImages();
           flyForwardAnim=CacheDataLoader.getInstance().getAnimation("flyingup");
           flyForwardAnim.setIsRepeated(false);
           flyBackAnim=CacheDataLoader.getInstance().getAnimation("flyingup");
           flyBackAnim.setIsRepeated(false);
           flyBackAnim.flipAllImages();
           landingForwardAnim=CacheDataLoader.getInstance().getAnimation("landing");
           landingBackAnim=CacheDataLoader.getInstance().getAnimation("landing");
           landingBackAnim.flipAllImages();
           behurtForwardAnim=CacheDataLoader.getInstance().getAnimation("hurting");
           behurtBackAnim=CacheDataLoader.getInstance().getAnimation("hurting");
           behurtBackAnim.flipAllImages();
           idleShootingForwardAnim=CacheDataLoader.getInstance().getAnimation("idleshoot");
           idleShootingBackAnim=CacheDataLoader.getInstance().getAnimation("idleshoot");
           idleShootingBackAnim.flipAllImages();
           runShootingForwardAnim=CacheDataLoader.getInstance().getAnimation("runshoot");
           runShootingBackAnim=CacheDataLoader.getInstance().getAnimation("runshoot");
           runShootingBackAnim.flipAllImages();
           flyShootingForwardAnim=CacheDataLoader.getInstance().getAnimation("flyingupshoot");
           flyShootingBackAnim=CacheDataLoader.getInstance().getAnimation("flyingupshoot");
           flyShootingBackAnim.flipAllImages();
           climWallBack=CacheDataLoader.getInstance().getAnimation("clim_wall");
           climWallForward=CacheDataLoader.getInstance().getAnimation("clim_wall");
           climWallForward.flipAllImages();



       }

    @Override
    public void run() {
        if(getDirection()==LEFT_DIR){
            setSpeedX(-3);
        }
        else{
            setSpeedX(3);
        }
    }

    @Override
    public void jump() {
          if(!isJumping()){
              setJumping(true);
              setSpeedY(-5.0f);
              //flyBackAnim.reset();
              //flyForwardAnim.reset();
          }
          else{//neu dang bay cho leo tuong
               Rectangle rectRightWall=getBoundCollisonWithMap();
               rectRightWall.x+=1;
               Rectangle rectLeftWall=getBoundCollisonWithMap();
               rectLeftWall.x-=1;
               if(getGameWorld().physicalMap.haveCollsionWithRightWall(rectRightWall)!=null&&getSpeedX()>0){
                   setSpeedY(-5.0f);
                   setSpeedX(-1);
                   flyBackAnim.reset();
                   flyForwardAnim.reset();
                   setDirection(LEFT_DIR);
               }
               else if(getGameWorld().physicalMap.haveCollsionWithRightWall(rectLeftWall)!=null&&getSpeedX()<0){
                   setSpeedY(-5.0f);
                   setSpeedX(-1);
                   flyBackAnim.reset();
                   flyForwardAnim.reset();
                   setDirection(RIGHT_DIR);
               }

          }
    }

    @Override
    public void dick() {
           if(isJumping()){
               setDicking(true);
           }
    }

    @Override
    public void standUp() {
        setDicking(false);
      idleForwardAnim.reset();
        idleBackAnim.reset();
        dickForwardAnim.reset();
        dickBackAnim.reset();
    }

    @Override
    public void stopRun() {
        setSpeedX(0);
        runForwardAnim.reset();
        runBackAnim.reset();
        runForwardAnim.unIgnoreFrame(0);
        runBackAnim.unIgnoreFrame(0);

    }

    @Override
    public void draw(Graphics2D g2) {

        switch(getState()){

            case ALIVE:
            case NOBEHURT:
                if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1)
                {
                    System.out.println("Plash...");
                }else{

                    if(isLanding()){

                        if(getDirection() == RIGHT_DIR){
                            landingForwardAnim.setCurrentFrame(landingBackAnim.getCurrentFrame());
                            landingForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundCollisonWithMap().height/2 - landingForwardAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }else{
                            landingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundCollisonWithMap().height/2 - landingBackAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }

                    }else if(isJumping()){

                        if(getDirection() == RIGHT_DIR){
                            flyForwardAnim.update(System.nanoTime());
                            if(isShooting){
                                flyShootingForwardAnim.setCurrentFrame(flyForwardAnim.getCurrentFrame());
                                flyShootingForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()) + 10, (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else
                                flyForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }else{
                            flyBackAnim.update(System.nanoTime());
                            if(isShooting){
                                flyShootingBackAnim.setCurrentFrame(flyBackAnim.getCurrentFrame());
                                flyShootingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()) - 10, (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else
                                flyBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }

                    }else if(isDicking()){

                        if(getDirection() == RIGHT_DIR){
                            dickForwardAnim.update(System.nanoTime());
                            dickForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundCollisonWithMap().height/2 - dickForwardAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }else{
                            dickBackAnim.update(System.nanoTime());
                            dickBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundCollisonWithMap().height/2 - dickBackAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }

                    }else{
                        if(getSpeedX() > 0){
                            runForwardAnim.update(System.nanoTime());
                            if(isShooting){
                                runShootingForwardAnim.setCurrentFrame(runForwardAnim.getCurrentFrame() - 1);
                                runShootingForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else
                                runForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            if(runForwardAnim.getCurrentFrame() == 1) runForwardAnim.setIgnoreFrame(0);
                        }else if(getSpeedX() < 0){
                            runBackAnim.update(System.nanoTime());
                            if(isShooting){
                                runShootingBackAnim.setCurrentFrame(runBackAnim.getCurrentFrame() - 1);
                                runShootingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else
                                runBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            if(runBackAnim.getCurrentFrame() == 1) runBackAnim.setIgnoreFrame(0);
                        }else{
                            if(getDirection() == RIGHT_DIR){
                                if(isShooting){
                                    idleShootingForwardAnim.update(System.nanoTime());
                                    idleShootingForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }else{
                                    idleForwardAnim.update(System.nanoTime());
                                    idleForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                            }else{
                                if(isShooting){
                                    idleShootingBackAnim.update(System.nanoTime());
                                    idleShootingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }else{
                                    idleBackAnim.update(System.nanoTime());
                                    idleBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                            }
                        }
                    }
                }

                break;

            case BEHURT:
                if(getDirection() == RIGHT_DIR){
                    behurtForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }else{
                    behurtBackAnim.setCurrentFrame(behurtForwardAnim.getCurrentFrame());
                    behurtBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
                break;

            case FEY:

                break;

        }

        //drawBoundForCollisionWithMap(g2);
        //drawBoundForCollisionWithEnemy(g2);
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

        if(!isShooting && !isDicking()){

            //shooting1.play();

            Bullet bullet = new BlueFire(getPosX(), getPosY(), getGameWorld());
            if(getDirection() == LEFT_DIR) {
                bullet.setSpeedX(-10);
                bullet.setPosX(bullet.getPosX() - 40);
                if(getSpeedX() != 0 && getSpeedY() == 0){
                    bullet.setPosX(bullet.getPosX() - 10);
                    bullet.setPosY(bullet.getPosY() - 5);
                }
            }else {
                bullet.setSpeedX(10);
                bullet.setPosX(bullet.getPosX() + 40);
                if(getSpeedX() != 0 && getSpeedY() == 0){
                    bullet.setPosX(bullet.getPosX() + 10);
                    bullet.setPosY(bullet.getPosY() - 5);
                }
            }
            if(isJumping())
                bullet.setPosY(bullet.getPosY() - 20);

            bullet.setTeamType(getTeamType());
            getGameWorld().bulletManager.addObject(bullet);

            lastShootingTime = System.nanoTime();
            isShooting = true;

        }

    }
    @Override
    public void update(){
           super.update();
          if(isShooting){
               if(System.nanoTime()-lastShootingTime>500*1000000){
                   isShooting=false;
               }
           }
           if(isLanding()){
               landingBackAnim.update(System.nanoTime());
               if(landingBackAnim.isLastFrame()){
                   setLanding(false);
                   landingBackAnim.reset();
                   runForwardAnim.reset();
                   runBackAnim.reset();
               }
           }
       }


}

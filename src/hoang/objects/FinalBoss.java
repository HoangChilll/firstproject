package hoang.objects;

import hoang.effect.Animation;
import hoang.effect.CacheDataLoader;

import java.awt.*;
import java.util.Hashtable;

public class FinalBoss extends Human {

    private Animation idleforward, idleback;
    private Animation shootingforward, shootingback;
    private Animation slideforward, slideback;

    private long startTimeForAttacked;

    private Hashtable<String, Long> timeAttack = new Hashtable<String, Long>();
    private String[] attackType = new String[4];
    private int attackIndex = 0;
    private long lastAttackTime;

    public FinalBoss(float x, float y, GameWorld  gameWorld) {
        super(x, y, 110, 150, 0.1f, 100, gameWorld);
        idleback = CacheDataLoader.getInstance().getAnimation("boss_idle");
        idleforward = CacheDataLoader.getInstance().getAnimation("boss_idle");
        idleforward.flipAllImages();

        shootingback = CacheDataLoader.getInstance().getAnimation("boss_shooting");
        shootingforward = CacheDataLoader.getInstance().getAnimation("boss_shooting");
        shootingforward.flipAllImages();

        slideback = CacheDataLoader.getInstance().getAnimation("boss_slide");
        slideforward = CacheDataLoader.getInstance().getAnimation("boss_slide");
        slideforward.flipAllImages();

        setTimeForNoBeHust(500*1000000);
        setDamage(10);

        attackType[0] = "NONE";
        attackType[1] = "shooting";
        attackType[2] = "NONE";
        attackType[3] = "slide";

        timeAttack.put("NONE", 2000L);
        timeAttack.put("shooting", 500L);
        timeAttack.put("slide", 5000L);

    }

    public void Update(){
        super.update();

        if(getGameWorld().megaMan.getPosX() > getPosX())
            setDirection(RIGHT_DIR);
        else setDirection(LEFT_DIR);

        if(startTimeForAttacked == 0)
            startTimeForAttacked = System.currentTimeMillis();
        else if(System.currentTimeMillis() - startTimeForAttacked > 300){
            attack();
            startTimeForAttacked = System.currentTimeMillis();
        }

        if(!attackType[attackIndex].equals("NONE")){
            if(attackType[attackIndex].equals("shooting")){

                Bullet bullet = new RocketBullet(getPosX(), getPosY() - 50, getGameWorld());
                if(getDirection() == LEFT_DIR) bullet.setSpeedX(-4);
                else bullet.setSpeedX(4);
                bullet.setTeamType(getTeamType());
                getGameWorld().bulletManager.addObject(bullet);

            }else if(attackType[attackIndex].equals("slide")){

                if(getGameWorld().physicalMap.haveCollsionWithLeftWall(getBoundCollisonWithMap())!=null)
                    setSpeedX(5);
                if(getGameWorld().physicalMap.haveCollsionWithRightWall(getBoundCollisonWithMap())!=null)
                    setSpeedX(-5);


                setPosX(getPosX() + getSpeedX());
            }
        }else{
            // stop attack
            setSpeedX(0);
        }

    }

    @Override
    public void run() {

    }

    @Override
    public void jump() {
        setSpeedY(-5);
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
    public void attack() {

        // only switch state attack

        if(System.currentTimeMillis() - lastAttackTime > timeAttack.get(attackType[attackIndex])){
            lastAttackTime = System.currentTimeMillis();

            attackIndex ++;
            if(attackIndex >= attackType.length) attackIndex = 0;

            if(attackType[attackIndex].equals("slide")){
                if(getPosX() < getGameWorld().megaMan.getPosX()) setSpeedX(5);
                else setSpeedX(-5);
            }

        }

    }


    @Override
    public void draw(Graphics2D g2) {

        if (getState() == NOBEHURT && (System.nanoTime() / 10000000) % 2 != 1) {
            System.out.println("Plash...");
        } else {

            if (attackType[attackIndex].equals("NONE")) {
                if (getDirection() == RIGHT_DIR) {
                    idleforward.update(System.nanoTime());
                    idleforward.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                } else {
                    idleback.update(System.nanoTime());
                    idleback.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
            } else if (attackType[attackIndex].equals("shooting")) {

                if (getDirection() == RIGHT_DIR) {
                    shootingforward.update(System.nanoTime());
                    shootingforward.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                } else {
                    shootingback.update(System.nanoTime());
                    shootingback.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }

            } else if (attackType[attackIndex].equals("slide")) {
                if (getSpeedX() > 0) {
                    slideforward.update(System.nanoTime());
                    slideforward.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY() + 50, g2);
                } else {
                    slideback.update(System.nanoTime());
                    slideback.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY() + 50, g2);
                }
            }
        }
        // drawBoundForCollisionWithEnemy(g2);
    }
    @Override
    public Rectangle getBoundForCollsionWithEnemy() {
        if(attackType[attackIndex].equals("slide")){
            Rectangle rect = getBoundCollisonWithMap();
            rect.y += 100;
            rect.height -= 100;
            return rect;
        }else
            return getBoundCollisonWithMap();
    }


}
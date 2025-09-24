package hoang.objects;

import hoang.effect.Animation;

import java.awt.*;

public abstract class ParticularObject extends GameObject {
        public  static final int LEAGUE_TEAM=1;
        public  static final int ENEMY_TEAM=2;
        public  static final int LEFT_DIR=0;
        public static final int RIGHT_DIR=1;
        public static final int ALIVE=0;
        public static final int BEHURT=1;
        public static final int FEY=2;// sap die
        public static final int DEATH=3;
        public static final int NOBEHURT=4;
        private int state;
        private float width;
        private float height;
        private float speedX;
        private float speedY;
        private float mass;
        private int blood;
        private int damage;
        private int direction;
        protected Animation beHustForwardAnim,beHustBackAnim;
        private int teamType;
        private long startTimeNoBeHurt;
        private long timeForNoBeHust;
        public ParticularObject(float x,float y,float width,float height,float mass,int blood,GameWorld gameWorld){
            super(x,y,gameWorld);
            setWidth(width);
            setHeight(height);
            setMass(mass);
            setBlood(blood);
            direction=RIGHT_DIR;
        }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }



    public static int getEnemyTeam() {
        return ENEMY_TEAM;
    }



    public static int getLeftDir() {
        return LEFT_DIR;
    }



    public static int getRightDir() {
        return RIGHT_DIR;
    }

    public static int getALIVE() {
        return ALIVE;
    }


    public static int getBEHURT() {
        return BEHURT;
    }


    public static int getFEY() {
        return FEY;
    }


    public static int getDEATH() {
        return DEATH;
    }



    public static int getNOBEHURT() {
        return NOBEHURT;
    }


    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Animation getBeHustForwardAnim() {
        return beHustForwardAnim;
    }

    public void setBeHustForwardAnim(Animation beHustForwardAnim) {
        this.beHustForwardAnim = beHustForwardAnim;
    }

    public Animation getBeHustBackAnim() {
        return beHustBackAnim;
    }

    public void setBeHustBackAnim(Animation beHustBackAnim) {
        this.beHustBackAnim = beHustBackAnim;
    }

    public int getTeamType() {
        return teamType;
    }

    public void setTeamType(int teamType) {
        this.teamType = teamType;
    }

    public long getStartTimeNoBeHurt() {
        return startTimeNoBeHurt;
    }

    public void setStartTimeNoBeHurt(long startTimeNoBeHurt) {
        this.startTimeNoBeHurt = startTimeNoBeHurt;
    }

    public long getTimeForNoBeHust() {
        return timeForNoBeHust;
    }

    public void setTimeForNoBeHust(long timeForNoBeHust) {
        this.timeForNoBeHust = timeForNoBeHust;
    }

    public static int getLeagueTeam() {
        return LEAGUE_TEAM;
    }


    public abstract void draw(Graphics2D g2);
    public abstract Rectangle getBoundForCollsionWithEnemy();
    public abstract void attack();
    public Rectangle getBoundCollisonWithMap(){
        Rectangle bound=new Rectangle();
        bound.x=(int)(getPosX()-(getWidth()/2));
        bound.y=(int)(getPosY()-(getHeight()/2));
        bound.width=(int)getWidth();
        bound.height=(int)getHeight();
        return bound;
    }
    public void  hurtingCallBack(){};
    public void beHust(int dameEat){
        setBlood(getBlood()-dameEat);
        state=BEHURT;
        hurtingCallBack();
    }
    @Override
    public void update(){
            switch(state){
                case ALIVE:
                ParticularObject object=getGameWorld().particularObjectManager.getCollisonWithEnemyObject(this);
                if(object!=null){
                    if(object.getDamage()>0){
                        beHust(object.getDamage());
                    }
                }
                    break;
                case BEHURT:
                    if(beHustBackAnim==null){
                        state=NOBEHURT;
                        startTimeNoBeHurt=System.nanoTime();
                        if(getBlood()==0){
                            state=FEY;
                        }
                    }
                    else{
                        beHustForwardAnim.update(System.nanoTime());
                        if(beHustForwardAnim.isLastFrame()){
                            beHustForwardAnim.reset();
                            state=NOBEHURT;
                            if(getBlood()==0){
                                state=FEY;
                                startTimeNoBeHurt=System.nanoTime();
                            }
                        }
                    }
                    break;
                case FEY:
                    state=DEATH;
                    break;
                case DEATH:
                    break;
                case NOBEHURT:
                    System.out.println("state=nobehurt");
                    if(System.nanoTime()-startTimeNoBeHurt>timeForNoBeHust){
                        state=ALIVE;
                    }
                    break;
            }

    }
    public void drawBoundForCollisonWithMap(Graphics2D g2){
        Rectangle rect=getBoundCollisonWithMap();
        g2.setColor(Color.BLUE);
        g2.drawRect(rect.x-(int)getGameWorld().camera.getPosX(),rect.y-(int)getGameWorld().camera.getPosY(),rect.width,rect.height);
    }
    public void drawBoundForCollisonWithEnemy(Graphics2D g2){
        Rectangle rect=getBoundForCollsionWithEnemy();
        g2.setColor(Color.RED);
        g2.drawRect(rect.x-(int)getGameWorld().camera.getPosX(),rect.y-(int)getGameWorld().camera.getPosY(),rect.width,rect.height);
    }
    public boolean isObjectOutOfCameraView(){
        if(getPosX() - getGameWorld().camera.getPosX() > getGameWorld().camera.getWidthView() ||
                getPosX() - getGameWorld().camera.getPosX() < -50
                ||getPosY() - getGameWorld().camera.getPosY() > getGameWorld().camera.getHeightView()
                ||getPosY() - getGameWorld().camera.getPosY() < -50)
            return true;
        else return false;
    }
}

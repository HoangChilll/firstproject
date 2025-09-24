package hoang.objects;

import hoang.userinterface.GameFrame;

import java.awt.*;
public class GameWorld {
    public MegaMan megaMan;
    public kakarot goku;
    public PhysicalMap physicalMap;
    public Camera camera;
    public BulletManager bulletManager;
    public BackgroundMap backgroundMap;
    public ParticularObjectManager particularObjectManager;
    public static final  int finalBossX=3600;
    public static final  int PAUSEGAME=0;
    public static final  int TUTORIAL=1;
    public static final  int GAMEPLAY=2;
    public static final  int GAMEWIN=3;
    public static final  int GAMEOVER=4;
    public static final  int INTROGAME=0;

    public static final  int MEETINGFINALBOSS=1;
    public int OPENINTROGAMEY=0;
    public int state =PAUSEGAME;
    public int previousState=state;
    public int tutorialState=INTROGAME;
    public int storyTutorial =0;
    public String[] texts1=new String[4];
    public  GameWorld(){
        megaMan=new MegaMan(300,300,this);
        megaMan.setTeamType(ParticularObject.LEAGUE_TEAM);
        goku=new kakarot(300,300,this);
        goku.setTeamType(ParticularObject.LEAGUE_TEAM);
        physicalMap=new PhysicalMap(0,0,this);
        camera=new Camera(0,0, GameFrame.SCREEN_WIDTH,GameFrame.SCREEN_HEIGHT,this);
        bulletManager=new BulletManager(this);
        backgroundMap=new BackgroundMap(0,0,this);
        particularObjectManager=new ParticularObjectManager(this);
        particularObjectManager.addObject(megaMan);
        initEnemies();
    }
    private void initEnemies(){
        ParticularObject redeye=new RedEyeDevil(1250,400,this);
        redeye.setDirection(ParticularObject.LEFT_DIR);
        redeye.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye);
    }
    public void update(){
        particularObjectManager.updateObject();
        bulletManager.updateObject();
        camera.update();
    }
    public void render(Graphics2D g2){
        backgroundMap.draw(g2);
       // physicalMap.draw(g2);
        bulletManager.draw(g2);
        particularObjectManager.draw(g2);


    }
    
}

package hoang.userinterface;
import hoang.effect.Animation;
import hoang.effect.CacheDataLoader;
import hoang.effect.FrameImage;
import hoang.objects.GameWorld;
import hoang.objects.MegaMan;
import hoang.objects.PhysicalMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
public class GamePanel extends JPanel implements Runnable, KeyListener {
    private Thread thread;
    private boolean isRunning;
    private inputManager inputManager;
    private BufferedImage buffImage;
    private Graphics2D buffG2D;
    public GameWorld gameWorld;
    public void paint(Graphics g){// override
        g.drawImage(buffImage,0,0,this);

    }
    public GamePanel() {
        gameWorld= new GameWorld();
        inputManager = new inputManager (gameWorld);
        buffImage=new BufferedImage(GameFrame.SCREEN_WIDTH,GameFrame.SCREEN_HEIGHT,BufferedImage.TYPE_INT_ARGB);
    }
    public void updateGame(){
        gameWorld.update();
    }
    public void renderGame(){
        if(buffImage==null) buffImage=new BufferedImage(GameFrame.SCREEN_WIDTH,GameFrame.SCREEN_HEIGHT,BufferedImage.TYPE_INT_ARGB);
        if(buffImage!=null){
            buffG2D=(Graphics2D)buffImage.getGraphics();
        }
        if(buffG2D!=null){
            buffG2D.setColor(Color.white);//tô màu
            //buffG2D.fillRect(0,0,GameFrame.SCREEN_WIDTH,GameFrame.SCREEN_HEIGHT);
        }
        //vẽ vô đây;
        //MegaMan.draw(buffG2D);
        gameWorld.render(buffG2D);
    }

    public void startGame(){
        if(thread==null){
            isRunning=true;
            thread=new Thread(this);
            thread.start();
        }
    }
    public void run() {
        int a = 1;
        long FPS=80;//framefersecond
        long period=1000*1000000/FPS;
        long beginTime=System.nanoTime();
        long sleepTime;
        while (isRunning) {//update ,render game
            updateGame();
            renderGame();
            repaint();
            long denltaTime=System.nanoTime()-beginTime;
            sleepTime=period-denltaTime;
            try {
                if(sleepTime>0){
                    Thread.sleep(sleepTime/1000000);
                }
                Thread.sleep(period/2000000);
            } catch (InterruptedException e){}
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        inputManager.processKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        inputManager.processKeyReleased(e.getKeyCode());
    }
}

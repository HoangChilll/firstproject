package hoang.userinterface;

import hoang.objects.GameWorld;
import hoang.objects.MegaMan;

import java.awt.event.KeyEvent;

public class inputManager {
    private GameWorld gameWorld;
    public inputManager(GameWorld gameWorld){
        this.gameWorld=gameWorld;
    }
    public void processKeyPressed(int keyCode){
        switch(keyCode) {
            case KeyEvent.VK_UP:
                System.out.println("you pressed up");
                break;
            case KeyEvent.VK_RIGHT:
                gameWorld.megaMan.setDirection(gameWorld.megaMan.RIGHT_DIR);
                gameWorld.megaMan.run();
                break;
            case KeyEvent.VK_LEFT:
                gameWorld.megaMan.setDirection(gameWorld.megaMan.LEFT_DIR);
                gameWorld.megaMan.run();
                break;
            case KeyEvent.VK_DOWN:

                break;
            case KeyEvent.VK_A:
                 gameWorld.megaMan.attack();
                break;
            case KeyEvent.VK_SPACE:
                gameWorld.megaMan.jump();
                break;
        }
    }
    public void processKeyReleased(int keyCode){
        switch(keyCode) {
            case KeyEvent.VK_UP:
                break;
            case KeyEvent.VK_RIGHT:
                if(gameWorld.megaMan.getSpeedX()>0){
                    gameWorld.megaMan.stopRun();
                }
                break;
            case KeyEvent.VK_LEFT:
                if(gameWorld.megaMan.getSpeedX()<0){
                    gameWorld.megaMan.stopRun();
                }
                break;
            case KeyEvent.VK_DOWN:
                gameWorld.megaMan.standUp();
                break;
            case KeyEvent.VK_A:
                break;
            case KeyEvent.VK_SPACE:
                break;
        }
    }

}

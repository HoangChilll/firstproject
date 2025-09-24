package hoang.objects;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ParticularObjectManager {
    protected List<ParticularObject> particularObjects;
    private GameWorld gameWorld;
    public ParticularObjectManager(GameWorld gameWorld){
        particularObjects= Collections.synchronizedList(new LinkedList<ParticularObject>());
        this.gameWorld=gameWorld;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }
    public void addObject(ParticularObject particularObject){
        synchronized (particularObjects){
            particularObjects.add(particularObject);
        }
    }
    public void removeObject(ParticularObject particularObject){
        synchronized (particularObjects){
            for(int id=0;id<particularObjects.size();id++ ){
                ParticularObject object=particularObjects.get(id);
                if(object==particularObject){
                    particularObjects.remove(id);
                }
            }
        }
    }
    public ParticularObject getCollisonWithEnemyObject(ParticularObject object){
        synchronized (particularObjects){
            for(int id=0;id<particularObjects.size();id++ ){
                ParticularObject objectInList=particularObjects.get(id);
                if(object.getTeamType()!=objectInList.getTeamType()&&object.getBoundForCollsionWithEnemy().intersects(objectInList.getBoundForCollsionWithEnemy())){

                   return objectInList;
                }
            }
        }
        return null;
    }
    public void updateObject(){
        synchronized (particularObjects){
            for(int i =0;i<particularObjects.size();i++) {
                ParticularObject object=particularObjects.get(i);
                if(!object.isObjectOutOfCameraView()) object.update();
                if(object.getState()==ParticularObject.DEATH){
                    particularObjects.remove(object);
                }
            }
        }
    }
    public void draw(Graphics2D g2){
        synchronized (particularObjects){
            for(ParticularObject object:particularObjects){
                    if(!object.isObjectOutOfCameraView()){
                        object.draw(g2);
                    }
            }
        }
    }
}

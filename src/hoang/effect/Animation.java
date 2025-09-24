package hoang.effect;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
    private String name;
    private boolean isRepeated;//biến kiểm soát lặp lại
    private ArrayList<FrameImage> frameImages;
    private int currentFrame;//ảnh hiện tại
    private ArrayList<Boolean>ignoreFrames;//mảng bỏ qua hành động
    private ArrayList<Double> delayFrames;//mảng thời gian delay giữa các hình
    private long beginTime;// biến thời gian bắt đầu animation
    private boolean drawRectFrame;//biến kiểm soát vẽ khung
    public Animation(){
        isRepeated=true;
        drawRectFrame=false;
        beginTime=0;
        currentFrame=0;
        frameImages=new ArrayList<FrameImage>();
        ignoreFrames=new ArrayList<Boolean>();
        delayFrames=new ArrayList<Double>();
    }
    public Animation(Animation animation){// tạo 1 bản sao của animation hiện tại
        beginTime=animation.beginTime;
        drawRectFrame=animation.drawRectFrame;
        currentFrame=animation.currentFrame;
        isRepeated=animation.isRepeated;
        frameImages=new ArrayList<FrameImage>();
        for(FrameImage d:animation.frameImages){
            frameImages.add(new FrameImage(d));
        }
        ignoreFrames=new ArrayList<Boolean>();
        for(Boolean d:animation.ignoreFrames){
            ignoreFrames.add(d);
        }
        delayFrames=new ArrayList<Double>();
        for(double d:animation.delayFrames){
            delayFrames.add(d);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsRepeated() {
        return isRepeated;
    }

    public void setIsRepeated(boolean repeated) {
        isRepeated = repeated;
    }

    public ArrayList<FrameImage> getFrameImages() {
        return frameImages;
    }

    public void setFrameImages(ArrayList<FrameImage> frameImages) {
        this.frameImages = frameImages;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        if(currentFrame>=0&&currentFrame<frameImages.size()){
            this.currentFrame=currentFrame;
        }
        else this.currentFrame=0;
    }

    public ArrayList<Boolean> getIgnoreFrames() {
        return ignoreFrames;
    }

    public void setIgnoreFrames(ArrayList<Boolean> ignoreFrames) {
        this.ignoreFrames = ignoreFrames;
    }

    public ArrayList<Double> getDelayFrames() {
        return delayFrames;
    }

    public void setDelayFrames(ArrayList<Double> delayFrames) {
        this.delayFrames = delayFrames;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public boolean getDrawRectFrame() {
        return drawRectFrame;
    }

    public void setDrawRectFrame(boolean drawRectFrame) {
        this.drawRectFrame = drawRectFrame;
    }
    public boolean isIgnoreFrame(int id){
        return ignoreFrames.get(id);
    }
    public void setIgnoreFrame(int id){
        if(id>=0&&id<ignoreFrames.size()){
            ignoreFrames.set(id,true);
        }
    }
    public void unIgnoreFrame(int id){
        if(id>=0&&id<ignoreFrames.size()){
            ignoreFrames.set(id,false);
        }
    }
    public void reset(){
        beginTime=0;
        currentFrame=0;
        for(int i =0;i<ignoreFrames.size();i++){
            ignoreFrames.set(i,false);
        }
    }
    public void add(FrameImage frameImage,double timeToNextFrame){
        ignoreFrames.add(false);
        frameImages.add(frameImage);
        delayFrames.add(timeToNextFrame);

    }
    public BufferedImage getCurrentImage(){
        return frameImages.get(currentFrame).getImage();
    }
    public void update(long currentTime){// kiểm tra để chuyển sang ảnh tiếp
        if(beginTime==0) beginTime=currentTime;
        else{
            if(currentTime-beginTime>delayFrames.get(currentFrame)){
                nextFrame();
                beginTime=currentTime;
            }
        }
    }
    private void nextFrame(){
        if(currentFrame>=frameImages.size()-1){
            if(isRepeated) currentFrame=0;
        }
        else currentFrame++;
        if(ignoreFrames.get(currentFrame)) nextFrame();
    }
    public boolean isLastFrame(){
        if(currentFrame==frameImages.size()-1) return true;
        return false;
    }
    public void flipAllImages(){// lật ngược hành động
       for(int i =0;i<frameImages.size();i++){
           BufferedImage image=frameImages.get(i).getImage();
           AffineTransform tx=AffineTransform.getScaleInstance(-1,1);
           tx.translate(-image.getWidth(),0);
           AffineTransformOp op=new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
           image=op.filter(image,null);
           frameImages.get(i).setImage(image);
       }
    }
    public void draw(int x, int y, Graphics2D g2){// vẽ hình
        BufferedImage image=getCurrentImage();
        g2.drawImage(image,x-image.getWidth()/2,y-image.getHeight()/2,null);
        if(drawRectFrame){
            g2.drawRect(x-image.getWidth()/2,y- image.getHeight()/2,image.getWidth(),image.getHeight());
        }
    }
}

package hoang.effect;
import java.awt.*;
import java.awt.image.*;
public class FrameImage {
    private String name;
    private BufferedImage image;
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    public FrameImage(FrameImage FrameImage){
        image=new BufferedImage(FrameImage.getImageWidth(),FrameImage.getImageHight(),FrameImage.getImage().getType());
        Graphics g=image.getGraphics();
        g.drawImage(FrameImage.getImage(),0,0,null);
    }

    public String getName() {
        return name;
    }
    public FrameImage(){
        name=null;
        image=null;
    }

    public void setName(String name) {
        this.name = name;
    }
    public FrameImage(String name, BufferedImage image){
        this.name=name;
        this.image=image;
    }
    public int getImageWidth(){
        return this.image.getWidth();
    }
    public int getImageHight(){
        return this.image.getHeight();
    }
    public void draw(Graphics2D g,int x,int y){
        g.drawImage(image,x-image.getWidth()/2,y-image.getHeight()/2,null);
    }
}

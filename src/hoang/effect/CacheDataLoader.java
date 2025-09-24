package hoang.effect;

import hoang.objects.BackgroundMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class CacheDataLoader {
    public static CacheDataLoader instance;
    private String frameFile="data/frame.txt";
    private String animationFile="data/animation.txt";
    private String physmapFile="data/phys_map.txt";
    private String backgroundMapFile="data/background_map.txt";
    private Hashtable<String,FrameImage> frameImages;
    private Hashtable<String,Animation> animations;
    private int[][] phys_map;
    private int[][] backgroundMap;
    private CacheDataLoader(){}
    public  static CacheDataLoader getInstance(){
        if(instance==null){
            instance=new CacheDataLoader();
        }
        return instance;
    }
    public void loadFrame() throws IOException{
        frameImages=new Hashtable<String,FrameImage>();
        FileReader fr=new FileReader(frameFile);
        BufferedReader br=new BufferedReader(fr);
        String line=null;
        if(br.readLine()==null){
            System.out.println("No data");
            throw new IOException();
        }
        else{
            fr=new FileReader(frameFile);
            br=new BufferedReader(fr);
            while((line=br.readLine()).equals(""));
            int n=Integer.parseInt(line);
            for(int i =0;i<n;i++){
                FrameImage frame=new FrameImage();
                while((line=br.readLine()).equals(""));
                frame.setName(line);
                while((line=br.readLine()).equals(""));
                String[] arc=line.split(" ");
                String path=arc[1];
                while((line=br.readLine()).equals(""));
                 arc=line.split(" ");
                int x=Integer.parseInt(arc[1]);
                while((line=br.readLine()).equals(""));
                arc=line.split(" ");
                int y=Integer.parseInt(arc[1]);
                while((line=br.readLine()).equals(""));
                 arc=line.split(" ");
                int w=Integer.parseInt(arc[1]);
                while((line=br.readLine()).equals(""));
                 arc=line.split(" ");
                int h=Integer.parseInt(arc[1]);
                BufferedImage imageData= ImageIO.read(new File(path));
                BufferedImage image=imageData.getSubimage(x,y,w,h);
                frame.setImage(image);
                instance.frameImages.put(frame.getName(),frame);
            }

        }
        br.close();
    }
    public void loadAnimation() throws IOException{
        animations=new Hashtable<String,Animation>();
        FileReader fr=new FileReader(animationFile);
        BufferedReader br=new BufferedReader(fr);
        String line=null;
        if(br.readLine()==null){
            System.out.println("No data");
            throw new IOException();
        }
        else{
            fr=new FileReader(animationFile);
            br=new BufferedReader(fr);
            while((line=br.readLine()).equals(""));
            int n=Integer.parseInt(line);
            for(int i =0;i<n;i++){
                Animation animation=new Animation();
                while((line=br.readLine()).equals(""));
                animation.setName(line);
                while((line=br.readLine()).equals(""));
                String[] arc=line.split(" ");
                for(int j=0;j<arc.length;j+=2) {
                    animation.add(getFrameImage(arc[j]), Double.parseDouble(arc[j + 1]));
                }
                instance.animations.put(animation.getName(),animation);
            }
        }
        br.close();
    }
    public FrameImage getFrameImage(String name){
        FrameImage frameImage =new FrameImage(instance.frameImages.get(name));
        return frameImage;
    }
    public Animation getAnimation(String name){
        Animation animation=new Animation(instance.animations.get(name));
        return animation;
    }
    public void loadData() throws IOException{
        loadFrame();
        loadAnimation();
        loadPhysicalMap();
        loadBackgroundMap();
    }
    public int [][] getPhysicalMap(){
        return instance.phys_map;
    }
    public void loadPhysicalMap() throws IOException{
        FileReader fr=new FileReader(physmapFile);
        BufferedReader br=new BufferedReader(fr);
        String line=null;
        line=br.readLine();
        int numberOfRows=Integer.parseInt(line);
        line =br.readLine();
        int numberOfColumns=Integer.parseInt(line);
        instance.phys_map=new int[numberOfRows][numberOfColumns];
        for(int i=0;i<numberOfRows;i++){
            line= br.readLine();
            String [] str=line.split(" ");
            for(int j=0;j<numberOfColumns;j++){
                instance.phys_map[i][j]=Integer.parseInt(str[j]);
            }
        }
        for(int i =0;i<numberOfRows;i++){
            for(int j=0;j<numberOfColumns;j++){
                System.out.print(" "+instance.phys_map[i][j]);
            }
            System.out.println();
        }
        br.close();
    }
    public int[][] getBackgroundMap(){
        return backgroundMap;
    }
    public void loadBackgroundMap() throws IOException{
        FileReader fr=new FileReader(backgroundMapFile);
        BufferedReader br=new BufferedReader(fr);
        String line=null;
        line=br.readLine();
        int numberOfRows=Integer.parseInt(line);
        line =br.readLine();
        int numberOfColumns=Integer.parseInt(line);
        instance.backgroundMap=new int[numberOfRows][numberOfColumns];
        for(int i=0;i<numberOfRows;i++){
            line= br.readLine();
            String [] str=line.split(" ");
            for(int j=0;j<numberOfColumns;j++){
                instance.backgroundMap[i][j]=Integer.parseInt(str[j]);
            }
        }
        for(int i =0;i<numberOfRows;i++){
            for(int j=0;j<numberOfColumns;j++){
                System.out.print(" "+instance.backgroundMap[i][j]);
            }
            System.out.println();
        }
        br.close();
    }
}

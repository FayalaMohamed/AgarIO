import java.awt.*;
import java.util.Random;
import java.awt.geom.Ellipse2D;

//creation du bot : un Ellipse2D.Double et une couleur
public class Bot {
    public Ellipse2D.Double bott;
    public Color couleur;
    public int x ,y, width, height;
    private Monde map;
    public int dx,dy;
    /*private boolean [] [] tPixel; //variables de notre essai de résolution du problème du bot
    private Point pixel;
    private Draw dessin;*/
    

    public Bot(int x, int y, int height, int width,Monde map/*,Draw dessin*/){
        this.map=map;
        Random a=new Random();
        this.x = x;
        //this.dessin=dessin;
        this.y = y;
        this.width= width;
        this.height= height;
        dx=a.nextInt(2);
        dy=2-dx;
        bott=new Ellipse2D.Double(this.x,this.y, height, width); // creation joueur (x,y,height,width) 
        couleur= new Color(a.nextInt(255),a.nextInt(255),a.nextInt(255)); // couleur RGB
    }

    // voir rapport problème bot
    
    /*public boolean [] [] tabPixel() throws AWTException{
        tPixel=new boolean [width][height];
        for(int i=0; i<width-1;i++){
            for(int j=0; j<height-1;j++){
                pixel=new Point (this.x+i,this.y+j);
                Color couleurPix = dessin.getCouleurUnPixel(this.x+i,this.y+j);
                if(couleurPix==couleur){
                    tPixel[i][j]=true;
                }else{
                    tPixel[i][j]=false;
                }
            }
        }
        return tPixel;
    }*/

    public void dessiner (Graphics g){
        g.fillOval(this.x, this.y, this.width, this.height);
    }

    public void tick(){
        bott.x+=dx+map.dx;
        bott.y+=dy+map.dy;
        this.x+=dx+map.dx;
        this.y+=dy+map.dy;
    }
    
}

import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

//creation du joueur : un Ellipse2D.Double et une couleur
public class Player {
    public Ellipse2D.Double playerr;
    public Color couleur;
    public int x ,y, width, height;
    

    public Player(int x, int y, int width, int height){
        Random a=new Random();
        this.x = x-width/2; // -width/2 et -height/2 : pour avoir le centre du joueur au centre de l'ecran
        this.y = y-height/2;
        this.width= width;
        this.height= height;
        playerr=new Ellipse2D.Double(this.x,this.y, width, height); // creation joueur (x,y,width,height) 
        couleur= new Color(a.nextInt(255),a.nextInt(255),a.nextInt(255)); // couleur RGB
    }

    public void dessiner (Graphics g){
        g.fillOval(this.x, this.y, this.width, this.height);
    }
    
}
import java.util.Random;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Special  {
    public Color couleurUp = new Color(0,255,0);
    public Color couleurDown = new Color(0,0,255);
    public Color couleurPoison = new Color(255,0,0);
    public Color couleurBouton = new Color(255,255,255);
    public ArrayList<Rectangle2D.Double> tabSpecial ;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public int nbSpecial;
    public float rayon;
    private Player joueur;
    
    //création d'un Special : une ArrayList contenant des Rectangle2D.Double et des couleurs caractérisant chaque type d'élément spécial
    // un carre est un Rectangle2D.Double de caractéristiques x,y,width,height avec x et y les coordonnées du coin nord est du carré 
    public Special(float rayon, int nbSpecial, Player joueur){
        this.nbSpecial=nbSpecial;
        this.rayon=rayon;
        this.joueur=joueur;
        tabSpecial= new ArrayList<Rectangle2D.Double>();
        Rectangle2D.Double unSpecial;
        for(int i=0;i<nbSpecial;i++){
            Random a=new Random();
            do{
                unSpecial=(new Rectangle2D.Double(a.nextInt(30*(int)screenSize.getWidth())-a.nextInt(30*(int)screenSize.getWidth()),
                                                     a.nextInt(30*(int)screenSize.getHeight())-a.nextInt(30*(int)screenSize.getHeight()), 
                                                     rayon, rayon));  // x,y,width,height
            }while(joueur.playerr.intersects(unSpecial.x, unSpecial.y, unSpecial.width, unSpecial.height));
            tabSpecial.add(unSpecial);
        }
    }
}
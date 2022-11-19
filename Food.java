import java.util.Random;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Food  {
    public ArrayList<Color> tabCouleur;
    public ArrayList<Ellipse2D.Double> tabNourriture ;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public int nbFood,rayonMax;
    private Player joueur ;
    
    //un attribut de type food contient un arrayList de couleurs et un ArrayList de cercles
    // un cercle est un Ellipse2D.Double de caractéristiques x,y,width,height avec x et y les coordonnées du coin nord est du "carré" contenant le cercle 
    public Food(int rayonMax, int nbFood, Player joueur){
		  this.nbFood=nbFood;
      this.rayonMax=rayonMax;
      this.joueur=joueur;      
      tabCouleur = new ArrayList<Color>();
      tabNourriture= new ArrayList<Ellipse2D.Double>();
      for(int i=0;i<nbFood;i++){
          Ellipse2D.Double nourriture;
          Random a=new Random();
          int rayon=a.nextInt(rayonMax-5)+5;
          tabCouleur.add(new Color(a.nextInt(255),a.nextInt(255),a.nextInt(255)));
          do{
            nourriture =new Ellipse2D.Double(a.nextInt(30*(int)screenSize.getWidth())-a.nextInt(30*(int)screenSize.getWidth()),
                                                  a.nextInt(30*(int)screenSize.getHeight())-a.nextInt(30*(int)screenSize.getHeight()), 
                                                  rayon, rayon);  // x,y,width,height
          }while(joueur.playerr.intersects(nourriture.x, nourriture.y, nourriture.width, nourriture.height));
          tabNourriture.add(nourriture);
      }
    }  
}


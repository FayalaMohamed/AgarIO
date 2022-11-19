import java.awt.*;


public class Monde {
    public int dx, dy;
    boolean poisonSimple,specialBouton,specialSpeedDown,specialSpeedUp;
    Food nourriture ;
    Special poisons ;
    Special specialsDown ;
    Special specialsBouton;
    Special specialsUp ;
    Player bott;
	public int dxRef=4;
    public int dyRef=4;
    private Player joueur;
    
    
	
    public Monde (boolean poisonSimple, boolean specialBouton , boolean specialSpeedUp , boolean specialSpeedDown, Player joueur){
        this.poisonSimple=poisonSimple;
        this.specialBouton=specialBouton;
        this.specialSpeedDown=specialSpeedDown;
        this.specialSpeedUp=specialSpeedUp;
        this.joueur=joueur;
        nourriture = new Food(20,25000,joueur);
        poisons = new Special(10,4000,joueur);
        specialsDown = new Special(10,3000,joueur);
        specialsBouton = new Special(10,3000,joueur);
        specialsUp = new Special(10,3000,joueur);
        dx=0;
        dy=0;
    }

    //methode permettant le mouvement de tous les éléments du monde 
    public void tick(){
        for(int i=0;i< nourriture.tabNourriture.size();i++){
			if(nourriture.tabNourriture.get(i)!=null){
			nourriture.tabNourriture.get(i).x +=dx;
			nourriture.tabNourriture.get(i).y +=dy;
			}
		}
		
        if(poisonSimple){
		    for(int i=0;i< poisons.tabSpecial.size();i++){
			    poisons.tabSpecial.get(i).x +=dx;
			    poisons.tabSpecial.get(i).y +=dy;
		    }
        }

        if(specialBouton){
            for(int i=0;i< specialsBouton.tabSpecial.size();i++){
			    specialsBouton.tabSpecial.get(i).x +=dx;
		    	specialsBouton.tabSpecial.get(i).y +=dy;
	    	}
        }

        if(specialSpeedDown){
            for(int i=0;i< specialsDown.tabSpecial.size();i++){
			    specialsDown.tabSpecial.get(i).x +=dx;
			    specialsDown.tabSpecial.get(i).y +=dy;
		    }
        }
        
        if(specialSpeedUp){
            for(int i=0;i< specialsUp.tabSpecial.size();i++){
			    specialsUp.tabSpecial.get(i).x +=dx;
		    	specialsUp.tabSpecial.get(i).y +=dy;
	    	}
        }

    }

    // permet de dessiner les éléments du monde
    // pour dessiner on choisit la couleur de ce qu'on va dessiner avec g2.setColor()
    //puis on fait g2.fill() pour colrier les pixels nécessaires au dessin
    public void dessinerMonde( Graphics g, Player joueur){
        Graphics2D g2 =(Graphics2D) g;

        for(int i=0;i<nourriture.tabNourriture.size();i++){
            g2.setColor(nourriture.tabCouleur.get(i));
            g2.fill(nourriture.tabNourriture.get(i));  
        }

        if(poisonSimple){
            g2.setColor(poisons.couleurPoison);
            for(int i=0;i<poisons.tabSpecial.size();i++){
                g2.fill(poisons.tabSpecial.get(i));
            }
        }

        if(specialBouton){
            g2.setColor(specialsBouton.couleurBouton);
            for(int i=0;i<specialsBouton.tabSpecial.size();i++){
                g2.fill(specialsBouton.tabSpecial.get(i));
            }
        } 


        if(specialSpeedDown){
            g2.setColor(specialsDown.couleurDown);
            for(int i=0;i<specialsDown.tabSpecial.size();i++){
                g2.fill(specialsDown.tabSpecial.get(i));
            }
        } 

        if(specialSpeedUp){
            g2.setColor(specialsUp.couleurUp);
            for(int i=0;i<specialsUp.tabSpecial.size();i++){
                g2.fill(specialsUp.tabSpecial.get(i)); 
            }
        } 

    }
    
}

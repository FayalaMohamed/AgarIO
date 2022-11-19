import java.awt.event.*;
import java.awt.Color ;

// l'ecouteur des boutons de couleurs dans la fenetre couleur
public class EcouteurCouleur implements ActionListener  {
    FenetreCouleurPremium fen;
    Color couleurChoix;
    

    public EcouteurCouleur(FenetreCouleurPremium fen, Color couleur) {
        this.fen=fen;
        couleurChoix=couleur;
    }

    
    public void actionPerformed(ActionEvent e) {
       fen.couleur=couleurChoix;
       fen.valide.setBackground(couleurChoix);
       System.out.println(couleurChoix);
       fen.fen.changerCouleur(couleurChoix);
       //fen.ExitAction.getInstance().actionPerformed(null);
       
    }    
}
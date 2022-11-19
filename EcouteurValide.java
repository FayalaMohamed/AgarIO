import java.awt.event.*;
import java.awt.Color ;

// ecouteur de validation du choix de la couleur
public class EcouteurValide implements ActionListener  {
    FenetreCouleurPremium fen;
    Color couleurChoix;


    public EcouteurValide(FenetreCouleurPremium fenetreCouleur, Color couleur) {
        fen=fenetreCouleur;
        couleurChoix=couleur;
    }
   
    public void actionPerformed(ActionEvent e) {
       //fen.fen.changerCouleur(couleurChoix);
       fen.dispose();
       
    } 
}

import java.awt.event.*;

//import javafx.scene.paint.Color;

//ouvre une fenetre couleur
public class EcouteurSelection implements ActionListener  {
    FenetreMenu fenmenu;

    public EcouteurSelection(FenetreMenu fenetreMenufmenu) {
        fenmenu=fenetreMenufmenu;
        
    }
    
    public void actionPerformed(ActionEvent e) {
       new FenetreCouleurPremium(fenmenu);
    }
    
}
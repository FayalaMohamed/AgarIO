import java.awt.*;
import javax.swing.*;

public class ButtonGrid extends JPanel {
    private int nbCouleurs = 20;
    private JButton boutton;
    private Color[] tableauCouleur = new Color[nbCouleurs];
    FenetreCouleurPremium fen;
    

    public void init(FenetreCouleurPremium fen) {
        this.fen=fen;
        setLayout(new GridLayout(2,10));
        tableauCouleur[0]=new Color(205, 97, 85);
        tableauCouleur[1]=new Color(236, 112, 99 );
        tableauCouleur[2]=new Color(175, 122, 197 );
        tableauCouleur[3]=new Color(165, 105, 189 );
        tableauCouleur[4]=new Color(84, 153, 199);
        tableauCouleur[5]=new Color(93, 173, 226);
        tableauCouleur[6]=new Color(72, 201, 176);
        tableauCouleur[7]=new Color(69, 179, 157);
        tableauCouleur[8]=new Color(82, 190, 128 );
        tableauCouleur[9]=new Color(88, 214, 141);
        tableauCouleur[10]=new Color(244, 208, 63);
        tableauCouleur[11]=new Color(245, 176, 65);
        tableauCouleur[12]=new Color(235, 152, 78 );
        tableauCouleur[13]=new Color(220, 118, 51);
        tableauCouleur[14]=new Color(240, 243, 244);
        tableauCouleur[15]=new Color(202, 207, 210);
        tableauCouleur[16]=new Color(170, 183, 184 );
        tableauCouleur[17]=new Color(153, 163, 164);
        tableauCouleur[18]=new Color(93, 109, 126);
        tableauCouleur[19]=new Color(86, 101, 115);

        

        for(int i=0;i<nbCouleurs;i++){
            boutton =new JButton("");
            boutton.setForeground(tableauCouleur[i]);
            boutton.setBackground(tableauCouleur[i]);
            boutton.addActionListener(new EcouteurCouleur(fen,tableauCouleur[i]));
            add(boutton);
        }
         
    }
    
 }
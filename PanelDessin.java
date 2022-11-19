import java.awt.*;
import javax.swing.*;

public class PanelDessin extends JPanel {
	
	private  Color couleur;
	private  FenetreMenu fen;

	// Redefinition de la méthode paintComponent pour changer le comportement d'affichage
	public PanelDessin(FenetreMenu fen){
		this.fen=fen;
		couleur=fen.couleur;
	}
	 
	
	public void paintComponent(Graphics g){
		// Options pour que ce soit plus joli
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh =new RenderingHints( 
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON
		);
		g2.setRenderingHints(rh);
		// Dessing d'un cercle rempli
		g.setColor(fen.couleur);
		System.out.println(fen.couleur);
		g.fillOval(487,140, 120, 120);
	}
	
	public void setColor(){
		couleur = fen.couleur;
	}
}

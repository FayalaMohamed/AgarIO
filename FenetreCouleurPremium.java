import java.awt.*;
import javax.swing.*;

//création de la fenetre couleur qui permet au joueur de choisir la couleur
public class FenetreCouleurPremium extends JFrame {
	public static final String ExitAction = null;
    
	

	private JLabel label ;

	private ButtonGrid bouttons ;
	public JButton valide ;
	public Color couleur;
	public FenetreMenu fen;
	


	public FenetreCouleurPremium(FenetreMenu fen) {
		super("Choix du Skin");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());		
		setSize(1900, 1000);
		this.fen=fen;
						
		
		label = new JLabel("Choix de la couleur du joueur");
		bouttons =new ButtonGrid();
		valide = new JButton("Valider");
        JPanel panelSelecCouleur = new PanneauFond("fondSelecCouleur.jpg");

		
		
		
		bouttons.setPreferredSize(new Dimension(750,200));

		label.setFont(new Font("Garamond", Font.BOLD, 30));
        label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(JLabel.CENTER);
		valide.setFont(new Font("Garamond", Font.BOLD, 30));
		bouttons.init(this);
		
		
		valide.addActionListener(new EcouteurValide(this,couleur));
        valide.setBackground(Color.WHITE);

		
        Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(200));
		box.add(label);
    	box.add(Box.createVerticalStrut(10));
    	box.add(bouttons);
    	box.add(Box.createVerticalStrut(10));
        //box.add(Box.createHorizontalStrut(50));
    	box.add(valide);
		panelSelecCouleur.add(box);

		add(panelSelecCouleur);
		
		
		setVisible(true);
		setResizable(false);
	}
}

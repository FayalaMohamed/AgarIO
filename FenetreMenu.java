import java.awt.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class FenetreMenu extends JFrame{
	//Déclaration des attributs de la fenetre
	private JPanel panelHaut;
	private JPanel panelCentre;
	private JPanel panelDroit;
	private JPanel panelBas;
	
	private JLabel nomJeu;
	private JLabel nomAvatar;
	private JLabel difficulte;
	
	private JButton niveau1;
	private JButton niveau2;
	private JButton niveau3;
	private JButton niveau4;
	private JButton selecCouleur;
	
	private JTextField avatar;
	private JTextArea scores;

	private Box box1 ;
	
	public Color couleur=new Color(255,255,255);

	public String pseudo;
	
    
	public FenetreMenu() throws IOException{
		super("MOMO.IO");
		pseudo="";
		setSize(1100,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Gson gson = new Gson();
		Reader reader = Files.newBufferedReader(Paths.get("users.json"));
		List<User> Users = new Gson().fromJson(reader, new TypeToken<List<User>>() {}.getType()); //// crée une liste de User contenus dans le fichier lu
		LinkedList<User> newUsersDiff1 = new LinkedList<User>();
		LinkedList<User> newUsersDiff2 = new LinkedList<User>();
		LinkedList<User> newUsersDiff3 = new LinkedList<User>();
		LinkedList<User> newUsersDiff4 = new LinkedList<User>();
		LinkedList<User> top4 = new LinkedList<User>();

		// Diviser la liste de User Users en 4 listes selon la difficulté

		for(int i =0; i<Users.size();i++){
			if (Users.get(i).getDifficulte()==1){
				newUsersDiff1.add(Users.get(i));
			} else if(Users.get(i).getDifficulte()==2){
				newUsersDiff2.add(Users.get(i));
			} else if(Users.get(i).getDifficulte()==3) {
				newUsersDiff3.add(Users.get(i));
			} else {
				newUsersDiff4.add(Users.get(i));
			}
		}

		
		top4.add(this.getScoreMax(newUsersDiff1));
		top4.add(this.getScoreMax(newUsersDiff2));
		top4.add(this.getScoreMax(newUsersDiff3));
		top4.add(this.getScoreMax(newUsersDiff4));
        
		
		//Définition des autres éléments
		panelHaut = new JPanel();
		panelCentre = new JPanel(new FlowLayout(0,420,10));
		panelDroit = new JPanel();
		panelBas = new JPanel(new BorderLayout());
        PanneauFond panelFond = new PanneauFond("fondagario.jpg");
		panelFond.setLayout(new BorderLayout());
		
		
		difficulte = new JLabel ("   Difficulté :");
        niveau1 = new JButton("Niveau 1");
		niveau2 = new JButton("Niveau 2");
		niveau3 = new JButton("Niveau 3");
		niveau4 = new JButton("Niveau 4");
		
		nomJeu = new JLabel ("MOMO.IO");
		nomAvatar = new JLabel ("Nom de l'avatar");
		
		selecCouleur = new JButton("Sélection couleur");
		selecCouleur.addActionListener(new EcouteurSelection(this));
		
		avatar = new JTextField (15);
		scores = new JTextArea ("Meilleurs scores : \n Niveau 1 : "+ top4.get(0).getScore()+ " par "+ top4.get(0).toString() + "\n Niveau 2 : "+ top4.get(1).getScore()+" par "+ top4.get(1).toString()+ "\n Niveau 3 : "+ top4.get(2).getScore()+" par "+ top4.get(2).toString()+ "\n Niveau 4 : "+ top4.get(3).getScore()+" par "+ top4.get(3).toString());
		
		
		
		//Changement de couleur des éléments
		selecCouleur.setBackground(new Color (255,224,0));
		scores.setBackground(new Color (78,172,203));
		niveau1.setBackground(new Color (255,224,0));
		niveau2.setBackground(new Color (78,172,203));
		niveau3.setBackground(new Color (255,224,0));
		niveau4.setBackground(new Color (78,172,203));
		
		nomJeu.setForeground(new Color (78,172,203));
		difficulte.setForeground(new Color (78,172,203));
		nomAvatar.setForeground(new Color (78,172,203));

        panelHaut.setOpaque(false);
		panelCentre.setOpaque(false);
		panelBas.setOpaque(false);
		panelDroit.setOpaque(false);
		
		//Changement de l'écriture des éléments
		
		Font f = new Font("Garamond", Font.BOLD, 18);
		Font f1 = new Font("Garamond", Font.BOLD, 34);
		
		
		selecCouleur.setFont(f);
		scores.setFont(f);
		nomAvatar.setFont(f);
		difficulte.setFont(f);
		niveau1.setFont(f);
		niveau2.setFont(f);
		niveau3.setFont(f);
		niveau4.setFont(f);
		
		nomJeu.setFont(f1);
		
		
		//ajout des éléments
		
		panelHaut.add(nomJeu);
		
		box1 = Box.createVerticalBox();
		box1.add(Box.createVerticalStrut(200));
		box1.add(nomAvatar);
		box1.add(Box.createVerticalStrut(10));
		box1.add(avatar);
		box1.add(Box.createVerticalStrut(10));
		box1.add(Box.createHorizontalStrut(70));
		box1.add(selecCouleur);
		panelCentre.add(box1);
		
		panelBas.add(scores, BorderLayout.WEST);
		
		panelFond.add(panelHaut, BorderLayout.NORTH);
		panelFond.add(panelCentre, BorderLayout.CENTER);
		panelFond.add(panelDroit, BorderLayout.EAST);
		panelFond.add(panelBas, BorderLayout.SOUTH);
		pseudo=avatar.getText();
		niveau1.addActionListener(new EcouteurDifficulte(false,false,false,false,this));
		niveau2.addActionListener(new EcouteurDifficulte(true,false,false,false,this));
		niveau3.addActionListener(new EcouteurDifficulte(true ,true,false,false,this));
		niveau4.addActionListener(new EcouteurDifficulte(true ,true,true,true,this));
		
		//Pour ne pas permettre au joueur de lancer une parte si son pseudo est vide
		niveau1.setEnabled(false);
		niveau2.setEnabled(false);
		niveau3.setEnabled(false);
		niveau4.setEnabled(false);
		avatar.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			  changed();
			}
			public void removeUpdate(DocumentEvent e) {
			  changed();
			}
			public void insertUpdate(DocumentEvent e) {
			  changed();
			}
		  
			public void changed() {
			   if (avatar.getText().isBlank()){
				 niveau1.setEnabled(false);
				 niveau2.setEnabled(false);
				 niveau3.setEnabled(false);
				 niveau4.setEnabled(false);
			   }
			   else {
				 niveau1.setEnabled(true);
				 niveau2.setEnabled(true);
				 niveau3.setEnabled(true);
				 niveau4.setEnabled(true);
			  }
		  
			}
		  });

		Box box2 = Box.createVerticalBox();
		box2.add(Box.createVerticalStrut(50));
		box2.add(difficulte);
    	box2.add(Box.createVerticalStrut(10));
    	box2.add(niveau1);
    	box2.add(Box.createVerticalStrut(10));
    	box2.add(niveau2);
    	box2.add(Box.createVerticalStrut(10));
    	box2.add(niveau3);
    	box2.add(Box.createVerticalStrut(10));
		box2.add(niveau4);
		panelDroit.add(box2);
		panelDroit.add(Box.createHorizontalStrut(50));
		
		JPanel pannelDessin = new PanelDessin(this);
		this.add(pannelDessin);

		this.show();
		add(panelFond);
		this.setVisible(true);
		setResizable(false);
		
	}

    public User getScoreMax(LinkedList<User> u){
		User UserMeilleurScore=u.get(0);
		for (int i =1; i<u.size();i++){
			if(u.get(i).isSup(UserMeilleurScore)){
				UserMeilleurScore=u.get(i);
			}
		}
		return UserMeilleurScore;
	}
	
	public void changerCouleur(Color nouvelleCouleur){
		System.out.println(nouvelleCouleur+"est validée");
		this.couleur=nouvelleCouleur;
		this.repaint();
	}

    public void lancerJeu(boolean poisonSimple, boolean specialBouton , boolean specialSpeedUp , boolean specialSpeedDown){
		new AgarIO(poisonSimple,specialBouton,specialSpeedUp,specialSpeedDown,couleur, getAvatar());
	}

    public String getAvatar(){
        return avatar.getText();
    }

}

import java.awt.event.*;
import java.awt.*;
import java.util.Random;
import java.awt.geom.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.*;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

public class Draw extends JPanel implements ActionListener, KeyListener {
    
    //variables boolÃ©ennes qui dÃ©terminent le niveau du jeu
    boolean poisonSimple,specialBouton,specialSpeedUp,specialSpeedDown;
    //Refresh rate
    Timer t = new Timer(1,this);
    
    String avatartxt;
    //dimensions de l'Ã©cran
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //crÃ©ation du joueur au centre de la fenetre
    Monde map ;

    Player joueur;
    Bot bot;

    public int dx  =0;
    public int dy  =0;
    public int score =0;
    
    private long time;
    
    private boolean lost=false;
    
    private Color couleurAvatar;

    private AgarIO fen;

    private boolean specialBtnOn =false;

    public void drawing(boolean poisonSimple, boolean specialBouton , boolean specialSpeedUp , boolean specialSpeedDown, Color couleurAvatar, AgarIO fen, String avatartxt){ 
        this.fen=fen;
        this.poisonSimple=poisonSimple;
        this.specialBouton=specialBouton;
        this.specialSpeedDown=specialSpeedDown;
        this.specialSpeedUp=specialSpeedUp;
        this.avatartxt=avatartxt;
        this.couleurAvatar= couleurAvatar;
        this.setBackground(Color.BLACK);
        joueur = new Player((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2, 100, 100); 
        map = new Monde (poisonSimple,specialBouton,specialSpeedUp,specialSpeedDown,joueur); // crÃ©ataion du monde qui contient nourriture + spÃ©cial
        
        do{
            Random a=new Random();
            bot=new Bot(a.nextInt((int)screenSize.getWidth()),a.nextInt((int)screenSize.getHeight()),joueur.height+8,joueur.width+8,map);
        }while(joueur.playerr.intersects(bot.bott.x, bot.bott.y, bot.bott.width, bot.bott.height));
        
        addKeyListener(this); //ajout de l'Ã©couteur du clavier
        setFocusable(true);
        t.start();
        time=System.nanoTime(); //temps du dÃ©but de la partie

    }

    public int getDifficulte(){ 
        // dÃ©termine le niveau de difficultÃ© 
        int diff=0;
        if (poisonSimple==false && specialBouton==false && specialSpeedDown==false){
            diff=1;
        }
        else if (poisonSimple==true && specialBouton==false && specialSpeedDown==false){
            diff=2;
        }
        else if (poisonSimple==true && specialBouton==true && specialSpeedDown==false){
            diff=3;
        }
        else if (poisonSimple==true && specialBouton==true && specialSpeedDown==true){
            diff=4;
        }
        return diff; 
    }

    public void actionPerformed (ActionEvent e){
		map.tick();
        bot.tick();
        repaint();
    }

    public void json() throws JsonIOException, IOException{
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("users.json")); //lire le fichier qui contient les Users
        List<User> Users = new Gson().fromJson(reader, new TypeToken<List<User>>() {}.getType()); // crÃ©e une liste de User contenus dans le fichier lu
        User u = new User(this.avatartxt, this.getDifficulte(), this.score); //crÃ©e un User de la partie en cours
        
        //vÃ©rifie si le User existe dÃ©jÃ 
        boolean memeUser=false;
        for (int i = 0; i<Users.size(); i++){
            if (Users.get(i).toString().equals(u.toString()) && Users.get(i).getDifficulte()==u.getDifficulte() && Users.get(i).getScore()==u.getScore()){
                memeUser=true;
            }
        }

        //ajout de l'User de la partie Ã  la liste
        if (memeUser==false){
            Users.add(u);
        }

        //Ã©crire les Users de la liste dans le fichier
        Writer writer = new FileWriter("Users.json");
        new Gson().toJson(Users, writer);
        writer.close();
    }
    
    /*@Override
    //Mouvement du joueur avec la souris
	public void actionPerformed(ActionEvent e) {
			Point mousePosition=getMousePosition();
			int x = MouseInfo.getPointerInfo().getLocation().x;
			int y = MouseInfo.getPointerInfo().getLocation().y;
			//if(mousePosition==null)return;
			/*double dx = mousePosition.x -joueur.playerr.x - joueur.width/2;
			double dy = mousePosition.y -y- joueur.playerr.y - joueur.height/2;
			

			if(dx*dx+dy*dy >12){
				double angle=Math.atan2(dy, dx);
				if(mousePosition.getX()<joueur.playerr.getBounds().getMinX()||mousePosition.getX()>joueur.playerr.getBounds().getMaxX()||mousePosition.getY()<
						joueur.playerr.getBounds().getMinY()||mousePosition.getY()>joueur.playerr.getBounds().getMaxY()){
					joueur.playerr.x+=(int)(joueur.Velocity*Math.cos(angle));
					joueur.playerr.y+=(int)(joueur.Velocity*Math.sin(angle));
					//Point view = new Point((int)joueur.playerr.x-WIDTH/2,(int)joueur.playerr.y-HEIGHT/2);
					//vPort.setViewPosition(view);
				}
			}		
			repaint();	
		
	}
	
	public void mouseClicked(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}*/
	
	
    // DÃ©palcement du joueur en appuyant sur les boutons
    //2 cas : cas normal + cas si specialBtn est activÃ© 
    // mouvement possible avec les flÃ©ches ou QSDZ
    // SPACE met en pause le jeu ou ferme la fenetre du jeu si lost==true et ouvre une nouvelle fenetre menu
    public void keyPressed(KeyEvent k){
        if(!specialBtnOn){
            switch(k.getKeyCode()){
                case KeyEvent.VK_S:
                map.dy = 0;
                map.dx = 0;
                map.dy = -map.dyRef;
                break; 
                case KeyEvent.VK_Z:
                map.dy = 0;
                map.dx = 0;
                map.dy = map.dyRef;
                break; 
                case KeyEvent.VK_D:
                map.dy = 0;
                map.dx = 0;
                map.dx = -map.dxRef;
                break; 
                case KeyEvent.VK_Q:
                map.dy = 0;
                map.dx = 0;
                map.dx = map.dxRef;
                break; 
                case KeyEvent.VK_DOWN:
                map.dy = 0;
                map.dx = 0;
                map.dy = -map.dyRef;
                break; 
                case KeyEvent.VK_UP:
                map.dy = 0;
                map.dx = 0;
                map.dy = map.dyRef;
                break; 
                case KeyEvent.VK_RIGHT:
                map.dy = 0;
                map.dx = 0;
                map.dx = -map.dxRef;
                break; 
                case KeyEvent.VK_LEFT:
                map.dy = 0;
                map.dx = 0;
                map.dx = map.dxRef;
                break;
                case KeyEvent.VK_SPACE:
                if(lost){
                    fen.dispose();
                    try {
                        new FenetreMenu();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else{
                    map.dy = 0;
                    map.dx = 0;
                    if(bot.dx!=0){bot.dx=0;}
                    if(bot.dy!=0){bot.dy=0;}

                }
                break;
            }
        }else{
            switch(k.getKeyCode()){
                case KeyEvent.VK_S:
                map.dy = 0;
                map.dx = 0;
                map.dy = map.dyRef;
                break; 
                case KeyEvent.VK_Z:
                map.dy = 0;
                map.dx = 0;
                map.dy = -map.dyRef;
                break; 
                case KeyEvent.VK_D:
                map.dy = 0;
                map.dx = 0;
                map.dx = map.dxRef;
                break; 
                case KeyEvent.VK_Q:
                map.dy = 0;
                map.dx = 0;
                map.dx = -map.dxRef;
                break; 
                case KeyEvent.VK_DOWN:
                map.dy = 0;
                map.dx = 0;
                map.dy = map.dyRef;
                break; 
                case KeyEvent.VK_UP:
                map.dy = 0;
                map.dx = 0;
                map.dy = -map.dyRef;
                break; 
                case KeyEvent.VK_RIGHT:
                map.dy = 0;
                map.dx = 0;
                map.dx = map.dxRef;
                break; 
                case KeyEvent.VK_LEFT:
                map.dy = 0;
                map.dx = 0;
                map.dx = -map.dxRef;
                break;
                case KeyEvent.VK_SPACE:
                if(lost){
                    fen.dispose();
                    try {
                        new FenetreMenu();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else{
                    map.dy = 0;
                    map.dx = 0;
                    if(bot.dx!=0){bot.dx=0;}
                    if(bot.dy!=0){bot.dy=0;}
                }
                break;
            }
        }
        
    }  
    
    //NÃ©cessaires Ã  implements KeyListener
    public void keyReleased(KeyEvent k){}
    public void keyTyped(KeyEvent k){}
	
    // En cas de collision du joueur avec un Ã©lÃ©ment de la map 
    // on efface cet Ã©lÃ©ment et on le remplace par un nouveau pour avoir toujours le mÃªme nombre d'Ã©lÃ©ments et avoir un jeu infini
    // on active l'effet de la nourriture (on augmente le rayon du joueur) et des Ã©lÃ©ments spÃ©ciaux
	public void checkcollision (Graphics g) throws AWTException{
        if(joueur.playerr.intersects(bot.bott.x, bot.bott.y, bot.bott.width, bot.bott.height) ){
            Random a=new Random();
            int rayonBot=bot.width;
            int rayonJoueur=joueur.width;
            if(rayonJoueur>=rayonBot){
                do{ 
                    int drayon=a.nextInt(16)-8;
                    bot=new Bot(a.nextInt((int)screenSize.getWidth()),a.nextInt((int)screenSize.getHeight()), joueur.height+drayon,joueur.width+drayon,map);
                }while(joueur.playerr.intersects(bot.bott.x, bot.bott.y, bot.bott.width, bot.bott.height) );
			    score +=rayonBot;
                joueur.height=joueur.height+rayonBot/6;
                joueur.width=joueur.width+rayonBot/6;
                joueur.playerr.height=joueur.playerr.height+rayonBot/6;
                joueur.playerr.width=joueur.playerr.width+rayonBot/6;
                joueur.playerr.x -= rayonBot/12; 
                joueur.playerr.y -= rayonBot/12;
                joueur.x -= rayonBot/12;
                joueur.y -= rayonBot/12;
			    repaint();
            }else{
                lost=true;
            }
        }

        for(int i=0; i<map.nourriture.tabNourriture.size(); i++){
            if( joueur.playerr.intersects(map.nourriture.tabNourriture.get(i).x, map.nourriture.tabNourriture.get(i).y, map.nourriture.tabNourriture.get(i).width, map.nourriture.tabNourriture.get(i).height)){
				Random a=new Random();
                int rayon=(int) map.nourriture.tabNourriture.get(i).width;
                int nouveauRayon =a.nextInt(map.nourriture.rayonMax);
                Ellipse2D.Double nourr ;
                do{ //pour ne pas permettre de crÃ©er une nourriture qui intersects directement avec le joueur
                    nourr=new Ellipse2D.Double(a.nextInt(60*(int)screenSize.getWidth())-a.nextInt(60*(int)screenSize.getWidth()),
                        a.nextInt(60*(int)screenSize.getHeight())-a.nextInt(60*(int)screenSize.getHeight()), 
                        nouveauRayon,  nouveauRayon);
                }while(joueur.playerr.intersects(nourr.x, nourr.y, nourr.width, nourr.height));
                map.nourriture.tabNourriture.set(i,nourr);
				map.nourriture.tabCouleur.set(i,new Color(a.nextInt(255),a.nextInt(255),a.nextInt(255)));
				score +=rayon;
                joueur.height=joueur.height+rayon/8;
                joueur.width=joueur.width+rayon/8;
                joueur.playerr.height=joueur.playerr.height+rayon/8;
                joueur.playerr.width=joueur.playerr.width+rayon/8;
                joueur.playerr.x -= rayon/16; // pour avoir un affichage du joueur au centre de l'Ã©cran mÃªme aprÃ¨s avoir mangÃ©
                joueur.playerr.y -= rayon/16;
                joueur.x -= rayon/16;
                joueur.y -= rayon/16;
				repaint();
				break;
            }
        }
        
        if(poisonSimple){
            for(int i=0; i<map.poisons.tabSpecial.size(); i++){
                if( joueur.playerr.intersects(map.poisons.tabSpecial.get(i).x, map.poisons.tabSpecial.get(i).y, map.poisons.tabSpecial.get(i).width, map.poisons.tabSpecial.get(i).height)){
                    map.poisons.tabSpecial.remove(i);
                    lost=true;
                    break;
                }
            }
        }
        
        if(specialBouton){
            for(int i=0; i<map.specialsBouton.tabSpecial.size(); i++){
                if( joueur.playerr.intersects(map.specialsBouton.tabSpecial.get(i).x, map.specialsBouton.tabSpecial.get(i).y, map.specialsBouton.tabSpecial.get(i).width, map.specialsBouton.tabSpecial.get(i).height)){
                    Random a=new Random();
                    Rectangle2D.Double unSpecial ;
                    do{
                        unSpecial=new Rectangle2D.Double(a.nextInt(60*(int)screenSize.getWidth())-a.nextInt(60*(int)screenSize.getWidth()),
                            a.nextInt(60*(int)screenSize.getHeight())-a.nextInt(60*(int)screenSize.getHeight()),
                            map.specialsBouton.rayon, map.specialsBouton.rayon);
                    }while(joueur.playerr.intersects(unSpecial.x, unSpecial.y, unSpecial.width, unSpecial.height));
                    map.specialsBouton.tabSpecial.set(i,unSpecial);
                    specialBtnOn=!specialBtnOn;
                    repaint();
                    break;
                }
            }
        }
        
        if(specialSpeedDown){
            for(int i=0; i<map.specialsDown.tabSpecial.size(); i++){
                if( joueur.playerr.intersects(map.specialsDown.tabSpecial.get(i).x, map.specialsDown.tabSpecial.get(i).y, map.specialsDown.tabSpecial.get(i).width, map.specialsDown.tabSpecial.get(i).height)){
                    Random a=new Random();
                    Rectangle2D.Double unSpecial ;
                    do{
                        unSpecial=new Rectangle2D.Double(a.nextInt(60*(int)screenSize.getWidth())-a.nextInt(60*(int)screenSize.getWidth()),
                            a.nextInt(60*(int)screenSize.getHeight())-a.nextInt(60*(int)screenSize.getHeight()),
                            map.specialsDown.rayon, map.specialsDown.rayon);
                    }while(joueur.playerr.intersects(unSpecial.x, unSpecial.y, unSpecial.width, unSpecial.height));
                    map.specialsDown.tabSpecial.set(i,unSpecial);
                    
                    specialDownEaten();
                    repaint();
                    break;
                }
            }
        }

        if(specialSpeedUp){
            for(int i=0; i<map.specialsUp.tabSpecial.size(); i++){
                if( joueur.playerr.intersects(map.specialsUp.tabSpecial.get(i).x, map.specialsUp.tabSpecial.get(i).y, map.specialsUp.tabSpecial.get(i).width, map.specialsUp.tabSpecial.get(i).height)){
                    Random a=new Random();
                    Rectangle2D.Double unSpecial ;
                    do{
                        unSpecial=new Rectangle2D.Double(a.nextInt(60*(int)screenSize.getWidth())-a.nextInt(60*(int)screenSize.getWidth()),
                            a.nextInt(60*(int)screenSize.getHeight())-a.nextInt(60*(int)screenSize.getHeight()),
                            map.specialsUp.rayon, map.specialsUp.rayon);
                    }while(joueur.playerr.intersects(unSpecial.x, unSpecial.y, unSpecial.width, unSpecial.height));
                    map.specialsUp.tabSpecial.set(i,unSpecial);
                    
                    specialUpEaten();
                    repaint();
                    break;
                }
            }
        }
    }



    //recréation du bot quand il sort du cadre de jeu
    public void BotSort(){
        if(bot.bott.x>(int)screenSize.getWidth() || bot.bott.y>(int)screenSize.getHeight() || bot.bott.x+bot.bott.width<0 || bot.bott.y+bot.bott.height<0){
            do{
                Random a=new Random();
                int drayon=a.nextInt(16)-8;
                bot=new Bot(a.nextInt((int)screenSize.getWidth()),a.nextInt((int)screenSize.getHeight()), joueur.height+drayon, joueur.width+drayon,map);
            }while(joueur.playerr.intersects(bot.bott.x, bot.bott.y, bot.bott.width, bot.bott.height));
        }
    }

    public void specialUpEaten(){
        //long timeDebutSpecial=System.nanoTime();
        //long timeFinSpecial=System.nanoTime();
        //int anciendx,anciendy;
        //anciendx=map.dx;
        //anciendy=map.dy;
        if(map.dxRef<70){
            map.dxRef=2*map.dxRef; //pour permettre le *2 meme en appuyant sur Q/S/D/Z aprÃ¨s on utilise des valeurs de references
            map.dyRef=2*map.dyRef;
            map.dy = 2*map.dy;
            map.dx = 2*map.dx;
        }
        //while(System.nanoTime() - timeDebutSpecial<10000){ 
        //}

    }

    public void specialDownEaten(){
        System.out.println(map.dx + " , "+map.dy);
        int signeDx=0;
        int signeDy=0;
        if(map.dx!=0 ){ signeDx=map.dx/Math.abs(map.dx);}
        if(map.dy!=0 ){ signeDy=map.dy/Math.abs(map.dy);}
        map.dxRef=2; 
        map.dyRef=2;
        if(map.dy!=0 ){map.dy = 2*signeDy;}
        if(map.dx!=0){map.dx = 2*signeDx;}
        System.out.println(map.dx + " , "+map.dy);
    }
    
    // Ã©crire les infos de la partie (score, temps et vitesse) 
    public void printInfoBall(Graphics2D g2){
		g2.setColor(Color.ORANGE);
		double a=TimeUnit.SECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
		Font font= new Font("arial",Font.BOLD,20);
		g2.setFont(font);
		g2.drawString("SCORE : "+score,0,50);
		g2.drawString("TIME: "+a, 0,70);
        g2.drawString("SPEED: "+Math.sqrt(Math.pow(map.dx, 2)+Math.pow(map.dy, 2)), 0,90);
	}


    //Ã©crire la lÃ©gende des Ã©lÃ©ments de la map selon le niveau de jeu
    public void printSpecialInfo(Graphics2D g2){
        Font font= new Font("arial",Font.BOLD,13);
        g2.setFont(font);

        g2.setColor(Color.ORANGE);
        g2.drawLine((int)screenSize.getWidth()-150,(int)screenSize.getHeight()-150, (int)screenSize.getWidth()-150,(int)screenSize.getHeight());
        g2.drawLine((int)screenSize.getWidth()-150,(int)screenSize.getHeight()-150, (int)screenSize.getWidth(),(int)screenSize.getHeight()-150);   
        g2.drawString("SPACE : Pause",(int)screenSize.getWidth()-140,(int)screenSize.getHeight()-130);

        if(poisonSimple){
            g2.setColor(map.poisons.couleurPoison);
            g2.fill(new Rectangle2D.Double((int)screenSize.getWidth()-140,(int)screenSize.getHeight()-120 ,10,10));
            g2.setColor(Color.ORANGE);
            g2.drawString("Poison ",(int)screenSize.getWidth()-120,(int)screenSize.getHeight()-110);
        }
        if(specialBouton){
            g2.setColor(map.poisons.couleurBouton);
            g2.fill(new Rectangle2D.Double((int)screenSize.getWidth()-140,(int)screenSize.getHeight()-100 ,10,10));
            g2.setColor(Color.ORANGE);
            g2.drawString("Inverser boutons ",(int)screenSize.getWidth()-120,(int)screenSize.getHeight()-90);
        }
        if(specialSpeedUp){
            g2.setColor(map.poisons.couleurUp);
            g2.fill(new Rectangle2D.Double((int)screenSize.getWidth()-140,(int)screenSize.getHeight()-80 ,10,10));
            g2.setColor(Color.ORANGE);
            g2.drawString("Speed x 2 ",(int)screenSize.getWidth()-120,(int)screenSize.getHeight()-70);

        }
        if(specialSpeedDown){
            g2.setColor(map.poisons.couleurDown);
            g2.fill(new Rectangle2D.Double((int)screenSize.getWidth()-140,(int)screenSize.getHeight()-60 ,10,10));
            g2.setColor(Color.ORANGE);
            g2.drawString("Speed = 2 ",(int)screenSize.getWidth()-120,(int)screenSize.getHeight()-50);
        }
        
    }

    public void setLost(boolean res){
        lost=res;
    }

    public void closeGame(){
        fen.dispose();
        try {
            new FenetreMenu();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Les 2 méhodes qui suivent sont expliquées dans le rapport (cf problème bot)

    /*public Color getCouleurUnPixel(int x,int y) throws AWTException{
         // create an object of robot class
         Robot r = new Robot();
 
         // get the pixel color
        Color c = r.getPixelColor(x, y);
        return c;

    }
    
    public boolean intersectsBot() throws AWTException{
        boolean res=false;
        boolean [] [] tPixel=bot.tabPixel();
        for(int i=0; i<bot.width-1;i++){
            for(int j=0; j<bot.height-1;j++){
                if(joueur.playerr.intersects(bot.x+i,bot.y+j,1,1) && tPixel[i][j]){
                    res = true;
                    break;
                }
            }
        }
        return res;
    }
    */

	

    // la mÃ©thode qui dessine tous les Ã©lÃ¨ments du jeu
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh =new RenderingHints( 
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        ); // pour avoir des contours softs
        g2.setRenderingHints(rh);
        
        if(lost){
            try {
                this.json();
            } catch (JsonIOException | IOException e) {
                e.printStackTrace();
            }
			g2.setColor(Color.ORANGE);
			Font font= new Font("arial",Font.BOLD,100);
			g2.setFont(font);
			g2.drawString("YOU LOST",(int)screenSize.getWidth()/2- 250, (int)screenSize.getHeight()/2-50);
            font= new Font("arial",Font.BOLD,20);
			g2.setFont(font);
            g2.drawString("PRESS SPACE TO GO BACK TO MENU",(int)screenSize.getWidth()/2- 180, (int)screenSize.getHeight()/2-10);
            
		}else{
			super.paintComponent(g);
			g2.setColor(couleurAvatar);
			joueur.dessiner(g);
            g2.setColor(Color.ORANGE);
            if(avatartxt.length()<10){
                Font font= new Font("arial",Font.BOLD,20);
                g2.setFont(font);
                g2.drawString(avatartxt,(int) screenSize.getWidth()/2-50,(int)screenSize.getHeight()/2);
            }else{
                Font font= new Font("arial",Font.BOLD,10);
                g2.setFont(font);
                g2.drawString(avatartxt,(int)screenSize.getWidth()/2-35, (int)screenSize.getHeight()/2-25);
            }
			map.dessinerMonde(g,joueur);
            g2.setColor(Color.YELLOW);
			bot.dessiner(g);
            BotSort();
            printSpecialInfo(g2);  
			printInfoBall(g2);
			try {
                checkcollision(g);
            } catch (AWTException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            if( !poisonSimple &&  !specialBouton &&  !specialSpeedUp &&  !specialSpeedDown ){
                JButton endGame = new JButton();
                endGame.setText("END GAME");
                endGame.addActionListener(new EcouteurEnd(this));
                endGame.setBackground(new Color(255,0,0));
                this.add(endGame);
            }
		}
    }

 
}
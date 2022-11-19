import java.awt.event.*;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

//ecouteur des boutons de choix de difficulté
public class EcouteurDifficulte implements ActionListener {
    
    private boolean poisonSimple;
    private boolean specialBouton;
    private boolean specialSpeedUp;
    private boolean specialSpeedDown;
    private FenetreMenu fen;

    public EcouteurDifficulte(boolean poisonSimple, boolean specialBouton , boolean specialSpeedUp , boolean specialSpeedDown,FenetreMenu fen){
        this.poisonSimple=poisonSimple;
        this.specialBouton=specialBouton;
        this.specialSpeedUp=specialSpeedUp;
        this.specialSpeedDown=specialSpeedDown;
        this.fen=fen;
    }

    public void music() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        File file = new File("music.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }

    public void actionPerformed(ActionEvent e){
        fen.lancerJeu(poisonSimple,specialBouton,specialSpeedUp,specialSpeedDown);
        fen.dispose();
        try {
            music();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}

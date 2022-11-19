import java.awt.event.*;
import java.io.IOException;

import com.google.gson.JsonIOException;

//import javafx.scene.paint.Color;

//ecouteur qui ferme le jeu dans le niveau 1
public class EcouteurEnd implements ActionListener  {
    Draw fen;

    public EcouteurEnd(Draw fen) {
        this.fen=fen;  
    }
    
    public void actionPerformed(ActionEvent e) {
       fen.setLost(true);
       try {
            fen.json();
        } catch (JsonIOException | IOException b) {
            b.printStackTrace();
        }
        fen.closeGame();
    }
    
}
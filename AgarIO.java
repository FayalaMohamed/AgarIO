import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;

public class AgarIO extends JFrame {

    static String avatartxt;
    


    public AgarIO(boolean poisonSimple, boolean specialBouton , boolean specialSpeedUp , boolean specialSpeedDown, Color couleurAvatar, String avatartxt){
        super("AgarIO");
        
        Draw object = new Draw(); 
        object.drawing(poisonSimple, specialBouton, specialSpeedUp, specialSpeedDown, couleurAvatar,this, avatartxt);
        this.add(object);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        this.setSize((int)screenSize.getWidth(),(int)screenSize.getHeight());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        AgarIO.avatartxt=avatartxt;
                 
    }

    
}
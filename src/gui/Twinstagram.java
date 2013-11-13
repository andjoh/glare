package gui;

public class Twinstagram{
public static void main(String args[]) {
  /**
 *
 * @author Andreas J
 */
      

   
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	DisplayController disp = new DisplayController();
            	LoginDialog ld= new LoginDialog(null);
            	ImageShow imsh= new ImageShow(disp);
            	ShowInterface showInterface;
                showInterface = new ShowInterface(imsh, ld);
            }
        });
    } 
}

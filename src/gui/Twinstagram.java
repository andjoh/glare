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
                ShowInterface showInterface;
                showInterface = new ShowInterface();
            }
        });
    } 
}

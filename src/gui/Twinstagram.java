

public class Twinstagram{
public static void main(String args[]) {
  /**
 *
 * @author Andreas J
 */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Twinstagram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    

   
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ShowInterface showInterface;
                showInterface = new ShowInterface();
            }
        });
    } 
}

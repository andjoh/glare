package gui;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
/*
 * @author Andreas J
 * 
 * 
 */
class TableUpdater extends Thread {
	/*
	 * Class for updating the ImageTable
	 * Is  passed reference to ImageTables tablemodel
	 * Which is manipulated in this thread to to feed the table
	 * with new data
	 * 
	 */
	private TableModel model;
	private SettingsViewModulDummy setview;

	public TableUpdater(TableModel model, SettingsViewModulDummy setview) {
 super();
		this.model = model;
		this.setview = setview;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Sets all columns  to contain an ImageIcon received from SettingsViewModulDummy
			for (int r = 0; r < model.getRowCount(); r++) {
                 final int rind=r;
				for (int c = 0; c < model.getColumnCount(); c++) {
					 final int cind=c;
				
						
						ImageIcon ic = setview.getThumbnail();
						model.setValueAt(ic, rind, cind);
						/*
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								ImageIcon ic = setview.getThumbnail();
								model.setValueAt(ic, rind, cind);
							}
						});*/
					

				}
			}
		}
	}
}
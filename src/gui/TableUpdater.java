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
	private static TableModel model;
	private SettingsViewModulDummy setview;
	private static boolean threadIsActive, shouldUpdate;

	public TableUpdater(TableModel model, SettingsViewModulDummy setview) {
        super();
		this.model = model;
		this.setview = setview;
		threadIsActive=true;
		shouldUpdate=true;
	}

	@Override
	public void run() {
		while (threadIsActive) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(shouldUpdate){
				updateTable();
			}
			// Sets all columns  to contain an ImageIcon received from SettingsViewModulDummy
		
		}
	}
	public void setshouldUpdate(boolean shouldUpdate){
		
	this.shouldUpdate=shouldUpdate;
	}
	public void setsthreadIsActive(boolean threadIsActive){
		
		this.threadIsActive=threadIsActive;
		}
	private void updateTable(){
		for (int r = 0; r < model.getRowCount(); r++) {
            final int rind=r;
			for (int c = 0; c < model.getColumnCount(); c++) {
				 final int cind=c;
			
					
					
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							ImageIcon ic = setview.getThumbnail();
							model.setValueAt(ic, rind, cind);
						}
					});
				

			}
		}
		setshouldUpdate(false);
	}
}
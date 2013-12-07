package gui;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bll.SettingsPicture;

/**
 * @author Andreas Johnstad
 *
 */ 
public class ImageTableListener implements ListSelectionListener {
	private ImageTable table; // reference to table
	private ImageTableModel model; // reference to table model
	private static int 
	        rStart = 0, rEnd = 0,  //indices indicating the are in which selected colunmns are
	        colIndexStart = 0,    // r stands for row, c for column
			colIndexEnd = 0;     

	/**
	 * @param table
	 * @param model
	 */
	public ImageTableListener(ImageTable table, ImageTableModel model) {
		this.table = table;
		this.model = model;

	}

	/**
	 *  (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {

		// this statement differentiates between click and released click
		// to prevent the methods inside the condition to be fired twice

		if (!e.getValueIsAdjusting()) {

			// clears previous flags
			model.clearFlags();
			// defines an area in which the selected cells are located in
			// by getting min and max indices
			// this narrows down the search in getCells()
			rStart = table.getSelectedRow();
			rEnd = table.getSelectionModel().getMaxSelectionIndex();
			colIndexStart = table.getSelectedColumn();
			colIndexEnd = table.getColumnModel().getSelectionModel()
					.getMaxSelectionIndex();
			getCells();
 
		}

	}

	
	/**
	 * // iterates through the cells in the defined area
	 */
	private void getCells() {
     SettingsPicture pic;
		for (int r = rStart; r <= rEnd; r++) {
			for (int c = colIndexStart;c <= colIndexEnd;c++) {
				// if the cell is selected
				if (table.isCellSelected(r, c)) {
                  
					// set temporary flag
					pic = model.getSetPic(r, c);
					if(pic!=null)pic.setIsTempFlagged(true);
				}
			}
		}

	}
}

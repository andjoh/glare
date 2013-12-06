package gui;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ImageTableListener implements ListSelectionListener {
	private ImageTable table;
	private ImageTableModel model;
	private static int rStart = 0, rEnd = 0, colIndexStart = 0,
			colIndexEnd = 0;

	public ImageTableListener(ImageTable table, ImageTableModel model) {
		this.table = table;
		this.model = model;

	}

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

	// iterates through the cells in the defined are
	private void getCells() {
     
		for (int i = rStart; i <= rEnd; i++) {
			for (int j = colIndexStart; j <= colIndexEnd; j++) {
				if (table.isCellSelected(i, j)) {
             
					model.setTempflagOnPicture(i, j, true);
				}
			}
		}

	}
}

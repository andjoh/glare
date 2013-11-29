package gui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import bll.SettingsPicture;

import resources.ViewControllerDummy;

/*
 * @author Andreas J
 * 
 * 
 */
class TableUpdater extends Thread {
	/*
	 * Class for updating the ImageTable Is passed reference to ImageTables
	 * tablemodel Which is manipulated in this thread to to feed the table with
	 * new data
	 */
	private TableModel model;
	private ImageTableModel imgmodel;
	private ArrayList<ImageIcon> images;
	private ViewControllerDummy setview;
	private boolean threadIsActive, shouldUpdate;

	public TableUpdater(TableModel model, ViewControllerDummy setview) {

		super();
		this.model = model;
		this.setview = setview;
		threadIsActive = true;
		shouldUpdate = true;
	}

	public TableUpdater(ImageTableModel imgmodel, ViewControllerDummy setview) {

		super();
		this.imgmodel = imgmodel;
		this.setview = setview;
		threadIsActive = true;
		shouldUpdate = true;
	}

	@Override
	public void run() {
		while (threadIsActive) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (shouldUpdate) {
				updateTable();
			}
			// Sets all columns to contain an ImageIcon received from
			// SettingsViewModulDummy

		}
	}

	public void setshouldUpdate(boolean shouldUpdate) {

		this.shouldUpdate = shouldUpdate;
	}

	public void setsthreadIsActive(boolean threadIsActive) {

		this.threadIsActive = threadIsActive;
	}

	private void updateTable() {
		for (int r = 0; r < model.getRowCount(); r++) {
			final int rind = r;
			for (int c = 0; c < model.getColumnCount(); c++) {
				final int cind = c;

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {

					}
				});

			}
		}
		setshouldUpdate(false);
	}
}
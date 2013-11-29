package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import bll.ViewController;

import resources.ViewControllerDummy;

public class TableSettingsPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Constraints[] gbc;
	private final static int COLS = 5, ROWS = 20;
	private ImageTableModel tablemodel;
	private JButton removeButton;
	private Dimension dim;
	private ViewController viewCtrl;
	private JScrollPane tableScroller;
	private ImageTable thumbnailTable;

	public TableSettingsPanel(ViewController viewCtrl,Dimension dim) {
		this.dim=dim;
		this.viewCtrl = viewCtrl;
		setOpaque(false);
		setLayout(new GridBagLayout());
		gbc = new Constraints[] { new Constraints(), new Constraints() };
		init();
		System.out.println("TableSettingsPanel"+super.getSize());
		setConstraints();
	}

	private void init() {
		// thumbnailTable properties
	
		tablemodel = new ImageTableModel(viewCtrl.getSettingsPictures(ROWS, COLS) );
		thumbnailTable = new ImageTable(tablemodel,dim);
		// tableScroller properties
		tableScroller = new JScrollPane();
		tableScroller.setOpaque(false);

		tableScroller.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		tableScroller.setViewportView(thumbnailTable);
		tableScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// removeButton Properties
		removeButton = new JButton("Remove");
		removeButton.addActionListener(this);
		;
	}

	private void setConstraints() {

		gbc[0].set(0, 0, 2, 1, 429, 375, 1.0, 1.0, new Insets(19, 11, 0, 47),
				GridBagConstraints.NORTHWEST);

		add(tableScroller, gbc[0]);
		gbc[1].set(0, 1, 1, 1, new Insets(9, 390, 57, 0),
				GridBagConstraints.NORTHWEST);
		add(removeButton, gbc[1]);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(removeButton)) {

		}

	}

	public ImageTableModel getImageTableModel() {
		return tablemodel;
	}
}

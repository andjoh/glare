package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;

import bll.SettingsPicture;
import bll.ViewController;

public class TableSettingsPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Constraints[] gbc;
	private ImageTableModel tablemodel;
	private JButton removeButton;
	private List<SettingsPicture> settingsPictures;
	private Dimension dim;
    private InputMap input;
	private ViewController viewCtrl;
	private JScrollPane tableScroller;
	private ImageTable thumbnailTable;

	public TableSettingsPanel(ViewController viewCtrl, Dimension dim) {
		this.dim = dim;
		this.viewCtrl = viewCtrl;
		setOpaque(false);
		setLayout(new GridBagLayout());
		gbc = new Constraints[] { new Constraints(), new Constraints() };
		setFocusable(true);
		this.setRequestFocusEnabled(true);
	
		init();
		setConstraints();
		addKeyBindings();
	}

	private void init() {
		// thumbnailTable properties
		settingsPictures=viewCtrl.getSettingsPictures();
		tablemodel = new ImageTableModel(settingsPictures);
		thumbnailTable = new ImageTable(tablemodel, dim);

		removeButton = new JButton("Remove");
		removeButton.addActionListener(this);

		// tableScroller properties
		tableScroller = new JScrollPane();
		tableScroller.setOpaque(false);
		// tableScroller.setColumnHeader(null);
		tableScroller.setBackground(Color.white.brighter());

		tableScroller.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		tableScroller.setViewportView(thumbnailTable);
		tableScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
private void addKeyBindings(){
		
		input= getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "backspace");
         getActionMap().put("backspace", new AbstractAction() {
			private static final long serialVersionUID = 1L;

						@Override
			            public void actionPerformed(ActionEvent e) {
			            	removeButton.doClick();
			            }
			        });	
	}
	private void setConstraints() {
		gbc[0].fill = GridBagConstraints.BOTH;
		gbc[0].set(0, 0, 1, 1, dim.width, dim.height * 3 / 4, 1.0, 1.0, new Insets(11, 10, 0, 18), GridBagConstraints.NORTHWEST);

		add(tableScroller, gbc[0]);

		gbc[1].set(0, 1, 1, 1, 0, 0, 1.0, 1.0, new Insets(6, dim.width / 2, 150, 18), GridBagConstraints.NORTHWEST);
		add(removeButton, gbc[1]);
	}

	public ImageTableModel getImageTableModel() {
		return tablemodel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(removeButton)) {
			thumbnailTable.removeFlagged();
			repaint();
		}

	}
}

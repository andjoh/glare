package gui;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

 public class TableSettingsPanel extends JPanel implements ActionListener {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private Constraints[] gbc;
		private static int cols=10,rows=10;
		private JButton removeButton;
		private JScrollPane tableScroller;
		private ImageTable thumbnailTable;
	
		public TableSettingsPanel() {
			setOpaque(false);
			setLayout(new GridBagLayout());
			gbc= new Constraints[]{new Constraints(),new Constraints()};
			init();

			setConstraints();
		}

		private void init() {
			// thumbnailTable properties
			thumbnailTable= new ImageTable(cols,rows);
			SettingsViewModulDummy setv= new SettingsViewModulDummy();
			setv.setH(thumbnailTable.getHeight()/rows);
			setv.setW(thumbnailTable.getWidth()/cols);
			System.out.println(thumbnailTable.getHeight());
			TableUpdater update = new TableUpdater(thumbnailTable.getModel(), setv);
			update.start();
			// tableScroller properties
			tableScroller = new JScrollPane();
			tableScroller.setOpaque(false);
	
			tableScroller.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			tableScroller.setViewportView(thumbnailTable);
			// removeButton Properties
			removeButton = new JButton("Remove");
			removeButton.addActionListener(this);
			;
		}

		private void setConstraints() {


             gbc[0].set(0, 0, 2, 1, 429, 375, 1.0, 1.0, new Insets(19, 11, 0, 47), GridBagConstraints.NORTHWEST);
			

			add(tableScroller, gbc[0]);
		   gbc[1].set(0, 1, 1, 1, new Insets(9, 390, 57, 0), GridBagConstraints.NORTHWEST);
			add(removeButton,gbc[1]);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
		
			
		}
	}

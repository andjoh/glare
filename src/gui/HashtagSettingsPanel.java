package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bll.ViewController;

public class HashtagSettingsPanel extends JPanel implements ActionListener,
		DocumentListener, ListSelectionListener {

	/**
	 * @author Andreas J
	 */
	private static final long serialVersionUID = 1L;
	private DefaultListModel<String> listModel;
	private JTextField addhashField;
	private JList<String> hashJList;


	private JLabel hashsignLabel;
	private JButton delhashButton, addhashButton;
	private JScrollPane jlistScroller;

	private Constraints[] gbcs = new Constraints[] { new Constraints(),
			new Constraints(), new Constraints(), new Constraints(),
			new Constraints() };

	public HashtagSettingsPanel( ) {
       
      
		setAutoscrolls(true);
		setMaximumSize(new Dimension(20000, 23000));
		setOpaque(false);
		setLayout(new GridBagLayout());
		init();
		setConstraints();
	}

	private void init() {

		// hasJlist properties

		listModel = new DefaultListModel<String>();
		hashJList = new JList<String>(listModel);
		hashJList
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		// jlistScroller properties
		jlistScroller = new JScrollPane();
		jlistScroller.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jlistScroller.setViewportView(hashJList);

		hashJList.addListSelectionListener(this);
		hashJList.setVisibleRowCount(5);
		// set addHashButtons properties
		addhashButton = new JButton();
		addhashButton.setIcon(new ImageIcon(getClass().getResource(
				"/resource/img/add.gif")));
		addhashButton.setBorderPainted(false);
		addhashButton.setContentAreaFilled(false);
		addhashButton.addActionListener(this);
		addhashButton.setEnabled(false);
		// delHashButton properties
		delhashButton = new JButton();
		delhashButton.setIcon(new ImageIcon(getClass().getResource(
				"/resource/img/del.gif")));
		delhashButton.setBorderPainted(false);
		delhashButton.setContentAreaFilled(false);
		delhashButton.addActionListener(this);
		delhashButton.setEnabled(false); //

		// RoundJTextField hashField propertie
		addhashField = new JTextField();
		addhashField.setMaximumSize(new java.awt.Dimension(1000, 1024));
		addhashField.getDocument().addDocumentListener(this);

		addhashField.setOpaque(false);
		// hashsignLabel properties

		hashsignLabel = new JLabel("#");
		hashsignLabel.setFont(new Font("Tahoma", 0, 14));
		hashsignLabel.setForeground(new Color(255, 255, 255));

	}

	private void setConstraints() {
		int gc = GridBagConstraints.NORTHWEST;
		// jlistScroller
		gbcs[0].fill = GridBagConstraints.BOTH;
		gbcs[0].set(0, 3, 3, 2, 69, 212, 1.0, 1.0, new Insets(10, 20, 0, 0), gc);
		add(jlistScroller, gbcs[0]);

		// hashField
		gbcs[1].set(0, 0, 2, 2, 84, 0, new Insets(60, 20, 0, 0), gc);
		add(addhashField, gbcs[1]);
		// addHashButton

		gbcs[2].set(3, 0, 1, 0, 0, 23, 0, 0, new Insets(50, 0, 0, 15), gc);
		add(addhashButton, gbcs[2]);

		// delhashButton

		gbcs[3].set(3, 3, 1, 1, 0, 23, 0, 0, new Insets(0, 0, 0, 15), gc);
		add(delhashButton, gbcs[3]);
		// hashSignLabel

		gbcs[4].set(0, 0, 1, 1, 21, 0, 0, 0, new Insets(60, 0, 0, 0), gc);
		add(hashsignLabel, gbcs[4]);

	}

	// listeners for add and remove button
	@Override
	public void actionPerformed(ActionEvent e) {
		Object c;
		int[] indexes;
		c = e.getSource();

		if (c.equals(delhashButton)) {
			indexes = hashJList.getSelectedIndices();
			for (int in : indexes) {
				if (listModel.getSize() > 0 && in < listModel.getSize())
					listModel.remove(in);

			}

		} else if (c.equals(addhashButton)) {
			listModel.addElement(addhashField.getText().trim());
			addhashField.setText("");
		}
		repaint();
	}

	// jlist listener. Disables remove button if no elements
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {

			if (hashJList.getSelectedIndex() == -1) {

				delhashButton.setEnabled(false);

			} else {

				delhashButton.setEnabled(true);
			}
		}
		repaint();
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		checkFieldsFull();

	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		checkFieldsFull();

	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		checkFieldsFull();

	}

	private void checkFieldsFull() {

		if (addhashField.getText().trim().isEmpty()) {
			addhashButton.setEnabled(false);

		} else {
			addhashButton.setEnabled(true);
		}

	}

	public DefaultListModel<String> getListModel() {
		return listModel;
	}

	public Set<String> getHashtagList() {
		Object[] stringList = listModel.toArray();
		Set<String> hashtagList = new HashSet<String>();
		for (int i = 0; i < stringList.length; i++) {
			hashtagList.add((String) stringList[i]);
		}

		return hashtagList;
	}

	public void setHashtagList(Set<String> hashtags) {
		for (String ht : hashtags) {
			listModel.addElement(ht);
		}
	}
}

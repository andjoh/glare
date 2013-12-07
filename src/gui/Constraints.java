package gui;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * 
 * @author Andreas J
 */

public class Constraints extends GridBagConstraints {
	/**
	 *  Convenience class to easier set GridBagConstraints 
	 *  Used by GUI classes 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor
	 * Calls superclass GridBagConstraints
	 */
	 
	public Constraints() {
		super();
	}
	
	/**
	 * @param grx
	 * @param gry
	 * @param grw
	 * @param grh
	 * @param ipx
	 * @param ipy
	 * @param ins
	 * @param anc
	 */
	public void set(int grx, int gry, int grw, int grh, int ipx, int ipy,
			Insets ins, int anc) {
		gridx = grx;
		gridy = gry;
		gridwidth = grw;
		gridheight = grh;
		ipadx = ipx;
		ipady = ipy;
		insets = ins;
		anchor = anc;

	}
	

	/**
	 * @param grx
	 * @param gry
	 * @param grw
	 * @param grh
	 * @param ipx
	 * @param ipy
	 */
	public void set(int grx, int gry, int grw, int grh, int ipx, int ipy) {
		gridx = grx;
		gridy = gry;
		insets = new Insets(0, 0, 0, 0);
		gridwidth = grw;
		gridheight = grh;
		ipadx = ipx;

	}
	/**
	 * Set properties of superclass 
	 * <p>
	 * Sets values to define constraints 
	 * that are used to place GUI components
	 * <p>
	 * @param  grx
	 *  @param  gry 
	 *  @param  grw 
	 *  @param  grh
	 *  @param  ipx
	 *  @param ipy
	 * @param ins
	 *  @param
	 */
	public void set(int grx, int gry, int grw, int grh, Insets ins, int anc) {
		gridx = grx;
		gridy = gry;
		gridwidth = grw;
		gridheight = grh;
		insets = ins;
		ipadx = 0;
		ipady = 0;
		anchor = anc;

	}
	/**
	 * Set properties of superclass 
	 * <p>
	 * Sets values to define constraints 
	 * that are used to place GUI components
	 * <p>
	 * @param  grx
	 *  @param  gry 
	 *  @param  grw 
	 *  @param  grh
	 *  @param  ipx
	 *  @param ipy
	 * @param  wgx
	 * @param  wgy
	 * @param ins
	 *  @param anc
	 */
	public void set(int grx, int gry, int grw, int grh, int ipx, int ipy,
			double wgx, double wgy, Insets ins, int anc) {
		gridx = grx;
		gridy = gry;
		gridwidth = grw;
		gridheight = grh;
		ipadx = ipx;
		weightx = wgx;
		weighty = wgy;
		ipady = ipy;
		if(ins!=null)insets = ins;
		if(anc!=-33)
		anchor = anc;

	}

}

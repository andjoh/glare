package gui;

import java.util.*;

import bll.*;

public class Administration {
	private AdministrationController administrationController;

	public Administration(AdministrationController administrationController) {
		this.administrationController = administrationController;
	}

	private void updateHashtagBySource(String source, List<String> hashtag) {		
		this.administrationController.updateHashtagBySource(source, hashtag);		
	}
}
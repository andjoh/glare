package gui;

import java.util.*;

import bll.*;

public class Settings {
	private SettingsController administrationController;

	public Settings(SettingsController administrationController) {
		this.administrationController = administrationController;
	}

	private void updateHashtagBySource(String source, Set<String> hashtag) {		
		this.administrationController.updateHashtagBySource(source, hashtag);		
	}
}
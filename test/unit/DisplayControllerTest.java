package unit;

import static org.junit.Assert.*;

import glare.ClassFactory;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bll.DisplayController;

public class DisplayControllerTest {
	
	private DisplayController dc;
	
	@Before
	public void setUp() throws IOException{
		dc = (DisplayController) ClassFactory.getBeanByName("displayController");
		dc.getPictureObjects(false);
	}
	@After
	public void tearDown(){
		dc = null;
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}

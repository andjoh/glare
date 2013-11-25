package unit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import glare.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import org.junit.*;

import bll.*;

public class ViewControllerTest {
	
	
	@Before
	public void setUp() throws IOException{
	}
	@After
	public void tearDown(){

	}

	@Test
	public void test() throws IOException {
		ViewController vc = (ViewController) ClassFactory.getBeanByName("viewController");
		BufferedImage bi = vc.getCurrentPicture();

		// Additional output
		System.out.println("BufferedImage " + bi.getHeight());
		System.out.println("");
		
		List<SettingsPicture> sp = vc.getSettingsPictures();
		
		// Additional output
		System.out.println("SettingsPicture");
		for ( SettingsPicture s : sp ) {
			System.out.println(s.getId());
		}
		System.out.println("");
		
		assertThat(sp.isEmpty(),is(false));
	}
}
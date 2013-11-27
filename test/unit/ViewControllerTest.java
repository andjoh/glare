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
		
		List<List<SettingsPicture>> sp2d = vc.getSettingsPictures(20,5);
		
		// Additional output
		System.out.println("SettingsPicture");
		for ( List<SettingsPicture> spl : sp2d ) {
			
			for(SettingsPicture s: spl){
				System.out.println(s.getId());
			}
			
		}
		System.out.println("");
		
		assertThat(sp2d.isEmpty(),is(false));
	}
}
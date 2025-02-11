package test.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import core.Main;

public class TestCore {
	
	@Test
	public void InitFunction1() {

		String result = Main.init("1-(-2+1*5)");
		
	    assertEquals("-2.0", result);

		
	}
	
	@Test
	public void InitFunctionA() {
	    assertThrows(NumberFormatException.class, () -> {
	        Main.init("1-s");
	    });
	}
	
	@Test
	public void InitFunction2() {

		String result = Main.init("1--2");
		
	    assertEquals("3.0", result);
	
	}
	
	
	@Test
	public void InitFunction3() {

		String result = Main.init("1-2");
		
	    assertEquals("-1.0", result);

		
	}
	
	
	@Test
	public void InitFunction4() {

		String result = Main.init("1.0-2");
		
	    assertEquals("-1.0", result);

		
	}
	
	
	@Test
	public void InitFunction5() {

		String result = Main.init("1.0--2.0");
		
	    assertEquals("3.0", result);

		
	}
	
	
	@Test
	public void InitFunction6() {

		String result = Main.init("1-(-2)");
		
	    assertEquals("3.0", result);

		
	}
	
	
	@Test
	public void InitFunction7() {

		String result = Main.init("1-(--2)");
		
	    assertEquals("-1.0", result);

		
	}

	public void InitFunction72() {

		String result = Main.init("1-(--2.0)");
		
	    assertEquals("-1.0", result);

		
	}
	
	@Test
	public void InitFunction8() {

		String result = Main.init("1-2*5");
		
	    assertEquals("-9.0", result);

		
	}
	
	
	@Test
	public void InitFunction9() {

		String result = Main.init("1-2*5+3");
		
	    assertEquals("-6.0", result);

		
	}
	
	
	@Test
	public void InitFunction10() {

		String result = Main.init("1*(2+5)");
		
	    assertEquals("7.0", result);

		
	}
	
	
	@Test
	public void InitFunction11() {

		String result = Main.init("2*(2+5+3)");
		
	    assertEquals("20.0", result);

		
	}
	
	
	@Test
	public void InitFunction12() {

		String result = Main.init("2.0*(2+5.0+3)");
		
	    assertEquals("20.0", result);

		
	}

	@Test
	public void InitFunction13() {

		String result = Main.init("40.0/(2.5+2.5+5.0*3)");
		
	    assertEquals("2.0", result);

		
	}

	@Test
	public void InitFunction14() { //TODO: Hier mit dem Testen der Potenzen weiter!

		String result = Main.init("2**3");
		
	    assertEquals("8.0", result);

		
	}
	
}

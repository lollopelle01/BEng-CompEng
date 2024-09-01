package edicecream.persistence.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

import edicecream.persistence.BadFileFormatException;
import edicecream.persistence.MyIceCreamRepository;

public class MyIceCreamRepositoryTest {

	@Test
	public void testIceCreamRepository_HappyPath_ReadQuantityOfNonExistentFlavor() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("\ncioccolato-1\ncrema -2\n fior di latte - 3");
		MyIceCreamRepository ic = new MyIceCreamRepository(mr);
		assertEquals(Integer.valueOf(1), ic.getAvailableQuantity("cioccolato"));
		assertEquals(Integer.valueOf(2), ic.getAvailableQuantity("crema"));
		assertEquals(Integer.valueOf(3), ic.getAvailableQuantity("fior di latte"));
		assertEquals(null, ic.getAvailableQuantity("stracciatella"));
		assertEquals(3, ic.getFlavors().size());
	}

	@Test
	public void testIceCreamRepository_HappyPath_CheckReadFlavors() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("\ncioccolato-1\ncrema -2\n fior di latte - 3");
		MyIceCreamRepository ic = new MyIceCreamRepository(mr);
		assertEquals(3, ic.getFlavors().size());
		assertTrue(ic.getFlavors().contains("cioccolato"));
		assertTrue(ic.getFlavors().contains("crema"));
		assertTrue(ic.getFlavors().contains("fior di latte"));
	}

	@Test
	public void testIceCreamRepository_HappyPath_CheckFlavorsAndQuantities() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("cioccolato-10");
		MyIceCreamRepository ic = new MyIceCreamRepository(mr);
		assertEquals(Integer.valueOf(10), ic.getAvailableQuantity("cioccolato"));
		assertTrue(! ic.removeQuantity("crema", 5));
		assertTrue(ic.removeQuantity("cioccolato", 5));
		assertEquals(Integer.valueOf(5), ic.getAvailableQuantity("cioccolato"));
		assertTrue(! ic.removeQuantity("cioccolato", 6));
		assertEquals(Integer.valueOf(5), ic.getAvailableQuantity("cioccolato"));
		assertTrue(ic.removeQuantity("cioccolato", 5));
		assertEquals(Integer.valueOf(0), ic.getAvailableQuantity("cioccolato"));
	}

	@Test(expected = BadFileFormatException.class)
	public void testTextParamError_MissingQuantity() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("\ncioccolato\ncrema -2\n fior di latte - 3");
		new MyIceCreamRepository(mr);
	}	
	
	@Test(expected = BadFileFormatException.class)
	public void testTextParamError_MissingFlavor() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("\ncioccolato-1\n-2\n fior di latte - 3");
		new MyIceCreamRepository(mr);
	}
	@Test(expected = BadFileFormatException.class)
	public void testTextParamError_MissingNewLine_TooMuchTokens() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("\ncioccolato-1 crema - 2\n fior di latte - 3");
		new MyIceCreamRepository(mr);
	}
	@Test(expected = BadFileFormatException.class)
	public void testTextParamError_BadQuantity() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("\ncioccolato-1\ncrema - 2\n fior di latte - 3.4");
		new MyIceCreamRepository(mr);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testTextParamError_IllegalArgument() throws IOException, BadFileFormatException {
		new MyIceCreamRepository(null);
	}
}

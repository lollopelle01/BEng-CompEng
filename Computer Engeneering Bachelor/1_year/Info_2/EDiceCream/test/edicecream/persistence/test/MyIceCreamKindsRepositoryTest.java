package edicecream.persistence.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

import edicecream.model.IceCreamKind;
import edicecream.persistence.BadFileFormatException;
import edicecream.persistence.MyIceCreamKindsRepository;

public class MyIceCreamKindsRepositoryTest {

	@Test
	public void testIceCreamKindRepositoryHappyPath() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("Cono- 1,00 - 1 -30\n\nCoppetta - 2,00- 2 -20\n");
		MyIceCreamKindsRepository ic = new MyIceCreamKindsRepository(mr);
		assertEquals(2, ic.getKindNames().size());
		assertNull(ic.getIceCreamKind("stecco"));
		IceCreamKind k = ic.getIceCreamKind("Cono");
		assertNotNull(k);
		assertEquals("Cono", k.getName());
		assertEquals(1f, k.getPrice(), 0.01);
		assertEquals(1, k.getMaxFlavors());
		assertEquals(30, k.getWeight());

		k = ic.getIceCreamKind("Coppetta");
		assertNotNull(k);
		assertEquals("Coppetta", k.getName());
		assertEquals(2f, k.getPrice(), 0.01);
		assertEquals(2, k.getMaxFlavors());
		assertEquals(20, k.getWeight());
	}

//	@Test(expected = BadFileFormatException.class)
//	public void testTextParamError0() throws IOException, BadFileFormatException {
//		Reader mr = new StringReader("Cono- 1,00 - 1 -10\n\nCoppetta - 2.00- 2 -5\n");
//		MyIceCreamKindsRepository repo = new MyIceCreamKindsRepository(mr);
//		IceCreamKind kind = repo.getIceCreamKind("Coppetta");
//		kind.toString();
//	}	

	@Test(expected = BadFileFormatException.class)
	public void testTextParamError_MissingKind() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("- 1,00 - 1 -10\nCoppetta - 2,00- 2 -5\n");
		new MyIceCreamKindsRepository(mr);
	}	

	@Test(expected = BadFileFormatException.class)
	public void testTextParamError_MissingNewLine() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("Cono- 1,00 - 1 -10 Coppetta - 2,00- 2 -5\n");
		new MyIceCreamKindsRepository(mr);
	}	

	@Test(expected = BadFileFormatException.class)
	public void testTextParamError_MissingMaxWeight() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("Cono- 1,00 - 1 -\nCoppetta - 2,00- 2 -5\n");
		new MyIceCreamKindsRepository(mr);
	}

	@Test(expected = BadFileFormatException.class)
	public void testTextParamError_MissingPrice() throws IOException, BadFileFormatException {
		Reader mr = new StringReader("Cono - - 1 - 10\nCoppetta - 2,00- 2 -5\n");
		new MyIceCreamKindsRepository(mr);
	}	

	@Test(expected = IllegalArgumentException.class)
	public void testTextParamError_BadNullParam() throws IOException, BadFileFormatException {
		new MyIceCreamKindsRepository(null);
	}
}

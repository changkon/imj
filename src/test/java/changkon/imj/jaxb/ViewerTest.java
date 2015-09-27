package changkon.imj.jaxb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import changkon.imj.domain.Gender;
import changkon.imj.dto.Viewer;

public class ViewerTest {

	@Test
	public void testUnMarshal() {
		String xml = "<viewer id=\"1\">"
				+ "<first-name>Chang Kon</first-name>"
				+ "<last-name>Han</last-name>"
				+ "<age>20</age>"
				+ "<gender>male</gender>"
				+ "<country>South Korea</country>"
				+ "</viewer>";
		
		Viewer viewer = JAXB.unmarshal(xml, Viewer.class);
		
		assertEquals(new Long(1), viewer.getId());
		assertEquals("Chang Kon", viewer.getFirstName());
		assertEquals("Han", viewer.getLastName());
		assertEquals(20, viewer.getAge());
		assertEquals(Gender.MALE, viewer.getGender());
		assertEquals("South Korea", viewer.getCountry());
	}

	@Test
	public void testMarshal() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<viewer id=\"1\">"
				+ "<first-name>Chang Kon</first-name>"
				+ "<last-name>Han</last-name>"
				+ "<age>20</age>"
				+ "<gender>male</gender>"
				+ "<country>South Korea</country>"
				+ "</viewer>";

		Viewer viewer = new Viewer();
		viewer.setId(new Long(1));
		viewer.setAge(20);
		viewer.setFirstName("Chang Kon");
		viewer.setLastName("Han");
		viewer.setGender(Gender.MALE);
		viewer.setCountry("South Korea");

		String xmlVersion = JAXB.marshal(viewer, Viewer.class);
		assertEquals(xml, xmlVersion);
	}

}

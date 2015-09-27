package changkon.imj.jaxb;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;

/**
 * <p>Takes in object and logger and prints to log</p>
 * <p>Helper class to debug</p>
 * @author Chang Kon Han
 * @see http://stackoverflow.com/questions/26139999/how-to-print-jaxb-to-java-object-in-to-logger
 */

public class JAXB {
	
	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(String xml, Class<T> tC) {

		try {
			JAXBContext jc = JAXBContext.newInstance(tC);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			
			StringReader reader = new StringReader(xml);
			
			return (T)unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static <T> String marshal(T t, Class<T> tC) {
		
		try {
			JAXBContext jc = JAXBContext.newInstance(tC);
			Marshaller marshaller = jc.createMarshaller();
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(t, stringWriter);
			
			return stringWriter.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public static <T> void prettyPrint(T t, Class<T> tC, Logger logger) {
		try {
			JAXBContext jc = JAXBContext.newInstance(tC);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(t, stringWriter);
			logger.info(stringWriter.toString());
		} catch (JAXBException e) {
			logger.error(e.getMessage());
		}
		
	}
}

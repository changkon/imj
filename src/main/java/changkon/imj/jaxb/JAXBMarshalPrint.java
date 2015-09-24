package changkon.imj.jaxb;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;

/**
 * <p>Takes in object and logger and prints to log</p>
 * <p>Helper class to debug</p>
 * @author Chang Kon Han
 * @see http://stackoverflow.com/questions/26139999/how-to-print-jaxb-to-java-object-in-to-logger
 */

public class JAXBMarshalPrint {
	
	public static <T> void marshalPrint(T t, Class<T> tC, Logger logger) {
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

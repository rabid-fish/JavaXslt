package com.github.rabid_fish.jaxb;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import com.github.rabid_fish.Contact;

/**
 * Written with help from:
 * http://www.mkyong.com/java/jaxb-hello-world-example/
 * http://stackoverflow.com/questions/4146540/how-to-marshal-an-object-via-jaxb-without-any-information-about-it
 * http://blog.bdoughan.com/2010/08/using-xmlanyelement-to-build-generic.html
 */
public class JaxbUtil {

	private static final JAXBContext JAXB_CONTEXT;
	static {
		try {
			JAXB_CONTEXT = JAXBContext.newInstance(new Class[]{ 
					JaxbListWrapper.class,
					Contact.class,
			});
		} catch (JAXBException e) {
			// Cause the app to keel over, which is a good thing, 
			// because if we can't create a context we best give up.
			throw new RuntimeException(e);
		}
	}
	
	public static Marshaller createMarshaller() throws JAXBException, PropertyException {
		Marshaller marshaller = JAXB_CONTEXT.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		return marshaller;
	}
	
	public static void marshallList(List<?> list) throws PropertyException, JAXBException {
		
		JaxbListWrapper jaxbList = new JaxbListWrapper(list);
		
		StringWriter writer = new StringWriter();
		Marshaller marshaller = createMarshaller();
		marshaller.marshal(jaxbList, writer);
		System.out.println(writer);
	}
}

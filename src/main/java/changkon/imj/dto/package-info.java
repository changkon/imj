@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(type=DateTime.class,
        value=DateTimeAdapter.class)
})

@org.hibernate.annotations.GenericGenerator(
 name = "ID_GENERATOR",
 strategy = "enhanced-sequence"
)
package changkon.imj.dto;

import org.joda.time.DateTime;

import changkon.imj.jaxb.DateTimeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
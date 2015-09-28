package changkon.imj.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="viewers")
@XmlAccessorType(XmlAccessType.FIELD)
public class Viewers {

	@XmlElement(name="viewer")
	private List<Viewer> viewer;
	
	public Viewers() {}

	public List<Viewer> getViewers() {
		return viewer;
	}

	public void setViewers(List<Viewer> viewer) {
		this.viewer = viewer;
	}
	
}

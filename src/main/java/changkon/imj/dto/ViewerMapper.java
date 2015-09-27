package changkon.imj.dto;

import changkon.imj.domain.Viewer;

public class ViewerMapper {

	public static Viewer toDomainModel(changkon.imj.dto.Viewer dtoViewer) {
		Viewer viewer = new Viewer();
		
		viewer.setId(dtoViewer.getId());
		viewer.setAge(dtoViewer.getAge());
		viewer.setCountry(dtoViewer.getCountry());
		viewer.setFirstName(dtoViewer.getFirstName());
		viewer.setLastName(dtoViewer.getLastName());
		viewer.setGender(dtoViewer.getGender());
		
		return viewer;
	}
	
	public static changkon.imj.dto.Viewer toDTOModel(Viewer viewer) {
		changkon.imj.dto.Viewer dtoViewer = new changkon.imj.dto.Viewer();
		
		dtoViewer.setAge(viewer.getAge());
		dtoViewer.setId(viewer.getId());
		dtoViewer.setCountry(viewer.getCountry());
		dtoViewer.setFirstName(viewer.getFirstName());
		dtoViewer.setLastName(viewer.getLastName());
		dtoViewer.setGender(viewer.getGender());
		
		return dtoViewer;
	}
	
}

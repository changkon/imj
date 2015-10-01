package changkon.imj.mapper;

import org.joda.time.DateTime;

import changkon.imj.dto.Log;

public class LogMapper {

	public static changkon.imj.domain.Log toDomainModel(Log log) {
		changkon.imj.domain.Log domainModel = new changkon.imj.domain.Log();

		domainModel.setId(log.getId());
		domainModel.setDate(log.getDate().toDate());
		domainModel.setGeoLocation(log.getGeolocation());
		domainModel.setMovie(MovieMapper.toDomainModel(log.getMovie()));
		domainModel.setViewer(ViewerMapper.toDomainModel(log.getViewer()));
		
		return domainModel;
	}
	
	public static Log toDTOModel(changkon.imj.domain.Log domainLog) {
		Log dtoLog = new Log();
		
		dtoLog.setId(domainLog.getId());
		dtoLog.setDate(new DateTime(domainLog.getDate()));
		dtoLog.setGeoLocation(domainLog.getGeoLocation());
		dtoLog.setMovie(MovieMapper.toDTOModel(domainLog.getMovie()));
		dtoLog.setViewer(ViewerMapper.toDTOModel(domainLog.getViewer()));
		
		return dtoLog;
	}
	
}

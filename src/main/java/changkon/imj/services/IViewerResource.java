package changkon.imj.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

import changkon.imj.domain.Genre;
import changkon.imj.dto.Log;
import changkon.imj.dto.Viewer;
import changkon.imj.dto.ViewerLogs;
import changkon.imj.dto.ViewerRecommendedMovies;
import changkon.imj.dto.Viewers;

@Path("/viewer")
public interface IViewerResource {

	@POST
	@Consumes({"application/xml", "application/json"})
	public Response createViewer(Viewer viewer);
	
	@DELETE
	@Path("{id:\\d+}")
	public void deleteViewer(@PathParam("id") long id);
	
	@PUT
	@Consumes({"application/xml", "application/json"})
	@Path("{id:\\d+}")
	public void updateViewer(@PathParam("id") long id, Viewer viewer);
	
	@GET
	@Produces({"application/xml", "application/json"})
	@Path("{id:\\d+}")
	public Viewer queryViewer(@PathParam("id") long id);
	
	@GET
	@Produces({"application/xml", "application/json"})
	public Viewers queryViewerList();
	
	@GET
	@Produces({"application/xml", "application/json"})
	public Viewers queryViewerList(@QueryParam("startId") int start, @QueryParam("size") int size);
	
	@POST
	@Consumes({"application/xml", "application/json"})
	@Path("{id:\\d+}/log")
	public Response createLog(@PathParam("id") long id, Log log);
	
	@GET
	@Produces({"application/xml", "application/json"})
	@Path("{id:\\d+}/log")
	public ViewerLogs queryLogs(@PathParam("id") long viewerId);
	
	@PUT
	@Consumes({"application/xml", "application/json"})
	@Path("{id:\\d+}/recommended")
	public void updateRecommended(@PathParam("id") long viewerId, ViewerRecommendedMovies recommendedMovies);
	
	@GET
	@Produces({"application/xml", "application/json"})
	@Path("{id:\\d+}/recommended")
	public ViewerRecommendedMovies queryRecommended(@PathParam("id") long viewerId);
	
	@GET
	@Produces("text/plain")
	@Path("{viewerId:\\d+}/recommended/{movieId:\\d+}")
	public void movieNotification(@PathParam("viewerId") final long viewerId, @PathParam("movieId") final long movieId, final @Suspended AsyncResponse response);

}

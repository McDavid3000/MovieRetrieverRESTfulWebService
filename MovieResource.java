package movierestfulservice;

import java.util.Collection;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 * Restful web service resource for movie DB
 * Contains GET and PUT requests to retrieve and add data
 * @author Devin Grant-Miles
 */
@Named 
@Path("/movies") 
public class MovieResource {

    @EJB
    private MovieDAOBean movieBean;
    @Context
    private UriInfo context;
    private static final char QUOTE = '\"';

    /**
     * Creates a new instance of MovieResource
     */
    public MovieResource() {
    }

    //Deals with any requests with no user input/no query
    //returns all movies to user
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getAllMovies() {
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buffer.append("<movies uri=").append(QUOTE).append(
                context.getAbsolutePath()).append(QUOTE).append(">");
        
        Collection<Movie> allMovies = movieBean.getMovieList();
        
        for (Movie movie : allMovies) {
            buffer.append(movie.getXMLString());
        } 
        buffer.append("</movies>");
        
        return buffer.toString();
    }

    //Request with user input searches using the param as a title 
    //returns a list of similar movies to what the users has input
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{title}")
    public String getMoviesByGenre(@PathParam("title") String movieTitle) {
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buffer.append("<movies uri=").append(QUOTE).append(
                context.getAbsolutePath()).append(QUOTE).append(">");
        
        Collection<Movie> similarMovies = movieBean.getSimilarMoviesList(movieTitle);
        
        for (Movie movie : similarMovies) {
            buffer.append(movie.getXMLString());
        }
        buffer.append("</movies>");
        
        return buffer.toString();
    }

    //Request with both movie title and year
    //Returns a single matching movie
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{title}/{year}")
    public String getSpecificMovie(@PathParam("title") String movieTitle, @PathParam("year") int year) {
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buffer.append("<movies uri=").append(QUOTE).append(
                context.getAbsolutePath()).append(QUOTE).append(">");

        Movie movie = movieBean.getMovieByTitleYear(movieTitle, year);

        
        buffer.append(movie.getXMLString());
        buffer.append("</movies>");

        return buffer.toString();
    }

    //Adds a rating to the movie corresponding to the path params title and year
    @PUT
    @Path("{title}/{year}/{rating}")
    public void updateMovieRating(@PathParam("title") String movieTitle, @PathParam("year") int year, @PathParam("rating") int rating) 
    {
        movieBean.updateMovieRating(movieTitle, year, rating);
    }

}

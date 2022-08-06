package movierestfulservice;

import java.util.List;

/**
 * Interface for a Movie Data Access Object (EJB)
 *
 * @author Devin Grant-Miles
 */
public interface MovieDAO {

    public List<Movie> getMovieList();

    public List<Movie> getSimilarMoviesList(String userInput);

    public List<Movie> getSimilarMovies(Movie userInput);

    public Movie getMovieByTitleYear(String title, int year);

    public void updateMovieRating(String title, int year, int rating);
}

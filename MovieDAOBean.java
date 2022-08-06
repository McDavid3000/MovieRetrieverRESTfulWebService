package movierestfulservice;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * EJB/Data Access Object for Movies objects
 * contains functions for retrieving and updating 
 * information in the database
 *
 * @author Devin Grant-Miles
 */
@WebService
@Stateless 
@LocalBean
public class MovieDAOBean implements MovieDAO {

    @PersistenceContext
    private EntityManager entityManager;

    //method that returns a list of all movies in database
    @Override
    public List<Movie> getMovieList() {
        List<Movie> movieList = new ArrayList();

        String jpqlCommand = "SELECT p FROM Movie p";
        Query query = entityManager.createQuery(jpqlCommand);
        movieList = query.getResultList();

        return movieList;
    }

    //method that returns that a list of movies similar 
    //to the first movie that matched the user's input 
    @Override
    public List<Movie> getSimilarMoviesList(String userInput) {
        List<Movie> movieList = new ArrayList(); 
        
        String jpqlCommand = "SELECT p FROM Movie p WHERE p.title LIKE :userInput";
        Query query = entityManager.createQuery(jpqlCommand);
        query.setParameter("userInput", "%" + userInput + "%");

        movieList = query.getResultList();
        movieList = getSimilarMovies(movieList.get(0)); 

        return movieList;
    }

    //Returns a single movie retrived from the DB that matches the inputted movie title and year
    @Override
    public Movie getMovieByTitleYear(String title, int year) {
        List<Movie> movieList = new ArrayList();
        
        String jpqlCommand = "SELECT p FROM Movie p WHERE p.title LIKE :title AND p.yearMade LIKE :year";
        Query query = entityManager.createQuery(jpqlCommand);
        query.setParameter("title", "%" + title + "%");
        query.setParameter("year", year);

        movieList = query.getResultList();

        return movieList.get(0);
    }

    //Simple algorithm that takes in a movie and returns a list of 
    //similar movies based on matching movie genres
    @Override
    public List<Movie> getSimilarMovies(Movie movie) {
        List<Movie> movieList = new ArrayList();
        List<Movie> genrelist = new ArrayList();
        String genre1 = movie.getGenre1();
        String genre2 = movie.getGenre2();
        String rRated = movie.getrRated();
        if (genre2.equals("")) {
            genre2 = "dontmatch";
        }

        String jpqlCommand = "SELECT p FROM Movie p WHERE p.genre1 LIKE :genre1 AND p.rRated LIKE :rRated";
        Query query = entityManager.createQuery(jpqlCommand);
        query.setParameter("genre1", "%" + genre1 + "%");
        query.setParameter("rRated", "%" + rRated + "%");
        genrelist = query.getResultList();
        for (Movie nextMovie : genrelist) {
            movieList.add(nextMovie);
        }

        jpqlCommand = "SELECT p FROM Movie p WHERE p.genre2 LIKE :genre1 AND p.rRated LIKE :rRated";
        query = entityManager.createQuery(jpqlCommand);
        query.setParameter("genre1", "%" + genre1 + "%");
        query.setParameter("rRated", "%" + rRated + "%");
        genrelist = query.getResultList();
        for (Movie nextMovie : genrelist) {
            movieList.add(nextMovie);
        }

        jpqlCommand = "SELECT p FROM Movie p WHERE p.genre1 LIKE :genre2 AND p.rRated LIKE :rRated";
        query = entityManager.createQuery(jpqlCommand);
        query.setParameter("genre2", "%" + genre2 + "%");
        query.setParameter("rRated", "%" + rRated + "%");
        genrelist = query.getResultList();
        for (Movie nextMovie : genrelist) {
            movieList.add(nextMovie);
        }

        jpqlCommand = "SELECT p FROM Movie p WHERE p.genre2 LIKE :genre2 AND p.rRated LIKE :rRated";
        query = entityManager.createQuery(jpqlCommand);
        query.setParameter("genre2", "%" + genre2 + "%");
        query.setParameter("rRated", "%" + rRated + "%");
        genrelist = query.getResultList();
        for (Movie nextMovie : genrelist) {
            movieList.add(nextMovie);
        }

        return movieList;
    }

    //Updates the rating of the movie in the database that matches the inputted title and year 
    @Override
    public void updateMovieRating(String title, int year, int rating) {
        List<Movie> movieList = new ArrayList();
        String jpqlCommand = "SELECT p FROM Movie p WHERE p.title LIKE :title AND p.yearMade LIKE :year";
        Query query = entityManager.createQuery(jpqlCommand);
        query.setParameter("title", "%" + title + "%");
        query.setParameter("year", year);

        movieList = query.getResultList();

        float currentRating = movieList.get(0).getUserRating();
        int currentNumRating = movieList.get(0).getNumberOfRatings();

        float newRating = ((currentRating * currentNumRating) + rating) / (currentNumRating + 1);
        currentNumRating++;

        movieList.get(0).setNumberOfRatings(currentNumRating);
        movieList.get(0).setUserRating(newRating);

        this.entityManager.persist(movieList.get(0));
        this.entityManager.persist(movieList.get(0));
    }
}

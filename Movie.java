package movierestfulservice;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Java class that represents a Movie object Uses entities to manage
 * interactions with database Based on petrestfulservice
 *
 * @author Devin Grant-Miles
 */
@Entity
@Table(name = "moviedb")
public class Movie implements Serializable {

    @Id
    @Column(name = "title")
    private String title;
    @Column(name = "genre1")
    private String genre1;
    @Column(name = "genre2")
    private String genre2;
    @Column(name = "subgenre1")
    private String subgenre1;
    @Column(name = "subgenre2")
    private String subgenre2;
    @Column(name = "subgenre3")
    private String subgenre3;
    @Column(name = "star1")
    private String star1;
    @Column(name = "star2")
    private String star2;
    @Column(name = "star3")
    private String star3;
    @Column(name = "star4")
    private String star4;
    @Column(name = "length")
    private String length;//need to rename
    @Column(name = "rrated")
    private String rRated;
    @Column(name = "year")
    private int yearMade;//need to rename
    @Column(name = "imdbrating")
    private float imdbRating;
    @Column(name = "userRating")
    private float userRating;
    @Column(name = "numberOfRatings")
    private int numberOfRatings;

    public Movie() {
    }

    public Movie(String title, String genre1, String genre2, String subgenre1, String subgenre2,
            String subgenre3, String star1, String star2, String star3, String star4, String rRated,
            int year, float imdbRating, String length, float userRating, int numberOfRatings) {
        this.rRated = rRated;
        this.title = title;
        this.genre1 = genre1;
        this.genre2 = genre2;
        this.imdbRating = imdbRating;
        this.length = length;
        this.star1 = star1;
        this.star2 = star2;
        this.star3 = star3;
        this.star4 = star4;
        this.yearMade = year;
        this.subgenre1 = subgenre1;
        this.subgenre2 = subgenre2;
        this.subgenre3 = subgenre3;
        this.userRating = userRating;
        this.numberOfRatings = numberOfRatings;
    }

    public void setYearMade(int yearMade) {
        this.yearMade = yearMade;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public int getYearMade() {
        return yearMade;
    }

    public float getUserRating() {
        return userRating;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre1() {
        return genre1;
    }

    public void setGenre1(String genre1) {
        this.genre1 = genre1;
    }

    public String getGenre2() {
        return genre2;
    }

    public void setGenre2(String genre2) {
        this.genre2 = genre2;
    }

    public String getSubgenre1() {
        return subgenre1;
    }

    public void setSubgenre1(String subgenre1) {
        this.subgenre1 = subgenre1;
    }

    public String getSubgenre2() {
        return subgenre2;
    }

    public void setSubgenre2(String subgenre2) {
        this.subgenre2 = subgenre2;
    }

    public String getSubgenre3() {
        return subgenre3;
    }

    public void setSubgenre3(String subgenre3) {
        this.subgenre3 = subgenre3;
    }

    public String getStar1() {
        return star1;
    }

    public void setStar1(String star1) {
        this.star1 = star1;
    }

    public String getStar2() {
        return star2;
    }

    public void setStar2(String star2) {
        this.star2 = star2;
    }

    public String getStar3() {
        return star3;
    }

    public void setStar3(String star3) {
        this.star3 = star3;
    }

    public String getStar4() {
        return star4;
    }

    public void setStar4(String star4) {
        this.star4 = star4;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getrRated() {
        return rRated;
    }

    public void setrRated(String rRated) {
        this.rRated = rRated;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }

    //converts movie object and fields to XML
    public String getXMLString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<movie>");
        buffer.append("<title>").append(getTitle()).append("</title>");
        buffer.append("<genre1>").append(getGenre1()).append("</genre1>");
        buffer.append("<genre2>").append(getGenre2()).append("</genre2>");
        buffer.append("<subgenre1>").append(getSubgenre1()).append("</subgenre1>");
        buffer.append("<subgenre2>").append(getSubgenre2()).append("</subgenre2>");
        buffer.append("<subgenre3>").append(getSubgenre3()).append("</subgenre3>");
        buffer.append("<star1>").append(getStar1()).append("</star1>");
        buffer.append("<star2>").append(getStar2()).append("</star2>");
        buffer.append("<star3>").append(getStar3()).append("</star3>");
        buffer.append("<star4>").append(getStar4()).append("</star4>");
        buffer.append("<length>").append(getLength()).append("</length>");
        buffer.append("<rRated>").append(getrRated()).append("</rRated>");
        buffer.append("<year>").append(getYearMade()).append("</year>");
        buffer.append("<imdbRating>").append(getImdbRating()).append("</imdbRating>");
        buffer.append("<userRating>").append(getUserRating()).append("</userRating>");
        buffer.append("<numberOfRatings>").append(getNumberOfRatings()).append("</numberOfRatings>");
        buffer.append("</movie>");
        return buffer.toString();
    }
}

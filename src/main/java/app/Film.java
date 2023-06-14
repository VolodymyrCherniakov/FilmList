package app;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

import utils.*;

import java.util.ArrayList;

/**
 * The `Film` class represents a film object with various properties such as name, genre, release year, and time length.
 * It also includes methods for setting and retrieving the film's information, calculating average rating, and checking for empty fields.
 */
public class Film {

    private String name;
    private String description;
    private FilmGenre genre;
    private double ratingAVG;
    private int year; // release year
    private LocalTime time;
    private ArrayList<Double> rating = new ArrayList<>();

    /**
     * Constructs an empty `Film` object.
     */
    public Film() {
        this.name = null;
        this.genre = null;
        this.year = 0;
        this.time = null;
        this.description = null;
    }

    /**
     * Constructs a `Film` object with the specified name.
     *
     * @param name the name (title) of the film
     */
    public Film(String name) {
        this.name = name;
        this.genre = null;
        this.year = 0;
        this.time = null;
        this.description = null;
    }

    /**
     * Constructs a `Film` object with the specified name, genre, release year, and time length.
     *
     * @param name  the name (title) of the film
     * @param genre the genre of the film
     * @param year  the release year of the film
     * @param time  the time length of the film
     */
    public Film(String name, FilmGenre genre, int year, LocalTime time) {
        this.name = name;
        //this.description = description;
        this.genre = genre;
        this.year = year;
        this.time = time;
    }

    /**
     * Returns the time length of the film.
     *
     * @return the time length of the film
     */
    public LocalTime getLength() {
        return time;
    }

    /**
     * Returns the time length of the film in seconds.
     *
     * @return the time length of the film in seconds
     */
    public int getLengthInSec() {
        return time.getHour() * 3600 + time.getMinute() * 60 + time.getSecond();
    }

    /**
     * Sets the time length of the film.
     *
     * @param length the time length of the film
     */
    public void setLength(LocalTime length) {
        this.time = length;
    }

    /**
     * Sets the time length of the film using the specified hours, minutes, and seconds.
     *
     * @param hours   the hours of the time length
     * @param minutes the minutes of the time length
     * @param seconds the seconds of the time length
     * @return 1 if the time length is valid and set successfully, -1 otherwise
     */
    public int setLength(int hours, int minutes, int seconds) {
        if (isTimeValid(hours, minutes, seconds)) {
            this.time = LocalTime.of(hours, minutes, seconds);
            return 1;
        }
        return -1;
    }

    /**
     * Checks if the specified hours, minutes, and seconds form a valid time.
     *
     * @param hours   the hours of the time
     * @param minutes the minutes of the time
     * @param seconds the seconds of the time
     * @return true if the time is valid, false otherwise
     */
    public static boolean isTimeValid(int hours, int minutes, int seconds) {
        try {
            LocalTime.of(hours, minutes, seconds);
        } catch (DateTimeException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns the name of the film.
     *
     * @return the name of the film
     */
    public String getName() {
        return name;
    }


    /**
     * Returns the name of the film in lower case.
     *
     * @return returns the name of the film in lower case
     */
    public String getNameLowerCase() {
        return name.toLowerCase();
    }

    /**
     * Sets the name of the film.
     *
     * @param name the name of the film
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the array with rates of the film.
     *
     * @return the array with rates of the film
     */
    public ArrayList<Double> getRates() {
        return rating;
    }

    /**
     * Returns the description of the film.
     *
     * @return the description of the film
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the film.
     *
     * @param description   the description of the film
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the genre of the film.
     *
     * @return the genre of the film
     */
    public FilmGenre getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the film.
     *
     * @param genre     the genre of the film
     */
    public void setGenre(FilmGenre genre) {
        this.genre = genre;
    }

    /**
     * Returns the average rating for the film.
     *
     * @return The average rating for the film.
     */
    public double getRatingAVG() {
        return ratingAVG;
    }

    /**
     * Rates the film with the given rating.
     *
     * @param rating The rating to be added.
     * @return 1 if the rating is valid and added successfully, -1 otherwise.
     */
    public int rate(double rating) {
        if (rating > 0 && rating <= 5) {
            this.rating.add(rating);
            calculateAVG();
            return 1;
        }
        return -1;
    }

    /**
     * Calculates the average rating for the film.
     * This method should be called after adding new ratings.
     */
    private void calculateAVG() {
        double res = 0;
        for (int i = 0; i < this.rating.size(); i++) {
            res += this.rating.get(i);
        }
        this.ratingAVG = res / this.rating.size();
    }

    /**
     * Returns the release year of the film.
     *
     * @return The release year of the film.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the release year of the film.
     *
     * @param year The release year to be set.
     */
    public void setYear(int year) {
        this.year = year;
    }


    /**
     * Checks if any of the film's properties is empty.
     *
     * @return true if any of the properties is empty, false otherwise
     */
    public boolean isEmpty() {
        if (this.genre != null && this.year != 0 && this.time != null && this.description != null) {
            return false;
        }
        return true;
    }

    /**
     * Returns the main information of the film as a formatted string.
     *
     * @return the main information about the film
     */
    public String mainInfo() {
        return String.format("%25S\t%20S\t%10S\t%4d\n", this.name, this.genre, this.time, this.year);
    }

    /**
     * Returns a string representation of the film.
     *
     * @return a string representation of the film
     */
    @Override
    public String toString() {
        return String.format("name: %S\nrating: %.1f\ngenre: %S\nlength: %S\nrelease year: %d\n",
                this.name, this.ratingAVG, this.genre, this.time, this.year);
    }

    /**
     * Returns the time length of the film formatted according to the specified DateTimeFormatter.
     *
     * @param dtf the DateTimeFormatter to format the time length
     * @return the formatted time length of the film
     */
    public String getLength(DateTimeFormatter dtf) {
        return time.format(dtf);
    }

}



package utils;

import app.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * The ProgramInterface interface defines the contract for a film program interface.
 * It specifies methods for managing and manipulating films.
 */
public interface ProgramInterface {

    /**
     * Adds a film to the program.
     *
     * @param f The film to be added.
     */
    public void addFilm(Film f);

    /**
     * Retrieves the film at the specified index.
     *
     * @param index The index of the film to retrieve.
     * @return The film at the specified index.
     */
    public Film get(int index);

    /**
     * Retrieves the index of the film with the specified name.
     *
     * @param line The name of the film.
     * @return The index of the film, or -1 if the film is not found.
     */
    public int getIndex(String line);

    /**
     * Retrieves the film with the specified name.
     *
     * @param line The name of the film.
     * @return The film with the specified name, or null if the film is not found.
     */
    public Film get(String line);

    /**
     * Retrieves the number of films.
     *
     * @return The number of films.
     */
    public int getSize();

    /**
     * Deletes a film with the specified name.
     *
     * @param name The name of the film to be deleted.
     */
    public void deleteFilm(String name);

    /**
     * Sorts the films by film name in ascending order.
     */
    public void sortByFilmName();

    /**
     * Sorts the films by film length in ascending order.
     */
    public void sortByFilmLength();

    /**
     * Sorts the films by release date in ascending order.
     */
    public void sortByReleaseDate();

    /**
     * Sorts the films by rating in ascending order.
     */
    public void sortByRating();

    /**
     * Sorts the films by film name in descending order.
     */
    public void sortByFilmNameDescending();

    /**
     * Sorts the films by film length in descending order.
     */
    public void sortByFilmLengthDescending();

    /**
     * Sorts the films by release date in descending order.
     */
    public void sortByReleaseDateDescending();

    /**
     * Sorts the films by rating in descending order.
     */
    public void sortByRatingDescending();

    /**
     * Reads movie data from a file using other functions.
     *
     * @param path The path to the file containing the film data.
     * @return true if the file was successfully read and the data loaded, false otherwise.
     */
    public boolean readFile(String path);

    /**
     * Saves program data to a file using other functions.
     *
     * @param path   The path to the file to save the data.
     * @param choose An integer representing the choice of saving format.
     * @throws IOException if an I/O error occurs while saving the data.
     */
    public void saveData(String path, int choose) throws IOException;

    /**
     * Filters films by genre.
     *
     * @param genre The genre to filter by.
     * @return The filtered list of films with a particular genre.
     */
    public ArrayList<Film> filterByGenre(FilmGenre genre, ArrayList<Film> filteredFilms);

    /**
     *
     * @param minLength     The min time length of the range.
     * @param maxLength     The max time length of the range.
     * @return The filtered list of films with the time length in the given hour boundaries.
     */
    public ArrayList<Film> filterByTimeLength(LocalTime minLength, LocalTime maxLength, ArrayList<Film> filteredFilms);

    /**
     * Filters films by year range.
     *
     * @param fromYear The starting year of the range.
     * @param toYear   The ending year of the range.
     * @return The filtered list of films with the years in the given boundaries.
     */
    public ArrayList<Film> filterByReleaseYear(int  fromYear, int toYear, ArrayList<Film> filteredFilms);
}

package utils;

import app.*;
import java.io.IOException;
import java.util.ArrayList;

public interface ProgramInterface {

    public void addFilm(Film f);

    public Film get(int index);

    public int getIndex(String line);

    public Film get(String line);

    public int getSize();

    public void deleteFilm(String name);

    public void sortByFilmName();

    public void sortByFilmLength();

    public void sortByReleaseDate();

    public void sortByRating();

    public void sortByFilmNameDescending();

    public void sortByFilmLengthDescending();

    public void sortByReleaseDateDescending();

    public void sortByRatingDescending();
    public boolean readFile(String path);

    public void saveData(String path, int choose) throws IOException;

    public ArrayList<Film> filmFilterGenre(FilmGenre genre, ArrayList<Film> arr);

    public ArrayList<Film> filmFilterYear(int from, int to, ArrayList<Film> arr);
}

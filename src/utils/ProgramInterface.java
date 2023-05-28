/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package utils;

import app.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author aurfy
 */
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
    
    public void SaveData() throws IOException;
    
    public ArrayList<Film> filmFilter(FilmGenre genre, ArrayList<Film> arr);
}

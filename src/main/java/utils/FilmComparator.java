package utils;

import app.*;
import java.util.Comparator;

public class FilmComparator {

    public static Comparator<Film> byName() {
        return Comparator.comparing(Film::getNameLowerCase);
    }

    public static Comparator<Film> byLength() {
        return Comparator.comparing(Film::getLengthInSec);
    }

    public static Comparator<Film> byReleaseDate() {
        return Comparator.comparing(Film::getYear);
    }

    public static Comparator<Film> byRatingAVG() {
        return Comparator.comparing(Film::getRatingAVG);
    }

    public static Comparator<Film> byLengthDescending() {
        return Comparator.comparingInt(Film::getLengthInSec).reversed();
    }

    public static Comparator<Film> byReleaseDateDescending() {
        return Comparator.comparing(Film::getYear).reversed();
    }

    public static Comparator<Film> byRatingAVGDescending() {
        return Comparator.comparing(Film::getRatingAVG).reversed();
    }

    public static Comparator<Film> byNameDescending() {
        return Comparator.comparing(Film::getNameLowerCase).reversed();
    }
}
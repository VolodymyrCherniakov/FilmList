package utils;

import app.*;

import java.util.Comparator;

import org.apache.commons.collections4.ComparatorUtils;

/**
 * The FilmComparator class provides comparator methods for sorting films based on different criteria.
 */
public class FilmComparator {

    /**
     * Returns a comparator for sorting films by name in ascending order.
     *
     * @return A comparator for sorting films by name in ascending order.
     */
    public static Comparator<Film> byName() {
        return ComparatorUtils.chainedComparator(
                Comparator.comparing(Film::getNameLowerCase)
        );
    }

    /**
     * Returns a comparator for sorting films by length in ascending order.
     *
     * @return A comparator for sorting films by length in ascending order.
     */
    public static Comparator<Film> byLength() {
        return ComparatorUtils.chainedComparator(
                Comparator.comparing(Film::getLengthInSec)
        );
    }

    /**
     * Returns a comparator for sorting films by release date in ascending order.
     *
     * @return A comparator for sorting films by release date in ascending order.
     */
    public static Comparator<Film> byReleaseDate() {
        return ComparatorUtils.chainedComparator(
                Comparator.comparing(Film::getYear)
        );
    }

    /**
     * Returns a comparator for sorting films by average rating in ascending order.
     *
     * @return A comparator for sorting films by average rating in ascending order.
     */
    public static Comparator<Film> byRatingAVG() {
        return ComparatorUtils.chainedComparator(
                Comparator.comparing(Film::getRatingAVG)
        );
    }

    /**
     * Returns a comparator for sorting films by length in descending order.
     *
     * @return A comparator for sorting films by length in descending order.
     */
    public static Comparator<Film> byLengthDescending() {
        return ComparatorUtils.chainedComparator(
                Comparator.comparingInt(Film::getLengthInSec).reversed()
        );
    }

    /**
     * Returns a comparator for sorting films by release date in descending order.
     *
     * @return A comparator for sorting films by release date in descending order.
     */
    public static Comparator<Film> byReleaseDateDescending() {
        return ComparatorUtils.chainedComparator(
                Comparator.comparing(Film::getYear).reversed()
        );
    }

    /**
     * Returns a comparator for sorting films by average rating in descending order.
     *
     * @return A comparator for sorting films by average rating in descending order.
     */
    public static Comparator<Film> byRatingAVGDescending() {
        return ComparatorUtils.chainedComparator(
                Comparator.comparing(Film::getRatingAVG).reversed()
        );
    }

    /**
     * Returns a comparator for sorting films by name in descending order.
     *
     * @return A comparator for sorting films by name in descending order.
     */
    public static Comparator<Film> byNameDescending() {
        return ComparatorUtils.chainedComparator(
                Comparator.comparing(Film::getNameLowerCase).reversed()
        );
    }

}
package ui;

import app.Film;
import app.FilmList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import utils.*;

/**
 *
 * @author aurfy
 */
public class MenuUI {

    static Scanner sc = new Scanner(System.in);
    private ProgramInterface program;

    public void start() throws IOException {
        boolean progWorks = true;
        while (progWorks) {
            program = new FilmList();
            //write the main start function;

            mainMenu();
            System.out.println("Did you want to start again? y/n");
            progWorks = sc.next().equalsIgnoreCase("y");
            sc.nextLine();
        }
    }

    public void mainMenu() throws IOException {
        boolean job = true;
        PrintFilmList();
        while (job) {
            System.out.println("""
                               \nEnter the choice:
                               1 - Add the film
                               2 - Delete the film
                               3 - Choose/change the film
                               4 - Display all movies
                               5 - Sort movies
                               6 - Filter the movies
                               7 - Save to file
                               0 - Exit""");
            switch (sc.nextInt()) {
                case 1:
                    addFilmToList();
                    break;
                case 2:
                    deleteFilmFromList();
                    break;
                case 3:
                    selectFilm();
                    break;
                case 4:
                    PrintFilmList();
                    break;
                case 5:
                    SortFilms();
                    break;
                case 6:
                    FilterFilms();
                    break;
                case 7:
                    saveData();
                    break;
                case 0:
                    job = false;
                    break;
                default:
                    System.out.println("Wrong choice, try again");
                    break;
            }
        }
    }

    private void addFilmToList() {
        System.out.print("Enter the name of film:");
        sc.skip("[\r\n]+");
        String line = sc.nextLine();
        Film temp = new Film(line);
        System.out.println("The name was accepted");
        boolean end = false;
        while (!end) {
            end = !temp.isEmpty();
            System.out.println("""
                                               \nYou can add something, enter the choice:
                                               1 - Genre
                                               2 - Release date
                                               3 - Time length
                                               4 - Description
                                               0 - Don't add more""");
            switch (sc.nextInt()) {
                case 1:
                    temp.setGenre(сhooseGenre());
                    System.out.println("The genre was accepted");
                    break;
                case 2:
                    System.out.print("Enter the release year: ");
                    temp.setYear(sc.nextInt());
                    System.out.println("Release date was accepted");
                    break;
                case 3:
                    System.out.print("Enter the time length of film (in the format: hours minutes seconds): ");
                    if (temp.setLength(sc.nextInt(), sc.nextInt(), sc.nextInt()) == 1) {
                        System.out.println("Time length was accepted");
                    } else {
                        System.out.println("Wrong time format");
                    }
                    break;
                case 4:
                    System.out.print("Enter the description to the film: ");
                    sc.skip("[\r\n]+");
                    line = sc.nextLine();
                    temp.setDescription(line);
                    break;
                case 0:
                    end = true;
                    break;
                default:
                    System.out.println("Wrong choice, try again");
                    break;
            }
        }
        program.addFilm(temp);
    }

    private void deleteFilmFromList() {
        System.out.print("Enter the title of the movie you want to delete: ");
        sc.skip("[\r\n]+");
        String line = sc.nextLine();
        Film temp = program.get(line);
        if (temp == null) {
            System.out.println("Wrong title");
        } else {
            program.deleteFilm(line);
        }
    }

    private void selectFilm() {
        boolean end = false;
        int index;
        String line;
        while (!end) {
            System.out.print("Enter the title of the movie you want change: ");
            sc.skip("[\r\n]+");
            line = sc.nextLine();
            index = program.getIndex(line);
            if (index == -1) {
                System.out.println("Wrong title");
                System.out.println("Do you want to continue:\n1 - Yes\n2 - No");
                if (sc.nextInt() != 1) {
                    end = true;
                }
            } else {
                changeFilm(index);
                end = true;
            }
        }

    }

    private void changeFilm(int index) {
        boolean end = false;
        while (!end) {
            System.out.println(program.get(index));
            PrintDescription(index);
            System.out.println("""
                                               \nWhat you want to change/add:
                                               1 - Name
                                               2 - Genre
                                               3 - Length of movie
                                               4 - Release year of movie
                                               5 - Description to the movie
                                               6 - Rate the movie
                                               0 - Save and back""");
            switch (sc.nextInt()) {
                case 1:
                    System.out.print("Enter the new title of movie: ");
                    sc.skip("[\r\n]+");
                    program.get(index).setName(sc.nextLine());
                    System.out.println("New title was accepted");
                    break;
                case 2:
                    program.get(index).setGenre(сhooseGenre());
                    System.out.println("New genre was accepted");
                    break;
                case 3:
                    System.out.print("Enter the time length of film (in the format: hours minutes seconds): ");
                    if (program.get(index).setLength(sc.nextInt(), sc.nextInt(), sc.nextInt()) == 1) {
                        System.out.println("New time length was accepted");
                    } else {
                        System.out.println("Wrong time format");
                    }
                    break;
                case 4:
                    System.out.print("Enter the release year: ");
                    program.get(index).setYear(sc.nextInt());
                    System.out.println("Year was accepted");
                    break;

                case 5:
                    System.out.print("Enter the description to the film: ");
                    sc.skip("[\r\n]+");
                    program.get(index).setDescription(sc.nextLine());
                    System.out.println("New description was accepted");
                    break;
                case 6:
                    System.out.println("Enter your rate from 0 to 5");
                    if (program.get(index).rate(sc.nextDouble()) == 1) {
                        System.out.println("You have successfully rated the film");
                    } else {
                        System.out.println("A grade is only possible from 0 to 5");
                    }
                    break;
                case 0:
                    end = true;
                    break;
                default:
                    System.out.println("Wrong choice, try again");
                    break;
            }
        }
    }

    private void PrintDescription(int index) {
        String line = program.get(index).getDescription();
        int rowLength = 80; // Maximum length of each row
        int startIndex = 0;

        int endIndex;
        while (startIndex < line.length()) {
            endIndex = Math.min(startIndex + rowLength, line.length());
            if (endIndex < line.length()) {
                while (endIndex > startIndex && !Character.isWhitespace(line.charAt(endIndex))) {
                    endIndex--;
                }
            }
            String row = line.substring(startIndex, endIndex);
            System.out.println(row);
            startIndex = endIndex;
        }
    }

    private void PrintFilmList() {
        //System.out.println("\nNumber\t\tName\t\tGenre\t\tLength\t\tRelease Date");
        System.out.printf("%S\t%25S\t%20S\t%10S\t%4S\n", "Number", "Name", "Genre", "Time", "Year");
        for (int i = 0; i < 85; i++) {
            System.out.print("-");
        }
        System.out.println("");
        for (int i = 0; i < program.getSize(); i++) {
            System.out.print((i + 1) + "\t");
            System.out.println(program.get(i).mainInfo());

        }
    }

    public FilmGenre сhooseGenre() {
        while (true) {
            System.out.println("""
            Enter the choice of the genre:
            A - Action
            C - Comedy
            D - Drama
            H - Horror
            R - Romance
            SF - Science-Fiction
            T - Thriller
            AN - Animation
            DO - Documentary
            F - Fantasy
            AD - Adventure
            HI - Historical""");
            sc.skip("[\r\n]+");
            String choice = sc.nextLine().trim().toUpperCase();
            switch (choice) {
                case "A":
                    return FilmGenre.ACTION;
                case "C":
                    return FilmGenre.COMEDY;
                case "D":
                    return FilmGenre.DRAMA;
                case "H":
                    return FilmGenre.HORROR;
                case "R":
                    return FilmGenre.ROMANCE;
                case "SF":
                    return FilmGenre.SCIENCE_FICTION;
                case "T":
                    return FilmGenre.THRILLER;
                case "AN":
                    return FilmGenre.ANIMATION;
                case "DO":
                    return FilmGenre.DOCUMENTARY;
                case "F":
                    return FilmGenre.FANTASY;
                case "AD":
                    return FilmGenre.ADVENTURE;
                case "HI":
                    return FilmGenre.HISTORICAL;
                default:
                    System.out.println("Wrong choice, try again");
                    break;
            }
        }
    }

    private void SortFilms() {
        boolean end = false;
        while (!end) {
            System.out.println("""
                               Enter the sorting method
                               1 - From A to Z
                               2 - From Z to A
                               3 - From newest to oldest
                               4 - From oldest to newest
                               5 - From best to worst
                               6 - From worst to best
                               7 - From shortest to longest
                               8 - From longest to shortest""");
            switch (sc.nextInt()) {
                case 1:
                    program.sortByFilmName();
                    end = true;
                    break;
                case 2:
                    program.sortByFilmNameDescending();
                    end = true;
                    break;
                case 3:
                    program.sortByReleaseDateDescending();
                    end = true;
                    break;
                case 4:
                    program.sortByReleaseDate();
                    end = true;
                    break;
                case 5:
                    program.sortByRatingDescending();
                    end = true;
                    break;
                case 6:
                    program.sortByRating();
                    end = true;
                    break;
                case 7:
                    program.sortByFilmLength();
                    end = true;
                    break;
                case 8:
                    program.sortByFilmLengthDescending();
                    end = true;
                    break;
                default:
                    System.out.println("Wrong choice, try again");
                    break;
            }
        }
        System.out.println("The sorting of the list was successful");
    }

    private void FilterFilms() {
        ArrayList<Film> filtered = new ArrayList<>();
        boolean end = false;
        while (!end) {
            FilmGenre genre = сhooseGenre();
            filtered = program.filmFilter(genre, filtered);
            PrintFiltered(filtered);
            System.out.println("Did you want to add something more? y/n");
            end = !sc.next().equalsIgnoreCase("y");
        }

    }

    private void PrintFiltered(ArrayList<Film> filtered) {
        System.out.printf("%S\t%25S\t%20S\t%10S\t%4S\n", "Number", "Name", "Genre", "Time", "Year");
        for (int i = 0; i < 85; i++) {
            System.out.print("-");
        }
        System.out.println("");
        int i = 1;
        for (Film f : filtered) {
            System.out.print(i + "\t");
            System.out.println(f.mainInfo());
            i++;
        }
    }

    private void saveData() throws IOException {
        try {
            program.SaveData();
        } catch (IOException e) {
            System.out.println("Problem with file");
        } catch (IllegalArgumentException e) {
            System.out.println("The data in the files are not valid");
            System.out.println(e.getMessage());
        }
    }
}
package ui;

import app.Film;
import app.FilmList;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import utils.*;

public class ConsoleUI {
    static Scanner sc = new Scanner(System.in);
    private ProgramInterface program;

    /**
     * Starts the program and handles its execution flow.
     * <p>
     * This method initializes the FilmList program, reads data from a file specified by the user,
     * displays the main menu, and allows the user to interact with the program. It continues to
     * execute until the user chooses to exit the program.
     *
     * @throws IOException If an I/O error occurs while reading the file or input from the user.
     */
    public void start() throws IOException {
        boolean end = false;
        program = new FilmList();
        while (!end) {
            System.out.println("Enter the name of the file from which you want to read data:");
            end = program.readFile(sc.nextLine());
        }
        end = false;
        while (!end) {
            mainMenu();
            System.out.println("Did you want to start again? y/n");
            end = !sc.next().equalsIgnoreCase("y");
            sc.nextLine();
        }
    }

    /**
     * Displays the main menu and handles user interaction.
     * <p>
     * This method presents the main menu options to the user and allows them to choose an action.
     * Based on the user's input, it performs the corresponding operation such as adding a film,
     * deleting a film, selecting a film, displaying the film list, sorting films, filtering films,
     * saving data to a file, or exiting the program.
     *
     * @throws IOException If an I/O error occurs while reading input from the user or saving data to a file.
     */
    public void mainMenu() throws IOException {
        boolean job = true;
        printFilmList();
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
            try {
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
                        printFilmList();
                        break;
                    case 5:
                        sortFilms();
                        break;
                    case 6:
                        filterFilms();
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
            } catch (Exception e) {
                System.err.println("An error occurred: wrong input");
                sc.nextLine(); // Consume the remaining input
            }
        }
    }

    private void addFilmToList() {
        sc.nextLine();
        String line;
        Film temp = new Film();
        boolean end = false;
        while (!end) {
            System.out.print("Enter the name of film:");
            line = sc.nextLine();
            if (program.get(line) == null) {
                temp = new Film(line);
                System.out.println("The name was accepted");
                addInfo(temp);
                end = true;
            } else {
                System.out.println("This film already exist, try again");
                System.out.println("Did you want to start again? y/n");
                end = !sc.next().equalsIgnoreCase("y");
                sc.nextLine();
            }
        }
    }

    /**
     * Adds additional information to a film object.
     * <p>
     * This method allows the user to add more details to a film object, such as genre, release date,
     * time length, and description. The user is prompted to enter the corresponding information and
     * the film object is updated accordingly.
     *
     * @param temp The film object to which additional information is to be added.
     */
    private void addInfo(Film temp) {
        boolean end = false;
        String line;
        while (!end) {
            end = !temp.isEmpty();
            System.out.println("""
                    \nYou can add something, enter the choice:
                    1 - Genre
                    2 - Release date
                    3 - Time length
                    4 - Description
                    0 - Don't add more""");

            try {
                switch (sc.nextInt()) {
                    case 1:
                        sc.nextLine();
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
            } catch (Exception e) {
                program.addFilm(temp);
                System.err.println("An error occurred: wrong input");
                System.out.println("If you want to add movie data, select 3");
                sc.nextLine();
            }
        }
        program.addFilm(temp);
    }

    /**
     * Deletes a film from the film list.
     * <p>
     * This method prompts the user to enter the title of the movie they want to delete. It then searches
     * for the film with the given title in the film list and removes it if found. If no film with the
     * specified title is found, an error message is displayed.
     */
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


    /**
     * Selects a film for further modifications.
     * <p>
     * This method prompts the user to enter the title of the movie they want to modify. It searches for the film
     * with the given title in the film list and allows the user to make changes to the film's details if it is found.
     * If no film with the specified title is found, an error message is displayed. The user has the option to continue
     * selecting films for modification or exit the selection process.
     */
    private void selectFilm() {
        boolean end = false;
        int index;
        String line;
        while (!end) {
            System.out.print("Enter the title of the movie you want change: ");
            sc.nextLine();
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

    /**
     * Modifies (change) the details of a film.
     * <p>
     * This method allows the user to change/add various details of a film, such as its title, genre, length, release year,
     * description, and rating. The method displays the current details of the film and prompts the user to select the
     * aspect they want to modify. The user can choose from a menu of options and make the necessary changes. Once the
     * modifications are made, the changes are saved and applied to the film. The user can choose to save the changes and
     * go back to the main menu.
     *
     * @param index The index of the film in the film list.
     */
    private void changeFilm(int index) {
        boolean end = false;
        while (!end) {
            System.out.println(program.get(index));
            printDescription(index);
            System.out.println("""
                    \nWhat you want to change/add:
                    1 - Name
                    2 - Genre
                    3 - Length of movie
                    4 - Release year of movie
                    5 - Description to the movie
                    6 - Rate the movie
                    0 - Save and back""");
            try {
                switch (sc.nextInt()) {
                    case 1:
                        System.out.print("Enter the new title of movie: ");
                        sc.skip("[\r\n]+");
                        program.get(index).setName(sc.nextLine());
                        System.out.println("New title was accepted");
                        break;
                    case 2:
                        sc.nextLine();
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
            } catch (Exception e) {
                System.err.println("An error occurred: wrong input");
                sc.nextLine(); // Consume the remaining input
            }
        }
    }

    /**
     * Prints the description of a film.
     * <p>
     * This method prints the description of a film in a formatted manner, ensuring that each line of the description does
     * not exceed a specified row length. The description is retrieved from the film at the given index in the film list.
     *
     * @param index The index of the film in the film list.
     */
    private void printDescription(int index) {
        String line = program.get(index).getDescription();
        int rowLength = 80;
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

    /**
     * Prints the list of films.
     * <p>
     * This method prints the list of films in a formatted manner, displaying the film number, name, genre, length, and release year.
     * The film list is retrieved from the program instance.
     */
    private void printFilmList() {
        //System.out.println("\nNumber\t\tName\t\tGenre\t\tLength\t\tRelease Date");
        System.out.printf("%S\t%21S\t%20S\t%10S\t%4S\n", "Number", "Name", "Genre", "Time", "Year");
        for (int i = 0; i < 85; i++) {
            System.out.print("-");
        }
        System.out.println("");
        for (int i = 0; i < program.getSize(); i++) {
            System.out.print((i + 1) + "\t");
            System.out.println(program.get(i).mainInfo());

        }
    }


    /**
     * Allows the user to choose a film genre.
     * <p>
     * This method displays a menu of film genres and prompts the user to enter their choice. The user's input is
     * case-insensitive and trimmed of leading and trailing whitespace. Once a valid genre choice is entered, the
     * corresponding FilmGenre enum value is returned.
     *
     * @return The chosen FilmGenre value representing the selected genre.
     */
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


    /**
     * Sorts the films in the program.
     * <p>
     * This method allows the user to choose a sorting method for the films. It displays a menu of sorting options and
     * prompts the user to enter their choice. Depending on the selected sorting method, the films in the program are
     * sorted accordingly. After the sorting is done, the sorted film list is printed.
     */
    private void sortFilms() {
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
            try {
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
            } catch (Exception e) {
                System.err.println("An error occurred: wrong input");
                sc.nextLine(); // Consume the remaining input
            }
        }
        System.out.println("The sorting of the list was successful");
        printFilmList();
    }


    /**
     * Filters the films in the program based on user criteria.
     * <p>
     * This method allows the user to filter the films in the program based on different criteria such as genre, release
     * year, and time length. The user is prompted to select the filter criteria from a menu. Once the criteria is chosen,
     * the corresponding filter method is called to filter the films. The filtered films are stored in an ArrayList and
     * printed.
     */
    private void filterFilms() {
        ArrayList<Film> filtered = new ArrayList<>();
        System.out.println("Enter what criteria to filter movies by\n1 - Genre\n2 - Release year\n3 - Time length\n0 - Delete all filters");
        try {
            switch (sc.nextInt()) {
                case 1:
                    filtered = genreFiltering(filtered);
                    break;
                case 2:
                    filtered = yearFiltering(filtered);
                    break;
                case 3:
                    filtered = timeFiltering(filtered);
                    break;
                case 0:
                    filtered = null;
                    break;
                default:
                    System.out.println("Wrong input, try again");
                    break;
            }
            if (filtered == null) {
                System.out.println("You haven't any filters");
                printFilmList();
            } else {
                System.out.println("\n\nYour filtered films:");
                printFiltered(filtered);
            }
        } catch (Exception e) {
            System.err.println("An error occurred: wrong input");
            sc.nextLine(); // Consume the remaining input
        }
    }

    /**
     * Filters the films by genre.
     * <p>
     * This method allows the user to filter the films by genre. The user is prompted to choose one or more genres from a
     * menu. Each selected genre is added to the filtered films list. The filtered films list is then returned.
     *
     * @param filtered The initial list of filtered films. This list can be empty or contain previously filtered films.
     * @return The filtered list of films by genre.
     */
    private ArrayList<Film> genreFiltering(ArrayList<Film> filtered) {
        boolean end = false;
        FilmGenre genre;
        sc.nextLine();
        while (!end) {
            genre = сhooseGenre();
            filtered = program.filterByGenre(genre, filtered);
            printFiltered(filtered);
            System.out.println("Would you like to add one more genre? (y/n)");
            String response = sc.nextLine().trim().toLowerCase();
            end = !response.equals("y");
        }
        return filtered;
    }

    /**
     * Filters the films by release year.
     * <p>
     * This method allows the user to filter the films based on the release year. The user is prompted to enter the start
     * and end years for filtering the films. Films released between the start and end years (inclusive) are added to the
     * filtered films list. The filtered films list is then returned.
     *
     * @param filtered The initial list of filtered films. This list can be empty or contain previously filtered films.
     * @return The filtered list of films by release year.
     * @throws Exception If there is an error in reading the input or performing the filtering operation.
     */
    private ArrayList<Film> yearFiltering(ArrayList<Film> filtered) throws Exception {
        int from, to;
        try {
            System.out.println("Enter the year from which you want to filter: ");
            from = sc.nextInt();
            System.out.println("Enter the year up to which you want to filter the data:");
            to = sc.nextInt();
            if (to > from) {
                filtered = program.filterByReleaseYear(from, to, filtered);
            }
        } catch (Exception e) {
            System.err.println("An error occurred: wrong input");
            sc.nextLine();
            return null;
        }
        return filtered;
    }


    /**
     * Filters films by time length.
     *
     * This method allows the user to filter films based on their time length. The user is prompted to enter the start time
     * and end time, and the films within that time range are filtered and returned.
     *
     * @param filtered The list of films to be filtered.
     * @return The filtered list of films based on the time length.
     */
    private ArrayList<Film> timeFiltering(ArrayList<Film> filtered) {
        LocalTime from = LocalTime.MIN, to = LocalTime.MAX;
        from = enterTime();
        if (from == null) {
            return null;
        } else {
            to = enterTime();
            if (to == null) {
                return null;
            } else {
                if (from.isBefore(to)) {
                    filtered = program.filterByTimeLength(from, to, filtered);
                } else {
                    System.out.println("Wrong time length input");
                }
            }
        }
        return filtered;
    }

    /**
     * Prompts the user to enter a time.
     * <p>
     * This method prompts the user to enter a time in the format of hours, minutes, and seconds separated by spaces.
     * The input is validated, and if a valid time is entered, a LocalTime object representing that time is returned.
     * If the input is invalid or an error occurs, null is returned.
     *
     * @return The LocalTime object representing the entered time, or null if the input is invalid.
     */
    private LocalTime enterTime() {
        int hh, mm, ss;
        boolean end = false;
        try {
            do {
                System.out.println("Enter the time to (in the format: hours minutes seconds (with spaces between))");
                hh = sc.nextInt();
                mm = sc.nextInt();
                ss = sc.nextInt();
                sc.nextLine();
                if (Film.isTimeValid(hh, mm, ss)) {
                    return LocalTime.of(hh, mm, ss);
                } else {
                    System.out.println("Wrong time format, do you want try again? (y/n)");
                    String response = sc.nextLine().trim().toLowerCase();
                    end = !response.equals("y");
                }
            } while (!end);
        } catch (Exception e) {
            System.err.println("An error occurred: wrong input");
            sc.nextLine();
            return null;
        }
        return null;
    }

    /**
     * Prints the filtered films.
     * <p>
     * This method prints the filtered films in a tabular format, displaying the film number, name, genre, time length,
     * and release year. The filtered films are provided as an ArrayList of Film objects.
     *
     * @param filtered The list of filtered films to be printed.
     */
    private void printFiltered(ArrayList<Film> filtered) {
        System.out.printf("%S\t%21S\t%20S\t%10S\t%4S\n", "Number", "Name", "Genre", "Time", "Year");
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

    /**
     * Saves the program data to a file.
     * <p>
     * This method allows the user to save the program data to a file. The user is prompted to enter the file path and choose
     * whether to save the data as a binary file or not. The data is then saved to the specified file.
     *
     * @throws IOException If an I/O error occurs while saving the data to the file.
     */
    private void saveData() throws IOException {
        System.out.println("Enter the path (The name of file):");
        sc.skip("[\r\n]+");
        String line = sc.nextLine();
        System.out.println("Do you want to save to a binary file?\n1 - Yes\n2 - No");
        try {
            int x = sc.nextInt();
            program.saveData(line, x);
            System.out.println("Files was succesfully saved");
        } catch (IOException e) {
            System.out.println("Problem with file");
        } catch (IllegalArgumentException e) {
            System.out.println("The data in the files are not valid");
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) throws IOException {
        ConsoleUI prog = new ConsoleUI();
        prog.start();
    }
}

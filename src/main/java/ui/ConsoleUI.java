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

    public void start() throws IOException {
        boolean end = false;
        program = new FilmList();
        while(!end){
            System.out.println("Enter the name of the file from which you want to read data:");
            end = program.readFile(sc.nextLine());
        }
        end = false;
        while (!end) {

            //write the main start function;

            mainMenu();
            System.out.println("Did you want to start again? y/n");
            end = !sc.next().equalsIgnoreCase("y");
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
            } catch (Exception e) {
                System.err.println("An error occurred: wrong input");
                sc.nextLine(); // Consume the remaining input
            }
        }
    }

    private void addFilmToList() {
        System.out.print("Enter the name of film:");
        sc.skip("[\r\n]+");
        String line = sc.nextLine();
        Film temp = new Film();
        boolean end = false;
        while(!end) {
            if (program.get(line) == null) {
                temp = new Film(line);
                System.out.println("The name was accepted");
                end = true;
            } else {
                System.out.println("This film already exist, try again");
            }
        }
        end = false;
        while (!end) {
            end = temp.isEmpty();
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
                program.addFilm(temp);
            } catch (Exception e) {
                System.err.println("An error occurred: wrong input");
                sc.nextLine(); // Consume the remaining input
            }
        }
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
            try {
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
            } catch (Exception e) {
                System.err.println("An error occurred: wrong input");
                sc.nextLine(); // Consume the remaining input
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
    }

    private void FilterFilms() {
        ArrayList<Film> filtered = new ArrayList<>();
            System.out.println("Enter what criteria to filter movies by\n1 - Genre\n2 - Release year\n3 - Time length");
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
                    default:
                        System.out.println("Wrong input, try again");
                        break;
                }
                PrintFiltered(filtered);
            } catch (Exception e) {
                System.err.println("An error occurred: wrong input");
                sc.nextLine(); // Consume the remaining input
            }
    }

    private ArrayList<Film> genreFiltering(ArrayList<Film> filtered) {
        boolean end = false;
        while(!end) {
            FilmGenre genre = сhooseGenre();
            filtered = program.filmFilterGenre(genre, filtered);
            System.out.println("Did you want to start again? y/n");
            end = !sc.next().equalsIgnoreCase("y");
            sc.nextLine();
        }
        return filtered;
    }

    private ArrayList<Film> yearFiltering(ArrayList<Film> filtered) {
        int from, to;
        System.out.println("Enter the year from which you want to filter: ");
        from = sc.nextInt();
        System.out.println("Enter the year up to which you want to filter the data:");
        to = sc.nextInt();
        if(to > from){
            program.filmFilterYear(from, to, filtered);
        }
        return filtered;
    }

    private ArrayList<Film> timeFiltering(ArrayList<Film> filtered){
        LocalTime from = LocalTime.of(0,0,0), to = LocalTime.of(23,59,59);
        int hh, mm, ss;
        System.out.println("Enter the time from (in the format: hours minutes seconds (with spaces between))");
        hh = sc.nextInt();
        mm = sc.nextInt();
        ss = sc.nextInt();
        if(Film.isTimeValid(hh, mm, ss)){
            from = LocalTime.of(hh, mm, ss);
        }
        System.out.println("Enter the time to (in the format: hours minutes seconds (with spaces between))");
        if(Film.isTimeValid(hh,mm,ss)){
            to = LocalTime.of(sc.nextInt(), sc.nextInt(), sc.nextInt());
        }
        if(from.isBefore(to)){
            
        }
        return filtered;
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

package app;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.io.FileUtils;
import utils.*;

/**
 * The FilmList class represents a collection of films.
 * It provides methods to manage and manipulate the films in the list.
 */
public class FilmList implements ProgramInterface {

    private ArrayList<Film> arr = new ArrayList<>();

    /**
     * Сonstructs an empty 'FilmList' object
     */
    public FilmList() {
    }


    @Override
    public void addFilm(Film f) {
        this.arr.add(f);
    }

    @Override
    public Film get(int index) {
        if (index >= 0 && index < arr.size()) {
            return arr.get(index);
        } else {
            return null;
        }
    }

    @Override
    public int getIndex(String line) {
        line = line.toLowerCase();
        for (int i = 0; i < this.arr.size(); i++) {
            if (line.equals(arr.get(i).getName().toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Film get(String line) {
        line = line.toLowerCase();
        for (Film film : arr) {
            if (line.equals(film.getName().toLowerCase())) {
                return film;
            }
        }
        return null;
    }

    @Override
    public int getSize() {
        return arr.size();
    }

    @Override
    public void deleteFilm(String name) {
        for (int i = 0; i < this.arr.size(); i++) {
            if (name.equals(this.arr.get(i).getName())) {
                this.arr.remove(i);
                break;
            }
        }
    }

    @Override
    public ArrayList<Film> filterByTimeLength(LocalTime minLength, LocalTime maxLength, ArrayList<Film> filteredFilms) {
        int min = minLength.toSecondOfDay();
        int max = maxLength.toSecondOfDay();
        for (Film film : arr) {
            if (film.getLengthInSec() >= min && film.getLengthInSec() <= max) {
                filteredFilms.add(film);
            }
        }
        return filteredFilms;
    }

    @Override
    public ArrayList<Film> filterByReleaseYear(int fromYear, int toYear, ArrayList<Film> filteredFilms) {
        int releaseYear;
        for (Film film : arr) {
            releaseYear = film.getYear();
            if (releaseYear >= fromYear && releaseYear <= toYear) {
                filteredFilms.add(film);
            }
        }
        return filteredFilms;
    }

    @Override
    public ArrayList<Film> filterByGenre(FilmGenre genre, ArrayList<Film> filteredFilms) {

        for (Film film : arr) {
            if (film.getGenre() == genre) {
                filteredFilms.add(film);
            }
        }
        return filteredFilms;
    }

    @Override
    public void sortByFilmName() {
        Collections.sort(arr, FilmComparator.byName());
    }


    @Override
    public void sortByFilmLength() {
        Collections.sort(arr, FilmComparator.byLength());
    }

    @Override
    public void sortByReleaseDate() {
        Collections.sort(arr, FilmComparator.byReleaseDate());
    }

    @Override
    public void sortByRating() {
        Collections.sort(arr, FilmComparator.byRatingAVG());
    }

    @Override
    public void sortByFilmLengthDescending() {
        Collections.sort(arr, FilmComparator.byLengthDescending());
    }

    @Override
    public void sortByReleaseDateDescending() {
        Collections.sort(arr, FilmComparator.byReleaseDateDescending());
    }

    @Override
    public void sortByRatingDescending() {
        Collections.sort(arr, FilmComparator.byRatingAVGDescending());
    }

    @Override
    public void sortByFilmNameDescending() {
        Collections.sort(arr, FilmComparator.byNameDescending());
    }

    /**
     * Loads film data from a file and adds the films to the FilmList.
     *
     * @param path the path to the directory containing the data file
     * @throws IOException if an I/O error occurs while reading the file
     * @throws IllegalArgumentException if the data file contains invalid data
     */
    public void loadFilmDataFromFile(String path) throws IOException {
        //File filename = new File("data_film.txt");
        String FILE_SEPARATOR = System.getProperty("file.separator");
        try (BufferedReader br = new BufferedReader(new FileReader(path + FILE_SEPARATOR + "data_film.txt"))) {
            String line, name;
            String[] parts;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            FilmGenre genre;
            LocalTime time;
            int year;
            Film f;
            try {
                while ((line = br.readLine()) != null) {
                    parts = line.split("\t");
                    name = parts[0];
                    genre = FilmGenre.valueOf(parts[1]);
                    year = Integer.parseInt(parts[2]);
                    time = LocalTime.parse(parts[3], dtf);
                    f = new Film(name, genre, year, time);
                    addFilm(f);
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Problem with file " + "data" + FILE_SEPARATOR + "data_film.txt" + "\n " + e.getMessage());
            }
        }
    }

    /**
     * Loads film descriptions from a file and updates the corresponding films in the FilmList.
     *
     * @param path the path to the directory containing the description file
     * @throws IOException if an I/O error occurs while reading the file
     * @throws IllegalArgumentException if the description file contains invalid data or if a corresponding film is not found
     */
    public void loadDescription(String path) throws IOException {
        String FILE_SEPARATOR = System.getProperty("file.separator");
        try (BufferedReader reader = new BufferedReader(new FileReader(path + FILE_SEPARATOR + "film_description.txt"))) {
            String line, title, description;
            String[] parts;
            int index;
            while ((line = reader.readLine()) != null) {
                parts = line.split("\t");

                title = parts[0];
                description = parts[1];
                index = getIndex(title);
                if (index != -1) {
                    get(index).setDescription(description);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Problem with file " + "data" + FILE_SEPARATOR + "film_description.txt" + "\n " + e.getMessage());
        }
    }

    /**
     * Reads film data from a binary file and prints the information to the console.
     *
     * @param path the path to the directory containing the binary file
     * @throws IOException if an I/O error occurs while reading the file
     * @throws IllegalArgumentException if the binary file is not found or if there is an error while reading the data
     */
    public void checkFilmDataBin(String path) throws IOException {
        String FILE_SEPARATOR = System.getProperty("file.separator");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        try (DataInputStream in = new DataInputStream(new FileInputStream(path + FILE_SEPARATOR + "film_data.bin"))) {
            while (in.available() > 0) {
                /*title = in.readUTF();
                genre = in.readUTF();
                year = in.readInt();
                time = LocalTime.parse(in.readUTF(), dtf);*/
                System.out.format("%25S\t%20S\t%4d\t%10s\n", in.readUTF(), in.readUTF(), in.readInt(), LocalTime.parse(in.readUTF(), dtf));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Problem with file " + path + FILE_SEPARATOR + "film_data.bin" + "\n");
        }
    }



    /**
     * Loads film ratings from a file and updates the ratings for the corresponding films.
     *
     * @param path the path to the directory containing the ratings file
     * @throws IOException if an I/O error occurs while reading the file
     * @throws IllegalArgumentException if the ratings file is not found or if there is an error while processing the ratings
     */
    public void loadRates(String path) throws IOException {
        String FILE_SEPARATOR = System.getProperty("file.separator");
        String title;
        String line;
        String[] parts;
        double rate;
        try (BufferedReader reader = new BufferedReader(new FileReader(path + FILE_SEPARATOR + "film_rates.txt"))) {
            int index;
            while ((line = reader.readLine()) != null) {
                // Process each line containing the film description
                parts = line.split("\t");
                title = parts[0];
                index = getIndex(title);
                if (index != -1) {
                    parts = parts[1].split(", ");
                    for (String s : parts) {
                        rate = Double.parseDouble(s);
                        arr.get(index).rate(rate);
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Problem with file " + path + FILE_SEPARATOR + "film_rates.txt" + "\n " + e.getMessage());
        }
    }

    @Override
    public boolean readFile(String path) {
        try {
            loadFilmDataFromFile(path);
            loadDescription(path);
            //loadRatesBin(String path);
            loadRates(path);
            return true;
        } catch (IOException e) {
            System.out.println("Problem with file");
        } catch (IllegalArgumentException e) {
            System.out.println("Data v souborech nejsou validni");
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public void saveData(String path, int choose) throws IOException {
        if (choose == 1) {
            saveFilmDataBin(path);
            checkFilmDataBin(path);
        } else {
            //dataWork.saveFilmDataBin(path, this.arr);
            saveFilmDataToFile(path);
            saveDescriptionToFile(path);
            saveRatesToFile(path);
        }

    }

    /**
     * Saves the film descriptions to a text file.
     *
     * @param path              The path to the directory where the file will be saved.
     * @throws IOException      If an I/O error occurs while writing to the file.
     */
    public void saveDescriptionToFile(String path) throws IOException {
        sortByFilmName();
        String FILE_SEPARATOR = System.getProperty("file.separator");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + FILE_SEPARATOR + "film_description.txt"))) {
            for (Film f : arr) {
                bw.write(f.getName() + "\t" + f.getDescription());
                bw.newLine();
            }
        }
    }

    /**
     * Saves the film data to a text file using FileUtils.
     *
     * @param path              The path to the directory where the file will be saved.
     * @throws IOException      If an I/O error occurs while writing to the file.
     */
    public void saveFilmDataToFile(String path) throws IOException {
        sortByFilmName();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        String FILE_SEPARATOR = System.getProperty("file.separator");
        StringBuilder sb = new StringBuilder();
        String fileName = path + FILE_SEPARATOR + "data_film.txt";
        try {
            for(Film f:arr){
                sb.append(f.getName()).append("\t").append(f.getGenre().toString()).append("\t").append(f.getYear()).append("\t");
                sb.append(f.getLength(dtf).toString()).append("\n");
            }

            FileUtils.writeStringToFile(new File(fileName), sb.toString(), "UTF-8");

            System.out.println("Data was successfully written to the file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing data to the file: " + e.getMessage());
        }
    }



    /**
     * Saves the film data to a binary file.
     *
     * @param path              The path to the directory where the file will be saved.
     * @throws IOException      If an I/O error occurs while writing to the file.
     */
    public void saveFilmDataBin(String path) throws IOException {
        sortByFilmName();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        String FILE_SEPARATOR = System.getProperty("file.separator");
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(path + FILE_SEPARATOR + "film_data.bin"))) {
            for (Film f : this.arr) {
                out.writeUTF(f.getName());
                out.writeUTF(f.getGenre().toString());
                out.writeInt(f.getYear());
                out.writeUTF(f.getLength(dtf));
            }
        }

    }

    /**
     * Saves the film ratings to a text file.
     *
     * @param path              The path to the directory where the file will be saved.
     * @throws IOException      If an I/O error occurs while writing to the file.
     */
    public void saveRatesToFile(String path) throws IOException {
        sortByFilmName();
        String FILE_SEPARATOR = System.getProperty("file.separator");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + FILE_SEPARATOR + "film_rates.txt"))) {
            for (Film f : arr) {
                bw.write(f.getName() + "\t");
                for (double i : f.getRates()) {
                    bw.write(i + ", ");
                }
                bw.newLine();
            }
        }
    }
}

package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import ui.*;
import utils.*;

public class FilmList implements ProgramInterface {

    private ArrayList<Film> arr = new ArrayList<>();

    public FilmList() {
        readFile();
    }

    private void readFile() {
        String FILE_SEPARATOR = System.getProperty("file.separator");
        try {
            loadFilmDataFromFile();
            loadDescription();
            loadRates();
        } catch (IOException e) {
            System.out.println("Problem with file");
        } catch (IllegalArgumentException e) {
            System.out.println("Data v souborech nejsou validni");
            System.out.println(e.getMessage());
        }
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
        for (int i = 0; i < this.arr.size() - 1; i++) {
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
    
    public ArrayList<Film> filmFilter(FilmGenre genre, ArrayList<Film> arr){
        for(Film f : this.arr){
            if(f.getGenre().equals(genre))
                arr.add(f);
        }
        return arr;
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

    public void loadFilmDataFromFile() throws IOException {
        //File filename = new File("data_film.txt");
        String FILE_SEPARATOR = System.getProperty("file.separator");
        try ( BufferedReader br = new BufferedReader(new FileReader("data" + FILE_SEPARATOR + "data_film.txt"))) {
            String line;
            String[] parts;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            String name;
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
                throw new IllegalArgumentException("Problem with file " + "data_film.txt" + "\n " + e.getMessage());
            }
        }
    }

    public void loadDescription() throws IOException {
        String FILE_SEPARATOR = System.getProperty("file.separator");
        try ( BufferedReader reader = new BufferedReader(new FileReader("data" + FILE_SEPARATOR + "film_description.txt"))) {
            String line, title, description;
            String[] parts;
            int index;
            while ((line = reader.readLine()) != null) {
                // Process each line containing the film description
                parts = line.split("\t");

                title = parts[0];
                description = parts[1];
                index = getIndex(title);
                if (index != -1) {
                    get(index).setDescription(description);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Problem with file " + "film_description.txt" + "\n " + e.getMessage());
        }
    }

    public void loadRates() throws IOException {
        String FILE_SEPARATOR = System.getProperty("file.separator");
        String title;
        int index;
        try ( DataInputStream in = new DataInputStream(new FileInputStream("data" + FILE_SEPARATOR + "film_rates.dat"))) {
            while (in.available() > 0) {
                title = in.readUTF();
                index = getIndex(title);
                if (index != -1) {
                    while (in.available() > 0) {
                        double rating = in.readDouble();
                        arr.get(index).rate(rating);
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Problem with file " + "film_rates.dat" + "\n " + e.getMessage());
        }
    }

    @Override
    public void SaveData() throws IOException {
        SaveDescriptionToFile();
        saveFilmDataToFile();
        saveRatesBinFile();
    }

    public void SaveDescriptionToFile() throws IOException {
        sortByFilmName();
        String FILE_SEPARATOR = System.getProperty("file.separator");
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter("data" + FILE_SEPARATOR + "film_description.txt"))) {
            for (Film f : arr) {
                bw.write(f.getName() + "\t" + f.getDescription());
                bw.newLine();
            }
        }
    }

    public void saveFilmDataToFile() throws IOException {
        sortByFilmName();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        String FILE_SEPARATOR = System.getProperty("file.separator");
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter("data" + FILE_SEPARATOR + "data_film.txt"))) {
            for (Film f : arr) {
                bw.write(f.getName() + "\t" + f.getGenre() + "\t" + f.getYear()
                        + "\t" + f.getLength(dtf));
                bw.newLine();
            }
        }
    }

    public void saveRatesBinFile() throws IOException {
        sortByFilmName();
        String FILE_SEPARATOR = System.getProperty("file.separator");
        try ( DataOutputStream out = new DataOutputStream(new FileOutputStream("data" + FILE_SEPARATOR + "film_rates.dat"))) {
            //int nameLength;
            for (Film f : arr) {
                out.writeUTF(f.getName());
                for (double i : f.getRates()) {
                    out.writeDouble(i);
                }

            }
        }
    }
}

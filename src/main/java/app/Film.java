package app;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

import utils.*;

import java.util.ArrayList;

public class Film {

        private String name;
        private String description;
        private FilmGenre genre;
        private double ratingAVG;
        private int year; // release year
        private LocalTime time;
        private ArrayList<Double> rating = new ArrayList<>();

        public Film() {
            this.name = null;
            this.genre = null;
            this.year = 0;
            this.time = null;
            this.description = null;
        }

        public Film(String name){
            this.name = name;
            this.genre = null;
            this.year = 0;
            this.time = null;
            this.description = null;
        }

        public Film(String name, FilmGenre genre, int year, LocalTime time) {
            this.name = name;
            //this.description = description;
            this.genre = genre;
            this.year = year;
            this.time = time;
        }

        public Film(String name, LocalTime time, int year) {
            this.name = name;
            this.time = time;
            this.year = year;
        }

        public LocalTime getLength() {
            return time;
        }

        public int getLengthInSec() {
            return time.getHour() * 3600 + time.getMinute() * 60 + time.getSecond();
        }

        public void setLength(LocalTime length) {
            this.time = length;
        }

        public int setLength(int hours, int minutes, int seconds) {
            if(isTimeValid(hours,  minutes, seconds)){
                this.time = LocalTime.of(hours, minutes, seconds);
                return 1;
            }
            return -1;
        }

        public static boolean isTimeValid(int hours, int minutes, int seconds) {
            try {
                LocalTime.of(hours, minutes, seconds);
            } catch (DateTimeException e) {
                return false;
            }
            return true;
        }

        public String getName() {
            return name;
        }

        public String getNameLowerCase(){
            return name.toLowerCase();
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<Double> getRates(){
            return rating;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public FilmGenre  getGenre() {
            return genre;
        }

        public void setGenre(FilmGenre  genre) {
            this.genre = genre;
        }

        public double getRatingAVG() {
            return ratingAVG;
        }

        public int rate(double rating) {
            if (rating > 0 && rating <= 5) {
                this.rating.add(rating);
                calculateAVG();
                return 1;
            }
            return -1;
        }

        private void calculateAVG(){
            double res = 0;
            for(int i = 0; i<this.rating.size(); i++){
                res += this.rating.get(i);
            }
            this.ratingAVG = res / this.rating.size();
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }



        public boolean isEmpty(){
            if(this.genre != null && this.year != 0 && this.time != null && this.description!= null){
                return false;
            }
            return true;
        }

        public String mainInfo() {
            return String.format("%25S\t%20S\t%10S\t%4d\n", this.name, this.genre, this.time, this.year);
        }

        @Override
        public String toString() {
            return String.format("name: %S\nrating: %.1f\ngenre: %S\nlength: %S\nrelease year: %d\n",
                    this.name, this.ratingAVG, this.genre, this.time, this.year);
        }

        public String getLength(DateTimeFormatter dtf) {
            return time.format(dtf);
        }

}



import java.io.*;
import java.util.LinkedList;

public class Mp3File implements Serializable {
 private String title;
 private String path;
 private String artist;
 private String album;
 private int year;
 private double rating;



 public Mp3File() {
  this("Unknown");
 }


 public Mp3File(String title) {
  super();
  this.title = title;
  this.path = "Unknown";
  this.artist = "Unknown";
  this.album = "Unknown";
  this.year = 0;
  this.rating = 0;
 }



 @Override
 public boolean equals(Object o) {
  if (o instanceof Mp3File) {
   return getTitle().equals(((Mp3File) o).getTitle()) && getArtist().equals(((Mp3File) o).getArtist());
  }
  return false;
 }
 @Override
 public String toString() {
  return "MP3 FILE [\ntitle=" + title + ", \npath=" + path + ", \nartist=" + artist + ", \nalbum=" + album + ", \nyear=" + year + ", \nrating=" + rating + "]";
 }
 public String getTitle() {
  return title;
 }
 public void setTitle(String title) {
  this.title = title;
 }
 public String getPath() {
  return path;
 }
 public void setPath(String path) {
  this.path = path;
 }
 public String getArtist() {
  return artist;
 }
 public void setArtist(String artist) {
  this.artist = artist;
 }
 public String getAlbum() {
  return album;
 }
 public void setAlbum(String album) {
  this.album = album;
 }
 public int getYear() {
  return year;
 }
 public void setYear(int year) {
  this.year = year;
 }
 public double getRating() {
  return rating;
 }
 public void setRating(double rating) {
  if (rating >= 0 && rating <= 10) {
   this.rating = rating;
  } else {
   this.rating = 5;
  }
 }



}
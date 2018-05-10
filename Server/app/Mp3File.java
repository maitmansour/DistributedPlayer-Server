import java.io.*;
import java.util.LinkedList;

public class Mp3File implements Serializable {
 private String title;
 private String artist;
 private String album;
 private int year;
 private String filename;



 public Mp3File() {
  this("Unknown","Unknown");
 }


 public Mp3File(String title,String filename) {
  super();
  this.title = title;
  this.artist = "Unknown";
  this.album = "Unknown";
  this.year = 0;
  this.filename = filename;
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
  return "MP3 FILE [\ntitle=" + title + ", \nartist=" + artist + ", \nalbum=" + album + ", \nyear=" + year + ", \nfilename=" + filename + "]";
 }
 public String getTitle() {
  return title;
 }
 public void setTitle(String title) {
  this.title = title;
 }
 public String getFilename() {
  return filename;
 }
 public void setFilename(String filename) {
  this.filename = filename;
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



}
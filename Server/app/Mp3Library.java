import java.io.*;
import java.util.LinkedList;


public class Mp3Library implements Serializable {
 private LinkedList < Mp3File > library;

 public Mp3Library() {
  super();
  this.library = new LinkedList < Mp3File > ();
 }

 public Mp3Library findByFeature(String featureName, String featureValue) {
  Mp3Library foundedFiles = new Mp3Library();
  switch (featureName) {
   case "title":
    for (Mp3File mp3File: library) {
     if (mp3File.getTitle().equals(featureValue)) {
      foundedFiles.addNewFile(mp3File);
     }
    }
    break;
   case "artist":
    for (Mp3File mp3File: library) {
     if (mp3File.getArtist().equals(featureValue)) {
      foundedFiles.addNewFile(mp3File);
     }
    }
    break;
   case "album":
    for (Mp3File mp3File: library) {
     if (mp3File.getAlbum().equals(featureValue)) {
      foundedFiles.addNewFile(mp3File);
     }
    }
    break;
   case "year":
    for (Mp3File mp3File: library) {
     if (mp3File.getYear() == Integer.valueOf(featureValue)) {
      foundedFiles.addNewFile(mp3File);
     }
    }
    break;   
    case "filename":
    for (Mp3File mp3File: library) {
     if (mp3File.getFilename().equals(featureValue)) {
      foundedFiles.addNewFile(mp3File);
     }
    }
    break;

  }
  if (foundedFiles.isEmpty()) {
   return null;
  }

  return foundedFiles;

 }

 private boolean isEmpty() {
  return this.library.isEmpty();

 }
 public boolean addNewFile(Mp3File file) {
  if (!library.contains(file)) {
   this.library.add(file);
   return true;
  }
  return false;
 }



 public boolean deleteFile(String path) {
  for (Mp3File mp3File: library) {
   if (mp3File.getFilename().equals(path)) {
    this.library.remove(mp3File);
    return true;
   }
  }
  return false;
 }


 @Override
 public String toString() {
  String libraryFiles = "[";
  for (Mp3File mp3File: library) {
   libraryFiles += mp3File.toString() + "";
  }
   libraryFiles = libraryFiles.substring(0, libraryFiles.length() - 1);
   libraryFiles += "]";
  return libraryFiles;
 }






}
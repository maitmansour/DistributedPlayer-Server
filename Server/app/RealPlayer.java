import java.io.*;
import java.util.logging.*;
import java.nio.file.*;

public class RealPlayer implements Mp3Player.Player {

 Mp3Library myLibrary;
 Logger logger;
 String dataDir;
 byte [] file;

 public RealPlayer() {
  dataDir = "data";
  myLibrary = new Mp3Library();

  /**
   *Open Log file and write on it
   */
  try {
   FileHandler fh;
   logger = Logger.getLogger("ServerLog");
   fh = new FileHandler(dataDir + "/server.log", true);
   logger.addHandler(fh);
   SimpleFormatter formatter = new SimpleFormatter();
   fh.setFormatter(formatter);
 } catch (SecurityException e) {
   e.printStackTrace();
 } catch (IOException e) {
   e.printStackTrace();
 }

 logger.info("Starting RealPlayer");

  /**
   *Read data from file if exist
   */
  logger.info("Charging Database File");
  try {
   FileInputStream fin = new FileInputStream(dataDir + "/data.dat");
   ObjectInputStream ois = new ObjectInputStream(fin);
   myLibrary = (Mp3Library) ois.readObject();
   ois.close();
 } catch (Exception e) {
   logger.info("Creation of Data file");
 }
 logger.info("Database Charged successfuly");
 logger.info("RealPlayer Started successfuly");
}


public String addNewFile(String title, String path, String artist, String album, String year, String rating, com.zeroc.Ice.Current current) {
  Mp3File tmpFile = new Mp3File(title);
  tmpFile.setAlbum(album);
  tmpFile.setArtist(artist);
  tmpFile.setPath(path);
  tmpFile.setRating(Double.parseDouble(rating));
  tmpFile.setYear(Integer.parseInt(year));
  Boolean added = myLibrary.addNewFile(tmpFile);
  this.saveObjectIntoDatabase();
  String bufferLog = "";
  if (added) {
   bufferLog = "file  (" + title + "," + path + "," + artist + "," + album + "," + year + "," + rating + ") was added successfuly";
 } else {
   bufferLog = "file  (" + title + "," + path + "," + artist + "," + album + "," + year + "," + rating + ") was already added";

 }
 logger.info(bufferLog);
 return bufferLog;

}

public String findByFeature(String featureName, String featureValue, com.zeroc.Ice.Current current) {
  Mp3Library foundedLibrary = myLibrary.findByFeature(featureName, featureValue);
  String resultBuffer = "";
  if (foundedLibrary != null) {
   resultBuffer += foundedLibrary;
 } else {
   logger.warning("No file (" + featureName + "," + featureValue + ") was founded");
   resultBuffer += "No file was found by feature <" + featureName + "> with value of <" + featureValue + ">";
 }
 logger.info("file (" + featureName + "," + featureValue + ") was founded successfuly");
 return resultBuffer;

}

public String deleteFile(String path, com.zeroc.Ice.Current current) {
  String resultBuffer = "deleteFile (" + path + ")";
  if (myLibrary.deleteFile(path)) {
   resultBuffer += " was deleted successfuly";
 } else {
   resultBuffer += " was not found";
 }



 logger.info(resultBuffer);
 this.saveObjectIntoDatabase();
 return resultBuffer;
}

public byte[] getFile(com.zeroc.Ice.Current current){
  try{
    Path path = Paths.get(dataDir + "/test.mp3");
    byte[] data = Files.readAllBytes(path);
    return data;
  }catch (Exception e) {
   e.printStackTrace();
 }

 return null;
}

public  void setFile(String name, byte[] part, String current, String size, com.zeroc.Ice.Current current_){
  File music_directory_file=new File(dataDir +"/"+ name);
  music_directory_file.mkdirs();
  String music_directory=music_directory_file.getAbsolutePath();



  logger.info("file set start");
  try (FileOutputStream fos = new FileOutputStream(music_directory + "/"+current+".mp3")) {
   fos.write(part);
   //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
  
  //Combining
   if (current.equals(size)) {
     logger.info("ALL PARTS DOWNLOADED");
     logger.info("START COMBINING");
     FileOutputStream fos2 = new FileOutputStream(music_directory + "/FULL.mp3");

     for (int i=0; i<=Integer.parseInt(size);i++ ) {
       Path path = Paths.get(music_directory + "/"+i+".mp3");
       byte[] data = Files.readAllBytes(path);
       fos2.write(data);
     }

     logger.info("COMBINING FINISHED");

   }

 }catch (Exception e) {
   e.printStackTrace();
 }
 logger.info("file setted finished ");
}

private void saveObjectIntoDatabase() {
  try {
   FileOutputStream fout = new FileOutputStream(dataDir + "/data.dat");
   ObjectOutputStream oos = new ObjectOutputStream(fout);
   oos.writeObject(myLibrary);
   oos.close();
 } catch (Exception e) {
   e.printStackTrace();
 }
}
}

import java.io.*;
import java.util.logging.*;
import java.nio.file.*;
import java.util.Arrays;

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


public String addNewFile(String title, String artist, String album, String year, String filename, com.zeroc.Ice.Current current) {
  Mp3File tmpFile = new Mp3File(title,filename);
  tmpFile.setAlbum(album);
  tmpFile.setArtist(artist);
  tmpFile.setFilename(filename);
  tmpFile.setYear(Integer.parseInt(year));
  Boolean added = myLibrary.addNewFile(tmpFile);
  this.saveObjectIntoDatabase();
  String bufferLog = "";
  if (added) {
   bufferLog = "file  (" + title + "," + filename + "," + artist + "," + album + "," + year + ",) was added successfuly";
 } else {
   bufferLog = "file  (" + title + "," + filename + "," + artist + "," + album + "," + year + ",) was already added";

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

public byte[] getFile(String name, String part,com.zeroc.Ice.Current current){
  try{
    logger.info("Start Sending part "+part+" on file "+name);
    Path path = Paths.get(dataDir + "/music/"+name+"/"+name+".mp3");
    byte[] data = Files.readAllBytes(path);
    logger.info("Spliting "+name+" into Binary array");
    byte[][] chunks=splitArray(data,100000);
    logger.info("End of Sending part "+part+" on file "+name);
    return chunks[Integer.parseInt(part)];
  }catch (Exception e) {
   e.printStackTrace();
 }

 return null;
}

public  void setFile(String name, byte[] part, String current, String size, com.zeroc.Ice.Current current_){
  File music_directory_file=new File(dataDir +"/music/"+ name);
  music_directory_file.mkdirs();
  String music_directory=music_directory_file.getAbsolutePath();


  logger.info("Start Receiving part "+current+" on file "+name);
  try (FileOutputStream fos = new FileOutputStream(music_directory + "/"+current+".mp3")) {
   fos.write(part);  
   if (current.equals(size)) {
     logger.info("All parts of file"+name+" are downloaded - Combining Started");

     FileOutputStream fos2 = new FileOutputStream(music_directory + "/"+name+".mp3");
     for (int i=0; i<=Integer.parseInt(size);i++ ) {
       logger.info("Start Combining the "+size+" parts on file "+name);
       Path path = Paths.get(music_directory + "/"+i+".mp3");
       byte[] data = Files.readAllBytes(path);
       fos2.write(data);
       logger.info("End of Combining part "+i+" on file "+name);
       logger.info("Start deleting part "+i+" on file "+name+" at "+path);
       Files.delete(path);
       logger.info("End of deleting part "+i+" on file "+name+" at "+path);
     }
     logger.info("End of Combining the "+size+" parts on file "+name);
   }

 }catch (Exception e) {
   e.printStackTrace();
 }
 logger.info("End of Receiving part "+current+" on file "+name);
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

private  byte[][] splitArray(byte[] arrayToSplit, int chunkSize){
  if(chunkSize<=0){
        return null;  // just in case :)
      }
    // first we have to check if the array can be split in multiple 
    // arrays of equal 'chunk' size
    int rest = arrayToSplit.length % chunkSize;  // if rest>0 then our last array will have less elements than the others 
    // then we check in how many arrays we can split our input array
    int chunks = arrayToSplit.length / chunkSize + (rest > 0 ? 1 : 0); // we may have to add an additional array for the 'rest'
    // now we know how many arrays we need and create our result array
    byte[][] arrays = new byte[chunks][];
    // we create our resulting arrays by copying the corresponding 
    // part from the input array. If we have a rest (rest>0), then
    // the last array will have less elements than the others. This 
    // needs to be handled separately, so we iterate 1 times less.
    for(int i = 0; i < (rest > 0 ? chunks - 1 : chunks); i++){
        // this copies 'chunk' times 'chunkSize' elements into a new array
      arrays[i] = Arrays.copyOfRange(arrayToSplit, i * chunkSize, i * chunkSize + chunkSize);
    }
    if(rest > 0){ // only when we have a rest
        // we copy the remaining elements into the last chunk
      arrays[chunks - 1] = Arrays.copyOfRange(arrayToSplit, (chunks - 1) * chunkSize, (chunks - 1) * chunkSize + rest);
    }
    return arrays; // that's it
  }
}

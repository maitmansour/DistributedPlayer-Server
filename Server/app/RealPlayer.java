import java.io.*;
import java.util.logging.*;
import java.nio.file.*;
import java.util.Arrays;
import java.net.*;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class RealPlayer implements Mp3Player.Player {

 Mp3Library myLibrary;
 Logger logger;
 String dataDir;
 byte [] file;
 String myNetworkInterface;
 String teleStreaming;
 String radioStreaming;

 public RealPlayer() {
  dataDir = "data";
  myNetworkInterface="wlp1s0";
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
 getLocalIP();

try{
  registerServer();
}catch (Exception e) {
     logger.info("Exeption during POST "+e.getMessage() );
}
      Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        try{
        RealPlayer.this.unRegisterServer();
      }catch (Exception e) {
        logger.info("Hook unRegisterServer Error");
      }
      }
    });
 startLiveStreaming();

}

public void getLocalIP(){
   logger.info("Loking for local IP");
 try{
  Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
  for (; n.hasMoreElements();)
  {
    NetworkInterface e = n.nextElement();
    if (e.getName().equals(myNetworkInterface)) {
      Enumeration<InetAddress> a = e.getInetAddresses();
      for (; a.hasMoreElements();)
      {
        InetAddress addr = a.nextElement();

        if ((!addr.getHostAddress().equals("127.0.0.1"))&&(!addr.getHostAddress().contains(":"))) {
          myNetworkInterface=addr.getHostAddress();
             logger.info("Loking for local IP finshed, result : "+myNetworkInterface);                 
        }

      }

    }
  }
} catch (Exception e) {
 logger.info("Network Error");
}
   logger.info("local IP founded successfuly");

}

public String addNewFile(String title, String artist, String album, String year, String filename,String image, com.zeroc.Ice.Current current) {
  Mp3File tmpFile = new Mp3File(title,filename);
  tmpFile.setAlbum(album);
  tmpFile.setArtist(artist);
  tmpFile.setFilename(filename);
  tmpFile.setYear(Integer.parseInt(year));
  tmpFile.setImage(image);
  Boolean added = myLibrary.addNewFile(tmpFile);
  this.saveObjectIntoDatabase();
  String bufferLog = "";
  if (added) {
   bufferLog = "file  (" + title + "," + filename + "," + artist + "," + album + "," + year + ","+image+") was added successfuly";
 } else {
   bufferLog = "file  (" + title + "," + filename + "," + artist + "," + album + "," + year + ","+image+") was already added";

 }
 logger.info(bufferLog);
 return added?"1":"0";

}

public String getAllMusic(com.zeroc.Ice.Current current){
  return myLibrary.toString();
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

public String deleteFile(String filename, com.zeroc.Ice.Current current) {
  String resultBuffer = "deleteFile (" + filename + ")";
  Boolean deleted = false;
  if (myLibrary.deleteFile(filename)) {
    try{
      String music_directory = dataDir +"/music/"+ filename;
      Path path1 = Paths.get(music_directory+"/"+filename+".mp3");
      Path path2 = Paths.get(music_directory);
      Files.delete(path1);
      Files.delete(path2);
      deleted=true;  
    }catch (Exception e) {
     System.out.println(e);
   }


   resultBuffer += " was deleted successfuly";
 } else {
   resultBuffer += " was not found";
 }



 logger.info(resultBuffer);
 this.saveObjectIntoDatabase();
 return deleted?"1":"0";
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

public void startLiveStreaming(){
  startTele();
  startRadio();
}

public void startTele(){
  try {
    StreamRTP rtp = new StreamRTP();
    rtp.start(myNetworkInterface, 5550, dataDir+"/tele/live.mp4","tele"); 
    teleStreaming="rtsp://@"+myNetworkInterface+":5550/tele"; 
    logger.info("Start tele Streaming ON : "+teleStreaming);
  } catch (Exception e) {
    e.printStackTrace();
  }
}

public void startRadio(){
  try {
    StreamRTP rtp = new StreamRTP();
    rtp.start(myNetworkInterface, 5551, dataDir+"/radio/live.mp3","radio"); 
    radioStreaming="rtsp://@"+myNetworkInterface+":5550/radio"; 
    logger.info("Start Radio Streaming ON : "+radioStreaming);
  } catch (Exception e) {
    e.printStackTrace();
  }

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



private void registerServer() throws Exception{
String url = "http://localhost/metaserver/web/index.php/connections/register";
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    //add reuqest header
    con.setRequestMethod("POST");
    String urlParameters = "ip="+myNetworkInterface+"&radio=rtsp://@"+myNetworkInterface+":5550/radio&tele=rtsp://@"+myNetworkInterface+":5551/tele";
    
    // Send post request
    con.setDoOutput(true);
    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    wr.writeBytes(urlParameters);
    wr.flush();
    wr.close();

    int responseCode = con.getResponseCode();
    logger.info("registering server on metaserver");
    logger.info("registering parameters : " +urlParameters);
    logger.info("registering response : " +responseCode);    
}
private void unRegisterServer() throws Exception{
String url = "http://localhost/metaserver/web/index.php/connections/unregister";
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    //add reuqest header
    con.setRequestMethod("POST");
    String urlParameters = "ip="+myNetworkInterface;
    
    // Send post request
    con.setDoOutput(true);
    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    wr.writeBytes(urlParameters);
    wr.flush();
    wr.close();

    int responseCode = con.getResponseCode();
    logger.info("UNregistering server on metaserver");
    logger.info("UNregistering parameters : " +urlParameters);
    logger.info("UNregistering response : " +responseCode);    
}
}
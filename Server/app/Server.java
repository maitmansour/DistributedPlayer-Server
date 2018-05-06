public class Server {
 public static void main(String[] args) throws Exception {
  try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
   com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("DistributedPlayerAdapter", "default -p 10000");
   com.zeroc.Ice.Object object = new RealPlayer();
   adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("DistributedPlayer"));
   adapter.activate();
   communicator.waitForShutdown();
  }
 }
}
package server;


import org.quartz.SchedulerException;

public class App {

  public static void main(String[] args) throws SchedulerException {
    Server server = new Server();
    server.init();
  }
}

package server;


import scripts.Bootstrap;


public class App {

  public static void main(String[] args) {
    new Bootstrap().init();
    new Server().start();
    new CronTaskReporte().run();

  }
}

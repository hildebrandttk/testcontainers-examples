package tk.hildebrandt.testcontainers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;

public class ExitWithoutStoppingContainers {

   private static final Logger LOG = LoggerFactory.getLogger(ExitWithoutStoppingContainers.class);

   public static void main(String[] args) {
      try {
         runContainer();
      } catch (Exception e) {
         LOG.error("error", e);
      }
   }

   private static void runContainer() throws Exception {
      attachShutdownHook();
      PostgreSQLContainer container = new PostgreSQLContainer("postgres:12");
      container.start();
      killMeHard(ExitWithoutStoppingContainers.class);
   }

   private static void attachShutdownHook() {
      Runtime.getRuntime().addShutdownHook(new Thread(
         () -> System.out
            .println("******************** EXIT ********************")));
   }

   private static void killMeHard(
      Class<ExitWithoutStoppingContainers> mainClass) throws IOException {
      if (System.getProperty("os.name").contains("Windows")) {
         throw new RuntimeException("this kill method will run on *ix only.");
      }
      String[] killMe = new String[]{"sh", "-c", String.format(
         "kill -9 $(jps | grep " + mainClass
            .getSimpleName() + "| awk '{print $1}')")};
      Process exec = Runtime.getRuntime().exec(killMe);
      while (exec.isAlive()) {
         byte[] buffer = new byte[1024];
         int len;
         while ((len = exec.getInputStream().read(buffer)) != -1) {
            System.out.write(buffer, 0, len);
         }
         System.out.println("not killed yet");
      }
   }
}

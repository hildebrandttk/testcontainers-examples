package tk.hildebrandt.testcontainers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

public class Plain {

   private static final Object LOCK = new Object();
   private static final Logger LOG = LoggerFactory.getLogger(Plain.class);
   private static final int POSTGRES_PORT = 5432;

   public static void main(String[] args) {
      try {
         runContainer();
      } catch (InterruptedException ignore) {
         //
      }
   }

   private static void runContainer() throws InterruptedException {
      GenericContainer genericContainer = new GenericContainer("postgres")
         .withExposedPorts(POSTGRES_PORT)
         .withEnv("POSTGRES_PASSWORD", "test1234");
      genericContainer.withLogConsumer(
         new Slf4jLogConsumer(LoggerFactory.getLogger("DOCKER_POSTGRES")));
      genericContainer.start();
      LOG.info("Postgress ist listening on port {}",
         genericContainer.getMappedPort(POSTGRES_PORT));
      synchronized (LOCK) {
         Thread.sleep(30000);
      }
      genericContainer.stop();
   }
}

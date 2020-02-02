package tk.hildebrandt.testcontainers.geb

import org.testcontainers.containers.RecordingFileFactory

class CustomRecordingFileFactory implements RecordingFileFactory {
   @Override
   File recordingFileForTest(File vncRecordingDirectory, String prefix,
                             boolean succeeded) {
      if(!vncRecordingDirectory.exists()){
         vncRecordingDirectory.mkdirs()
      }
      return new File(prefix + '.flv', vncRecordingDirectory)
   }
}

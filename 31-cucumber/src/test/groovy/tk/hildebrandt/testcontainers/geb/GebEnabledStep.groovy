package tk.hildebrandt.testcontainers.geb

import geb.Browser
import geb.Configuration
import geb.ConfigurationLoader
import geb.Page

class GebEnabledStep {

   String gebConfEnv = null
   String gebConfScript = null

   protected Browser browser

   protected <T extends Page> T to(Map params = [:], Class<T> pageType,
                                   Object[] args) {
      return getBrowser().to(params, pageType, args)
   }

   Configuration createConf() {
      new ConfigurationLoader(gebConfEnv, System.properties,
         new GroovyClassLoader(getClass().classLoader)).getConf(gebConfScript)
   }

   Browser createBrowser() {
      new Browser(createConf())
   }

   Browser getBrowser() {
      if (browser == null) {
         browser = createBrowser()
      }
      browser
   }

   def methodMissing(String name, args) {
      getBrowser()."$name"(*args)
   }

   def propertyMissing(String name) {
      getBrowser()."$name"
   }

   void propertyMissing(String name, value) {
      getBrowser()."$name" = value
   }
}

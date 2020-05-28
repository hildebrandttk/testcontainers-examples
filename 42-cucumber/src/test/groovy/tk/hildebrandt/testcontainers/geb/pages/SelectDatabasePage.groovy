package tk.hildebrandt.testcontainers.geb.pages

import geb.Page
import geb.module.FormElement
import geb.module.PasswordInput
import geb.module.Select
import geb.module.TextInput
import org.openqa.selenium.By

class SelectDatabasePage extends Page {

   static url = 'http://adminer:8080/all'

   static at = {
      title.startsWith('Select database - ') && title.endsWith('Adminer')
   }

   static content = {
      submitButton { $('input', type: 'submit').module(FormElement) }
      databaseSelect { $(By.name('db')).module(Select)}
   }

   ManageDatabasePage selectDatabaseByName(String dbName){
      databaseSelect.setSelected(dbName)
      waitFor { browser.at(ManageDatabasePage) }
      browser.page(ManageDatabasePage)
   }
}

package tk.hildebrandt.testcontainers.geb.pages

import geb.Page
import geb.module.FormElement
import geb.module.PasswordInput
import geb.module.Select
import geb.module.TextInput
import org.openqa.selenium.By
import tk.hildebrandt.testcontainers.DatabaseType

class LoginPage extends Page {

   static url = 'http://adminer:8080/all'

   static at = { title == 'Login - Adminer' }

   static content = {
      dbmsInput { $(By.name('auth[driver]')).module(Select) }
      serverInput { $(By.name('auth[server]')).module(TextInput) }
      usernameInput { $(By.name('auth[username]')).module(TextInput) }
      passwordInput { $(By.name('auth[password]')).module(PasswordInput) }
      databaseInput { $(By.name('auth[db]')).module(TextInput) }
      submitButton {
         $('input', type: 'submit', value: 'Login').module(FormElement)
      }
   }

   SelectDatabasePage login(DatabaseType databaseType) {
      dbmsInput.setSelected(databaseType.getAdminerType()) //server for mysql
      serverInput.setText(databaseType.getNetworkAlias())
      usernameInput.setText('adminertest')
      passwordInput.setText('test1234')
      submitButton.click(SelectDatabasePage)
      browser.page as SelectDatabasePage
   }
}

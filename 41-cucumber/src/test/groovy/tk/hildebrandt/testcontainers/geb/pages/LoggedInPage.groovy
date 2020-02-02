package tk.hildebrandt.testcontainers.geb.pages

import geb.Page
import geb.module.FormElement
import geb.module.PasswordInput
import geb.module.Select
import geb.module.TextInput

class LoggedInPage extends Page {

   static url = 'http://adminer:8080/all'

   static at = {
      title != 'Login - Adminer' &&
         (title.startsWith('Schema:') || title.startsWith('Database:')) &&
         title.endsWith('Adminer')
   }

   static content = {
      dbmsInput { $(By.name('auth[driver]')).module(Select) }
      serverInput { $(By.name('auth[server]')).module(TextInput) }
      usernameInput { $(By.name('auth[username]')).module(TextInput) }
      passwordInput { $(By.name('auth[password]')).module(PasswordInput) }
      serverInput { $(By.name('auth[db]')).module(TextInput) }
      submitButton { $('input', type: 'submit').module(FormElement) }
   }

   void login() {
      dbmsInput.setSelected('pgsql') //server for mysql
      usernameInput.setText('postgres')
      passwordInput.setText('test1234')
      serverInput.setText('postgres')
      submitButton.click()
   }
}

package tk.hildebrandt.testcontainers.geb.pages

import geb.Page
import geb.module.FormElement
import geb.module.Select

class ManageDatabasePage extends Page {

   static at = {
      (title.startsWith('Schema:') || title.startsWith('Database:')) && title.endsWith('Adminer')
   }

   static content = {
      databaseSelect { $(Byte.name('db')).module(Select)}
      createTableLink { $("p.links")*.find(href: endsWith("&create="))}
   }
}

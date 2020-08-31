package tk.hildebrandt.testcontainers.geb

import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import tk.hildebrandt.testcontainers.TestContext
import tk.hildebrandt.testcontainers.geb.pages.LoginPage
import tk.hildebrandt.testcontainers.geb.pages.ManageDatabasePage
import tk.hildebrandt.testcontainers.geb.pages.SelectDatabasePage

class AdminerSteps extends GebEnabledStep {

   AdminerSteps(TestContext testContext) {
      super(testContext)
   }

   @When('^user set valid credentials for current dbms in web browser$')
   void login() {
      to(LoginPage)
         .login(testContext.dbmsType)
   }

   @Then('^she is able to select an database schema ([a-z_]*)')
   ManageDatabasePage selectDatabase(String databaseName){
      continueAt(SelectDatabasePage)
         .selectDatabaseByName(databaseName)
   }
}

Feature: Multiple database support
   Adminer should support mysql, postgres, ... databases.

   Scenario Outline: Login to <dbmsType> with version <dbmsVersion>
      Given A running database instance of type <dbmsType> with version <dbmsVersion>
      And a running instance of adminer
      When user set valid credentials for current db in web browser
      Examples:
         | dbmsType | dbmsVersion |
         | postgres | 9.6         |
         | postgres | 10          |
         | postgres | 11          |
         | postgres | latest      |
         | mysql    | 5.7         |

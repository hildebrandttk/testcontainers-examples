Feature: Multiple database support
   Adminer should support mysql, postgres, ... databases.

   Scenario Outline: Login to <dbmsType> with version <dbmsVersion>
      Given A running database instance of type <dbmsType> with version <dbmsVersion>
      And a running instance of adminer
      When user set valid credentials for current dbms in web browser
      And she is able to select an database schema <databaseSchema>
      Examples:
         | dbmsType | dbmsVersion | databaseSchema |
         | postgres | 9.6         | adminertest |
         | postgres | 10          | adminertest |
         | postgres | 11          | adminertest |
         | postgres | 12          | adminertest |
         | postgres | latest      | adminertest |
         | mysql    | 5.7         | adminertest |
         | mysql    | 8           | adminertest |

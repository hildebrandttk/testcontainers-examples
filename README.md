# testcontainers-examples
Examples from my conference talk (https://h9t.eu/s/tc) abount testcontainers in 2019 and 2020.


+ Plain.java: introduce base concepts, ryuk, ...
+ 1?-junit4*: introduce junit4 support with different flavors of container lifestyles  
+ 1?-junit5*: introduce junit5 support with different flavors of container lifestyles
+ 30-spring-boot-example-app-app: minimal example spring-data-jdbc app with tests using h2 database 
+ 21-spring-boot-junit-4-per-testclass: junit4 spring boot test setup 
+ 22-spring-boot-junit-5-per-testclass: junit5 example with container per test class
+ 23-spring-boot-junit-5-per-testsuite: junit5 example with container per test suite
+ 24-spring-boot-embedded: example using testcontainers-spring-boot embedded mode [https://github.com/testcontainers/testcontainers-spring-boot]
+ 30-selenium: pure selenium example performing a bing search with recording
+ 41-cucumber: cucumber, selenium and geb example performing a login check with 
adminer with different dbms. Cucumber is used to setup database and 
adminer as cucumber steps. Geb tests drive the selenium tests with the login.
* docker: contains a script and dockerfiles to create prefilled mysql and postgres images used by some examples. 
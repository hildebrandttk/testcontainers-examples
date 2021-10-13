# testcontainers-examples
Examples from my conference talk (https://h9t.eu/s/tc) abount testcontainers in 2019 and 2020.

*Create missing containers with docker/build.(sh|bat) before running database tests*

+ 0?-*: introduce base concepts, ryuk, ...
+ 1?-junit4*: introduce junit4 support with different flavors of container lifestyles. 
+ 1?-junit5*: introduce junit5 support with different flavors of container lifestyles.
+ 2?-*: different options creating containers on the fly
+ 30-spring-boot-example-app-app: minimal example spring-data-jdbc app with tests using h2 database. 
+ 31-spring-boot-junit-4-per-testclass: junit4 spring boot test setup.
+ 32-spring-boot-junit-5-per-testclass: junit5 example with container per test class.
+ 33-spring-boot-junit-5-per-testsuite: junit5 example with container per test suite.
+ 34-spring-boot-embedded: example using testcontainers-spring-boot embedded mode https://github.com/testcontainers/testcontainers-spring-boot.
+ 35-spring-boot-testcontainers-jdbc-driver: using tc-jdbc-driver with spring-boot-test https://www.testcontainers.org/modules/databases/.
+ 40-selenium: pure selenium example performing a bing search.
+ 41-selenium-recording: pure selenium example performing a bing search with recording.
+ 42-cucumber: cucumber, selenium and geb example performing a login check with on
adminer with different dbmss. Cucumber is used to setup database and 
adminer as cucumber steps. Geb tests drive the selenium tests with the login. Cucumber generates a html report including recorded video captures.
 https://www.adminer.org/de/
+ 43-docker-compose: use docker-compose to spin off an complex environment under test.  
* docker: contains a script and dockerfiles to create prefilled mysql and postgres images used by some examples.

*List of docker images for preload*

```bash
docker pull postgres:9.6
docker pull postgres:10
docker pull postgres:11
docker pull postgres:12
docker pull postgres:13
docker pull postgres:14
docker pull postgres:latest
docker pull mysql:5.7
docker pull mysql:8
docker pull adminer:latest
docker pull selenium/standalone-chrome-debug:3.141.59
docker pull selenium/standalone-firefox-debug:3.141.59
docker pull adoptopenjdk:11-hotspot
docker pull testcontainers/ryuk:0.3.1
```

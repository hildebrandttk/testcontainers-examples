package tk.hildebrandt.testcontainers

enum DatabaseType {
   MYSQL('mysql', 'server'),
   POSTGRES('postgres', 'pgsql')

   final String dockerImageName
   final String adminerType

   DatabaseType(String dockerImageName, String adminerType) {
      this.dockerImageName = dockerImageName
      this.adminerType = adminerType
   }

   static DatabaseType byDockerImageName(String imageName) {
      values().find { i -> i.dockerImageName.equals(imageName) }
   }

   String getAdminerType() {
      adminerType
   }

   String getNetworkAlias() {
      dockerImageName
   }
}

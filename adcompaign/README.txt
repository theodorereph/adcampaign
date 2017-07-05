Ad Campaign Server is a Spring Boot application that has restful endpoints
    localhost:8080/ad is a POST service that will take a JSON message and creates an ad campaign
    localhost:8080/ad/{partner_id} is a GET service that will return active campaign for a specified partner id
    localhost:8080/ad/allCampaigns is a GET service that will return ALL campaigns

How to compile
    Uses Java 1.8
    Download maven from https://maven.apache.org/download.cgi?Preferred=ftp%3A%2F%2Fmirror.reverse.net%2Fpub%2Fapache%2F
    JAVA_HOME must be set
    PATH must have $JAVA_HOME/bin and the bin directory of the maven
    mvn clean install

    NOTE: integration and unit tests will be executed as when the mvn install is executed

How to run
    java -jar target/ad-compaign-1.0-SNAPSHOT.jar

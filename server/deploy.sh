kill -9 $(lsof -t -i:8080)
echo "Killed process running on port 8080"
#java -jar /Users/macbookpro/IdeaProjects/crud_application/target/crud_application-0.0.1-SNAPSHOT.jar
java -jar /home/crud_application-0.0.1-SNAPSHOT.jar
echo "Started server using java -jar command"

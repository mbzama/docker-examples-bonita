java -version
javac -version

cd pom-master
mvn clean install

cd ..
cd hr-rest
mvn clean compile package

cd ..
cd hr-web
mvn clean compile package

cd ..
#docker-compose up -d --build

#docker ps
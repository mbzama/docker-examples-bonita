# Components
 - **hr-web:** Web UI to user management
 - **hr-rest:** APIs to add/update/delete users from Bonita 
 - **bonita-engine:** Create/Manage cases/tasks
 - **postgres:** Used by bonita engine to store process definitions, metadata, business objects, user data etc.,
 - **mongodb:** To store transaction history
![alt text](https://github.com/mbzama/docker-examples-bonita/blob/master/2_hr-app/hr-app.png)

# Technical Stack
 - Spring mvc
 - Angular 1
 - Hibernate
 - Postgres
 - MAVEN
 - Java 8
 - Bonita Engine 7.9.2
 
 # Instructions to run the POC:
 1. Check out as zip or clone the [repo](https://github.com/mbzama/docker-examples-bonita) 
 
 2. Navigate to 2_hr-app folder:
    `cd 2_hr-app`

 3. Run this shell script to pull, build and run the docker container:
    `./build.sh`

 4. Make sure all these containers are running:
    `docker ps`
    ![alt text](https://github.com/mbzama/docker-examples-bonita/blob/master/verify_containers.png)

 5. Bonita setup: All the related files are in [artifacts](https://github.com/mbzama/docker-examples-bonita/tree/master/artifacts) folder. Please find the detailed instructions [here](https://github.com/mbzama/docker-examples-bonita/blob/master/1_bonita/Bonita_Setup.docx) 
    - Install organisation and add walter.bates user to Administrator/User profiles
    - Install BDM
    - Install Bar file
    - Activate the process


# Verifying / Testing:
 1. Access the web app using:
    http://localhost:9999/hr-web/

 2. For testing APIs import this [file](https://github.com/mbzama/docker-examples-bonita/blob/master/artifacts/hr-app.postman_collection.json) in POSTMAN app
 3. For accessing POSTGRES database use [pgAdmin](https://www.pgadmin.org/download/) tool and connect using this:
      - **URL:** http://localhost:5432/
      - **Credentials:** postgres / example
 4. For accessing MONGODB use [mongodb compass](https://docs.mongodb.com/compass/master/install/)      
      - **URL:** http://localhost:27017/
      - **Credentials:**
 5. For Bonita Portal:
      - **URL:** http://localhost:8080/bonita/login.jsp?redirectUrl=%2Fbonita%2Fportal%2Fhomepage
      - **Credentials:**
      
         --> **Technical User:** tech_user / secret
         
         --> **Admin / User:** walter.bates / bpm
         


# Upload image to public docker registry (dockerhub)
   Please refer to [this](https://github.com/mbzama/docker-training/blob/master/README.md#upload-image-to-public-registry-dockerhub)


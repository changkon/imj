# Internet Movie Journal
Software Engineering 325 Main Assignment. RESTful Web service using Java's JAX-RS.  

## Outline  
Interne Movie Journal is a tracking system which logs records of viewers watching movies. Users can view their logs in an online persistent database.  

IMJ uses imdb api for some of the information (recommended movie, movie profile)  
More information: http://www.omdbapi.com/

### Users
* Viewer
* Administrator

### Functionality  
* Log movie view
* View user details
* View list of users
* View logs of specific user
* View recommended movies for specific user
* View movie and list of users that watched it
* Update database information (Movies/Viewer)
* Delete database information(Movies/Viewer)
* Notification system of recommended movies

## Setup  
Clone the git repository or download zip file.  
```
git clone https://github.com/changkon/se325_main.git
```

### Eclipse  
File -> Import -> Maven -> Existing Maven Projects -> Change root directory to root directory of project -> Finish

## Testing  
In Eclipse, to run the junit tests:  
Right click project -> Run as.. -> Maven install  

If you don't have maven, it must be installed.  

## Web application  
To run the web application:  
Right click project -> Run as.. -> Run Configurations.. -> Create new Run configuration with Goals: **jetty:run**  

View the web application by going to
```
localhost:10003
```

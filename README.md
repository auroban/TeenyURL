# TeenyURL
A simple URL shortening application

To check the app in action, kindly run the following **cURL** command

```
curl -X POST \
  https://turls.in/v1/url/ \
  -H 'Content-Type: application/json' \
  -d '{
	"url" : "https://www.facebook.com"
}'
```

## Getting Started
This project requires **Redis** and **MongoDB** along with **JDK 13** to run it locally. The configurations required for running this app and connecting it to Redis and Mongo are specified in **application-dev.yml** file.

### Prerequisites
* **Redis** - [Install Redis](https://redis.io/topics/quickstart) and ensure it's running on the default port *6379*. If redis is running on a different port, please specify in the **application-dev.yml** file under `redisson` section.
* **MongoDB** - [Install MongoDB](https://docs.mongodb.com/manual/installation/) and ensure it's running on the default port *27017*. If MongoDB is running on a different port, please specify in the **application-dev.yml** file under `spring.data.mongodb` section
* **Java Development Kit** - [Install JDK](https://www3.ntu.edu.sg/home/ehchua/programming/howto/JDK_Howto.html) and ensure the version is **13 or above**

Please make a directory named ***.teenyurlservice*** under your home directory, copy the **counter.txt** file given in this project directory and paste it in the aforementioned directory that you just created. 

The app runs on port ***3001***, but this can be changed in the **application-dev.yml**

### Running the app
Once you have everything set up and running, please run the **run.sh** file given in this project directory

```
sh run.sh
```

You should now have the app running and it can be seen on your `localhost:3001`

### Testing it locally
To test it locally, make a **POST** request to `localhost:3001/v1/url` with the following request body: 
```
{
	"url" : "{your_long_url}"
}
```

Eg. 
```
{
	"url" : "http://iamtheproudownerofthelongestlongestlongestdomainnameinthisworld.com/"
}
```



## Alternating Iterator
- The alternating iterator is not thread safe
- The implementation is included in pinto.cleo.userdemo.util.AlternatingIterator and the corresponding tests are 
    in /test/ pinto.cleo.userdemo.util.AlternatingIteratorSpec

## UserDemo  
### Assumptions -

- Used an embedded data source since this is a sample demo. Will need to use a reliable persistance store like Oracle, 
    Postgres etc in PROD
- Included spring actuator that provides health endpoints
- PUT action can only be performed on users that are already present in the DB. Hence the user has to be created first.
    I have added additional endpoints POST /user and GET /user/{id} to help validate
- I have used an 'id' as a unique identifier for a user
- Email id need not be unique across users (PUT/POST). If we need it to be then we need to add additional checks
- Used the basic email validation check. If we need additional validation on this, we need to write a custom JSR validator
- Some of the things i have not looked into for this particular demo - integration tests, validation edge cases, build & deployment pipeline etc

### Demo

Download the zip file, move it to a folder of your choice and unzip. Or clone this repository.
cd into 'user-app-demo' folder and execute
```commandline
./gradlew clean build && java -jar ./build/libs/user-demo-0.0.1.jar
```
or 
```commandline
./gradlew bootrun
```

#### Example to create a user

```commandline
curl --request POST \
  --url http://localhost:8080/user/ \
  --header 'content-type: application/json' \
  --data '{
"name": "Steven",
"email": "stevengerrard@lfc.com"
}'
```
##### Response - The id of the new user created is returned
```json
{
	"messages": [
		"User successfully created. User id is: 1"
	]
}
```

#### Example to update a user
```commandline
curl --request PUT \
  --url http://localhost:8080/user/1 \
  --header 'content-type: application/json' \
  --data '{
	"name": "Steven Gerrard",
	"email": "stevengerrard@gmail.com"
}'
```

##### Response - 204 with no content if the action is successful 
##### Error - 404 NOT_FOUND
```json
{
	"errors": [
		"The resource 10 was not found. Hence could not be updated."
	]
}
```
##### Error - 400 BAD_REQUEST
```json
{
	"errors": [
		{
			"field": "email",
			"message": "must be a well-formed email address"
		},
		{
			"field": "name",
			"message": "must not be empty"
		}
	]
}
```

#### Example to fetch a user
```commandline
curl --request GET \
  --url http://localhost:8080/user/1
```
##### Response 200 OK
```json
{
	"id": 1,
	"name": "Steven Gerrard",
	"email": "stevengerrard@lfc.com"
}
```
##### Response 404 NOT_FOUND
```json
{
	"errors": [
		"The requested resource 10 was not found"
	]
}
```

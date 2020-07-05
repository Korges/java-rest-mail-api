# java-rest-mail-api

## 1. Set up your email credentials under application.properties
you can check example there\
https://www.baeldung.com/spring-email#2-spring-boot-mail-server-properties

## 2. Create email
POST http://localhost:8080/emails
</br>
sample json input:
```
{
    "subject": "Welcome",
    "text": "How are you?",
    "recipients": [
    	"some@email.com"
    ]
}
```
response:
```
{
    "id": "5f0202f2c2f3ab3234d3bac6",
    "subject": "Welcome",
    "text": "How are you?",
    "recipients": [
        "some@email.com"
    ],
    "attachments": [],
    "emailStatus": "PENDING",
    "priority": "LOWEST"
}
```
## 3. Send email
POST http://localhost:8080/emails/send/5f0202f2c2f3ab3234d3bac6
</br>
response:
<pre>
{
    "id": "5f0202f2c2f3ab3234d3bac6",
    "subject": "Welcome",
    "text": "How are you?",
    "recipients": [
        "some@email.com"
    ],
    "attachments": [],
    <b>"emailStatus": "SENT"</b>,
    "priority": "LOWEST"
}
</pre>
## 4. You can add attachments. Enter attachment filepath. You can put them under /resources package
```
{
    "id": "5f0202f2c2f3ab3234d3bac6",
    "subject": "Welcome",
    "text": "How are you?",
    "recipients": [
        "some@email.com"
    ],
    "attachments": ["attachment.txt"],
    "emailStatus": "SENT",
    "priority": "LOWEST"
}
```
## 5. You can set priority. By default its LOWEST
* HIGHEST
* HIGH
* MIDDLE
* LOW
* LOWEST
</br>
example:
```
POST http://localhost:8080/emails
{
    "subject": "Welcome",
    "text": "How are you?",
    "recipients": [
    	"some@email.com"
    ],
    "priority": "HIGHEST"
}
```
6. All API methods:
* Find all emails in the system
\
GET http://localhost:8080/emails
* Find email by id
\
GET http://localhost:8080/emails/{id}
* Check status of the email
\
GET http://localhost:8080/emails/{id}/status
* Create new email
\
POST http://localhost:8080/emails
* Update email
\
PUT http://localhost:8080/emails/{id}
* Send email
\
POST http://localhost:8080/emails/send/{id}
* Send all pending emails
\
POST http://localhost:8080/emails/send/all

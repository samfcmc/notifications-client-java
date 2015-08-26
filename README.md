notifications-client-java
=========================

Java Client for the [Notifications Service](https://github.com/samfcmc/bennu-notifications). The Client is responsible for generating and sending notifications to the Notifications Service.

NOTE: This is not a consumer. The Client just creates and sends notifications. It does not "consume" them.

Installation
============

If you are using Maven, you need to add the dependency to your `pom.xml` file

```xml
...
<dependencies>
  <dependency>
    <groupId>org.fenixedu</groupId>
    <artifactId>bennu-notifications-client</artifactId>
    <version>1.0</version>
  </dependency>
  ...
</dependencies>
```

Requirements
============

-	MySQL
-	Maven
-	JDK 8

Usage
=====

In order to be able to use this client, first you need to have a MySQL server running and a database configured for this project.

This client is a REST API client for the Notifications Service. It seems weird that it requires a database but we want to make sure that everything works, even if the Notifications Service is down. When this happens, the client stores the notifications in a persistent way so it can try to send all pending notifications when the service becomes available again.

Then we need to create an instance of the `Client` class, so we can use that instance to create and send notifications.

Configure a database
--------------------

Connect to your MySQL server and create a database, for instance`notifications` .

In your project that uses this client you need a file `fenix-framework.properties` at `src/main/resources`. This file should look like this:

```
dbAlias = //localhost:3306/notifications
dbUsername = root
dbPassword = root
updateRepositoryStructureIfNeeded = true
```

Change the `dbAlias`, `dbUsername` and `dbPassword` according to your database settings.

Instantiate the client
----------------------

To instantiate a client you need to use `NotificationsClientFactory` class. You can do it using one of the two possible ways. Using a properties file and passing the needed values directly.

### Using a properties file

First, you need a file `notifications.properties` at`src/main/resources` that looks like this:

```
url       = <Url where the notifications service is running>
appId     = <application id generated by the notifications service>
appSecret = <application secret key generated by the notifications service>
```

Then, in your code, you can instantiate the client:

```java
NotificationsClient client = NotificationsClientFactory.getClientFromPropertiesFile();
```

If your file has a name different that `notifications.properties` you have to pass it explicitly to the `NotificationsClientFactory` :

```java
NotificationsClient client = NotificationsClientFactory,getClientFromPropertiesFile("filename");
```

### Passing the needed parameters directly

If you don't want to use a properties file you can pass all the parameters directly to the `NotificationsClientFactory` class:

```java
NotificationsClient client = NotificationsClientFactory.getClient("url", "app Id", "app Secret");
```

Send a notification
-------------------

After you have instantiated the `NotificationsClient` class you can use that instance to create and send notifications.

In order to create a notification you need to provide the username(s) of the user(s) to who you want to send notifications and the payload. The payload is just a Json object with some properties that the server stores and consumers use to show the notifications in a proper way. You don't need to know the struture of this object. We have a builder to help you create this payload in a structured way.

To create this payload you need to use `Notification.Builder` class. Here you have a full example of what to do in your code to send a notification to some users.

```java
NotificationsClient client = NotificationsClientFactory.getClientFromPropertiesFile();

String username1 = "username1";
String username2 = "username2";

Notification.Builder builder = new Notification.Builder();
build.link("link").image("url of an image").type("string type").description("EN", "description text in english").usernames("username1", "username2", ....);

// Sending a notification to multiple users
client.postNotification(builder.build());
```

The `image` and `link` attributes are optional. However, `type` and `description`, and `usernames` are mandatory. If you try to build a `NotificationPayload` object without specifying the`type`, with at least, one `description` in one language and with, at least, one username, it will raise an exception.

You can provide a description in more than one language:

```java
// Provide a description in english
builder.description("EN", "Description in english");
// Provide a description in portuguese
builder.description("PT", "Descrição em português");
```

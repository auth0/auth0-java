# Auth0 Java

[![Build][travis-ci-badge]][travis-ci-url]
[![MIT][mit-badge]][mit-url]
[![Maven][maven-badge]][maven-url]
[![JCenter][jcenter-badge]][jcenter-url]

Java client library for the [Auth0](https://auth0.com) platform.

## Download

Get Auth0 Java via Maven:

```xml
<dependency>
  <groupId>com.auth0</groupId>
  <artifactId>auth0</artifactId>
  <version>1.0.0</version>
</dependency>
```

or Gradle:

```gradle
compile 'com.auth0:auth0:1.0.0'
```

Make sure your client type is set to `Non Interactive Client`.


## Auth API

The implementation is based on the [Authentication API Docs](https://auth0.com/docs/api/authentication).

Create a new `AuthAPI` instance by providing the client data from the [dashboard](https://manage.auth0.com/#/clients).

```java
AuthAPI auth = new AuthAPI("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}");
```

### Authorize - /authorize

Creates an `AuthorizeUrlBuilder` to authenticate the user with an OAuth provider. The `redirectUri` must be white-listed in the "Allowed Callback URLs" section of the Client Settings. Parameters can be added to the final url by using the builder methods. When ready, call `build()` and obtain the Url.

`AuthorizeUrlBuilder authorize("{CONNECTION}", "{REDIRECT_URI}")`

Example:
```java
AuthorizeUrlBuilder builder = auth.authorize("facebook", "https://me.auth0.com/callback");
builder.withAudience("https://api.me.auth0.com/users");
builder.withScope("openid contacts");
builder.withState("state123");

String url = builder.build();
// https://me.auth0.com/authorize?redirect_uri=https://me.auth0.com/callback&response_type=code&client_id=iaih2D7AC56kksdkPtWAsjI3li&audience=https://api.me.auth0.com/users&scope=openid%20contacts&connection=facebook&state=state123
```

### Logout - /v2/logout
Creates a `LogoutUrlBuilder` to log out the user. The `returnToUrl` must be white-listed in the "Allowed Logout URLs" section of the Client Settings. Parameters can be added to the final url by using the builder methods. When ready, call `build()` and obtain the Url.

`LogoutUrlBuilder logout("{RETURN_TO_URL}", "{SEND_CLIENT_ID}")`

Example:
```java
LogoutUrlBuilder builder = auth.logout("https://me.auth0.com/home", true);
builder.useFederated(true);
builder.withAccessToken("aToKen");

String url = builder.build();
// https://me.auth0.com/v2/logout?returnTo=https://me.auth0.com/home&client_id=iaih2D7AC56kksdkPtWAsjI3li&access_token=aToKen&federated=
```

### UserInfo - /userinfo
Creates a new request to get the user information associated to a given access token. This will only work if the token has been granted the `openid` scope.

`Request<UserInfo> userInfo("{ACCESS_TOKEN}")`

Example:
```java
Request<UserInfo> request = auth.userInfo("nisd1h9dk.....s1doWJOsaf");
try {
    UserInfo info = request.execute();
    // info.getValues();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

### Reset Password - /dbconnections/change_password
Creates a new request to reset the user's password. This will only work for db connections.

`Request resetPassword("{EMAIL}", "{CONNECTION}")`

Example:
```java
Request request = auth.resetPassword("user@domain.com", "Username-Password-Authentication");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```


### Sign Up - /dbconnections/signup
Creates a new request to create a new user. Up to 10 additional Sign Up fields can be added to the request. This will only work for db connections.

`SignUpRequest signUp("{EMAIL}", "{USERNAME}", "{PASSWORD}", "{CONNECTION}")`

`SignUpRequest signUp("{EMAIL}", "{PASSWORD}", "{CONNECTION}")`

Example:
```java
SignUpRequest request = auth.signUp("user@domain.com", "username", "password123", "Username-Password-Authentication");
Map<String, String> fields = new HashMap<>();
fields.put("location", "Buenos Aires");
fields.put("age", "25");
request.setCustomFields(fields);
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

### Log In with Authorization Code - /oauth/token

Creates a new request to log in the user with a `code` previously obtained by calling the /authorize endpoint. The redirect uri must be the one sent in the /authorize call.

`AuthRequest loginWithAuthorizationCode("{CODE}", "{REDIRECT_URI}")`

Example:
```java
AuthRequest request = loginWithAuthorizationCode("asdfgh", "https://me.auth0.com/callback");
request.setAudience("https://api.me.auth0.com/users");
request.setScope("openid contacts");
try {
    TokenHolder holder = request.execute();
    // holder.getAccessToken();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

### Log In with Password - /oauth/token

Creates a new request to log in the user with `username` and `password`. The connection used is the one defined as "Default Directory" in the account settings.

`AuthRequest loginWithPassword("{USERNAME_OR_EMAIL}", "{PASSWORD}")`

Example:
```java
AuthRequest request = loginWithPassword("me@domain.com", "password123");
request.setAudience("https://api.me.auth0.com/users");
request.setScope("openid contacts");
try {
    TokenHolder holder = request.execute();
    // holder.getAccessToken();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

### Log In with Password Realm - /oauth/token

Creates a new request to log in the user with `username` and `password` using the Password Realm.

`AuthRequest loginWithPasswordRealm("{USERNAME_OR_EMAIL}", "{PASSWORD}", "{REALM}")`

Example:
```java
AuthRequest request = loginWithPasswordRealm("me@domain.com", "password123", "Username-Password-Authentication");
request.setAudience("https://api.me.auth0.com/users");
request.setScope("openid contacts");
try {
    TokenHolder holder = request.execute();
    // holder.getAccessToken();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

### Log In with Client Credentials - /oauth/token

Creates a new request to log in the user using the Client Credentials.

`AuthRequest loginWithClientCredentials("{AUDIENCE}")`

Example:
```java
AuthRequest request = loginWithClientCredentials("https://api.me.auth0.com/users");
request.setScope("openid contacts");
try {
    TokenHolder holder = request.execute();
    // holder.getAccessToken();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```


## Management API

The implementation is based on the [Management API Docs](https://auth0.com/docs/api/management/v2).

Create a new `MgmtAPI` instance by providing the domain from the [client dashboard](https://manage.auth0.com/#/clients) and the API Token. Click [here](https://auth0.com/docs/api/management/v2#!/Introduction/Getting_an_API_token) for more information on how to obtain a valid API Token.

```java
MgmtAPI mgmt = new MgmtAPI("{YOUR_DOMAIN}", "{YOUR_API_TOKEN}");
```

### Client Grants

To access the entity methods call `mgmt.clientGrants()`.

#### List

Creates a new request to list the Client Grants. An API Token with scope `read:client_grants` is required.

`Request<List<ClientGrant>> list()`

Example:
```java
Request<List<ClientGrant>> request = mgmt.clientGrants().list();
try {
    List<ClientGrant> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Create

Creates a new request to create a new Client Grant. An API Token with scope `create:client_grants` is required.

`Request<ClientGrant> create("{CLIENT_ID}", "{AUDIENCE}", [{SCOPE}])`

Example:
```java
Request<ClientGrant> request = mgmt.clientGrants().create("A2gnGsRYhk1v9Sf", "https://api.me.auth0.com/users", new String[]{"openid"});
try {
    ClientGrant response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Delete

Creates a new request to delete a Client Grant. An API Token with scope `delete:client_grants` is required.

`Request delete("{CLIENT_GRANT_ID}")`

Example:
```java
Request request = mgmt.clientGrants().delete("912");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Update

Creates a new request to update a Client Grant. An API Token with scope `update:client_grants` is required.

`Request<ClientGrant> update("{CLIENT_GRANT_ID}", {SCOPE})`

Example:
```java
Request request = mgmt.clientGrants().update("912", new String[]{"openid profile"});
try {
    ClientGrant response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```


### Clients

To access the entity methods call `mgmt.clients()`.

#### List

Creates a new request to list the Clients. An API Token with scope `read:clients` is needed. If you also need the client_secret and encryption_key attributes the token must have `read:client_keys` scope.

`Request<List<Client>> list()`

Example:
```java
Request<List<Client>> request = mgmt.clients().list();
try {
    List<Client> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Get

Creates a new request to get a Client. An API Token with scope `read:clients` is needed. If you also need the client_secret and encryption_key attributes the token must have `read:client_keys` scope.

`Request<Client> get("{CLIENT_ID}")`

Example:
```java
Request<Client> request = mgmt.clients().get("A2gnGsRYhk1v9Sf");
try {
    Client response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Create

Creates a new request to create a new Client. An API Token with scope `create:clients` is needed.

`Request<Client> create({DATA})`

Example:
```java
Client data = new Client(...);
Request<Client> request = mgmt.clients().create(data);
try {
    Client response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Delete

Creates a new request to delete a Client. An API Token with scope `delete:clients` is needed.

`Request delete("{CLIENT_ID}")`

Example:
```java
Request request = mgmt.clients().delete("A2gnGsRYhk1v9Sf");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Update

Creates a new request to update a Client. An API Token with scope `update:clients` is needed. If you also need to update the client_secret and encryption_key attributes the token must have `update:client_keys` scope.

`Request<Client> update("{CLIENT_ID}", {DATA})`

Example:
```java
Client data = new Client(...);
Request request = mgmt.clients().update("A2gnGsRYhk1v9Sf", data);
try {
    Client response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Rotate Secret

Creates a new request to rotate a Client's secret. An API Token with scope `update:client_keys` is needed.

`Request<Client> rotateSecret("{CLIENT_ID}")`

Example:
```java
Request<Client> request = mgmt.clients().rotateSecret("A2gnGsRYhk1v9Sf");
try {
    Client response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```


### Connections

To access the entity methods call `mgmt.connections()`.

#### List

Creates a new request to list the Connections. An API Token with scope `read:connections` is needed.
You can pass an optional Filter to narrow the results in the response.

`Request<List<Connection>> list({FILTER})`

Example:
```java
ConnectionFilter filter = new ConnectionFilter(...);
Request<List<Connection>> request = mgmt.connections().list(filter);
try {
    List<Connection> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Get

Creates a new request to get a Connection. An API Token with scope `read:connections` is needed.
You can pass an optional Filter to narrow the results in the response.

`Request<Connection> get("{CONNECTION_ID}", {FILTER})`

Example:
```java
ConnectionFilter filter = new ConnectionFilter(...);
Request<Connection> request = mgmt.connections().get("123", filter);
try {
    Connection response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Create

Creates a new request to create a new Connection. An API Token with scope `create:connections` is needed.

`Request<Connection> create({DATA})`

Example:
```java
Connection data = new Connection(...);
Request<Connection> request = mgmt.connections().create(data);
try {
    Connection response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Delete

Creates a new request to delete a Connection. An API Token with scope `delete:connections` is needed.

`Request delete("{CONNECTION_ID}")`

Example:
```java
Request request = mgmt.connections().delete("123");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Update

Creates a new request to update a Connection. An API Token with scope `update:connections` is needed.

`Request<Connection> update("{CONNECTION_ID}", {DATA})`

Example:
```java
Connection data = new Connection(...);
Request request = mgmt.connections().update("123", data);
try {
    Connection response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Delete User

Creates a new request to delete a User from a Connection. An API Token with scope `delete:users` is needed.

`Request delete("{CONNECTION_ID}", "{EMAIL}")`

Example:
```java
Request request = mgmt.connections().deleteUser("123", "me@auth0.com");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```


### Device Credentials

To access the entity methods call `mgmt.deviceCredentials()`.

#### List

Creates a new request to list the Device Credentials. An API Token with scope `read:device_credentials` is needed.
You can pass an optional Filter to narrow the results in the response.

`Request<List<DeviceCredentials>> list({FILTER})`

Example:
```java
DeviceCredentialsFilter filter = new DeviceCredentialsFilter(...);
Request<List<DeviceCredentials>> request = mgmt.deviceCredentials().list(filter);
try {
    List<DeviceCredentials> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Create

Creates a new request to create a new Device Credentials. An API Token with scope `create:current_user_device_credentials` is needed.

`Request<DeviceCredentials> create({DATA})`

Example:
```java
DeviceCredentials data = new DeviceCredentials(...);
Request<DeviceCredentials> request = mgmt.deviceCredentials().create(data);
try {
    DeviceCredentials response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Delete

Creates a new request to delete a Device Credentials. An API Token with scope `delete:device_credentials` is needed.

`Request delete("{DEVICE_CREDENTIALS_ID}")`

Example:
```java
Request request = mgmt.deviceCredentials().delete("123");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```


### Log Events

To access the entity methods call `mgmt.logEvents()`.

#### List

Creates a new request to list the Log Events. An API Token with scope `read:logs` is needed.
You can pass an optional Filter to narrow the results in the response.

`Request<LogEventsPage> list({FILTER})`

Example:
```java
LogEventFilter filter = new LogEventFilter(...);
Request<LogEventsPage> request = mgmt.logEvents().list(filter);
try {
    LogEventsPage response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Get

Creates a new request to get a Log Event. An API Token with scope `read:logs` is needed.

`Request<LogEvent> get("{LOG_EVENT_ID}")`

Example:
```java
Request<LogEvent> request = mgmt.logEvents().get("123");
try {
    LogEvent response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```


### Resource Servers

To access the entity methods call `mgmt.resourceServers()`.

#### List

Creates a new request to list the Resource Servers. An API Token with scope `read:resource_servers` is needed.

`Request<List<ResourceServer>> list()`

Example:
```java
Request<List<ResourceServer>> request = mgmt.resourceServers().list();
try {
    List<ResourceServer> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Get

Creates a new request to get a Resource Server. An API Token with scope `read:resource_servers` is needed.

`Request<ResourceServer> get("{RESOURCE_SERVER_ID}")`

Example:
```java
Request<ResourceServer> request = mgmt.resourceServers().get("123");
try {
    ResourceServer response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Create

Creates a new request to create a new Resource Server. An API Token with scope `create:resource_servers` is needed.

`Request<ResourceServer> create({DATA})`

Example:
```java
ResourceServer data = new ResourceServer(...);
Request<ResourceServer> request = mgmt.resourceServers().create(data);
try {
    ResourceServer response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Delete

Creates a new request to delete a Resource Server. An API Token with scope `delete:resource_servers` is needed.

`Request delete("{RESOURCE_SERVER_ID}")`

Example:
```java
Request request = mgmt.resourceServers().delete("123");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Update

Creates a new request to update a ResourceServer. An API Token with scope `update:resource_servers` is needed.

`Request<ResourceServer> update("{RESOURCE_SERVER_ID}", {DATA})`

Example:
```java
ResourceServer data = new ResourceServer(...);
Request request = mgmt.resourceServers().update("123", data);
try {
    ResourceServer response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```


### Rules

To access the entity methods call `mgmt.rules()`.

#### List

Creates a new request to list the Rules. An API Token with scope `read:rules` is needed.
You can pass an optional Filter to narrow the results in the response.

`Request<List<Rule>> list({FILTER})`

Example:
```java
Request<List<Rule>> request = mgmt.rules().list(filter);
try {
    List<Rule> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Get

Creates a new request to get a Rule. An API Token with scope `read:rules` is needed.
You can pass an optional Filter to narrow the results in the response.

`Request<Rule> get("{RULE_ID}", {FILTER})`

Example:
```java
Request<Rule> request = mgmt.rules().get("123", filter);
try {
    Rule response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Create

Creates a new request to create a new Rule. An API Token with scope `create:rules` is needed.

`Request<Rule> create({DATA})`

Example:
```java
Rule data = new Rule(...);
Request<Rule> request = mgmt.rules().create(data);
try {
    Rule response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Delete

Creates a new request to delete a Rule. An API Token with scope `delete:rules` is needed.

`Request delete("{RULE_ID}")`

Example:
```java
Request request = mgmt.rules().delete("123");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Update

Creates a new request to update a Rule. An API Token with scope `update:rules` is needed.

`Request<Rule> update("{RULE_ID}", {DATA})`

Example:
```java
Rule data = new Rule(...);
Request request = mgmt.rules().update("123", data);
try {
    Rule response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```


### User Blocks

To access the entity methods call `mgmt.userBlocks()`.

#### Get

Creates a new request to get the User Blocks. An API Token with scope `read:users` is needed.

`Request<UserBlocks> get("{USER_ID}")`

Example:
```java
Request<UserBlocks> request = mgmt.userBlocks().get("auth0|123");
try {
    UserBlocks response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Get by Identifier

Creates a new request to get the User Blocks by Identifier. The Identifier can be a username, phone_number, or email. An API Token with scope `read:users` is needed.

`Request<UserBlocks> getByIdentifier("{IDENTIFIER}")`

Example:
```java
Request<UserBlocks> request = mgmt.userBlocks().getByIdentifier("me@auth0.com");
try {
    UserBlocks response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Delete

Creates a new request to delete the User Blocks. An API Token with scope `update:users` is needed.

`Request delete("{USER_ID}")`

Example:
```java
Request request = mgmt.userBlocks().delete("auth0|123");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Delete by Identifier

Creates a new request to delete the User Blocks by Identifier. The Identifier can be a username, phone_number, or email. An API Token with scope `update:users` is needed.

`Request deleteByIdentifier("{IDENTIFIER}")`

Example:
```java
Request request = mgmt.userBlocks().deleteByIdentifier("auth0|123");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```


### Users

To access the entity methods call `mgmt.users()`.

#### List

Creates a new request to list the Users. An API Token with scope `read:users` is needed. If you want the identities.access_token property to be included, you will also need the scope `read:user_idp_tokens`.
You can pass an optional Filter to narrow the results in the response.

`Request<UsersPage> list({FILTER})`

Example:
```java
UserFilter filter = new UserFilter(...);
Request<UsersPage> request = mgmt.users().list(filter);
try {
    UsersPage response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Get

Creates a new request to get a User. An API Token with scope `read:users` is needed. If you want the identities.access_token property to be included, you will also need the scope `read:user_idp_tokens`.
You can pass an optional Filter to narrow the results in the response.

`Request<User> get("{USER_ID}", {FILTER})`

Example:
```java
UserFilter filter = new UserFilter(...);
Request<User> request = mgmt.users().get("auth0|123", filter);
try {
    User response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Create

Creates a new request to create a new User. An API Token with scope `create:users` is needed.

`Request<User> create({DATA})`

Example:
```java
User data = new User(...);
Request<User> request = mgmt.users().create(data);
try {
    User response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Delete

Creates a new request to delete a User. An API Token with scope `delete:users` is needed.

`Request delete("{USER_ID}")`

Example:
```java
Request request = mgmt.users().delete("auth0|123");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Update

Creates a new request to update a User. An API Token with scope `update:users` is needed. If you're updating app_metadata you'll also need `update:users_app_metadata` scope.

`Request<User> update("{USER_ID}", {DATA})`

Example:
```java
User data = new User(...);
Request request = mgmt.users().update("auth0|123", data);
try {
    User response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Get Guardian Enrollments

Creates a new request to list the User's Guardian Enrollments. An API Token with scope `read:users` is needed.

`Request<List<Enrollment>> getEnrollments("{USER_ID}")`

Example:
```java
Request<List<Enrollment>> request = mgmt.users().getEnrollments("auth0|123");
try {
    List<Enrollment> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Get Log Events

Creates a new request to list the User's Log Events. An API Token with scope `read:logs` is needed.
You can pass an optional Filter to narrow the results in the response.

`Request<LogEventsPage> getLogEvents("{USER_ID}", {FILTER})`

Example:
```java
LogEventFilter filter = new LogEventFilter(...);
Request<LogEventsPage> request = mgmt.users().getLogEvents("auth0|123", filter);
try {
    LogEventsPage response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```


#### Delete Multifactor Provider

Creates a new request to delete the User's Multifactor Provider. An API Token with scope `update:users` is needed.

`Request deleteMultifactorProvider("{USER_ID}", "{MULTIFACTOR_PROVIDER}")`

Example:
```java
Request request = mgmt.users().deleteMultifactorProvider("auth0|123", "duo");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Rotate Recovery Code

Creates a new request to rotate the User's Recovery Code. An API Token with scope `update:users` is needed.

`Request<RecoveryCode> rotateRecoveryCode("{USER_ID}")`

Example:
```java
Request<RecoveryCode> request = mgmt.users().rotateRecoveryCode("auth0|123");
try {
    RecoveryCode response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Link Identities

Creates a new request to link two User identities. An API Token with scope `update:users` is needed.

`Request<List<Identities>> linkIdentity("{PRIMARY_USER_ID}", "{SECONDARY_USER_ID}", "{PROVIDER}", "{CONNECTION_ID}")`

Example:
```java
Request<List<Identities>> request = mgmt.users().linkIdentity("auth0|123", "124", "facebook", "c90");
try {
    List<Identities> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Un-Link Identities

Creates a new request to un-link two User identities. An API Token with scope `update:users` is needed.

`Request<List<Identities>> unlinkIdentity("{PRIMARY_USER_ID}", "{SECONDARY_USER_ID}", "{PROVIDER}")`

Example:
```java
Request<List<Identities>> request = mgmt.users().unlinkIdentity("auth0|123", "124", "facebook");
try {
    List<Identities> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```


### Blacklists

To access the entity methods call `mgmt.blacklists()`.

#### List

Creates a new request to get the Blacklisted Tokens. An API Token with scope `blacklist:tokens` is needed.

`Request<List<Token>> getBlacklist("{AUDIENCE}")`

Example:
```java
Request<List<Token>> request = mgmt.blacklists().getBlacklist("https://api.me.auth0.com/users");
try {
    List<Token> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Add

Creates a new request to add a Token to the Blacklist. An API Token with scope `blacklist:tokens` is needed.

`Request blacklistToken({TOKEN})`

Example:
```java
Token data = new Token(...);
Request request = mgmt.blacklists().blacklistToken(data);
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```


### Email Provider

To access the entity methods call `mgmt.emailProvider()`.

#### Get

Creates a new request to get the Email Provider. An API Token with scope `read:email_provider` is required.
You can pass an optional Filter to narrow the results in the response.

`Request<EmailProvider> get({FILTER})`

Example:
```java
FieldsFilter filter = new FieldsFilter(...);
Request<EmailProvider> request = mgmt.emailProvider().get();
try {
    EmailProvider response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Setup

Creates a new request to setup the Email Provider. An API Token with scope `create:email_provider` is required.

`Request<EmailProvider> create({DATA})`

Example:
```java
EmailProvider data = new EmailProvider(...);
Request<EmailProvider> request = mgmt.emailProvider().setup(data);
try {
    EmailProvider response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Delete

Creates a new request to delete a Email Provider. An API Token with scope `delete:email_provider` is required.

`Request delete()`

Example:
```java
Request request = mgmt.emailProvider().delete();
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Update

Creates a new request to update a Email Provider. An API Token with scope `update:email_provider` is required.

`Request<EmailProvider> update({DATA})`

Example:
```java
EmailProvider data = new EmailProvider(...);
Request<EmailProvider> request = mgmt.emailProvider().update(data);
try {
    EmailProvider response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```


### Guardian

To access the entity methods call `mgmt.guardian()`.

#### Create Enrollment Ticket

Creates a new request to create a new Enrollment Ticket. An API Token with scope `create:guardian_enrollment_tickets` is required.

`Request<EnrollmentTicket> createEnrollmentTicket()`

Example:
```java
EnrollmentTicket data = new EnrollmentTicket(...);
Request<EnrollmentTicket> request = mgmt.guardian().createEnrollmentTicket(data);
try {
    EnrollmentTicket response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Delete Enrollment

Creates a new request to delete an Enrollment. An API Token with scope `delete:guardian_enrollments` is required.

`Request deleteEnrollment({ENROLLMENT_ID})`

Example:
```java
Request request = mgmt.guardian().deleteEnrollment("123");
try {
    request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Get Templates

Creates a new request to get the Templates. An API Token with scope `read:guardian_factors` is required.

`Request<GuardianTemplates> getTemplates()`

Example:
```java
Request<GuardianTemplates> request = mgmt.guardian().getTemplates();
try {
    GuardianTemplates response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Update Templates

Creates a new request to update the Templates. An API Token with scope `update:guardian_factors` is required.

`Request<GuardianTemplates> updateTemplates({DATA})`

Example:
```java
GuardianTemplates data = new GuardianTemplates(...);
Request<GuardianTemplates> request = mgmt.guardian().updateTemplates(data);
try {
    GuardianTemplates response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### List Factors

Creates a new request to list the Factors. An API Token with scope `read:guardian_factors` is needed.

`Request<List<Factor>> listFactors()`

Example:
```java
Request<List<Factor>> request = mgmt.guardian().listFactors();
try {
    List<Factor> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Update Factor

Creates a new request to update a Factor. An API Token with scope `update:guardian_factors` is required.

`Request<Factor> updateFactor("{NAME}", {ENABLED})`

Example:
```java
Request<Factor> request = mgmt.guardian().updateFactor("sms", true);
try {
    Factor response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Get Twilio Factor Provider

Creates a new request to get the Twilio Factor Provider. An API Token with scope `read:guardian_factors` is needed.

`Request<TwilioFactorProvider> getTwilioFactorProvider()`

Example:
```java
Request<TwilioFactorProvider> request = mgmt.guardian().getTwilioFactorProvider();
try {
    TwilioFactorProvider response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Update Twilio Factor Provider

Creates a new request to update the Twilio Factor Provider. An API Token with scope `update:guardian_factors` is needed.

`Request<TwilioFactorProvider> updateTwilioFactorProvider()`

Example:
```java
TwilioFactorProvider data = new TwilioFactorProvider(...);
Request<TwilioFactorProvider> request = mgmt.guardian().updateTwilioFactorProvider(data);
try {
    TwilioFactorProvider response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Get Sns Factor Provider

Creates a new request to get the Sns Factor Provider. An API Token with scope `read:guardian_factors` is needed.

`Request<SnsFactorProvider> getSnsFactorProvider()`

Example:
```java
Request<SnsFactorProvider> request = mgmt.guardian().getSnsFactorProvider();
try {
    SnsFactorProvider response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Update Sns Factor Provider

Creates a new request to update the Sns Factor Provider. An API Token with scope `update:guardian_factors` is needed.

`Request<SnsFactorProvider> updateSnsFactorProvider()`

Example:
```java
SnsFactorProvider data = new SnsFactorProvider(...);
Request<SnsFactorProvider> request = mgmt.guardian().updateSnsFactorProvider(data);
try {
    SnsFactorProvider response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```



### Stats

To access the entity methods call `mgmt.emailProvider()`.

#### Get Active Users Count

Creates a new request to get the Active Users Count (logged in during the last 30 days). An API Token with scope `read:stats` is required.

`Request<Integer> getActiveUsersCount()`

Example:
```java
Request<Integer> request = mgmt.stats().getActiveUsersCount();
try {
    Integer response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Get Daily Stats

Creates a new request to get the Active Users Count (logged in during the last 30 days). An API Token with scope `read:stats` is required.

`Request<List<DailyStats>> getDailyStats("{DATE_FROM}", "{DATE_TO}")`

Example:
```java
Request<List<DailyStats>> request = mgmt.stats().getDailyStats("20170110", "20170210");
try {
    List<DailyStats> response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```



### Tenant Settings

To access the entity methods call `mgmt.tenants()`.

#### Get

Creates a new request to get the Tenant settings. An API Token with scope `read:tenant_settings` is needed.
You can pass an optional Filter to narrow the results in the response.

`Request<Tenant> get({FILTER})`

Example:
```java
FieldsFilter filter = new FieldsFilter(...);
Request<Tenant> request = mgmt.tenants().get(filter);
try {
    Tenant response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Update

Creates a new request to update the Tenant settings. An API Token with scope `update:tenant_settings` is needed.

`Request<Tenant> update()`

Example:
```java
Tenant data = new Tenant(...);
Request<Tenant> request = mgmt.tenants().update(data);
try {
    Tenant response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

### Tickets

To access the entity methods call `mgmt.tickets()`.

#### Create Email Verification Ticket

Creates a new request to create a new Email Verification Ticket. An API Token with scope `create:user_tickets` is required.

`Request<EmailVerificationTicket> requestEmailVerification()`

Example:
```java
EmailVerificationTicket data = new EmailVerificationTicket(...);
Request<EmailVerificationTicket> request = mgmt.tickets().requestEmailVerification(data);
try {
    EmailVerificationTicket response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```

#### Create Password Change Ticket

Creates a new request to create a new Password Change Ticket. An API Token with scope `create:user_tickets` is required.

`Request<PasswordChangeTicket> requestPasswordChange()`

Example:
```java
PasswordChangeTicket data = new PasswordChangeTicket(...);
Request<PasswordChangeTicket> request = mgmt.tickets().requestPasswordChange(data);
try {
    PasswordChangeTicket response = request.execute();
} catch (APIException exception) {
    // api error
} catch (Auth0Exception exception) {
    // request error
}
```



## Exceptions

### Auth0Exception
Base checked exception thrown when a Request creation or execution fails.

```java
try {
    request.execute();
} catch(Auth0Exception e) {
    e.getMessage(); // description
}
```

### APIException
Auth0Exception child thrown when the Request was executed fine but the Response was not successful.

```java
try {
    request.execute();
} catch(APIException e) {
    e.getStatusCode(); // http status code
    e.getError(); // api error code
    e.getDescription(); // api error description
}
```

## Documentation

For more information about [auth0](http://auth0.com) check our [documentation page](http://docs.auth0.com/).

## What is Auth0?

Auth0 helps you to:

* Add authentication with [multiple authentication sources](https://docs.auth0.com/identityproviders), either social like **Google, Facebook, Microsoft Account, LinkedIn, GitHub, Twitter, Box, Salesforce, amont others**, or enterprise identity systems like **Windows Azure AD, Google Apps, Active Directory, ADFS or any SAML Identity Provider**.
* Add authentication through more traditional **[username/password databases](https://docs.auth0.com/mysql-connection-tutorial)**.
* Add support for **[linking different user accounts](https://docs.auth0.com/link-accounts)** with the same user.
* Support for generating signed [Json Web Tokens](https://docs.auth0.com/jwt) to call your APIs and **flow the user identity** securely.
* Analytics of how, when and where users are logging in.
* Pull data from other sources and add it to the user profile, through [JavaScript rules](https://docs.auth0.com/rules).

## Create a free Auth0 Account

1. Go to [Auth0](https://auth0.com) and click Sign Up.
2. Use Google, GitHub or Microsoft Account to login.

## Issue Reporting

If you have found a bug or if you have a feature request, please report them at this repository issues section. Please do not report security vulnerabilities on the public GitHub issue tracker. The [Responsible Disclosure Program](https://auth0.com/whitehat) details the procedure for disclosing security issues.

## Author

[Auth0](https://auth0.com)

## License

This project is licensed under the MIT license. See the [LICENSE](LICENSE) file for more info.


<!-- Vars -->

[travis-ci-badge]: https://travis-ci.org/auth0/auth0-java.svg?branch=master
[travis-ci-url]: https://travis-ci.org/auth0/auth0-java
[mit-badge]: http://img.shields.io/:license-mit-blue.svg?style=flat
[mit-url]: https://raw.githubusercontent.com/auth0/auth0-java/master/LICENSE
[maven-badge]: https://img.shields.io/maven-central/v/com.auth0/auth0.svg
[maven-url]: http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.auth0%22%20AND%20a%3A%22auth0%22
[jcenter-badge]: https://api.bintray.com/packages/auth0/lock-android/auth0/images/download.svg
[jcenter-url]: https://bintray.com/auth0/lock-android/auth0/_latestVersion
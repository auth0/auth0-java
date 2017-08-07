# Change Log

## [1.2.0](https://github.com/auth0/auth0-java/tree/1.2.0) (2017-08-07)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.1.0...1.2.0)
**Closed issues**
- Get AD connection's provisioning_ticket_url property [\#66](https://github.com/auth0/auth0-java/issues/66)

**Added**
- Add support for provisioning_ticket_url property to Connection POJO [\#67](https://github.com/auth0/auth0-java/pull/67) ([unnamed38](https://github.com/unnamed38))
- Add public User constructor without parameters [\#59](https://github.com/auth0/auth0-java/pull/59) ([lbalmaceda](https://github.com/lbalmaceda))

**Removed**
- Remove invalid Logout URL parameter [\#65](https://github.com/auth0/auth0-java/pull/65) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.1.0](https://github.com/auth0/auth0-java/tree/1.1.0) (2017-05-23)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.0.0...1.1.0)

**Added**
- Add getter for User and ProfileData extra properties [\#56](https://github.com/auth0/auth0-java/pull/56) ([lbalmaceda](https://github.com/lbalmaceda))
- Add renew authentication endpoint [\#51](https://github.com/auth0/auth0-java/pull/51) ([lbalmaceda](https://github.com/lbalmaceda))
- Add revoke token endpoint [\#50](https://github.com/auth0/auth0-java/pull/50) ([lbalmaceda](https://github.com/lbalmaceda))
- Add getter for Identity extra properties  [\#45](https://github.com/auth0/auth0-java/pull/45) ([lbalmaceda](https://github.com/lbalmaceda))
- Add response_type and custom parameter setter for AuthorizeUrlBuilder [\#40](https://github.com/auth0/auth0-java/pull/40) ([lbalmaceda](https://github.com/lbalmaceda))

**Changed**
- Improve Guardian section [\#39](https://github.com/auth0/auth0-java/pull/39) ([nikolaseu](https://github.com/nikolaseu))
- Simplify/reduce amount of code [\#36](https://github.com/auth0/auth0-java/pull/36) ([nikolaseu](https://github.com/nikolaseu))

**Fixed**
- Fix "q" query parameter encoding [\#55](https://github.com/auth0/auth0-java/pull/55) ([lbalmaceda](https://github.com/lbalmaceda))
- Close the ResponseBody after its parsed [\#38](https://github.com/auth0/auth0-java/pull/38) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.0.0](https://github.com/auth0/auth0-java/tree/1.0.0) (2017-01-30)

Reworked Auth0 SDK for java by providing better support for non-Android application (for Android please use [Auth0.Android](https://github.com/auth0/Auth0.Android)).

The changes from v0 includes:

- OAuth 2.0 endpoints in Authentication API
- Sync calls by default
- Added Management API endpoints
- Better error handling for Auth and Management API erros

### Auth API

The implementation is based on the [Authentication API Docs](https://auth0.com/docs/api/authentication).

Create a new `AuthAPI` instance by providing the client data from the [dashboard](https://manage.auth0.com/#/clients).

```java
AuthAPI auth = new AuthAPI("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}");
```

### Management API

The implementation is based on the [Management API Docs](https://auth0.com/docs/api/management/v2).

Create a new `ManagementAPI` instance by providing the domain from the [client dashboard](https://manage.auth0.com/#/clients) and the API Token. Click [here](https://auth0.com/docs/api/management/v2#!/Introduction/Getting_an_API_token) for more information on how to obtain a valid API Token.

```java
ManagementAPI mgmt = new ManagementAPI("{YOUR_DOMAIN}", "{YOUR_API_TOKEN}");
```

The Management API is divided into different entities. Each of them have the list, create, update, delete and update methods plus a few more if corresponds. The calls are authenticated using the API Token given in the `ManagementAPI` instance creation and must contain the `scope` required by each entity. See the javadoc for details on which `scope` is expected for each call.

* **Client Grants:** See [Docs](https://auth0.com/docs/api/management/v2#!/Client_Grants/get_client_grants). Access the methods by calling `mgmt.clientGrants()`. 
* **Clients:** See [Docs](https://auth0.com/docs/api/management/v2#!/Clients/get_clients). Access the methods by calling `mgmt.clients()`. 
* **Connections:** See [Docs](https://auth0.com/docs/api/management/v2#!/Connections/get_connections). Access the methods by calling `mgmt.connections()`. 
* **Device Credentials:** See [Docs](https://auth0.com/docs/api/management/v2#!/Device_Credentials/get_device_credentials). Access the methods by calling `mgmt.deviceCredentials()`. 
* **Logs:** See [Docs](https://auth0.com/docs/api/management/v2#!/Logs/get_logs). Access the methods by calling `mgmt.logEvents()`. 
* **Rules:** See [Docs](https://auth0.com/docs/api/management/v2#!/Rules/get_rules). Access the methods by calling `mgmt.rules()`. 
* **User Blocks:** See [Docs](https://auth0.com/docs/api/management/v2#!/User_Blocks/get_user_blocks). Access the methods by calling `mgmt.userBlocks()`. 
* **Users:** See [Docs](https://auth0.com/docs/api/management/v2#!/Users/get_users). Access the methods by calling `mgmt.users()`. 
* **Blacklists:** See [Docs](https://auth0.com/docs/api/management/v2#!/Blacklists/get_tokens). Access the methods by calling `mgmt.blacklists()`. 
* **Emails:** See [Docs](https://auth0.com/docs/api/management/v2#!/Emails/get_provider). Access the methods by calling `mgmt.emailProvider()`. 
* **Guardian:** See [Docs](https://auth0.com/docs/api/management/v2#!/Guardian/get_factors). Access the methods by calling `mgmt.guardian()`. 
* **Stats:** See [Docs](https://auth0.com/docs/api/management/v2#!/Stats/get_active_users). Access the methods by calling `mgmt.stats()`. 
* **Tenants:** See [Docs](https://auth0.com/docs/api/management/v2#!/Tenants/get_settings). Access the methods by calling `mgmt.tenants()`. 
* **Tickets:** See [Docs](https://auth0.com/docs/api/management/v2#!/Tickets/post_email_verification). Access the methods by calling `mgmt.tickets()`. 


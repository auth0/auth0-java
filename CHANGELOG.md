# Change Log

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


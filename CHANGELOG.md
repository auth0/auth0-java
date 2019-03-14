# Change Log

## [1.11.0](https://github.com/auth0/auth0-java/tree/1.11.0) (2019-03-14)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.10.0...1.11.0)

**Added**
- Add "mark_email_as_verified" property to PasswordChangeTicket [\#189](https://github.com/auth0/auth0-java/pull/189) ([akvamalin](https://github.com/akvamalin))

## [1.10.0](https://github.com/auth0/auth0-java/tree/1.10.0) (2019-01-03)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.9.1...1.10.0)

**Added**
- Add Serializable to the user data. [\#178](https://github.com/auth0/auth0-java/pull/178) ([dfredell](https://github.com/dfredell))
- Include grant_types in Client [\#166](https://github.com/auth0/auth0-java/pull/166) ([osule](https://github.com/osule))

**Fixed**
- Closing response body on RateLimitException [\#175](https://github.com/auth0/auth0-java/pull/175) ([j-m-x](https://github.com/j-m-x))

**Security**
- Bump jackson-databind to patch security issues. [\#181](https://github.com/auth0/auth0-java/pull/181) ([gkwang](https://github.com/gkwang))

## [1.9.1](https://github.com/auth0/auth0-java/tree/1.9.1) (2018-10-23)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.9.0...1.9.1)

**Security**
- Use jackson-databind 2.9.7 [\#168](https://github.com/auth0/auth0-java/pull/168) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.9.0](https://github.com/auth0/auth0-java/tree/1.9.0) (2018-09-25)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.8.0...1.9.0)

**Added**
- Allow to retrieve Rate Limit headers [\#153](https://github.com/auth0/auth0-java/pull/153) ([rvillablanca](https://github.com/rvillablanca))
- Add web_origins attribute to the Client class [\#148](https://github.com/auth0/auth0-java/pull/148) ([lbalmaceda](https://github.com/lbalmaceda))
- Application (aka Client) description field support [\#147](https://github.com/auth0/auth0-java/pull/147) ([rrybalkin](https://github.com/rrybalkin))

## [1.8.0](https://github.com/auth0/auth0-java/tree/1.8.0) (2018-07-13)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.7.0...1.8.0)

**Added**
- Allow to update the Management API token [\#141](https://github.com/auth0/auth0-java/pull/141) ([lbalmaceda](https://github.com/lbalmaceda))
- Allow to set search_engine version for Users API [\#140](https://github.com/auth0/auth0-java/pull/140) ([lbalmaceda](https://github.com/lbalmaceda))
- Make Connections accept include_totals parameter [\#135](https://github.com/auth0/auth0-java/pull/135) ([lbalmaceda](https://github.com/lbalmaceda))
- Add pagination support to Client Grants, Grants, Resource Servers and Rules [\#132](https://github.com/auth0/auth0-java/pull/132) ([lbalmaceda](https://github.com/lbalmaceda))

**Deprecated**
- Deprecate old list methods that do not support pagination [\#136](https://github.com/auth0/auth0-java/pull/136) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.7.0](https://github.com/auth0/auth0-java/tree/1.7.0) (2018-06-11)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.6.0...1.7.0)

**Added**
- Support pagination on the Clients entity [\#124](https://github.com/auth0/auth0-java/pull/124) ([lbalmaceda](https://github.com/lbalmaceda))
- Add Resend verification email functionality [\#120](https://github.com/auth0/auth0-java/pull/120) ([minhlongdo](https://github.com/minhlongdo))

**Deprecated**
- Deprecate ClientsEntity#list() method [\#128](https://github.com/auth0/auth0-java/pull/128) ([lbalmaceda](https://github.com/lbalmaceda))

**Security**
- Security fix and dependencies update [\#129](https://github.com/auth0/auth0-java/pull/129) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.6.0](https://github.com/auth0/auth0-java/tree/1.6.0) (2018-06-04)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.5.1...1.6.0)

**Added**
- Expose additional error response properties in the Exception [\#123](https://github.com/auth0/auth0-java/pull/123) ([lbalmaceda](https://github.com/lbalmaceda))
- Add email-templates endpoints [\#117](https://github.com/auth0/auth0-java/pull/117) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.5.1](https://github.com/auth0/auth0-java/tree/1.5.1) (2018-03-01)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.5.0...1.5.1)

**Fixed**
- Support password policy error response [\#108](https://github.com/auth0/auth0-java/pull/108) ([lbalmaceda](https://github.com/lbalmaceda))
- Close ResponseBody buffer after read [\#101](https://github.com/auth0/auth0-java/pull/101) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.5.0](https://github.com/auth0/auth0-java/tree/1.5.0) (2017-12-07)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.4.0...1.5.0)

**Added**
- Resource server [\#77](https://github.com/auth0/auth0-java/pull/77) ([mfarsikov](https://github.com/mfarsikov))

## [1.4.0](https://github.com/auth0/auth0-java/tree/1.4.0) (2017-11-30)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.3.1...1.4.0)

**Added**
- Add user_id setter [\#93](https://github.com/auth0/auth0-java/pull/93) ([lbalmaceda](https://github.com/lbalmaceda))
- Add /v2/users-by-email endpoint [\#87](https://github.com/auth0/auth0-java/pull/87) ([lbalmaceda](https://github.com/lbalmaceda))

**Breaking changes**
- Include a proper SignUp response [\#92](https://github.com/auth0/auth0-java/pull/92) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.3.1](https://github.com/auth0/auth0-java/tree/1.3.1) (2017-11-01)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.3.0...1.3.1)
**Closed issues**
- NullPointerException, StdDeserializer [\#78](https://github.com/auth0/auth0-java/issues/78)

**Fixed**
- Upgrade Jackson-databind dependency [\#82](https://github.com/auth0/auth0-java/pull/82) ([LuisSaybe](https://github.com/LuisSaybe))

## [1.3.0](https://github.com/auth0/auth0-java/tree/1.3.0) (2017-09-08)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.2.0...1.3.0)

**Added**
- implemented /api/v2/grants endpoint of auth0 management api [\#74](https://github.com/auth0/auth0-java/pull/74) ([neshanjo](https://github.com/neshanjo))

**Changed**
- removed unmotivated throwing of UnsupportedEncodingException [\#75](https://github.com/auth0/auth0-java/pull/75) ([neshanjo](https://github.com/neshanjo))

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


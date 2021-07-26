# Change Log

## [1.33.0](https://github.com/auth0/auth0-java/tree/1.33.0) (2021-07-26)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.32.0...1.33.0)

**Added**
- [SDK-2664] Add support for checkpoint pagination [\#362](https://github.com/auth0/auth0-java/pull/362) ([jimmyjames](https://github.com/jimmyjames))

**Deprecated**
- Deprecate unused constructors in Page implementations [\#363](https://github.com/auth0/auth0-java/pull/363) ([jimmyjames](https://github.com/jimmyjames))

## [1.32.0](https://github.com/auth0/auth0-java/tree/1.32.0) (2021-07-05)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.31.0...1.32.0)

**Added**
- [SDK-2622] Add expiresAt to token response [\#357](https://github.com/auth0/auth0-java/pull/357) ([jimmyjames](https://github.com/jimmyjames))

**Changed**
- Update OSS release plugin version [\#358](https://github.com/auth0/auth0-java/pull/358) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.31.0](https://github.com/auth0/auth0-java/tree/1.31.0) (2021-05-10)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.30.0...1.31.0)

**Added**
- [SDK-2550] Add networking client timeout configuration [\#354](https://github.com/auth0/auth0-java/pull/354) ([jimmyjames](https://github.com/jimmyjames))

## [1.30.0](https://github.com/auth0/auth0-java/tree/1.30.0) (2021-04-28)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.29.0...1.30.0)

**Added**
- [SDK-2537] Add missing parameters to Ticket entities [\#352](https://github.com/auth0/auth0-java/pull/352) ([jimmyjames](https://github.com/jimmyjames))

## [1.29.0](https://github.com/auth0/auth0-java/tree/1.29.0) (2021-04-09)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.28.1...1.29.0)

**Added**
- Adds method to get an organization's enabled connection and update JavaDocs [\#344](https://github.com/auth0/auth0-java/pull/344) ([jimmyjames](https://github.com/jimmyjames))
- Add Organizations Management API Support [SDK-2437] [\#341](https://github.com/auth0/auth0-java/pull/341) ([jimmyjames](https://github.com/jimmyjames))

## [1.28.1](https://github.com/auth0/auth0-java/tree/1.28.1) (2021-04-05)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.28.0...1.28.1)

**Fixed**
- Fix `NoSuchMethodError` when projects have OkHttp 3 dependency [\#342](https://github.com/auth0/auth0-java/pull/342) ([jimmyjames](https://github.com/jimmyjames))

## [1.28.0](https://github.com/auth0/auth0-java/tree/1.28.0) (2021-03-25)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.27.0...1.28.0)

**Added**
- Add support for organizations feature [\#338](https://github.com/auth0/auth0-java/pull/338) ([jimmyjames](https://github.com/jimmyjames))

**Changed**
- Update Gradle OSS Plugin [\#339](https://github.com/auth0/auth0-java/pull/339) ([jimmyjames](https://github.com/jimmyjames))

## [1.27.0](https://github.com/auth0/auth0-java/tree/1.27.0) (2021-02-05)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.26.0...1.27.0)

**Added**
- Add support for async requests [\#328](https://github.com/auth0/auth0-java/pull/328) ([jimmyjames](https://github.com/jimmyjames))

**Changed**
- Update dependency versions [\#329](https://github.com/auth0/auth0-java/pull/329) ([jimmyjames](https://github.com/jimmyjames))

## [1.26.0](https://github.com/auth0/auth0-java/tree/1.26.0) (2020-12-02)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.25.0...1.26.0)

**Added**
- Add refresh token configuration object to client [\#321](https://github.com/auth0/auth0-java/pull/321) ([JakeUskoski](https://github.com/JakeUskoski))

**Fixed**
- Fix OkHttp dependency version [\#325](https://github.com/auth0/auth0-java/pull/325) ([jimmyjames](https://github.com/jimmyjames))
- Tidy up code [\#317](https://github.com/auth0/auth0-java/pull/317) ([jsalinaspolo](https://github.com/jsalinaspolo))

## [1.25.0](https://github.com/auth0/auth0-java/tree/1.25.0) (2020-11-16)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.24.0...1.25.0)

**Changed**
- Update to OkHttp 4 [\#319](https://github.com/auth0/auth0-java/pull/319) ([jimmyjames](https://github.com/jimmyjames))
- Use java8 [\#316](https://github.com/auth0/auth0-java/pull/316) ([jsalinaspolo](https://github.com/jsalinaspolo))

## [1.24.0](https://github.com/auth0/auth0-java/tree/1.24.0) (2020-11-02)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.23.0...1.24.0)

**Added**
- Add methods to determine cause of APIException [\#310](https://github.com/auth0/auth0-java/pull/310) ([jimmyjames](https://github.com/jimmyjames))

**Breaking Changes**
- Return TokenRequest instead of AuthRequest [\#309](https://github.com/auth0/auth0-java/pull/309) ([jimmyjames](https://github.com/jimmyjames))

> Note: We aim to not introduce breaking changes within a release stream. We have taken steps, including running API compatibility checks, to prevent introducing breaking changes in the future.

**Fixed**
- Add unchecked warnings failures for src [\#311](https://github.com/auth0/auth0-java/pull/311) ([jimmyjames](https://github.com/jimmyjames))

## [1.23.0](https://github.com/auth0/auth0-java/tree/1.23.0) (2020-10-22)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.22.1...1.23.0)

**Added**
- Add support for MFA OTP exchange [\#303](https://github.com/auth0/auth0-java/pull/303) ([jimmyjames](https://github.com/jimmyjames))
- Passwordless support [\#300](https://github.com/auth0/auth0-java/pull/300) ([jimmyjames](https://github.com/jimmyjames))
- Add scope to TokenHolder [\#297](https://github.com/auth0/auth0-java/pull/297) ([jimmyjames](https://github.com/jimmyjames))
- Add inicludeEmailInRedirect field for create email verification ticket endpoint [\#296](https://github.com/auth0/auth0-java/pull/296) ([jimmyjames](https://github.com/jimmyjames))

**Changed**
- Update dependencies [\#299](https://github.com/auth0/auth0-java/pull/299) ([jimmyjames](https://github.com/jimmyjames))

## [1.22.1](https://github.com/auth0/auth0-java/tree/1.22.1) (2020-10-13)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.22.0...1.22.1)

**Fixed**
- Use Standard Jackson Date serialization [\#295](https://github.com/auth0/auth0-java/pull/295) ([jimmyjames](https://github.com/jimmyjames))

## [1.22.0](https://github.com/auth0/auth0-java/tree/1.22.0) (2020-09-28)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.21.0...1.22.0)

**Added**
- Add support for identity field for email verification jobs and tickets [\#293](https://github.com/auth0/auth0-java/pull/293) ([jimmyjames](https://github.com/jimmyjames))

## [1.21.0](https://github.com/auth0/auth0-java/tree/1.21.0) (2020-08-27)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.20.0...1.21.0)

**Added**
- Add support for Log Streams [\#284](https://github.com/auth0/auth0-java/pull/284) ([jimmyjames](https://github.com/jimmyjames))

## [1.20.0](https://github.com/auth0/auth0-java/tree/1.20.0) (2020-07-27)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.19.0...1.20.0)

**Added**
- Add missing Linking User Accounts endpoint [\#270](https://github.com/auth0/auth0-java/pull/270) ([cschwalm](https://github.com/cschwalm))

**Fixed**
- Fix and document thread-safety [\#272](https://github.com/auth0/auth0-java/pull/272) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.19.0](https://github.com/auth0/auth0-java/tree/1.19.0) (2020-06-05)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.18.0...1.19.0)

**Added**
- Add display_name property to Connection [\#267](https://github.com/auth0/auth0-java/pull/267) ([lbalmaceda](https://github.com/lbalmaceda))
- Add support for java Proxy with basic auth [\#266](https://github.com/auth0/auth0-java/pull/266) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.18.0](https://github.com/auth0/auth0-java/tree/1.18.0) (2020-05-29)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.17.0...1.18.0)

**Added**
- Add ID Token verifier API [\#260](https://github.com/auth0/auth0-java/pull/260) ([jimmyjames](https://github.com/jimmyjames))
- Add signature verification classes [\#258](https://github.com/auth0/auth0-java/pull/258) ([jimmyjames](https://github.com/jimmyjames))

**Changed**
- Update to OkHttp 3.14.9 [\#262](https://github.com/auth0/auth0-java/pull/262) ([jimmyjames](https://github.com/jimmyjames))

## [1.17.0](https://github.com/auth0/auth0-java/tree/1.17.0) (2020-05-22)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.16.0...1.17.0)

**Added**
- Add missing property last_password_reset [\#256](https://github.com/auth0/auth0-java/pull/256) ([mario-moura-silva](https://github.com/mario-moura-silva))
- Add missing "sources" property to the Permission [\#254](https://github.com/auth0/auth0-java/pull/254) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.16.0](https://github.com/auth0/auth0-java/tree/1.16.0) (2020-04-24)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.15.0...1.16.0)

**Added**
- Add Job "POST User Imports" endpoint [\#249](https://github.com/auth0/auth0-java/pull/249) ([lbalmaceda](https://github.com/lbalmaceda))
- Add a new class to handle Multipart requests [\#248](https://github.com/auth0/auth0-java/pull/248) ([lbalmaceda](https://github.com/lbalmaceda))
- Add Job "GET Status" endpoint [\#247](https://github.com/auth0/auth0-java/pull/247) ([lbalmaceda](https://github.com/lbalmaceda))
- Add missing "initiate_login_uri" property to Client [\#244](https://github.com/auth0/auth0-java/pull/244) ([lbalmaceda](https://github.com/lbalmaceda))
- Use char array for passwords [\#242](https://github.com/auth0/auth0-java/pull/242) ([jimmyjames](https://github.com/jimmyjames))
- Add Job "POST User Exports" endpoint [\#241](https://github.com/auth0/auth0-java/pull/241) ([lbalmaceda](https://github.com/lbalmaceda))
- Add Rules Configs entity [\#240](https://github.com/auth0/auth0-java/pull/240) ([lbalmaceda](https://github.com/lbalmaceda))

**Deprecated**
- deprecated string password APIs [\#243](https://github.com/auth0/auth0-java/pull/243) ([jimmyjames](https://github.com/jimmyjames))

**Fixed**
- Make CreatedUser take the id from different properties [\#245](https://github.com/auth0/auth0-java/pull/245) ([lbalmaceda](https://github.com/lbalmaceda))

**Security**
- Use char array for passwords [\#242](https://github.com/auth0/auth0-java/pull/242) ([jimmyjames](https://github.com/jimmyjames))

## [1.15.0](https://github.com/auth0/auth0-java/tree/1.15.0) (2019-11-12)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.14.3...1.15.0)

**Changed**
- Allow to set the client is_first_party property [\#230](https://github.com/auth0/auth0-java/pull/230) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.14.3](https://github.com/auth0/auth0-java/tree/1.14.3) (2019-09-25)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.14.2...1.14.3)

**Security**
- Update jackson-databind to address CVE [\#225](https://github.com/auth0/auth0-java/pull/225) ([jimmyjames](https://github.com/jimmyjames))

## [1.14.2](https://github.com/auth0/auth0-java/tree/1.14.2) (2019-08-15)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.14.1...1.14.2)

**Fixed**
- Find the right JDK version for Telemetry [\#220](https://github.com/auth0/auth0-java/pull/220) ([lbalmaceda](https://github.com/lbalmaceda))

**Security**
- Update dependencies [\#222](https://github.com/auth0/auth0-java/pull/222) ([jimmyjames](https://github.com/jimmyjames))

## [1.14.1](https://github.com/auth0/auth0-java/tree/1.14.1) (2019-07-03)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.14.0...1.14.1)

**Security**
- Update jackson-databind dependency to fix security vulnerability [\#217](https://github.com/auth0/auth0-java/pull/217) ([jimmyjames](https://github.com/jimmyjames))

## [1.14.0](https://github.com/auth0/auth0-java/tree/1.14.0) (2019-07-02)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.13.3...1.14.0)

**Added**
- Add getter for DeviceCredentials client_id [\#215](https://github.com/auth0/auth0-java/pull/215) ([lbalmaceda](https://github.com/lbalmaceda))
- Add missing ResourceServer properties [\#214](https://github.com/auth0/auth0-java/pull/214) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.13.3](https://github.com/auth0/auth0-java/tree/1.13.3) (2019-05-22)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.13.2...1.13.3)

**Fixed**
- Add missing metadata field to Connection object [\#206](https://github.com/auth0/auth0-java/pull/206) ([AlisonT1230](https://github.com/AlisonT1230))

**Security**
- Bump dependencies and solve security issues [\#209](https://github.com/auth0/auth0-java/pull/209) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.13.2](https://github.com/auth0/auth0-java/tree/1.13.2) (2019-05-01)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.13.1...1.13.2)

**Fixed**
- Dynamically obtain the project version for telemetry [\#201](https://github.com/auth0/auth0-java/pull/201) ([lbalmaceda](https://github.com/lbalmaceda))

## [1.13.1](https://github.com/auth0/auth0-java/tree/1.13.1) (2019-04-25)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.13.0...1.13.1)

**Fixed**
- Fix for issue #198: Core Authorization fails when adding Roles to User [\#199](https://github.com/auth0/auth0-java/pull/199) ([jakbutler](https://github.com/jakbutler))

## [1.13.0](https://github.com/auth0/auth0-java/tree/1.13.0) (2019-04-23)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.12.0...1.13.0)

**Added**
- Add support for Core Authorization's User Roles and Permissions [\#193](https://github.com/auth0/auth0-java/pull/193) ([jakbutler](https://github.com/jakbutler))

## [1.12.0](https://github.com/auth0/auth0-java/tree/1.12.0) (2019-04-17)
[Full Changelog](https://github.com/auth0/auth0-java/compare/1.11.0...1.12.0)

**Added**
- Update Telemetry format and allow to customize it [\#195](https://github.com/auth0/auth0-java/pull/195) ([lbalmaceda](https://github.com/lbalmaceda))

**Changed**
- Remove default version for search_engine [\#194](https://github.com/auth0/auth0-java/pull/194) ([lbalmaceda](https://github.com/lbalmaceda))

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


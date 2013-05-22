# Filtre

Filtre is a configurable IP address filter for J2EE Web applications.

## Usage

By convention, Filtre will attempt to load its configuration from
`filtre.properties` stored on the root of the classpath:

```
allowList=127.0.0.1,127.0.0.3
denyList=127.0.0.2
```

Filtre can also be configured entirely within `web.xml`. This is useful
when multiple instances are needed to protect different URL patterns
with different allow and deny lists. Optional init parameters are
`allowList`, `denyList`, and `configLocation`:

```xml
<filter>
    <filter-name>filtre</filter-name>
    <filter-class>com.earldouglas.filtre.Filtre</filter-class>
    <init-param>
        <param-name>allowList</param-name>
        <param-value>127.0.0.1,127.0.0.3</param-value>
   </init-param>
</filter>

<filter-mapping>
    <filter-name>filtre</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

Filtre is configured through three init-param elements:

* `allowList`: a comma-separated list of IP addresses to be allowed
  access
* `denyList`: a comma-separated list of IP addresses to be denied
  access
* `configLocation`: the path of a properties file containing the
  `allowList` or `denyList` properties

## Address format

Currently supported are IPv4 addresses in CIDR notation:

```
127.0.0.1
127.0.0.1/0
127.0.0.1/31
127.0.0.1/32
```

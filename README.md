# Indigo Framework - Caching Provider

This is the open-source a library like SpringCache, easy to usage, dedicated to optimization your legacy codes.

Version: 1.0.0
License: MIT

## Install

Maven dependency

```maven
<dependency>
 <groupId>com.indigo</groupId>
 <artifactId>cachingprovider</artifactId>
 <version>1.0-SNAPSHOT</version>
</dependency>
```

## About

### Provider

Data are stored in the regions. Each cache region has its own data provider that is described by the following interface:

```java
void set (String key, T value);

T get (String key, Class<T> clazz);

boolean contains (String key);
```
The library offers two implementations of provider. Data can be stored in project ram memory (ConcurrentHashMap) or
in Redis (in memory database). For custom implementation, we can implement provider interface and define custom region.

## Setup

Before usage, we must define our cache regions. We have a two way:

### RegionManagerFactory
```java
RegionManager getManager (String regionName, @NonNull CacheType cacheType, RedisConfig redisConfig, int expirationTime, boolean autoUpdate);
```

```properties
a=3
```

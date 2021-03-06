# Bitlet Synchronizer

[![CI Status](http://img.shields.io/travis/crescentflare/BitletSynchronizer.svg?style=flat)](https://travis-ci.org/crescentflare/BitletSynchronizer)
[![License](https://img.shields.io/cocoapods/l/BitletSynchronizer.svg?style=flat)](http://cocoapods.org/pods/BitletSynchronizer)
[![Version](https://img.shields.io/cocoapods/v/BitletSynchronizer.svg?style=flat)](http://cocoapods.org/pods/BitletSynchronizer)
[![Version](https://img.shields.io/bintray/v/crescentflare/maven/BitletSynchronizerLib.svg?style=flat)](https://bintray.com/crescentflare/maven/BitletSynchronizerLib)

Bitlet Synchronizer is a project to improve handling of data in iOS and Android applications. The goal is to provide a simple way to fetch data from one or multiple sources without having to program things manually. Later on, it will also able modify and push the data back, as long as there is an API supporting it.

The library has been designed to be generic, and not depend on any existing library. The examples will show how to use it together with AlamoFire (iOS) or Retrofit (Android).


### Features

* Provides a Bitlet protocol (iOS) or interface (Android) to integrate the library with your domain layer (data models)
* A synchronizer singleton to easily start the bitlet loading process
* Supports data caching in memory with cache expire times that can be specified per bitlet
* When loading the same resource multiple times, the synchronizer will combine them and makes only 1 actual load call


### iOS integration guide

The library is available through [CocoaPods](http://cocoapods.org). To install it, simply add the following line to your Podfile:

```ruby
pod "BitletSynchronizer", '~> 0.2.0'
```


### Android integration guide

When using gradle, the library can easily be imported into the build.gradle file of your project. Add the following dependency:

```
compile 'com.crescentflare.bitletsynchronizer:BitletSynchronizerLib:0.2.0'
```

Make sure that jcenter is added as a repository.


### Example

The provided example will show how to integrate the library with a popular networking library and a domain layer featuring models. It will demonstrate fetching, serializing and caching data (including error handling).


### Status

The library is new and provides a limited amount of functionality, but can already be useful. New features may be added in the future.

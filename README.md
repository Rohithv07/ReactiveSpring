# ReactiveSpring
Reactive Programming in Spring

## Why Reactive Programming

<table style="width:100%">
	<tr>
		<th>Past 15 years</th>
		<th>Where we are now</th>
	</tr>
	<tr>
		<td> Monolith Applications </td>
		<td> Microservices </td>
	</tr>
	<tr>
		<td> Run in App server </td>
		<td> Run in cloud </td>
	</tr>
	<tr>
		<td> Doesn't hold distributed systems </td>
		<td> Holds distributed systems </td>
	</tr>
</table>

### How our app needs to be : 

- Scale based on the load

- Efficient use of resources

- Response time must be faster

The model what we have is Thread per request where for each request there can be a thread for that request.

### Handling Concurrent Requests

* Managed by **server.tomcat.max-threads** property

* Default value is 200 connections

* We can use application.properties or application.yml file for overriding the value

* Each thread will takes some memory

* Common stack size is 1 MB

* Higher the thread pool size, higher the consumption

* With less memory available, our application will work poorely.

### How it is handled today

* Load is balanced using horizontal scaling and can be achieved by some container orchestration

* But we need to create several instances and it will increase the cost for the organisation

### Traditional Rest APIs 

* Works perfectly for many use cases

* Many API will be design in the future also with the same design

* This is not out of date

* Also there is no necessity in moving traditional Rest API to a new model as it have own advantages 

### How Tradition Rest API work

```java
// simple rest api end point
@GetMapping("/getCatalog")
	public ResponseMessage getCatalog() {
		return movieFeignClient.listAllMovies();
	}
```

* Imperative style API
	* Top-down approach

* Blocking and synchronous

* Blocking means, when the user gives a request to the server and the data has to be fetched from the database, a thread takes care of the request and made the call to the database. Till the response gets received, the thread will be doing nothing and its in a blocking state which is inefficient.

* Synchronous means, the client needs to wait for an API call to return before code execution can continue which may lead to poor performance or latency related issues.

* So what can we do
    * Need to make calls asynchronously, non blocking
    * We can have callbacks and futures

#### Callbacks :

* Complex

* No return value

* Code is hard to read and maintain

* Leads to **Callback** hell

#### Future :

* Another alternative to write asynchronous code in java

* Return Future instance

* Hard to compose multiple async operations

#### Completable Future :

* Supports functional style APIs

* Easy to compose async operations

* Not a great fit asynchronous call with multiple items

Tradition Rest API does not have any backpressure feature which tells the database, suppose, if one of our request call is responding with a huge amount of data which is not needed for the client, then we can ask the database to slow down the process like giving a backpressure.

#### In general

* We need to design API which is async and non blocking

* Move away from Thread per request model

* Use fewer threads

* Back pressure compatible


## Reactive Programming

* Asynchronous and non blocking

* Data will flow as **Event/Message Driven** stream

* Functional style code

* Back pressure on data stream

* Not to wait and block 

### Data flow as Event Driven stream

* One event or message for every result from data source(DB, external services etc)

* One event or message for completion or error
	* OnNext(item) -> Data stream events
	* OnComplete() -> Completion/Success event
	* OnError() -> Error event

#### Functional Style Code

* Easy to work with lambdas

* Similar to Streams API

#### Reactive Steam Specification

* Specification or rules for reactive stream

* Publisher

* Subscriber

* Subscritpion

* Processor

##### Publisher

* Represents the Data Source

```java
public interface Publisher<T> {
	public void subscribe(Subscriber<? super T> s);
}

```

##### Subscriber

```java
public interface Subscriber<T> {
	public void onSubscribe(Subscription s);
	public void onNext(T t);
	public void onError(Throwable t);
	public void onComplete();
}

```

##### Subscription

```java
public interface Subscription {
	public void request(long n);
	public void cancel();
}

```

#### Publisher/Subscriber Event Flow

* Subscriber invoke `subscribe()` method and passes instance of Subscriber as input to Publisher

* Publisher confirms by sending Subscription

* Subscriber gives a `request(n)` method to get the data

* Publisher produces `onNext(data)` event for the specified `n` times and on successful completion produces `onComplete()` event

* So subscriber provides the number of data which says about the back pressure feature

#### Processor

* Like combination of both subscriber and publisher

```java
public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {

}
```

### Reactive Libraries

* RxJava

* Reactor

* Flow Class - JDK 9


### Project Reactor

#### reactor-core

* Core library of project reactor

* Implementation of reactive streams specification

* flux and mono

* flux represents 0 to n elements

* mono represents 0 to 1 elements

##### Flux

![Flux](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/doc-files/marbles/flux.svg) 

[Image taken from Project Reactor](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html)

```java
// flux

Flux.just("Spring", "Spring Boot", "Reactive Spring Boot")
.map(s -> s.concat("flux"))
.subscribe(System.out::println);

```

##### Mono

![Mono](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/doc-files/marbles/mono.svg)

[Image taken from Project Reactor](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html)

```java
// mono

Mono.just("Spring")
.map(s -> s.concat("flux"))
.subscribe(System.out::println);

```
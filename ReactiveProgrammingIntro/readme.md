# Revisiting Reactive Programming

## Reactive Programming Performance

- Reactive does *NOT* equal fast.

- A typical CRUD type application will not see much, if any performance improvement.

- Reactive can imporve computing efficiency.

	* Best used for streaming type application.

- Immutable nature of Reactive applications can help with application quality.

![Reactive Manifesto](https://www.reactivemanifesto.org/images/reactive-traits.svg)

Note: Taken from [Reactive Manifesto](https://www.reactivemanifesto.org/)

### Responsive

- System responds in timely manner.

- Cornerstone of usablity and utility.

- Also means problems may be detected quickly and dealt with effectively.

- provied rapid and consistent response times.

- Consistent behavior simplifies error handling, builds end user confidence and encourages further interaction.

### Resilient

- System stays responsive in face of failure.

- Achieved by replication, containment, isolation and delegation.

- Failures are contained within each component.

- Parts of system can fail, without compromising system as a whole.

- Recovery of each component is delegated to another.

- High availablity is ensured by replication where it is necessary

### Elastic

- System stays responsive under varying workload.

- React to changes in the input rate by increasing or decreasing resources allocated to service inputs.

- Achieve elasticity in a cost effective way on commodity hardware and software platforms.

### Message Driven

- Rely on asynchronous message passing to establish boundary b/w components.

- Ensures loose coupling, isolation and location transparency.

- Message passing enables load management, elasticity and flow control.

- Location transparent messaging makes maagement of failures possible.

- Non-blocking communication allows recipients to only consume resources while active, leading to less system overhead.


## Reactive Programming

- Useful implementation technique.

- Focuses on non-blocking, asynchronous execution which is a key characterestics of reactive systems.

- *Reactive Manifesto* has a wholistic view of the system while *Reactive Programming* is more granular at the program level.

- Reactive programming is an asynchronous programming focused on streams of dtaa.

- Reactive programs also maintain a continuos interaction with their environment, but at a speed which is determined by the environment not the program itself. Interactive programs work at their own pace and mostly deal with communication, while reactive programs only work in response to external demands and mostly deal with accurate interrupt handling. (Gerard Berry)

### Use Cases 

- External Service calls.

- Highly concurrent message consumers.

- Spreadsheets

- Abstraction over asynchronous processing.

- Abstract whether or not your program is syn or asyn.

### Features

- Data Streams.

- Async.

- Non blocking.

- Backpressure.

- Failures as messages.

#### Asynchronous

- Events are captured asynchronously.

- A function is defined to execute when an event is emitted.

- Another function is defined if an error is emitted.

- Another function is defined when complete is emitted.

### #GOF Pattern

![GOF Pattern](https://upload.wikimedia.org/wikipedia/commons/thumb/a/a8/Observer_w_update.svg/500px-Observer_w_update.svg.png)

*Note*: This image is taken from wikipedia

#### Non blocking

- In blocking, the code will stop and wait for more data.

- In non blocking, the code will process the available data, ask to be notified when more is available and then continue.

#### Backpressure

- ability of subscriber to throttle data.(slowing down the process)

#### Falires as messages

- Exceptions are not throwin in a traditional sense.

- Would break processing of stream.

- Exception are processed by a handler function.

## Reactive Streams API

- Publisher

- Subscriber

- Subscription

- Processor


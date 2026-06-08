# ThreadPoolTaskExecutor ClassLoader Behavior

This project tests and documents the behavior of `ThreadPoolTaskExecutor` in Spring Boot 3 and Spring Boot 4, specifically around classloader context propagation to worker threads.

The core problem: worker threads in a `ThreadPoolTaskExecutor` do not automatically inherit the correct context classloader from the submitting thread. This matters when tasks perform dynamic class loading (e.g. via `ServiceLoader`), as the wrong classloader causes lookup failures at runtime.

The issue is only happening, when running a spring boot application from an uber-jar. For example: `java -jar app-spring-boot-406-0.0.1-SNAPSHOT.jar`

## Variants

Each variant exposes a `/greeting` endpoint that processes characters. The loading of `JAXBContextFactory` via `ServiceLoader` on a thread pool worker thread is just there to make the class loader issue visible.

### V1 — Baseline (`/greeting`)

Submits tasks directly to the `ThreadPoolTaskExecutor` from a regular Spring MVC request thread. The setup is fragile.

### V2 — Risky (`/greeting/v2`)

Same thread pool, but tasks are submitted from a `ForkJoinPool` thread via a `parallelStream()`. The ForkJoinPool thread carries a different classloader, which is inherited by the pool worker — causing `ServiceLoader` to fail to find the provider.

### V3 — Decorated Thread Pool (`/greeting/v3`)

Uses a `ThreadPoolTaskExecutor` configured with a `TaskDecorator`. The decorator captures the submitting thread's context classloader at submission time and restores it on the worker thread for the duration of the task.

### V4 — Explicit Classloader Override (`/greeting/v4`)

The task itself explicitly sets the context classloader to the classloader of `JAXBContextFactory` before performing the dynamic class loading, then restores the original classloader afterward. Fixes the problem without relying on a decorated pool.


# How to force the fail

1. Start the app from jar file (running it from IDE is not using the problematic Jar class loader)
```bash
./gradlew assemble
java -jar build/libs/app-spring-boot-406-0.0.1-SNAPSHOT.jar
```
2. Call the `v2` endpoint
3. Any consecutive calls to the `v1` endpoint are now also failing.
4. Calls to `v3` and `v4` are always successful

Side note: The request response is always success, because the errors are happining async to the http request

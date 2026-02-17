STORY.md — My Development Journey


Challenge 1 — Setting Up Spring Security

# The Problem
Spring Security is one of those things that looks simple on the surface but has a lot of moving parts. I needed to:
- Create an in-memory user (`librarian` / `password`)
- Make GET endpoints public
- Lock down POST, PUT, and DELETE behind authentication
- Return proper 401 responses for unauthenticated requests


# How I Resolved It
I switched to the modern approach — defining `SecurityFilterChain` and `UserDetailsService` as `@Bean` methods instead. The key decisions were:

- BCrypt for password encoding — even for an in-memory user, storing plain-text passwords is bad practice. BCrypt scrambles the password so it's never stored in readable form.
- Disabling CSRF — REST APIs using Basic Auth don't need CSRF protection because there's no browser session cookie for an attacker to exploit. Leaving it enabled would have broken POST requests from tools like Postman and cURL.
- Method-level authorization — using `requestMatchers(HttpMethod.GET, "/api/**").permitAll()` cleanly separates read access from write access without duplicating rules for every endpoint.


# Challenge 2 — Switching Between H2 and PostgreSQL

# The Problem
I initially planned to use Elephant SQL but quickly realized it that the server was down. I decided to switch to H2 in-memory, but the configuration between the two databases is different enough that it wasn't just a one-line swap.

The things that changed:
- The JDBC URL format is completely different
- The Hibernate dialect needed to change (`H2Dialect` vs `PostgreSQLDialect`)
- `ddl-auto` behaviour differs — `create-drop` makes sense for H2 (wipe on shutdown), `update` makes more sense for Postgres (preserve data)
- H2 ships with a built-in browser console that needs extra security config to work (iframe permissions, CSRF exceptions)
- The `pom.xml` dependency needed to swap from `org.postgresql` to `com.h2database`

# How I Resolved It
I treated them as two separate configurations and swapped everything systematically rather than changing things one at a time. The H2 console also needed two extra lines in `SecurityConfig.java` that Postgres didn't need — `frameOptions().sameOrigin()` to allow the iframe to render, and a `permitAll()` rule for `/h2-console/**`.

The lesson: database switches aren't just a URL change. Every layer — the driver, the dialect, the DDL strategy, and the security config — needs to be consistent.


# Challenge 3 — The `findByISBN` Case Mismatch Bug

# The Problem
This one caused a startup crash that looked intimidating at first:

```
Could not resolve attribute 'ISBN' of 'com.management.library.data.models.Book'
```

The app wouldn't even start. The error was deep in a stack trace that initially pointed at the controller, then the service, then the repository — it took a moment to realize the actual root cause was in the repository method name.

Spring Data JPA generates SQL queries by reading repository method names as plain English. `findByISBN` tells it to look for a field called `ISBN` on the `Book` entity. But the actual field in the entity was named `isbn` (lowercase camelCase). Spring Data is case-sensitive when matching field names, so it genuinely couldn't find a field called `ISBN`.

# How I Resolved It
Renamed the repository method from:
```java
Optional<Book> findByISBN(String ISBN);
```
to:
```java
Optional<Book> findByIsbn(String isbn);
```

And updated the corresponding call in `BookServiceImpl.java` to match. The fix itself was one word — the challenge was understanding *why* it failed. Spring Data's query derivation reads method names character by character to build SQL, so the casing has to match the Java field name exactly.

---

# Overall Reflection

The hardest part of this project wasn't writing the code — it was understanding *why* things work the way they do. Spring Boot does a lot of magic automatically, which is convenient until something breaks and you need to debug it.

The three challenges above taught me:
1. Always check which Spring Boot version tutorials are written for.
2. Database migrations touch more than just the connection string
3. Read error messages from the *bottom up* — the root cause is usually the last "Caused by" in the stack trace, not the first line

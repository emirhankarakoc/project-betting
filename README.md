# Sports Prediction API

This Spring Boot app lets users predict match results. An admin creates a round, adds games, closes the round, and enters the real scores. The app checks the predictions and can send email updates.

## What to read

- The account, round, game, and prediction code is in `src/main/java/`.
- Tests are in `src/test/`.
- Example flows are in [testScenarios.md](testScenarios.md).

This is a learning project. It does not take real-money bets.

## Run locally

You need Java, MySQL 8, and your own database settings. Set `DB_PASSWORD`. Email needs your own SMTP account and `MAIL_PASSWORD`.

```bash
./mvnw test
./mvnw spring-boot:run
```


[Short video](https://www.youtube.com/watch?v=CNWLksVEIfY)

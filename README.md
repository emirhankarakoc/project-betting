# Sports Prediction Backend Prototype

A Spring Boot API for creating prediction rounds, adding matches, recording user predictions, closing a round, entering results, and notifying participants by email.

## Workflow
1. An admin creates a round and adds games.
2. Users register and submit predictions for games in the round.
3. The admin closes predictions and records match results.
4. The application calculates outcomes and can send notifications.

The code includes account and scenario tests under `src/test`. It was built as a backend learning project; it does not process real-money wagers.

## Run locally
Install Java and MySQL 8. Configure your own datasource and provide `DB_PASSWORD`. Email delivery requires your own SMTP account and `MAIL_PASSWORD`; you can inspect core round/prediction flows without presenting email delivery as active.

```bash
./mvnw test
./mvnw spring-boot:run
```

See `testScenarios.md` for example flows. An introduction video is at https://www.youtube.com/watch?v=CNWLksVEIfY.

## Status
A prototype, not a production betting service. Do not reuse demonstration users or tokens as authorization controls. Values previously committed to Git history must be rotated if they belonged to active accounts.

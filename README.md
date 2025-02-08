# Landlord Kingdom

## Description

Landlord Kingdom is a system that allows landlords to manage their properties and tenants to manage their leases. System offers three access levels:
- Admin: responsible for managing users and accepting new landlords and their properties
- Owner: adds and manages properties and leases, he is able to review and accept applications from tenants, can generate reports that include information about payments for given property
- Tenant: can apply for a lease, pay rent and review his payment history

System is designed for use by multiple users including:
- Authentication (login and password or Google account)
- Authorization (access levels)
- Accounting (system generates logs of all operations performed by users including transactions status)
- Notifications (system sends emails to users about important events)
- Internationalization (system is available in multiple languages (English, Polish))
- Transactions (ACID) (operations are performed in transactions, so if something goes wrong, system can rollback changes)
- Optimistic locking (system uses versioning to prevent lost updates)

To verify the correctness of the system, we have prepared a set of integration tests that cover the most important functionalities of the system. We have also prepared a set of test data that can be used to verify the correctness of the system.

## How to run
To run the application, you need to have installed:
- [Docker](https://www.docker.com/get-started/)
- [Node.js](https://nodejs.org/en/download)
- [Yarn](https://classic.yarnpkg.com/en/)

To run the application from docker-ssbd02 directory, run the following command:
```bash
docker compose -f dev.docker-compose.yml up -d --build
``` 
and from webapp directory run:
```bash
yarn
yarn dev
```

Application will be available at [http://localhost:3000](http://localhost:3000) and can be used with the following credentials:
- username: admin
- password: password

Emails are sent to smtp4dev server, which is available at [http://localhost:5000](http://localhost:5000)

## How to test
To run the tests, you need to have installed:
- [Docker](https://www.docker.com/get-started/)
- [Java 21](https://jdk.java.net/21/)
- [Maven](https://maven.apache.org/download.cgi)

To run the tests from project directory, run the following command:
```bash
mvn package
mvn verify
```

## Used technologies Backend
- [Java 21](https://jdk.java.net/21/)
- [Spring Framework 6.1.6](https://spring.io/projects/spring-framework)
- [Spring Security 6.2.4](https://spring.io/projects/spring-security)
- [Spring Data JPA 3.2.4](https://spring.io/projects/spring-data-jpa)
- [JUnit 5](https://junit.org/junit5/)
- [Mockito 5.11.0](https://site.mockito.org/)
- [Testcontainers 1.19.7](https://www.testcontainers.org/)
- [Rest Assured 5.4.0](https://rest-assured.io/)
- [PostgreSQL 16.2](https://www.postgresql.org/)

## Used technologies Frontend
- [TypeScript 5.2.2](https://www.typescriptlang.org/)
- [React 18.0.2](https://reactjs.org/)
- [Axios 1.6.8](https://axios-http.com/)
- [Tanstack Query 5.32.0](https://tanstack.com/)
- [Zustand 4.5.2](https://zustand.surge.sh/)
- [Zod 3.22.5](https://zod.dev/)
- [i18next 23.11.2](https://www.i18next.com/)
- [Tailwind 3.4.3](https://tailwindcss.com/)
- [Shadcn/ui](https://ui.shadcn.com/)
- and many more... 

## Used technologies Runtime
- [Docker 27.5.1](https://www.docker.com/)
- [Maven 3.9.6](https://maven.apache.org/)
- [Tomcat 10.1.20](https://tomcat.apache.org/)
- [Vite 5.2.0](https://vitejs.dev/)
- [Node.js 20.13.1](https://nodejs.org/en/)
- [Yarn 1.22.22](https://classic.yarnpkg.com/en/)
- [smtp4dev](https://github.com/rnwood/smtp4dev)
- [nginx 1.21.3](https://www.nginx.com/)

## Authors
- Kacper Kafara
  [<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/LinkedIn_icon.svg/2048px-LinkedIn_icon.svg.png" alt="LinkedIn" height=16/>](https://www.linkedin.com/in/kacperkafara/)
  [<img src="https://upload.wikimedia.org/wikipedia/commons/9/91/Octicons-mark-github.svg" alt="GitHub" height=16/>](https://github.com/KacperKafara)
- Konrad Koza
  [<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/LinkedIn_icon.svg/2048px-LinkedIn_icon.svg.png" alt="LinkedIn" height=16/>](https://www.linkedin.com/in/konrad-koza1/)
  [<img src="https://upload.wikimedia.org/wikipedia/commons/9/91/Octicons-mark-github.svg" alt="GitHub" height=16/>](https://github.com/Konradkoza)
- Adam Czerwonka
  [<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/LinkedIn_icon.svg/2048px-LinkedIn_icon.svg.png" alt="LinkedIn" height=16/>](https://www.linkedin.com/in/adamczerwonka/)
  [<img src="https://upload.wikimedia.org/wikipedia/commons/9/91/Octicons-mark-github.svg" alt="GitHub" height=16/>](https://github.com/AdamCzerwonka)
- Marcel Badek
  [<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/LinkedIn_icon.svg/2048px-LinkedIn_icon.svg.png" alt="LinkedIn" height=16/>](https://www.linkedin.com/in/marcel-badek/)
  [<img src="https://upload.wikimedia.org/wikipedia/commons/9/91/Octicons-mark-github.svg" alt="GitHub" height=16/>](https://github.com/marcelbadek)
- Jakub Górski
  [<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/LinkedIn_icon.svg/2048px-LinkedIn_icon.svg.png" alt="LinkedIn" height=16/>](https://www.linkedin.com/in/gorski-jakub/)
  [<img src="https://upload.wikimedia.org/wikipedia/commons/9/91/Octicons-mark-github.svg" alt="GitHub" height=16/>](https://github.com/gorskij)
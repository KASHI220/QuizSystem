QuizSystem

A microservices-based Quiz System built with Java and Spring Boot.

The application allows students to register, log in, take quizzes from different categories, submit answers, retry a failed quiz once, and view their quiz history.
🚀 Features

    Student registration and login
    Password encryption using BCrypt
    Multiple quiz categories
    Random question selection
    Quiz submission and automatic score calculation
    Pass/fail evaluation
    One retry attempt when a student fails
    Quiz attempt history
    Submission timestamp
    REST API communication between microservices
    MongoDB for question data
    PostgreSQL for student and quiz history data

🏗️ Microservices Architecture

The project is divided into independent microservices:

                    ┌─────────────────┐
                    │     Student     │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │   Info Service  │
                    │   Port: 8081    │
                    └─────────────────┘
                             │
                             │
                             ▼
                    ┌─────────────────┐
                    │  Quiz Service   │
                    │   Port: 8083    │
                    └───────┬─────────┘
                            │
                 ┌──────────┴──────────┐
                 │                     │
                 ▼                     ▼
        ┌─────────────────┐   ┌─────────────────┐
        │ Question Service│   │ History Service │
        │   Port: 8082    │   │   Port: 8084    │
        └────────┬────────┘   └────────┬────────┘
                 │                     │
                 ▼                     ▼
          ┌─────────────┐       ┌─────────────┐
          │ MongoDB     │       │ PostgreSQL  │
          │ Atlas       │       │             │
          └─────────────┘       └─────────────┘

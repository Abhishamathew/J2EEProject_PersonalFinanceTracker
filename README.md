# Finance/Budget Management System

## How to run the project
Download the project and open it in your IDE. Make sure you have Java 21 installed.
Update the `application.properties` file with your MySQL database credentials.
Create a database named `project_j2ee` in your MySQL server.
Run the project using your IDE.

## How to test the project
For the frontend, you can access the application at http://localhost:8080/ after running the project.

- Create a new by navigating to the registration page and filling out the form.
- You can log in with the registered user credentials.
- Once logged in, you can create a new budget.
- Then click on the view details button of the budget to view the details of the budget.
- You can add expenses to the budget by clicking on the add new expense button.
- You can also add alerts to the budget by clicking on the add new alert button.
- You can edit the alerts or expenses by clicking on their respective edit buttons.
- You can delete the alerts or expenses by clicking on their respective delete buttons.
- You can go back to the budget list by clicking on the dashboard navigation.
- Notifications will be crated when the total expenses exceed the alert target amount and the expense is before the alert deadline.
- You can view the notifications by clicking on the notification navigation in the top right corner.
- You can delete the notifications by clicking on the delete button of the notification.

For the APIs you can use Postman to test the APIs. 

# Budget Management API Documentation

## Overview
This API provides endpoints for managing budgets, expenses, alerts, notifications, and user accounts in a budget management application.

## Detailed API Documentation
The detailed API documentation is available at (recommended detailed documentation with tested api responses) [Postman Documentation](https://documenter.getpostman.com/view/14091899/2sB2cd5JXA)
or find the swagger documentation after running the application at [Swagger UI](http://localhost:8080/swagger-ui/index.html#/).

## Table of Contents
- [User APIs](#user-apis)
- [Budget APIs](#budget-apis)
- [Expense APIs](#expense-apis)
- [Alert APIs](#alert-apis)
- [Notification APIs](#notification-apis)

## User APIs
APIs for managing users

### Get a user by ID
- **URL**: `/api/users/{id}`
- **Method**: GET
- **Description**: Retrieve a specific user by their ID
- **Path Parameters**:
    - `id` - User ID

### User login
- **URL**: `/api/users/login`
- **Method**: POST
- **Description**: Authenticate a user with username and password
- **Request Parameters**:
    - `userName` - User's username
    - `password` - User's password
- **Responses**:
    - `200 OK` - Login successful (returns user ID as string)
    - `200 OK` - Login failed (returns null)

### Register a new user
- **URL**: `/api/users/register`
- **Method**: POST
- **Description**: Register a new user with the provided details
- **Request Parameters**:
    - `userName` - User's username
    - `password` - User's password
    - `email` - User's email address
    - `phoneNumber` - User's phone number
- **Responses**:
    - `200 OK` - Registration successful (returns "Registered successfully")

## Budget APIs
APIs for managing budgets

### Get all budgets by user ID
- **URL**: `/api/budgets/user/{userId}`
- **Method**: GET
- **Description**: Retrieve all budgets associated with a specific user ID
- **Path Parameters**:
    - `userId` - ID of the user

### Get a budget by ID
- **URL**: `/api/budgets/{budgetId}`
- **Method**: GET
- **Description**: Retrieve a specific budget by its ID
- **Path Parameters**:
    - `budgetId` - ID of the budget

### Create a new budget
- **URL**: `/api/budgets`
- **Method**: POST
- **Description**: Create a new budget for the user with the specified details
- **Request Parameters**:
    - `amount` - Total budget amount
    - `expenditure` - Current expenditure (defaults to 0.0)
    - `startDate` - Budget start date (YYYY-MM-DD format)
    - `endDate` - Budget end date (YYYY-MM-DD format)
    - `userId` - ID of the user who owns the budget

### Update an existing budget
- **URL**: `/api/budgets/{budgetId}`
- **Method**: PUT
- **Description**: Update the details of an existing budget
- **Path Parameters**:
    - `budgetId` - ID of the budget to update
- **Request Parameters**:
    - `amount` - Updated budget amount
    - `expenditure` - Updated expenditure
    - `startDate` - Updated start date (YYYY-MM-DD format)
    - `endDate` - Updated end date (YYYY-MM-DD format)
- **Responses**:
    - `404 Not Found` - If budget doesn't exist

### Delete a budget by ID
- **URL**: `/api/budgets/{budgetId}`
- **Method**: DELETE
- **Description**: Delete a specific budget by its ID
- **Path Parameters**:
    - `budgetId` - ID of the budget to delete
- **Responses**:
    - `204 No Content` - Budget deleted successfully
    - `404 Not Found` - Budget not found

## Expense APIs
APIs for managing the budget expenses

### Get all expenses by budget ID
- **URL**: `/api/expenses/budget/{budgetId}`
- **Method**: GET
- **Description**: Retrieve all expenses associated with a specific budget ID
- **Path Parameters**:
    - `budgetId` - ID of the budget

### Get an expense by ID
- **URL**: `/api/expenses/{expenseId}`
- **Method**: GET
- **Description**: Retrieve a specific expense by its ID
- **Path Parameters**:
    - `expenseId` - ID of the expense

### Create a new expense
- **URL**: `/api/expenses`
- **Method**: POST
- **Description**: Create a new expense with the specified details
- **Request Parameters**:
    - `amount` - Expense amount
    - `category` - Expense category
    - `date` - Expense date (YYYY-MM-DD format)
    - `budgetId` - ID of the budget this expense belongs to
- **Responses**:
    - `201 Created` - Returns the created expense

### Update an existing expense
- **URL**: `/api/expenses/{expenseId}`
- **Method**: PUT
- **Description**: Update the details of an existing expense
- **Path Parameters**:
    - `expenseId` - ID of the expense to update
- **Request Parameters**:
    - `amount` - Updated expense amount
    - `category` - Updated expense category
    - `date` - Updated expense date (YYYY-MM-DD format)
    - `budgetId` - Updated budget ID

### Delete an expense by ID
- **URL**: `/api/expenses/{expenseId}`
- **Method**: DELETE
- **Description**: Delete a specific expense by its ID
- **Path Parameters**:
    - `expenseId` - ID of the expense to delete
- **Responses**:
    - `204 No Content` - Expense deleted successfully
    - `404 Not Found` - Expense not found

## Alert APIs
APIs for managing budget alerts

### Get all alerts by budget ID
- **URL**: `/api/alerts/budget/{budgetId}`
- **Method**: GET
- **Description**: Retrieve all alerts associated with a specific budget ID
- **Path Parameters**:
    - `budgetId` - ID of the budget

### Get an alert by ID
- **URL**: `/api/alerts/{alertId}`
- **Method**: GET
- **Description**: Retrieve a specific alert by its ID
- **Path Parameters**:
    - `alertId` - ID of the alert
- **Responses**:
    - `404 Not Found` - If alert doesn't exist

### Create a new alert
- **URL**: `/api/alerts`
- **Method**: POST
- **Description**: Create a new alert with the specified details
- **Request Parameters**:
    - `description` - Alert description
    - `targetAmount` - Target amount for the alert
    - `currentAmount` - Current amount (defaults to 0.0)
    - `deadline` - Alert deadline date (YYYY-MM-DD format)
    - `budgetId` - ID of the budget this alert belongs to
- **Responses**:
    - `201 Created` - Returns the created alert

### Update an existing alert
- **URL**: `/api/alerts/{alertId}`
- **Method**: PUT
- **Description**: Update the details of an existing alert
- **Path Parameters**:
    - `alertId` - ID of the alert to update
- **Request Parameters**:
    - `description` - Updated alert description
    - `targetAmount` - Updated target amount
    - `currentAmount` - Updated current amount
    - `deadline` - Updated deadline date (YYYY-MM-DD format)
    - `budgetId` - Updated budget ID
- **Responses**:
    - `404 Not Found` - If alert doesn't exist

### Delete an alert by ID
- **URL**: `/api/alerts/{alertId}`
- **Method**: DELETE
- **Description**: Delete a specific alert by its ID
- **Path Parameters**:
    - `alertId` - ID of the alert to delete
- **Responses**:
    - `204 No Content` - Alert deleted successfully
    - `404 Not Found` - Alert not found

## Notification APIs
APIs for managing user notifications

### Get all notifications by user ID
- **URL**: `/api/notifications/user/{userId}`
- **Method**: GET
- **Description**: Retrieve all notifications associated with a specific user ID
- **Path Parameters**:
    - `userId` - ID of the user

### Create a new notification
- **URL**: `/api/notifications`
- **Method**: POST
- **Description**: Create a new notification with the specified details
- **Request Body**: Notification object

### Delete a notification by ID
- **URL**: `/api/notifications/{id}`
- **Method**: DELETE
- **Description**: Delete a specific notification by its ID
- **Path Parameters**:
    - `id` - ID of the notification to delete
- **Responses**:
    - `204 No Content` - Notification deleted successfully
    - `404 Not Found` - Notification not found
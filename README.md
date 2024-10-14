# Project Name: Courses and Employees Management System

## About the Project
This project is a web application for managing data related to courses and employees. Users can view courses and employees, create new records, update existing records, or delete them. The project uses React.js for frontend development and interacts with a backend API built with Spring Boot.

## Features
- **Course Management**: Users can view existing courses, add new courses, update existing courses, and delete them.
- **Student Management**: Users can view students enrolled in specific courses, add new students, update existing students, and delete them.
- **Department Management**: Users can view existing departments, add new departments, update existing departments, and delete them.
- **Employee Management**: Users can view employees associated with specific departments, add new employees, update existing employees, and delete them.
- **Search and Filtering**: Users can search for students and employees and narrow down results with filtering options.
- **Pagination**: The listed records can be paginated.

## Technologies
- **Frontend**: React.js, React Bootstrap
- **Backend**: Spring Boot, H2 Database

## Installation
1. Clone this repository:
   ```bash
   git clone <repo-url>
   ```

2. Install the necessary dependencies for the backend:
   ```bash
   cd backend
   ./mvnw install
   ```

3. Install the necessary dependencies for the frontend:
   ```bash
   cd frontend
   npm install
   ```

4. Start the backend server:
   ```bash
   ./mvnw spring-boot:run
   ```

5. Start the frontend application:
   ```bash
   npm start
   ```

## Usage
- Visit `http://localhost:3000` in your browser to view the application.
- You can view students and employees by selecting between Courses and Departments.

# Courses and Employees Management System

## About the Project
This project is a web application for managing data related to courses and employees. Users can view courses and employees, create new records, update existing records, or delete them. The project uses React.js for frontend development and interacts with a backend API built with Spring Boot.

## Features
- **Login & Register**: The page will display login on the left and registration on the right. Upon successful login, the JWT token is stored in session storage for future requests.
- **Student Management**: Users can view students enrolled in specific courses, add new students, update existing students, and delete them.
- **Employee Management**: Users can view employees associated with specific departments, add new employees, update existing employees, and delete them.
- **Search and Filtering**: Users can search for students and employees and narrow down results with filtering options.
- **Pagination**: The listed records can be paginated.

## Technologies
- **Frontend**: React.js, React Bootstrap
- **Backend**: Spring Boot, H2 Database

## Installation
1. Clone this repository:
   ```bash
   git clone https://github.com/my-cs-user/case-study.git
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
- Register with a new user and login.
- You can view students and employees by selecting between Courses and Departments.

## Notes
- Departments and courses are treated as reference entities. Their addition, deletion, and updates are managed through the API, allowing for dynamic changes to these entities directly from the frontend if there will be a page for that later.
- The backend project starts with default data automatically loaded. This is achieved using a `data.sql` file located under the `resources` directory, ensuring that the application is pre-populated with initial data upon startup.

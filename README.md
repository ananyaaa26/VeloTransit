# Transport Ticket Booking System

A complete transport booking system built with a Java Spring Boot microservice architecture. This application provides a full end-to-end flow for users to register, log in, view available routes, and book tickets in real-time. It also features a separate admin dashboard for managing the vehicles and routes that power the system.

This project is built as a multi-module Maven project, demonstrating a clean separation of concerns between two independent microservices:
* **`ticket-service`**: The user-facing service that handles all user interactions, authentication, and the booking process.
* **`vehicle-service`**: A backend data-management service that manages all vehicle and route information.

## System Architecture

The application runs on two separate ports and the services communicate with each other "behind the scenes."

```
+----------------+       (Login/Booking)       +-----------------------+
|                | --------------------------> |                       |
|    Browser     |                             |  Ticket Service       |
| (HTML/CSS/JS)  |       (Get Route List)      |  (Port 8081)          |
|                | -----------------+          |                       |
+----------------+                  |          +-----------+-----------+
                                    |                      |
                                    | (Get Route Details)  | (OpenFeign)
                                    |                      |
                                    V                      V
+----------------+       +-----------------------+
|  Admin Panel   | ----> |                       |
| (Add Vehicle/Route)  |  Vehicle Service      |
|                |       |  (Port 8082)          |
+----------------+       +-----------------------+
```

## Features

* **Microservice Architecture**: Two independent services (`ticket-service` & `vehicle-service`).
* **Spring Security & JWT**: Full user registration and login system with stateless JWT-based authentication.
* **Role-Based Access**: Clear separation between `ROLE_USER` and `ROLE_ADMIN` privileges.
* **Admin Dashboard**: A separate UI for admins to add new vehicles and routes to the database.
* **Dynamic User Dashboard**: Users see a real-time list of available routes fetched from the `vehicle-service`.
* **Real-time Seat Grid**: Users can select seats from a dynamic grid that shows booked vs. available seats.
* **JPA & Hibernate**: All data is persisted in a MySQL database using Spring Data JPA.
* **Inter-Service Communication**: The `ticket-service` uses Spring Cloud OpenFeign to call the `vehicle-service` for data.

## Technologies Used

| Category | Technology |
| --- | --- |
| **Backend** | Java 17, Spring Boot, Spring Security (JWT), Spring Data JPA, Spring Cloud OpenFeign |
| **Database** | MySQL |
| **Frontend** | HTML5, CSS3, JavaScript (Fetch API), Bootstrap |
| **Build** | Apache Maven |

## Prerequisites

Before you begin, ensure you have the following installed on your system:
* **Java JDK 17** or later
* **Apache Maven**
* **MySQL Server** (or MySQL Workbench)

## Setup and Installation

Follow these steps carefully to get the project running locally.

### 1. Clone the Repository

```bash
git clone [https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git](https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git)
cd YOUR_REPOSITORY_NAME
```

### 2. Database Setup

You must create two separate, empty databases for the microservices.

Open MySQL Workbench (or your preferred SQL client) and run the following commands:
```sql
CREATE DATABASE ticket_transport;
CREATE DATABASE vehicle_transport;
```
The tables (`users`, `tickets`, `vehicles`, `routes`) will be created automatically by Hibernate when you run the applications.

### 3. Configure Database Credentials

You must update your MySQL username and password in **two** separate files.

**A. For the `ticket-service`:**
* **File:** `ticket-service/src/main/resources/application.properties`
* Update these lines with your credentials:
    ```properties
    spring.datasource.username=YOUR_MYSQL_USERNAME
    spring.datasource.password=YOUR_MYSQL_PASSWORD
    ```

**B. For the `vehicle-service`:**
* **File:** `vehicle-service/src/main/resources/application.properties`
* Update these lines with your credentials:
    ```properties
    spring.datasource.username=YOUR_MYSQL_USERNAME
    spring.datasource.password=YOUR_MYSQL_PASSWORD
    ```

### 4. Run the Applications

You must start the services **in the correct order**.

1.  **Start `vehicle-service` first:**
    * Navigate to `vehicle-service/src/main/java/com/example/transport/vehicle/`
    * Find and run the `VehicleServiceApplication.java` file.
    * It will start on **port 8082**.

2.  **Start `ticket-service` second:**
    * Navigate to `ticket-service/src/main/java/com/example/transport/ticket/`
    * Find and run the `TicketServiceApplication.java` file.
    * It will start on **port 8081**.

### 5. Create an Admin User

For security, admin accounts must be created manually.

1.  Navigate to the user signup page: **`http://localhost:8081/signup.html`**
2.  Create a new user (e.g., username: `admin-user`, password: `password123`).
3.  Open MySQL Workbench and run this SQL command to upgrade the new user to an admin:
    ```sql
    UPDATE ticket_transport.users 
    SET role = 'ROLE_ADMIN' 
    WHERE username = 'admin-user';
    ```
    
### 6. Access the Application

You are now ready to use the system!

* **Admin Panel:**
    * URL: **`http://localhost:8081/admin-login.html`**
    * Log in with the admin account you just created. You can now add vehicles and routes.

* **User Application:**
    * URL: **`http://localhost:8081/index.html`**
    * Log in with a regular user account (you can create another one via the signup page). You will see the routes added by the admin and can book tickets.

## API Endpoints

<details>
<summary><b>Vehicle Service (Port 8082)</b></summary>
  
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/vehicles` | Lists all available vehicles. |
| `POST` | `/vehicles` | Adds a new vehicle. |
| `GET` | `/routes` | Lists all available routes. |
| `GET` | `/routes/{id}` | Gets details for a single route. |
| `POST` | `/routes` | Adds a new route. |

</details>

<details>
<summary><b>Ticket Service (Port 8081)</b></summary>
  
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/register` | Creates a new user account. |
| `POST` | `/login` | Authenticates a user and returns a JWT. |
| `POST` | `/tickets` | (Protected) Books a new ticket. |
| `GET` | `/tickets/{id}` | (Protected) Gets details for a specific ticket. |
| `GET` | `/tickets/booked-seats/{routeId}` | (Protected) Gets a list of booked seats for a route. |

</details>

## Screenshots

*(Add your own screenshots here to showcase the project)*

**Admin Dashboard:**
![Admin Dashboard](PATH_TO_YOUR_SCREENSHOT.png)

**User Dashboard (Route Search):**
![User Dashboard](PATH_TO_YOUR_SCREENSHOT.png)

**Seat Booking Grid:**
![Seat Booking](PATH_TO_YOUR_SCREENSHOT.png)

## Future Work
* Implement a "My Bookings" page for users to see their ticket history.
* Add the ability for users to cancel a booking.
* Integrate a **Spring Cloud Gateway** as a single entry point for all API requests.
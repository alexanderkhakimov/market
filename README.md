## Project Description: Online Store in Java

**Tech Stack:**
- **Java** - the primary programming language.
- **Spring Boot** - framework for building the application.
- **Spring Security** - for security and authentication.
- **SQL** - for working with relational databases.
- **Hibernate** - ORM for database interaction.
- **Docker** - for containerization and easy deployment.

### Current Features:
The following functionalities have been implemented so far:
1. **User Registration and Authentication:**
   - Users can register and log in to the system.
   - Data validation is implemented during registration.
2. **Role-Based Access:**
   - **User:** Can view products, add them to the cart, and manage their account balance.
   - **Admin:** Has access to advanced features (e.g., managing products, users, etc.).
3. **Product Management:**
   - View a list of products.
   - Filter and search for products.
4. **Shopping Cart:**
   - Users can add products to the cart and place orders.
5. **User Account:**
   - Each user has a personal balance for making purchases.
   - Order and transaction history.

### Architecture:
- The application is built using RESTful API principles.
- Layered architecture is used: controllers, services, repositories.
- The SQL database stores information about users, products, orders, and transactions.
- Spring Security ensures data security and access control.

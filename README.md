# task-management-api
REST API with custom data structures and algorithms in Java
# Task Management REST API

A professional Java REST API demonstrating data structures, algorithms, and clean architecture principles. Built with Spring Boot.

## 🎯 Project Highlights

### Technical Skills Demonstrated
- **Custom Data Structures**: Min-heap based priority queue implementation
- **Algorithm Design**: Binary search, multi-criteria filtering, top-K selection
- **RESTful API Design**: Complete CRUD operations with advanced endpoints
- **OOP Principles**: Encapsulation, inheritance, polymorphism
- **Spring Boot**: Dependency injection, REST controllers, service layer
- **Testing**: JUnit 5 unit tests with comprehensive coverage

### Key Features
- ✅ Priority-based task management with custom heap implementation
- ✅ Efficient search and filtering algorithms
- ✅ Multiple sorting and grouping operations
- ✅ Task statistics and analytics
- ✅ RESTful API with 13 endpoints
- ✅ Clean architecture with separation of concerns

## 🏗️ Architecture

```
task-management-api/
├── src/main/java/com/resume/taskapi/
│   ├── model/              # Data models (Task)
│   ├── datastructures/     # Custom implementations (PriorityQueue)
│   ├── algorithm/          # Search and sort algorithms
│   ├── service/            # Business logic layer
│   ├── controller/         # REST API endpoints
│   └── TaskManagementApiApplication.java
├── src/main/resources/
│   └── application.properties
├── src/test/java/          # Unit tests
├── pom.xml                 # Maven dependencies
└── README.md
```

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Installation & Running

1. **Clone the repository**
```bash
git clone <your-repo-url>
cd task-management-api
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The API will start at `http://localhost:8080`

### Running Tests
```bash
mvn test
```

## 📡 API Endpoints

### Basic CRUD Operations
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tasks` | Create a new task |
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get task by ID |
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |

### Advanced Operations
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks/priority` | Get tasks sorted by priority |
| GET | `/api/tasks/next` | Get next highest priority task |
| GET | `/api/tasks/top/{k}` | Get top K priority tasks |
| GET | `/api/tasks/search?keyword={keyword}` | Search tasks by keyword |
| GET | `/api/tasks/filter?status=&priority=&assignedTo=` | Filter by criteria |
| GET | `/api/tasks/grouped` | Get tasks grouped by status |
| GET | `/api/tasks/overdue` | Get overdue tasks |
| GET | `/api/tasks/statistics` | Get task statistics |

## 💡 Usage Examples

### Create a Task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Fix critical bug",
    "description": "Production issue in payment service",
    "priority": "CRITICAL",
    "dueDate": "2024-12-31T23:59:59"
  }'
```

### Get Top 5 Priority Tasks
```bash
curl http://localhost:8080/api/tasks/top/5
```

### Search Tasks
```bash
curl http://localhost:8080/api/tasks/search?keyword=bug
```

### Filter Tasks
```bash
curl "http://localhost:8080/api/tasks/filter?status=TODO&priority=HIGH"
```

### Get Statistics
```bash
curl http://localhost:8080/api/tasks/statistics
```

## 🧮 Data Structures & Algorithms

### Custom Priority Queue (Min-Heap)
- **Insert**: O(log n)
- **Poll (remove highest priority)**: O(log n)
- **Peek**: O(1)
- **Update priority**: O(log n)
- **Remove by ID**: O(log n)

Implementation uses:
- Array-based heap structure
- HashMap for O(1) ID lookups
- Bubble-up and bubble-down operations

### Search Algorithms
- **Binary Search**: O(log n) for sorted ID search
- **Multi-criteria Filter**: O(n) with optimized predicates
- **Top-K Selection**: O(n log k) using partial sort
- **Keyword Search**: O(n × m) where m is average string length
- **Grouping**: O(n) using HashMap aggregation

## 📊 Task Model

```java
{
  "id": "TASK-0001",
  "title": "Task title",
  "description": "Task description",
  "priority": "HIGH",           // LOW, MEDIUM, HIGH, CRITICAL
  "status": "TODO",             // TODO, IN_PROGRESS, COMPLETED, CANCELLED
  "createdAt": "2024-01-15T10:30:00",
  "dueDate": "2024-01-20T17:00:00",
  "assignedTo": "developer@example.com"
}
```

## 🧪 Testing

The project includes comprehensive unit tests covering:
- Priority queue operations (insert, poll, remove, update)
- Search algorithms (binary search, keyword search, filtering)
- Task comparison and sorting
- Statistics calculation
- Edge cases and boundary conditions

Run tests with coverage:
```bash
mvn test jacoco:report
```

## 🎓 Learning Outcomes

This project demonstrates:
1. **Data Structure Implementation**: Built a custom min-heap from scratch
2. **Algorithm Design**: Multiple search/sort algorithms with complexity analysis
3. **API Design**: RESTful principles with proper HTTP methods and status codes
4. **Clean Code**: Separation of concerns, SOLID principles
5. **Testing**: Unit tests with JUnit 5
6. **Documentation**: Comprehensive README and inline comments

## 🔧 Technologies Used

- **Java 17**: Modern Java features
- **Spring Boot 3.2**: REST API framework
- **Maven**: Build and dependency management
- **JUnit 5**: Testing framework
- **Spring Web**: REST controllers
- **Jackson**: JSON serialization

## 📈 Future Enhancements

- [ ] Add database persistence (PostgreSQL/MongoDB)
- [ ] Implement authentication and authorization
- [ ] Add pagination for large result sets
- [ ] Create Swagger/OpenAPI documentation
- [ ] Add caching layer (Redis)
- [ ] Implement rate limiting
- [ ] Add WebSocket support for real-time updates
- [ ] Create a frontend UI (React/Angular)

## 📝 License

This project is available for personal and educational use.

## 👤 Author

[Your Name]
- GitHub: [@anuragsharma23](https://github.com/yourusername)
- LinkedIn: [](https://linkedin.com/in/yourprofile)
- Email: iitanurag99@example.com

---

⭐ If you found this project helpful, please give it a star on GitHub!

# Bibliographic Database System (BibDB)

**CPT402 Software Architectures – Coursework 3**  
**Group TXR**  
*Spring Boot + Bootstrap | MVC Distributed Architecture*

---

## 📌 Project Overview

BibDB is a simplified academic publication management system inspired by [Bibsonomy](https://www.bibsonomy.org/).  
It allows users to register, search, add, tag, and bookmark scholarly publications.  
The system is built following the **Model–View–Controller (MVC)** design pattern and uses a **multi‑tiered distributed architecture**.

---

## 🚀 Core Features

- User registration & JWT‑based authentication
- Publication CRUD (Create, Read, Update, Delete)
- Full‑text fuzzy search (title, authors, keywords) with pagination
- Tag management (assign / remove tags)
- Personal bookmarks / favourites
- BibTeX import (optional)
- Dataset auto‑initialisation (200+ real academic records)

---

## 🛠 Tech Stack

| Layer     | Technology                          |
|-----------|-------------------------------------|
| Backend   | Spring Boot 2.7, Spring Data JPA, Spring Security, JWT |
| Frontend  | HTML5, Bootstrap 5, Axios (vanilla JS) |
| Database  | H2 (in‑memory, dev/test), MySQL 8 (production) |
| API Doc   | Swagger (SpringDoc OpenAPI)         |
| Build     | Maven                               |
| Testing   | JMeter, JUnit, Postman              |
| AI Tools  | GitHub Copilot / ChatGPT (specifications & signatures only) |

---

## ⚡ Quick Start

### Prerequisites
- JDK 11 or 17
- Maven 3.8+ (IntelliJ built‑in Maven is fine)

### Run the application
```bash
# Clone the repository
git clone https://github.com/your-org/bib-db-system.git
cd bib-db-system

# Run with Maven wrapper (Windows use mvnw.cmd)
./mvnw spring-boot:run

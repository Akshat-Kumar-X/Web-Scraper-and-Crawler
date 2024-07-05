# WebScraper & WebCrawler Application

WebScraper & WebCrawler is a Java-based application that crawls and scrapes data from websites. This project uses the `Jsoup` library to parse HTML and extract information, such as product names and prices, from e-commerce websites like Amazon. 

## Features

- Crawls and scrapes data from web pages
- Extracts product information including name, price, and image URL
- Handles errors and retries on server unavailability

## Prerequisites

- Java 8 or higher
- Maven 3.6.3 or higher

## Use Case Diagram
![image](https://github.com/Akshat-Kumar-X/Web-Scraper-and-Crawler/assets/112055229/a754632f-d7f6-4cad-99cf-ee97f3126a36)

# How to Setup

### Step 1: Install Java
- Ensure you have Java Development Kit (JDK) installed on your machine. You can download it from the [Oracle website](https://www.oracle.com/java/technologies/downloads/#java11)

### Step 2: Install Maven
- Download and install Apache Maven from the [Official website](https://maven.apache.org/install.html)

### Step 3: Clone the Repository
- Open a terminal and clone the project repository:

```bash
git clone https://github.com/yourusername/WebScraper.git
cd WebScraper
```
### Step 4: Install Project Dependencies
- Navigate to the project directory and use Maven to install the required dependencies:

```bash
mvn clean install
```
### Step 5: Run the Application
- To run the application, execute the following command:

```bash
mvn exec:java -Dexec.mainClass="FlipkartScraper"
```

## Contributors
Current Contributors:
- Akshat Kumar
- Mr. Siddhartha Tripathy
Contributions are welcome! Please feel free to submit a Pull Request.

## License
This project is licensed under the MIT License.

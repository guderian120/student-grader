# Installation and Compilation Guide

## Prerequisites
- **Java Development Kit (JDK)**: Version 17 or higher.
- **JavaFX SDK**: Included in the `lib` directory (version 17.0.2).
- **SQLite JDBC Driver**: Included in the `lib` directory.

## Directory Structure Setup
Ensure your project directory looks like this:
```
grader_pj/
├── lib/
│   ├── javafx-sdk-17.0.2/
│   └── sqlite-jdbc-3.36.0.3.jar
├── src/
│   └── java/
│       └── com/
│           └── grader/
│               ├── GradedApplication.java
│               ├── dao/
│               └── model/
└── bin/ (will be created during compilation)
```

## Compilation

1. Open a terminal (Command Prompt or PowerShell) and navigate to the project root directory (`grader_pj`).

2. Create the output directory if it doesn't exist:
   ```powershell
   mkdir bin
   ```

3. Compile the source code.
   
   **Windows (PowerShell):**
   ```powershell
   javac -d bin -cp "lib/sqlite-jdbc-3.36.0.3.jar;lib/javafx-sdk-17.0.2/lib/*" src/java/com/grader/*.java src/java/com/grader/dao/*.java src/java/com/grader/model/*.java
   ```

   **Linux/macOS:**
   ```bash
   javac -d bin -cp "lib/sqlite-jdbc-3.36.0.3.jar:lib/javafx-sdk-17.0.2/lib/*" src/java/com/grader/*.java src/java/com/grader/dao/*.java src/java/com/grader/model/*.java
   ```

## Running the Application

To run the application, use the following command. Note that we need to specify the module path for JavaFX.

**Windows (PowerShell):**
```powershell
java -cp "bin;lib/sqlite-jdbc-3.36.0.3.jar" --module-path "lib/javafx-sdk-17.0.2/lib" --add-modules javafx.controls,javafx.fxml com.grader.GradedApplication
```

**Linux/macOS:**
```bash
java -cp "bin:lib/sqlite-jdbc-3.36.0.3.jar" --module-path "lib/javafx-sdk-17.0.2/lib" --add-modules javafx.controls,javafx.fxml com.grader.GradedApplication
```

## Troubleshooting

- **"Module not found"**: Ensure the path to `javafx-sdk-17.0.2/lib` is correct in the `--module-path` argument.
- **"Class not found"**: Ensure you ran the compilation step successfully and the `bin` directory contains the compiled `.class` files.
- **Database Errors**: The application will automatically create `grader.db` in the root directory if it doesn't exist. Ensure you have write permissions in the folder.

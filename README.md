# Pharmacy Java Project 💊

This repository contains a Java project developed as a unit project for the Object-Oriented Programming I course. The project focuses on creating a pharmacy management system with a graphical user interface (GUI) using Swing and JDBC for database connectivity.

## Overview

The project consists of several Java classes, each serving a specific purpose in the pharmacy management system. Below is a brief description of each file:

### `DBConnection.java`

This file contains the `DBConnection` class, which provides a static method to establish a connection to the MySQL database using JDBC.

### `MainGUI.java`

The `MainGUI` class represents the main graphical user interface of the application. It provides options for both patients and doctors to log in or sign up.

### `PatientLoginGUI.java`

The `PatientLoginGUI` class handles the login process for patients. It prompts patients to enter their ID, first name, and last name for authentication.

### `PatientSignupGUI.java`

The `PatientSignupGUI` class allows patients to sign up by providing necessary details such as ID, first name, and last name.

### `PatientMainPage.java`

The `PatientMainPage` class displays the main page for patients after successful login. It provides options to order drugs, check balance, and logout.

### `DoctorLoginGUI.java`

The `DoctorLoginGUI` class handles the login process for doctors. Similar to `PatientLoginGUI`, it prompts doctors to enter their ID and password for authentication.

### `DoctorSignupGUI.java`

The `DoctorSignupGUI` class allows doctors to sign up by providing their ID and password.

### `DoctorMainPage.java`

The `DoctorMainPage` class displays the main page for doctors after successful login. It provides options to change the password, search drugs, and logout.

### `DoctorChangePasswordGUI.java`

The `DoctorChangePasswordGUI` class provides a GUI for doctors to change their passwords. It prompts doctors to enter their ID and new password.

### `CurrentStockManagementGUI.java`

The `CurrentStockManagementGUI` class allows administrators to manage the current stock of drugs. It enables searching for a drug, viewing its current stock, and updating the stock.

## Usage

To use this project, make sure you have Java and MySQL installed on your system. Clone the repository and import it into your preferred Java IDE. Set up the MySQL database according to the provided schema. Update the database connection details in `DBConnection.java`. Then, run the `MainGUI.java` file to start the application.

Feel free to explore the various functionalities provided by the application, including patient and doctor login, signup, drug ordering, stock management, and more.

## Credits

This project was developed by Nathan Githinji aka me as a unit project for the Object-Oriented Programming I course. Any contributions or suggestions are welcome.

Enjoy managing your pharmacy with ease using this Java application! 🚀

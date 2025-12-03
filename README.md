# 🏦 Banking System Automated E2E Test Suite

## Project Overview

This repository contains a comprehensive End-to-End (E2E) automation test suite for a core **Banking System** application. The tests are designed to validate critical user flows, from user registration to financial transactions and administrative settings.

[cite_start]The entire test suite is built using **Selenium WebDriver** for browser interaction and **JUnit** for the testing framework.

## Features Covered

This test suite covers all primary features and user paths, including:

* **User Management:**
    * [cite_start]`RegisterTest`: Successful new user registration[cite: 483].
    * [cite_start]`Login Test`: Valid and invalid login scenarios (including missing credentials)[cite: 484, 485].
    * [cite_start]`LogoutTest`: Secure user logout[cite: 487].
* **Account & Transactions:**
    * [cite_start]`OpenSavingsAccountTest`: Creation of new savings accounts[cite: 488].
    * [cite_start]`Transfer Funds Test`: Verifying fund transfers between accounts[cite: 489].
* **Administration:**
    * [cite_start]`AdminSettings Test`: Modification and validation of administrative settings[cite: 486].

## Prerequisites

To run these tests locally, you need the following installed:

* **Java Development Kit (JDK)** (Version 11 or higher recommended)
* **Maven** (or Gradle, depending on your build tool)
* **WebDriver:** Ensure you have the appropriate browser driver (e.g., ChromeDriver, GeckoDriver) installed and its path correctly configured in your test setup.

## Getting Started

### 1. Clone the Repository
git clone https://github.com/Sruthy2020/Selenium-test-practice/
cd Selenium-test-practice
### 2. Configure Dependencies

If using Maven, navigate to the project directory and build the project:
```
mvn clean install
```
### 3. Running the Tests
The tests are configured to run in a specific order to ensure data integrity, as the registration must occur before any other operation.
- Important Order: The RegisterTest must be executed first to create the necessary user account before subsequent tests can run successfully (e.g., login, savings account, fund transfers).

You can execute the entire suite via your IDE (IntelliJ, Eclipse) or via the command line using Maven/JUnit:
```
mvn test
```
### 4. Reporting
Test execution generates detailed reports, which include:
- Status (Passed/Failed) for each test case.
- Detailed breakdown of test steps and expected results.
- Execution logs and timestamps.

A sample test report is available in the repository as Selenium-Report.pdf

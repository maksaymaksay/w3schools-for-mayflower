# w3school-for-myflower
This project focuses on testing the UI using Selenium. It includes various test scenarios to validate different functionalities of the application.

## Setup
To run the tests, make sure you have the following prerequisites:

- JDK (Java Development Kit)

## Getting Started

1. Clone the repository:

   ```shell
   git clone <repository-url>
   ```

2. Change into the project directory:

   ```shell
   cd <project-directory>
   ```

3. Build the project using the Maven Wrapper:

   ```shell
   ./mvnw clean install
   ```

4. Execute the tests:

   ```shell
   ./mvnw test
   ```

## Test Scenarios

### Test: checkAddressOfContractNameTest

- Description: Verifies that the record with ContactName 'Giovanni Rovelli' has the expected Address 'Via Ludovico il Moro 22'.

### Test: checkCustomersCityTest

- Description: Retrieves only the rows from the Customers table where the City is 'London' and verifies that there are exactly 6 records.

### Test: addNewCustomerTest

- Description: Inserts a new record into the Customers table and verifies that the record is added successfully.

### Test: updateCustomerFieldsTest

- Description: Updates the fields (except CustomerID) of a Customer record and verifies that the changes are reflected in the database.

### Test: deleteCustomerTest

- Description: Deletes a Customer record from the Customers table and verifies that the deletion is successful.

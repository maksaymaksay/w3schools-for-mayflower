import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import w3schools.dto.Customer;
import w3schools.pages.MainPage;

import java.util.List;

public class MainPageTest extends BaseTest {

    private MainPage mainPage;

    @Before
    public void init() {
        mainPage = new MainPage(driver);
    }

    @Test
    // Вывести все строки таблицы Customers и убедиться, что запись с ContactName равной 'Giovanni Rovelli'
    // имеет Address = 'Via Ludovico il Moro 22'.
    public void checkAddressOfContractNameTest() {
        String selectSqlQuery = "SELECT * FROM Customers;";

        List<Customer> customerList = mainPage
                .openW3schools()
                .executeSQLScript(selectSqlQuery)
                .getCustomersFromResultTable();

        Customer customer = customerList.stream()
                .filter(c -> ("Giovanni Rovelli").equals(c.getContactName()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Customer doesn't exist"));

        Assert.assertEquals("Actual address is not equal to expected",
                "Via Ludovico il Moro 22", customer.getAddress());
    }

    @Test
    // Вывести только те строки таблицы Customers, где city='London'.
    // Проверить, что в таблице ровно 6 записей.
    public void checkCustomersCityTest() {
        String selectSqlQuery = "SELECT * FROM Customers WHERE City = \\'London\\';";

        int numOfRecords = mainPage
                .openW3schools()
                .executeSQLScript(selectSqlQuery)
                .getCustomersFromResultTable()
                .size();

        Assert.assertEquals("The number of city records is not equal to expected", 6, numOfRecords);
    }

    @Test
    // Добавить новую запись в таблицу Customers и проверить, что эта запись добавилась.
    public void addNewCustomerTest() {
        String insertSqlQuery = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) " +
                "VALUES (\\'Albert Einstein\\', \\'Hans Albert Einstein\\', \\'Kramgasse 49\\', \\'Bern\\', \\'3011\\', \\'Switzerland\\');";
        String selectSqlQuery = "SELECT * FROM Customers;";

        int numOfRecords = mainPage
                .openW3schools()
                .executeSQLScript(selectSqlQuery)
                .getCustomersFromResultTable()
                .size();

        List<Customer> customerList = mainPage
                .executeSQLScript(insertSqlQuery)
                .executeSQLScript(selectSqlQuery)
                .getCustomersFromResultTable();

        Assert.assertEquals(numOfRecords + 1, customerList.size());

        Assert.assertTrue("The new customer wasn't added", customerList.stream()
                .anyMatch(customer -> "Albert Einstein".equals(customer.getCustomerName())
                        && "Hans Albert Einstein".equals(customer.getContactName())
                        && "Kramgasse 49".equals(customer.getAddress())
                        && "Bern".equals(customer.getCity())
                        && "3011".equals(customer.getPostalCode())
                        && "Switzerland".equals(customer.getCountry())));
    }

    @Test
    // Обновить все поля (кроме CustomerID) в любой записи таблицы Customers и проверить, что изменения записались в базу.
    public void updateCustomerFieldsTest() {
        String selectSqlQuery = "SELECT * FROM Customers WHERE CustomerID = 1;";
        String updateSqlQuery = "UPDATE Customers " +
                "SET CustomerName = \\'Test Testovich\\', ContactName = \\'Testetta Testovna\\', Address = \\'Testing address\\', " +
                "City = \\'City of Test\\', PostalCode = \\'123456\\', Country = \\'United States Of Tests\\' " +
                "WHERE CustomerID = 1;";

        List<Customer> customerList = mainPage
                .openW3schools()
                .executeSQLScript(selectSqlQuery)
                .getCustomersFromResultTable();

        Assert.assertTrue(customerList.stream()
                .anyMatch(customer -> "Alfreds Futterkiste".equals(customer.getCustomerName())
                        && "Maria Anders".equals(customer.getContactName())
                        && "Obere Str. 57".equals(customer.getAddress())
                        && "Berlin".equals(customer.getCity())
                        && "12209".equals(customer.getPostalCode())
                        && "Germany".equals(customer.getCountry())));

        List<Customer> updatedCustomerList = mainPage
                .executeSQLScript(updateSqlQuery)
                .executeSQLScript(selectSqlQuery)
                .getCustomersFromResultTable();

        Assert.assertTrue("The fields of the customer weren't updated", updatedCustomerList.stream()
                .anyMatch(customer -> "Test Testovich".equals(customer.getCustomerName())
                        && "Testetta Testovna".equals(customer.getContactName())
                        && "Testing address".equals(customer.getAddress())
                        && "City of Test".equals(customer.getCity())
                        && "123456".equals(customer.getPostalCode())
                        && "United States Of Tests".equals(customer.getCountry())));
    }

    @Test
    // Удалить запись о клиенте из таблицы Customers и проверить, что удаление произошло успешно.
    public void deleteCustomerTest() {
        String selectSqlQuery = "SELECT * FROM Customers;";
        String deleteSqlQuery = "DELETE FROM Customers WHERE CustomerName = \\'Wolski\\';";

        int numOfRecords = mainPage
                .openW3schools()
                .executeSQLScript(selectSqlQuery)
                .getCustomersFromResultTable()
                .size();

        List<Customer> customerList = mainPage
                .executeSQLScript(deleteSqlQuery)
                .executeSQLScript(selectSqlQuery)
                .getCustomersFromResultTable();

        Assert.assertEquals(numOfRecords - 1, customerList.size());

        Assert.assertTrue("The customer wasn't deleted", customerList.stream()
                .noneMatch(customer -> "Wolski".equals(customer.getCustomerName())
                        && "Zbyszek".equals(customer.getContactName())
                        && "ul. Filtrowa 68".equals(customer.getAddress())
                        && "Walla".equals(customer.getCity())
                        && "01-012".equals(customer.getPostalCode())
                        && "Poland".equals(customer.getCountry())));
    }
}

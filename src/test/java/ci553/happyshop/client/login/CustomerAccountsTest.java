package ci553.happyshop.client.login;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class CustomerAccountsTest {

    private CustomerAccounts customerAccounts;
    private final String TEST_FILE = "users.txt";

    @BeforeEach
    void setUp() {
        //delete file before each test is ran.
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }

        customerAccounts = new CustomerAccounts();
    }

    @AfterEach
    void tearDown() {
        // Clean up the file after test finishes
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testDefaultAccountsCreation() {
        // test if we can log in to the default admin accounts
        assertTrue(customerAccounts.login("admin", "admin123"));
        //Test if we can get the accounts type.
        assertEquals("ADMIN", customerAccounts.getCurrentAccountType());
        //test if we can log in to the default customer account
        assertTrue(customerAccounts.login("frank", "scott"));
        assertEquals("CUSTOMER", customerAccounts.getCurrentAccountType());
        assertTrue(customerAccounts.login("picker", "picker"));
        assertEquals("PICKER", customerAccounts.getCurrentAccountType());
    }
    @Test
    void testAddNewAccount() {
        Account test = new CustomerAccount("newguy", "new@test.com", "pass123", false);

        customerAccounts.addAccount(test);

        assertTrue(customerAccounts.login("newguy", "pass123"));
    }
    @Test
    void testNewAccountWithAnExistingPassword() {
        Account test = new CustomerAccount("tester", "testingtester@test.com", "admin123", false);

        customerAccounts.addAccount(test);

        assertTrue(customerAccounts.login("tester", "admin123"));
    }
    @Test
    void testLoginFailure() {
        assertFalse(customerAccounts.login("admin", "wrongpassword"));
        assertFalse(customerAccounts.login("mwuahahah", "admin123"));
        assertEquals("NONE", customerAccounts.getCurrentAccountType());
    }

    @Test
    void testValidation() {
        assertTrue(customerAccounts.checkNewCustomerDetails("user", "12345", "a@b.com"));
        assertFalse(customerAccounts.checkNewCustomerDetails("user", "12345", "ab.com"));
        assertFalse(customerAccounts.checkNewCustomerDetails("user", "1234", "a@b.com"));
    }

    @Test
    void testPersistence() {
        Account savedUser = new CustomerAccount("persistUser", "p@test.com", "saveMe", false);
        customerAccounts.addAccount(savedUser);

        CustomerAccounts newSession = new CustomerAccounts();

        boolean success = newSession.login("persistUser", "saveMe");

        assertTrue(success, "Data should persist to file and be reloadable by new instance");
    }
}
package ci553.happyshop.client.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountUtilsTest {

    @Test
    @DisplayName("Compare known hash of admin123 to calculated hash")
    void hashPassword() {
        //compares the hash of "admin123" to the returned hash of "admin123"
        assertEquals("240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9", AccountUtils.hashString("admin123"));
    }
    @Test
    @DisplayName("Compare raw password vs hash")
    void checkCorrectPassword()
    {
        //checks the raw password vs the stored hash
        var storedHash = AccountUtils.hashString("admin123");
        var rawPassword = "admin123";
        assertTrue(AccountUtils.checkHash(rawPassword, storedHash));
    }
    @Test
    @DisplayName("Compare different raw password to hash")
    void checkIncorrectPassword()
    {
        //checks the raw password vs the stored hash which is a different password.
        var storedHash = AccountUtils.hashString("wrong");
        var rawPassword = "correct";
        assertFalse(AccountUtils.checkHash(rawPassword, storedHash));
    }
}
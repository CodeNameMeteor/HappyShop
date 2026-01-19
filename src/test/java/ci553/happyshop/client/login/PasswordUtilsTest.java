package ci553.happyshop.client.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilsTest {

    @Test
    @DisplayName("Compare known hash of admin123 to calculated hash")
    void hashPassword() {
        //compares the hash of "admin123" to the returned hash of "admin123"
        assertEquals("240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9", PasswordUtils.hashPassword("admin123"));
    }
    @Test
    @DisplayName("Compare raw password vs hash")
    void checkCorrectPassword()
    {
        //checks the raw password vs the stored hash
        var storedHash = PasswordUtils.hashPassword("admin123");
        var rawPassword = "admin123";
        assertTrue(PasswordUtils.checkPassword(rawPassword, storedHash));
    }
    @Test
    @DisplayName("Compare different raw password to hash")
    void checkIncorrectPassword()
    {
        //checks the raw password vs the stored hash which is a different password.
        var storedHash = PasswordUtils.hashPassword("wrong");
        var rawPassword = "correct";
        assertFalse(PasswordUtils.checkPassword(rawPassword, storedHash));
    }
}
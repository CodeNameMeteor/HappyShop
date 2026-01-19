package ci553.happyshop.client.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilsTest {

    @Test
    @DisplayName("Compare known hash of admin123 to calculated hash")
    void hashPassword() {
        assertEquals("240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9", PasswordUtils.hashPassword("admin123"));
    }
    @Test
    @DisplayName("Compare raw password vs hash")
    void checkCorrectPassword()
    {
        var storedHash = PasswordUtils.hashPassword("admin123");
        var rawPassword = "admin123";
        assertTrue(PasswordUtils.checkPassword(rawPassword, storedHash));
    }
    @Test
    @DisplayName("Compare different raw password to hash")
    void checkIncorrectPassword()
    {
        var storedHash = PasswordUtils.hashPassword("wrong");
        var rawPassword = "correct";
        assertFalse(PasswordUtils.checkPassword(rawPassword, storedHash));
    }
}
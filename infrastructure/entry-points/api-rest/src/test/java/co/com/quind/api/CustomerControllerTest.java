package co.com.quind.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerControllerTest {

    CustomerController customerController = new CustomerController();

    @Test
    void apiRestTest() {
        var response = customerController.commandName();
        assertEquals("Hello World", response);
    }
}

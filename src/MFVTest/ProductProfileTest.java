package MFVTest;

import entity.ProductProfile;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ProductProfileTest {
    @org.junit.jupiter.api.Test
    void setPrice(){
        Executable tmp = () -> {TestCases.product1.setPrice(new BigDecimal("0.0"));};
        assertThrows(InvalidParameterException.class, tmp);
        tmp = () -> {TestCases.product1.setPrice(new BigDecimal("-10.0"));};
        assertThrows(InvalidParameterException.class, tmp);
    }

    @org.junit.jupiter.api.Test
    void setCategory() {
        for (String s : TestCases.randomStrings) {
            System.out.println("Testing:" + s);
            Executable tmp = () -> {TestCases.product1.setCategory(s);};
            Exception e = assertThrows(InvalidParameterException.class, tmp);
            System.out.println("Success");
        }
    }

    @org.junit.jupiter.api.Test
    void setSalesMode() {
        for (String s : TestCases.randomStrings) {
            System.out.println("Testing:" + s);
            Executable tmp = () -> {TestCases.product1.setSalesMode(s);};
            Exception e = assertThrows(InvalidParameterException.class, tmp);
            System.out.println("Success");
        }
    }

    @org.junit.jupiter.api.Test
    void getAllNames() {
        assertEquals(Arrays.asList("Sausage Pie", "a2", "a3"), TestCases.product1.getAllNames());
        System.out.println("Success");
    }
}
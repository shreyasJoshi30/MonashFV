package MFVTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void setState() {
        for (String s : TestCases.randomStrings) {
            System.out.println("Testing:" + s);
            Executable tmp = () -> {TestCases.item1.setState(s);};
            Exception e = assertThrows(InvalidParameterException.class, tmp);
            System.out.println("Success");
        }
    }
}
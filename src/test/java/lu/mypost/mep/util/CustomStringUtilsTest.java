package lu.mypost.mep.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
public class CustomStringUtilsTest {

    @Test
    public void formatNameToIdWithNoSpecialCharacterTest() {

        String input = "testWithNoSpecialCharacter";
        String expectedResult = "testWithNoSpecialCharacter";
        String actualResult = CustomStringUtils.formatNameToId(input);

        assertEquals("Conversion of name with no special character failed", expectedResult, actualResult);
    }

    @Test
    public void formatNameToIdWithSpaceSpecialCharacterTest() {

        String input = "testWith SpecialCharacter";
        String expectedResult = "testWith_SpecialCharacter";
        String actualResult = CustomStringUtils.formatNameToId(input);

        assertEquals("Conversion of name with space failed", expectedResult, actualResult);
    }

    @Test
    public void formatNameToIdWithDotSpecialCharacterTest() {

        String input = "testWith.SpecialCharacter";
        String expectedResult = "testWith_SpecialCharacter";
        String actualResult = CustomStringUtils.formatNameToId(input);

        assertEquals("Conversion of name with dot failed", expectedResult, actualResult);
    }

    @Test
    public void formatNameToIdWithAllSpecialCharacterTest() {

        String input = "testWith. SpecialCharacter";
        String expectedResult = "testWith__SpecialCharacter";
        String actualResult = CustomStringUtils.formatNameToId(input);

        assertEquals("Conversion of all handled characters failed", expectedResult, actualResult);
    }

}

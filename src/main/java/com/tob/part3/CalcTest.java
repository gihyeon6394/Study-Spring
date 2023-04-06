package com.tob.part3;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.JUnitCore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CalcTest {

    private Calculator calculator;
    private File file;


    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        JUnitCore.main("com.tob.part3.CalcTest");
    }


    @Before
    public void beforeTest() throws IOException {
        calculator = new Calculator();
        ClassPathResource resource = new ClassPathResource("numbers.txt");
        file = resource.getFile();

    }


    @Test
    public void sumOfNumbers() throws IOException {
//        Calculator calculator = new Calculator();
//        ClassPathResource resource = new ClassPathResource("numbers.txt");
//        int sum = calculator.sum1(resource.getFile());
//        int sum = calculator.sum2(file);
        int sum = calculator.sum3(file);

        assertThat(sum, is(6));

    }


    @Test
    public void multipleOfNumbers() throws IOException {
//        Calculator calculator = new Calculator();
//        ClassPathResource resource = new ClassPathResource("numbers.txt");
//        int sum = calculator.multiple1(file);
//        int sum = calculator.multiple2(file);
        int sum = calculator.multiple4(file);

        assertThat(sum, is(6));

    }

    @Test
    public void concatenateOfNubmers() throws IOException {
        String sum = calculator.concatenate(file);
        assertThat(sum, is("123"));

    }

}

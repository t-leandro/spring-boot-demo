package com.example.calculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BigDecimalCalculatorTest {

   @Autowired
    private ApplicationContext ctx;

    private Calculator calculator;

    @Before
    public void setUp(){
        calculator = ctx.getAutowireCapableBeanFactory().createBean(BigDecimalCalculator.class);
    }

    @Test
    public void sum() {
        String result = calculator.sum("1","2");
        Assert.assertEquals(result, "3");

        result = calculator.sum("1.00000000","10000.73645472827633");
        Assert.assertEquals(result, "10001.73645472827633");


    }

    @Test
    public void subtraction() {
        String result = calculator.subtraction("1","2");
        Assert.assertEquals(result, "-1");

        result = calculator.subtraction("30000000000000000","2");
        Assert.assertEquals(result, "29999999999999998");
    }

    @Test
    public void multiplication() {
        String result = calculator.multiplication("1","2");
        Assert.assertEquals(result, "2");

        result = calculator.multiplication("173534343.92388237273672326356253625323232","0");
        Assert.assertEquals(result, "0E-32");
    }

    @Test
    public void division() {
        String result = calculator.division("1","2");
        Assert.assertEquals(result, "0.5000000000");
        // TODO : what?
        calculator.division("1","0");
        Assert.assertEquals(result, "0.5000000000");
    }
}
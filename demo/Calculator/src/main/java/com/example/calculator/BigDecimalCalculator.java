package com.example.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

/**
 * Implementation of Calculator interface that uses BigDecimal
 */
@Service
public class BigDecimalCalculator implements Calculator {
    private static final Logger LOG = LoggerFactory.getLogger(BigDecimalCalculator.class);

    @Value("${divide.rounding.mode:UP}")
    private RoundingMode divideRoundingMode;

    @Value("${divide.scale:10}")
    private int divideScale;

    @Override
    public String sum(String a, String b) {
        LOG.info("Entered in big decimal calculator sum method");
        return performOperation(a, b, BigDecimal::add);
    }

    @Override
    public String subtraction(String a, String b) {
        LOG.info("Entered in big decimal calculator substraction method");
        return performOperation(a,b, BigDecimal::subtract);
    }

    @Override
    public String multiplication(String a, String b) {
        LOG.info("Entered in big decimal calculator multiplication method");
        return performOperation(a,b, BigDecimal::multiply);
    }

    @Override
    public String division(String a, String b) {
        LOG.info("Entered in big decimal calculator division method with round type {} and scale {}",
                divideRoundingMode.toString(),
                divideScale);

        return performOperation(a,b, (n1,n2)-> n1.divide(n2, divideScale, divideRoundingMode));
    }

    private String performOperation (String a, String b, BiFunction<BigDecimal,BigDecimal,BigDecimal> operation) {
        LOG.info("Started operation with operands a = {} and b = {}", a ,b);
        try{
            BigDecimal n1 = new BigDecimal(a);
            BigDecimal n2 = new BigDecimal(b);

            return  operation
                    .apply(n1, n2)
                    .toString();
        }
        catch(Exception e){
            LOG.error("Could not perform operation",e);
            return null;
        }
    }

    /*private static BigDecimal stringToBigDecimal(final String formattedString,
                                                 final Locale locale)
    {
        final DecimalFormatSymbols symbols;
        final char                 groupSeparatorChar;
        final String               groupSeparator;
        final char                 decimalSeparatorChar;
        final String               decimalSeparator;
        String                     fixedString;
        final BigDecimal           number;

        symbols              = new DecimalFormatSymbols(locale);
        groupSeparatorChar   = symbols.getGroupingSeparator();
        decimalSeparatorChar = symbols.getDecimalSeparator();

        if(groupSeparatorChar == '.')
        {
            groupSeparator = "\\" + groupSeparatorChar;
        }
        else
        {
            groupSeparator = Character.toString(groupSeparatorChar);
        }

        if(decimalSeparatorChar == '.')
        {
            decimalSeparator = "\\" + decimalSeparatorChar;
        }
        else
        {
            decimalSeparator = Character.toString(decimalSeparatorChar);
        }

        fixedString = formattedString.replaceAll(groupSeparator , "");
        fixedString = fixedString.replaceAll(decimalSeparator , ".");
        number      = new BigDecimal(fixedString);

        return (number);
    }*/

}

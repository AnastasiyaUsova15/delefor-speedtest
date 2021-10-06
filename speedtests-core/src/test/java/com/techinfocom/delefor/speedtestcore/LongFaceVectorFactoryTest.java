package com.techinfocom.delefor.speedtestcore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.IDataProviderMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LongFaceVectorFactoryTest {
    private static Logger LOGGER = LoggerFactory.getLogger(LongFaceVectorFactoryTest.class);

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "^We found 3 vector parts instead of expected 512 for .*")
    public void testErrorString() {
        final LongFaceVectorFactory factory = new LongFaceVectorFactory(512);
        final LongFaceVector vector = factory.getInstance("-0.049911,-0.0057704,-0.0235393");
        Assert.fail("We expect an exception here");
    }

    @Test
    public void testCreateFromString() {
        final LongFaceVectorFactory factory = new LongFaceVectorFactory(3);
        final LongFaceVector vector = factory.getInstance("-0.049911,-0.0057704,-0.0235393");
        Assert.assertNotNull(vector);
        Assert.assertEquals(vector.getLength(), 3);
    }

//    @DataProvider
//    private Object[][] testCreateFromFile(){
//        return new Object[][]{
//                {"/src/test/resources/218116.txt"}
//        };
//    }

    @Test
    public void testCreateFromFile() {
        final LongFaceVectorFactory factory = new LongFaceVectorFactory(512);
        final File currentDir = new File(".");
        LOGGER.info("currentDir={}", currentDir.getAbsolutePath());
        final File dataFile = new File(currentDir.getAbsolutePath() + "/src/test/resources/218116(14).txt");
        LOGGER.info("dataFile={}", dataFile.getAbsolutePath());
        final List<LongFaceVector> vectors = factory.getInstance(dataFile);
        Assert.assertNotNull(vectors);
        Assert.assertFalse(vectors.isEmpty());
        Assert.assertEquals(vectors.get(0).getLength(), 512);
    }

    @DataProvider
    private Object[][] testCreateVector() {
        return new Object[][]{
                {
                        "-0.049911,-0.0057704,-0.0235393,0.0078325,-0.0027073,-0.04085,0.019478,0.0279391,0.0320073,0.0477408",
                        10,
                        new long[]{-49911000l, -5770400l, -23539300l, 7832500l, -2707300l, -40850000l, 19478000l, 27939100l, 32007300l, 47740800l}
                },
                {
                        "0.0436645,0.0107457,0.0298183,-0.0088567,-0.1005214,-0.0045564,-0.0036799,0.0407416,-0.0747393,-0.0276896",
                        10,
                        new long[]{43664500, 10745700, 29818300, -8856700, -100521400, -4556400, -3679900, 40741600, -74739300, -27689600}
                }
        };
    }

    @Test(dataProvider = "testCreateVector")
    public void testCreateVector(String str, int len, long[] expectedValues) {
        Assert.assertEquals(expectedValues.length, len, "Test configuration error: len and expectedValues.length must be equal");
        final LongFaceVectorFactory factory = new LongFaceVectorFactory(len);
        final LongFaceVector vector = factory.getInstance(str);
        Assert.assertEquals(vector.getLength(), expectedValues.length);
        LOGGER.info("testCreateVector: result=[{}]",
                Arrays.asList(vector.getData())
                        .stream()
                        .map(i -> "" + i)
                        .collect(Collectors.joining(", "))
        );
        for (int i = 0; i < len; i++) {
            Assert.assertEquals(vector.getData()[i].longValue(), expectedValues[i]);
        }
    }

    @DataProvider
    private Object[][] testSimpleFormatConvert() {
        return new Object[][]{
                {"0.0", 0l},
                {"-0.0", 0l},
                {"0.07", 70000000l},
                {"-0.07", -70000000l},
                {"0.12233453", 122334530l},
                {"-0.12233453", -122334530l},
                {"0.123456789", 123456789l},
                {"-0.123456789", -123456789l},
                {"-0.1234567876", -123456787l},
                {"0.1", 100000000l},
                {"-0.1", -100000000l},
                {"0.01", 10000000l},
                {"0.001", 1000000l},
                {"0.0001", 100000l},
                {"0.00001", 10000l},
                {"0.000001", 1000l},
                {"0.0000001", 100l},
                {"0.00000001", 10l},
                {"0.000000001", 1l},
                {"0.0000000017", 1l},
                {"0.0000000001", 0l},
        };
    }

    @Test(dataProvider = "testSimpleFormatConvert")
    public void testSimpleFormatConvert(String str, long expected) {
        Assert.assertEquals(LongFaceVectorFactory.simpleFormatConvert(str), expected, "Conversion error: '" + str + "'");
    }

    @DataProvider
    private Object[][] testScienceFormatConvert() {
        return new Object[][]{
                {"-9.123456789e-01", -912345678l},
                {"-9.123456e-06", -9123l},
                {"-9.12345e-06", -9123l},
                {"-9.123456789e-07", -912l},
                {"-2e-07", -200l},
                {"1.0e-22", 0l},
                {"-9.04e-01", -904000000l},
                {"9.04e-01", 904000000l},
                {"-9.04e-02", -90400000l},
                {"9.04e-02", 90400000l},
                {"-9.04e-03", -9040000l},
                {"9.04e-03", 9040000l},
                {"-9.04e-04", -904000l},
                {"9.04e-04", 904000l},
                {"-9.04e-05", -90400l},
                {"9.04e-05", 90400l},
                {"-9.04e-06", -9040l},
                {"9.04e-06", 9040l},
                {"-9.04e-07", -904l},
                {"9.04e-07", 904l},
                {"-9.04e-08", -90l},
                {"9.04e-08", 90l},
                {"-9.04e-09", -9l},
                {"9.04e-09", 9l},
                {"-9.04e-10", 0l},
                {"9.04e-10", 0l}
        };
    }

    @Test(dataProvider = "testScienceFormatConvert")
    public void testScienceFormatConvert(String str, long expected) {
        final Long result = LongFaceVectorFactory.scienceFormatConvert(str);
        Assert.assertEquals(LongFaceVectorFactory.scienceFormatConvert(str), expected, "Conversion error: '" + str + "'");
        LOGGER.info("testCreateVector: result={}", result);
    }

    @Test
    public void testConvert() {
        for(Object[] obj : testSimpleFormatConvert()) {
            final String str = (String)obj[0];
            final Long expected = (Long)obj[1];
            Assert.assertEquals(LongFaceVectorFactory.convert(str), expected.longValue(), "Conversion error: '" + str + "'");
        }
        for(Object[] obj : testScienceFormatConvert()) {
            final String str = (String)obj[0];
            final Long expected = (Long)obj[1];
            Assert.assertEquals(LongFaceVectorFactory.convert(str), expected.longValue(), "Conversion error: '" + str + "'");
        }
    }
}

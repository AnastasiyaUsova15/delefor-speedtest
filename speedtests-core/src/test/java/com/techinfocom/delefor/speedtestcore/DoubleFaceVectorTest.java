package com.techinfocom.delefor.speedtestcore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DoubleFaceVectorTest {
    private static Logger LOGGER = LoggerFactory.getLogger(DoubleFaceVectorFactoryTest.class);

    @DataProvider
    private Object[][] testEuclidDistance() {
        return new Object[][] {
                {
                        "-0.049911,-0.0057704,-0.0235393,0.0078325,-0.0027073,-0.04085,0.019478,0.0279391,0.0320073,0.0477408",
                        "0.0436645,0.0107457,0.0298183,-0.0088567,-0.1005214,-0.0045564,-0.0036799,0.0407416,-0.0747393,-0.0276896",
                        0.0408243, 0.0000001
                },
                {
                        "-0.049911,-0.0057704,-0.0235393,0.0078325,-0.0027073,-0.04085,0.019478,0.0279391,0.0320073,0.0477408",
                        "-0.049911,-0.0057704,-0.0235393,0.0078325,-0.0027073,-0.04085,0.019478,0.0279391,0.0320073,0.0477408",
                        0.0, 0.0
                },
        };
    }

    @Test (dataProvider = "testEuclidDistance")
    public void testEuclidDistance (String v1, String v2, double expectedResult1, double delta) {
        final DoubleFaceVectorFactory factory = new DoubleFaceVectorFactory(10);
        final DoubleFaceVector vector1 = factory.getInstance(v1);
        final DoubleFaceVector vector2 = factory.getInstance(v2);
        final double result = vector1.euclidDistance(vector2);
        LOGGER.info("testEuclidDistance: result={} expectedResult1={} delta={}", result, expectedResult1, delta);
        Assert.assertEquals(result, expectedResult1, delta);
    }

    @DataProvider
    private Object[][] testEuclidDistanceLimit() {
        return new Object[][] {
                {
                        "-0.049911,-0.0057704,-0.0235393,0.0078325,-0.0027073,-0.04085,0.019478,0.0279391,0.0320073,0.0477408",
                        "0.0436645,0.0107457,0.0298183,-0.0088567,-0.1005214,-0.0045564,-0.0036799,0.0407416,-0.0747393,-0.0276896",
                        0.01
                },
                {
                        "-0.049911,-0.0057704,-0.0235393,0.0078325,-0.0027073,-0.04085,0.019478,0.0279391,0.0320073,0.0477408",
                        "0.0436645,0.0107457,0.0298183,-0.0088567,-0.1005214,-0.0045564,-0.0036799,0.0407416,-0.0747393,-0.0276896",
                        0.000001
                },
        };
    }

    @Test (dataProvider = "testEuclidDistanceLimit")
    public void testEuclidDistanceLimit (String v1, String v2, double limit) {
        final DoubleFaceVectorFactory factory = new DoubleFaceVectorFactory(10);
        final DoubleFaceVector vector1 = factory.getInstance(v1);
        final DoubleFaceVector vector2 = factory.getInstance(v2);
        final Double result = vector1.euclidDistance(vector2, limit);
        LOGGER.info("testEuclidDistance: result={}", result);
        Assert.assertNull(result);
    }
}


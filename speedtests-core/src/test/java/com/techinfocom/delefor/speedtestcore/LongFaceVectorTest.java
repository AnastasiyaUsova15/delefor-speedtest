package com.techinfocom.delefor.speedtestcore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LongFaceVectorTest {
    private static Logger LOGGER = LoggerFactory.getLogger(LongFaceVectorFactoryTest.class);

    @DataProvider
    private Object[][] testEuclidDistance() {
        return new Object[][] {
                {
                        "-0.049911,-0.0057704,-0.0235393,0.0078325,-0.0027073,-0.04085,0.019478,0.0279391,0.0320073,0.0477408",
                        "0.0436645,0.0107457,0.0298183,-0.0088567,-0.1005214,-0.0045564,-0.0036799,0.0407416,-0.0747393,-0.0276896",
                        40824316388010000l
                },
                {
                        "-0.049911,-0.0057704,-0.0235393,0.0078325,-0.0027073,-0.04085,0.019478,0.0279391,0.0320073,0.0477408",
                        "-0.049911,-0.0057704,-0.0235393,0.0078325,-0.0027073,-0.04085,0.019478,0.0279391,0.0320073,0.0477408",
                        0l
                }
        };
    }

    @Test(dataProvider = "testEuclidDistance")
    public void testEuclidDistance (String v1, String v2, long expectedResult) {
        final LongFaceVectorFactory factory = new LongFaceVectorFactory(10);
        final LongFaceVector vector1 = factory.getInstance(v1);
        final LongFaceVector vector2 = factory.getInstance(v2);
        final long result = vector1.euclidDistance(vector2);
        LOGGER.info("testErrorEuclidDistance: result={} expectedResult={}", result, expectedResult);
        Assert.assertEquals(result, expectedResult);
    }

    @DataProvider
    private Object[][] testEuclidDistanceLimit() {
        return new Object[][] {
                {
                        "-0.049911,-0.0057704,-0.0235393,0.0078325,-0.0027073,-0.04085,0.019478,0.0279391,0.0320073,0.0477408",
                        "0.0436645,0.0107457,0.0298183,-0.0088567,-0.1005214,-0.0045564,-0.0036799,0.0407416,-0.0747393,-0.0276896",
                        10000000l
                },
                {
                        "-0.049911,-0.0057704,-0.0235393,0.0078325,-0.0027073,-0.04085,0.019478,0.0279391,0.0320073,0.0477408",
                        "0.0436645,0.0107457,0.0298183,-0.0088567,-0.1005214,-0.0045564,-0.0036799,0.0407416,-0.0747393,-0.0276896",
                        100000000000l
                },
        };
    }

    @Test(dataProvider = "testEuclidDistanceLimit")
    public void testEuclidDistanceLimit (String v1, String v2, long limit) {
        final LongFaceVectorFactory factory = new LongFaceVectorFactory(10);
        final LongFaceVector vector1 = factory.getInstance(v1);
        final LongFaceVector vector2 = factory.getInstance(v2);
        final Long result = vector1.euclidDistance(vector2, limit);
        LOGGER.info("testEuclidDistance: result={}", result);
        //Assert.assertEquals(result, 0l);
        Assert.assertNull(result);

    }
}

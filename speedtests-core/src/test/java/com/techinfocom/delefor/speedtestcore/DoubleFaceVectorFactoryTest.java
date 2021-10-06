package com.techinfocom.delefor.speedtestcore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class DoubleFaceVectorFactoryTest {
    private static Logger LOGGER = LoggerFactory.getLogger(DoubleFaceVectorFactoryTest.class);

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "^We found 3 vector parts instead of expected 512 for .*")
    public void testErrorString() {
        final DoubleFaceVectorFactory factory = new DoubleFaceVectorFactory(512);
        final DoubleFaceVector vector = factory.getInstance("-0.049911,-0.0057704,-0.0235393");
        Assert.fail("We expect an exception here");
    }

    @Test
    public void testCreateFromString() {
        final DoubleFaceVectorFactory factory = new DoubleFaceVectorFactory(3);
        final DoubleFaceVector vector = factory.getInstance("-0.049911,-0.0057704,-0.0235393");
        Assert.assertNotNull(vector);
        Assert.assertEquals(vector.getLength(), 3);
    }

    @Test
    public void testCreateFromFile() {
        final DoubleFaceVectorFactory factory = new DoubleFaceVectorFactory(512);
        final File currentDir = new File(".");
        LOGGER.info("currentDir={}", currentDir.getAbsolutePath());
        final File dataFile = new File(currentDir.getAbsolutePath() + "/src/test/resources/218116.txt");
        LOGGER.info("dataFile={}", dataFile.getAbsolutePath());
        final List<DoubleFaceVector> vectors = factory.getInstance(dataFile);
        Assert.assertNotNull(vectors);
        Assert.assertFalse(vectors.isEmpty());
        Assert.assertEquals(vectors.get(0).getLength(), 512);
    }
}
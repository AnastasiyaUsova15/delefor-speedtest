package com.techinfocom.delefor.speedtestcore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DoubleFaceVectorFactory {
    private final int vectorLength;

    public DoubleFaceVectorFactory(int vectorLength) {
        this.vectorLength = vectorLength;
    }

    public DoubleFaceVector getInstance(String line) {
        if (line == null) {
            throw new IllegalArgumentException("line can not be null");
        }

        final String[] parts = line.split(",");
        if (parts.length != vectorLength) {
            throw new IllegalStateException("We found " + parts.length + " vector parts instead of expected " + vectorLength + " for " + line);
        }
        final double[] data = new double[vectorLength];

        for (int i = 0; i < vectorLength; i++) {
            data[i] = Double.parseDouble(parts[i]);
        }
        return new DoubleFaceVector(data);
    }

    public List<DoubleFaceVector> getInstance(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file can not be null");
        }

        if (!file.isFile()) {
            //This file is not a directory.
        }

        if (!file.canRead()) {
            //The program has permission to read this file.
        }

        final List<DoubleFaceVector> result = new ArrayList<>();
        try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
            final String[] strs = reader.lines().collect(Collectors.joining("|")).split("\\|");
            for(String line : strs) {
                result.add(getInstance(line));
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }
}


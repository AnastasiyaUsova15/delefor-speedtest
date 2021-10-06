package com.techinfocom.delefor.speedtestcore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LongFaceVectorFactory {
    private final int vectorLength;

    public LongFaceVectorFactory(int vectorLength) {
        this.vectorLength = vectorLength;
    }

    public LongFaceVector getInstance(String line) {
        if (line == null) {
            throw new IllegalArgumentException("line can not be null");
        }

        final String[] parts = line.split(",");
        if (parts.length != vectorLength) {
            throw new IllegalStateException("We found " + parts.length + " vector parts instead of expected " + vectorLength + " for " + line);
        }
        final long[] data = new long[vectorLength];

        for (int i = 0; i < vectorLength; i++) {
            data[i] = LongFaceVectorFactory.convert(parts[i]);
        }
        return new LongFaceVector(data);
    }

    public List<LongFaceVector> getInstance(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file can not be null");
        }

        if (!file.isFile()) {
            //This file is not a directory.
        }

        if (!file.canRead()) {
            //The program has permission to read this file.
        }

        final List<LongFaceVector> result = new ArrayList<>();
        try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
            final String[] strs = reader.lines().collect(Collectors.joining("|")).split("\\|");
            for (String line : strs) {
                result.add(getInstance(line));
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    static long simpleFormatConvert(String str) {
        final boolean isNegative;
        if (str.startsWith("-")) {
            isNegative = true;
            str = str.replaceFirst("-", "");
        } else {
            isNegative = false;
        }
        str = str.replaceFirst("0\\.", "");

        int pow = 8;
        if (str.length()>pow +1) {
            str = str.substring(0, pow+1);
        }

        while (str.startsWith("0")) {
            pow--;
            str = str.replaceFirst("0", "");
        }
        final long result;
        if (str.length() == 0) {
            result = 0l;
        } else {
            result = Long.parseLong(str) * pow(pow - str.length() + 1) * (isNegative ? -1l : 1);
        }
        return result;
    }

    static long scienceFormatConvert(String str) {
        long result = 0;

        final boolean isNegative;
        if (str.startsWith("-")) {
            isNegative = true;
            str = str.replaceFirst("-", "");
        } else {
            isNegative = false;
        }

        String[] Chars = str.split("e");
        Chars[1] = Chars[1].substring(1, Chars[1].length());
        Chars[0] = Chars[0].replace(".", "");
        int pow = 8;
        int deg = Integer.parseInt(Chars[1]);
        if (Chars[0].length() > pow) {
            Chars[0] = Chars[0].substring(0, pow + 1);
        }
        if (pow - deg <= 0) {
            if (pow - deg >= -1) {
                Chars[0] = Chars[0].substring(0, Chars[0].length() - (deg - pow + 1));
                result = Long.parseLong(Chars[0]) * pow(pow - (deg - 1) - (Chars[0].length() - 1)) * (isNegative ? -1l : 1);
            }
            else result = 0l;
        } else {
            if (Chars[0].length() + deg > 10){
                Chars[0] = Chars[0].substring(0, pow - deg + 2);
            }
            result = Long.parseLong(Chars[0]) * pow(pow - (deg - 1) - (Chars[0].length() - 1)) * (isNegative ? -1l : 1);
        }
        return result;
    }

    static long convert(String str) {
        if (str.matches("-?0\\.[0-9]*")) {
            return simpleFormatConvert(str);
        } else if (str.matches("-?[1-9]\\.?[0-9]*e-[0-9]*")) {
            return scienceFormatConvert(str);
        } else {
            throw new IllegalArgumentException("Value '" + str + "' can not be parsed");
        }
    }

    static long pow(int pow) {
        switch (pow) {
            case 0:
                return 1l;
            case 1:
                return 10l;
            case 2:
                return 100l;
            case 3:
                return 1000l;
            case 4:
                return 10000l;
            case 5:
                return 100000l;
            case 6:
                return 1000000l;
            case 7:
                return 10000000l;
            case 8:
                return 100000000l;
             default:
                throw new IllegalArgumentException("Illegal pow: " + pow);
        }
    }
}

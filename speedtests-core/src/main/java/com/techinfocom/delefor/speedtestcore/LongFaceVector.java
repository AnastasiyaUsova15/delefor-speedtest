package com.techinfocom.delefor.speedtestcore;

/**
 * This interface publishes functions to be implemented in Face vectors
 */
public class LongFaceVector implements FaceVector<Long> {
    private final Long[] data;

    LongFaceVector(long[] data) {
        this.data = new Long[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = Long.valueOf(data[i]);
        }
    }

    LongFaceVector(Long[] data) {
        this.data = new Long[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = Long.valueOf(data[i]);
        }
    }

    public int getLength() {
        return data.length;
    }

    @Override
    public Long euclidDistance(FaceVector<Long> another) {
        if (another.getData().length != data.length) {
            throw new IllegalStateException("data size mismatch: expected vector length is " + another.getData().length);
        }

        long result = 0;
        for (int i = 0; i < data.length; i++) {
            final long delta = data[i] - another.getData()[i];
            result += delta * delta;
        }
        return result;
    }

    @Override
    public Long euclidDistance(FaceVector<Long> another, Long limit) {

        if (another.getData().length != data.length) {
            throw new IllegalStateException("data size mismatch: expected vector length is " + another.getData().length);
        }

        long result = 0;
        for (int i = 0;i < data.length; i++) {
            final long delta = data[i] - another.getData()[i];
            result += delta * delta;

            if (result > limit) {
                return null;
            }
        }
        return result;
    }

    @Override
    public Long[] getData() { return data; }

}
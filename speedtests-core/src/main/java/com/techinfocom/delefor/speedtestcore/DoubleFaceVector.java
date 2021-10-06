package com.techinfocom.delefor.speedtestcore;

/**
 * This interface publishes functions to be implemented in Face vectors
 */
public class DoubleFaceVector implements FaceVector<Double> {
    private final Double[] data;

    DoubleFaceVector(double[] data) {
        this.data = new Double[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = Double.valueOf(data[i]);
        }
    }

    DoubleFaceVector(Double[] data) {
        this.data = new Double[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = Double.valueOf(data[i]);
        }
    }

    public int getLength() {
        return data.length;
    }

    @Override
    public Double euclidDistance(FaceVector<Double> another) {
        if (another.getData().length != data.length) {
            throw new IllegalStateException("data size mismatch: expected vector length is " + another.getData().length);
        }

        double result = 0.0;
        for (int i = 0; i < data.length; i++) {
            final double delta = data[i] - another.getData()[i];
            result += delta * delta;
        }
        return result;
    }

    @Override
    public Double euclidDistance(FaceVector<Double> another, Double limit) {
        if (another.getData().length != data.length) {
            throw new IllegalStateException("data size mismatch: expected vector length is " + another.getData().length);
        }

        double result = 0.0;
        for (int i = 0; i < data.length; i++) {
            final double delta = data[i] - another.getData()[i];
            result += delta * delta;

            if (result > limit) {
                return null;
            }
        }
        return result;
    }

    @Override
    public Double[] getData() {
        return data;
    }

}

package com.techinfocom.delefor.speedtestcore;

public interface FaceVector<T extends Number> {
    /**
     * This function calculates a distance between this vector and a given one.
     * The distance is calculated using Euclid metrics
     *
     * @param another the FaceVector the distance is calculated to.
     * @return full Euclid distance
     */
    T euclidDistance(FaceVector<T> another);

    /**
     * This function calculates a distance between this vector and a given one.
     * The distance is calculated using limited Euclid metrics
     *
     * @param another the FaceVector the distance is calculated to.
     * @param limit   the maximal distance the system takes info account. Once the distance is more than the
     *                value the system stops a calculation and exits.
     * @return full Euclid distance or null to show the limit is reached.
     */

    T euclidDistance(FaceVector<T> another, T limit);

    T[] getData();
}

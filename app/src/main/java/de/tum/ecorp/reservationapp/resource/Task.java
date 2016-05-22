package de.tum.ecorp.reservationapp.resource;

public interface Task<ResultType> {
    void before();

    void handleResult(ResultType result);
}

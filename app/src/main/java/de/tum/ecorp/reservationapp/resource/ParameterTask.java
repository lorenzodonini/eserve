package de.tum.ecorp.reservationapp.resource;

public abstract class ParameterTask<ParameterType, ResultType> implements Task<ResultType> {

    private ParameterType[] parameters;

    public ParameterTask(ParameterType... parameters) {
        this.parameters = parameters;
    }

    public ParameterType[] getParameters() {
        return parameters;
    }
}

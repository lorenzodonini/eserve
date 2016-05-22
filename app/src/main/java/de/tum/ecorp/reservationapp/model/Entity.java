package de.tum.ecorp.reservationapp.model;

/**
 * Created by felix on 22/05/16.
 */
public abstract class Entity {

    private static Long idCounter = Long.valueOf(1);

    protected final Long id;

    public Entity() {
        id = idCounter++;
    }

    public Long getId() {
        return id;
    }
}

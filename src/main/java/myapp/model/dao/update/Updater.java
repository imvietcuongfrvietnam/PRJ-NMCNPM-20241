package myapp.model.dao.update;

import myapp.model.entities.entitiesdb.UserInformation;
import myapp.model.entities.entitiessystem.UserCredentials;

public interface Updater<T> {
    void update(T entity);

    void update(UserInformation entity, UserCredentials userCredentials);
}

package edu.school21.repositories;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface OrmRepository {
    void save(Object entity) throws SQLException, IllegalAccessException;

    void update(Object entity) throws SQLException, IllegalAccessException;

    <T> T findById(Long id, Class<T> aClass) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

}

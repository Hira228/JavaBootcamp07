package edu.school21.managers;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;
import edu.school21.repositories.OrmRepository;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Optional;

public class OrmManager implements OrmRepository {
    DataSource dataSource;

    public OrmManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Object entity) throws SQLException, IllegalAccessException {
        StringBuilder sqlQuery = new StringBuilder();
        try (Connection connection = dataSource.getConnection()) {
            if(entity != null) {
                Field[] fields = entity.getClass().getDeclaredFields();
                OrmEntity ormEntity = entity.getClass().getAnnotation(OrmEntity.class);
                sqlQuery.append("INSERT INTO ").append(ormEntity.table()).append(" (");
                for(Field field : fields) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    if(ormColumn != null) {
                        sqlQuery.append(ormColumn.name()).append(",");
                    }

                }
                sqlQuery.setLength(sqlQuery.length() - 1);
                sqlQuery.append(") VALUES (");
                for(Field field : fields) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    if(ormColumn != null) {
                        field.setAccessible(true);
                        Object o = field.get(entity);
                        if(o != null) {
                            if(o instanceof String) {
                                sqlQuery.append("'").append(o).append("',");
                            }
                            else {
                                sqlQuery.append(o).append(",");
                            }
                        } else sqlQuery.append("null,");
                    }
                }
                sqlQuery.setLength(sqlQuery.length() - 1);
                sqlQuery.append(")");
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.toString());
                preparedStatement.executeUpdate();
            }
        }
    }

    public void update(Object entity) throws SQLException, IllegalAccessException {
        StringBuilder sqlQuery = new StringBuilder();
        StringBuilder idString = new StringBuilder();
        try (Connection connection = dataSource.getConnection()){
            if(entity != null) {
                OrmEntity ormEntity = entity.getClass().getAnnotation(OrmEntity.class);
                sqlQuery.append("UPDATE ").append(ormEntity.table()).append(" SET ");
                Field[] fields = entity.getClass().getDeclaredFields();
                for (Field field : fields) {
                    OrmColumnId ormColumnId = field.getAnnotation(OrmColumnId.class);
                    if(ormColumnId != null){
                        field.setAccessible(true);
                        idString.append(field.getName()).append(" = ").append(field.get(entity).toString());
                    }
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    if(ormColumn != null) {
                        sqlQuery.append(ormColumn.name()).append(" = ");
                        field.setAccessible(true);
                        Object o = field.get(entity);
                        if(o != null) {
                            if(o instanceof String) {
                                sqlQuery.append("'").append(o).append("'").append(",");
                            }else sqlQuery.append(o).append(",");
                        }
                    }
                }
                sqlQuery.setLength(sqlQuery.length() - 1);

                sqlQuery.append(" WHERE ").append(idString);
                System.out.println(sqlQuery);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.toString());
                preparedStatement.executeUpdate();
            }

        }
    }

    public <T> T findById(Long id, Class<T> aClass) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T instance = null;
        StringBuilder sqQuery = new StringBuilder();

        OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
        Field[] fields = aClass.getDeclaredFields();
        sqQuery.append("SELECT * FROM ").append(ormEntity.table()).append(" WHERE id = ").append(id);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqQuery.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet);
                instance = aClass.getConstructor().newInstance();
                for(Field field : fields) {
                    Annotation[] annotations = field.getAnnotations();
                    for(Annotation annotation : annotations) {
                        if(annotation instanceof OrmColumn) {
                            field.setAccessible(true);
                            field.set(instance, resultSet.getObject(((OrmColumn) annotation).name()));
                        }
                        if(annotation instanceof OrmColumnId) {
                            field.setAccessible(true);
                            field.set(instance, resultSet.getObject("id"));
                        }
                    }
                }
            }

        }
        return instance;
    }

}

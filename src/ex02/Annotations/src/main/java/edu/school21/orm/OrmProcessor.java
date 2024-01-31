package edu.school21.orm;

import com.google.auto.service.AutoService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("edu.school21.annotations.*")
@AutoService(Processor.class) public class OrmProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        HikariConfig config = new HikariConfig("/hikari.properties");
        StringBuilder dropTable = new StringBuilder();
        HikariDataSource dataSource = new HikariDataSource(config);
        try (Connection connection = dataSource.getConnection()){
            StringBuilder sqlQuery = new StringBuilder();
            for(Element element : roundEnv.getElementsAnnotatedWith(OrmEntity.class)){
                dropTable.append("drop table if exists ").append(element.getAnnotation(OrmEntity.class).table());
                PreparedStatement preparedStatement = connection.prepareStatement(dropTable.toString());
                preparedStatement.executeUpdate();
                sqlQuery.append("CREATE TABLE ").append(element.getAnnotation(OrmEntity.class).table()).append(" (");
                List<? extends Element> enclosedElements = element.getEnclosedElements();
                for(Element element1 : enclosedElements) {
                    OrmColumnId ormColumnId = element1.getAnnotation(OrmColumnId.class);
                    if(ormColumnId != null) {
                        sqlQuery.append("id serial primary key,");
                    }
                    OrmColumn ormColumn = element1.getAnnotation(OrmColumn.class);
                    if (ormColumn != null ) {
                        sqlQuery.append(ormColumn.name() + " ");
                        if (element1.asType().toString().equals(String.class.getName())) {
                            sqlQuery.append("VARCHAR(" + ormColumn.length() + "),\n");
                        }
                        if (element1.asType().toString().equals(Integer.class.getName())) {
                            sqlQuery.append("Integer,\n");
                        }
                        if (element1.asType().toString().equals(Long.class.getName())) {
                            sqlQuery.append("Numeric,\n");
                        }
                        if (element1.asType().toString().equals(Boolean.class.getName())) {
                            sqlQuery.append("Boolean,\n");
                        }
                        if (element1.asType().toString().equals(Double.class.getName())) {
                            sqlQuery.append("Real,\n");
                        }
                }
                }
                sqlQuery.setLength(sqlQuery.length() - 2);
                sqlQuery.append(")");
                System.out.println(preparedStatement);
                PreparedStatement prepared = connection.prepareStatement(sqlQuery.toString());
                System.out.println(prepared);
                prepared.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}

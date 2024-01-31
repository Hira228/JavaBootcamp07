package edu.school21.program;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.employee.User;
import edu.school21.managers.OrmManager;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Program {
    public static void main(String[] args) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        HikariConfig hikariConfig = new HikariConfig("/hikari.properties");
        DataSource dataSource = new HikariDataSource(hikariConfig);
        OrmManager ormManager = new OrmManager(dataSource);
        User user = ormManager.findById(2L, User.class);
        user.setAge(23);
        user.setFirstName("antoinco");
        user.setLastName("lala");
        ormManager.update(user);
//        System.out.println(user);

    }
}

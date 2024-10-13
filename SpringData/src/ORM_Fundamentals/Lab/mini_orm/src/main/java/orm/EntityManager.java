package orm;

import orm.Annotations.Colum;
import orm.Annotations.Entity;
import orm.Annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EntityManager<E> implements DatabaseContext <E> {
    private static final String INSERT_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String UPDATE_WITH_WHERE_TEMPLATE = "UPDATE %s SET %s WHERE %s";
    private static final String ID_CONDITION = "%s=%s";

    private static final String SELECT_WITH_WHERE_PLACEHOLDER_TEMPLATE = "SELECT %s FROM %s %s";

    private final Connection connection;

    //constructor
    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {
        Field idColumn = getIdColumn(entity);
        idColumn.setAccessible(true);
        Object idValue = idColumn.get(entity);

        if (idValue == null || (long) idValue == 0) {
            return doInsert(entity);
        }

        return doUpdate(entity, idColumn, idValue);
    }

    private boolean doUpdate(E entity, Field idColumn, Object idValue) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity);
        List<String> columns = getColumnsWithoutId(entity);
        List<String> values = getColumnValuesWithoutId(entity);

        List<String> columnsWithValues = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            String s = columns.get(i) + "=" + values.get(i);

            columnsWithValues.add(s);
        }

        String idCondition = String.format(ID_CONDITION, idColumn.getName(), idValue.toString());

        String updateQuery = String.format(UPDATE_WITH_WHERE_TEMPLATE,
                tableName,
                String.join(",", columnsWithValues),
                idCondition);

        PreparedStatement statement = connection.prepareStatement(updateQuery);
        int updateCount = statement.executeUpdate();

        return updateCount == 1;
    }

    private boolean doInsert(E entity) throws IllegalAccessException, SQLException {
        // Generate INSERT
        // Get table name
        // Collect column without ID
        // Collect values without ID
        String tableName = getTableName(entity);
        List<String> columnsList = getColumnsWithoutId(entity);
        List<String> values = getColumnValuesWithoutId(entity);

        String formattedInsert = String.format(INSERT_TEMPLATE,
                tableName,
                String.join(",", columnsList),
                String.join(",", values));

        // Execute
        PreparedStatement statement = connection.prepareStatement(formattedInsert);
        int changedRows = statement.executeUpdate();

        // Parse result
        return changedRows == 1;
    }



    @Override
    public Iterable<E> find(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return find(table, null);
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return baseFind(table, where, null);
    }

    @Override
    public E findFirst(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return findFirst(table, null);
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<E> result = baseFind(table, where, 1);

        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }


    private List<E> baseFind(Class<E> table, String where, Integer limit) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String fieldList = "*";
        String tableName = getTableName(table);
        String whereClause = where == null ? "" : "WHERE " + where;
        String limitClause = limit == null ? "" : " LIMIT " + limit;

        String selectStatement = String.format(SELECT_WITH_WHERE_PLACEHOLDER_TEMPLATE,
                fieldList,
                tableName,
                whereClause + limitClause);

        PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<E> result = new ArrayList<>();

        while (resultSet.next()) {
            E current = generateEntity(table, resultSet);
            result.add(current);
        }

        return result;
    }

    private E generateEntity(Class<E> table, ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        // Create object
        E result = table.getDeclaredConstructor().newInstance(); // new User(); new E();

        // Fill object with data
        Field[] declaredFields = table.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            fillField(result, declaredField, resultSet);
        }

        return result;
    }

    private void fillField(E result, Field declaredField, ResultSet resultSet) throws SQLException, IllegalAccessException {
        declaredField.setAccessible(true);

        String dbFieldName = declaredField.getAnnotation(Colum.class).name();
        Class<?> javaType = declaredField.getType();

        if (javaType == int.class || javaType == Integer.class) {
            int value = resultSet.getInt(dbFieldName);
            declaredField.setInt(result, value);
        } else if (javaType == long.class || javaType == Long.class) {
            long value = resultSet.getLong(dbFieldName);
            declaredField.setLong(result, value);
        } else if (javaType == LocalDate.class) {
            LocalDate value = resultSet.getObject(dbFieldName, LocalDate.class);
            declaredField.set(result, value);
        } else if (javaType == String.class) {
            String value = resultSet.getString(dbFieldName);
            declaredField.set(result, value);
        } else {
            throw new RuntimeException("Unsupported type " + javaType);
        }
    }



    private Field getIdColumn(E entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                return declaredField;
            }
        }

        throw new RuntimeException("Entity has no Id column");
    }



    private String getTableName(E entity) {
        Entity annotation = entity.getClass().getAnnotation(Entity.class);

        if (annotation == null) {
            throw new RuntimeException("No Entity annotation present");
        }

        return annotation.name();
    }


    // FIXME: This is duplicated
    private String getTableName(Class<E> clazz) {
        Entity annotation = clazz.getAnnotation(Entity.class);

        if (annotation == null) {
            throw new RuntimeException("No Entity annotation present");
        }

        return annotation.name();
    }




    private List<String> getColumnsWithoutId(E entity) {
        List<String> result = new ArrayList<>();
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                continue;
            }

            Colum column = declaredField.getAnnotation(Colum.class);
            if (column == null) {
                continue;
            }

            result.add(column.name());
        }

        return result;
    }





    private List<String> getColumnValuesWithoutId(E entity) throws IllegalAccessException {
        List<String> result = new ArrayList<>();

        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                continue;
            }
            if (!declaredField.isAnnotationPresent(Colum.class)) {
                continue;
            }

            declaredField.setAccessible(true);
            Object fieldValue = declaredField.get(entity);

            result.add("'" + fieldValue.toString() + "'");
        }

        return result;
    }
}

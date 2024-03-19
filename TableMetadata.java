package classGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TableMetadata {

    public static List<Column> getTableColumns(String tableName) throws SQLException {
        List<Column> columns = new ArrayList<>();

        try (Connection connection = DatabaseConnection.connect()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, tableName, null);

             // Get primary key columns
            ResultSet primaryKeyResultSet = metaData.getPrimaryKeys(null, null, tableName);
            Set<String> primaryKeys = new HashSet<>();
            while (primaryKeyResultSet.next()) {
                primaryKeys.add(primaryKeyResultSet.getString("COLUMN_NAME"));
            }

             // Get foreign keys for the specified table
            ResultSet foreignKeyResultSet = metaData.getImportedKeys(null, null, tableName);
            // Map column names to their associated foreign table names
            Map<String, String> foreignKeyTableMap = new HashMap<>();
            while (foreignKeyResultSet.next()) {
                String columnName = foreignKeyResultSet.getString("FKCOLUMN_NAME");
                String foreignTableName = foreignKeyResultSet.getString("PKTABLE_NAME");
                foreignKeyTableMap.put(columnName, foreignTableName);
            }

            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                String dataType = resultSet.getString("TYPE_NAME");
                System.out.println(dataType);
                boolean isPrimaryKey = primaryKeys.contains(columnName);
                String foreignTableName = foreignKeyTableMap.get(columnName);
                if(foreignTableName!=null){
                    columns.add(new Column(columnName, dataType,isPrimaryKey,true,foreignTableName));
                }
                else{
                    columns.add(new Column(columnName, dataType,isPrimaryKey,false,null));
                }
            }
        }

        return columns;
    }
}
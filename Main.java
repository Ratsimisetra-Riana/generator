package classGenerator;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Prompt language
        System.out.print("select language: ");
        String language = scanner.nextLine();

        //Prompt table name
        System.out.print("Enter the table name: ");
        String tableName = scanner.nextLine();

        //Prompt package name
        System.out.print("Enter the package name: ");
        String packageName = scanner.nextLine();

        //Prompt imports
        System.out.print("Add import?(y/n): ");
        String response = scanner.nextLine();
        ArrayList<String> imports = new ArrayList<String>();
        if(response.equals("y")){
            String prompt = null;
            while(1==1){
                System.out.print("import: ");
                prompt=scanner.nextLine();
                if(prompt.equals("stop")){
                    break;
                }
                imports.add(prompt);
            }
        }
        
        //Generation
        try {
            List<Column> columns = TableMetadata.getTableColumns(tableName);
            TableClassGenerator.generateClass(tableName, columns,XmlManager.getLanguageProperties(language),packageName,imports);
            TableClassGenerator.generateRepository(tableName, columns,XmlManager.getLanguageProperties(language),packageName);
            TableClassGenerator.generateController(tableName, packageName,XmlManager.getLanguageProperties(language));
            VueGenerator.createForm(tableName,columns);
            VueGenerator.createList(tableName,columns);
            VueGenerator.createModifyForm(tableName,columns);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}


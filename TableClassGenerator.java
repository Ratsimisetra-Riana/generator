package classGenerator;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableClassGenerator {

    public static void generateClass(String tableName, List<Column> columns,Map<String,String> properties,String packageName,ArrayList<String> imports) {
        try{
            String s1 = tableName.substring(0, 1).toUpperCase();  // first letter = J  
            String s2 = tableName.substring(1);     // after 1st letter = avatpoint  
            String className = s1 + s2;

            FileWriter writer = new FileWriter("/home/riana/Documents/S5/Naina-FRAMEWORK/Class-Generator/Generated_files/models/"+className+properties.get("extension"));
            //package
            writer.write(properties.get("package")+" "+packageName+";\n\n");

            //necessary imports
            writer.write("import jakarta.persistence.Entity;\n" + //
                            "import jakarta.persistence.GeneratedValue;\n" + //
                            "import jakarta.persistence.GenerationType;\n" + //
                            "import jakarta.persistence.Id;\n");
            int i=0;

            for(i=0;i<columns.size();i++){
                if(columns.get(i).getIsForeignKey().equals(true)){
                    writer.write("import jakarta.persistence.JoinColumn;\nimport jakarta.persistence.ManyToOne;\n");
                }
            }
        

            //custom imports
            
            for(i=0;i<imports.size();i++){
                writer.write("import "+imports.get(i)+";\n");
            }

            //class\n    }\n\n
            // J + avatpoint
            writer.write("\n@Entity\npublic class " + className + " {\n\n");

            //attributes
            for (i=0;i<columns.size();i++) {
                if(columns.get(i).getIsPrimaryKey().equals(true)){
                    System.out.println(columns.get(i).getDataType());
                    writer.write("    @Id\n    @GeneratedValue(strategy = GenerationType.AUTO)\n    private " + properties.get(columns.get(i).getDataType()) + " " + columns.get(i).getColumnName() + ";\n");
                }
                else if(columns.get(i).getIsForeignKey().equals(true)){
                    String foreignclass1 = columns.get(i).getTableName().substring(0,1).toUpperCase();
                    String foreignclass2 = columns.get(i).getTableName().substring(1);
                    String foreignClassName = foreignclass1+foreignclass2;
                    writer.write("    @ManyToOne\n    @JoinColumn(name="+'"'+columns.get(i).getColumnName()+'"'+")\n    private "+foreignClassName+" "+columns.get(i).getTableName()+";\n");
                }
                else{
                    writer.write("    private " + properties.get(columns.get(i).getDataType()) + " " + columns.get(i).getColumnName() + ";\n");
                }
               
            }

            //constructor
            writer.write("\n    public "+className+"(){\n\n    }\n");
            //constructor + args
            writer.write("\n    public "+className+"(");
            for(i=0;i<columns.size();i++){
                if(i!=columns.size()-1){
                    if(columns.get(i).getIsForeignKey().equals(false)){
                        writer.write(properties.get(columns.get(i).getDataType())+" "+columns.get(i).getColumnName()+",");
                    }
                    else{
                        String foreignclass1 = columns.get(i).getTableName().substring(0,1).toUpperCase();
                        String foreignclass2 = columns.get(i).getTableName().substring(1);
                        String foreignClassName = foreignclass1+foreignclass2;
                        writer.write(foreignClassName+" "+columns.get(i).getTableName()+",");
                    }
                }
                else{
                    if(columns.get(i).getIsForeignKey().equals(false)){
                        writer.write(properties.get(columns.get(i).getDataType())+" "+columns.get(i).getColumnName()+"){\n");
                    }
                    else{
                        String foreignclass1 = columns.get(i).getTableName().substring(0,1).toUpperCase();
                        String foreignclass2 = columns.get(i).getTableName().substring(1);
                        String foreignClassName = foreignclass1+foreignclass2;
                        writer.write(foreignClassName+" "+columns.get(i).getTableName()+"){\n");
                    }
                }
            }
            for(i=0;i<columns.size();i++){
                if(i!=columns.size()-1){
                    if(columns.get(i).getIsForeignKey().equals(false)){
                        writer.write("        this."+columns.get(i).getColumnName()+" = "+columns.get(i).getColumnName()+";\n");
                    }
                    else{
                        writer.write("        this."+columns.get(i).getTableName()+" = "+columns.get(i).getTableName()+";\n");
                    }
                }
                else{
                    if(columns.get(i).getIsForeignKey().equals(false)){
                        writer.write("        this."+columns.get(i).getColumnName()+" = "+columns.get(i).getColumnName()+";\n    }\n\n");
                    }
                   else{
                        writer.write("        this."+columns.get(i).getTableName()+" = "+columns.get(i).getTableName()+";\n    }\n\n");
                   }
                }
            }
            

            // Add getters and setters here if needed
            for (i=0;i<columns.size();i++) {
                if(columns.get(i).getIsForeignKey().equals(false)){
                    String s3 = columns.get(i).getColumnName().substring(0, 1).toUpperCase();  // first letter = J  
                    String s4 = columns.get(i).getColumnName().substring(1);     // after 1st letter = avatpoint  
                    String columNameFUper = s3 + s4; // J + avatpoint
                    writer.write("    public " +"void "+"set"+columNameFUper +"("+properties.get(columns.get(i).getDataType())+" "+columns.get(i).getColumnName()+ "){\n        this."+columns.get(i).getColumnName()+" = "+columns.get(i).getColumnName()+";"+"\n    }\n\n");
                    writer.write("    public " +properties.get(columns.get(i).getDataType()) +" get"+columNameFUper +"(){\n        return this."+columns.get(i).getColumnName()+";"+"\n    }\n\n");
                }
                else{
                    String s3 = columns.get(i).getTableName().substring(0, 1).toUpperCase();  // first letter = J  
                    String s4 = columns.get(i).getTableName().substring(1);     // after 1st letter = avatpoint  
                    String tableNameFUper = s3 + s4; // J + avatpoint
                    writer.write("    public " +"void "+"set"+tableNameFUper +"("+tableNameFUper+" "+columns.get(i).getTableName()+ "){\n        this."+columns.get(i).getTableName()+" = "+columns.get(i).getTableName()+";"+"\n    }\n\n");
                    writer.write("    public " +tableNameFUper +" get"+tableNameFUper +"(){\n        return this."+columns.get(i).getTableName()+";"+"\n    }\n\n");
                }    
            }


            writer.write("}");
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void generateRepository(String tableName, List<Column> columns,Map<String,String> properties,String packageName) {
        try{
            String s1 = tableName.substring(0, 1).toUpperCase();  // first letter = J  
            String s2 = tableName.substring(1);     // after 1st letter = avatpoint  
            String className = s1 + s2;

            FileWriter writer = new FileWriter("/home/riana/Documents/S5/Naina-FRAMEWORK/Class-Generator/Generated_files/repositories/"+className+"Repository"+properties.get("extension"));
            //package
            writer.write(properties.get("package")+" "+packageName.replaceAll("models", "repositories")+";\n\n");

            //necessary imports
            writer.write("import org.springframework.data.jpa.repository.JpaRepository;\n"+
                            "import "+packageName+"."+className+";\n");

            //class
            // J + avatpoint
            for (int i=0;i<columns.size();i++) {
                if(columns.get(i).getDataType().equals("int4")||columns.get(i).getDataType().equals("serial")){
                    if(columns.get(i).getIsPrimaryKey().equals(true)){
                        writer.write("\npublic interface " + className +"Repository extends JpaRepository<"+className+","+"Integer"+">"+ " {\n\n}");
                    }
                }
                else{
                    if(columns.get(i).getIsPrimaryKey().equals(true)){
                        writer.write("\npublic interface " + className +"Repository extends JpaRepository<"+className+","+properties.get(columns.get(i).getDataType())+">"+ " {\n\n}");
                    }
                }
                
            }
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void generateController(String tableName,String packageName,Map<String,String> properties) {
        try{
            String s1 = tableName.substring(0, 1).toUpperCase();  // first letter = J  
            String s2 = tableName.substring(1);     // after 1st letter = avatpoint  
            String className = s1 + s2;

            FileWriter writer = new FileWriter("/home/riana/Documents/S5/Naina-FRAMEWORK/Class-Generator/Generated_files/controllers/"+className+"Controller"+properties.get("extension"));
            //package
            writer.write(properties.get("package")+" "+packageName.replaceAll("models", "controllers")+";\n\n");

            //necessary imports
            writer.write("import org.springframework.data.jpa.repository.JpaRepository;\n"+
                            "import "+packageName+"."+className+";\n"+
                            "import org.springframework.web.bind.annotation.GetMapping;\n"+
                            "import org.springframework.web.bind.annotation.DeleteMapping;\n"+
                            "import org.springframework.web.bind.annotation.PostMapping;\n"+
                            "import org.springframework.web.bind.annotation.PutMapping;\n"+
                            "import org.springframework.web.bind.annotation.RequestBody;\n"+
                            "import org.springframework.web.bind.annotation.RequestParam;\n"+
                            "import org.springframework.validation.annotation.Validated;\n"+
                            "import org.springframework.web.bind.annotation.RestController;\n"+
                            "import org.springframework.web.bind.annotation.PathVariable;\n"+
                            "import org.springframework.web.bind.annotation.CrossOrigin;\n" + //
                            "import java.util.List;\n"+
                            "import java.util.Optional;\n\n"+
                            "import "+packageName+"."+className+";\n"+
                            "import "+"com."+packageName.split("\\.")[1]+"."+"repositories."+className+"Repository;\n\n");

            //class
            // J + avatpoint
            writer.write("\n@CrossOrigin(origins ="+'"'+"*"+'"'+")"+"\n@RestController\npublic class " + className +"Controller"+" {\n\n");

            //attributes
            writer.write("    private final "+className+"Repository "+className.toLowerCase()+"Repository;\n\n");

            //constructor
            writer.write("    public "+className+"Controller("+className+"Repository "+className.toLowerCase()+"Repository) {\n"+
                            "       this."+className.toLowerCase()+"Repository = "+className.toLowerCase()+"Repository;\n    }\n\n");
            
            //CRUD Methods
            writer.write("    @GetMapping("+'"'+className.toLowerCase()+"s"+'"'+")\n"+
                        "    public List<"+className+"> findAll"+className+"() {\n"+
                        "        return this."+className.toLowerCase()+"Repository.findAll();\n"+
                        "    }\n\n");

            writer.write("    @GetMapping("+'"'+className.toLowerCase()+"s/{id}"+'"'+")\n"+
            "    public Optional<"+className+"> find"+className+"(@PathVariable("+'"'+"id"+'"'+") int id) {\n"+
            "        return this."+className.toLowerCase()+"Repository.findById(id);\n"+
            "    }\n\n");

            writer.write("    @PostMapping("+'"'+className.toLowerCase()+"s"+'"'+")\n"+
            "    public "+className+" save"+className+"(@Validated @RequestBody "+className+" "+className.toLowerCase()+") {\n"+
            "        return this."+className.toLowerCase()+"Repository.save("+className.toLowerCase()+");\n"+
            "    }\n\n");

            writer.write("    @PutMapping("+'"'+className.toLowerCase()+"s/{id}"+'"'+")\n"+
            "    public "+className+" update"+className+"(@PathVariable("+'"'+"id"+'"'+") int id, @RequestBody "+className+" "+className.toLowerCase()+") {\n"+
            "        return this."+className.toLowerCase()+"Repository.save("+className.toLowerCase()+");\n"+
            "    }\n\n");

            writer.write("    @DeleteMapping("+'"'+className.toLowerCase()+"s/{id}"+'"'+")\n"+
            "    public void"+" delete"+className+"(@PathVariable("+'"'+"id"+'"'+") int id) {\n"+
            "        this."+className.toLowerCase()+"Repository.deleteById(id);\n"+
            "    }\n\n");



            writer.write("}");
            
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

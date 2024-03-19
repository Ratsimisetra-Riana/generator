package classGenerator;

import java.io.FileWriter;
import java.util.List;

import classGenerator.Column;

public class VueGenerator {
    
    public VueGenerator(){

    }

    public static void createForm(String tableName, List<Column> columns) {
        try{
            String s1 = tableName.substring(0, 1).toUpperCase();  // first letter = J  
            String s2 = tableName.substring(1);     // after 1st letter = avatpoint  
            String className = s1 + s2;

            FileWriter writer = new FileWriter("/home/riana/Documents/S5/Naina-FRAMEWORK/Class-Generator/Generated_files/components/"+className+"Form.vue");
            
            ///script
            writer.write("<script setup>\n");

            //necessary imports
            writer.write("    import {onMounted , ref} from 'vue'\n    import router from '../router'\n\n");
            int i = 0;
            //states
            for (i=0;i<columns.size();i++) {
                if(columns.get(i).getIsPrimaryKey().equals(false)&&columns.get(i).getIsForeignKey().equals(false)){
                    writer.write("    var "+columns.get(i).getColumnName()+" = ref('')\n");
                }
                else if(columns.get(i).getIsForeignKey().equals(true)){
                    writer.write("    var "+columns.get(i).getTableName()+"s = ref([])\n"+
                    "    var selected_"+columns.get(i).getTableName()+" = ref({})\n");
                }
            }
            writer.write("\n");

            //inputs listeners
            String functionPrefix = "onChange";
            for (i=0;i<columns.size();i++) {
                if(columns.get(i).getIsPrimaryKey().equals(false)&&columns.get(i).getIsForeignKey().equals(false)){
                    String c1 =  columns.get(i).getColumnName().substring(0, 1).toUpperCase();
                    String c2 = columns.get(i).getColumnName().substring(1);
                    String columnMaj = c1+c2;
                    writer.write("    function "+functionPrefix+columnMaj+"(event){\n");
                    writer.write("        "+columns.get(i).getColumnName()+".value = event.target.value\n        console.log("+columns.get(i).getColumnName()+")\n    }\n\n");
                }
                else if(columns.get(i).getIsForeignKey().equals(true)){
                    writer.write("    function "+functionPrefix+"Selected"+columns.get(i).getTableName()+"(event){\n");
                    writer.write("        selected_"+columns.get(i).getTableName()+".value ="+columns.get(i).getTableName()+"s.value[event.target.value]\n        console.log(selected_"+columns.get(i).getTableName()+")\n    }\n\n");
                }
                
            }

            //fetching 
            for (i=0;i<columns.size();i++) {
                if(columns.get(i).getIsForeignKey().equals(true)){
                    String c1 =  columns.get(i).getTableName().substring(0, 1).toUpperCase();
                    String c2 = columns.get(i).getTableName().substring(1);
                    String tableNameMaj = c1+c2;
                    writer.write("    async function fetch"+tableNameMaj+"s(){\n"+
                    "        const url = 'http://localhost:8080/"+columns.get(i).getTableName()+"s';\n"+
                    "        console.log(url);\n\n"+
                    "        try{\n"+
                    "            const response = await fetch(url,{\n"+
                    "                method : 'GET',\n"+
                    "                headers : {\n"+
                    "                    'Content-Type' : 'application/json'\n"+
                    "                }\n"+
                    "            });\n");
                    writer.write("            const data = await response.json();\n");
                    writer.write("            console.log(data);\n");
                    writer.write("            "+columns.get(i).getTableName()+"s.value = data;"+"\n");
                    writer.write("            console.log("+columns.get(i).getTableName()+"s.value)"+"\n");
                    writer.write("        }catch(err){\n");
                    writer.write("            console.log(err.message);\n        }\n    }\n\n");
                }
                
            }

            //submit
            writer.write("    async function onSubmit(e){\n"+
            "        e.preventDefault();\n"+
            "        const url = 'http://localhost:8080/"+tableName+"s';\n"+
            "        console.log(url);\n\n"+
            "        try{\n"+
            "            const response = await fetch(url,{\n"+
            "                method : 'POST',\n"+
            "                headers : {\n"+
            "                    'Content-Type' : 'application/json'\n"+
            "                },\n"+
            "                body : JSON.stringify({");
            for (i=0;i<columns.size();i++) {
                if(columns.get(i).getIsPrimaryKey().equals(false)&&columns.get(i).getIsForeignKey().equals(false)){
                    if(i==columns.size()-1){
                        writer.write("'"+columns.get(i).getColumnName()+"' : "+columns.get(i).getColumnName()+".value}).replace(/\\\\/g, '').replace(/\"({.*})\"/g, '$1')\n            })\n");
                    }
                    else{
                        writer.write("'"+columns.get(i).getColumnName()+"' : "+columns.get(i).getColumnName()+".value,");
                    }
                }
                else if(columns.get(i).getIsForeignKey().equals(true)){
                    if(i==columns.size()-1){
                        writer.write("'"+columns.get(i).getTableName()+"' : JSON.stringify(selected_"+columns.get(i).getTableName()+".value)})"+".replace(/\\/g, '')"+".replace(/"+'"'+"({.*})"+'"'+"/g,"+" '$1')\n            })\n");
                    }
                    else{
                        writer.write("'"+columns.get(i).getTableName()+"' : JSON.stringify(selected_"+columns.get(i).getTableName()+".value),");
                    }
                }
                
            }
            writer.write("            const data = await response.json();\n");
            writer.write("            console.log(data);\n");
            writer.write("            router.push('/list_"+tableName+"s')"+"\n");
            writer.write("        }catch(err){\n");
            writer.write("            console.log(err.message);\n        }\n    }\n");
            
            //onMounted
            writer.write("    onMounted(() => {\n");
            for(i=0;i<columns.size();i++){
                if(columns.get(i).getIsForeignKey().equals(true)){
                    String c1 =  columns.get(i).getTableName().substring(0, 1).toUpperCase();
                    String c2 = columns.get(i).getTableName().substring(1);
                    String tableNameMaj = c1+c2;
                    writer.write("        fetch"+tableNameMaj+"s()\n");
                }
            }
            writer.write("    })\n\n");
            writer.write("</script>\n\n"); 

            ///template
            writer.write("<template>\n");
            writer.write("    <form>\n");
            for(i=0;i<columns.size();i++){
                String c1 =  columns.get(i).getColumnName().substring(0, 1).toUpperCase();
                String c2 = columns.get(i).getColumnName().substring(1);
                String columnMaj = c1+c2;
                if(columns.get(i).getIsPrimaryKey().equals(false)&&columns.get(i).getIsForeignKey().equals(false)){
                    writer.write("        <label for="+'"'+columns.get(i).getColumnName()+'"'+">"+columns.get(i).getColumnName()+" : </label><input @change="+'"'+functionPrefix+columnMaj+'"'+" type="+'"'+"text"+'"'+" name="+'"'+columns.get(i).getColumnName()+'"'+"/>\n");
                }
                else if(columns.get(i).getIsForeignKey().equals(true)){
                    writer.write("        <select name="+'"'+columns.get(i).getTableName()+"s"+'"'+">\n"+
                    "            <option v-for="+'"'+"("+columns.get(i).getTableName()+",index) in "+columns.get(i).getTableName()+"s"+'"'+" :key="+'"'+"index"+'"'+" name="+'"'+columns.get(i).getTableName()+"s"+'"'+" :value="+'"'+"index"+'"'+" @click="+'"'+functionPrefix+"Selected"+columns.get(i).getTableName()+'"'+">\n"+
                    "                {{ console.log(index) }}\n"+
                    "                {{ "+columns.get(i).getTableName()+".nom }}\n"+
                    "            </option>\n"+
                    "        </select>\n");
                }
            }
            writer.write("        <button  @click="+'"'+"onSubmit"+'"'+">submit</button>\n    </form>\n");
            writer.write("</template>");
            
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void createList(String tableName, List<Column> columns){
        try{
            String s1 = tableName.substring(0, 1).toUpperCase();  // first letter = J  
            String s2 = tableName.substring(1);     // after 1st letter = avatpoint  
            String className = s1 + s2;

            FileWriter writer = new FileWriter("/home/riana/Documents/S5/Naina-FRAMEWORK/Class-Generator/Generated_files/components/"+"List"+className+".vue");
            
            ///script
            writer.write("<script setup>\n");

            //necessary imports
            writer.write("    import {onMounted , ref} from 'vue'\n    import router from '../router'\n\n");
            int i = 0;
            //states
            writer.write("    var "+tableName+"s"+" = ref([])\n");
                
            writer.write("\n");

            //fetching
            writer.write("    async function fetch"+className+"s(){\n"+
            "        const url = 'http://localhost:8080/"+tableName+"s';\n"+
            "        console.log(url);\n\n"+
            "        try{\n"+
            "            const response = await fetch(url,{\n"+
            "                method : 'GET',\n"+
            "                headers : {\n"+
            "                    'Content-Type' : 'application/json'\n"+
            "                }\n"+
            "            });\n");
            writer.write("            const data = await response.json();\n");
            writer.write("            console.log(data);\n");
            writer.write("            "+tableName+"s.value = data;"+"\n");
            writer.write("            console.log("+tableName+"s.value)"+"\n");
            writer.write("        }catch(err){\n");
            writer.write("            console.log(err.message);\n        }\n    }\n\n");
            writer.write("    onMounted(() => {\n");
            writer.write("        fetch"+className+"s()\n    })\n\n");

            //modify action
            writer.write("    function onClickModify(event){\n"+
            "        console.log("+tableName+"s.value)\n"+
            "        console.log("+'"'+"/modify_"+tableName+"/"+'"'+"+"+tableName+"s.value[event.target.value].id"+tableName+");\n"+
            "        router.push("+'"'+"/modify_"+tableName+"/"+'"'+"+"+tableName+"s.value[event.target.value].id"+tableName+");\n    }\n");
            
            //delete action
            writer.write("    async function onClickDelete(event){\n"+
            "        const url = 'http://localhost:8080/"+tableName+"s/'+"+tableName+"s.value[event.target.value].id"+tableName+";\n"+
            "        console.log(url);\n\n"+
            "        try{\n"+
            "            await fetch(url,{\n"+
            "                method : 'DELETE',\n"+
            "                headers : {\n"+
            "                }\n"+
            "            });\n");
            writer.write("        }catch(err){\n");
            writer.write("            console.log(err.message);\n        }\n    }\n\n");

            writer.write("</script>\n\n");


            ///template
            writer.write("<template>\n");
            writer.write("    <ul>\n"+
            "        <li v-for="+'"'+"("+tableName+",index) in "+tableName+"s"+'"'+" :key="+'"'+"index"+'"'+">\n"+
            "            {{ console.log(index) }}\n");
            String row = "";
            for(i=0;i<columns.size();i++){
                if(columns.get(i).getIsPrimaryKey().equals(false)&&columns.get(i).getIsForeignKey().equals(false)){
                    row = row+"{{ "+tableName+"."+columns.get(i).getColumnName()+" }} ";
                }
                else if(columns.get(i).getIsForeignKey().equals(true)){
                    row = row+"{{ "+tableName+"."+columns.get(i).getTableName()+".nom }} ";
                }
            }
            writer.write("            "+row+" <button @click="+'"'+"onClickModify"+'"'+" :value="+'"'+"index"+'"'+">modify</button> <button @click="+'"'+"onClickDelete"+'"'+" :value="+'"'+"index"+'"'+">delete</button>\n        </li>\n    </ul>");

            writer.write("</template>");
            
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void createModifyForm(String tableName, List<Column> columns){
        try{
            String s1 = tableName.substring(0, 1).toUpperCase();  // first letter = J  
            String s2 = tableName.substring(1);     // after 1st letter = avatpoint  
            String className = s1 + s2;

            FileWriter writer = new FileWriter("/home/riana/Documents/S5/Naina-FRAMEWORK/Class-Generator/Generated_files/components/Modify"+className+"Form.vue");
            
            ///script
            writer.write("<script setup>\n");

            //necessary imports
            writer.write("    import {onMounted , ref} from 'vue'\n    import router from '../router'\n    import { defineProps } from 'vue'\n\n");

            //props
            writer.write("    const props = defineProps({\n" + //
            "        id:String\n" + //
            "    })\n\n");

            int i = 0;
            //states
            writer.write("    var id"+tableName+" = ref(props.id)\n");
            for (i=0;i<columns.size();i++) {
                if(columns.get(i).getIsPrimaryKey().equals(false)&&columns.get(i).getIsForeignKey().equals(false)){
                    writer.write("    var "+columns.get(i).getColumnName()+" = ref('')\n");
                }
                else if(columns.get(i).getIsForeignKey().equals(true)){
                    writer.write("    var "+columns.get(i).getTableName()+"s = ref([])\n"+
                    "    var selected_"+columns.get(i).getTableName()+" = ref({})\n");
                }
            }
            writer.write("\n");

            //inputs listeners
            String functionPrefix = "onChange";
            for (i=0;i<columns.size();i++) {
                if(columns.get(i).getIsPrimaryKey().equals(false)&&columns.get(i).getIsForeignKey().equals(false)){
                    String c1 =  columns.get(i).getColumnName().substring(0, 1).toUpperCase();
                    String c2 = columns.get(i).getColumnName().substring(1);
                    String columnMaj = c1+c2;
                    writer.write("    function "+functionPrefix+columnMaj+"(event){\n");
                    writer.write("        "+columns.get(i).getColumnName()+".value = event.target.value\n        console.log("+columns.get(i).getColumnName()+")\n    }\n\n");
                }
                else if(columns.get(i).getIsForeignKey().equals(true)){
                    writer.write("    function "+functionPrefix+"Selected"+columns.get(i).getTableName()+"(event){\n");
                    writer.write("        selected_"+columns.get(i).getTableName()+".value ="+columns.get(i).getTableName()+"s.value[event.target.value]\n        console.log(selected_"+columns.get(i).getTableName()+")\n    }\n\n");
                }
                
            }

            //fetching byId
            writer.write("    async function fetch"+className+"ById(){\n"+
            "        const url = 'http://localhost:8080/"+tableName+"s/'+"+"id"+tableName+".value;\n"+
            "        console.log(url);\n\n"+
            "        try{\n"+
            "            const response = await fetch(url,{\n"+
            "                method : 'GET',\n"+
            "                headers : {\n"+
            "                    'Content-Type' : 'application/json'\n"+
            "                }\n"+
            "            });\n");
            writer.write("            const data = await response.json();\n");
            writer.write("            console.log(data);\n");

            for (i=0;i<columns.size();i++) {
                if(columns.get(i).getIsPrimaryKey().equals(false)&&columns.get(i).getIsForeignKey().equals(false)){
                    writer.write("            "+columns.get(i).getColumnName()+".value = data."+columns.get(i).getColumnName()+";"+"\n");
                }
                else if(columns.get(i).getIsForeignKey().equals(true)){
                    writer.write("            selected_"+columns.get(i).getTableName()+".value = data."+columns.get(i).getTableName()+";"+"\n");
                }
            }
           
            writer.write("        }catch(err){\n");
            writer.write("            console.log(err.message);\n        }\n    }\n\n");


            //fetching 
            for (i=0;i<columns.size();i++) {
                if(columns.get(i).getIsForeignKey().equals(true)){
                    String c1 =  columns.get(i).getTableName().substring(0, 1).toUpperCase();
                    String c2 = columns.get(i).getTableName().substring(1);
                    String tableNameMaj = c1+c2;
                    writer.write("    async function fetch"+tableNameMaj+"s(){\n"+
                    "        const url = 'http://localhost:8080/"+columns.get(i).getTableName()+"s';\n"+
                    "        console.log(url);\n\n"+
                    "        try{\n"+
                    "            const response = await fetch(url,{\n"+
                    "                method : 'GET',\n"+
                    "                headers : {\n"+
                    "                    'Content-Type' : 'application/json'\n"+
                    "                }\n"+
                    "            });\n");
                    writer.write("            const data = await response.json();\n");
                    writer.write("            console.log(data);\n");
                    writer.write("            "+columns.get(i).getTableName()+"s.value = data;"+"\n");
                    writer.write("            console.log("+columns.get(i).getTableName()+"s.value)"+"\n");
                    writer.write("        }catch(err){\n");
                    writer.write("            console.log(err.message);\n        }\n    }\n\n");
                }
                
            }

            //submit
            writer.write("    async function onSubmit(e){\n"+
            "        e.preventDefault();\n"+
            "        const url = 'http://localhost:8080/"+tableName+"s/'+id"+tableName+".value;\n"+
            "        console.log(url);\n\n"+
            "        try{\n"+
            "            const response = await fetch(url,{\n"+
            "                method : 'PUT',\n"+
            "                headers : {\n"+
            "                    'Content-Type' : 'application/json'\n"+
            "                },\n"+
            "                body : JSON.stringify({");

            

            for (i=0;i<columns.size();i++) {
                if(columns.get(i).getIsPrimaryKey().equals(false)&&columns.get(i).getIsForeignKey().equals(false)){
                    if(i==columns.size()-1){
                        writer.write("'"+columns.get(i).getColumnName()+"' : "+columns.get(i).getColumnName()+".value}).replace(/\\\\/g, '').replace(/\"({.*})\"/g, '$1')\n            })\n");
                    }
                    else{
                        writer.write("'"+columns.get(i).getColumnName()+"' : "+columns.get(i).getColumnName()+".value,");
                    }
                }
                else if(columns.get(i).getIsPrimaryKey().equals(true)){
                    if(columns.get(i).getDataType().equals("int4")||columns.get(i).getDataType().equals("serial")){
                        writer.write("'id"+tableName+"' : parseInt(id"+tableName+".value),");
                    }
                    else{
                        writer.write("'id"+tableName+"' : id"+tableName+".value,");
                    }
                }
                else if(columns.get(i).getIsForeignKey().equals(true)){
                    if(i==columns.size()-1){
                        writer.write("'"+columns.get(i).getTableName()+"' : JSON.stringify(selected_"+columns.get(i).getTableName()+".value)})"+".replace(/\\/g, '')"+".replace(/"+'"'+"({.*})"+'"'+"/g,"+" '$1')\n            })\n");
                    }
                    else{
                        writer.write("'"+columns.get(i).getTableName()+"' : JSON.stringify(selected_"+columns.get(i).getTableName()+".value),");
                    }
                }
                
            }
            writer.write("            const data = await response.json();\n");
            writer.write("            console.log(data);\n");
            writer.write("            router.push('/list_"+tableName+"s')"+"\n");
            writer.write("        }catch(err){\n");
            writer.write("            console.log(err.message);\n        }\n    }\n");
            
            //onMounted
            writer.write("    onMounted(() => {\n");
            writer.write("        fetch"+className+"ById(id"+tableName+")\n");
            for(i=0;i<columns.size();i++){
                if(columns.get(i).getIsForeignKey().equals(true)){
                    String c1 =  columns.get(i).getTableName().substring(0, 1).toUpperCase();
                    String c2 = columns.get(i).getTableName().substring(1);
                    String tableNameMaj = c1+c2;
                    writer.write("        fetch"+tableNameMaj+"s()\n");
                }
            }
            writer.write("    })\n\n");
            writer.write("</script>\n\n"); 

            ///template
            writer.write("<template>\n");
            writer.write("    <form>\n");
            for(i=0;i<columns.size();i++){
                String c1 =  columns.get(i).getColumnName().substring(0, 1).toUpperCase();
                String c2 = columns.get(i).getColumnName().substring(1);
                String columnMaj = c1+c2;
                if(columns.get(i).getIsPrimaryKey().equals(false)&&columns.get(i).getIsForeignKey().equals(false)){
                    writer.write("        <label for="+'"'+columns.get(i).getColumnName()+'"'+">"+columns.get(i).getColumnName()+" : </label><input @change="+'"'+functionPrefix+columnMaj+'"'+" type="+'"'+"text"+'"'+" name="+'"'+columns.get(i).getColumnName()+'"'+" :value="+'"'+columns.get(i).getColumnName()+""+'"'+"/>\n");
                }
                else if(columns.get(i).getIsForeignKey().equals(true)){
                    writer.write("        <select name="+'"'+columns.get(i).getTableName()+"s"+'"'+">\n"+
                    "            <option v-for="+'"'+"("+columns.get(i).getTableName()+",index) in "+columns.get(i).getTableName()+"s"+'"'+" :key="+'"'+"index"+'"'+" name="+'"'+columns.get(i).getTableName()+"s"+'"'+" :value="+'"'+"index"+'"'+" @click="+'"'+functionPrefix+"Selected"+columns.get(i).getTableName()+'"'+">\n"+
                    "                {{ console.log(index) }}\n"+
                    "                {{ "+columns.get(i).getTableName()+".nom }}\n"+
                    "            </option>\n"+
                    "        </select>\n");
                }
            }
            writer.write("        <button  @click="+'"'+"onSubmit"+'"'+">submit</button>\n    </form>\n");
            writer.write("</template>");
            
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}

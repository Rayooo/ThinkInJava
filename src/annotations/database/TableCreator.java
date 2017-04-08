package annotations.database;

import com.sun.istack.internal.NotNull;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray on 2017/4/7.
 */
public class TableCreator {

    @Test
    public void main() throws ClassNotFoundException {
        String[] args = {"annotations.database.Member"};
        if(args.length < 0){
            System.out.println("arguments: annotated classes");
            System.exit(0);
        }
        for (String className : args){
            Class<?> cl = Class.forName(className);
            DBTable dbTable = cl.getAnnotation(DBTable.class);
            if(dbTable == null){
                System.out.println("No DBTable annotations in class " + className);
                continue;
            }

            String tableName = dbTable.name();
            if(tableName.length() < 1){
                tableName = cl.getName();
            }
            List<String> columnDefs = new ArrayList<>();
            for(Field field : cl.getDeclaredFields()){
                String columnName = null;
                Annotation[] annotations = field.getDeclaredAnnotations();

                if(annotations.length < 1) continue;

                if(annotations[0] instanceof SQLInteger){
                    SQLInteger sInt = (SQLInteger) annotations[0];
                    if(sInt.name().length() < 1){
                        columnName = field.getName();
                    }
                    else{
                        columnName = sInt.name();
                    }
                    columnDefs.add(columnName + " INT " + getConstraints(sInt.constraints()));
                }

                if(annotations[0] instanceof SQLString){
                    SQLString sString = (SQLString) annotations[0];
                    if(sString.name().length() < 1){
                        columnName = field.getName();
                    }
                    else{
                        columnName = sString.name();
                    }
                    columnDefs.add(columnName + "VARCHAR(" + sString.value() + ")" + getConstraints(sString.constraints()));
                }

                StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");
                for(String columnDef : columnDefs){
                    createCommand.append("\n   " + columnDef + ",");
                }
                String tableCreate = createCommand.substring(0, createCommand.length() - 1) + ");";
                System.out.println("Table Creation SQL for " + className + "is : \n " + tableCreate);

            }

        }


    }

    private static String getConstraints(Constraints con){
        StringBuilder constraints = new StringBuilder();
        if(!con.allowNull()){
            constraints.append(" Not Null");
        }
        if(con.primaryKey()){
            constraints.append(" PRIMARY KEY");
        }
        if(con.unique()){
            constraints.append(" UNIQUE");
        }
        return constraints.toString();
    }

}















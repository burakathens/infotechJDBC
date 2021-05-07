package tests.jdbc;

import org.testng.annotations.Test;
import utilities.DatabaseConnectorV1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostgreSQL {
    List<Map<String,String>> dataList = new ArrayList<>();
    @Test
    void tc001(){
        String query = "select * from customers";
        dataList = DatabaseConnectorV1.getQueryResultWithAListMap(query);
        System.out.println("my data: " + dataList.get(0).get("contact_name"));
    }




}

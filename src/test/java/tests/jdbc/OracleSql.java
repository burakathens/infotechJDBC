package tests.jdbc;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleSql {
    String URL ="jdbc:oracle:thin:@localhost:1521/ORCLCDB.localdomain";
    String USERNAME="samet";
    String PASSWORD="Ankara06";

    Connection connection;//baglanti olusturur
    Statement statement;//SLQ ifadesi dondurur
    ResultSet resultSet;//SLQ ifadesini JDBC.ye tasir.
    @BeforeTest
    void start() throws SQLException {
        connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        statement = connection.createStatement();
    }
    @Test
    void tc001() throws SQLException {
        //personel tablosundaki isimleri yazdir
        String sorgu = "select PERSONEL_ISIM from PERSONEL";
        resultSet = statement.executeQuery(sorgu);//bir sorgu cevabi alirsin
//        boolean isResult = statement.execute(sorgu);//true veya false dondurur
//        int resultROw = statement.executeUpdate(sorgu);//etkilenen satir sayisini dondurur
//        resultSet = statement.executeBatch(new int[]);
//        resultSet.absolute(6);boolean dondurur 6. satirda veri var mi ogrenirsin
//        resultSet.last();
//        resultSet.first();
        while (resultSet.next()){
            System.out.println("personel isimleri: "+resultSet.getString("PERSONEL_ISIM")+"\n");
        }
    }
    @Test
    void tc002() throws SQLException {
        DatabaseMetaData allInformation = connection.getMetaData();
        System.out.println("allInformation.getUserName() = " + allInformation.getUserName());
        System.out.println("allInformation.getURL() = " + allInformation.getURL());
        System.out.println("allInformation.getDatabaseProductName() = " + allInformation.getDatabaseProductName());
        System.out.println("allInformation.getDriverName() = " + allInformation.getDriverName());
        System.out.println("allInformation.getDriverVersion() = " + allInformation.getDriverVersion());
    }
    @Test
    void tc003() throws SQLException {
        //ismi A ile baslayan 2 tane personel vardir....
        String query = "\n" +
                "select PERSONEL_ISIM\n" +
                "from PERSONEL\n" +
                "where PERSONEL_ISIM like 'A%'";
        resultSet = statement.executeQuery(query);
        int sum = 0;
        while (resultSet.next()){
            sum++;
        }
        Assert.assertEquals(sum,2);
    }
    @Test
    void tc004() throws SQLException {
        //isyerindeki toplam aylik maas tutari 36625.
        String query = "select sum(MAAS)\n" +
                "from PERSONEL";
        resultSet = statement.executeQuery(query);
        resultSet.next();//veri islemek icin verinin ustunden gecmelisin
        Assert.assertEquals(resultSet.getInt(1),36625);
    }
    @Test
    void tc005() throws SQLException {
        //ortalama maastan daha dusuk alan toplam 8 kisi var, bu isimleri listele
        //Ahmet ve Nese ortalamanin altilda maas aliyorlar..?
        List<String>allNames = new ArrayList<>();
        String query = "select PERSONEL_ISIM\n" +
                        "from PERSONEL\n" +
                        "where MAAS<(select avg(MAAS) from PERSONEL)";
        resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            allNames.add(resultSet.getString(1));
        }
        Assert.assertEquals(allNames.size(),8);
        System.out.println("allNames = " + allNames);
        Assert.assertTrue(allNames.contains("NESE"));
        Assert.assertTrue(allNames.contains("AHMET"));
    }
    @Test
    void tc006() throws SQLException {
        //database.de yeni bir veri ekleniyor ve bu daha database.de sorgulaniyor
        String updateQuery = "insert into PERSONEL values (4444,'SALIH','MUDUR',8888, null,2500,10)";
        int result = statement.executeUpdate(updateQuery);
        System.out.println(result);
        Assert.assertEquals(result, 1);
        String testNewData = "select *\n" +
                            "from PERSONEL\n" +
                            "where PERSONEL_ID=4444";
        resultSet = statement.executeQuery(testNewData);
        while (resultSet.next()){
            Assert.assertEquals(resultSet.getInt(1),4444);
        }
    }
    //dll,dml
    @Test
    void tc007Update() throws SQLException {
        String updateData = "update PERSONEL\n" +
                            "set MESLEK='MUDUR'\n" +
                            "where MESLEK='TERFI'";
        System.out.println("statement.executeUpdate(updateData) = " +
                statement.executeUpdate(updateData));
    }




}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class main {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
	Properties prop = new Properties();
	String nombre = "resources/config.properties";
	prop.load(new FileInputStream(nombre));
	String driver = prop.getProperty("driver");
	Class.forName(driver);
	
	
	String url = prop.getProperty("url");
	String user = prop.getProperty("user");
	String password = prop.getProperty("password");
	Connection con = DriverManager.getConnection(url, user, password);
	
	
	String query;
	
	query = "CREATE DATABASE OBLIGATORIOBD";
	Statement stmt = con.createStatement();
	stmt.executeUpdate(query);
	
	stmt.close();
	
	query = "CREATE TABLE OBLIGATORIOBD.Productos ("
			+ "Codigo VARCHAR(45) PRIMARY KEY,"
			+ "Nombre VARCHAR(45),"
			+ "Precio INT)";
	Statement stmt1 = con.createStatement();
	stmt1.executeUpdate(query);
	stmt1.close();
	
	query = "CREATE TABLE OBLIGATORIOBD.Ventas("
			+ "Numero int NOT NULL,"
			+ "CodProd VARCHAR (45),"
			+ "Unidades INT,"
			+ "Cliente VARCHAR(45),"
			+ "PRIMARY KEY (Numero,CodProd),"
			+ "FOREIGN KEY (CodProd)REFERENCES OBLIGATORIOBD.Productos(Codigo))";
	
	Statement stmt2 = con.createStatement();
	stmt2.executeUpdate(query);
	stmt2.close();
	
	query = "INSERT INTO OBLIGATORIOBD.Productos(Codigo,Nombre,Precio) VALUES("
			+ "'Pul1001','Pulguicida',500),"
			+ "('HongoBravo','Funguicida',1250),"
			+ "('Pio1234','Piojicida',720),"
			+ "('PiPi69','Polvos pica pica',6969),"
			+ "('Fushhh02','Spray anti suegros',20)";
	
	Statement st1 = con.createStatement();
	st1.executeUpdate(query);
	st1.close();
	
	}
	
}
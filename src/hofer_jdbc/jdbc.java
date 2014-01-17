package hofer_jdbc;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class jdbc {
	
	  private static Connection connect = null;
	  private static Statement statement = null;
	  private static PreparedStatement preparedStatement = null;
	  private static ResultSet resultSet = null;
	  
	public static void read() throws Exception 
	{
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/autoliste", "root", "");
			
			statement = connect.createStatement();
			
			System.out.println("###Select###");
			resultSet = statement.executeQuery("select * from autos");
			write(resultSet);
			
			
			//prepared Statement
			System.out.println("###Insert mit prepared Statement###");
			preparedStatement = connect.prepareStatement("insert into  autos values (?, ?, ?, ?)");
		    preparedStatement.setString(1, "Mercedes");
			preparedStatement.setInt(2, 2012);
			preparedStatement.setInt(3, 115);
			preparedStatement.setString(4, "S Klasse");
			preparedStatement.executeUpdate();
			
			System.out.println("###Select mit neuem Datensatz");
			preparedStatement = connect.prepareStatement("SELECT * from autos");
			resultSet = preparedStatement.executeQuery();
			write(resultSet);
			      
			System.out.println("###Update von Typ###");
			preparedStatement = connect.prepareStatement("update autos set a_typ = ? where a_typ = ?");
			preparedStatement.setString(1, "E Klasse");
			preparedStatement.setString(2, "S Klasse");
			preparedStatement.executeUpdate();

			System.out.println("###Ausgabe mit neuem Typ###");
			preparedStatement = connect.prepareStatement("SELECT * from autos");
			resultSet = preparedStatement.executeQuery();
			write(resultSet);
			
			System.out.println("###Delete###");
			preparedStatement = connect.prepareStatement("delete from autos where a_ps= ? ; ");
			preparedStatement.setString(1, "115");
		    preparedStatement.executeUpdate();
			      
			System.out.println("###Ausgabe ohne Merz###");
		    preparedStatement = connect.prepareStatement("SELECT * from autos");
			resultSet = preparedStatement.executeQuery();
			write(resultSet);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void write(ResultSet s) throws SQLException
	{
		while (resultSet.next()) {
			String marke = resultSet.getString("a_marke");
		    String baujahr = resultSet.getString("a_baujahr");
		    String ps = resultSet.getString("a_ps");
		    String typ = resultSet.getString("a_typ");
		    System.out.println("Marke: " + marke);
		    System.out.println("Baujahr: " + baujahr);
		    System.out.println("PS: " + ps);
		    System.out.println("Typ: " + typ);
		    System.out.println("+++++++++++++++++++++");
		}
	}
	private void close() {
	    try {
	      if (resultSet != null) {
	        resultSet.close();
	      }

	      if (statement != null) {
	        statement.close();
	      }

	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {

	    }
	  }
}

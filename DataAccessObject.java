package sqlDatabaseApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.BorderLayout;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class DataAccessObject {

	public static void main(String[] args) throws SQLException {

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		String s;

		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			myConn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;" + "databaseName=Northwind",
					"AJPrez", "24cVF@#nm95");
			
			myStmt = myConn.createStatement();
			s = "SELECT ContactName FROM Customers";
			myRs = myStmt.executeQuery(s);
			ResultSetMetaData rsmt = myRs.getMetaData();
			int c = rsmt.getColumnCount();
			Vector column = new Vector(c);
			for (int i = 1; i <= c; i++) {
				column.add(rsmt.getColumnName(i));
			}

			Vector data = new Vector();
			Vector row = new Vector();
			while (myRs.next()) {
				row = new Vector(c);
				for (int i = 1; i <= c; i++) {
					row.add(myRs.getString(i));
				}
				data.add(row);
			}

			JFrame frame = new JFrame();
			frame.setSize(500, 120);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel panel = new JPanel();
			JTable table = new JTable(data, column);
			JScrollPane jsp = new JScrollPane(table);
			panel.setLayout(new BorderLayout());
			panel.add(jsp, BorderLayout.CENTER);
			frame.setContentPane(panel);
			frame.setVisible(true);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR");
		} finally {
			try {
				myStmt.close();
				myRs.close();
				myConn.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "ERROR CLOSE");
			}
		}
	}
}

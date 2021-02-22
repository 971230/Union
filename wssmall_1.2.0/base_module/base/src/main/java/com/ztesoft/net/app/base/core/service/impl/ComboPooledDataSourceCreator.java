package com.ztesoft.net.app.base.core.service.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ztesoft.net.app.base.core.service.IDataSourceCreator;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

public class ComboPooledDataSourceCreator implements IDataSourceCreator {
	private DataSource dataSource;
	@Override
	public DataSource createDataSource(String driver,String url,String username,String password) {

		try {
			ComboPooledDataSource comboPooledDataSource = (ComboPooledDataSource)dataSource;

			comboPooledDataSource.setUser(username);
			comboPooledDataSource.setPassword(password);
			comboPooledDataSource.setJdbcUrl(url);
			comboPooledDataSource.setDriverClass(driver);

			return comboPooledDataSource;
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static void main(String args[]) throws SQLException{
//		IDataSourceCreator creator = new ComboPooledDataSourceCreator();
//		DataSource dataSource = creator.createDataSource("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@localhost:1521:XE","mall", "mall");
//		java.sql.Connection con =  dataSource.getConnection();
//		Statement st = con.createStatement();
//		st.execute("delete from test");
	}


	public DataSource getDataSource() {
		return dataSource;
	}


	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}

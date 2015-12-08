package model;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



public class MortgageDAO
{
	private DataSource dataSource;
	private final String TABLE = "MORTGAGE";

	public MortgageDAO() throws Exception
	{
		super();
		this.dataSource = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
	}

	public MortgageBean getRate(double principle, int amort, String bank) throws Exception
	{
		//MortgageBean mb = null;
		MortgageBean mb = new MortgageBean(bank);
		/*String query_rate = "SELECT rate FROM " + TABLE
				+ " where ABOVE<=" + principle+ " AND " 
				+ principle + "<=UPTO AND "
				+"AMORT=" + amort + " AND "
				+ "BANK=\'" + bank +"\'";
		String query_amort = "SELECT amort FROM " + TABLE
				+ " where AMORT=" + amort
				+ " AND BANK=\'" + bank + "\'";
		String query_principle = "SELECT rate FROM " + TABLE
				+" where ABOVE<=" + principle + " AND "
				+ principle + "<=UPTO AND "
				+ "BANK=\'" + bank + "\'";
		*/
		String query_rate = "SELECT rate FROM " + TABLE
				+ " where ABOVE<=? AND ?<=UPTO AND "
				+"AMORT=? AND BANK=\'" + bank +"\'";
		String query_amort = "SELECT amort FROM " + TABLE
				+ " where AMORT=? AND BANK=\'" + bank + "\'";
		String query_principle = "SELECT rate FROM " + TABLE
				+" where ABOVE<=? AND ?<=UPTO AND BANK=\'" + bank + "\'";
		//Statement stmt = null;
		PreparedStatement pstmt = null;
		try
		{
			/*Connection con = this.dataSource.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate("set schema Roumani");
			ResultSet rs = stmt.executeQuery(query_amort);
			if(!rs.next())
			{
				throw new Exception("The entered amortization is not offered!");
			}
			rs = stmt.executeQuery(query_principle);
			if(!rs.next())
			{
				throw new Exception("Principle is not fell into any of the offered ranges!");
			}
			rs = stmt.executeQuery(query_rate);
			if(rs.next())
			{
				double r = rs.getDouble("RATE");
				//mb = new MortgageBean(bank, r);
				mb.setRate(r);
			}
			else
			{
				throw new Exception("no offer from this bank");
			}*/
			
			//-----prepareStatement to prevent SQL injection
			Connection con = this.dataSource.getConnection();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("set schema Roumani");
			//---------------------------
			pstmt = con.prepareStatement(query_amort);
			pstmt.setInt(1,amort);
			ResultSet rs = pstmt.executeQuery();
			if(!rs.next())
			{
				throw new Exception("The entered amortization is not offered!");
			}
			closeStatement(pstmt);
			//------------------------------
			pstmt = con.prepareStatement(query_principle);
			pstmt.setDouble(1, principle);
			pstmt.setDouble(2, principle);
			rs = pstmt.executeQuery();
			if(!rs.next())
			{
				throw new Exception("Principle is not fell into any of the offered ranges!");
			}	
			closeStatement(pstmt);
			
			pstmt = con.prepareStatement(query_rate);
			pstmt.setDouble(1, principle);
			pstmt.setDouble(2, principle);
			pstmt.setInt(3, amort);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				double r = rs.getDouble("RATE");
				mb.setRate(r);
			}
			else
			{
				throw new Exception("no offer from this bank");
			}
			rs.close(); stmt.close(); con.close();
		}
		catch(Exception e)
		{
			System.out.println("exception in dao getRate()");
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		finally 
		{
			closeStatement(pstmt);
		}
		return mb;
	}

	public List<MortgageBean> getBanks() throws Exception
	{
		//grab all available banks
		List<MortgageBean> mbList = new LinkedList<MortgageBean>();
		String query = "SELECT distinct bank FROM " + TABLE;
		Connection con = this.dataSource.getConnection();;
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = con.createStatement();
			stmt.executeUpdate("set schema Roumani");
			rs = stmt.executeQuery(query);		

			while(rs.next())
			{
				String bank = rs.getString("BANK");
				mbList.add(new MortgageBean(bank));
			}
		}
		catch(Exception e)
		{
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                	System.out.println("exception in dao create()");
		}
		finally 
		{
			rs.close(); stmt.close(); con.close();
		}

		return mbList;		
	}
	
	
	private void closeStatement(PreparedStatement pstmt) throws SQLException
	{
		if(pstmt != null) 
		{ 
			pstmt.close(); 
		}
	}

}



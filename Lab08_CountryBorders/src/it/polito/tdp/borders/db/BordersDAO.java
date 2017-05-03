package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {
	
TreeMap <String,Country>mappa=new TreeMap<String,Country>();
	public List<Country> loadAllCountries() {
		ArrayList<Country> nazioni=new ArrayList<Country>();

		String sql = "SELECT ccode,StateAbb,StateNme " + "FROM country " + "ORDER BY StateAbb ";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c=new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				mappa.put(rs.getString("StateAbb"), c);
				nazioni.add(c);
			//	System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
			}

			conn.close();
			return nazioni;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Error -- loadAllCountries");
			throw new RuntimeException("Database Error");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		String sql = "SELECT state1ab,state2ab " + "FROM contiguity " + "WHERE year<=? && conttype=1 ";
		ArrayList<Border> bordi=new ArrayList<Border>();
				try {
					Connection conn = DBConnect.getInstance().getConnection();
					PreparedStatement st = conn.prepareStatement(sql);
					st.setInt(1, anno);
					ResultSet rs = st.executeQuery();

					while (rs.next()) {
						if(mappa.get(rs.getString("state1ab"))!=null&&mappa.get(rs.getString("state2ab"))!=null)
				bordi.add(new Border(mappa.get(rs.getString("state1ab")),mappa.get(rs.getString("state2ab"))));
						//	System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
					}

					conn.close();
					return bordi;

				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Database Error -- loadAllCountries");
					throw new RuntimeException("Database Error");
				}
			}
}

package com.meteorology.swat.junk;

import com.meteorology.swat.DAO.ResultDAO;
import com.meteorology.swat.DAOImpl.ResultDAOImpl;

public class Sample9 {
	
	public static void main(String[] args) {
		ResultDAO r = new ResultDAOImpl();
		r.fetchResultsWithDateState("2015-06-07", "2015-06-23", "IA");
	}

}

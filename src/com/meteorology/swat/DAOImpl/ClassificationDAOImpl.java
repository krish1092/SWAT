package com.meteorology.swat.DAOImpl;

import java.math.BigInteger;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.meteorology.swat.DAO.ClassificationDAO;
import com.meteorology.swat.bean.Classification;

/**
 * An implementation of ClassificationDAO.
 * @author Krishnan Subramanian
 *
 */
public class ClassificationDAOImpl implements ClassificationDAO {

	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ClassificationDAOImpl.class);
	
	@Override
	public BigInteger insert(Classification classification) throws DataAccessException {
		String sql ="insert into classification (info_id,classification,count)"
				+ "values (?,?,?)";
		this.jdbcTemplate.update(sql,classification.getInfoID(),classification.getClassification(),classification.getCount());
		logger.info("Successfully inserted " + classification.getInfoID() + " into the classfication table");
		
		sql="SELECT last_insert_id()";
		logger.info("Inserted classification_id:" + classification.getInfoID());
		return BigInteger.valueOf(this.jdbcTemplate.queryForObject(sql,Integer.class));
	}

	@Override	
	public void setDataSource(DataSource ds) {
		this.jdbcTemplate=new JdbcTemplate(ds);
	}
}

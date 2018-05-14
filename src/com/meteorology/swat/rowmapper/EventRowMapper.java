package com.meteorology.swat.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.meteorology.swat.model.EventStorageModel;

/**
 * Rowmapper for the event table.
 * @author Krishnan Subramanian
 *
 */
public class EventRowMapper implements RowMapper<EventStorageModel>{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventStorageModel mapRow(ResultSet rs, int row) throws SQLException {
		EventStorageModel esm= new EventStorageModel();
		esm.setBeginLat(rs.getDouble("begin_lat"));
		esm.setBeginLong(rs.getDouble("begin_long"));
		esm.setEndLat(rs.getDouble("end_lat"));
		esm.setEndLong(rs.getDouble("end_long"));
		return esm;
	}
}

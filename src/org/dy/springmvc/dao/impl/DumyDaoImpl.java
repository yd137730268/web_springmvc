package org.dy.springmvc.dao.impl;

import org.dy.springmvc.dao.DummyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DumyDaoImpl implements DummyDao {
	private final Logger logger = LoggerFactory.getLogger(DumyDaoImpl.class);

	@Override
	public String getDummy() throws Exception {
		logger.info("getDumy...");
		return "dumyData";
	}

}

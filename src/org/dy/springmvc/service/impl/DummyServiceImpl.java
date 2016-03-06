package org.dy.springmvc.service.impl;

import org.dy.springmvc.dao.DummyDao;
import org.dy.springmvc.service.DummyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DummyServiceImpl implements DummyService {
	private final Logger logger = LoggerFactory
			.getLogger(DummyServiceImpl.class);

	@Autowired
	private DummyDao dummyDao;

	@Override
	public String dummyService() throws Exception {
		logger.info("dummyService...");
		return dummyDao.getDummy();
	}

}

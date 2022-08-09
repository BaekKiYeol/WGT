package com.varxyz.wgt.owner.service;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.varxyz.wgt.data.DataSourceConfig;
import com.varxyz.wgt.owner.dao.OwnerDao;
import com.varxyz.wgt.owner.doamin.Owner;

public interface OwnerService {
	AnnotationConfigApplicationContext context = 
			new AnnotationConfigApplicationContext(DataSourceConfig.class);
	
	OwnerDao ownerDao = context.getBean("ownerDao", OwnerDao.class);

	// 점주 추가
	public void addOwner(Owner owner);
		
}

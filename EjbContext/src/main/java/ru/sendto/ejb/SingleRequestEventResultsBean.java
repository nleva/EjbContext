/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sendto.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import ru.sendto.dto.Dto;

/**
 * Bean to share data inside a request
 * Wires dto with additional data 
 * @author Lev Nadeinsky
 * @date	2017-10-22
 */
@Singleton
@Lock(LockType.READ)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@LocalBean
public class SingleRequestEventResultsBean {

	ThreadLocal<List<Dto>> localList = new ThreadLocal<>();
	
	public List<Dto> get(){
		List<Dto> list = localList.get();
		if(list==null) localList.set(list=new ArrayList<>());
		return list;
	}

	public void add(Dto dto) {
		get().add(dto);
	}
	
	public void addAll(Collection<Dto> collection) {
		get().addAll(collection);
	}
	
	public void clear() {
		get().clear();
	}
	
}

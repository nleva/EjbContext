/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sendto.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import ru.sendto.dto.Dto;

/**
 * Bean to share data inside a transaction
 * Wires dto with additional data 
 * @author Lev Nadeinsky
 * @date	2017-05-06
 */
@Singleton
@Lock(LockType.READ)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@LocalBean
public class EventResultsBean {

	IdentityHashMap<Dto, List<Dto>> data = new IdentityHashMap<>();

	public List<Dto> get(Dto key){
		return data.get(key);
	}

	@Lock(LockType.WRITE)
	public void put(Dto key, Dto value){
		List<Dto> list = data.get(key);
		if(list==null){
			list=new ArrayList<>();
			data.put(key, list);
		}
		list.add(value);
	}


	@Lock(LockType.WRITE)
	public void putAll(Dto key, Collection<Dto> value){
		List<Dto> list = data.get(key);
		if(list==null){
			list=new ArrayList<>();
			data.put(key, list);
		}
		list.addAll(value);
	}
	
	@Lock(LockType.WRITE)
	public void clear(Dto key){
		data.remove(key);
	}
	
}

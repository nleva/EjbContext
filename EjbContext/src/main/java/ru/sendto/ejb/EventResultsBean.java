/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sendto.ejb;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

import ru.sendto.dto.Dto;

/**
 * Bean to share data inside a transaction
 * Wires dto with additional data 
 * @author Lev Nadeinsky
 * @date	2017-05-06
 */
@Stateless
@LocalBean
public class EventResultsBean {

	IdentityHashMap<Dto, List<Dto>> data = new IdentityHashMap<>();
	boolean started = false;

	public Map<Dto, List<Dto>> getData() {
		started=true;
		return data;
	}

	public void put(Dto key, Dto value){
		List<Dto> list = data.get(key);
		if(list==null){
			list=new ArrayList<>();
			data.put(key, list);
		}
		list.add(value);
	}
	
	public void clear(Dto key){
		data.remove(key);
	}
	
}

package com.zjlh.villa.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlh.villa.dao.FacilityDaoHibernate4;
import com.zjlh.villa.dao.VillaFacilityDaoHibernate4;
import com.zjlh.villa.entity.Facility;
import com.zjlh.villa.entity.Svs;
import com.zjlh.villa.entity.VillaFacility;

@Service
public class FacilityService {
	@Autowired
	private FacilityDaoHibernate4 dao;
	@Autowired
	private VillaFacilityDaoHibernate4 vdao;

	/**
	 * 添加别墅
	 * 
	 * @param facility
	 */
	public void addFacility(Facility facility) {
		dao.save(facility);
	}

	/**
	 * 根据内容和类型查找
	 * 
	 * @param content
	 * @param type
	 * @return
	 */
	public List<Facility> qryFacility(String content, String type) {
		
		if (StringUtils.isBlank(content) && StringUtils.isBlank(type)) {
			return dao.findAll(Facility.class);
		}else {
			
			List<Object> values = new ArrayList<Object>();
			
			String hql = "FROM Facility WHERE 1=1";
			int i=0;
			StrBuilder sb = new StrBuilder(hql);
			
			if (StringUtils.isNotBlank(content)) {		
				content = "%"+ content +"%";
				sb.append(" and content like ?"+String.valueOf(i));
				values.add(content);
				i++;
			}
			
			if (StringUtils.isNotBlank(type)) {
				sb.append(" and type = ?"+String.valueOf(i));
				values.add(Integer.parseInt(type));
				i++;
			}
			
			return dao.execute(sb.toString(),values);
		}
	
	}

	/**
	 * 删除单条设施
	 * 
	 * @param id
	 */
	public void delFacility(int id) {
		String sql = "DELETE FROM VillaFacility WHERE facility="+id;
		dao.sql(sql);
		dao.delete(Facility.class, id);
	}

	/**
	 * 根据ID验证设施是否存在
	 * 
	 * @param id
	 * @return
	 */
	public boolean verifyFacility(int id) {
		return dao.exist(Facility.class, id);
	}

	/**
	 * 根据内容验证设施是否存在
	 * 
	 * @param content
	 * @return
	 */
	public boolean verifyFacility(String content) {
		return dao.exist(Facility.class, "content", content);
	}

	
	/**
	 * 根据ID获取一项设施
	 * @param id
	 * @return
	 */
		public Facility getFacility(int id){
			return dao.get(Facility.class, id);
		}
		
	/**
	 * 根据ID列表获取设施列表	
	 * @param args
	 * @return
	 */
		public List<Facility> getFacilityByIDs(String[] args){
			List<Facility> facilitylist = new ArrayList<Facility>();
			
			for (int i = 0; i < args.length; i++) {
				int id = Integer.parseInt(args[i]);
				Facility facility = getFacility(id);
				facilitylist.add(facility);
			}
			
			return facilitylist;
		}	
	
	
	
	
	
	
	// 以上facility

	// 以下villa_facility

	/**
	 * 保存单条别墅记录
	 * 
	 * @param villaFacility
	 */
	public void addVillaFacility(VillaFacility villaFacility) {
		vdao.save(villaFacility);
	}

	/**
	 * 保存多条别墅记录
	 * 
	 * @param array
	 * @param villaid
	 */
	public void addVillaFacilityList(List<Facility> facilitylist, int villaid) {
		for (int i = 0; i < facilitylist.size(); i++) {// 遍历设施id
			int serviceid = facilitylist.get(i).getId();
			if (!verifyVillaFacility(villaid, serviceid)) {// 验证该别墅是否存在该设施
				VillaFacility villaFacility = new VillaFacility(villaid, serviceid);// 实例化别墅设施
				addVillaFacility(villaFacility);// 保存别墅设施
			}
		}
	}

	
	 /** 更新多条别墅设施记录	
	 * @param facilitylist
	 * @param villaid
	 */
		public void updateVillaFacilityList(List<Facility> facility,int villaid) {
			String sql = "DELETE FROM VillaFacility WHERE villa="+villaid;
			vdao.sql(sql);		
			addVillaFacilityList(facility, villaid);
		}
		
			
	
	
	
	/**
	 * 根据别墅ID和设施ID判断是否存在
	 * 
	 * @param villa
	 * @param facility
	 * @return
	 */
	public boolean verifyVillaFacility(int villa, int facility) {
		List<String> keys = new ArrayList<String>();
		keys.add("villa");
		keys.add("facility");
		return vdao.exist("VillaFacility", keys, villa, facility);
	}

	/**
	 * 查找该别墅的所有设施
	 * 
	 * @param villaid
	 * @return
	 */
	public List<VillaFacility> qryVillaFacility(int villaid) {
		return vdao.getList(VillaFacility.class, "villa", villaid);
	}

	/**
	 * 删除别墅下的所有设施
	 * 
	 * @param villaid
	 */
	public void delFacilityList(int villaid) {
		List<VillaFacility> list = qryVillaFacility(villaid);
		for (int i = 0; i < list.size(); i++) {
			vdao.delete(list.get(i));
		}
	}
}

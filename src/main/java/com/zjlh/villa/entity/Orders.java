package com.zjlh.villa.entity;

// Generated 2015-8-8 15:38:17 by Hibernate Tools 4.3.1

import java.util.Date;

import javax.management.loading.PrivateClassLoader;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Formula;

/**
 * Orders generated by hbm2java
 */
@Entity
@Table(name = "Orders", catalog = "villa")
public class Orders implements java.io.Serializable {

	private Integer id;
	private Date oederTime;
	private Date payTime;
	private Integer member;
	private Integer villa;
	private Integer store;
	private Date startDay;
	private Integer startPeriod;
	private String startPeriodValue;
	private Date endDay;
	private Integer endPeriod;
	private String endPeriodValue;
	private Double money;
	private Integer state;
	private String stateValue;
	private String villaName;
	private String truename;
	private String phone;
	private String openid;
	
	
	
	public Orders() {
	}

	public Orders(Date oederTime, Date payTime, Integer member, Integer villa,
			Integer store, Date startDay, Integer startPeriod, Date endDay,
			Integer endPeriod, Double money, Integer state) {
		this.oederTime = oederTime;
		this.payTime = payTime;
		this.member = member;
		this.villa = villa;
		this.store = store;
		this.startDay = startDay;
		this.startPeriod = startPeriod;
		this.endDay = endDay;
		this.endPeriod = endPeriod;
		this.money = money;
		this.state = state;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "oeder_time", length = 19)
	public Date getOederTime() {
		return this.oederTime;
	}

	public void setOederTime(Date oederTime) {
		this.oederTime = oederTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "pay_time", length = 19)
	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	@Column(name = "member")
	public Integer getMember() {
		return this.member;
	}

	public void setMember(Integer member) {
		this.member = member;
	}

	@Column(name = "villa")
	public Integer getVilla() {
		return this.villa;
	}

	public void setVilla(Integer villa) {
		this.villa = villa;
	}

	@Column(name = "store")
	public Integer getStore() {
		return this.store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "start_day", length = 10)
	public Date getStartDay() {
		return this.startDay;
	}

	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}

	@Column(name = "start_period")
	public Integer getStartPeriod() {
		return this.startPeriod;
	}

	public void setStartPeriod(Integer startPeriod) {
		this.startPeriod = startPeriod;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "end_day", length = 10)
	public Date getEndDay() {
		return this.endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	@Column(name = "end_period")
	public Integer getEndPeriod() {
		return this.endPeriod;
	}

	public void setEndPeriod(Integer endPeriod) {
		this.endPeriod = endPeriod;
	}

	@Column(name = "money", precision = 22, scale = 0)
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@Formula("(select d.value from Orders o join Dictionary d on o.state = d.code where d.key = 'order_state' and o.id = id)")
	public String getStateValue() {
		return stateValue;
	}

	public void setStateValue(String stateValue) {
		this.stateValue = stateValue;
	}
	@Formula("(select d.value from Orders o join Dictionary d on o.start_period = d.code where d.key = 'order_period' and o.id = id)")
	public String getStartPeriodValue() {
		return startPeriodValue;
	}

	public void setStartPeriodValue(String startPeriodValue) {
		this.startPeriodValue = startPeriodValue;
	}
	@Formula("(select d.value from Orders o join Dictionary d on o.end_period = d.code where d.key = 'order_period' and o.id = id)")
	public String getEndPeriodValue() {
		return endPeriodValue;
	}

	public void setEndPeriodValue(String endPeriodValue) {
		this.endPeriodValue = endPeriodValue;
	}
	
	@Formula("(select v.name from Villa v where v.id = villa)")
	public String getVillaName() {
		return villaName;
	}

	public void setVillaName(String villaName) {
		this.villaName = villaName;
	}
	
	@Formula("(select m.truename from Member m  where m.id = member)")
	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	@Formula("(select m.phone from Member m where m.id = member)")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Formula("(select m.openid from Member m where m.id = member)")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
	
	
}

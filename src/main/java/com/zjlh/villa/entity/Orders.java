package com.zjlh.villa.entity;

// Generated 2015-8-5 20:46:39 by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Orders generated by hbm2java
 */
@Entity
@Table(name = "orders", catalog = "villa")
public class Orders implements java.io.Serializable {

	private Integer id;
	private Date oederTime;
	private Date payTime;
	private Integer member;
	private Integer ville;
	private Integer store;
	private Date startDay;
	private Integer startPeriod;
	private Date endDay;
	private Integer endPeriod;
	private Float money;
	private Integer state;

	public Orders() {
	}

	public Orders(Date oederTime, Date payTime, Integer member, Integer ville,
			Integer store, Date startDay, Integer startPeriod, Date endDay,
			Integer endPeriod, Float money, Integer state) {
		this.oederTime = oederTime;
		this.payTime = payTime;
		this.member = member;
		this.ville = ville;
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

	@Column(name = "ville")
	public Integer getVille() {
		return this.ville;
	}

	public void setVille(Integer ville) {
		this.ville = ville;
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

	@Column(name = "money", precision = 12, scale = 0)
	public Float getMoney() {
		return this.money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}

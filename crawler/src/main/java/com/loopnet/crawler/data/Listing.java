package com.loopnet.crawler.data;

import org.bson.types.ObjectId;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;

@Entity("listing")
public class Listing {
	
	@Id private ObjectId id;
	private String title;
	private String location;
	private String askingPrice;
	private String grossIncome;
	private String cashFlow;
	private String ebita;
	// fixture, furniture, equipment
	private String ffe;
	private String inventory;
	private String realEstate;
	private String established;
	private String employees;
	private String facilities;
	private String competition;
	private String growth;
	private String financing;
	private String training;
	private String sellingReason;
	private String imageUrl;
	private String sourceUrl;
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAskingPrice() {
		return askingPrice;
	}
	public void setAskingPrice(String askingPrice) {
		this.askingPrice = askingPrice;
	}
	public String getGrossIncome() {
		return grossIncome;
	}
	public void setGrossIncome(String grossIncome) {
		this.grossIncome = grossIncome;
	}
	public String getCashFlow() {
		return cashFlow;
	}
	public void setCashFlow(String cashFlow) {
		this.cashFlow = cashFlow;
	}
	public String getEbita() {
		return ebita;
	}
	public void setEbita(String ebita) {
		this.ebita = ebita;
	}
	public String getFfe() {
		return ffe;
	}
	public void setFfe(String ffe) {
		this.ffe = ffe;
	}
	public String getInventory() {
		return inventory;
	}
	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
	public String getRealEstate() {
		return realEstate;
	}
	public void setRealEstate(String realEstate) {
		this.realEstate = realEstate;
	}
	public String getEstablished() {
		return established;
	}
	public void setEstablished(String established) {
		this.established = established;
	}
	public String getEmployees() {
		return employees;
	}
	public void setEmployees(String employees) {
		this.employees = employees;
	}
	public String getFacilities() {
		return facilities;
	}
	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}
	public String getCompetition() {
		return competition;
	}
	public void setCompetition(String competition) {
		this.competition = competition;
	}
	public String getGrowth() {
		return growth;
	}
	public void setGrowth(String growth) {
		this.growth = growth;
	}
	public String getFinancing() {
		return financing;
	}
	public void setFinancing(String financing) {
		this.financing = financing;
	}
	public String getTraining() {
		return training;
	}
	public void setTraining(String training) {
		this.training = training;
	}
	public String getSellingReason() {
		return sellingReason;
	}
	public void setSellingReason(String sellingReason) {
		this.sellingReason = sellingReason;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getSorceUrl() {
		return sourceUrl;
	}
	public void setSorceUrl(String sorceUrl) {
		this.sourceUrl = sorceUrl;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	
}

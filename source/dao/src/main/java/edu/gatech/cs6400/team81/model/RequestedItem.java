package edu.gatech.cs6400.team81.model;

import java.util.Date;

public class RequestedItem extends BasePOJO {

    private Site site;

    private int requestId;
    private int itemId;
    private int requesteeSiteId;
    private Date ReqDateTime;
    private String userId;
    private RequestedItemStatus status;
    private int numberRequested;
    private int numberFilled;

    private ItemCategory category;

    private Date expireDate;

    private String name;

    private int numberUnits;

    private ItemStorageType storageType;

    private String subCategory;

    private Item item;
    
    public RequestedItem() {

    }

    public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getItemId() {
        return itemId;
    }

    public int getRequesteeSiteId() {
        return requesteeSiteId;
    }

    public void setRequesteeSiteId(int requesteeSiteId) {
        this.requesteeSiteId = requesteeSiteId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Date getReqDateTime() {
        return ReqDateTime;
    }

    public void setReqDateTime(Date reqDateTime) {
        ReqDateTime = reqDateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RequestedItemStatus getStatus() {
        return status;
    }

    public void setStatus(RequestedItemStatus status) {
        this.status = status;
    }

    public int getNumberRequested() {
        return numberRequested;
    }

    public void setNumberRequested(int numberRequested) {
        this.numberRequested = numberRequested;
    }

    public int getNumberFilled() {
        return numberFilled;
    }

    public void setNumberFilled(int numberFilled) {
        this.numberFilled = numberFilled;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberUnits() {
        return numberUnits;
    }

    public void setNumberUnits(int numberUnits) {
        this.numberUnits = numberUnits;
    }

    public ItemStorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(ItemStorageType storageType) {
        this.storageType = storageType;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}

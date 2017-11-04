package com.hgsoft.macao.entity;

public class CardHolderInfo implements java.io.Serializable{
	
	private static final long serialVersionUID = 4755244523479582704L;
	private Long id;
	private Long macaoBankAccountId;
	private String type;
	private Long typeId;
	
	
	public CardHolderInfo() {
		super();
	}

	public CardHolderInfo(Long id, Long macaoBankAccountId, String type,Long typeId) {
		this.id = id;
		this.macaoBankAccountId = macaoBankAccountId;
		this.type = type;
		this.typeId = typeId;
	}

	public Long getMacaoBankAccountId() {
		return macaoBankAccountId;
	}

	public void setMacaoBankAccountId(Long macaoBankAccountId) {
		this.macaoBankAccountId = macaoBankAccountId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	
}

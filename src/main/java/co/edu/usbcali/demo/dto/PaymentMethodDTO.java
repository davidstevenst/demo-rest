package co.edu.usbcali.demo.dto;

import javax.validation.constraints.NotNull;

public class PaymentMethodDTO {

	
	private Integer payId;
	
	@NotNull
	private String enable;
	
	@NotNull
	private String name;

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PaymentMethodDTO(Integer payId, @NotNull String enable, @NotNull String name) {
		super();
		this.payId = payId;
		this.enable = enable;
		this.name = name;
	}
	
	public PaymentMethodDTO() {
		
	}
	
	
}

package co.edu.usbcali.demo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ShoppingCartDTO {

	@NotNull
	private Integer carId;
	

	private Integer items;

	@Size(max = 19)
	private Long total;
	
	@NotNull
	@Size(min = 1, max = 1)
	private String enable;

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public Integer getItems() {
		return items;
	}

	public void setItems(Integer items) {
		this.items = items;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public ShoppingCartDTO(@NotNull Integer carId, Integer items, @Size(max = 19) Long total,
			@NotNull @Size(min = 1, max = 1) String enable) {
		super();
		this.carId = carId;
		this.items = items;
		this.total = total;
		this.enable = enable;
	}
	
	public ShoppingCartDTO() {
		super();
	}
	
	
	
	
}

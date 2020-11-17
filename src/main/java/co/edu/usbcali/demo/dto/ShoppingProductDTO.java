package co.edu.usbcali.demo.dto;

import javax.validation.constraints.NotNull;

public class ShoppingProductDTO {

	
	@NotNull
	private Integer shprId;
	
	private Integer quantity;
	
	private Long total;
	
	private String proId;

	private String name;
	
	private String image;
	
	private Integer price;

	public ShoppingProductDTO(@NotNull Integer shprId, Integer quantity, Long total, String proId, String name,
			String image, Integer price) {
		super();
		this.shprId = shprId;
		this.quantity = quantity;
		this.total = total;
		this.proId = proId;
		this.name = name;
		this.image = image;
		this.price = price;
	}

	public ShoppingProductDTO() {
		super();
	}
	
	
	public Integer getShprId() {
		return shprId;
	}

	public void setShprId(Integer shprId) {
		this.shprId = shprId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	
	
	
}

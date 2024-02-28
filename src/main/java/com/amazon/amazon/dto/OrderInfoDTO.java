package com.amazon.amazon.dto;

import java.util.Map;

public class OrderInfoDTO {
    private String userName;
    private Long orderId;
    private Map<Long, Integer> productCountMap;

    public OrderInfoDTO(String userName, Long orderId, Map<Long, Integer> productCountMap) {
        this.userName = userName;
        this.orderId = orderId;
        this.productCountMap = productCountMap;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Map<Long, Integer> getProductCountMap() {
		return productCountMap;
	}

	public void setProductCountMap(Map<Long, Integer> productCountMap) {
		this.productCountMap = productCountMap;
	}

	public OrderInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}


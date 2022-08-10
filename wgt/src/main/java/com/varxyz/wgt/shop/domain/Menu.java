package com.varxyz.wgt.shop.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Menu {
	private String shopName;
	private String menuName;
	private int menuPrice;
	private String menuIntro;
	private String menuImg;
}

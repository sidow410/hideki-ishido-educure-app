package jp.co.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmartPhonesForm {
	
	private Integer id;		//識別番号
	private String model;	//機種名
	private Integer size;	//容量
	private String color;	//色
	private Integer stock;	//在庫数
	private String os;	//Andorid or iPhone

}
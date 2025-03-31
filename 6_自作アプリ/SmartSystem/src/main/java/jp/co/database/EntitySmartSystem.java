package jp.co.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntitySmartSystem {

	//usersテーブル
	private Integer number;		//管理番号（手動管理、100番台と200番台）
	private String name;	//ユーザー名（日本語表記）
	private String password;	//パスワード
	private String authority;	//権限（マネージャーor従業員）
	
	
	//smart_phonesテーブル
	private Integer id;		//管理番号
	private String model;	//機種名
	private Integer size;		//容量
	private String color;	//色
	private Integer stock;		//在庫数
	private String os;	//種類（Android or iPhone）
	
}
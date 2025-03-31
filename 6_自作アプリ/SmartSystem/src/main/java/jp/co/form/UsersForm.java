package jp.co.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersForm {
	
	private Integer number;		//識別番号（100番台がマネージャー、200番台は従業員）
	private String name;	//名前（日本語表記）
	private String password;	//制限なしパスワード
	private String authority;	//マネージャーor従業員
	
}

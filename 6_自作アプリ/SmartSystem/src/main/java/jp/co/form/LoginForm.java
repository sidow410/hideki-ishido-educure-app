package jp.co.form;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
	
	@NotBlank(message="自分を見失っていませんか？")
	private String name;	//usersテーブル
	
	@NotBlank(message="貴方の存在を証明して下さい")
	private String password;	//usersテーブル
}

package jp.co.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.database.DaoMethod;
import jp.co.database.EntitySmartSystem;
import jp.co.form.SmartPhonesForm;
import jp.co.form.UsersForm;

@Controller
public class ResultController {
	
	@Autowired
	private DaoMethod dao;
	
	@Autowired
	HttpSession session;
	
	
	
	@GetMapping("/soldOut")
	public String soldOut(Model model) {
		
		List<EntitySmartSystem> soldOut = dao.soldOut();
		model.addAttribute("soldOutList", soldOut);
		return "soldOut";
	}
	
	
	
	@GetMapping("/function")
	public String allSearch(Model model) {
		
		List<EntitySmartSystem> allSearchSmartPhones = dao.allSearchSmartPhones();
		model.addAttribute("searchResult", allSearchSmartPhones);
		return "function";
	}
	
	
	
	@PostMapping("/function")
	public String function(@ModelAttribute("function")SmartPhonesForm form,
			@RequestParam(value = "btn")String btn, Model model) {
		
		
		if(btn.equals("Android")) {
			
			List<EntitySmartSystem> android = dao.androidSearch();
			model.addAttribute("searchResult", android);
			model.addAttribute("success","Android機種のみを表示しています。お目が高い！");
			return "function";
			
		}else if(btn.equals("iPhone")) {
			
			List<EntitySmartSystem> iPhone = dao.iPhoneSearch();
			model.addAttribute("searchResult", iPhone);
			model.addAttribute("success","iPhone機種のみを表示しています。マジョリティー！");
			return "function";
			
		}
		
		
		
		Integer id = form.getId();
		boolean idCheck = true;
		if(id != null) {
			idCheck = dao.idCheck(id);	//trueであればinsert出来る。falseだとupdateとdelete出来る。
		}
		String phonesModel = form.getModel();
		Integer size = form.getSize();
		String color = form.getColor();
		Integer stock = form.getStock();
		String os = form.getOs();
		
		
		//①(1_true)検索ボタンが押された場合、引数を条件とした検索結果を表示する
		if(btn.equals("search")) {
			
			List<EntitySmartSystem> select = dao.selectSmartPhones(phonesModel, size, os);
			
			//(2_true)リストの中身（検索結果）があった場合、検索結果とメッセージを表示する
			if(select != null && !select.isEmpty()) {
				model.addAttribute("searchResult", select);
				model.addAttribute("success","貴方が必要とした結果が表示されている事を祈ります");
				return "function";
				
			//(2_false)リストの中身が空（検索結果がなかった）場合、メッセージのみを表示する
			}else {
				model.addAttribute("error","ごめんなさい…貴方が探している答えを見つける事は出来ませんでした…");
				return "function";
			}
			
			
		//②(1_true)登録ボタンが押された場合
		}else if(btn.equals("insert")) {
			
			//(2_true)idが被っていなかった場合
			if(idCheck) {
				
				//(3_true)全ての項目が入力されている場合、登録して全検索結果を表示する
				if(id != null
					&& phonesModel != null && !phonesModel.isEmpty()
					&& size != null
					&& color != null && !color.isEmpty()
					&& stock != null
					&& os != null && !os.isEmpty()) {
					
					dao.insertSmartPhones(id, phonesModel, size, color, stock, os);
					model.addAttribute("success","貴方の想いが追加されました。是非その目で確かめて下さい");
					List<EntitySmartSystem> all = dao.allSearchSmartPhones();
					model.addAttribute("searchResult", all);
					return "function";
					
				//(3_false)入力されていない項目が1つでもある場合、メッセージを表示する
				}else {	
					model.addAttribute("error", "何か忘れている項目があります。慌てず、落ち着いて");
					return "function";
				}
				
			//(2_false)idが既存の場合、メッセージを表示する
			}else {
				model.addAttribute("error", "貴方が求めたidは、先に他の誰かが必要としていたようです");
				return "function";
			}
			
		//③(1_true)更新ボタンが押された場合
		}else if(btn.equals("update")) {
			
			//(2_true)該当するidが存在しなかった場合、メッセージを表示する
			if(idCheck) {
				model.addAttribute("error", "idが存在していないようです。見間違えていませんか？");
				return "function";
				
			//(2_false)該当idが存在した場合
			}else {
				
				//(3-true)stockがnullじゃない場合、該当idのstock数を更新して全検索結果を表示する
				if(stock != null) {
					dao.updateSmartPhones(id, stock);
					model.addAttribute("success","貴方の願いが反映されました。ご確認をお願いします");
					List<EntitySmartSystem> all = dao.allSearchSmartPhones();
					model.addAttribute("searchResult", all);
					return "function";
					
				//(3_false)stockに値がない場合、メッセージを表示する
				}else {
					model.addAttribute("error", "どうやら変更すべき数値が入力されていないようです…");
					return "function";
				}
			}
			
		//④(1_true)削除ボタンが押された場合
		}else if(btn.equals("delete")) {
			
			//(2_true)該当するidが存在しなかった場合、メッセージを表示する
			if(idCheck) {
				model.addAttribute("error", "そのidは既に存在していないようです…");
				return "function";
				
			//(2_false)該当idが存在した場合、その情報を削除して全検索結果を表示する
			}else {
				dao.deleteSmartPhones(id);
				model.addAttribute("success","貴方の要望通り、不要な項目を排除しました。弔いをしてあげて下さい");
				List<EntitySmartSystem> all = dao.allSearchSmartPhones();
				model.addAttribute("searchResult", all);
				return "function";
			}
		
		//⑤(1_false)販売ボタンが押された場合、押されたidのstock値を-1して全検索結果を表示する
		}else {
			//どうにかしてidを受け取る。下記変数のidは変更必須。
			Integer sell = Integer.parseInt(btn);
			dao.reduce(sell);
			model.addAttribute("success","商品を販売していただきありがとうございます！");
			List<EntitySmartSystem> all = dao.allSearchSmartPhones();
			model.addAttribute("searchResult", all);
			return "function";
		}
	}
	
	
	
	@GetMapping("/users")
	public String allUsers(Model model) {
		
		List<EntitySmartSystem> allUsers = dao.allUsers();
		model.addAttribute("usersResult", allUsers);
		return "users";
	}
	
	
	
	@PostMapping("/users")
	public String users(@ModelAttribute("users")UsersForm form,
			@RequestParam(value = "btn")String btn, Model model) {
		
		Integer number = form.getNumber();
		boolean numberCheck = true;
		if(number != null) {
			numberCheck = dao.numberCheck(number);	//trueであればinsert出来る。falseだとupdateとdelete出来る。
		}
		
		String name = form.getName();
		String password = form.getPassword();
		String authority = form.getAuthority();
		
		//①(1_true)検索ボタンが押された場合、引数を条件とした検索結果を表示する
		if(btn.equals("search")) {
			
			List<EntitySmartSystem> select = dao.selectUsers(number, name, authority);
			
			//(2_true)リストの中身（検索結果）があった場合、検索結果とメッセージを表示する
			if(select != null && !select.isEmpty()) {
				model.addAttribute("usersResult", select);
				model.addAttribute("success","貴方が必要とした結果が表示されている事を祈ります");
				return "users";
				
			//(2_false)リストの中身が空（検索結果がなかった）場合、メッセージのみを表示する
			}else {
				model.addAttribute("error","ごめんなさい…貴方が探している答えを見つける事は出来ませんでした…");
				return "users";
			}
		
		//②(1_true)登録ボタンが押された場合
		}else if(btn.equals("insert")) {
			
			//(2_true)idが被っていなかった場合
			if(numberCheck) {
				
				//(3_true)全ての項目が入力されている場合、登録して全検索結果を表示する
				if(number != null 
					&& !name.equals(null) && !name.isEmpty()
					&& !password.equals(null) && !password.isEmpty()
					&& !authority.equals(null) && !authority.isEmpty()) {
					
					dao.insertUsers(number, name, password, authority);
					model.addAttribute("success","貴方の想いが追加されました。是非その目で確かめて下さい");
					List<EntitySmartSystem> allUsers = dao.allUsers();
					model.addAttribute("usersResult", allUsers);
					return "users";
					
				//(3_false)入力されていない項目が1つでもある場合、メッセージを表示する
				}else {
					model.addAttribute("error", "何か忘れている項目があります。慌てず、落ち着いて");
					return "users";
				}
			
			//(2_false)idが既存の場合、メッセージを表示する
			}else {
				model.addAttribute("error", "貴方が求めたidは、先に他の誰かが必要としていたようです");
				return "users";
			}
			
		//③(1_true)更新ボタンが押された場合
		}else if(btn.equals("update")) {
				
			//(2_true)該当するidが存在しなかった場合、メッセージを表示する
			if(numberCheck) {
				model.addAttribute("error", "idが存在していないようです。見間違えていませんか？");
				return "users";
				
			//(2_false)該当idが存在した場合
			}else {
				
				//(3-true)全ての項目がnullの場合、メッセージを表示する
				if((name.equals(null) || name.isEmpty())
					&& (password.equals(null) || password.isEmpty())
					&& (authority.equals(null) || authority.isEmpty())) {
					
					model.addAttribute("error", "何を変更すれば良いのか分かりません…");
					return "users";
					
				//(3_false)何かしらの項目が入力されている場合、それらを更新して全検索結果を表示する
				}else {
					dao.updateUsers(number, name, password, authority);
					model.addAttribute("success","貴方の願いが反映されました。ご確認をお願いします");
					List<EntitySmartSystem> all = dao.allUsers();
					model.addAttribute("usersResult", all);
					return "users";
				}
			}
		
		//④(1_true)削除ボタンが押された場合
		}else if(btn.equals("delete")) {
			
			//(2_true)該当するidが存在しなかった場合、メッセージを表示する
			if(numberCheck) {
				model.addAttribute("error", "そのidは既に存在していないようです…");
				return "users";
				
			//(2_false)該当idが存在した場合、その情報を削除して全検索結果を表示する
			}else {
				dao.deleteUsers(number);
				model.addAttribute("success","貴方の要望通り、不要な項目を排除しました。弔いをしてあげて下さい");
				List<EntitySmartSystem> all = dao.allUsers();
				model.addAttribute("usersResult", all);
				return "users";
			}
		}
		
		//(1_false)到達しない処理。(btn.equals("delete")を削除する事で必要無くなる
		return "users";
	}
	
	
}

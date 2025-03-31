package jp.co.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class DaoMethod {
	
	//このクラスでは、以下のデータベースを扱うものとする
	static String url = "jdbc:postgresql:smart_system";
	static String user = "hogeuser";
	static String dbPassword = "hoge";
	
	
	
	
	//①nameで検索し取得したパスワードがpasswordと一致していればtrueを返す
	public boolean loginCheck(String name, String password) {
			
			boolean loginCheck = false;
			String pass = null;
			ResultSet rs = null;
			
			String loginCheckSql = "SELECT password FROM users WHERE name = ?";
			
			try(Connection con = DriverManager.getConnection(url,user,dbPassword);
					PreparedStatement stmt = con.prepareStatement(loginCheckSql)){
				
				stmt.setString(1, name);
				rs = stmt.executeQuery();
				
				if(rs.next()) {
					pass = rs.getString("password");
				}
				
				if(password.equals(pass)){
					loginCheck = true;
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			return loginCheck;
		}
	
	
	//②usersテーブルのnameが合致するauthorityを取得する（マネージャーor従業員）
	public String usersAuthority(String name) {
		
		String authority = null;
		ResultSet rs = null;
		
		String usersAuthoritySql = "SELECT authority FROM users WHERE name = ?";
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(usersAuthoritySql)){
			
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				authority = rs.getString("authority");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return authority;
	}
	
	
	//③smart_phonesテーブルの全情報を取得する
 	public List<EntitySmartSystem> allSearchSmartPhones(){
		
		List<EntitySmartSystem> list = new ArrayList<>();
		ResultSet rs = null;
		
		String allSearchSmartPhonesSql = "SELECT * FROM smart_phones ORDER BY id";
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(allSearchSmartPhonesSql)){
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				EntitySmartSystem dto = new EntitySmartSystem();
				dto.setId(rs.getInt("id"));
				dto.setModel(rs.getString("model"));
				dto.setSize(rs.getInt("size"));
				dto.setColor(rs.getString("color"));
				dto.setStock(rs.getInt("stock"));
				dto.setOs(rs.getString("os"));
				list.add(dto);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	//④smart_phonesテーブルのos = Androidの情報を取得する
	public List<EntitySmartSystem> androidSearch(){
		
		List<EntitySmartSystem> list = new ArrayList<>();
		ResultSet rs = null;
		
		String androidSearchSql = "SELECT * FROM smart_phones WHERE os = 'Android' ORDER BY id";
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(androidSearchSql)){
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				EntitySmartSystem dto = new EntitySmartSystem();
				dto.setId(rs.getInt("id"));
				dto.setModel(rs.getString("model"));
				dto.setSize(rs.getInt("size"));
				dto.setColor(rs.getString("color"));
				dto.setStock(rs.getInt("stock"));
				dto.setOs(rs.getString("os"));
				list.add(dto);
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
	//⑤smart_phonesテーブルのos = iPhoneの情報を取得する
	public List<EntitySmartSystem> iPhoneSearch(){
			
			List<EntitySmartSystem> list = new ArrayList<>();
			ResultSet rs = null;
			
			String iPhoneSearchSql = "SELECT * FROM smart_phones WHERE os = 'iPhone' ORDER BY id";
			
			try(Connection con = DriverManager.getConnection(url,user,dbPassword);
					PreparedStatement stmt = con.prepareStatement(iPhoneSearchSql)){
				
				rs = stmt.executeQuery();
				
				while(rs.next()) {
					EntitySmartSystem dto = new EntitySmartSystem();
					dto.setId(rs.getInt("id"));
					dto.setModel(rs.getString("model"));
					dto.setSize(rs.getInt("size"));
					dto.setColor(rs.getString("color"));
					dto.setStock(rs.getInt("stock"));
					dto.setOs(rs.getString("os"));
					list.add(dto);
				}
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			return list;
		}
	
	
	
	//⑥引数を条件としてsmart_phonesテーブルの情報を検索する条件がない場合は全検索）
	public List<EntitySmartSystem> selectSmartPhones(String model, Integer size, String os){
		
		List<EntitySmartSystem> list = new ArrayList<>();
		List<Object> param = new ArrayList<>();
		ResultSet rs = null;
		
		String selectSmartPhonesSql = "SELECT * FROM smart_phones WHERE 1=1";
		
		if(model != null && !model.isEmpty()) {
			selectSmartPhonesSql += " AND model LIKE ?";
			param.add("%" + model + "%");
		}
		
		if(size != null) {
			selectSmartPhonesSql += " AND size = ?";
			param.add(size);
		}
		
		if(os != null && !os.isEmpty()) {
			selectSmartPhonesSql += " AND os = ?";
			param.add(os);
		}
		
		selectSmartPhonesSql += " ORDER BY id";
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(selectSmartPhonesSql)){
			
			for (int i = 0; i < param.size(); i++) {
				stmt.setObject(i + 1, param.get(i));
			}
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				EntitySmartSystem dto = new EntitySmartSystem();
				dto.setId(rs.getInt("id"));
				dto.setModel(rs.getString("model"));
				dto.setSize(rs.getInt("size"));
				dto.setColor(rs.getString("color"));
				dto.setStock(rs.getInt("stock"));
				dto.setOs(rs.getString("os"));
				list.add(dto);
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
	//⑦smart_phonesテーブルにnumberが存在しなければtrueを返す（被っている場合はfalseを返す）
	public boolean idCheck(Integer id) {
		
		boolean idCheck = true;
		Integer nowId = null;
		ResultSet rs = null;
		
		String idCheckSql = "SELECT id FROM smart_phones WHERE id = ?";
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(idCheckSql)){
			
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			//得る結果が1つの場合はwhileではなくifのが良い。
			if(rs.next()) {
				nowId = rs.getInt("id");
			}
			
			//1000以上の数値を扱う場合は演算子(==)ではなく.equals()メソッドのが良い。
			if(nowId.equals(id)){
				idCheck = false;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return idCheck;
	}
	
	
	
	//⑧引数の情報をsmart_phonesテーブルに登録する
	public void insertSmartPhones(Integer id, String model, Integer size, String color, Integer stock, String os) {
		
		String insertSmartPhonesSql = "INSERT INTO smart_phones(id, model, size, color, stock, os) VALUES(?,?,?,?,?,?)";
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(insertSmartPhonesSql)){
			
			stmt.setInt(1, id);
			stmt.setString(2, model);
			stmt.setInt(3, size);
			stmt.setString(4, color);
			stmt.setInt(5, stock);
			stmt.setString(6, os);
			stmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//⑨idが合致するsmart_phonesテーブルに引数（在庫）の情報を反映（更新）する
	public void updateSmartPhones(Integer id,Integer stock) {
		
		String updateSmartPhonesSql = "UPDATE smart_phones SET stock = ? WHERE id = ?";
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(updateSmartPhonesSql)){
			
			stmt.setInt(1, stock);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//⑩idが合致するsmart_phonesテーブルの情報を削除する
	public void deleteSmartPhones(Integer id) {
		
		String deleteSmartPhones = "DELETE FROM smart_phones WHERE id = ?";
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(deleteSmartPhones)){

			stmt.setInt(1, id);
			stmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//⑪smart_phonesテーブルで引数が合致するstockの値を-1する
	public void reduce(Integer id) {
		
		String reduceSql = "UPDATE smart_phones SET stock = stock - 1 WHERE id = ? AND stock > 0";
		
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(reduceSql)){
			
			stmt.setInt(1, id);
			stmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	//⑫smart_phonesテーブルのstock = 0の情報を取得する
	public List<EntitySmartSystem> soldOut(){
		
		List<EntitySmartSystem> list = new ArrayList<>();
		ResultSet rs = null;
		
		String soldOutSql = "SELECT * FROM smart_phones WHERE stock = 0";
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(soldOutSql)){
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				EntitySmartSystem dto = new EntitySmartSystem();
				dto.setId(rs.getInt("id"));
				dto.setModel(rs.getString("model"));
				dto.setSize(rs.getInt("size"));
				dto.setColor(rs.getString("color"));
				dto.setStock(rs.getInt("stock"));
				dto.setOs(rs.getString("os"));
				list.add(dto);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
	//⑬usersテーブルの全情報を取得する
	public List<EntitySmartSystem> allUsers(){
		
		List<EntitySmartSystem> list = new ArrayList<>();
		ResultSet rs = null;
		
		String allUsersSql = "SELECT * FROM users ORDER BY number";
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(allUsersSql)){
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				EntitySmartSystem dto = new EntitySmartSystem();
				dto.setNumber(rs.getInt("number"));
				dto.setName(rs.getString("name"));
				dto.setPassword(rs.getString("password"));
				dto.setAuthority(rs.getString("authority"));
				list.add(dto);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
	//⑭引数を条件としてusersテーブルの情報を検索する（条件がない場合は全検索）
	public List<EntitySmartSystem> selectUsers(Integer number, String name, String authority){
			
		List<EntitySmartSystem> list = new ArrayList<>();
		List<Object> param = new ArrayList<>();
		ResultSet rs = null;
		
		String selectUsersSql = "SELECT * FROM users WHERE 1=1";
		
		if(number != null) {
			selectUsersSql += " AND number = ?";
			param.add(number);
		}
			
		if(name != null && !name.isEmpty()) {
			selectUsersSql += " AND name LIKE ?";
			param.add("%" + name + "%");
		}
		
		if(authority != null && !authority.isEmpty()) {
			selectUsersSql += " AND authority = ?";
			param.add(authority);
		}
		
		selectUsersSql += " ORDER BY number";
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(selectUsersSql)){
			
			for (int i = 0; i < param.size(); i++) {
				stmt.setObject(i + 1, param.get(i));
			}
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				EntitySmartSystem dto = new EntitySmartSystem();
				dto.setNumber(rs.getInt("number"));
				dto.setName(rs.getString("name"));
				dto.setPassword(rs.getString("password"));
				dto.setAuthority(rs.getString("authority"));
				list.add(dto);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
	//⑮usersテーブルにnumberが存在しなければtrueを返す（被っている場合はfalseを返す）
	public boolean numberCheck(Integer number) {
		
		boolean numberCheck = true;
		Integer num = null;
		ResultSet rs = null;
		
		String numberCheckSql = "SELECT number FROM users WHERE number = ?";
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(numberCheckSql)){
			
			stmt.setInt(1, number);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				num = rs.getInt("number");
			}
			
			if(num == number){
				numberCheck = false;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return numberCheck;
	}
	
	
	
	//⑯引数の情報をusersテーブルに登録する
	public void insertUsers(Integer number, String name, String password, String authority){
		
		String insertUsersSql = "INSERT INTO users(number, name, password, authority) VALUES(?,?,?,?)";
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(insertUsersSql)){
			
			stmt.setInt(1, number);
			stmt.setString(2, name);
			stmt.setString(3, password);
			stmt.setString(4, authority);
			stmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//⑰idが合致するusersテーブルに引数の情報を反映（更新）する
	public void updateUsers(Integer number, String name, String password, String authority) {
		
		List<Object> param = new ArrayList<>();
		boolean check = false;
		
		String updateUsersSql = "UPDATE users SET";
		
		if(name != null && !name.isEmpty()) {
			updateUsersSql += " name = ?";
			param.add(name);
			check = true;
		}
		
		if(password != null && !password.isEmpty()) {
			
			if(check) {
				updateUsersSql += ",";
			}
			
			updateUsersSql += " password = ?";
			param.add(password);
			check = true;
		}
		
		if(authority != null && !authority.isEmpty()) {
			
			if(check) {
				updateUsersSql += ",";
			}
			
			updateUsersSql += " authority = ?";
			param.add(authority);
		}
		
		updateUsersSql += " WHERE number = ?";
		param.add(number);
				
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(updateUsersSql)){
			
			for (int i = 0; i < param.size(); i++) {
				stmt.setObject(i + 1, param.get(i));
			}
			
			stmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//⑱引数が合致するusersテーブルの情報を削除する
	public void deleteUsers(Integer number) {
		
		String deleteUsers = "DELETE FROM users WHERE number = ?";
		
		try(Connection con = DriverManager.getConnection(url,user,dbPassword);
				PreparedStatement stmt = con.prepareStatement(deleteUsers)){

			stmt.setInt(1, number);
			stmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}

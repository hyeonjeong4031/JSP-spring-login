package user;
// commend + shift + o 하면 자동 import 된다! 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	
	// db 접근을 위한 객체 
	private Connection conn;
	
	private PreparedStatement pstmt;
	
	//어떠한 정보를 담을 수 있는 객체  
	private ResultSet rs;

	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPassword = "1111";
			// driver -  db에 접근 가능하도록 매개체 역할을하는 하나의 라이브러리다. 
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}catch(Exception e) {
			e.printStackTrace();
			}
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try {
			//🔰 PreparedStatement에 어떠한 정해진 sql문장을 데이터베이스에 삽입하는 형식으로 인스턴스를 가져옴 
			pstmt = conn.prepareStatement(SQL);
//		가장 중요한 부분 - set으로  1, userID 넣어줌  - 기본적으로 SQL Injection같은 해킹 기법을 막아줌 
			// 매개변수로 들어온 내용을 ? 에 해당하는 부분에 넣어준다.
			pstmt.setString(1, userID);
			// 결과를 담을 수 있는 객체에 실행 결과를   넣어준다.
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1;
				}else {
					return 0; // 비밀번호 불일
				}
			}
			return -1; //아이디가 없음   
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -2; // db 오류  

	}
}

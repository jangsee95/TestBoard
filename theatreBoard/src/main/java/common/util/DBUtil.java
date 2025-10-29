package common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private final String url = System.getenv("DB_URL");
	private final String username = System.getenv("DB_USERNAME"); 
	private final String password = System.getenv("DB_PASSWORD");
	
	// private final String url = "jdbc:mysql://localhost:3306/theatre_db";
	// private final String username = "theatre_user"; 
	// private final String password = "Abcd1234!";
	
	private final String driverName = "com.mysql.cj.jdbc.Driver";
	/**
	 * Singleton Design Pattern을 적용해준다.
	 */
	private static DBUtil instance = new DBUtil();

private DBUtil() {
        try {
            Class.forName(driverName);
				System.out.println("System.getenv(DB_URL): " + System.getenv("DB_URL"));
      			System.out.println("System.getenv(DB_USER): " + System.getenv("DB_USERNAME"));
        		System.out.println("System.getenv(DB_PASS): " + System.getenv("DB_PASSWORD"));
        } catch (ClassNotFoundException e) {
            // 환경 변수가 설정되지 않았을 경우를 대비한 로깅 추가 (선택 사항)
            if (url == null || username == null || password == null) {


                System.err.println("DB 연결 환경 변수(DB_URL, DB_USER, DB_PASSWORD)가 설정되지 않았습니다.");
            }
            e.printStackTrace();
        }
    }

	public static DBUtil getInstance() {
		return instance;
	}

	/**
	 * DriverManager를 통해 Connection을 생성하고 반환한다.
	 *
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {

        // 환경 변수가 null이면 연결 시도 전에 예외 발생 (더 명확한 오류 처리)
        if (url == null || username == null || password == null) {
            throw new SQLException("DB 연결 환경 변수가 설정되지 않았습니다.");
        }
        return DriverManager.getConnection(url, username, password);
    }

	/**
	 * 사용한 리소스들을 정리한다. Connection, Statement, ResultSet 모두 AutoCloseable 타입이다. ... 을
	 * 이용하므로 필요에 따라서 select 계열 호출 후는 ResultSet, Statement, Connection dml 호출 후는
	 * Statement, Connection 등 다양한 조합으로 사용할 수 있다.
	 *
	 * @param closeables
	 */
	public void close(AutoCloseable... closeables) {
		for (AutoCloseable c : closeables) {
			if (c != null) {
				try {
					c.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		// DBUtil 인스턴스를 가져옵니다.
		DBUtil util = DBUtil.getInstance();

		// Connection 객체를 담을 변수를 선언합니다.
		Connection conn = null;

		System.out.println("데이터베이스 연결 테스트를 시작합니다...");

		try {
			// 실제로 연결을 시도합니다!
			conn = util.getConnection();

			// 이 라인이 실행되면 연결에 성공한 것입니다.
			System.out.println("연결 성공! Connection 객체: " + conn);

		} catch (SQLException e) {
			// 연결 실패 시 예외가 발생합니다.
			System.err.println("연결 실패! 오류 메시지:");
			// 어떤 오류 때문에 실패했는지 자세히 출력해줍니다. (매우 중요!)
			e.printStackTrace();

		} finally {
			// 테스트가 끝난 후 연결을 닫아줍니다.
			if (conn != null) {
				try {
					conn.close();
					System.out.println("연결을 성공적으로 닫았습니다.");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
package dao;
import utils.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//基类：数据库操作通用类
public class BaseDao {
//	private Logger log = Logger.getLogger(BaseDao.class);

    protected Connection conn =null;
    protected PreparedStatement pstmt = null;

    //通用更新方法
    public int exceuteUpdate(String preparedSql, Object... param) {
        int num = 0;
        try {
            conn = DBUtil.getDataSource();
            pstmt = conn.prepareStatement(preparedSql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    // 为预编译sql设置参数
                    pstmt.setObject(i + 1, param[i]);
                }
            }
            num = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, null);
        }
        return num;
    }

    //通用查询方法
    public ResultSet executeQuery(String sql, Object... params) {
        ResultSet rs = null;
        try {
            conn = DBUtil.getDataSource();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            rs = pstmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //连接方法
    public static void main(String[] args) throws Exception {
        DBUtil dbUtil = new DBUtil();
        Connection conn =dbUtil.getDataSource();
        if (conn!=null) {
            System.out.println("连接数据库成功!");
        }

    }

    //通用关闭方法
    public void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        // 若结果集对象不为空，则关闭
        if (rs != null) {
            try {
                rs.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 若Statement对象不为空，则关闭
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 若数据库连接对象不为空，则关闭
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

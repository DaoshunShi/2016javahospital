package test;

import org.junit.Test;

import com.mysql.jdbc.CallableStatement;

import java.sql.ResultSet;  
import java.sql.SQLException;
import java.sql.Types; 

public class JdbcTest3 {

		// TODO Auto-generated method stub
		 // 测试SqlHelper  
	    @Test//每一个测试的方法前都要加@Test  
	    public void testSqlHelper1() {// 一条SQL语句insert/update/delete  
	        testInsert();  
	        testUpdate();  
	        testDelete();  
	    }  
	  
	    @Test  
	    public void testSqlHelper2() {// 测试一个事务的提交  
	        testUpdateMuti();  
	    }  
	  
	    @Test  
	    public void testSqlHelper3() {// 测试SQl的Select语句  
	        testQuery();  
	    }  
	  
	    @Test  
	    public void testSqlHelper4() {// 测试调用无返回值的存储过程  
	        testInsertProc();  
	        testUpdateProc();  
	        testDeleteProc();  
	    }  
	  
	    @Test  
	    public void testSqlHelper5() {// 测试调用有返回值的存储过程  
	        testCallProcOutput();  
	        testCallProcInput();  
	    }  
	  
	    private void testCallProcInput() {  
	        ResultSet rs = null;  
	        try {  
	            String sql = "{call proc_userinfo_findByUsername(?)}";  
	            String[] in = { "Tom" };  
	            // Integer[] out ={Types.INTEGER};  
	            CallableStatement cs = (CallableStatement) MySQLHelper.callProcInput(  
	                    sql, in);  
	            rs = cs.executeQuery();  
	            while (rs.next()) {  
	                System.out.println("username:" + rs.getString(2)  
	                        + "\tpassword:" + rs.getString(3) + "\tsalary:"  
	                        + rs.getDouble(5));  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            throw new RuntimeException(e.getMessage());  
	        } finally {  
	            MySQLHelper.close(rs, MySQLHelper.getCs(), MySQLHelper.getConn());  
	        }  
	    }  
	  
	    private void testCallProcOutput() {  
	        ResultSet rs = null;  
	        try {  
	            String sql = "{call proc_userinfo_getCount(?)}";  
	            Integer[] out = { Types.INTEGER };  
	            CallableStatement cs = (CallableStatement) MySQLHelper  
	                    .callProcOutput(sql, out);  
	            rs = cs.executeQuery();  
	            while (rs.next()) {  
	                System.out.println("Record numbers:"+rs.getInt(1));  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            throw new RuntimeException(e.getMessage());  
	        } finally {  
	        	MySQLHelper.close(rs, MySQLHelper.getCs(), MySQLHelper.getConn());  
	        }  
	    }  
	  
	    private void testDeleteProc() {  
	        String sql = "{call proc_userinfo_delete(?)}";  
	        String[] parameters = { "Jim" };  
	        MySQLHelper.callProc(sql, parameters);  
	    }  
	  
	    private void testUpdateProc() {  
	        String sql = "{call proc_userinfo_update(?,?,?)}";  
	        String[] parameters = { "Lucy", "ncist", "5200.00" };  
	        MySQLHelper.callProc(sql, parameters);  
	    }  
	  
	    private void testInsertProc() {  
	        String sql = "{call proc_userinfo_insert(?,?,?,?)}";  
	        String[] parameters = { "wYan", "wyan7", "female", "5600.00" };  
	        MySQLHelper.callProc(sql, parameters);  
	    }  
	  
	    private void testUpdateMuti() {  
	        String sql1 = "UPDATE userinfo SET salary=salary-100 WHERE username = ?";  
	        String sql2 = "UPDATE userinfo SET salary=salary+100 WHERE username = ?";  
	        String[] sql = { sql1, sql2 };  
	        String[] sql1_params = { "Tom" };  
	        String[] sql2_params = { "Jim" };  
	        String[][] parameters = { sql1_params, sql2_params };  
	        MySQLHelper.executeUpdateMultiParams(sql, parameters);  
	    }  
	  
	    private void testInsert() {  
	        String sql = "INSERT INTO userinfo (username,password,gender,salary) VALUES (?,?,?,?)";  
	        String[] parameters = { "wqiang", "wYan", "male", "6000.00" };  
	        MySQLHelper.executeUpdate(sql, parameters);  
	    }  
	  
	    private void testUpdate() {  
	        String sql = "UPDATE userinfo SET password=?,salary=? WHERE username = 'Jim'";  
	        String[] parameters = { "xaut", "6500.00" };  
	        MySQLHelper.executeUpdate(sql, parameters);  
	    }  
	  
	    private void testDelete() {  
	        String sql = "DELETE FROM userinfo WHERE username = ?";  
	        String[] parameters = { "xiaoqiang" };  
	        MySQLHelper.executeUpdate(sql, parameters);  
	    }  
	  
	    private void testQuery() {  
	        String sql = "SELECT * FROM userinfo";  
	        try {  
	            ResultSet rs = MySQLHelper.executeQuery(sql, null);  
	            while (rs.next()) {  
	                System.out.println("userName:" + rs.getString("userName")  
	                        + "\tpassword:" + rs.getString("password")  
	                        + "\tgender:" + rs.getString("gender") + "\tsalary:"  
	                        + rs.getDouble("salary"));  
	            }  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        } finally {  
	        	MySQLHelper.close(MySQLHelper.getRs(), MySQLHelper.getPs(), MySQLHelper  
	                    .getConn());  
	        }  
	    }  
	}  

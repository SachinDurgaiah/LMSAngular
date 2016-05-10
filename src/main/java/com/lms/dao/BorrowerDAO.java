package com.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.lms.entity.Author;
import com.lms.entity.Book;
import com.lms.entity.BookLoans;
import com.lms.entity.Borrower;
import com.lms.entity.LibraryBranch;
import com.lms.entity.Publisher;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

public class BorrowerDAO extends BaseDAO implements ResultSetExtractor<List<Borrower>>{


	@SuppressWarnings("unchecked")
	public List<Borrower> readAllBorrower(int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		System.out.println("In the borrower dao");
		return (List<Borrower>) template.query("select * from tbl_borrower", this);
	}
	
	public List<Borrower> readAllBorrower() throws ClassNotFoundException, SQLException{
		
		System.out.println("In the borrower dao");
		return (List<Borrower>) template.query("select * from tbl_borrower",this);
	}
	
@SuppressWarnings("unchecked")
public List<Borrower> readAllBorrower(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
	setPageNo(pageNo);
	String name = "%"+searchString+"%";
		System.out.println("In the borrower1 dao");
		return (List<Borrower>) template.query("select * from tbl_borrower where name like ? or address like ?", new Object[] {name,name},this);
	}
	
public List<Borrower> readAllBorrowerByName(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
	setPageNo(pageNo);
	String name = "%"+searchString+"%";
		System.out.println("In the borrower1 dao");
		return (List<Borrower>) template.query("select * from tbl_borrower where name like ? ", new Object[] {name},this);
	}

public List<Borrower> readAllBorrowerByAddress(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
	setPageNo(pageNo);
	String name = "%"+searchString+"%";
		System.out.println("In the borrower1 dao");
		return (List<Borrower>) template.query("select * from tbl_borrower where address like ? ", new Object[] {name},this);
	}

	public Integer getCount() throws ClassNotFoundException, SQLException{
		//return getCount("select count(*) from tbl_borrower",null);
		
		SqlRowSet count=template.queryForRowSet("select count(*) from tbl_borrower");
        int res = count.getInt("count(*)");
	 
		return res;
	}
	
	public boolean verifyBorrower(int cardNo) throws ClassNotFoundException, SQLException{
		List<Borrower> b=template.query("select * from tbl_borrower where cardNo=?", new Object[] {cardNo},this);
		if(b!=null){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public void addBorrower(String borrowerName,String borrowerAddress,String borrowerPhone) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_borrower (name, address, phone) values (?, ?, ?)", new Object[] {borrowerName, borrowerAddress, borrowerPhone});
	}
	

	public int CheckCard(Integer cardNo) throws ClassNotFoundException, SQLException {
		System.out.println("In borrower Dao "+cardNo);
		//return getCount("select count(*) from tbl_borrower where cardNo = ?",new Object[] {cardNo});
		List<Borrower> count=template.query("select * from tbl_borrower where cardNo = ?",new Object[] {cardNo}, this);
        int res = count.size();
	 System.out.println("res "+res);
		return res;
		
	}


	public void deleteBorrower(Integer cardNo) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		template.update("delete from tbl_borrower where cardNo = ?", new Object[] {cardNo});
		
	}

	public void updateBorrower(String name, String address, String phone, int card_no ) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("in update borrower");
		template.update("update tbl_borrower set name = ?, address=?, phone=? where cardNo = ?", new Object[] {name,address,phone,card_no});
		return;
	}



	

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		System.out.println("In the extract borrower dao");
		List<Borrower> br = new ArrayList<Borrower>();
		while(rs.next()){
			Borrower b = new Borrower();
			b.setCardNo(rs.getInt("cardNo"));
			b.setName(rs.getString("name"));
			b.setAddress(rs.getString("address"));
			b.setPhone(rs.getString("phone"));
		
			br.add(b);
		}
		return br;
	}

	
	public Borrower readBorrowerByID(Integer cardNo) throws ClassNotFoundException, SQLException {
		
		System.out.println("In the card verification");
		List<Borrower> borr = (List<Borrower>) template.query("select * from tbl_borrower where cardNo = ?", new Object[] {cardNo},this);
		if(borr!=null && borr.size() >0){
			return borr.get(0);
		}
		return null;
	}

}

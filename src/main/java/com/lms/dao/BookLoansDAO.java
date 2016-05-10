package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.lms.dao.BaseDAO;
import com.lms.entity.Author;
import com.lms.entity.Book;
import com.lms.entity.BookLoans;
import com.lms.entity.Borrower;
import com.lms.entity.LibraryBranch;
import com.lms.entity.Publisher;

public class BookLoansDAO extends BaseDAO implements ResultSetExtractor<List<BookLoans>>{

	

	

	//checking for a book if they are already checked-out by the borrower. 
	public boolean CheckForbooks(Book book, Borrower borrower) throws ClassNotFoundException, SQLException {
		//Integer result = getCount("select count(*) from tbl_book_loans where bookId = ? and cardNo= ? and dateIn is NULL", new Object[] {book.getBookId(),borrower.getCardNo()});
		
		SqlRowSet count=template.queryForRowSet("select count(*) from tbl_book_loans where bookId = ? and cardNo= ? and dateIn is NULL", new Object[] {book.getBookId(),borrower.getCardNo()});
        int res = count.getInt("count(*)");
	 
		//return res;
		
		
		if(res ==0){
			return true;
		}
		else{
			return false;
		}
	}

	//inserting a new book to the Book loans table.
	public void CheckOutbook(int book_id, int card_no,int branch_id) throws ClassNotFoundException, SQLException {
		System.out.println("Final step of bookloaning");
		try{
			System.out.println("bookId "+book_id);
			System.out.println("branchID "+branch_id);
			System.out.println("CardNo "+card_no);
			template.update("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate ) "
				+ "values( ?, ?, ?,CURDATE(),DATE_ADD(CURDATE(),INTERVAL 15 DAY))",new Object[] {book_id,branch_id,card_no});
		System.out.println("entry added to the book loans table");
		template.update("update tbl_book_copies set noOfCopies = noOfCopies-1 where bookId=? and branchId =?", new Object[] {book_id,branch_id});
		}
		catch(Exception e){
		System.out.println(e);
		}
	}

	//updating the number of copies after updating the number of copies.
	public void UpdateBooksafterCheckOutbook(Book book, LibraryBranch branch) throws ClassNotFoundException, SQLException {
	
		template.update("update tbl_book_copies set noOfCopies = noOfCopies-1 where bookId=? and brachId =?", new Object[] {book.getBookId(),branch.getBranchId()});
	}

	public void CheckInbook(Book book, LibraryBranch branch, Borrower borrower) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		template.update("update tbl_book_loans set dateIn = CURDATE() where bookId=? and brachId =? and cardNo = ?", new Object[] {book.getBookId(),borrower.getCardNo()});
	}

	
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {
List<BookLoans> bookLoans = new ArrayList<BookLoans>();
		
		while(rs.next()){
			BookLoans b = new BookLoans();
			b.setDateOut(rs.getDate("dateOut"));
			b.setDateIn(rs.getDate("dateIn"));
			b.setDueDate(rs.getDate("dueDate"));
			
			Borrower brr = new Borrower();
			brr.setCardNo(rs.getInt("cardNo"));
			b.setBorrower(brr);
			
			Book bo = new Book();
			bo.setBookId(rs.getInt("bookId"));
			b.setBook(bo);
			
			LibraryBranch lib = new LibraryBranch();
			lib.setBranchId(rs.getInt("branchId"));
			b.setBranch(lib);
			
			bookLoans.add(b);
		}
		return bookLoans;

	}



	public void renew(int cardNo1, int i) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		//int cardNo=Integer.parseInt(cardNo1);
		//int bookId=Integer.parseInt(i);
		System.out.println("renewing");
		try{
			template.update("update tbl_book_loans set dueDate=DATE_ADD(CURDATE(),INTERVAL 25 DAY) where bookId=? and cardNo=?",new Object[] {i,cardNo1});
		}
		catch(Exception e){
			System.out.println(e);
		}
	}



	public void returnbook(int c_no, int b_id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("In return book in boooks in BookloansDAO");
		//final int c_no=Integer.parseInt(i);
		System.out.println("card no:  "+c_no);
		System.out.println("book id:  "+b_id);
		//final int b_id=Integer.parseInt(j);
		//int branch_id = getC("select branchId from tbl_book_loans where bookId=? and cardNo = ? and dateIn is NULL", new Object[] {b_id,c_no});
		
	/*	final String cardNo = ;
		final String bookId = ;
		final String INSERT_SQL = "select branchId from tbl_book_loans where bookId=? and cardNo = ? and dateIn is NULL ";*/
		List<BookLoans> b=  template.query("select * from tbl_book_loans where bookId=? and cardNo = ? and dateIn is NULL", new Object[] {b_id,c_no},this);
		for(BookLoans bl :b){
			System.out.println(bl.getBranch().getBranchId());
		}
		BookLoans branch = b.get(0);
		int b_id1 = branch.getBranch().getBranchId();
		System.out.println("branch ID "+b_id1);
		//return bookId;
		
		
		//System.out.println("branch id: "+b_id1);
		template.update("delete from tbl_book_loans where bookId=? and branchId=? and cardNo = ?",new Object[] {b_id,b_id1,c_no});
		System.out.println("book returned.");
		template.update("update tbl_book_copies set noOfCopies = noOfCopies+1 where bookId=? and branchId =?", new Object[] {b_id,b_id1});
		
	}
	
	
	

}

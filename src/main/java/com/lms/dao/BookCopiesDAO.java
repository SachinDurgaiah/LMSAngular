package com.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.lms.entity.Book;
import com.lms.entity.BookCopies;
import com.lms.entity.LibraryBranch;

public class BookCopiesDAO extends BaseDAO implements ResultSetExtractor<List<BookCopies>> {
	

	///librarian service.
	public int CheckBookInbranch(int b_id,int br_id) throws ClassNotFoundException, SQLException {
		//return getCount("select count(*) from tbl_book_copies where bookId = ? and branchId = ?", new Object[] {book.getBookId(), branch.getBranchId()} );
		System.out.println("In check record");
		List<BookCopies> b= (List<BookCopies>)template.query("select * from tbl_book_copies where bookId = ? and branchId = ?", new Object[] {b_id, br_id},this);
		int res = b.size();
		 if(b!=null && b.size()>0){
			 return 1;
		 }
		 else{
			 return 0;
		 }
		/*SqlRowSet count=template.queryForRowSet("select count(*) from tbl_book_copies where bookId = ? and branchId = ?", new Object[] {b_id, br_id});
        int res = count.getInt("count(*)");
        return res*/
		
	}
	
	///librarian service.
	/*public Integer CountBookInbranch(Book book, LibraryBranch branch) throws ClassNotFoundException, SQLException {
		//***********check this function specifically if it returns the nomber of copies or the number of ..!!!!!
		return getC("select noOfCopies from tbl_book_copies where bookId = ? and branchId = ?", new Object[] {book.getBookId(), branch.getBranchId()} );
	}
*/
	///librarian service.
	public void Insertbook_copies(int book_id,int branch_id, Integer copies) throws ClassNotFoundException, SQLException {
		template.update("insert into tbl_book_copies (bookId, branchId,noOfCopies) values (?, ?, ?)", new Object[] {book_id, branch_id,copies });
		
	}
	
	///librarian service.
	public void Updatebook_copies(int book_id, int branch_id, Integer copies) throws ClassNotFoundException, SQLException {
		template.update("update tbl_book_copies set noOfCopies= ? where bookId = ? and branchId = ?", new Object[] {book_id,branch_id,copies });
	}
	
	
	public void Number_of_Copies(String bookId, String branchId, int nofc) throws ClassNotFoundException, SQLException {
		int bId=Integer.parseInt(bookId);
		int brId=Integer.parseInt(branchId);
		System.out.println("FInal step to updating the number of copies");
	
		template.update("insert into tbl_book_copies (bookId, branchId,noOfCopies) values (?, ?, ?)", new Object[] {bId,brId,nofc });
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> bookcopies = new ArrayList<BookCopies>();
		
		while(rs.next()){
			BookCopies bc = new BookCopies();
			bc.setNoOfCopies(rs.getInt("noOfCopies"));
			
			Book bo = new Book();
			bo.setBookId(rs.getInt("bookId"));
			bc.setBook(bo);
			
			LibraryBranch lib = new LibraryBranch();
			lib.setBranchId(rs.getInt("branchId"));
			bc.setBranch(lib);
			
			bookcopies.add(bc);
			
		}
		return bookcopies;
	}

}

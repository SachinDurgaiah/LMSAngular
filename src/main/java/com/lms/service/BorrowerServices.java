package com.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
//import com.lms.entity.LibraryBranch;

import org.springframework.beans.factory.annotation.Autowired;

import com.lms.dao.AuthorDAO;
import com.lms.dao.BookCopiesDAO;
import com.lms.dao.BookDAO;
import com.lms.dao.BookLoansDAO;
import com.lms.dao.BorrowerDAO;
import com.lms.dao.GenreDAO;
import com.lms.dao.LibraryBranchDAO;
import com.lms.dao.PublisherDAO;
import com.lms.entity.Author;
import com.lms.entity.Book;
import com.lms.entity.Borrower;
import com.lms.entity.LibraryBranch;

public class BorrowerServices {

	//verify carNo
	//List the branches
	//list the books associated with that branch
	//check if the books is already checked out by that user
	//add an entry in the book loans table
	//list the books loaned out by the user and let him select which one he chooses to return.
	//check for the due date and update the book_loans table with curdate for dateIn.
	
	//verifying the cardNo for now pass card number as integer which is retieved as an integer
	

	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookDAO bDAO;
	
	@Autowired
	BookLoansDAO blDAO;
	
	@Autowired
	BorrowerDAO brDAO;
	
	@Autowired
	GenreDAO gDAO;
	
	@Autowired
	LibraryBranchDAO lbDAO;
	
	@Autowired
	PublisherDAO pDAO;
	
	@Autowired
	BookCopiesDAO bcDAO;
	
	public int checkCardNo(Integer cardNo) throws ClassNotFoundException, SQLException{
		
			System.out.println("in borower DAO");
			return brDAO.CheckCard(cardNo);
		
	}
	
	//listing the branches
	public List<LibraryBranch> getAllBranches() throws ClassNotFoundException, SQLException{

			return lbDAO.readAllBranches(1);
		
	}
	
	public Integer getAllBranchesCount() throws ClassNotFoundException, SQLException{

		System.out.println("getting branch count");
		
		Integer count = lbDAO.getCount1();
			 //listS=count.size();
		return count;
	
}

	
	
	//listing the books associated with that branch
	public List<Book> getAllBooksWithBranch(int branch_id,int pageNo) throws ClassNotFoundException, SQLException{
	
			return bDAO.readAllBookswithBranch(branch_id,pageNo);
		
	}
	
	//checking if the books is already checked out by that user. Make a call to this function form testservice and continue based on the results.
	public boolean CheckIfBookIsLoaned(Book book,Borrower borrower) throws ClassNotFoundException, SQLException{
		
			return blDAO.CheckForbooks (book, borrower);
		
	}
	
	//add an entry in the book loans table
	public void CheckOutBook(int bookID,int cardNo,int branchID) throws ClassNotFoundException, SQLException{
		
			System.out.println("BookId :"+bookID);
			System.out.println("Branch ID "+branchID);
			System.out.println("card number "+cardNo);
			blDAO.CheckOutbook (bookID, cardNo,branchID);
		
	}
	
	//update the bookCopies table after the check out.
	public void UpdateBooksAfterCheckOut(Book book,LibraryBranch branch) throws ClassNotFoundException, SQLException{
		
			blDAO.UpdateBooksafterCheckOutbook (book,branch);
		
	}
	
	
	public Integer getBookCount(int branchId) throws ClassNotFoundException, SQLException{
	
			
			return bDAO.getCount1(branchId);
		
	}
	
	//list the books loaned out by the user and let him select which one he chooses to return.
	public List<Book> getAllbooksOfBorrower(int cardNo,int pageNo) throws ClassNotFoundException, SQLException{
		
			return bDAO.readAllBookswithCard(cardNo,pageNo);
		
	}
	
	
	//check for the due date and update the book_loans table with curdate for dateIn.
	public void CheckInBook(int c_no,int b_id ) throws ClassNotFoundException, SQLException{
		
			System.out.println("In check in boooks in borrower services");
			blDAO.returnbook(c_no,b_id);
			
	}
	
}

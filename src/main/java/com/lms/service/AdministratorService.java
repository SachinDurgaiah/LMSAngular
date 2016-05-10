package com.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

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
import com.lms.entity.Publisher;
import com.lms.entity.BookCopies;
import com.lms.entity.Borrower;
import com.lms.entity.Genre;
import com.lms.entity.LibraryBranch;		


//addbranch,deletebranch,updatebranch.


public class AdministratorService {

	
	
	@Autowired
	BookDAO bDAO;
	
	@Autowired
	AuthorDAO aDAO;
	
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
	
	///write createnew book function
	@Transactional
	public void deleteBook(Integer bookId) throws ClassNotFoundException, SQLException{
		
		bDAO.deleteBook(bookId);
			
		
	}
	
	
	public Integer getBookCount() throws ClassNotFoundException, SQLException{
		
			return aDAO.getCount();
		
	}
	
	public Integer getBookCount1(int cardNo) throws ClassNotFoundException, SQLException{
		
			return bDAO.getCount11(cardNo);
		
	}
	
	public Integer getBorrowerCount() throws ClassNotFoundException, SQLException{
		
			return brDAO.getCount();
		
	}
	
	
	public Integer getAuthorCount() throws ClassNotFoundException, SQLException{
		
			return aDAO.getCount();
		
	}
	
	public Integer getPublisherCount() throws ClassNotFoundException, SQLException{
		
			return pDAO.getCount();
		
	}
	
	
	public int getBranchCount() throws ClassNotFoundException, SQLException{
		System.out.println("in admin services");
			return lbDAO.getCount1();
		
	}
	
	@Transactional
	public void updateBook(Book book) throws ClassNotFoundException, SQLException{
		
			bDAO.updateBook(book);
			
		
	}
	
	
	
	@Transactional
	public int createAuthor(Author a) throws ClassNotFoundException, SQLException{
	
		int k1=0;
		
			k1= aDAO.addAuthorWithID(a);
			
		return k1;
	}
	
	
	@Transactional
	public void createAuthorWBook(int au_id,int b_id) throws ClassNotFoundException, SQLException{
		
			aDAO.addAuthorWithBook(au_id,b_id);
					
	}
	
	@Transactional
	public void deleteAuthors(int i) throws ClassNotFoundException, SQLException{
	System.out.println("inside delete authors");
	//int au_id=Integer.parseInt("authorId");
			aDAO.deleteAuthor(i);
			
		
	}
	
	@Transactional
	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException{
	
			aDAO.updateAuthor(author);
			
		
	}
	
	
	@Transactional
	public void createPublisher(String name,String address) throws ClassNotFoundException, SQLException{
	System.out.println("adding publisher");
		pDAO.addpublisher(name,address);
			
	}
	
	@Transactional
	public void deletePublisher(Integer publisherId) throws ClassNotFoundException, SQLException{
		
		pDAO.deletePublisher(publisherId);
		
	}
	
	@Transactional
	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException{
		
		pDAO.updatePublisher(publisher);
			
	}
	
	
	
	public void createLibraryBranch(String branchName, String branchAddress) throws ClassNotFoundException, SQLException{
		
		lbDAO.addLirbaryBranch(branchName,branchAddress);
			
	}
	
	
	public void deleteLibraryBranch(Integer branchId) throws ClassNotFoundException, SQLException{
	
		lbDAO.deleteLirbaryBranch(branchId);
			
	}
	
	public void updateLibraryBranch(LibraryBranch branch) throws ClassNotFoundException, SQLException{
		
		lbDAO.updateLirbaryBranch(branch);
			
	}
	
	@Transactional
	public void createBorrower(String borrowerName,String borrowerAddress,String borrowerPhone) throws ClassNotFoundException, SQLException{
		
		brDAO.addBorrower(borrowerName,borrowerAddress,borrowerPhone);
			
	}
	
	@Transactional
	public void deleteBorrower(Integer cardNo) throws ClassNotFoundException, SQLException{
		
		brDAO.deleteBorrower(cardNo);
			
	}
	
	/*@Transactional
	public void updateBorrower(Borrower borrower) throws ClassNotFoundException, SQLException{
	
		brDAO.updateBorrower(borrower);
			
	}
	*/
	
	@Transactional
	public Integer createAuthorWithID(Author author) throws ClassNotFoundException, SQLException{
	
		Integer authorId = null;
		
			authorId = aDAO.addAuthorWithID(author);
			
		return authorId;
	}
	
	public List<Genre> getAllGenre() throws ClassNotFoundException, SQLException{
		
			return gDAO.readAllGenre();
		
	}
	
	public List<Book> getAllBooks(int pageNo) throws ClassNotFoundException, SQLException{
		
			List<Book> book = bDAO.readAllBooks(pageNo);
			List<Author> authors = new ArrayList<Author>();
			for(Book b: book){
				List<Author> author = aDAO.findAuthorByBookId(pageNo,b.getBookId());
				b.setAuthor(author);
			}
			
		return book;
	}
	
	
	
	
	public List<Author> getAllAuthors(int pageNo) throws ClassNotFoundException, SQLException{
		List<Author> authors = aDAO.readAllAuthors(pageNo);
		List<Book> books = new ArrayList<Book>();
		for(Author a: authors){
			
			List<Book> book = bDAO.readAllBookByAuthorId(pageNo,a.getAuthorId());
			if(book!=null){
			for(Book b:book){
				b.setGenre(gDAO.readAllGenreByBook(b.getBookId()));
				b.setAuthor(aDAO.findAuthorByBookId(pageNo,b.getBookId()));
			}
			}
			a.setBooks(book);
			
		}
	return authors;
}

	
	public List<Author> getAllAuthors() throws ClassNotFoundException, SQLException{
		
			return aDAO.readAllAuthors();
		
	}
	
	public List<LibraryBranch> getAllBranches(int pageNo) throws ClassNotFoundException, SQLException{
		
			return lbDAO.readAllBranches(pageNo);
		
	}
	
	public List<LibraryBranch> getAllBranches() throws ClassNotFoundException, SQLException{
		
			return lbDAO.readAllBranches();
		
	}
	
	public List<Publisher> getAllPubliser(int pageNo) throws ClassNotFoundException, SQLException{
		
			return pDAO.readAllPublihser(pageNo);
		
	}
	
	public List<Borrower> getAllBorrower(int pageNo) throws ClassNotFoundException, SQLException{
		
			return brDAO.readAllBorrower(pageNo);
		
	}
	
	public List<Borrower> getAllBorrower() throws ClassNotFoundException, SQLException{
		
			return brDAO.readAllBorrower();
		
	}
	
	public List<Book> getAllBooksOfBorrower(int cardNo,int pageNo) throws ClassNotFoundException, SQLException{
		
			return bDAO.readAllBookswithCard(cardNo,pageNo);
		
	}

	public Author getAuthorByID(Integer authorId) throws ClassNotFoundException, SQLException {
		
			return aDAO.readAuthorsByID(authorId);
		
	}
	
	public Borrower getBorrowerByID(Integer cardNo) throws ClassNotFoundException, SQLException {
		
			return brDAO.readBorrowerByID(cardNo);
		
	}

	public Publisher getpublisherByID(Integer publisherId) throws ClassNotFoundException, SQLException {
		
			return pDAO.readPublisherByID(publisherId);
		
	}

	public Book getbookByID(Integer bookId) throws ClassNotFoundException, SQLException {
	
			return bDAO.readBookByID(bookId);
		
	}
	
	@Transactional
	public void Update_publisher(Publisher a) throws ClassNotFoundException, SQLException {
		
		pDAO.updatePublisher(a);
			
	}

	/*public Integer createBook(Book b) throws ClassNotFoundException, SQLException {
		
		Integer b_id=0;
		
		b_id=bDAO.saveWithId(b);
			
		return b_id;
	}*/

	
	public Integer createBook(String title,int pub_id) throws ClassNotFoundException, SQLException {
	
		Integer b_id=0;
		
		b_id=bDAO.saveWithId1(title,pub_id);
			
		return b_id;
	}
	
	@Transactional
	public void createBookAuthors(Integer bId, int auId) throws ClassNotFoundException, SQLException {
		
			BookDAO bdao = new BookDAO();
			bdao.saveIO(bId,auId);
			
	
		
	}

	public void updateBookAuthors(Integer bId, int auId) throws ClassNotFoundException, SQLException {
		
		bDAO.saveIO(bId,auId);	
		
	}
	
	public void createBookGenre(int gId, Integer bId) throws ClassNotFoundException, SQLException {
	
		bDAO.saveIO1(gId,bId);
					
	}

	public void updateBook_Authors(int au_Id, Integer bId) throws ClassNotFoundException, SQLException {
		
		bDAO.update_book_author(au_Id,bId);
					
	}

	
	public void updateBookGenre(int gId, Integer bId) throws ClassNotFoundException, SQLException {
		
		bDAO.Updatebook_genre(gId,bId);
					
	}
	
	public void UpdateBook(Book a) throws ClassNotFoundException, SQLException {
		
			bDAO.updateBook(a);
					
	}

	public void UpdateBook(String title, int booki) throws ClassNotFoundException, SQLException {
		
			bDAO.updateBook1(title,booki);
			
	}

	
	@Transactional
	public void setNoOfCopies(String bookId, String branchId, int nofc) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
			bcDAO.Number_of_Copies(bookId,branchId,nofc);
			
	}

	public boolean verifyCard(int cardN) throws ClassNotFoundException, SQLException {
		
		boolean present = false;
		
			
			if(brDAO.readBorrowerByID(cardN) != null)
				present = true;
		
		
		return present;
	}

	
	public void renewBook(int cardNo1, int i) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		//int c= Integer.parseInt(cardNo1);
			blDAO.renew(cardNo1,i);
			
	}

	
	public void returnbook(String cardNo1, String bookId1) throws ClassNotFoundException, SQLException {
		int c_no=Integer.parseInt(cardNo1);
		int B_id= Integer.parseInt(bookId1);
		
			blDAO.returnbook(c_no,B_id);			
		
	}

	@Transactional
	public void ChangeAuthor(int i, String authorName) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
			aDAO.changeAuthor(i,authorName);
			//bl.Number_of_Copies(bookId,branchId,nofc);
			
	}
	
	public List<Author> getAllAuthorsByName(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
	
			return aDAO.readAuthorsByName(searchString, pageNo);
		
	}
	
	public List<Author> getAllAuthorsByNameandBook(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		
			return aDAO.readAuthorsByNameandbook(searchString, pageNo);
		
	}
	
	public List<Book> getAllBooks(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		
			return bDAO.readAllBooksByAll(searchString, pageNo);
		
	}
	
	
	public List<Book> getAllBookByTitle(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
	
			return bDAO.readAllBooks1(searchString, pageNo);
		
	}
	
	
	public List<Book> getAllBookByAuthor(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		
			return bDAO.readAllBooksByAuthors(searchString, pageNo);
		
	}
	
	
	public List<Author> getAllAuthorsByBook1(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		
			return aDAO.readAuthorsByBook(searchString, pageNo);
		
	}
	
	public List<Borrower> getAllBorrower1(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		
			return brDAO.readAllBorrower(searchString,pageNo);
		
	}
	
	public List<Borrower> getAllBorrowerByName(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		
			return brDAO.readAllBorrowerByName(searchString,pageNo);
		
	}
	
	
	public List<Borrower> getAllBorrowerByAddress(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		
			return brDAO.readAllBorrowerByAddress(searchString,pageNo);
		
	}
	
	
	public List<LibraryBranch> getAllBranchName(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		
			System.out.println("In admin service ");
			return lbDAO.getAllBranchByName(searchString,pageNo);
		
	}
	
	
	public List<LibraryBranch> getAllBranchAddress(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		
			System.out.println("In admin service ");
			return lbDAO.getAllBranchByAddress(searchString,pageNo);
		
	}
	
	
	public List<LibraryBranch> getAllBranch(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		
			System.out.println("In admin service ");
			return lbDAO.getAllBranch(searchString,pageNo);
		
	}
	
	public List<Publisher> getAllPublisher(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		
			System.out.println("In admin service of publisher");
			return pDAO.getAllPublisher(searchString,pageNo);
		
	}
	
	public List<Publisher> getAllPublisherByName(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		
			System.out.println("In admin service of publisher");
			return pDAO.getAllPublisherByName(searchString,pageNo);
		
	}
	
	public List<Publisher> getAllPublisherByAddress(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		
			System.out.println("In admin service of publisher");
			return pDAO.getAllPublisherByAddress(searchString,pageNo);
		
	}
	
	public List<Book> getAllAuthorsByBook(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		
			return bDAO.readAuthorsBybook(searchString, pageNo);
	}
	
}

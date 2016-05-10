package com.gcit.lms;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dao.AuthorDAO;
import com.lms.dao.BookDAO;
import com.lms.dao.BorrowerDAO;
import com.lms.dao.GenreDAO;
import com.lms.dao.LibraryBranchDAO;
import com.lms.dao.PublisherDAO;
import com.lms.domain.Genre;
import com.lms.entity.Author;
import com.lms.entity.Book;
import com.lms.entity.BookCopies;
import com.lms.entity.Borrower;
import com.lms.entity.LibraryBranch;
import com.lms.entity.Publisher;
import com.lms.service.AdministratorService;
import com.lms.service.BorrowerServices;
import com.lms.service.LibrarianService;
import com.mysql.jdbc.interceptors.SessionAssociationInterceptor;

/**
 * Handles requests for the application home page.
 */
@RestController
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	

	
	
	@Autowired
	AdministratorService service; 
	
	@Autowired
	BorrowerServices brservice;
	
	@Autowired
	LibrarianService lbservice;
	
	@Autowired
	AuthorDAO aDAO;
	
	@Autowired
	LibraryBranchDAO lbDAO;
	
	@Autowired
	BorrowerDAO brDAO;
	
	@Autowired
	PublisherDAO pDAO;
	
	@Autowired
	BookDAO bDAO;
	
	@Autowired
	GenreDAO gDAO;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/administrator", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String goToSCT() {
	    return "administrator";
	}
	
	@RequestMapping(value = "/librarian", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String goToSCT1() {
	    return "librarian";
	}
	
	
	@RequestMapping(value = "/borrowermenu", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String borrowermenu(Locale locale, Model model, @RequestBody Author author) throws ClassNotFoundException, SQLException {
		//service.createAuthor(author);
		 return "borrowermenu";
	}
	
	
	
	
	
	@RequestMapping(value = "/borrower", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String goToSCT3() {
	    return "borrower";
	}
	
	@RequestMapping(value = "/borrower1", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String goToSCT4() {
	    return "borrower1";
	}
	
	@RequestMapping(value = "/librarybranch", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String updateLibBanches() {
	    return "librarybranch";
	}
	
	
	
	
	
	
	@RequestMapping(value = "/addBcopies", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String addBcopies(Locale locale, Model model, @RequestParam String bookId,String branchId,String noOfCopies) throws ClassNotFoundException, SQLException {
		int nofc = Integer.parseInt(noOfCopies);
		String addAuthorResult = "";
		if (nofc != 0) {
			
				service.setNoOfCopies(bookId, branchId, nofc);
				addAuthorResult = "Books copies updated successfully";
				return addAuthorResult;
				
		} else {
			
			addAuthorResult = "Number of copies cannot be empty 0";
			return addAuthorResult;
		}
	}
	/////////////added authors with multiple books associated with it.
	@RequestMapping(value = "/addauthor", method = RequestMethod.POST, consumes="application/json")
	public void addauthor(Locale locale, Model model, @RequestBody Author a) throws ClassNotFoundException, SQLException  {
		//service.createAuthor(author);
		//int b_id=Integer.parseInt(bookId);
		String addAuthorResult = "";
	
		aDAO.addAuthor(a);
			
			//System.out.println("book name is: " + bookName);
			
	}


	
	@RequestMapping(value = "/addBooks", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String addBooks(Locale locale, Model model, @RequestBody Book b) throws ClassNotFoundException, SQLException  {
	    //return "addbook";
		System.out.println(b.getTitle());
		System.out.println(b.getPublisher().getPublisherId());
		Integer BookId = service.createBook(b.getTitle(),b.getPublisher().getPublisherId());
		System.out.println("bookId "+BookId);
		for(Author a: b.getAuthor()){
			
			System.out.println(a.getAuthorId());
			bDAO.saveIO(BookId,a.getAuthorId());
			//service.createBookAuthors(BookId, a.getAuthorId());
		}
		for(com.lms.entity.Genre g:b.getGenre()){
			System.out.println(g.getGenre_id());
			service.createBookGenre(g.getGenre_id(), BookId);
		}
		
		return "success";
	}
	
	@RequestMapping(value = "/deletebook", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public void deletebook(Locale locale, Model model, @RequestBody Book book) throws ClassNotFoundException, SQLException  {
	    //return "addbook";
		
		/*Integer bookId1 = Integer.parseInt("bookId");
		AdministratorService service = new AdministratorService();
		StringBuilder str = new StringBuilder();*/
		
			service.deleteBook(book.getBookId());
			
		
	}
	
	@RequestMapping(value = "/deleteBorrower", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public void deleteBorrower(Locale locale, Model model, @RequestBody Borrower borrower) throws ClassNotFoundException, SQLException  {
	    //return "addbook";
		//int c_no=Integer.parseInt(cardNo);
			service.deleteBorrower(borrower.getCardNo());
		
	}
	
	@RequestMapping(value = "/deletePublisher", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public void deletePublisher(Locale locale, Model model, @RequestBody Publisher publisher) throws ClassNotFoundException, SQLException  {
	    //return "addbook";
		//int p_no=Integer.parseInt(publisherId);
			service.deletePublisher(publisher.getPublisherId());
		
	}
	
	
	@RequestMapping(value = "/deletebranch", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public void deletebranch(Locale locale, Model model, @RequestBody LibraryBranch branch) throws ClassNotFoundException, SQLException  {
	    //return "addbook";
		//int b_no=Integer.parseInt(branchId);
			service.deleteLibraryBranch(branch.getBranchId());
		
	}
	
	@RequestMapping(value = "/deleteAuthors", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public void deleteAuthors(Locale locale, Model model, @RequestBody Author author) throws ClassNotFoundException, SQLException  {
	    //return "addbook";
		System.out.println("inside delete authors "+author.getAuthorId());
		//int a_no=Integer.parseInt(authorId);
			service.deleteAuthors(author.getAuthorId());
		
	}
	
	////////***********************write a proper function involving changing genre and al..
	@RequestMapping(value = "/updatebook", method =  RequestMethod.POST, produces="application/json")
	public void updatebook(Locale locale, Model model, @RequestParam String bookId) throws ClassNotFoundException, SQLException  {
		
	}
	
	
	@RequestMapping(value = "/addborrower", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String addborrower(Locale locale, Model model, @RequestBody Borrower b) throws ClassNotFoundException, SQLException {
		String addAuthorResult = "";
		if (b.getName() != null && b.getName().length() > 3 && b.getName().length() < 45) {
			
			
				service.createBorrower(b.getName(),b.getAddress(),b.getPhone());
				
				addAuthorResult = "Borrower added sucessfully.";
				
			
			return addAuthorResult;
		}
		else {
			addAuthorResult = "Borrower Name cannot be empty or more than 45 chars in length";
			return addAuthorResult;
			
		}
	}
	
	@RequestMapping(value = "/addpublisher", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String goToSCT8(Locale locale, Model model, @RequestBody Publisher p) throws ClassNotFoundException, SQLException  {
		String addPublisherResult = "";
		if (p.getPublisherName() != null && p.getPublisherName().length() > 3 && p.getPublisherName().length() < 45) {
			/*Publisher p = new Publisher();
			p.setPublisherName(publisherName);
			p.setPublisherAddress(publisherAddress);*/
			System.out.println("adding new pub");
				service.createPublisher(p.getPublisherName(),p.getPublisherAddress());
				
				addPublisherResult = "Publisher added sucessfully.";
				return addPublisherResult;
			
		} else {
		
			addPublisherResult = "Publisher Name cannot be empty or more than 45 chars in length";
			return addPublisherResult;
		}
	}
	
	@RequestMapping(value = "/addbranch", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String addbranch(Locale locale, Model model, @RequestBody LibraryBranch b) throws ClassNotFoundException, SQLException  {
		String addAuthorResult = "";
		if (b.getBranchName() != null && b.getBranchName().length() > 3 && b.getBranchName().length() < 45) {
			/*LibraryBranch l = new LibraryBranch();
			l.setBranchName(branchName);
			l.setBranchAddress(branchAddress);*/
			
				service.createLibraryBranch(b.getBranchName(),b.getBranchAddress());
				addAuthorResult = "Branch added sucessfully.";
				return addAuthorResult;
			
		} else {
			addAuthorResult = "Branch Name cannot be empty or more than 45 chars in length";
			return addAuthorResult;
		}
	}
	
	
	@RequestMapping(value = "/changeAuthor", method = {RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public int updateAuthor(Locale locale, Model model, @RequestBody Author a) throws ClassNotFoundException, SQLException {
		System.out.println("author id "+a.getAuthorId());
		System.out.println("author new name "+a.getAuthorName());
		//service.ChangeAuthor(a.getAuthorId(),a.getAuthorName());
		aDAO.changeAuthor(a.getAuthorId(),a.getAuthorName());
		return 1;
	}
	
	@RequestMapping(value = "/getAuthorByID", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public Author getAuthorByID(Locale locale, Model model, @RequestBody Author author) throws ClassNotFoundException, SQLException {
		return aDAO.readAuthorsByID(author.getAuthorId());
		
		
	    //return "editauthor";
	}
	
	
	
	@RequestMapping(value = "/changeBooks", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String changeBooks(Locale locale, Model model, @RequestBody Book b) throws ClassNotFoundException, SQLException  {
	    //return "addbook";
		System.out.println(b.getTitle());
		System.out.println("book id "+b.getBookId());
		bDAO.updateBook12(b.getTitle(), b.getBookId(), b.getPublisher().getPublisherId());
		System.out.println(b.getPublisher().getPublisherId());
		//Integer BookId = service.createBook(b.getTitle(),b.getPublisher().getPublisherId());
		//System.out.println("bookId "+BookId);
		bDAO.deleteAuthor(b.getBookId());
		for(Author a: b.getAuthor()){
			
			System.out.println(a.getAuthorId());
			bDAO.saveIO(b.getBookId(),a.getAuthorId());
			//service.createBookAuthors(BookId, a.getAuthorId());
		}
		bDAO.deleteGenre(b.getBookId());
		for(com.lms.entity.Genre g:b.getGenre()){
			System.out.println(g.getGenre_id());
			service.createBookGenre(g.getGenre_id(), b.getBookId());
		}
	  
		return "success";
	}
	
	@RequestMapping(value = "/getBookByID", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public Book getBookByID(Locale locale, Model model, @RequestBody Book book) throws ClassNotFoundException, SQLException {
		return bDAO.readBookByID(book.getBookId());
		
		
	    //return "editauthor";
	}
	
	
	
	@RequestMapping(value = "/updateBorrower", method = {RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public void updateBorrower(Locale locale, Model model, @RequestBody Borrower borrower) throws ClassNotFoundException, SQLException  {
		System.out.println("name "+borrower.getName());
		String addAuthorResult=null;
				System.out.println("borrower name :"+borrower.getName());
				System.out.println("borrower address :"+borrower.getAddress());
				System.out.println("borrower phone :"+borrower.getPhone());
				System.out.println("cardNo:"+borrower.getCardNo());
				brDAO.updateBorrower(borrower.getName(),borrower.getAddress(),borrower.getPhone(),borrower.getCardNo());
				System.out.println("Borrower updated sucessfully");
				addAuthorResult = "Borrower updated sucessfully.";
				return;

	}
	
	@RequestMapping(value = "/getBorrowerByID", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public Borrower getBorrowerByID(Locale locale, Model model, @RequestBody Borrower borrower) throws ClassNotFoundException, SQLException {
		System.out.println("getting borrower details using id "+borrower.getCardNo());
		return brDAO.readBorrowerByID(borrower.getCardNo());
		
		
	}
	
	@RequestMapping(value = "/updateBranch", method = {RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public String updateBranch(Locale locale, Model model, @RequestBody LibraryBranch branch) throws ClassNotFoundException, SQLException {
		
		System.out.println("Hello Post");
	

		String addAuthorResult = "";
	
			LibraryBranch a = new LibraryBranch();
			a.setBranchId(branch.getBranchId());
			//System.out.println("branch Id " + branchId);
			a.setBranchName(branch.getBranchName());
			a.setBranchAddress(branch.getBranchAddress());
			System.out.println("branch id "+branch.getBranchId());
			System.out.println("branch name "+branch.getBranchName());
			System.out.println("branch address "+branch.getBranchAddress());
				lbservice.UpdateDetails_branches(a);
				//returnPath = "/viewbranch.jsp";
				addAuthorResult = "Branch updated sucessfully.";
				return addAuthorResult;
		
		
		
	}
	
	@RequestMapping(value = "/updateBranches", method = {RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public String updateBranches(Locale locale, Model model, @RequestBody LibraryBranch branch) throws ClassNotFoundException, SQLException {
		
		System.out.println("Hello Post");
	

		String addAuthorResult = "";
	
			LibraryBranch a = new LibraryBranch();
			a.setBranchId(branch.getBranchId());
			//System.out.println("branch Id " + branchId);
			a.setBranchName(branch.getBranchName());
			a.setBranchAddress(branch.getBranchAddress());
			System.out.println("branch id "+branch.getBranchId());
			System.out.println("branch name "+branch.getBranchName());
			System.out.println("branch address "+branch.getBranchAddress());
				lbservice.UpdateDetails_branches(a);
				//returnPath = "/viewbranch.jsp";
				addAuthorResult = "Branch updated sucessfully.";
				return addAuthorResult;
		
		
		
	}
	
	
	
	@RequestMapping(value = "/getBranchID1", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public LibraryBranch getBranchID1(Locale locale, Model model, @RequestBody LibraryBranch branch) throws ClassNotFoundException, SQLException {
		System.out.println("branch id : "+branch.getBranchId());
		
		return lbDAO.readAuthorsByID(branch.getBranchId());
		
		
	}
	
	
		
	@RequestMapping(value = "/updatePublisher", method =  {RequestMethod.POST, RequestMethod.GET}, consumes="application/json")
	public void updatePublisher(Locale locale, Model model, @RequestBody Publisher publisher) throws ClassNotFoundException, SQLException {

		String addAuthorResult = "";
		
			Publisher a = new Publisher();
			a.setPublisherId(publisher.getPublisherId());
			System.out.println("publisher id :"+publisher.getPublisherId());
			a.setPublisherName(publisher.getPublisherName());
			System.out.println("publisher name :"+publisher.getPublisherName());
			a.setPublisherAddress(publisher.getPublisherAddress());
			System.out.println("publisher address :"+publisher.getPublisherAddress());

				service.Update_publisher(a);
				
				addAuthorResult = "Publisher updated sucessfully.";
				return;
			
		
	}
	
	
	@RequestMapping(value = "/getPublisherID", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public Publisher getPublisherID(Locale locale, Model model, @RequestBody Publisher publisher) throws ClassNotFoundException, SQLException {
		return pDAO.readPublisherByID(publisher.getPublisherId());
		
		
	}
	
	

	
	@RequestMapping(value="/author/get", method={RequestMethod.GET, RequestMethod.POST} , produces="application/json")
	public List<Author> viewAuthors() throws ClassNotFoundException {
		System.out.println("Printing my page No from REST" +1);
		
		try {
			return service.getAllAuthors(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	@RequestMapping(value="/genre/get", method={RequestMethod.GET, RequestMethod.POST} , produces="application/json")
	public List<com.lms.entity.Genre> viewGenre() throws ClassNotFoundException {
		System.out.println("Printing my page No from REST" +1);
		
		try {
			return gDAO.readAllGenre();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	@RequestMapping(value="/book/get", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Book> viewbook() throws ClassNotFoundException {
		System.out.println("Printing my page No from REST" +1);
		
		try {
			return service.getAllBooks(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/borrower/get", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Borrower> viewborrower() throws ClassNotFoundException, SQLException{
		System.out.println("Printing my page No from REST" +1);
		return service.getAllBorrower(1);
	}
	
	@RequestMapping(value="/branch/get", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<LibraryBranch> viewbranch() throws ClassNotFoundException, SQLException{
		System.out.println("Printing branches");
		return service.getAllBranches(1);
	}
	
	@RequestMapping(value="/publisher/get", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Publisher> viewpublisher() throws ClassNotFoundException, SQLException{
		System.out.println("Printing my page No from REST" +1);
		return service.getAllPubliser(1);
	}
	
	@RequestMapping(value="/DisplayAllBranches/", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<LibraryBranch> DisplayAllBranches() throws ClassNotFoundException, SQLException{
		System.out.println("Printing my page No from REST" +1);
		
		return brservice.getAllBranches();
	}
	
	int branch_id=0;
	@RequestMapping(value="/allBooks", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Book> allBooks(@RequestBody LibraryBranch branch) throws ClassNotFoundException {
		System.out.println("Printing my page No from REST" +1);
		branch_id=branch.getBranchId();
		try {
			return service.getAllBooks(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/bookSelected/{bookId}/{noOfCopies}", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public int bookSelected(Locale locale, @PathVariable("bookId") Integer bookId, @PathVariable("noOfCopies") Integer noOfCopies) throws ClassNotFoundException, SQLException {
		//Author author = new Author();
		System.out.println("Booksid is :"+bookId);
		System.out.println("number of copies is :"+noOfCopies);
		System.out.println("branch_id is :"+branch_id);
		//int c_no=Integer.parseInt(cardNo);
		lbservice.UpdateBookCopies(bookId,branch_id,noOfCopies);
		return 1;
		
	}
	
	
	
	
///////////////checkbook for checkout		
	@RequestMapping(value = "/findBforcheckout", method = RequestMethod.GET, produces="application/json")
	public String CheckBorrower(Locale locale, Model model,@RequestBody Borrower b) throws ClassNotFoundException, SQLException {
		//Author author = new Author();
		System.out.println("card no is :"+b.getCardNo());
		//int c_no=Integer.parseInt(cardNo);
		int c=brservice.checkCardNo(b.getCardNo());
		
		System.out.println("c :"+c);
		if(c!=0){
			
	    	int branchCount =  service.getBranchCount();
	    	System.out.println("branch count "+branchCount);
	    	String bc=Integer.toString(branchCount) ;
	    	
		model.addAttribute("bc", bc);
		return "DisplayAllBranches";
		}
		else {
			return "borrowermenu";
		}
	}
	
	int card_no=0;
	@RequestMapping(value = "/verifyBorrowerforbookreturn", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String verifyBorrowerforbookreturn(Locale locale, Model model,@RequestBody Borrower b) throws ClassNotFoundException, SQLException {
		//Author author = new Author();
		System.out.println("card no is :"+b.getCardNo());
		card_no=b.getCardNo();
		//int c_no=Integer.parseInt(cardNo);
		int c=brservice.checkCardNo(b.getCardNo());
		//service.createAuthor(author);
		System.out.println("c :"+c);
		//return c;
		if(c!=0){
			return "a";
		}
		else{
			return null;
		}
		
	}
	@RequestMapping(value = "/renew", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public int renew(Locale locale, Model model, @RequestBody Book book) throws ClassNotFoundException, SQLException  {
		//String addAuthorResult = "";
		//System.out.println(b.getCardNo());
		System.out.println(book.getBookId());
		String returnBookResult = null;
		service.renewBook(card_no, book.getBookId());
			//brservice.CheckInBook();
			returnBookResult="Book successfully returned";
			card_no=0;
			return 1;
	}
	
	
	
	
	
//=================================================Borrower services============================================//
	///////getting all the books from a branch
	
	/*@RequestMapping(value="/verifyCard", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public boolean verifyCard(@PathVariable Integer pageNo, @RequestBody Borrower b) throws ClassNotFoundException, SQLException{
		System.out.println("Printing my page No from REST" +pageNo);
		boolean b = brDAO.verifyBorrower(b.getCardNo());
	}
	*/
	int br_id=0;
	@RequestMapping(value="/getAllBooksWithBranch", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Book> getAllBooksWithBranch(Locale locale, @RequestBody LibraryBranch lb) throws ClassNotFoundException, SQLException{
		System.out.println("Printing my page No from REST" +1);
		//int br_id=Integer.parseInt(l);
		System.out.println("getting all book for the branch");
		br_id=lb.getBranchId();
		return bDAO.readAllBookswithBranch(lb.getBranchId(),1);
	}
	

	@RequestMapping(value = "/borrowBook", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public String borrowBook(Locale locale, Model model,@RequestBody Book b1) throws ClassNotFoundException, SQLException {
		//Author author = new Author();
		String addAuthorResult = "";

			int bookID = b1.getBookId();
			int CardNo = card_no;
			int branchID = br_id;
			System.out.println("in checkout books");
			brservice.CheckOutBook(bookID, CardNo, branchID);
			addAuthorResult="book checked out successfully";
			return addAuthorResult;
			
			
	}
	int c_no=0;
	@RequestMapping(value = "/returnBooks", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public int returnBook(Locale locale, Model model, @RequestBody Book book) throws ClassNotFoundException, SQLException  {
		//String addAuthorResult = "";
		//System.out.println(b.getCardNo());
		System.out.println(book.getBookId());
		String returnBookResult = null;
		
			brservice.CheckInBook(c_no, book.getBookId());
			returnBookResult="Book successfully returned";
			c_no=0;
			return 1;
	}
	
	@RequestMapping(value = "/getBooksofUser", method = {RequestMethod.GET, RequestMethod.POST}, produces="application/json")
	public List<Book> getBooksofUser(Locale locale, Model model, @RequestBody Borrower b) throws ClassNotFoundException, SQLException  {
		//String addAuthorResult = "";
		
		String returnBookResult = null;
		System.out.println("card no to retrieve books :"+b.getCardNo());
		c_no=b.getCardNo();
		System.out.println("c_no value is "+c_no);
			return brservice.getAllbooksOfBorrower(b.getCardNo(),1);
			
	}
	
	
}

package com.lms.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.lms.entity.Author;
import com.lms.entity.Book;
import com.lms.entity.Genre;
import com.lms.entity.LibraryBranch;
import com.lms.entity.Publisher;

@SuppressWarnings("unchecked")
public class BookDAO extends BaseDAO implements ResultSetExtractor<List<Book>>{


	public void addBook(Book book) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_book (title, publisherId) values (?, ?)", new Object[] {book.getTitle(), book.getPublisher().getPublisherId()});
	}
	
	//USe this wen you are adding the author. If he wants to select the book for the author. Then add the bookId and Author ID in the Book_AUthor table.
	public List<Book> readAllBooks(int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return (List<Book>) template.query("select * from tbl_book", this);///call the readall first level function.
	}
	
	
	
	
	public List<Book> readAllBooks1(String name,int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		name = "%"+name+"%";
		return (List<Book>) template.query("select * from tbl_book where title like ?",new Object[] {name},this);///call the readall first level function.
		//return (List<Author>) readAll("select * from tbl_author as a left join tbl_book_authors as ba on ba.authorId=a.authorId left join tbl_book as b on ba.bookId=b.bookId where a.authorName like ? or b.title like ?", new Object[] {name,name});
	}
	
	
	public List<Book> readAllBooksByAuthors(String name,int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		name = "%"+name+"%";
		//return (List<Book>) readAll("select * from tbl_book where title like ?",new Object[] {name});///call the readall first level function.
		return (List<Book>) template.query("select * from tbl_book as b left join tbl_book_authors as ba on ba.bookId=a.bookId left join tbl_author as a on ba.authorId=b.authorId where a.authorName like ?", new Object[] {name},this);
	}
	public List<Book> readAllBooksByAll(String name,int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		name = "%"+name+"%";
		//return (List<Book>) readAll("select * from tbl_book where title like ?",new Object[] {name});///call the readall first level function.
		return (List<Book>) template.query("select * from tbl_book as b left join tbl_book_authors as ba on ba.bookId=a.bookId left join tbl_author as a on ba.authorId=b.authorId where a.authorName like ? or b.title like ?", new Object[] {name,name},this);
	}
	
	
	//display all the books associated with the branch
	public List<Book> readAllBookswithBranch(int branch_id,int pageNo) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		setPageNo(pageNo);
		return (List<Book>) template.query("select * from tbl_book where bookId in (select bc.bookId from tbl_book_copies as bc where bc.branchId = ? and noOfCopies>0)", new Object[] {branch_id},this);
	}
	
	public Integer getCount() throws ClassNotFoundException, SQLException{

        String sql = "select count(*) from tbl_book";
	    SqlRowSet count=template.queryForRowSet("select count(*) from tbl_book");
        int res = count.getInt("count(*)");
	 
		return res;
	}
	
	public Integer getCount1(int bId) throws ClassNotFoundException, SQLException{
		
		 SqlRowSet count=template.queryForRowSet("select count(*) from tbl_book left join tbl_book_loans on tbl_book.bookId=tbl_book_loans.bookId where tbl_book_loans.branchId=?",new Object[] {bId});
	        Integer res = count.getInt("count(*)");
		 
			return res;
		
		//return getCount12("select count(*) from tbl_book left join tbl_book_loans on tbl_book.bookId=tbl_book_loans.bookId where tbl_book_loans.branchId=?",new Object[] {bId});
	}
	
	public Integer getCount11(int card_no) throws ClassNotFoundException, SQLException{
		SqlRowSet count=template.queryForRowSet("select count(*) from tbl_book left join tbl_book_loans on tbl_book.bookId=tbl_book_loans.bookId where tbl_book_loans.cardNo=?",new Object[] {card_no});
        int res = count.getInt("count(*)");
	 
		return res;
		//return getCount12("select count(*) from tbl_book left join tbl_book_loans on tbl_book.bookId=tbl_book_loans.bookId where tbl_book_loans.cardNo=?",new Object[] {card_no});
	}
	
	
	
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		AuthorDAO adao = new AuthorDAO();
		GenreDAO gdao = new GenreDAO();
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("pubId"));
			b.setPublisher(p);
			
			books.add(b);
		}
		return books;
	}
	

	public void deleteBook(Integer bookId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		template.update("delete from tbl_book_authors where bookId = ?", new Object[] {bookId});
		template.update("delete from tbl_book where bookId = ?", new Object[] {bookId});
		
	}
	
	public void deleteBook_authors(Integer bookId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		template.update("delete from tbl_book_authors where bookId = ?", new Object[] {bookId});
		
	}
	
	
	public void updateBook1(String title, int booki) throws ClassNotFoundException, SQLException {
		template.update("update tbl_book set title = ? where bookId = ?", new Object[] {title,booki});
		
	}

	public void updateBook12(String title, int booki,int pubId) throws ClassNotFoundException, SQLException {
		template.update("update tbl_book set title = ?,pubId = ? where bookId = ?", new Object[] {title,pubId,booki});
		
	}
	
	
	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		template.update("update tbl_book set title = ? where bookId = ?", new Object[] {book.getTitle(),book.getPubId()});
	}


/*	public Integer saveWithId(Book b) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Integer b_Id = saveWithID("insert into tbl_book (title, pubId) values (?, ?)", new Object[] {b.getTitle(), b.getPublisher().getPublisherId()});
		return b_Id;
		
	}*/
	public Integer saveWithId1(String title,int pub_id) throws ClassNotFoundException, SQLException {
		
			//final String title1 = title;
			template.update("insert into tbl_book (title, pubId) values (?, ?) ",new Object[] {title,pub_id});
			//final String INSERT_SQL = "insert into tbl_book (title, pubId) values (?, ?) ";
			int bookId = template.queryForObject("SELECT LAST_INSERT_ID()",Integer.class);
			/*KeyHolder keyHolder = new GeneratedKeyHolder();
			template.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
					ps.setString(1, title1);
					ps.setInt(2, 2);
					return ps;
				}
			}, keyHolder);
			int bookId = keyHolder.getKey().intValue();*/
			
			return bookId;
		
		
	}


	public void saveIO(Integer bId, int auId) throws ClassNotFoundException, SQLException {
		template.update("insert into tbl_book_authors (bookId, authorId) values (?, ?)",bId, auId);
		
	}
	
	public void update_book_author(Integer auId, int bId) throws ClassNotFoundException, SQLException {
		
		SqlRowSet count=template.queryForRowSet("select * from tbl_book_authors where bookId=? and authorId=?", new Object[] {bId,auId});
	        //int res = count.getInt("count(*)");
		 
			//return res;
		//boolean b=getCt("select * from tbl_book_authors where bookId=? and authorId=?", new Object[] {bId,auId});
		if(!count.next()){
			template.update("insert into tbl_book_authors (bookId,authorId) values (?,?)",bId,auId);
		}
		
	}

	
	public void updatebook_author(Integer bId, int auId) throws ClassNotFoundException, SQLException {
	
		SqlRowSet count=template.queryForRowSet("select * from tbl_book_authors where bookId=? and authorId=?", new Object[] {bId,auId});
		//boolean b=getCt("select * from tbl_book_authors where bookId=? and authorId=? ", new Object[] {bId,auId});
		if(!count.next()){
			template.update("insert into tbl_book_authors (bookId,authorId) values (?,?)",bId,auId);
		}
		
	}

	public void deleteGenre(Integer bookId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("old book_genre deleted");
		template.update("delete from tbl_book_genres where bookId = ?", new Object[] {bookId});
		
	}
	
	public void deleteAuthor(Integer bookId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("old book_authors deleted");
		template.update("delete from tbl_book_authors where bookId = ?", new Object[] {bookId});
		
	}
	
	public void saveIO1(int gId, Integer bId) throws ClassNotFoundException, SQLException {
		
		template.update("insert into tbl_book_genres (genre_id,bookId) values (?,?)",gId,bId);
		
	}
	public void Updatebook_genre(int gId, Integer bId) throws ClassNotFoundException, SQLException {
		// = (List<Genre>) readAll("select * from tbl_book_genres where bookId=?",new Object[] {bId});
		SqlRowSet g=template.queryForRowSet("select * from tbl_book_genres where bookId=? and genre_id=? ", new Object[] {bId,gId});
		//boolean b=getCt("select * from tbl_book_genres where bookId=? and genre_id=? ", new Object[] {bId,gId});
		if(!g.next()){
			template.update("insert into tbl_book_genres (genre_id,bookId) values (?,?)",gId,bId);
		}
		
		
	}

	public Book readBookByID(Integer bookId) throws ClassNotFoundException, SQLException {
		List<Book> b = (List<Book>) template.query("select * from tbl_book where bookId = ?", new Object[] {bookId},this);
		if(b!=null && b.size() >0){
			return b.get(0);
		}
		return null;
	}
	
	///////////query might be wrong
	public List<Book> readAuthorsBybook(String name, int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		name = "%"+name+"%";
		return (List<Book>) template.query("select * from tbl_author as a left join tbl_book_authors as ba on ba.authorId=a.authorId left join tbl_book as b on ba.bookId=b.bookId where b.title like ?", new Object[] {name},this);
	}

	public List<Book> readAllBookswithCard(int cardNo, int pageNo) {
		// TODO Auto-generated method stub
		setPageNo(pageNo);
		return (List<Book>) template.query("select * from tbl_book where bookId in (select bl.bookId from tbl_book_loans as bl where bl.cardNo=? and bl.dateIn is null)", new Object[] {cardNo},this);
		//return null;
	}

	public List<Book> readAllBookByAuthorId(int pageNo, int a_id) {
		setPageNo(pageNo);
	List<Book> b1=	(List<Book>) template.query("select * from tbl_book as b where b.bookId in(select ba.bookId from tbl_book_authors as ba where ba.authorId=?)", new Object[] {a_id},this);
		
	List<Book> books = new ArrayList<Book>();
		
	for (Book b : b1){
			//Book b = new Book();
		b.setBookId(b.getBookId());
		b.setTitle(b.getTitle());
		Publisher p = new Publisher();
		p.setPublisherId(p.getPublisherId());
		b.setPublisher(p);

		books.add(b);
		}
		return books;
	}

	


	






	


	

	

}


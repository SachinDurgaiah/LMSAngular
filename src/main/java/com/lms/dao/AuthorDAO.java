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

import com.lms.entity.Author;
import com.lms.entity.Book;

@SuppressWarnings("unchecked")
public class AuthorDAO extends BaseDAO implements ResultSetExtractor<List<Author>>{

	
	public List<Author> readAuthorsByName(String name, int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		name = "%"+name+"%";
		return (List<Author>) template.query("select * from tbl_author where authorName like ?", new Object[] {name},this);
	}
	
	public List<Author> readAuthorsByNameandbook(String name, int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		System.out.println("in author DAO");
		name = "%"+name+"%";
		return (List<Author>) template.query("select * from tbl_author as a left join tbl_book_authors as ba on ba.authorId=a.authorId left join tbl_book as b on ba.bookId=b.bookId where a.authorName like ? or b.title like ?", new Object[] {name,name},this);
	}
	
	public List<Author> readAuthorsByBook(String name, int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		name = "%"+name+"%";
		return (List<Author>) template.query("select * from tbl_author as a left join tbl_book_authors as ba on ba.authorId=a.authorId left join tbl_book as b on ba.bookId=b.bookId where b.title like ?", new Object[] {name},this);
	}
	
	
	
	
	public void addAuthorWithBook(int au_id,int b_id) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_book_authors (bookId,authorId) values (?,?)", new Object[] {b_id,au_id});
	}
	public void addAuthor(Author author) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_author (authorName) values (?)", new Object[] {author.getAuthorName()});
	}
	public Integer addAuthorWithID(Author a) throws ClassNotFoundException, SQLException{
		//return saveWithID("insert into tbl_author (authorName) values (?)", new Object[] {a.getAuthorName()});
		final String title1 = a.getAuthorName();
		final String INSERT_SQL = "insert into tbl_author (authorName) values (?) ";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, title1);
				//ps.setInt(2, 2);
				return ps;
			}
		}, keyHolder);
		int authorId = keyHolder.getKey().intValue();
		
		return authorId;
	}
	
	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException{
		template.update("update tbl_author set authorName = ? where authorId = ?", new Object[] {author.getAuthorName(), author.getAuthorId()});
	}
	
	public void deleteAuthor(int i) throws ClassNotFoundException, SQLException{
		System.out.println("final step of deletion "+i);
		template.update("delete from tbl_author where authorId = ?",new Object[]{i});
		
	}
	
	public List<Author> readAllAuthors(int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return (List<Author>) template.query("select * from tbl_author", this);
	}
	public List<Author> readAllAuthors() throws ClassNotFoundException, SQLException{
		
		return (List<Author>) template.query("select * from tbl_author", this);
	}
	public List<Author> readAuthorsByName(String name) throws ClassNotFoundException, SQLException{
		return (List<Author>) template.query("select * from tbl_author where authorName like ?", new Object[] {name},this);
	}
	
	public Integer getCount() throws ClassNotFoundException, SQLException{
		//return getCount("select count(*) from tbl_author",null);
		String sql = "select count(*) from tbl_author";
	    SqlRowSet count=template.queryForRowSet("select count(*) from tbl_book");
        int res = count.getInt("count(*)");
	 
		return res;
	}
	
	
	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<Author>();
		BookDAO bdao = new BookDAO();
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			
			authors.add(a);
		}
		return authors;
	}
	
	

	public Author readAuthorsByID(Integer authorId) throws ClassNotFoundException, SQLException {
		List<Author> authors = (List<Author>) template.query("select * from tbl_author where authorId = ?", new Object[] {authorId},this);
		if(authors!=null && authors.size() >0){
			return authors.get(0);
		}
		return null;
	}

	public void changeAuthor(int i, String authorName) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		//int a_id=Integer.parseInt(i);
		//int au_name=Integer.parseInt(authorName);
		System.out.println("Final steep of updating author");
		template.update("update tbl_author set authorName=? where authorId=?", new Object[] {authorName,i});
		
	}
	
	public void deleteBookAuthor(Integer authorId) throws ClassNotFoundException, SQLException{
		
		template.update("delete from tbl_book_authors where authorId = ?",new Object[]{authorId});
		
	}
	
	

	public List<Author> findAuthorByBookId(int pageNo, int bookId) {
		setPageNo(pageNo);
		return (List<Author>) template.query("select * from tbl_author as a where a.authorId in(select ba.authorId from tbl_book_authors as ba where ba.bookId=?) ", new Object[] {bookId},this);///call the readall first level function.
		
	}

}

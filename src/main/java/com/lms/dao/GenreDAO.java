package com.lms.dao;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.lms.entity.Book;
import com.lms.entity.Genre;
	

	public class GenreDAO extends BaseDAO implements ResultSetExtractor<List<Genre>> {
		
		public void addGenre(Genre genre) throws ClassNotFoundException, SQLException{
			template.update("insert into tbl_genre (genre_name) values (?)", new Object[] {genre.getGenre_name()});
		}
		
		public void updateGenre(Genre genre) throws ClassNotFoundException, SQLException{
			template.update("update tbl_author set authorName = ? where authorId = ?", new Object[] {genre.getGenre_name(), genre.getGenre_id()});
		}
		
		public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException{
			template.update("delete from tbl_author where authorId = ?", new Object[] {genre.getGenre_id()});
		}
		
		public List<Genre> readAllGenre() throws ClassNotFoundException, SQLException{
			return  template.query("select * from tbl_genre", this);
		}
		
		
		public List<Genre> readAllGenreByBook(int bookId){
			return (List<Genre>) template.query("select * from tbl_genre where genre_id in(select genre_id from tbl_book_genres where bookId=?) ",new Object[] {bookId}, this);
		}
		/*public List<Genre> readAuthorsByBookId(int ) throws ClassNotFoundException, SQLException{
			return (List<Author>) readAll("select * from tbl_author where authorName like ?", new Object[] {name});
		}*/
		
		public Integer getCount() throws ClassNotFoundException, SQLException{
			//return getCount("select count(*) from tbl_genre",null);
			
			SqlRowSet count=template.queryForRowSet("select count(*) from tbl_genre");
	        int res = count.getInt("count(*)");
		 
			return res;
			
		}
		
		@Override
		public List<Genre> extractData(ResultSet rs) throws SQLException {
			List<Genre> genre = new ArrayList<Genre>();
			
			while(rs.next()){
				Genre g = new Genre();
				//BookDAO bookDao = new BookDAO();
				g.setGenre_id(rs.getInt("genre_id"));
				g.setGenre_name(rs.getString("genre_name"));
				//a.setBookList(bookDAO.readAllBookByAuthorId(rs.getInt("authorId"))
				
				genre.add(g);
			}
			return genre;
		}
		
}

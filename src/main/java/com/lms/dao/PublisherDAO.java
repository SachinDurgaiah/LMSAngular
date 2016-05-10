package com.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.lms.entity.Book;
import com.lms.entity.Borrower;
import com.lms.entity.LibraryBranch;
import com.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO implements ResultSetExtractor<List<Publisher>>{


	@Override
public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publisher = new ArrayList<Publisher>();
		BookDAO bdao = new BookDAO();
		
		while(rs.next()){
			
			Publisher pub = new Publisher();
			pub.setPublisherId(rs.getInt("publisherId"));
			pub.setPublisherName(rs.getString("publisherName"));
			pub.setPublisherAddress(rs.getString("publisherAddress"));
			/*
			
			try{
				
					pub.setBook((List<Book>) bdao.readFirstLevel("select * from tbl_book where pubId = ?", new Object[] {pub.getPublisherId()}));
				
				}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			publisher.add(pub);
		}
		return publisher;
	}


	
	/*@Override
	public List<?> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Publisher> publisher = new ArrayList<Publisher>();
		while(rs.next()){
			Publisher pub = new Publisher();
			pub.setPublisherId(rs.getInt("publisherId"));
			pub.setPublisherName(rs.getString("publisherName"));
			pub.setPublisherAddress(rs.getString("publisherAddress"));
			
			publisher.add(pub);
		}
		
		return publisher;
	}*/

	public void addpublisher(String name,String Address) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		template.update("insert into tbl_publisher (publisherName, publisherAddress) values (?, ?)", new Object[] {name,Address});
	
	}
	public Integer getCount() throws ClassNotFoundException, SQLException{
		//return getCount("select count(*) from tbl_publisher",null);
		
		SqlRowSet count=template.queryForRowSet("select count(*) from tbl_publisher");
        int res = count.getInt("count(*)");
	 
		return res;
		
	}
	

	public void deletePublisher(Integer publisherId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		template.update("delete from tbl_publisher where publisherId = ?", new Object[] {publisherId});
		
	}

	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		template.update("update tbl_publisher set publisherName = ?,publisherAddress=? where publisherId = ?", new Object[] {publisher.getPublisherName(), publisher.getPublisherAddress(),publisher.getPublisherId()});
	}

	@SuppressWarnings("unchecked")
	public List<Publisher> readAllPublihser(int pageNo) throws ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		return (List<Publisher>) template.query("select * from tbl_publisher", this);
	}

	@SuppressWarnings("unchecked")
	public List<Publisher> getAllPublisher(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException {
		searchString = "%"+searchString+"%";
		setPageNo(pageNo);
		return (List<Publisher>) template.query("select * from tbl_publisher where publisherName like ? or publisherAddress like ?", new Object[] {searchString,searchString}, this);
	}
	
	public List<Publisher> getAllPublisherByName(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException {
		searchString = "%"+searchString+"%";
		setPageNo(pageNo);
		return (List<Publisher>) template.query("select * from tbl_publisher where publisherName like ?", new Object[] {searchString}, this);
	}
	
	public List<Publisher> getAllPublisherByAddress(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException {
		searchString = "%"+searchString+"%";
		setPageNo(pageNo);
		return (List<Publisher>) template.query("select * from tbl_publisher where publisherAddress like ?", new Object[] {searchString}, this);
	}
	
	
	public Publisher readPublisherByID(Integer publisherId) throws ClassNotFoundException, SQLException {
		@SuppressWarnings("unchecked")
		List<Publisher> pr = (List<Publisher>) template.query("select * from tbl_publisher where publisherId = ?", new Object[] {publisherId}, this);
		if(pr!=null && pr.size() >0){
			return pr.get(0);
		}
		return null;
	}

}

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
import com.lms.entity.Borrower;
import com.lms.entity.LibraryBranch;
import com.lms.entity.Publisher;
import com.lms.service.ConnectionUtil;
 
public class LibraryBranchDAO extends BaseDAO implements ResultSetExtractor<List<LibraryBranch>>{

	
	public void Update_library(LibraryBranch branch) throws ClassNotFoundException, SQLException{
		template.update("update tbl_library_branch set branchName= ?,branchAddress = ? where branchId = ?", new Object[] {branch.getBranchName(),branch.getBranchAddress(), branch.getBranchId()});
	}
	
	public int getCount() throws ClassNotFoundException, SQLException{
		//return getCount("select count(*) from tbl_borrower",null);
		List<LibraryBranch> count=template.query("select * from tbl_borrower",this);
        int res = count.size(); 
        System.out.println("res size :"+res);
		return res;
	}
	
	
	public int getCount1() throws ClassNotFoundException, SQLException{
		//return getCount("select count(*) from tbl_borrower",null);
		System.out.println("Final step of getting the count of branches");
		List<LibraryBranch> count=template.query("select * from tbl_library_branch",this);
		int res = count.size(); 
        System.out.println("res size :"+res);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<LibraryBranch> readAllBranches(int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return (List<LibraryBranch>) template.query("select * from tbl_library_branch", this);
	}
	
	public List<LibraryBranch> readAllBranches() throws ClassNotFoundException, SQLException{
		
		return (List<LibraryBranch>) template.query("select * from tbl_library_branch", this);
	}
	
	
	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> branch = new ArrayList<LibraryBranch>();
		
		while(rs.next()){
			LibraryBranch b = new LibraryBranch();
			b.setBranchId(rs.getInt("branchId"));
			b.setBranchName(rs.getString("branchName"));
			b.setBranchAddress(rs.getString("branchAddress"));
			
			branch.add(b);
		}
		return branch;
	}
	
	

	public void addLirbaryBranch(String name, String address) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		template.update("insert into tbl_library_branch (branchName,branchAddress) values (?,?)", new Object[] {name,address});
		
		
	}

	public void deleteLirbaryBranch(Integer branchId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		template.update("delete from tbl_library_branch where branchId = ?", new Object[] {branchId});
		
	}

	public void updateLirbaryBranch(LibraryBranch branch) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		template.update("update tbl_library_branch set branchName = ?,branchAddress=? where branchId = ?", new Object[] {branch.getBranchName(),branch.getBranchAddress(),branch.getBranchId()});
	}


	
	public LibraryBranch readAuthorsByID(Integer branchId) throws ClassNotFoundException, SQLException {
		@SuppressWarnings("unchecked")
		List<LibraryBranch> branch = (List<LibraryBranch>) template.query("select * from tbl_library_branch where branchId = ?", new Object[] {branchId}, this);
		if(branch!=null && branch.size() >0){
			return branch.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<LibraryBranch> getAllBranch(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		searchString = "%"+searchString+"%";
			System.out.println("In the borrower1 dao");
			return (List<LibraryBranch>) template.query("select * from tbl_library_branch where branchName like ? or branchAddress like ?", new Object[] {searchString,searchString},this);
		}
	
	public List<LibraryBranch> getAllBranchByName(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		searchString = "%"+searchString+"%";
			System.out.println("In the borrower1 dao");
			return (List<LibraryBranch>) template.query("select * from tbl_library_branch where branchName like ?", new Object[] {searchString}, this);
		}
	
	public List<LibraryBranch> getAllBranchByAddress(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		searchString = "%"+searchString+"%";
			System.out.println("In the borrower1 dao");
			return (List<LibraryBranch>) template.query("select * from tbl_library_branch where branchAddress like ?", new Object[] {searchString}, this);
		}
	
	
	
}

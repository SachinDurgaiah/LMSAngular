<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.lms.entity.Author"%>
<%@ page import="com.lms.entity.Book"%>
<%@ page import="com.lms.entity.Borrower"%>
<%@ page import="com.lms.service.AdministratorService"%>
<%@ include file="include.html"%>
    <% 
    	AdministratorService service = new AdministratorService();
    	List<Borrower> bow =new ArrayList<Borrower>();
    	Integer borrowerCount = service.getBorrowerCount();

    		if (request.getAttribute("bow") != null) {
    			bow = (List<Borrower>)request.getAttribute("bow");
    		}else{
    			bow = service.getAllBorrower(1);	
    		}
    		
    %>

    
<script type="text/javascript">

$(function(){
	$('#searchType li a').on('click', function(){
		console.log("Selected option :"+$(this).text());
		sType=$(this).text();
	});
});

   function searchBorrowers(){
	   $('#searchType li a').on('click', function(){
			console.log("Selected option :"+$(this).text());
			sType=$(this).text();
		});
	   console.log($('#searchString'));
	   ///if($('#searchString').val().legth<3) return;
    $.ajax({
    	
		  url: "searchBorrowers",
		  data:{
			  searchString:$('#searchStrings').val(),
			  text: sType	
			  }
		}).done(function(data) {
		  $('#borrowTable').html(data);
		});
   
}


function deleteBorrower(cardNo){
	$.ajax({
		  url: "deleteBorrower",
		  data:{
			  cardNo: cardNo
		  }
		}).done(function(data) {
		  $('#borrowTable').html(data);
		});
}

function editBorrower(cardNo){
	$.ajax({
		  url: "editBorrower",
		  data:{
			  cardNo: cardNo
		  }
		}).done(function(data) {
		  $('#borrowTable').html(data);
		});
}
</script>

<h2>Available Borrowers in LMS</h2>
${result}


	<form action="searchBorrowers">
       <div class="row">
       <div class="cl-lg-6">
       <div class="input-group">
       
      <input type="text" class="form-control" placeholder="Borrower Name"
			aria-describedby="basic-addon1" name="searchString" id="searchStrings" onkeyup="searchBorrowers()">
			<div class = "input-group-btn">
			<button type = "button" class = "btn btn-primary dropdown-toggle" data-toggle = "dropdown" aria-haspopup="true" aria-expanded="false">
			<span class = "caret"></span> <span class="sr-only">Toggle Dropdown</span>
   			</button>
   	
   			 <ul id="searchType" class = "dropdown-menu dropdown-menu-right">
   				<li><a tabindex="-1" href="#">Search by Borrower name</a></li>
   				<li><a tabindex="-1" href="#">Search by Borrower Address</a></li>
   				<li role="separator" class="divider"></li>
 				<li><a tabindex="-1" href="#">Search by All</a></li>  			
   </ul>
   <button type="button" class="btn btn-success" onclick="searchBorrowers()">Search</button>
        </div>
        </div>
        </div>
        </div>
        </form>
<div class="alert alert-danger" role="alert">
  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  <span class="sr-only">Error:</span>
 Enter a valid name!
</div>



<nav>
	<ul class="pagination">
		<li><a href="#" aria-label="Previous"> <span
				aria-hidden="true">&laquo;</span>
		</a></li>
		<%if(borrowerCount!=null &&  borrowerCount >0){
			int pageNo = borrowerCount % 10;
			int pages = 0;
			if(pageNo == 0){
				pages = borrowerCount/10;
			}else{
				pages = borrowerCount/10 + 1;
			}
			for(int i=1; i<=pages;i++){%>
				<li><a href="pageBorrowers?pageNo=<%=i%>"><%=i %></a></li>
			<%}
			
		} %>
		<li>
      		<a href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
	</ul>
</nav>


<div class="row">
	<div class="col-md-6">
		<table border="2" id="borrowTable"class="table">
	<tr>
		<th>Borrower Name</th>
		<th>Address</th>
		<th>Phone</th>
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	
		<%for (Borrower br: bow){ %>
		<tr>
		<td><% out.println(br.getName()); %></td>
		<td><% out.println(br.getAddress()); %></td>
		<td><% out.println(br.getPhone()); %></td>
		
		
		
		</td>

				<td align="center"><button type="button"
						class="btn btn btn-primary" data-toggle="modal"
						data-target="#myModal1"
						href="editBorrower?cardNo=<%=br.getCardNo()%>">EDIT</button></td>


				<td><button type="button" class="btn btn-sm btn-danger"
						onclick="deleteBorrower?cardNo(<%=br.getCardNo()%>)">DELETE</button></td>
				
			</tr>
		
		
		<%} %>
		
	

</table>
	</div>
</div>

</br>
<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>
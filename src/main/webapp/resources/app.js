var libraryModule = angular.module('libraryApp', [ 'angularUtils.directives.dirPagination','ngRoute', 'ngCookies' ]);

libraryModule.config([ "$routeProvider", function($routeProvider) {
	return $routeProvider.when("/", {
		redirectTo : "/home"
	}).when("/home", {
		templateUrl : "home.html"
	}).when("/listAuthors", {
		templateUrl : "listAuthors.html"
	}).when("/editBooks", {
		templateUrl : "editBooks.html"
	}).when("/addauthor", {
		templateUrl : "addauthors.html"
	}).when("/addBook", {
		templateUrl : "addBook.html"
	}).when("/listBooks", {
		templateUrl : "listBooks.html"
	}).when("/listBorrowers", {
		templateUrl : "listBorrowers.html"
	}).when("/listPublisher", {
		templateUrl : "listPublisher.html"
	}).when("/listBranches", {
		templateUrl : "listBranches.html"
	}).when("/editAuthors1", {
		templateUrl : "editAuthors1.html"
	}).when("/editBorrowers", {
		templateUrl : "editBorrowers.html"
	}).when("/editBranch", {
		templateUrl : "editBranch.html"
	}).when("/editPublisher", {
		templateUrl : "editPublisher.html"
	}).when("/librarybranch", {
		templateUrl : "libraryBranches.html"
	}).when("/addBcopies", {
		templateUrl : "addBcopies.html"
	}).when("/addPublisher", {
		templateUrl : "addPublisher.html"
	}).when("/addBorrower", {
		templateUrl : "addBorrower.html"
	}).when("/addBranch", {
		templateUrl : "addBranch.html"
	}).when("/CheckIn", {
		templateUrl : "borrower_checkIn.html"
	}).when("/CheckOut", {
		templateUrl : "borrower_checkOut.html"
	}).when("/listallCheckoutbooks", {
		templateUrl : "listallCheckoutbooks.html"
	}).when("/DisplayBranchesForCheckout", {
		templateUrl : "DisplayBranchesForCheckout.html"
	}).when("/booksOfBranch", {
		templateUrl : "booksOfBranch.html"
	}).when("/librarian_addBooks", {
		templateUrl : "librarian_addBooks.html"
	}).when("/booksList", {
		templateUrl : "booksList.html"
	}).when("/librarian", {
		templateUrl : "librarian_listofBranches.html"
	}).when("/renewAuthentication", {
		templateUrl : "renewAuthentication.html"
	}).when("/renewBooks", {
		templateUrl : "renewBooks.html"
	}).when("/service", {
		templateUrl : "home.html/#service"
	})

} ]);

libraryModule.controller('authorCtrl', function($scope, $route, $rootScope,
		$http, $cookieStore) {

	// get all authors and display initially

	$http.get('http://localhost:8080/lms/author/get').success(function(data) {
		$scope.authors = data;
		console.log($scope.authors);
	});
	
	
	
	$scope.addAuthor = function() {
		var author = {
			authorName : $scope.author.authorName
		};
		$http.post('http://localhost:8080/lms/addauthor', author).success(
				function(data) {
					alert('Author Added');
					window.location.href = "http://localhost:8080/lms/#/listAuthors";
					
				});

	};

	$scope.deleteAuthor = function deleteAuthor(authorId) {

		$http.post('http://localhost:8080/lms/deleteAuthors', {
			'authorId' : authorId,
		}).success(function(data) {
			alert('Author deleted');
			window.location.href = "http://localhost:8080/lms/#/listAuthors";
		});
		$route.reload();
		window.location.href = "http://localhost:8080/lms/#/listAuthors";
	};

	$scope.getAuthorByID = function getAuthorByID(authorId) {

		$http.post('http://localhost:8080/lms/getAuthorByID', {
			'authorId' : authorId

		}).success(function(data) {
			
			$rootScope.author = data;
			console.log($scope.author);
			window.location.href = "http://localhost:8080/lms/#/editAuthors1";
		});
	};

	$scope.changeAuthor = function changeAuthor() {
		$http.post('http://localhost:8080/lms/changeAuthor', {
			'authorName' : $scope.author.authorName,
			'authorId' : $scope.author.authorId,
		}).success(function(data) {
			$scope.authors = data;
			
				alert('fetched the author details.');
				window.location.href = "http://localhost:8080/lms/#/listAuthors";
			
		});
		$route.reload();
		window.location.href = "http://localhost:8080/lms/#/listAuthors";

	};
	/*$route.reload();
	window.location.href = "http://localhost:8080/lms/#/listAuthors";*/

});


libraryModule.controller('addBookCtrl', function($scope, $http, $cookieStore) {
	// get all authors and display initially
	/*$scope.pageSize=5;
	$scope.currentPage=1;*/
	$http.get('http://localhost:8080/lms/author/get').success(function(data) {
		$scope.authors = data;
		console.log($scope.authors);
	});
	
	$http.get('http://localhost:8080/lms/genre/get').success(function(data) {
		$scope.genre = data;
		console.log($scope.genre);
	});
	$http.get('http://localhost:8080/lms/publisher/get').success(function(data) {
		$scope.publisher = data;
		console.log($scope.publisher);
	});
	
	
	$scope.addBook = function(){
		var title = $scope.title;
		var selectedAuthors=$scope.selectedAuthors;
		var selectedGenres=$scope.selectedGenres;
		var selectedPublisher=$scope.selectedPublisher;
		console.log($scope.selectedAuthors);
		console.log($scope.selectedGenres);
		$http.post('http://localhost:8080/lms/addBooks', {
			'title': title,
			'author': selectedAuthors,
			'genre':selectedGenres,
			'publisher':selectedPublisher
	}).success(function(res) {
		window.location.href = "http://localhost:8080/lms/#/listBooks";
		}
		
		);
		
		
	};
	
	
	
	
});





libraryModule.controller('bookCtrl', function($scope,$route,$rootScope, $http, $cookieStore) {
	// get all authors and display initially

	$http.get('http://localhost:8080/lms/book/get').success(function(data) {
		$scope.book123 = data;
		console.log($scope.book123);
	});
	
	
	
	$http.get('http://localhost:8080/lms/author/get').success(function(data1) {
		$rootScope.authors1 = data1;
		//alert('got all authors');
		console.log($scope.authors1);
	});
	
	$http.get('http://localhost:8080/lms/genre/get').success(function(data2) {
		//alert('got all genres');
		$rootScope.genre1 = data2;
		console.log($scope.genre1);
		
	});
	$http.get('http://localhost:8080/lms/publisher/get').success(function(data3) {
		//alert('got all publishers1');
		$rootScope.publisher1 = data3;
		console.log($scope.publisher1);
	});
	
	

	$scope.deleteBook = function deleteBook(bookId) {

		$http.post('http://localhost:8080/lms/deletebook', {
			'bookId' : bookId,
		}).success(function(data) {
			alert('Book deleted');
			window.location.href = "http://localhost:8080/lms/#/listBooks";
		});
		//$route.reload();
		window.location.href = "http://localhost:8080/lms/#/listBooks";
	};
	$scope.getBookByID = function getBookByID(bookId) {

		
		$http.post('http://localhost:8080/lms/getBookByID', {
			'bookId' : bookId

		}).success(function(data) {
			
			
			
			$rootScope.books = data;
			console.log($scope.books);
			
			
			
			window.location.href = "http://localhost:8080/lms/#/editBooks";
			
		});
	};
			$scope.changeBooks = function(){
				//alert('in change books');
				var bookId=$scope.books.bookId;
				var title = $scope.title;
				var selectedAuthors=$scope.selectedAuthors;
				var selectedGenres=$scope.selectedGenres;
				var selectedPublisher=$scope.selectedPublisher;
				console.log($scope.selectedAuthors);
				console.log($scope.selectedGenres);
				//alert('in change books');
				$http.post('http://localhost:8080/lms/changeBooks', {
					'bookId': bookId,
					'title': title,
					'author': selectedAuthors,
					'genre':selectedGenres,
					'publisher':selectedPublisher
			}).success(function(res) {
				window.location.href = "http://localhost:8080/lms/#/listBooks";
			}
			
			);
				$route.reload();
				window.location.href = "http://localhost:8080/lms/#/listBooks";
			
		};

	
	
});


libraryModule.controller('borrowerCtrl', function($scope, $route, $rootScope,
		$http, $cookieStore) {
	// get all authors and display initially
	$http.get('http://localhost:8080/lms/borrower/get').success(function(data) {
		$scope.borrower = data;
		console.log($scope.borrower);
	});

	$scope.addBorrower = function() {
		// var author={authorName:$scope.author.authorName};
		$http.post('http://localhost:8080/lms/addborrower', {

			'name' : $scope.borrower.name,
			'address' : $scope.borrower.address,
			'phone' : $scope.borrower.phone,
		}).success(function(data) {
			alert('Author Added');
			window.location.href = "http://localhost:8080/lms/#/listBorrowers";
		});
		

	};

	$scope.deleteBorrower = function deleteBorrower(cardNo) {

		$http.post('http://localhost:8080/lms/deleteBorrower', {
			'cardNo' : cardNo,
		}).success(function(data) {
			alert('Borrower deleted');
			window.location.href = "http://localhost:8080/lms/#/listBorrowers";
		});
		$route.reload();
		window.location.href = "http://localhost:8080/lms/#/listBorrowers";
	};

	$scope.BorrowerByID = function BorrowerByID(cardNo) {

		$http.post('http://localhost:8080/lms/getBorrowerByID', {
			'cardNo' : cardNo,
		}).success(function(data) {
			alert('got the values using card no');
			$rootScope.borrowers = data;
			console.log($scope.borrowers);
			window.location.href = "http://localhost:8080/lms/#/editBorrowers";
		});
	};

	$scope.editBorrower = function editBorrower() {
		$http.post('http://localhost:8080/lms/updateBorrower', {
			'cardNo' : $scope.borrowers.cardNo,
			'name' : $scope.borrowers.name,
			'address' : $scope.borrowers.address,
			'phone' : $scope.borrowers.phone,
		}).success(function() {
			//$scope.borrowers = data;
			alert('Borrower updated successfully');
			window.location.href = "http://localhost:8080/lms/#/listBorrowers";
		});
		 $route.reload();
		window.location.href = "http://localhost:8080/lms/#/listBorrowers";

	};

});

libraryModule.controller('publisherCtrl', function($scope, $http, $route,$rootScope, $cookieStore) {
	// get all authors and display initially
	$http.get('http://localhost:8080/lms/publisher/get').success(
			function(data) {
				$scope.publisher = data;
				console.log($scope.publisher);
			});

	$scope.addPublisher = function() {
		// var author={authorName:$scope.author.authorName};
		$http.post('http://localhost:8080/lms/addpublisher', {

			'publisherName' : $scope.publisher.publisherName,
			'publisherAddress' : $scope.publisher.publisherAddress,

		}).success(function(data) {
			alert('Author Added');
			window.location.href = "http://localhost:8080/lms/#/listPublisher";
		});
		$route.reload();
		window.location.href = "http://localhost:8080/lms/#/listPublisher";
	};

	$scope.deletePublisher = function deletePublisher(publisherId) {

		$http.post('http://localhost:8080/lms/deletePublisher', {
			'publisherId' : publisherId,
		}).success(function(data) {
			alert('Publisher deleted');
			 window.location.href = "http://localhost:8080/lms/#/listPublisher";
		});
		$route.reload();
		 window.location.href = "http://localhost:8080/lms/#/listPublisher";
	};

	$scope.getPublisherID = function getPublisherID(publisherId) {

		$http.post('http://localhost:8080/lms/getPublisherID', {
			'publisherId' : publisherId,
		}).success(function(data) {
			alert('in publisher edit');
			$rootScope.publishers = data;
			console.log($scope.publishers);
			window.location = "http://localhost:8080/lms/#/editPublisher";
		});
	};

	$scope.updatePublisher = function updatePublisher() {
		$http.post('http://localhost:8080/lms/updatePublisher', {
			'publisherId' : $scope.publishers.publisherId,
			'publisherName' : $scope.publishers.publisherName,
			'publisherAddress' : $scope.publishers.publisherAddress,

		}).success(function(data) {
			alert('Author updated');
			window.location.href = "http://localhost:8080/lms/#/listPublisher";
		});
		$route.reload();
		window.location.href = "http://localhost:8080/lms/#/listPublisher";
		

	};

});

libraryModule.controller('branchCtrl', function($scope, $http, $rootScope,$cookieStore) {
	// get all authors and display initially
	$http.get('http://localhost:8080/lms/branch/get').success(function(data) {
		$scope.branch = data;
		console.log($scope.branch);
	});

	$scope.deleteBranch = function deleteBranch(branchId) {

		$http.post('http://localhost:8080/lms/deletebranch', {
			'branchId' : branchId,
		}).success(function(data) {
			alert('Branch deleted');
			//$route.reload();
			window.location.href = "http://localhost:8080/lms/#/listBranches";
		});
		//$route.reload();
		//window.location.href = "http://localhost:8080/lms/#/listBranches";
	};

	$scope.addBranch = function() {
		// var author={authorName:$scope.author.authorName};
		$http.post('http://localhost:8080/lms/addbranch', {

			'branchName' : $scope.branch.branchName,
			'branchAddress' : $scope.branch.branchAddress,

		}).success(function(data) {
			alert('branch Added');
			window.location.href = "http://localhost:8080/lms/#/listBranches";
		});
		//$route.reload();
		window.location.href = "http://localhost:8080/lms/#/listBranches";
	};

	$scope.getBranchID1 = function getBranchID1(branchId) {

		$http.post('http://localhost:8080/lms/getBranchID1', {
			'branchId' : branchId

		}).success(function(data) {

			$rootScope.branchs = data;
			console.log($scope.branchs);
			window.location = "http://localhost:8080/lms/#/editBranch";
		});
	};

	$scope.updateBranch = function updateBranch() {
		$http.post('http://localhost:8080/lms/updateBranch', {
			'branchId' : $scope.branchs.branchId,
			'branchName' : $scope.branchs.branchName,
			'branchAddress' : $scope.branchs.branchAddress,

		}).success(function(data) {
			//$scope.branchswer = data;
			//window.location = "http://localhost:8080/lms/#/editBranch";
			window.location.href = "http://localhost:8080/lms/#/listBranches";
			
		});
		//$route.reload();
		//window.location.href = "http://localhost:8080/lms/#/listBranches";

	};

});


libraryModule
.controller(
		'renewCtrl',
		function($scope, $http, $route, $rootScope, $cookieStore) {
			// get all authors and display initially
			var cn = null;
			$scope.verifyCard = function() {

				var cardNo = {
					cardNo : $scope.borrower.cardNo
				};
				cn = cardNo;
				$http
						.post(
								'http://localhost:8080/lms/verifyBorrowerforbookreturn',
								{
									'cardNo' : $scope.borrower.cardNo,
								})
						.success(
								function(data) {
									
									if (!data) {
										window.location.href = "http://localhost:8080/lms/#/renewAuthentication";

									} else {
										alert('borrower validated');
										$http
												.post(
														'http://localhost:8080/lms/getBooksofUser',
														cardNo)
												.success(
														function(data) {
															$rootScope.book1 = data;
															console
																	.log($scope.book1);

															window.location.href = "http://localhost:8080/lms/#/renewBooks";
														});
									}
								});
			};

			$scope.renewBook = function renewBook(bookId) {
				$http
						.post('http://localhost:8080/lms/renew',
								{
									'bookId' : bookId,
								})
						.success(
								function(data) {
									alert('Book renewed');
									$scope.authors = data;
									window.location.href = "http://localhost:8080/lms/#/home";
								});

			};

			// ////(((((((((()))))))))))))))))))))))) add a code to get
			// the list of all checked out books by that user
		});




libraryModule.controller('librarianCtrl', function($scope, $http, $route,$rootScope, $cookieStore) {
	// get all authors and display initially
	$http.post('http://localhost:8080/lms/branch/get').success(

	function(data) {
		$scope.branch = data;
		console.log($scope.branch);
	});

	$scope.getBranchID1 = function getBranchID1(branchId) {

		$http.post('http://localhost:8080/lms/getBranchID1', {
			'branchId' : branchId
		
		}).success(function(data) {
			alert('in innn');
			$rootScope.branches = data;
			console.log($scope.branches);
			window.location = "http://localhost:8080/lms/#/librarian";
		});
	};

	$scope.updateBranches = function updateBranches() {
		$http.post('http://localhost:8080/lms/updateBranch', {
			'branchId' : $scope.branches.branchId,
			'branchName' : $scope.branches.branchName,
			'branchAddress' : $scope.branches.branchAddress,

		}).success(function(data) {
			//$scope.publisher = data;
			window.location = "http://localhost:8080/lms/#/home";
		});
		

	};

});

libraryModule.controller('addBookCopies', function($scope, $http, $route,$rootScope, $cookieStore) {
	// get all authors and display initially
	$http.post('http://localhost:8080/lms/branch/get').success(

	function(data) {
		$scope.branches1 = data;
		console.log($scope.branches1);
	});

	$scope.branchSelected = function branchSelected(branchId) {

		$http.post('http://localhost:8080/lms/allBooks', {
			'branchId' : branchId

		}).success(function(data) {

			$rootScope.bookList = data;
			console.log($scope.bookList);
			window.location = "http://localhost:8080/lms/#/booksList";
		});
	};

	$scope.bookSelected = function bookSelected(bookId) {
		
		$http({
    url: 'http://localhost:8080/lms/bookSelected/'+bookId+'/'+$scope.noOfCopies, 
    method: "GET"
 }).success(function(data) {
			alert('Number of books updated');
			window.location.href = "http://localhost:8080/lms/#/home";
		});
		

	};

});

libraryModule
		.controller(
				'CheckInCtrl',
				function($scope, $http, $route, $rootScope, $cookieStore) {
					// get all authors and display initially
					var cn = null;
					$scope.verifyCard = function() {

						var cardNo = {
							cardNo : $scope.borrower.cardNo
						};
						cn = cardNo;
						$http
								.post(
										'http://localhost:8080/lms/verifyBorrowerforbookreturn',
										{
											'cardNo' : $scope.borrower.cardNo,
										})
								.success(
										function(data) {
											
											if (!data) {
												window.location.href = "http://localhost:8080/lms/#/CheckIn";

											} else {
												alert('borrower validated');
												$http
														.post(
																'http://localhost:8080/lms/getBooksofUser',
																cardNo)
														.success(
																function(data) {
																	$rootScope.book1 = data;
																	console
																			.log($scope.book1);

																	window.location.href = "http://localhost:8080/lms/#/listallCheckoutbooks";
																});
											}
										});
					};

					$scope.returnBook = function returnBook(bookId) {
						$http
								.post('http://localhost:8080/lms/returnBooks',
										{
											'bookId' : bookId,
										})
								.success(
										function(data) {
											alert('Book returned');
											$scope.authors = data;
											window.location.href = "http://localhost:8080/lms/#/home";
										});

					};

					// ////(((((((((()))))))))))))))))))))))) add a code to get
					// the list of all checked out books by that user
				});

libraryModule
		.controller(
				'CheckOutCtrl',
				function($scope, $http, $route, $rootScope, $cookieStore) {
					// get all authors and display initially

					$scope.verifyCard1 = function() {

						// var cardNo={cardNo: $scope.borrower.cardNo};

						$http
								.post(
										'http://localhost:8080/lms/verifyBorrowerforbookreturn',
										{
											'cardNo' : $scope.borrower.cardNo,
										})
								.success(
										function(data) {

											/*
											 * $scope.res = data;
											 * 
											 * if(res.equals("Displaycheckedoutbooks")){
											 * alert('borrower validated');
											 */
											$http
													.post(
															'http://localhost:8080/lms/branch/get')
													.success(
															function(data) {
																$rootScope.branches = data;
																console
																		.log($scope.branches);

																window.location.href = "http://localhost:8080/lms/#/DisplayBranchesForCheckout";
															});
											/*
											 * } else{ alert('borrower does not
											 * exist'); window.location.href =
											 * "http://localhost:8080/lms/#/borrower_checkIn"; }
											 */

										});
					};
					$scope.branchSelected = function branchSelected(branchId) {
						$http
								.post(
										'http://localhost:8080/lms/getAllBooksWithBranch',
										{
											'branchId' : branchId,
										})
								.success(
										function(data) {
											// alert('Book returned');
											$rootScope.books123 = data;
											console.log($scope.books123);
											window.location.href = "http://localhost:8080/lms/#/booksOfBranch";
										});

					};

					$scope.borrowBook = function borrowBook(bookId) {
						$http
								.post('http://localhost:8080/lms/borrowBook', {
									'bookId' : bookId,
								})
								.success(
										function(data) {
											alert('Book checked out');
											$scope.books1 = data;

											window.location.href = "http://localhost:8080/lms/#/home";
										});

					};

				});

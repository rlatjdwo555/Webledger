<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="ErrorPage.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="apple-touch-icon" sizes="76x76"
	href="/resources/img/apple-icon.png" />
<link rel="icon" type="image/png" href="/resources/img/favicon.png" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>거북이 자산관리</title>
<meta
	content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'
	name='viewport' />
<meta name="viewport" content="width=device-width" />
<!-- Bootstrap core CSS     -->
<link href="/resources/css/bootstrap.min.css" rel="stylesheet" />
<!--  Material Dashboard CSS    -->
<link href="/resources/css/material-dashboard.css?v=1.2.0"
	rel="stylesheet" />
<!--  CSS for Demo Purpose, don't include it in your project     -->
<link href="/resources/css/demo.css" rel="stylesheet" />
<!--     Fonts and icons     -->
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css"
	rel="stylesheet">
<link
	href='http://fonts.googleapis.com/css?family=Roboto:400,700,300|Material+Icons'
	rel='stylesheet' type='text/css'>
</head>

<body>
	<div class="wrapper">
		<div class="sidebar" data-color="purple"
			data-image="/resources/img/sidebar-1.jpg">
			<!--
        Tip 1: You can change the color of the sidebar using: data-color="purple | blue | green | orange | red"

        Tip 2: you can also add an image using data-image tag
   		-->
			<div class="logo">
				<a href="http://www.creative-tim.com" class="simple-text"> 님
					${sessionScope.userName}님 어서오세요 ! </a>
			</div>
			<div class="sidebar-wrapper">
				<ul class="nav">
					<li><a href="/main"> <i class="material-icons">dashboard</i>
							<p>Dashboard</p>
					</a></li>
					<li><a href="/addRecord"> <i class="material-icons">person</i>
							<p>User Profile</p>
					</a></li>
					<li class="active"><a href="/table"> <i
							class="material-icons">content_paste</i>
							<p>Table List</p>
					</a></li>
				</ul>
			</div>
		</div>
		<div class="main-panel">
			<nav class="navbar navbar-transparent navbar-absolute">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#"> Table List </a>
				</div>
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav navbar-right">
						<li><a href="#pablo" class="dropdown-toggle"
							data-toggle="dropdown"> <i class="material-icons">dashboard</i>
								<p class="hidden-lg hidden-md">Dashboard</p>
						</a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <i class="material-icons">notifications</i>
								<span class="notification">5</span>
								<p class="hidden-lg hidden-md">Notifications</p>
						</a>
							<ul class="dropdown-menu">
								<li><a href="#">Mike John responded to your email</a></li>
								<li><a href="#">You have 5 new tasks</a></li>
								<li><a href="#">You're now friend with Andrew</a></li>
								<li><a href="#">Another Notification</a></li>
								<li><a href="#">Another One</a></li>
							</ul></li>
						<li><a href="#pablo" class="dropdown-toggle"
							data-toggle="dropdown"> <i class="material-icons">person</i>
								<p class="hidden-lg hidden-md">Profile</p>
						</a></li>
					</ul>
					<form class="navbar-form navbar-right" role="search">
						<div class="form-group  is-empty">
							<input type="text" class="form-control" placeholder="Search">
							<span class="material-input"></span>
						</div>
						<button type="submit"
							class="btn btn-white btn-round btn-just-icon">
							<i class="material-icons">search</i>
							<div class="ripple-container"></div>
						</button>
					</form>
				</div>
			</div>
			</nav>
			<div class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-12">
							<div class="card">
								<div class="card-header" data-background-color="purple">
									<h4 class="title">My Account</h4>
									<p class="category">Here is a subtitle for this table</p>
									<!-- 모두/수입/지출 선택해서 보기  -->
								</div>
								<div class="card-content table-responsive">
								
								<form class="navbar-form navbar-left" role="search">
										<div class="form-group  is-empty">	
											<input type="text" class="form-control" id="title" placeholder="Category">
											<input type="text" class="form-control" id="content" placeholder="Content" >
											<input type="text" class="form-control" id="creDate" placeholder="Date">
											<input type="text" class="form-control" id="price" placeholder="Price">
								
											<span class="material-input"></span>
										</div>
										<button id="cbutton" onclick=addAccount()
											class="btn btn-white btn-round btn-just-icon">
											<i class="material-icons">create</i>
											<div class="ripple-container"></div>
										</button>
									</form>
									
									<div id="accountList"></div>
									
									<nav align="center">
										<p class="pageNumber"></p>
									</nav>
									
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<!--   Core JS Files   -->
<script src="/resources/js/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
<script src="/resources/js/material.min.js" type="text/javascript"></script>
<!--  Charts Plugin -->
<script src="/resources/js/chartist.min.js"></script>
<!--  Dynamic Elements plugin -->
<script src="/resources/js/arrive.min.js"></script>
<!--  PerfectScrollbar Library -->
<script src="/resources/js/perfect-scrollbar.jquery.min.js"></script>
<!--  Notifications Plugin    -->
<script src="/resources/js/bootstrap-notify.js"></script>

<script src="/resources/js/material-dashboard.js?v=1.2.0"></script>
<!-- Material Dashboard DEMO methods, don't include it in your project! -->
<script src="/resources/js/demo.js"></script>

<script>
var accountPage = 1;

getAccountList(1);

function getAccountList(page){
	
	$.getJSON("table/list/"+page, function(data){
		
		var str="<table class='table'>"
		+"<thead class='text-primary'>"
			+"<th>Category</th>"
			+"<th>Content</th>"
			+"<th>Date</th>"
			+"<th>Price</th>"
			+"<th>Edit</th>"
		+"</thead><tbody>";
		
		$(data.alist).each(function(){
			var date = this.creDate
			
			str+= "<tr><td>"+this.title+"</td>"
			 +"<td>"+this.content+"</td>"
			 +"<td>"+date.substring(2,10)+"</td>"
			 +"<td class='text-primary'>"+this.price+" 원</td>"
			 +"<td class='td-actions text-left'>"
				 +"<button type='button' rel='tooltip' title='Edit Task'"
						+"class='btn btn-primary btn-simple btn-xs' onclick='editAcc()'>"
						+"<i class='material-icons'>edit</i>"
					+"</button>"
					+"<button type='button' rel='tooltip' title='Remove'"
						+"class='btn btn-danger btn-simple btn-xs' onclick='deleteAcc("+this.ano+")'>"
						+"<i class='material-icons'>close</i>"
			 +"</button></td></tr>"
			 +"<input type='hidden' value='this.ano' name='ano'>";
		});
		
		str+="</tbody></table>";
		
		$("#accountList").html(str);	
		printPaging(data.pageMaker);
	});
}

function printPaging(pageMaker){
	var str="<ul class='pagination'><li class='page-item'>";
	
	if(pageMaker.prev){
		str+="<li class='page-item'>"
		+"<a href='"+(pageMaker.startPage - 1)+"' class='page-link' aria-label='Previous'>"
		+"<span aria-hidden='true'><<</span></a></li>";
	}else{
		str+="<li class='page-item'>"
		+"<a href='#' class='page-link' aria-label='Previous'>"
		+"<span aria-hidden='true'><<</span></a></li>";
	}
	
	for(var i=pageMaker.startPage, len=pageMaker.endPage; i<=len; i++){
		if(pageMaker.cri.page == i){
			str+="<li class='page-item active'><a href='"+i+"' class='page-link'>"+i+"</a></li>";
		}else{
			str+="<li class='page-item'><a href='"+i+"' class='page-link'>"+i+"</a></li>";
		}
	}
	
	if(pageMaker.next){
		str+="<li class='page-item'>"
		+"<a href='"+(pageMaker.endPage + 1)+"' class='page-link' aria-label='Next'>"
		+"<span aria-hidden='true'>>></span></a></li>";
	}else{
		str+="<li class='page-item'>"
		+"<a href='#' class='page-link' aria-label='Next'>"
		+"<span aria-hidden='true'>>></span></a></li>";
	}
	
	str+="</li></ul>";
	
	$('.pageNumber').html(str);
}

$(".pageNumber").on("click", "li a", function(event){
	event.preventDefault();
	accountPage = $(this).attr("href");
	getAccountList(accountPage);
});

function editAcc(){
	var el = document.getElementById("cbutton"); 
	el.setAttribute("onclick", "update()");
	
	
}


function deleteAcc(ano){
	$.ajax({
		type : 'delete',
		url : 'table/delete/'+ano,
		headers : {
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override" : "DELETE"
		},
		dataType : 'text',
		success : function(result){
			console.log("result: "+result);
			if(result == 'SUCCESS'){
				alert("삭제 되었습니다.");
				getAccountList(accountPage);
				//getAllList();
			}
		}
	});
}

function addAccount(){
	var title = $("#title").val();
	var content = $("#content").val();
	var creDate = $("#creDate").val();
	var price = $("#price").val();
	
	$.ajax({
		type : 'post',
		url : 'table/add',
		headers : {
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override" : "POST"
		},
		dataType : 'text',
		data : JSON.stringify({
			title : title,
			content : content,
			creDate : creDate,
			price : price
		}),
		success : function(result){
			if(result == 'SUCCESS'){
				alert("등록되었습니다.");
				getAccountList(accountPage);
				//getAllList();
			}
		}
	});
}


</script>
</html>
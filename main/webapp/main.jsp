<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<title>project</title>
</head>
<body>
	<!-- 로그인이 된 사람은 로그인 정보를 담을 수 있도록 만들어준다. -->
	<%
	
	String userID = null;
	if (session.getAttribute("userID") != null) {
		
		userID = (String) session.getAttribute("userID");
		
	}
	
	%>
	
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
			data-toggle="collapse" data-target="#bs-example-nabvar-collapse-1">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">Sy Web Site</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
				<li><a href="font.jsp">포스터 분석</a></li>
				<li><a href="posterCategory.jsp">포스터 카테고리</a></li>
				<li><a href="analisys.jsp">통계</a></li>
				<li><a href="bbs.jsp">게시판</a></li>
			</ul>
			
			<%
				//로그인이 되어있지 않다면
				if (userID == null) {
			%>
			
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" 
					role="button" aria-haspopup="true" data-toggle="dropdown" 
					aria-expanded="false">접속하기<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li class="active"><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul>
				</li>
			</ul>
			
			<%
				} else {
			%>
			
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					role="button" aria-haspopup="true" data-toggle="dropdown"
					aria-expanded="false">회원관리<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li class="active"><a href="logoutAction.jsp">로그아웃</a></li>
					</ul>
				</li>
			</ul>
			
			<%
				}
			%>
			
		</div>
	</nav>
	<div class="container">
		<div class="row">
			<!-- 홀수와 짝수로 색상이 변경됨 -->
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<!-- thead : 테이블의 제목부분 각각의 속성을 알려주는 역할 -->
				<thead>
					<tr>
						<th style="background-color: #eeeee; text-align: center;">번호</th>
						<th style="background-color: #eeeee; text-align: center;">제목</th>
						<th style="background-color: #eeeee; text-align: center;">작성자</th>
						<th style="background-color: #eeeee; text-align: center;">작성일</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>번호</td>
						<td>제목</td>
						<td>작성자</td>
						<td>날짜</td>
					</tr>
				</tbody>
			</table>
			<a href="write.jsp" class="btn btn-primary pull right">글쓰기</a>
		</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-1.11.3.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>
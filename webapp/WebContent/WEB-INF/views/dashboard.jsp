<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="label.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> <spring:message
					code="label.header" /></a>
			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="?lang=fr">
					<button type="button" class="btn btn-default">
						<spring:message code="french" />
					</button>
				</a> <a href="?lang=en">
					<button type="button" class="btn btn-default">
						<spring:message code="english" />
					</button>
				</a> <a href="logout"><button type="button" class="btn btn-default">
						<i class="fa fa-sign-out" aria-hidden="true"></i>
					</button></a>
			</div>
		</div> 
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${computerTotal}"></c:out>
				<spring:message code="label.count" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="dashboard" method="GET"
						class="form-inline">

						<spring:message code="label.filter" var="filter" />
						<spring:message code="label.search" var="searching" />

						<input type="search" id="searchbox" name="search"
							class="form-control" value='${search}' placeholder="${searching}" />
						<input type="submit" id="searchsubmit" value="${filter}"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message
							code="label.add" /></a> <a class="btn btn-default" id="editComputer"
						href="#" onclick="$.fn.toggleEditMode();"><spring:message
							code="label.delete" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><spring:message code="label.name" /></th>
						<th><spring:message code="label.introduced" /></th>
						<!-- Table header for Discontinued Date -->
						<th><spring:message code="label.discontinued" /></th>
						<!-- Table header for Company -->
						<th><spring:message code="label.company" /></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computerPage}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="0"></td>
							<td><a href="editComputer?id=${computer.getId()}" onclick=""><c:out
										value="${computer.getName()}" /></a></td>
							<td><c:out value="${computer.getIntroduced()}" /></td>
							<td><c:out value="${computer.getDiscontinued()}" /></td>
							<td><c:out value="${computer.getCompanyName()}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><a
					href="?page=${pageActual-1}&size=${pageSize}&search=${search}"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<li><a href="?page=1&size=${pageSize}&search=${search}">1</a></li>
				<li><a href="?page=2&size=${pageSize}&search=${search}">2</a></li>
				<li><a href="?page=3&size=${pageSize}&search=${search}">3</a></li>
				<li><a href="?page=4&size=${pageSize}&search=${search}">4</a></li>
				<li><a href="?page=5&size=${pageSize}&search=${search}">5</a></li>
				<li><a
					href="?page=${pageActual+1}&size=${pageSize}&search=${search}"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="?page=1&size=10&search=${search}"><button type="button"
						class="btn btn-default">10</button></a> <a
					href="?page=1&size=50&search=${search}"><button type="button"
						class="btn btn-default">50</button></a> <a
					href="?page=1&size=100&search=${search}"><button type="button"
						class="btn btn-default">100</button></a>
			</div>
		</div>
	</footer>
	<script src="./js/jquery.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/dashboard.js"></script>

</body>
</html>
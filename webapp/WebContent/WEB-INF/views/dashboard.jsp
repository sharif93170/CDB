<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="pagination" uri="/WEB-INF/taglibs/pagination.tld"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp"%>
<body>
	<%@ include file="navbar.jsp"%>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${numberOfComputers}
				<spring:message code="dashboard.numberOfComputers" />
			</h1>
			<c:choose>
				<c:when test="${computerDeleteSuccess=='true'}">
					<div class="alert alert-success">
						<strong>${deleteState} <spring:message
								code="dashboard.deleteMessage" /></strong>
					</div>
					<br />
				</c:when>
				<c:otherwise>
					<br />
				</c:otherwise>
			</c:choose>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm"
						action="${pageContext.request.contextPath}/computer/search"
						method="GET" class="form-inline">
						
						<spring:message code="dashboard.searchPlaceholder" var="searchPlaceholder"></spring:message>
						 <input
							type="search" id="searchbox" name="search" class="form-control"
							placeholder="${searchPlaceholder}" value="${search}" />
							
						<spring:message code="dashboard.searchsubmit" var="submitSearch"></spring:message>
						 <input
							type="submit" id="searchsubmit" value="${submitSearch}"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="${pageContext.request.contextPath}/computer/addComputer"><spring:message code="dashboard.addComputer" /></a> 
						<a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();"><spring:message code="dashboard.editComputer" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm"
			action="${pageContext.request.contextPath}/computer/deleteComputer"
			method="POST">
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
						<th><spring:message code="dashboard.tableHeaderComputerName" /></th>
						<th><spring:message
								code="dashboard.tableHeaderComputerIntroducedDate" /></th>
						<!-- Table header for Discontinued Date -->
						<th><spring:message
								code="dashboard.tableHeaderComputerDiscontinuedDate" /></th>
						<!-- Table header for Company -->
						<th><spring:message
								code="dashboard.tableHeaderComputerCompany" /></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers}" var="record">

						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${record.id }"></td>
							<td><a
								href="${pageContext.request.contextPath}/computer/editComputer?ComputerToModifie=${record.id }"
								onclick="">${record.name }</a></td>
							<td>${record.introduced }</td>
							<td>${record.discontinued }</td>
							<td>${record.company.name }</td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">

		<div class="container text-center">
			<c:url var="searchUri" value="/computer/searchPage?pageNumber=##" />
			<pagination:display maxLinks="10"
				currPage="${computerPage}"
				totalPages="${numberTotalOfPages}" uri="${searchUri}"
				searchName="${search}"
				nombreElementPerPage="${nombrElementPerPage }" />

			<div class="btn-group btn-group-sm pull-right" role="group">

				<form
					action="${pageContext.request.contextPath}/computer/nombreElementPerPage"
					method="get">
					<button type="submit" name="buttonSetNumberElementDisplayed"
						value="10" class="btn btn-default">10</button>
					<button type="submit" name="buttonSetNumberElementDisplayed"
						value="50" class="btn btn-default">50</button>
					<button type="submit" name="buttonSetNumberElementDisplayed"
						value="100" class="btn btn-default">100</button>
				</form>

			</div>

		</div>

	</footer>



</body>
</html>
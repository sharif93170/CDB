<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp"%>
<body>
	<%@ include file="navbar.jsp"%>

	<section id="main">
		<div>
			<c:choose>
				<c:when test="${success=='true'}">
					<div class="alert alert-success">
						<strong><spring:message code="addComputer.success" /></strong>
					</div>
					<br />
				</c:when>
				<c:when test="${success=='false'}">
					<div class="alert alert-danger">
						<strong><spring:message code="addComputer.notSuccess" />
							${errors}</strong>
					</div>
					<br />
				</c:when>
				<c:otherwise>
					<br />
				</c:otherwise>
			</c:choose>
		</div>
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="addComputer.pageName" />
					</h1>
					<form:form
						action="${pageContext.request.contextPath}/computer/addComputer"
						method="POST" id="addComputerForm" modelAttribute="ComputerDTO">
						<fieldset>
							<div class="form-group">
								<form:label for="computerName" path="name">
									<spring:message code="dashboard.tableHeaderComputerName" />
								</form:label>
								<spring:message code="dashboard.tableHeaderComputerName"
									var="ComputerNamePlaceholder"></spring:message>

								<form:input data-validation="length alphanumeric" type="text"
									class="form-control" id="computerName" name="computerName"
									placeholder="${ComputerNamePlaceholder}"
									value="${computer.name}" path="name" />
							</div>
							<div class="form-group">
								<form:label for="introduced" path="introduced">
									<spring:message
										code="dashboard.tableHeaderComputerIntroducedDate" />
								</form:label>
								<form:input type="date" class="form-control" id="introduced"
									name="introduced" placeholder="Introduced date"
									value="${computer.introduced}" path="introduced" />
							</div>
							<div class="form-group">
								<form:label for="discontinued" path="discontinued">
									<spring:message
										code="dashboard.tableHeaderComputerDiscontinuedDate" />
								</form:label>
								<form:input type="date" class="form-control" id="discontinued"
									name="discontinued" placeholder="Discontinued date"
									value="${computer.discontinued}" path="discontinued" />
							</div>
							<div class="form-group">
								<form:label for="companyId" path="companyId">
									<spring:message code="dashboard.tableHeaderComputerCompany" />
								</form:label>
								<form:select class="form-control" id="companyId"
									name="companyId" path="companyId">
									<option value="${computer.company.id}">${computer.company.name}</option>
									<option value="0">--</option>
									<c:forEach items="${companies}" var="record">
										<option value="${record.id }">${record.name }</option>
									</c:forEach>
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<spring:message code="addComputer.addsubmit" var="addsubmit"></spring:message>
							<input type="submit" value="${addsubmit}" class="btn btn-primary"
								id="Add" name="save">
							<spring:message code="dictionnay.or" />
							<a href="${pageContext.request.contextPath}/computer/"
								class="btn btn-default"><spring:message
									code="addComputer.cancel" /> </a>
						</div>
					</form:form>
				</div>
			</div>


		</div>
	</section>
</body>
<script>
	var namehaveWrongSize = "<spring:message code='addComputervalidatorMessage.length' />";
	var nameconteinIllegalcarracters = "<spring:message code='addComputervalidatorMessage.illecgalcarracters' />";
	var discountedDateWithoutIntroduced = "<spring:message code='addComputervalidatorMessage.discountedDateWithoutIntroduced' />";
	var wrongOrderOfDate = "<spring:message code='addComputervalidatorMessage.wrongOrderOfDate' />";
</script>

<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>

<script type='text/javascript'
	src='http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js'></script>
		<script src="../js/addComputerValidator.js"></script>
</html>
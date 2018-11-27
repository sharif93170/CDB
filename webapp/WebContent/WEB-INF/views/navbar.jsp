<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<h2>
			<a href="${pageContext.request.contextPath}/computer/"> <spring:message
					code="application.name" />
			</a>
		</h2>
		<div>
			<a href="?lang=en">English</a> | <a
				href="?lang=fr">Français</a> <a
				href="<c:url value="/logout" />">Logout</a>
		</div>
	</div>
</header>
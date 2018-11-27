<!DOCTYPE html>
<html>
<%@ include file="WEB-INF/views/header.jsp" %> 
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
			<h1 id="homeTitle">Welcome to computer database Application</h1>
			 
	</header>

	<section id="main">
		<div class="container">
			<div class="container">
				<a href="/computer/"> See computers </a>
				<p>${message}</p>
				
		</div>
		</div>

		<div class="container" style="margin-top: 10px;"></div>
	</section>

	<footer class="navbar-fixed-bottom"> </footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>
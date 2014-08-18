<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" indent="yes" />

	<xsl:template match="/">
		<html>
			<head>
				<title>Contact Update</title>
				<link rel="stylesheet" href="static/css/reset.css" />
				<link rel="stylesheet" href="static/css/default.css" />
				<script type="text/javascript" src="static/js/jquery-1.10.2.min.js"></script>
				
				<script type="text/javascript">
					
					$(document).ready(
						function() {
							$('a.save').click(
								function() {
									alert('I promise I\'ll save you!');
									return false;
								}
							);
						}
					);
					
				</script>
				
				<style type="text/css">
					
					div.buttons {
						margin-left: 210px;
					}
					
				</style>
			</head>
			<body>
				<div class="content">
					<h1>Contact Update</h1>
					<form action="">
						<div>
							<label for="firstName">First Name </label>
							<input type="text" name="firstName" />
						</div>
						<div>
							<label for="lastName">Last Name </label>
							<input type="text" name="lastName" />
						</div>
						<div>
							<label for="phoneNumber">Phone Number </label>
							<input type="text" name="phoneNumber" />
						</div>
						
						<div class="buttons">
							<a class="button save" href="MainServlet?example=1&amp;action=save">save</a>
						</div>
					</form>
				</div>
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>

<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" indent="yes" />

	<xsl:template match="/">
		<html>
			<head>
				<title>Contact List</title>
				<link rel="stylesheet" href="static/css/reset.css" />
				<link rel="stylesheet" href="static/css/default.css" />
				<script type="text/javascript" src="static/js/jquery-1.10.2.min.js"></script>
			</head>
			<body>
				<div class="content">
					<h1>Contact List</h1>
					<div class="tableWrapper">
						<table class="contact">
							<tr>
								<th>First Name</th>
								<th>Phone Number</th>
								<th>Action</th>
							</tr>
							<xsl:apply-templates select="/list/contact" />
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="/list/contact">
		<xsl:variable name="contactId" select="id" />
		<xsl:variable name="cssClass">
			<xsl:choose>
				<xsl:when test="position() mod 2 = 0">even</xsl:when>
				<xsl:otherwise>odd</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<tr class="{$cssClass}">
			<xsl:apply-templates select="firstName" />
			<xsl:apply-templates select="phoneNumber" />
			<td>
				<div class="buttons">
					<a class="button" href="MainServlet?example=1&amp;action=update&amp;id={$contactId}">update</a>
					<a class="button" href="MainServlet?example=1&amp;action=delete&amp;id={$contactId}">delete</a>
				</div>
			</td>
		</tr>
	</xsl:template>
 
	<xsl:template match="/list/contact/firstName">
		<td class="firstName"><xsl:apply-templates /></td>
	</xsl:template>
 
	<xsl:template match="/list/contact/phoneNumber">
		<td class="phoneNumber"><xsl:apply-templates /></td>
	</xsl:template>
 
</xsl:stylesheet>

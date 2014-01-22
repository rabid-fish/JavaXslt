<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" indent="yes" />

	<xsl:template match="/">
		<html>
			<body>
				<xsl:apply-templates />
			</body>
		</html>
	</xsl:template>

	<xsl:template match="/root/contact">
		<h1 align="center">
			<xsl:apply-templates />
		</h1>
	</xsl:template>
 
	<xsl:template match="/root/contact/firstName">
		<span class="firstName">
			<xsl:apply-templates />
		</span>
	</xsl:template>
 
	<xsl:template match="/root/contact/lastName" />
 
	<xsl:template match="/root/contact/phoneNumber">
		<span class="phoneNumber">
			<xsl:apply-templates />
		</span>
	</xsl:template>
 
</xsl:stylesheet>

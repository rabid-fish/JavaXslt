<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0" xmlns:sql="org.apache.xalan.lib.sql.XConnection"
	extension-element-prefixes="sql">

	<!-- 
		See http://xml.apache.org/xalan-j/samples.html#sql
		for examples using xalan's lib sql extension.
	 -->

	<xsl:output method="html" indent="yes" />

	<xsl:param name="driver" select="'org.h2.Driver'" />
	<xsl:param name="url" select="'jdbc:h2:~/test;USER=sa'" />
	<xsl:param name="query" select="'SELECT * FROM contact'" />

	<xsl:template match="/">
		<xsl:variable name="db" select="sql:new($driver, $url)" />
		<xsl:variable name="table" select='sql:query($db, $query)' />
		<xsl:copy-of select="$table" />
		<xsl:value-of select="sql:close($db)" />
	</xsl:template>

</xsl:stylesheet>

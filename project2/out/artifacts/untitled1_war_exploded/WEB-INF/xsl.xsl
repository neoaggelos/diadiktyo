<?xml version="1.0"?>
<xsl:stylesheet version="1.0"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!-- page body -->
    <xsl:template match="/">
        <html>
            <head>
                <title>Results</title>
            </head>
            <body background="Money.jpg" style="text-align: center">
                <h1 style="background-color: #446600;
          color: #FFFFFF; font-size: 20pt; text-align: center; font-family: Ubuntu;
          letter-spacing: 1.0em; margin: 5% 20% 5% 20%; width:60%">Results</h1>
                <xsl:apply-templates select="/*"/>
            </body>
        </html>
    </xsl:template>

    <!-- content goes in an unordered list -->
    <xsl:template match="/*">
        <ul style="margin-left:20%; margin-right: 20%; width: 60%;" >
            <xsl:apply-templates/>
        </ul>
    </xsl:template>

    <!-- top-level items go in separate list item -->
    <xsl:template match="/*/*">
        <li>
            <xsl:apply-templates select="*"/>
        </li>
    </xsl:template>

    <!-- attributes go in a comma separated list -->
    <xsl:template match="*">
        <b><xsl:value-of select="name()"/></b> : <xsl:value-of select="."/>,
    </xsl:template>

</xsl:stylesheet>
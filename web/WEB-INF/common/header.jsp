<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="syrup" uri="/WEB-INF/syrup.tld" %>
 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en">
<head>
<title>Syrup - ${requestScope.pageTitle}</title>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<link rel="shortcut icon" href="<c:url value="/images/favicon.ico" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/960/reset.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/960/text.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/960/960.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/prompt.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/annotate.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/superfish.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/superfish-navbar.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/jquery-ui-1.8.1.custom/css/flick/jquery-ui-1.8.1.custom.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/javascript/fileuploader/fileuploader.css" />" />

<script type="text/javascript" src="<c:url value="/javascript/util.js" />"></script>
<script type="text/javascript" src="<c:url value="/jquery-ui-1.8.1.custom/js/jquery-1.4.2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/jquery-ui-1.8.1.custom/js/jquery-ui-1.8.1.custom.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/javascript/jquery-jeditable-min.js" />"></script>
<script type="text/javascript" src="<c:url value="/javascript/jquery-impromptu.2.7.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/javascript/jquery.textarearesizer.compressed.js" />"></script>
<script type="text/javascript" src="<c:url value="/javascript/jquery-rotate.js" />"></script>
<script type="text/javascript" src="<c:url value="/javascript/jqtouch.js" />"></script>
<script type="text/javascript" src="<c:url value="/javascript/superfish.js" />"></script>
<script type="text/javascript" src="<c:url value="/javascript/hoverIntent.js" />"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/javascript/jquery.annotate.js" />"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/javascript/jquery.touch.js" />"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/javascript/jqtouch_site.js" />"></script>
<script type="text/javascript" src="<c:url value="/javascript/fileuploader/fileuploader.js" />"></script>

<script LANGUAGE="Javascript">
<!---
function decision(message, url){
if(confirm(message)) location.href = url;
}
// --->


$(document).ready(function() {
	$('textarea.resizable:not(.processed)').TextAreaResizer();
	$('ul.sf-menu').superfish({
		delay:       1000,                            // one second delay on mouseout
		animation:   {opacity:'show',height:'show'},  // fade-in and slide-down animation
		speed:       'fast',                          // faster animation speed
		autoArrows:  false,                           // disable generation of arrow mark-up
		dropShadows: false                            // disable drop shadows
	});
});


</script>
</head>
<body>
<div style="width:100%; height:5px; background-color:black;"></div>
<div>


<div id="header">
    
    
	<%@ include file="/WEB-INF/common/message.jsp"%>
	<%
	String ua = request.getHeader( "User-Agent" );
	boolean isFirefox = ( ua != null && ua.indexOf( "Firefox/" ) != -1 );
	boolean isMSIE = ( ua != null && ua.indexOf( "MSIE 6.0" ) != -1 );
	response.setHeader( "Vary", "User-Agent" );
	%>
	<% if( isMSIE ){ %>
	  <span class="alert_message" style="position:absolute; top:0; right:200; width:500px;">This isn't designed for <b>Internet Explorer 6.0</b>. You should use another browser.</span>
	<% } %>
	
	<div id="topnav" style="margin-bottom:3em;width:100%;">
	
	<ul class="sf-menu" >
		<li class="<c:if test="${currentTab == 'home'}">current</c:if>"><a
			href="<c:url value="/home" />">Home  <span class="sf-sub-indicator"> &#187;</span></a>
			<ul>
				<li><a title="Create a project"
					href="<c:url value="/project/setup" />">Create a Project</a></li>
			    <li><a title="File Upload"
					href="<c:url value="/filesysteminfo" />">File upload</a></li>
			</ul>
		</li>
		<c:choose>
		  <c:when test="${currentTab != 'home'}">
		    <%// Do not show project %>
		  </c:when>
		  <c:otherwise>
			<c:if test="${!empty project.id}">
				<li>
				   <c:url value="/page/setup" var="createPage">
				     <c:param name="projectId" value="${project.id}"/>
				     <c:param name="action" value="new"/>
				   </c:url>
				   <c:url value="/project/setup" var="projectHome">
		             <c:param name="projectId" value="${project.id}"/>
		           </c:url>
				   <a href="${projectHome}">Project  <span class="sf-sub-indicator"> &#187;</span></a>
		            <ul>
		                <li><a title="Create a page" href="${createPage}">Create a page</a></li>
		            </ul>
		        </li>
			 </c:if>
		  </c:otherwise>
		  </c:choose>
		<li <c:if test="${currentTab == 'help'}">class="current"</c:if>><a
			href="<c:url value="/help" />">Help</a></li>
	</ul>
	<span style="float:right;"><a href="<c:url value="/home" />" class="logo">Syrup </a></span>
	</div>
	<div style="border-bottom:1px solid #CCCCCC;"></div>
</div>

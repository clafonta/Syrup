<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Help" scope="request" />
<c:set var="currentTab" value="help" scope="request" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<h1>
	<a href="http://960.gs/">960 Grid System</a>
</h1>
<div class="container_12">
	<h2>
		12 Column Grid
	</h2>
	<!-- end .grid_10 -->
	<div class="clear"></div>
	<div class="grid_3">
		<p id="palette">
			
		</p>
		
	</div>
	<!-- end .grid_3 -->
	<div class="grid_9">
		<p id="dcanvas" style="height:500px;">
			 
		</p>
	</div>
	<!-- end .grid_9 -->
	<div class="clear"></div>

</div>
<jsp:include page="/WEB-INF/common/footer.jsp" />
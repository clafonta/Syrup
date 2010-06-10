<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="syrup" uri="/WEB-INF/syrup.tld" %>
<c:set var="pageTitle" value="Home" scope="request" />
<c:set var="currentTab" value="home" scope="request" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<script type="text/javascript"><!--
$(document).ready(function(){
    	
    	$("#toAnnotateDiv").annotateImage({
    	      editable: true,
    	      useAjax: false,
    	      notes: [ { "top": 286, 
    	                 "left": 161, 
    	                 "width": 52, 
    	                 "height": 37, 
    	                 "text": "Small people on the steps", 
    	                 "id": "e69213d0-2eef-40fa-a04b-0ed998f9f1f5", 
    	                 "editable": false },
    	               { "top": 134, 
    	                 "left": 179, 
    	                 "width": 68, 
    	                 "height": 74, 
    	                 "text": "National Gallery Dome", 
    	                 "id": "e7f44ac5-bcf2-412d-b440-6dbb8b19ffbe", 
    	                 "editable": true } ]   
    	    });
    	    	
        
    });

    --></script>
<div id="canvas-container" class="container_12">
    <!-- end .grid_10 -->
    <div class="clear"></div>
    <div id="toAnnotateDiv" style="background-color:yellow;width:700px;height:800px;">You have text
        <img src="<c:url value="/images/trafalgar-square-annotated.jpg"/>" alt="trafalgar-square-annotated.jpg" class="">
    </div>
    
</div>
<jsp:include page="/WEB-INF/common/footer.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="syrup" uri="/WEB-INF/syrup.tld" %>
<c:set var="pageTitle" value="Home" scope="request" />
<c:set var="currentTab" value="home" scope="request" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<script>
$(document).ready( function() {
	$('.delete-page').each( function() {
        $(this).click( function() {
            var pageId = this.id.split("_")[1];
            $.prompt(
                'Are you sure you want to delete this Page?',
                {
                    callback: function (proceed) {
                        if(proceed) {
                            //document.location="<c:url value="/page/setup" />?action=deletePage&pageId="+ pageId;
                        	$.post('<c:url value="/page/setup"/>', { pageId: pageId,
								action: 'deletePage' } ,function(data){
								   //console.log(data);
								   if(data.result.success){
									   $('#deleted').fadeIn('fast').animate({opacity: 1.0}, 300).fadeOut('fast'); 
									   $('#page-info-block_'+pageId).hide();
									   
								    }
							}, 'json' );
                        }
                    },
                    buttons: {
                        'Delete Page': true,
                        Cancel: false
                    }
                });
            });
        });
});
</script>
<div class="container_12">
	<!-- end .grid_10 -->
	<div class="clear"></div>
	
	<div class="clear"></div>
	<div class="grid_3" id="palette">
		<div class="clear"></div>
		   <c:choose>
		   	<c:when test="${not empty pages}">
		       <p><h1>Pages</h1></p>
			   <c:forEach var="page" items="${pages}"  varStatus="status">	
			   <p id="page-info-block_${page.id}">
			   <span><a class="delete-page remove_grey" id="delete-page_<c:out value="${page.id}"/>" title="Delete this page" href="#">x</a></span>
			   <a href="<c:url value="/page/setup?pageId=${page.id}" />">${page.shortName}</a>
			   </p>
			   </c:forEach>
			
		   	</c:when>
		   	<c:otherwise>
		   	<div class="clear"></div>
		   	<p class="info_message">No pages here.</p></c:otherwise>
		   </c:choose>
	</div>
	<!-- end .grid_3 -->
	<div class="grid_9">
	    
		<p id="dcanvas" style="height:50px;">
			 Page details
		</p>
	</div>
	<!-- end .grid_9 -->
	<div class="clear"></div>

</div>
<jsp:include page="/WEB-INF/common/footer.jsp" />
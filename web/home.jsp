<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="syrup" uri="/WEB-INF/syrup.tld" %>
<c:set var="pageTitle" value="Home" scope="request" />
<c:set var="currentTab" value="home" scope="request" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<script>
$(document).ready( function() {
	$('.delete-project ').each( function() {
        $(this).click( function() {
            var projectId = this.id.split("_")[1];
            $.prompt(
                'Are you sure you want to delete this project?',
                {
                    callback: function (proceed) {
                        if(proceed) {
                            //document.location="<c:url value="/page/setup" />?action=deletePage&pageId="+ pageId;
                        	$.post('<c:url value="/project/setup"/>', { projectId: projectId,
								action: 'deleteProject' } ,function(data){
								   //console.log(data);
								   if(data.result.success){
									   $('#deleted').fadeIn('fast').animate({opacity: 1.0}, 300).fadeOut('fast'); 
									   $('#project-info-block_'+projectId).hide();
									   
								    }
							}, 'json' );
                        }
                    },
                    buttons: {
                        'Delete Project': true,
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
	<div class="grid_12" id="palette">
		<div class="clear"></div>
		   <div><h3>Projects</h3></div>
		   <c:choose>
		   	<c:when test="${not empty projects}">
		       
			   <c:forEach var="project" items="${projects}"  varStatus="status">	
			   <div class="group" id="project-info-block_${project.id}">
			   <span><a class="delete-project remove_grey" id="delete-project_<c:out value="${project.id}"/>" title="Delete this project" href="#">x</a></span>
			   <a href="<c:url value="/project/setup?projectId=${project.id}" />">${project.name}</a>
			   </div>
			   </c:forEach>
			
		   	</c:when>
		   	<c:otherwise>
		   	<div class="clear"></div>
		   	<p class="info_message">No projects here. <a href="<c:url value="/project/setup"/>">Create one</a>.</p></c:otherwise>
		   </c:choose>
	</div>
	<div class="clear"></div>
</div>
<jsp:include page="/WEB-INF/common/footer.jsp" />
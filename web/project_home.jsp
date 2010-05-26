<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="syrup" uri="/WEB-INF/syrup.tld" %>
<c:set var="pageTitle" value="Home" scope="request" />
<c:set var="currentTab" value="home" scope="request" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<script>
$(document).ready( function() {
	$('.update-project').button().click( function() {
		var id = $('#id').val();
		var name = $('input[name=name]').val()
        $.post('<c:url value="/project/setup"/>', { name: name, projectId: id } ,function(data){
               //console.log(data);
               $('#name').removeClass('ui-state-error');
               if(data.result.success && data.result.projectid){
                   $('#updated').fadeIn('fast').animate({opacity: 1.0}, 300).fadeOut('fast'); 
                   // We redirect here. Because appending HTML would require we append this 
                   // click function, (which appends to itself, not good). Looping here.
                   // We redirect for now. 
                   document.location="<c:url value="/project/setup" />?projectId=" + data.result.projectid; 
                   //$('#project-pages').show();
                   
                }else {
                	 var message = "";
                    if(data.result.name) {
                        $('#name').addClass('ui-state-error');
                        message = data.result.name;
                    }
                   
                    if(data.result.fail){
                        message = message + " " +data.result.fail;
                    }
                    $.prompt('<div style=\"color:red;\">Error:</div> ' + message);
                        
                }
               
        }, 'json' );
        });
	$('.delete-project ').click( function() {
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
                                       document.location="<c:url value="/home" />"; 
                                       
                                    }else {

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
</script>
<div class="container_12">
	<!-- end .grid_10 -->
	<div class="clear"></div>
	
	<div class="clear"></div>
	<div class="grid_12" id="palette">
		<div class="clear"></div>
		   <input type="hidden" name="id" value="${project.id}"/>
           <div class="group-center">
                <fieldset>
                    <label for="name"><h4>Project name:</h4></label>
                    <div><input type="text" id="name" value="${project.name}" class="text ui-corner-all ui-widget-content" name="name" maxlength="100" size="60px"></input></div>
                    <div style="padding-top:1em;">
			            <c:choose>
			                <c:when test="${!empty project.id}">
			                    <button class="update-project" >Update project</button>
			                </c:when>
			                <c:otherwise>
			                    <button class="update-project" >Create new project</button>
			                </c:otherwise>
			            </c:choose>
			            <c:if test="${!empty project.id}">
			                <div align="right"><a href="#" class="delete-project">Delete project</a></div>
			            </c:if>
			        </div>
                </fieldset>
           </div>
            <div id="project-pages" class="group-center <c:if test="${empty project.id}">hide</c:if>">
                <h4>Pages</h4>
                 <c:choose>
                    <c:when test="${!empty project.pages}">
                        <c:forEach var="page" items="${project.pages}"  varStatus="status">
                               <div><a href="<c:url value="/page/setup?projectId=${project.id}&pageId=${page.id}"/>">${page.name}</a></div>  
                        </c:forEach> 
                    </c:when>
                    <c:otherwise>
                       <c:url value="/page/setup" var="pageCreateUrl">
			              <c:param name="projectId" value="${project.id}" />                                                                               
			           </c:url>
                       <div class="info_message">No pages here. <a href="${pageCreateUrl}">Create one</a>. </div>
                    </c:otherwise>
                </c:choose>
            </div>
	</div>
	<div class="clear"></div>
</div>
<jsp:include page="/WEB-INF/common/footer.jsp" />
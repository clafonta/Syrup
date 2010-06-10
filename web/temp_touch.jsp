<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="syrup" uri="/WEB-INF/syrup.tld" %>
<c:set var="pageTitle" value="Home" scope="request" />
<c:set var="currentTab" value="home" scope="request" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<script type="text/javascript"><!--
    $(document).ready(function(){

    	//Counter
        counter = 0;
        // Non clone drag
       
        
        $('.draginfo').dblclick( function() {
            
        });
        
        $(".drag").draggable({
            helper: 'clone', scroll: false, appendTo: '#canvas-container'
        });
            
        $("#dcanvas").droppable({
            accept: function() { return true; },
            drop: function(event, ui) {
               if (ui.helper.attr('id').search(/drag[0-9]/) != -1){
                   counter++;
                   offsetElement = $(ui.helper).offset();
                   offNewContainer = $('#dcanvas').offset();
                   $(this).append($(ui.helper).clone()
                           .css("left",offsetElement.left-offNewContainer.left)
                           .css("top",offsetElement.top-offNewContainer.top+6).appendTo('#dcanvas')
                           .draggable().removeClass('drag').addClass('draginfo draggable-clone').attr("id","clonediv_"+counter));
               }    
            }
        });

        $(".draggable-clone").draggable({containment: '#dcanvas', scroll: false});
        
        
    });

    --></script>
<div id="canvas-container" class="container_12">
    <!-- end .grid_10 -->
    <div class="clear"></div>
    <div class="grid_2" id="palette">
	   <p id="touchme3" class="largetouchbox touchable drag"><span>#3</span><b>Touchable</b></p>
	   <img src="/Syrup/images/sample/circle_blue.png" alt="circle_blue.png" class="drag ui-draggable" style="margin: 0pt; padding: 0pt;" id="drag16">
	   <img src="/Syrup/images/sample/triangle_pink.png" alt="circle_blue.png" class="touchable" style="margin: 0pt; padding: 0pt;" id="drag16">
	   
	   <p></p>
    </div>
    <!-- end .grid_2 -->
	    <div class="grid_10">
		    <p id="dcanvas" style="position:relative;">
		             <c:forEach var="asset" items="${pageItem.assets}"  varStatus="status"> 
		                <img id="drag-item_${asset.id}" style="position:absolute;top:${asset.top}px; left:${asset.left}px; margin:0; padding:0; " class="draggable-clone draginfo" alt="${asset.source}" src="<syrup:library name="${asset.source}" />"/>
		             </c:forEach>
		        </p>
	    </div>
    <div class="clear"></div>
    
</div>
<jsp:include page="/WEB-INF/common/footer.jsp" />
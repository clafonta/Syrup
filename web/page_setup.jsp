<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="syrup" uri="/WEB-INF/syrup.tld" %>
<c:set var="pageTitle" value="Home" scope="request" />
<c:set var="currentTab" value="home" scope="request" />
<jsp:include page="/WEB-INF/common/header.jsp" />
	<script type="text/javascript"><!--
    $(document).ready(function(){
    	$("#dialog").dialog({ autoOpen: false });
    	
    	$('#save-page')
        .button()
        .click(function() {
        	var projectId = $('#projectId').val();
        	var pageId = $('#pageId').val();
        	var assetIdValues = new Array();
            var topValues = new Array();
            var leftValues = new Array();
            var sourceValues = new Array();
        	$('#dcanvas').children().each(function() {
                var $child = $(this);
                var assetId = $child.attr("id").split("_")[1];
                //console.log('ID: ' + $child.attr("id"));
                //console.log('Left: '+$child.position().left);
                //console.log('Top: '+$child.position().top);
                assetIdValues.push(assetId);
                // HACK: I'm not sure why the top position is less
                topValues.push(assetId+'_'+($child.position().top));
                leftValues.push(assetId+'_'+$child.position().left);
                sourceValues.push(assetId+'_'+$child.attr("alt"));
            });
        	$('#page_name').removeClass('ui-state-error');
        	var pageName = $('#page_name').val();
        	$.post('<c:url value="/page/setup"/>', { projectId: projectId, pageId: pageId, name: pageName,
            	'assetId[]': assetIdValues, 'left[]': leftValues, 'top[]': topValues,
            	'source[]': sourceValues  } ,function(data){
                   ////console.log(data);
                   if(data.result.success && data.result.pageId){
                       // If page was new, we want to refresh the page. 
                       // Otherwise, we would need to append/remove all kinds of 
                       // things to the page. yuck.
                       <c:if test='${empty page.id}'>
                       document.location='<c:url value="/page/setup"/>?projectId='+projectId+'&pageId='+ data.result.pageId;
                       </c:if>
                       
                    }else {
                    	var message = "";
                    	if(data.result.pageName){
                            $('#page_name').addClass('ui-state-error');
                              message = message + '<div>' + data.result.pageName +'</div>';
                        }
                    	$.prompt('<div style=\"color:red;\">Not updated:</div> ' + message);
                    }
            }, 'json' );
        });
        //Counter
        counter = ${pageItem.nextAvailableAssetId};
        // Non clone drag
        $(".dragster").draggable({containment: 'parent'});
        //Make clone and make it draggable
        $('.drag').draggable({helper:'clone',containment: 'dcanvas', 
            stop:function(ev, ui) {
            	var pos=$(ui.helper).offset();
            	objName = '#clonediv_'+counter
            	$(objName).css({'left':pos.left,'top':pos.top});
            	$(objName).addClass('dropped').addClass('draginfo');
                $(objName).draggable({
                	containment: 'parent'
                });
			
            }
        });
        $('.draginfo').dblclick( function() {
        	 var pos=$(this).position();
        	 var pageId = $('#pageId').val();
             var pageName = $('#pageName').val();
             var sourceValue = $(this).attr("alt");
        	 $('#asset-top')[0].value = pos.top;
             $('#asset-left')[0].value = pos.left;
             $("#dialog").dialog('open');
        });
        
        $('.delete-page').click( function() {
            var pageId = this.id.split("_")[1];
            var projectId = $('#projectId').val();
            $.prompt(
                'Are you sure you want to delete this page?',
                {
                    callback: function (proceed) {
                        if(proceed) {
                            $.post('<c:url value="/page/delete"/>', { projectId: projectId, pageId: pageId } ,function(data){
                                   if(data.result.success){
                                	   document.location='<c:url value="/page/setup?projectId=${project.id}" />';                                        
                                    }else {
                                        // Not deleted?
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
       
        $('.delete-asset').each( function() {
	        $(this).click( function() {
	        	var assetId = $(this).attr("id").split("_")[1];
                var pageId = $('#pageId').val();
                $.post('<c:url value="/page/setup"/>', { pageId: pageId, assetId: assetId,
					action: 'deleteAsset' } ,function(data){
					   ////console.log(data);
					   if(data.result.success){
						   $('#updated').fadeIn('fast').animate({opacity: 1.0}, 300).fadeOut('fast'); 
						   $('#drag-item_'+assetId).hide();
						   $('#asset-info_'+assetId).hide();
						   
					    }
				}, 'json' );
	        })
	    });
        
        //Make element droppable
        $("#dcanvas").droppable({
			drop: function(ev, ui) {
				if (ui.helper.attr('id').search(/drag[0-9]/) != -1){
					counter++;
					var element=$(ui.draggable).clone();
					element.addClass("tempclass");
					$(this).append(element);
					$(".tempclass").attr("id","clonediv_"+counter);
					$("#clonediv_"+counter).removeClass("tempclass");
					//Get the dynamically item id
					draggedNumber = ui.helper.attr('id').search(/drag([0-9])/)
					itemDragged = "dragged" + RegExp.$1
					////console.log(itemDragged)

					$("#clonediv_"+counter).addClass(itemDragged);
				}
        	}
        });
    });

	--></script>

<div class="container_12">
	<div class="clear"></div>
	<div id="dialog" title="Info">
	    
	    <div> Top: <input id="asset-top" value="" class="location"/> Left: <input id="asset-left" value="" class="location"/></div>
	</div>
	
	<div class="clear"></div>
	<h2>Project: <a href="<c:url value="/project/setup?projectId=${project.id}"/>">${project.name}</a></h2>
	<div class="clear"></div>
	<div class="grid_2" id="palette">
		<div class="group">
		   <span style="float:right;"><a href="<c:url value="/page/setup?projectId=${project.id}&action=new"/>">new page</a></span>
		   <h4>Pages</h4>
	       <div class="scroll-pages">
	           <c:forEach var="pageInfo" items="${project.pages}" >
	               <p id="scroll-page-id_${page.id}"><a href="<c:url value="/page/setup?projectId=${project.id}&pageId=${pageInfo.id}"/>">${pageInfo.name}</a></p>
	           </c:forEach>
	       </div>
	   </div>
	   <div class="group scroll-pallette"><h4>Pallette</h4>
		   <div><img id="drag1" style="margin:0; padding:0;" class="drag" alt="square_black.png" src="<c:url value="/images/sample/square_black.png" />"></div>
		   <div><img id="drag2" style="margin:0; padding:0;" class="drag" alt="triangle_pink.png" src="<c:url value="/images/sample/triangle_pink.png" />"></div>
		   <div><img id="drag3" style="margin:0; padding:0;" class="drag" alt="circle_blue.png" src="<c:url value="/images/sample/circle_blue.png" />"></div>
	   </div>
	</div>
	<!-- end .grid_2 -->
	<div class="grid_10">
	    <input type="hidden" id="projectId" value="${project.id}"/>
	    <input type="hidden" id="pageId" value="${pageItem.id}"/>
	   
	    <div class="group">
	    
	    <fieldset>
	    <label for="page_name">Page name:</label>
	    <input id="page_name" class="text ui-corner-all ui-widget-content ui-widget" name="page_name" type="text" value="${pageItem.name}"></input>
	    <span style="float:right;">
	    <c:choose>
	       <c:when test="${empty pageItem.id}"><button id="save-page">Create Page</button></c:when>
	       <c:otherwise><button id="save-page">Save Page</button> <a href="#" id="delete-page_${page.id}" class="delete-page">Delete page</a></c:otherwise>
	    </c:choose> 
	    </span>
	    </fieldset>
		<p id="dcanvas">
			 
			 <c:forEach var="asset" items="${pageItem.assets}"  varStatus="status">	 
			    
			 	<img id="drag-item_${asset.id}" style="position:absolute; top:${asset.top}px; left:${asset.left}px; margin:0; padding:0; " class="dragster draginfo" alt="${asset.source}" src="<c:url value="/images/sample/${asset.source}" />"/>
			 </c:forEach>
		</p>
		</div>
	</div>
	<!-- end .grid_10 -->
	<div class="clear"></div>

</div>
<jsp:include page="/WEB-INF/common/footer.jsp" />
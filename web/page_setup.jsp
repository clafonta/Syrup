<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="syrup" uri="/WEB-INF/syrup.tld" %>
<%@ taglib prefix="syrup-tag" tagdir="/WEB-INF/tags" %>
<c:set var="pageTitle" value="Home" scope="request" />
<c:set var="currentTab" value="home" scope="request" />
<jsp:include page="/WEB-INF/common/header.jsp" />

<script type="text/javascript"><!--
//$(window).load(function() {
$(document).ready(function(){
	$("#dcanvas").annotateImage({
		getUrl: '<c:url value="/page/note/get"/>',  
		saveUrl: '<c:url value="/page/note/save"/>',  
		deleteUrl: '<c:url value="/page/note/delete"/>',  
		useAjax: true,  
		canvasLeftPos: $('#dcanvas').position().left,
		canvasTopPos: $('#dcanvas').position().top,
		canvasHeight: $('#dcanvas').height(),
        editable: true,
        projectId: ${project.id},
        pageId: ${pageItem.id},
        notes: [ { "top": 286, 
                   "left": 161, 
                   "width": 52, 
                   "height": 37, 
                   "text": "Small people on the steps", 
                   "id": "e69213d0-2eef-40fa-a04b-0ed998f9f1f5", 
                   "editable": true },
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
            var cpor = $('#dcanvas').offset();
        	$('#dcanvas').children().each(function() {
                var $child = $(this);
                var assetId = $child.attr("id").split("_")[1];
                assetIdValues.push(assetId);
                topValues.push(assetId+'_'+($child.position().top));
                leftValues.push(assetId+'_'+($child.position().left));
                sourceValues.push(assetId+'_'+$child.attr("alt"));
            });
        	$('#page_name').removeClass('ui-state-error');
        	var pageName = $('#page_name').val();
        	$.post('<c:url value="/page/setup"/>', { projectId: projectId, pageId: pageId, name: pageName,
            	'assetId[]': assetIdValues, 'left[]': leftValues, 'top[]': topValues,
            	'source[]': sourceValues  } ,function(data){
                   console.log(data);
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
       
        
        $('.draginfo').dblclick( function() {
            console.log("I fix");
             var cpor = $('#dcanvas').offset();
        	 var pos = $(this).offset();
        	 var pageId = $('#pageId').val();
             var pageName = $('#pageName').val();
             var sourceValue = $(this).attr("alt");
             var assetId = $(this).attr("id");
        	 $('#asset-top')[0].value = pos.top;
             $('#asset-left')[0].value = pos.left;
             $('#asset-parent-top')[0].value = cpor.top;
             $('#asset-parent-left')[0].value =  cpor.left;
             $('#asset-id')[0].value = assetId;
             $('#dialog').attr({title: 'BLAH'});
             $('#delete-asset').append('delete me').attr({id: assetId});
             $('[name=RotateL]').attr("id","RotateL-"+assetId);
             $('#dialog').dialog('open');
        });
        $('.hide-notes').click( function() {
        	$('#image-annotate-canvas').hide();
        	$('#hide-notes-link').hide();
        	$('#image-annotate-add-button').hide();
        	$('#show-notes-link').show();
        	return false;
        });
        $('.show-notes').click( function() {
            $('#image-annotate-canvas').show();
            $('#show-notes-link').hide();
            $('#hide-notes-link').show();
            $('#image-annotate-add-button').show();
            return false;
        });

        $('.image-annotate-add').click( function() {
        	//$("#dcanvas").annotateImage.add($("#dcanvas"));
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
                var projectId = $('#projectId').val();
                $.post('<c:url value="/asset/delete"/>', { projectId: projectId, 
                    pageId: pageId, assetId: assetId } ,function(data){
					   if(data.result.success){
						   $('#dialog').dialog('close');
						   $('#drag-item_'+assetId).remove();
						   $('#clonediv_'+assetId).remove();
						   $('#deleted').fadeIn('fast').animate({opacity: 1.0}, 300).fadeOut('fast'); 
						   
						   
					    }
				}, 'json' );
	        })
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

        $('.rotate-asset').button().click(function() {
        	var assetId = $(this).attr("id").split("_")[1];
        	$('#drag-item_'+assetId).rotate(-45);
        });
        $('.resize').button().click(function() {
            var assetId = $(this).attr("id").split("_")[1];
            $('#drag-item_'+assetId).css('height', '20px');
        });

        $('#copy-link').click(function() {
            var projectId = '${project.id}';
            var pageId = '${pageItem.id}';
            $.post('<c:url value="/page/copy"/>', { projectId: projectId, 
                pageId: pageId } ,function(data){
                	if(data.result.success && data.result.pageId){
                        document.location='<c:url value="/page/setup"/>?projectId='+projectId+'&pageId='+ data.result.pageId;
                     }else {
                         var message = "";
                         if(data.result.fail){
                               message = data.result.fail;
                         }
                         $.prompt('<div style=\"color:red;\">Not updated:</div> ' + message);
                     }
            }, 'json' );
        });
                
    });

	--></script>

<div id="canvas-container" class="container_12">
	<div class="clear"></div>
	<div id="dialog" title="Info Helper">
	    <a id="delete-asset" class="delete-asset" href="#"></a>
	    <div> Top: <input id="asset-top" value="" class="location"/> Left: <input id="asset-left" value="" class="location"/></div>
	    <div> Parent Top: <input id="asset-parent-top" value="" class="location"/> Left:<input id="asset-parent-left" value="" class="location"/></div>
	    <div> Item ID: <input id="asset-id" value="" class="location"/></div>
	    <table class="simple">
		  <tr><td align="left">
		  <input type="button" value="<-Rotate" name="RotateL" class="rotate-asset">
		  <input type="button" class="rotate-asset" value="Rotate->" name="RotateR" id="RotateR"></td></tr>
		</table>
		<table class="simple">
          <tr><td align="left">
          <input type="button" value="Smaller" name="RotateL" class="resize"></td></tr>
        </table>
	</div>
	
	<div class="clear"></div>
	
    <!-- end .grid_10 -->
	
	<div class="grid_2" id="palette">
	
	
	    <h4>Project: <a href="<c:url value="/project/setup?projectId=${project.id}"/>">${project.name}</a></h4>
	    <div class="clear"></div>
		<div class="group">
		   <h4>Pages</h4>
	       <div class="scroll-pages">
	           <c:forEach var="pageInfo" items="${project.pages}" >
	               <p id="scroll-page-id_${page.id}"><a href="<c:url value="/page/setup?projectId=${project.id}&pageId=${pageInfo.id}"/>">${pageInfo.name}</a></p>
	           </c:forEach>
	       </div>
	   </div>
	   <div class="group scroll-pallette"><h4>Pallette</h4>
		    <c:forEach var="libraryItem" items="${library}" varStatus="status" >
                   <div class="pallette-item"><img id="drag${status.count}" style="margin:0; padding:0;" class="drag transform" alt="${libraryItem.name}" src="<syrup:library name="${libraryItem.name}" />">
                   
                   </div>
            </c:forEach>
	   </div>
	</div>
	<!-- end .grid_2 -->
	<div class="grid_10">
	
	
	
        <input type="hidden" id="projectId" value="${project.id}"/>
        <input type="hidden" id="pageId" value="${pageItem.id}"/>
       
        <div class="group" style="position:relative;">
        <fieldset>
        <label for="page_name">Page name:</label>
        <input id="page_name" class="text ui-corner-all ui-widget-content ui-widget" name="page_name" type="text" value="${pageItem.name}" style="width:300px;"></input>
        <span style="float:right;">
        <c:choose>
           <c:when test="${empty pageItem.id}"><button id="save-page">Create Page</button></c:when>
           <c:otherwise><button id="save-page">Save Page</button> <span id="hide-notes-link"><a href="#" id="hide-notes" class="hide-notes">Hide Notes</a></span><span id="show-notes-link" style="display:none;"><a href="#" id="show-notes" class="show-notes" >Show Notes</a></span> | <a href="#" id="delete-page_${page.id}" class="delete-page">Delete</a> | <a id="copy-link" href="#"/>Copy</a> | <a href="<c:url value="/page/setup?projectId=${project.id}&action=new"/>">New</a></c:otherwise>
        </c:choose> 
        </span>
        </fieldset>
        <p id="dcanvas">
          
             <c:forEach var="asset" items="${pageItem.assets}"  varStatus="status"> 
                <img id="drag-item_${asset.id}" style="position:absolute;top:${asset.top}px; left:${asset.left}px; margin:0; padding:0; " class="draggable-clone draginfo" alt="${asset.source}" src="<syrup:library name="${asset.source}" />"/>
             </c:forEach>
          
        </p>
        <c:if test="${not empty pageItem.id}">
        <div class="group">
        <h4>History</h4>
        
            _syrup-tag:historyPage projectId="${project.id}" pageId="${pageItem.id}"_
        </div>
        </c:if>
        </div>
    </div>
	<div class="clear"></div>

</div>
<jsp:include page="/WEB-INF/common/footer.jsp" />
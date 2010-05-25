<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="syrup" uri="/WEB-INF/syrup.tld" %>
<c:set var="pageTitle" value="Home" scope="request" />
<c:set var="currentTab" value="home" scope="request" />
<jsp:include page="/WEB-INF/common/header.jsp" />
	<script type="text/javascript">
    $(document).ready(function(){
        //Counter
        counter = 0;
        //Make element draggable
        $(".drag").draggable({
            helper:'clone',
            containment: 'dcanvas',

            //When first dragged
            stop:function(ev, ui) {
            	var pos=$(ui.helper).offset();
            	objName = "#clonediv"+counter
            	$(objName).css({"left":pos.left,"top":pos.top});
            	//$(objName).removeClass("drag");
            	$(objName).addClass("dropped");


               	//When an existiung object is dragged
				var trash = false;
                $(objName).draggable({
                	containment: 'parent',
                    stop:function(ev, ui) {
                    	var pos=$(ui.helper).offset();
                    	//console.log($(this).attr("id"));
						//console.log(pos.left);
                        //console.log(pos.top);
						if(pos.top < 300){
							//console.log("trash can");
							trash = true;
							$(this).remove();
						}
                    }
                });
			
            }
        });
        //Make element droppable
        $("#dcanvas").droppable({
			drop: function(ev, ui) {
				if (ui.helper.attr('id').search(/drag[0-9]/) != -1){
					counter++;
					var element=$(ui.draggable).clone();
					element.addClass("tempclass");
					$(this).append(element);
					$(".tempclass").attr("id","clonediv"+counter);
					$("#clonediv"+counter).removeClass("tempclass");

					//Get the dynamically item id
					draggedNumber = ui.helper.attr('id').search(/drag([0-9])/)
					itemDragged = "dragged" + RegExp.$1
					//console.log(itemDragged)

					$("#clonediv"+counter).addClass(itemDragged);
				}
        	}
        });
    });

	</script>

<div class="container_12">
	<!-- end .grid_10 -->
	<div class="clear"></div>
	<div class="grid_3" id="palette">
		
		   <ul>
			<li><img id="drag1" class="drag" src="images/sample/hello_kitty_car.png"></li>
			<li><img id="drag2" class="drag" src="images/sample/hello_kitty_pirate.png"></li>
			</ul>
		
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
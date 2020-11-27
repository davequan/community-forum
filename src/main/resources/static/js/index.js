$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");
	//retrieve title and content
	var title = $("#recipient-name").val();
	var content = $("#message-text").val();
	//send asynchronous post
	$.post(
		CONTEXT_PATH+"/discuss/add",
		{"title":title,"content":content},
		function (data){
			data=$.parseJSON(data);
			//show the return message in hint body
			$("#hintBody").text(data.msg);
			// show promtBox
			$("#hintModal").modal("show");
			//hide afrer 2 seconds
			setTimeout(function(){
				$("#hintModal").modal("hide");
				// refresh page
				if (data.code == 0){
					window.location.reload();
				}
			}, 2000);
		}
	);
}

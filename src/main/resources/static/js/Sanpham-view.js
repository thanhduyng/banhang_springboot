$(document).ready(function() {
	var urls = "/sanpham/ #myModals";
	$('#model-body').html(event).load(urls);
	$('#bnn').on('click', function(event) {
		event.preventDefault();
		$('#myModal').modal("hide");
	});

	$('.chi').on('click', function(event) {
		event.preventDefault();
		$('#myChitiet').modal();
	});

	
});



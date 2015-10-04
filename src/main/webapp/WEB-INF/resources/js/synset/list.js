var ListSynset = {
	init: function() {
		var self = this;
		
		$('#confirmDelete').on('show.bs.modal', function(e) {
			var btn = $(e.relatedTarget);
			var url = btn.data('url');
			
			var modal = $(this);
			modal.find('form').attr('action', url);
		})
	},
}

$(document).ready(function() {
	ListSynset.init();
})
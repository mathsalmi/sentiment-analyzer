var EditSynset = {
	init: function() {
		var self = this;
		
		$('#addTermBtn').click(function(e) {
			e.preventDefault();
			self.newTerm();
		})
	},
	
	newTerm: function() {
		var id = $('#termsWrapper').children('div').length;
		
		var html = $('<div />');
			$('<input />').attr({
				'type':'text',
				'name':'terms[' + id + '].term'
			}).appendTo(html);
			$('<input />').attr({
				'type':'number', 
				'name':'terms[' + id + '].senseNumber',
				'min':'1',
				'step':'1'
			}).appendTo(html);
			
		$('#termsWrapper').append(html);
	}
}

$(document).ready(function() {
	EditSynset.init();
})
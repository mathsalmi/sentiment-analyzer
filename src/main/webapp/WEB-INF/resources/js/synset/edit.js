var EditSynset = {
	init: function() {
		var self = this;
		
		$('#addTermBtn').click(function(e) {
			e.preventDefault();
			self.newTerm();
		})
		
		self.addDeleteSynsetEvent('.synset-btn-delete');
		
		$('#formSynset').on('submit', function(e) {
			self.onSubmit();
		})
	},
	
	newTerm: function() {
		var self = this;
		var fields = $('#fieldsTmpl').html();
		var newfield = $(fields).appendTo('#termsWrapper');
		
		self.addDeleteSynsetEvent(newfield.find('.synset-btn-delete'));
	},
	
	onSubmit: function() {
		$('#termsWrapper .synset-item').each(function(index) {
			$(this).children('input').each(function() {
				var name = 'terms[' + index + '].' + $(this).attr('name'); 
				$(this).attr('name', name);
			})
		})
	},
	
	addDeleteSynsetEvent: function(selector) {
		$(selector).click(function(e) {
			e.preventDefault();
			$(this).closest('.synset-item').remove();
		})
	},
}

$(document).ready(function() {
	EditSynset.init();
})
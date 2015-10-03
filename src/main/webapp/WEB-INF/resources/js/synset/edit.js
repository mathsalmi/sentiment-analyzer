var EditSynset = {
	init: function() {
		var self = this;
		
		$('#addTermBtn').click(function(e) {
			e.preventDefault();
			self.newTerm();
		})
		
		$('.synset-btn-delete').click(function(e) {
			e.preventDefault();
			$(this).closest('.synset-item').remove();
		})
		
		$('#formSynset').on('submit', function(e) {
			self.onSubmit();
		})
	},
	
	newTerm: function() {
		var fields = $('#fieldsTmpl').html();
		$('#termsWrapper').append(fields);
	},
	
	onSubmit: function() {
		console.log('hello');
		
		$('#termsWrapper .synset-item').each(function(index) {
			$(this).children('input').each(function() {
				var name = 'terms[' + index + '].' + $(this).attr('name'); 
				$(this).attr('name', name);
			})
		})
	}
}

$(document).ready(function() {
	EditSynset.init();
})
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
	},
	
	newTerm: function() {
		var id = $('#termsWrapper').children('div').length;
		
		var html = $('<div />').addClass('form-group form-inline synset-item');
			$('<input />').attr({
				'type':'text',
				'name':'terms[' + id + '].term',
				'class':'form-control'
			}).appendTo(html);
			$('<input />').attr({
				'type':'number', 
				'name':'terms[' + id + '].senseNumber',
				'min':'1',
				'step':'1',
				'class':'form-control'
			}).appendTo(html);
			$('<a href="#" class="btn btn-default synset-btn-delete" title="Excluir"><span class="glyphicon glyphicon-remove"><span class="hidden">Excluir</span></span></a>')
				.click(function(e) {
					e.preventDefault();
					$(this).closest('.synset-item').remove();
				})
				.appendTo(html);
			
		$('#termsWrapper').append(html);
	},
}

$(document).ready(function() {
	EditSynset.init();
})
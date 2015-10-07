var Contribute = {
	posSlider: null,
	
	negSlider: null,
	
	objSlider: null,
	
	init: function() {
		var self = this;
		self.posSlider = $('#posSlider');
		self.negSlider = $('#negSlider');
		self.objSlider = $('#objSlider');

		$('.slider').slider({
			orientation: 'horizontal',
			range: 'min',
			max: 100,
			min: 0,
			step: 1,
			slide: function() { self.sliderOnChange() },
			change: function() { self.sliderOnChange(); },
		});
		
		$('#voteForm').on('submit', function(e) {
			self.submit(e);
		})
	},
	
	sliderOnChange: function() {
		var self = this;
		
		$('.slider').each(function() {
			var value = $(this).slider('value');
			$(this).parent().children('.slider-value').text(value + '%');
		});
		
		self.calculateNeutralValue();
	},
	
	calculateNeutralValue: function() {
//		var self = this;
//		var positive = self.posSlider.slider('value');
//		var negative = self.negSlider.slider('value');
//		var neutral = self.negSlider.slider('value');
//		
//		var neutral = 100 - (positive + negative);
////		
//		console.log('hello')
		
		//self.objSlider.slider('value', neutral);
	},
	
	submit: function(e) {
		e.preventDefault();
		var self = this;
		
		var positive = self.posSlider.slider('value') / 100;
		var negative = self.negSlider.slider('value') / 100;
		
		$('[name="positiveScore"]').val(positive);
		$('[name="negativeScore"]').val(negative);
	},
}

$(document).ready(function() {
	Contribute.init();
})
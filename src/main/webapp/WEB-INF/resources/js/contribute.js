var Contribute = {
	posSlider: null,
	
	negSlider: null,
	
	objSlider: null,
	
	init: function() {
		var self = this;
		self.posSlider = $('#posSlider');
		self.negSlider = $('#negSlider');
		self.objSlider = $('#objSlider');
		
		self.initSliders();
		self.initNeutral();
		
		// submit form
		$('#voteForm').on('submit', function(e) {
			self.submit(e);
		})
	},
	
	/**
	 * Configura os sliders positivo e negativo
	 */
	initSliders: function() {
		var self = this;
		
		$('#posSlider, #negSlider').slider({
			orientation: 'horizontal',
			range: 'min',
			max: 100,
			min: 0,
			step: 1,
			slide: updateSliderEvent,
			change: updateSliderEvent,
		});
		
		// evento dos sliders
		function updateSliderEvent(event, ui) {
			var slider = $(ui.handle).parent();
			var value = ui.value;
			
			self.calculateSliderPercentage(slider, value);
			self.calculateSliderValues(slider);
		}
	},
	
	/**
	 * Configura o "slider" do neutro.
	 */
	initNeutral: function() {
		var self = this;
		
		self.objSlider.progressbar({
			min:0,
			max:100,
			step:1,
			value:100,
			change: function() { self.calculateSliderPercentage($(this), $(this).progressbar('value')); }
		});
		
		// modificação das classes para que o componente progressbar seja visualmente igual ao slider.
		self.objSlider.attr('class', 'slider ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all');
		self.objSlider.children('div').attr('class', 'ui-slider-range ui-widget-header ui-corner-all ui-slider-range-min');
	},
	
	calculateSliderPercentage: function(slider, value) {
		slider.parent().children('.slider-value').text(value + '%');
	},
	
	/**
	 * Calcula os valores dos sliders.
	 * 
	 * A somatória dos três (positivo, negativo e neutro) deve ser 100.
	 * 
	 * O slider neutro deve começar em 100 e os outros dois em 0.
	 * Assim, conforme se aumenta o positivo ou o negativo, diminui-se o neutro.
	 * 
	 * Quando o neutro chega a 0, diminui-se o outro slider.
	 * 
	 * Exemplo: 
	 * coloca-se o slider positivo em 50%. Assim:
	 *     positivo = 50%
	 *     neutro = 50%
	 *     negativo = 0%
	 * agora, deseja-se colocar o negativo em 60%. Assim:
	 *     positivo = 40%
	 *     neutro = 0%
	 *     negativo = 60%
	 * 
	 * Isso porque enquanto se aumenta o negativo, o valor do neutro diminuirá até
	 * atingir 0. Aí é necessário reduzir 10 do positivo para que a somatória dos 3
	 * sliders sempre seja 100.
	 * 
	 * @param caller objeto jQuery que está chamando esse método (slider positivo ou negativo)
	 */
	calculateSliderValues: function(caller) {
		var self = this;
		
		var positive = self.posSlider.slider('value');
		var negative = self.negSlider.slider('value');
		var neutral = self.objSlider.progressbar('value');
		
		var total = positive + negative + neutral;
		
		// if everything is ok, just stop.
		if(total == 100) {
			return;
		}
		
		// calculate new value for neutral slider
		var extra = neutral - (total - 100);
		if(extra >= 0) {
			// if extra value is >= 0, then update the neutral slider
			self.objSlider.progressbar('value', extra);
		} else {
			// if extra is negative, then we also need to reduce either the positive or negative sliders.
			
			// if caller is positive, then reduce from negative or vice-versa
			var obj = self.posSlider;
			if(caller.is(self.posSlider)) {
				obj = self.negSlider;
			}
	
			var newval = obj.slider('value') - Math.abs(extra);
			obj.slider('value', newval);
		}
	},
	
	submit: function(e) {
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
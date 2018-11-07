$.validator.addMethod("dateValid", function(value, element, params) {
	if (value === '' || $(params).val() === '') {
		return true
	}

	if (!/Invalid|NaN/.test(new Date(value))) {
		return new Date(value) > new Date($(params).val());
	}

	return isNaN(value) && isNaN($(params).val())
			|| (Number(value) > Number($(params).val()));
}, 'The discontinued date must be later than the introduced date.');

$(document).ready(function() {
	$("#addComputer").validate({
		rules : {
			computerName : {
				required : true
			},
			discontinued : {
				dateValid : "#introduced"
			}
		},
		messages : {
			computerName : "Required field"

		}
	});

	$("#editComputer").validate({
		rules : {
			computerName : {
				required : true
			},
			discontinued : {
				dateValid : "#introduced"
			}
		},
		messages : {
			computerName : "Required field"
		}
	});

});

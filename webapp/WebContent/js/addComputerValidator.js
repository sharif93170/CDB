/**
 * 
 */
$(document)
		.ready(
				function() {
					$('#Add')
							.click(
									function(e) {
										var computerName = $('#computerName')
												.val();
										var introduced = $('#introduced').val();
										var discontinued = $('#discontinued')
												.val();
										var companyId = $('#companyId').val();

										$(".error").remove();

										if (computerName.length < 5) {
											$('#computerName')
													.after(
															'<span  class="error" style="color:blue;font-weight:bold"> '+namehaveWrongSize+' </span>');

											e.preventDefault();
										}
										
										if(computerName.search("<") >= 0  || computerName.search("/") >= 0 || computerName.search(">") >= 0 || computerName.search("*") >= 0 ){
											$('#computerName')
											.after(
													'<span  class="error" style="color:blue;font-weight:bold"> '+nameconteinIllegalcarracters+' </span>');

												e.preventDefault();
										}

										if (introduced == ''
												&& discontinued != '') {
											$('#introduced')
													.after(
															'<span class="error" style="color:blue;font-weight:bold">'+discountedDateWithoutIntroduced+'</span>');

											e.preventDefault();
										}

										if (discontinued != ''
												&& introduced != '') {
											if (discontinued < introduced) {
												$('#introduced')
														.after(
																'<span class="error" style="color:blue;font-weight:bold">'+wrongOrderOfDate+'</span>');
												$('#discontinued')
														.after(
																'<span class="error" style="color:blue;font-weight:bold">'+wrongOrderOfDate+'</span>');

												e.preventDefault();
											}
										}

									});

				})
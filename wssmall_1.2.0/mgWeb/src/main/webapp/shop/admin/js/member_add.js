function replyjsonBack(responseText) {
	if (responseText.result == 1 || responseText.result == 2) {
		alert(responseText.message);

	}
	if (responseText.result == 0) {
		alert(responseText.message);

		window.location.href = "member!memberlist.do";
	}
}
$(function() {
  $("form.validate").validate();
			initCity();
		});

$(document).ready(function() {

	$("#alertSuccess").hide();

	$("#alertError").hide();
});

//SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	console.log("Status ",status);
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	$.ajax({
		url : "employees",
		type : type,
		data : $("#formItem").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onItemSaveComplete(response.responseText, status);
		}
	});
});

function onItemSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidItemIDSave").val($(this).data("itemid"));
	$("#name").val($(this).closest("tr").find('td:eq(0)').text());
	$("#address").val($(this).closest("tr").find('td:eq(1)').text());
	$("#phone").val($(this).closest("tr").find('td:eq(2)').text());
	$("#gender").val($(this).closest("tr").find('td:eq(3)').text());
	$("#empContact").val($(this).closest("tr").find('td:eq(4)').text());

});

//Delete=============================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "employees",
		type : "DELETE",
		data : "employeeId=" + $(this).data("itemid"),
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
		}
	});
});

function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

//CLIENTMODEL=============================================================================================================================================
function validateItemForm() {
console.log("Status ");
	// CODE
	if ($("#name").val().trim() == "") {
		return "Insert Name.";
	}
	if ($("#address").val().trim() == "") {
		return "Insert Address.";
	}
	if ($("#phone").val().trim() == "") {
		return "Insert Phone.";
	}
	if ($("#gender").val().trim() == "") {
		return "Insert Gender.";
	}
	if ($("#age").val().trim() == "") {
		return "Insert Age.";
	}
	if ($("#email").val().trim() == "") {
		return "Insert Email.";
	}

	return true;
}
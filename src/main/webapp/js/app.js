/**
 * 
 */

$(function(){

	$('#customerTable').on('click',function(e){
		var target = e.target;
		if($(target).data('op') === 'delete'){
			var name = $(e.target).data('name'),
				id = $(e.target).data('id');
			if(confirm('Are you sure to delete ' + name)){
				console.log('sure')
				$.ajax({
					url : 'customer/' + id,
					type: 'DELETE',
					error: function(XMLHttpRequest, textStatus, errorThrown){
						alert('delete error');
					},
					success: function(){
						$(e.target).closest('tr').remove();
					}
				});
			}
		}
	});
	
	$('#productTable').on('click',function(e){
		var target = e.target;
		if($(target).data('op') === 'delete'){
			var name = $(e.target).data('name'),
				id = $(e.target).data('id');
			if(confirm('Are you sure to delete ' + name)){
				console.log('sure')
				$.ajax({
					url : 'product/' + id,
					type: 'DELETE',
					error: function(XMLHttpRequest, textStatus, errorThrown){
						alert('delete error');
					},
					success: function(){
						$(e.target).closest('tr').remove();
					}
				});
			}
		}
	});
	
});
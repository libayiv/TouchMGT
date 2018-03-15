var vm = new Vue({
	el : '#rrapp',
	data : {
		outlets : {},
		uedata : [],
		ue : ""
	},
	created : function() {
		
	},
	mounted : function() {
		this.$nextTick(function() {
			this.load();
			vm.ue = UE.getEditor('editor', {
				BaseUrl : '',
				UEDITOR_HOME_URL : '../../statics/ueditor/',
			});
		})
	},
	methods : {
		cancel : function() {

		},
		saveOrUpdate : function() {
			var content = vm.uedata.push(UE.getEditor('editor').getContent());
			console.log(vm.uedata.join("\n"));
			vm.outlets.content = content;
			$.ajax({
				type : "POST",
				url : baseURL + "touch/buscenter/update",
				contentType : "application/json",
				data : JSON.stringify(vm.outlets),
				success : function(r) {
					if (r.code === 0) {
						alert('操作成功', function() {
							vm.reload();
						});
					} else {
						alert(r.msg);
					}
				}
			});
		},
		load : function() {
			var type = "department";
			$.post(baseURL + "touch/buscenter/getInfo", {
				"type" : type
			}, function(r) {
				var data = r;
				vm.outlets = data.outlets;
				vm.ue.ready(function() {
                    vm.ue.setContent(vm.outlets.content);
                });
			}, 'json');
		}
		

	}

})
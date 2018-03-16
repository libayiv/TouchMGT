var vm = new Vue({
	el : '#rrapp',
	data : {
		outlets : {},
		uedata : [],
		ue : "",
		typeName:""
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
			var content = vm.ue.getContent();
			var contentBase64 = encodeBase64(content);
			vm.outlets.content =contentBase64;
			var param = {"outlets":vm.outlets}
			$.ajax({
				type : "POST",
				url : baseURL + "touch/buscenter/update",
				contentType : "application/json",
				data : JSON.stringify(param),
				success : function(r) {
					if (r.code === 0) {
						alert("保存成功");
					} else {
						alert(r.msg);
					}
				}
			});
		},
		load : function() {
			var type = getUrlQueryString("type");
			if(type=="department"){
				vm.typeName = "直销事业部";
			}else if(type=="join"){
				vm.typeName = "加入BFSuma";
			}else if(type=="plan"){
				vm.typeName = "事业计划";
			}else if(type=="basics"){
				vm.typeName = "直销基础知识";
			}
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
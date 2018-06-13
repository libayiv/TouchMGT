$(function () {

    new AjaxUpload('#app_upload', {
        action: baseURL + "touch/fileupload/upload?modularName=app",
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onComplete : function(file, r){
        	if(r.code == 0){
                vm.app.url = r.url;
				$("#fileName").val(r.fileName);
				alert("文件上传成功");
            }else{
                alert(r.msg);
            }
        }
    });
    
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            title: null
        },
        showList: true,
        title:null,
        configList:{},
        app:{
            
        }
    },
    mounted: function() {
		this.$nextTick(function () {
			this.query();
		})
	},
    methods: {
        query: function () {
            vm.reload();
        },

        saveOrUpdate: function () {
        
        	      	
        	if(vm.app.version_code==null || vm.app.version_code==''){
        		alert("请填写APP包的版本！");
        		return;
        	}
        	if(vm.app.url == null || vm.app.url==''){
        		alert("请选择APK！");
        		return;
        	}
        	
            var url = "touch/app/save";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.app),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
   						vm.reload();

                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
      
        reload: function () {
			$.post(baseURL + "touch/app/max",{}, function(r){
				 if(r.code == 500){
					 alert(r.msg);
					 return false;
				 }else{
					 vm.app=r.app;		            
				 }
			},'json');
        }
       
         
    }
});




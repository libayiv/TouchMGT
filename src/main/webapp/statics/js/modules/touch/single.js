$(function () {

    new AjaxUpload('#banner_upload', {
        action: baseURL + "touch/fileupload/upload?modularName=banner",
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
                alert('只支持jpg、png、gif格式的图片！');
                return false;
            }
        },
        onComplete : function(file, r){
        	if(r.code == 0){
            	vm.banner.coversrc = r.fileName;
            	vm.banner.oriFileName = r.oriFileName;
/*            	$("#banner_img").removeAttr("width");
            	$("#banner_img").removeAttr("height");
*/            	$("#banner_img").attr("src", r.url);
                alert("图片上传成功");
            }else{
                alert(r.msg);
            }
        }
    });
    
});
var hasPermission;
var platformList = getDictList("PLATFORM");

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            title: null
        },
        showList: true,
        title:null,
        platformList:platformList,
        configList:{},
        banner:{
            status:1,
            is_single:1
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
        
        	      	
        	if(vm.banner.title==null || vm.banner.title==''){
        		alert("请填写标题！");
        		return;
        	}else if(vm.banner.title.length > 50){
        		alert("标题不能大于50字！");
        		return;
        	}
        	if(vm.banner.coversrc == null || vm.banner.coversrc==''){
        		alert("请上传图片！");
        		return;
        	}
        	if(vm.banner.value=='' || vm.banner.value==null){
        		alert("请选择跳转位置！");
        		return;
        	}
        
            var url = vm.banner.id == null ? "touch/banner/save" : "touch/banner/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.banner),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
   						 this.parent.location.href='#modules/touch/banner.html';

                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
      
        reload: function () {
			$.post(baseURL + "touch/banner/single",{}, function(r){
				 if(r.code == 500){
					 vm.banner= {status:1,is_single:1, pc_valid:1, fileName:'', oriFileName:'', rank:0, playbackLength:10};
					 alert(r.msg, function(){
						 this.parent.location.href='#modules/touch/banner.html';
                     });
					 return false;
				 }else{
					 vm.configList=r.config;
					 if(r.single==null){
						 return false;
					 }
					 vm.banner=r.single;
		                if(r.single.coversrc != null && r.single.coversrc != ''){
		                
		                	$("#banner_img").attr("src", localStorage.fileUrlPath + r.single.coversrc);
		                }
		                
				 }
			},'json');
        }
       
         
    }
});




$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'touch/news/list',
        datatype: "json",
        colModel: [			
			{ label: '编号', name: 'id', index: "pid", width: 45, key: true, hidden: true},
			{ label: '封面', name: 'coversrc', formatter: function(value, options, row){
				return '<img src="'+ localStorage.fileUrlPath + value +'" width="100px;" height="100px;" />';
			} },
			{ label: '标题', name: 'title' },
			{ label: '是否显示', name: 'status', width: 60, formatter: function(value, options, row){
				var id='checked_' + row.id;
				return value == 1 ? 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.disable(\'' + row.id + '\')" checked/><label for="'+id+'" class="newlabel"></label>' : 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.enable(\'' + row.id + '\')"/><label for="'+id+'" class="newlabel"></label>';
			} },
			{ label: '操作', name: 'operation', width: 80, formatter: function(value, options, row){
				if(hasPermission == null){
					$.ajax({
		                type: "GET",
		                url: baseURL + "sys/permitted/hasPermission?permission=touch:news:save",
		                contentType: "application/json",
		                async:false,
		                success: function(r){
		                    if(r.code === 0){
		                    	hasPermission = r.hasPermission;
		                    }else{
		                    	hasPermission = false;
		                    }
		                }
		            });
				}
				if(hasPermission){
					return '<a class="btn btn-primary" onclick="vm.update(\'' + row.id + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</a>';
				}
				return '';
			} },
        ],
		viewrecords: true,
        height: 'auto',
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        cellEdit:true,
        cellurl: baseURL +"touch/news/updateSortNum",
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        },
        beforeSubmitCell :function(rowid, cellname, value, iRow, iCol){  
            // 传递参数  
        	var platform= $("#jqGrid").getCell(rowid,'platform');
        	var data = {'pid':rowid, 'sortNum':value, 'platform':platform};  
            return data;
         },
         afterSubmitCell:function(serverresponse, rowid, cellname, value, iRow, iCol){
        	 //修改失败
        	 if(serverresponse.responseJSON.code == 500){
        		 alert(serverresponse.responseJSON.msg);
        	 }else{
                 alert('操作成功', function(){
                     vm.reload();
                 });        		 
        	 }
         }
    });
    
    new AjaxUpload('#news_upload', {
        action: baseURL + "touch/fileupload/upload?modularName=news",
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
            	vm.news.coversrc = r.fileName;
            	vm.news.oriFileName = r.oriFileName;
/*            	$("#news_img").removeAttr("width");
            	$("#news_img").removeAttr("height");
*/            	$("#news_img").attr("src", r.url);
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
        news:{
            status:1,
            is_hot:0,
            is_activity:0
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
        	$("#news_img").removeAttr("src");
            vm.showList = false;
            vm.title = "新增";
        	$("#url").hide();
            vm.news = {status:1,is_hot:0, is_activity:0,pc_valid:1, fileName:'', oriFileName:'',playbackLength:10};

        },
        update: function (pid) {
            vm.showList = false;
            vm.title = "编辑";
            vm.getnews(pid);
        },
        del: function () {
            var pids = getSelectedRows();
            if(pids == null){
                return ;
            }
            confirm("确定要删除选中的记录？", function(){
            	vm.updateStatus(pids.toString(), '0');
            });
        },
        saveOrUpdate: function () {
              	
        	if(vm.news.title==null || vm.news.title==''){
        		alert("请填写标题！");
        		return;
        	}else if(vm.news.title.length > 100){
        		alert("标题不能大于100字！");
        		return;
        	}
        	if(vm.news.coversrc == null || vm.news.coversrc==''){
        		alert("请上传图片！");
        		return;
        	}
        	
            var url = vm.news.id == null ? "touch/news/save" : "touch/news/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.news),
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
        getnews: function(pid){
        	$("#news_img").removeAttr("src");
            $.get(baseURL + "touch/news/info/"+pid, function(r){
                vm.news = r.news;
                if(r.news.coversrc != null && r.news.coversrc != ''){
                	/*$("#news_img").removeAttr("width");
                	$("#news_img").removeAttr("height");*/
                	$("#news_img").attr("src", localStorage.fileUrlPath + r.news.coversrc);
                } else {
                	/*$("#news_img").attr("width", "100px");
                	$("#news_img").attr("height", "100px");*/
                }
               
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'title': vm.q.title},
                page:page
            }).trigger("reloadGrid");
        },
        updateStatus: function(pids, status){
        	var data = {'pids':pids, 'status': status};
            $.ajax({
                type: "POST",
                url: baseURL + "touch/news/updateStatus",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function(r){
                    if(r.code == 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        enable: function(pid){
        	vm.updateStatus(pid, "1");
        },
        disable: function(pid){
        	vm.updateStatus(pid, "0");
        }
       
    }
});




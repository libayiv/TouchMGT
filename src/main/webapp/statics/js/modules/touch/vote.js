$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'touch/vote/list',
        datatype: "json",
        colModel: [			
			{ label: '编号', name: 'id', index: "pid", width: 45, key: true, hidden: true},
			{ label: '序号', name: 'rank', width: 45, editable:true },
			{ label: '封面', name: 'coversrc', formatter: function(value, options, row){
				return '<img src="'+ localStorage.fileUrlPath + value +'" width="100px;" height="100px;" />';
			} },
			{ label: '标题', name: 'title' },
			{ label: '类型', name: 'type', width: 60 },
			{ label: '是否显示', name: 'status', width: 60, formatter: function(value, options, row){
				var id='checked_' + row.id;
				return value == 1 ? 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.disable(\'' + row.id + '\')" checked/><label for="'+id+'" class="newlabel"></label>' : 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.enable(\'' + row.id + '\')"/><label for="'+id+'" class="newlabel"></label>';
			} },
			{ label: '操作', name: 'operation', formatter: function(value, options, row){
				if(hasPermission == null){
					$.ajax({
		                type: "GET",
		                url: baseURL + "sys/permitted/hasPermission?permission=touch:vote:save",
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
					var str='<a class="btn btn-primary btn-sm"  onclick="vm.update(\'' + row.id + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</a>';
					var appendStr=row.type=="vote"?'<a class="btn btn-info btn-sm" style="margin-left:5px" onclick="vm.checkResult(\'' + row.id + '\',\'views\')"><i class="fa fa-reply"></i>&nbsp;查看投票结果</a><a class="btn btn-warning btn-sm" style="margin-left:5px" onclick="vm.checkResult(\'' + row.id + '\',\'edit\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑投票项</a>':"";
					return str+appendStr;
				}
				return '';
			} },
        ],
		viewrecords: true,
        height: 'auto',
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: false, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        cellEdit:true,
        cellurl: baseURL +"touch/vote/updateSortNum",
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
        loadComplete: function (res) {
            // res是服务器返回的数据
        	vm.configList = res.config;
        },
        beforeSubmitCell :function(rowid, cellname, value, iRow, iCol){  
            // 传递参数  
        	var data = {'id':rowid, 'rank':value};  
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
    
	$("#jqGrid2").jqGrid({
		url: baseURL + 'touch/vote/result',
		datatype: "json",
		colModel: [				
			{ label: '编号', name: 'id', index: "pid", width: 45, key: true, hidden: true},
			{ label: 'banner', name: 'coversrc',width: 60, formatter: function(value, options, row){
				return '<img src="'+ localStorage.fileUrlPath + value +'" width="50px;" />';
			} },
			{ label: 'title',width: 60, name: 'title' },
			{ label: 'intro',width: 60, name: 'intro' },
			{ label: 'votes',width: 200, name: 'votes', hidden: true },
			{ label: '是否显示', hidden: true, name: 'status', width: 60, formatter: function(value, options, row){
				var id='checked_' + row.id;
				return value == 1 ? 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.detailDisable(\'' + row.id + '\')" checked/><label for="'+id+'" class="newlabel"></label>' : 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.detailEnable(\'' + row.id + '\')"/><label for="'+id+'" class="newlabel"></label>';
			} },
			{ label: '操作', name: 'operation', formatter: function(value, options, row){
				var str=''
				if(vm.voteStatus=='views'){
					str='<a class="btn btn-primary btn-sm"  onclick="vm.checkUser(\'' + row.id + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;查看具体</a>';
				}else{
					str='<a class="btn btn-primary btn-sm"  onclick="vm.editDetail(\'' + row.id + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑简介</a>';
				}
					return str;

			} }
			],
			viewrecords: true,
			height: 'auto',
			rowNum: 10,
			rowList : [10,30,50],
			rownumWidth: 25, 
			multiselect: true,
			pager: "#jqGridPager2",
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
				$("#jqGrid2").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
			}
	});
	$("#jqGrid3").jqGrid({
		url: baseURL + 'touch/vote/user',
		datatype: "json",
		colModel: [			
			{ label: '编号', name: 'id', index: "pid", width: 45, key: true, hidden: true},
			{ label: 'user-thumb', name: 'thumb',width: 60, formatter: function(value, options, row){
				return '<img src="'+ value +'" width="50px;" />';
			} },
			{ label: 'user-nick',width: 60, name: 'nick' },
			{ label: 'vote-title',width: 60, name: 'title' },
			{ label: 'vote-pubdate',width: 200, name: 'pubdate' }
			],
			viewrecords: true,
			height: 'auto',
			rowNum: 10,
			rowList : [10,30,50],
			rownumWidth: 25, 
			multiselect: true,
			pager: "#jqGridPager3",
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
				$("#jqGrid3").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
			}
	});
    new AjaxUpload('#vote_upload', {
        action: baseURL + "touch/fileupload/upload?modularName=vote",
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
            	vm.vote.coversrc = r.fileName;
            	vm.vote.oriFileName = r.oriFileName;
            	$("#vote_img").attr("src", r.url);
                alert("图片上传成功");
            }else{
                alert(r.msg);
            }
        }
    });
    new AjaxUpload('#detail_upload', {
        action: baseURL + "touch/fileupload/upload?modularName=vote",
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
            	vm.detail.coversrc = r.fileName;
            	$("#detail_img").attr("src", r.url);
                alert("图片上传成功");
            }else{
                alert(r.msg);
            }
        }
    });
    $("#datetimeEnd").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,
    }).on("changeDate",function(){
    	vm.vote.endTime=$("#datetimeEnd").val();
    });
});
var hasPermission;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            title: null
        },
        showList: true,
        showResult:false,
        showUser:false,
        showDetail:false,
        title:null,
        voteStatus:null,
        configList:{},
        detail:{},
        vote:{
            status:1
        }
    },
	mounted: function() { 
		this.ue = UE.getEditor('editor',{ 
			BaseUrl: '', 
			UEDITOR_HOME_URL: '../../statics/ueditor/', 
		}); 
	},
    methods: {
        query: function () {
            vm.reload();
        },
        addVote: function(){
        	$("#vote_img").removeAttr("src");
            vm.showList = false;
            vm.showResult=false;
			vm.showUser=false;
			vm.showDetail=false;
            vm.title = "新增投票活动";
            $("#datetimeEnd").val("");
            vm.vote = {status:1,views:0, type:'vote',fileName:'', oriFileName:'', rank:0, playbackLength:10};

        },
        addHealth: function(){
        	$("#vote_img").removeAttr("src");
            vm.showList = false;
            vm.showResult=false;
			vm.showUser=false;
			vm.showDetail=false;
            vm.title = "新增健康测评";
            $("#datetimeEnd").val("");
            vm.vote = {status:1,views:0, type:'health',fileName:'', oriFileName:'', rank:0, playbackLength:10};

        },
        addDetail: function(){
        	$("#detail_img").removeAttr("src");
            vm.showList = false;
            vm.showResult=false;
			vm.showUser=false;
			vm.showDetail=true;
            vm.title = "新增投票活动选项";
            var voteId=vm.detail.vote_id;
            UE.getEditor('editor').setContent("");
            vm.detail = {status:1,votes:0,vote_id:voteId,fileName:'', oriFileName:'', playbackLength:10};

        },
        update: function (pid) {
            vm.showList = false;
            vm.showResult=false;
			vm.showUser=false;
			vm.showDetail=false;
            vm.title = "编辑";
            vm.getVote(pid);
        },
        editDetail:function (pid) {
            vm.showList = false;
            vm.showResult=false;
			vm.showUser=false;
			vm.showDetail=true;
            vm.title = "编辑投票活动选项";
            vm.getDetail(pid);
        },
        checkResult:function(pid,status){
        	vm.showResult=true;
			vm.showList=false;
			vm.showUser=false;
			vm.showDetail=false;
			var page = $("#jqGrid2").jqGrid('getGridParam','page');
			vm.detail.vote_id=pid;
			if(status=='views'){
				vm.voteStatus='views';
				$("#jqGrid2").jqGrid('setGridParam',{
					postData:{'vote_id': pid},
					page:page
				}).hideCol('status').showCol('votes').trigger("reloadGrid");
				
			}else{
				vm.voteStatus='edit';
				$("#jqGrid2").jqGrid('setGridParam',{
					postData:{'vote_id': pid},
					page:page
				}).hideCol('votes').showCol('status').trigger("reloadGrid");
	
			}
			
        },
        checkUser:function(pid){
        	vm.showResult=false;
			vm.showList=false;
			vm.showUser=true;
			vm.showDetail=false;
			var page = $("#jqGrid3").jqGrid('getGridParam','page');
			$("#jqGrid3").jqGrid('setGridParam',{
				postData:{'detail_id': pid},
				page:page
			}).trigger("reloadGrid");
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
        	var re = /^[0-9]+$/ ;
        	if(vm.vote.rank==null || vm.vote.rank==''){    
        		alert("请填写序号!");
       		 	return;
        	} 
        	if(!re.test(vm.vote.rank)){    
        		alert("序号请填写正整数!");
       		 	return;
        	}         	
        	if(vm.vote.title==null || vm.vote.title==''){
        		alert("请填写标题！");
        		return;
        	}else if(vm.vote.title.length > 50){
        		alert("标题不能大于50字！");
        		return;
        	}
        	if(vm.vote.coversrc == null || vm.vote.coversrc==''){
        		alert("请上传图片！");
        		return;
        	}
        	if(vm.vote.endTime==null || vm.vote.endTime==''){
        		alert("请选择结束时间！");
        		return;
        	}
        	if(vm.vote.intro==null || vm.vote.intro==''){
            	var str =vm.vote.type=='vote'?'活动简介':'测评跳转地址';

        		alert("请填写"+str);
        		return;
        	}
        	
            var url = vm.vote.id == null ? "touch/vote/save" : "touch/vote/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.vote),
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
        saveDetail: function () {
        	     	
        	if(vm.detail.title==null || vm.detail.title==''){
        		alert("请填写标题！");
        		return;
        	}else if(vm.detail.title.length > 50){
        		alert("标题不能大于50字！");
        		return;
        	}
        	if(vm.detail.coversrc == null || vm.detail.coversrc==''){
        		alert("请上传图片！");
        		return;
        	}
        	var content =  UE.getEditor('editor').getContent();
        	$.base64.utf8encode = true;  
        	vm.detail.content=$.base64.btoa(content);
            var url = vm.detail.id == null ? "touch/vote/saveDetail" : "touch/vote/updateDetail";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.detail),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.checkResult(vm.detail.vote_id,'edit');
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        getVote: function(pid){
        	$("#vote_img").removeAttr("src");
            $.get(baseURL + "touch/vote/info/"+pid, function(r){
                vm.vote = r.vote;
                $("#datetimeEnd").val(r.vote.endTime);

                if(r.vote.coversrc != null && r.vote.coversrc != ''){
               
                	$("#vote_img").attr("src", localStorage.fileUrlPath + r.vote.coversrc);
                }
               
            });
        },
        getDetail: function(pid){
        	$("#detail_img").removeAttr("src");
            $.get(baseURL + "touch/vote/detail/"+pid, function(r){
                vm.detail = r.detail;
                if(vm.detail.content != null && vm.detail.content != ''){
                    UE.getEditor('editor').setContent(vm.detail.content);
                }else{
                	 UE.getEditor('editor').setContent('');
                }
                if(r.detail.coversrc != null && r.detail.coversrc != ''){
                	$("#detail_img").attr("src", localStorage.fileUrlPath + r.detail.coversrc);
                }
               
            });
        },
        reload: function () {
            vm.showList = true;
        	vm.showResult=false;
			vm.showUser=false;
			vm.showDetail=false;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'title': vm.q.title},
                page:page
            }).trigger("reloadGrid");
        },
        search: function () {
            vm.showList = true;
        	vm.showResult=false;
			vm.showUser=false;
			vm.showDetail=false;
            var page = 1;
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'title': vm.q.title},
                page:page
            }).trigger("reloadGrid");
        },
        reloadDetail: function (status) {
        	this.checkResult(vm.detail.vote_id,status);
        },
        updateStatus: function(pids, status){
        	var data = {'pids':pids, 'status': status};
            $.ajax({
                type: "POST",
                url: baseURL + "touch/vote/updateStatus",
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
        updateDetailStatus: function(pids, status){
        	var data = {'pids':pids, 'status': status};
            $.ajax({
                type: "POST",
                url: baseURL + "touch/vote/detailStatus",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function(r){
                    if(r.code == 0){
                        alert('操作成功', function(){
                        	vm.checkResult(vm.detail.vote_id,'edit');;
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
        },
        detailEnable: function(pid){
        	vm.updateDetailStatus(pid, "1");
        },
        detailDisable: function(pid){
        	vm.updateDetailStatus(pid, "0");
        }
       
    }
});




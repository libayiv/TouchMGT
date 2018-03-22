$(function() {
	$("#jqGrid")
			.jqGrid(
					{
						url : baseURL + 'sys/message/list',
						datatype : "json",
						colModel : [
								{
									label : '编号',
									name : 'id',
									index : "pid",
									width : 45,
									key : true,
									hidden : true
								},
								{
									label : '消息tag',
									name : 'tag'
								},
								{
									label : '标题',
									name : 'title'
								},
								{
									label : '简介',
									name : 'intro'
								},
								{
									label : '类型',
									name : 'type',
									formatter: function(value, options, row){
										 return value == 1 ? '手动发送' : '自动定时发送';
									}
								},
								{
									label : '操作',
									name : 'operation',
									width : 80,
									formatter : function(value, options, row) {
										if (hasPermission == null) {
											$
													.ajax({
														type : "GET",
														url : baseURL
																+ "sys/permitted/hasPermission?permission=sys:message:save",
														contentType : "application/json",
														async : false,
														success : function(r) {
															if (r.code === 0) {
																hasPermission = r.hasPermission;
															} else {
																hasPermission = false;
															}
														}
													});
										}
										if (hasPermission) {
											return '<a class="btn btn-primary" onclick="vm.update(\''
													+ row.id
													+ '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</a>'
													+ '<a class="btn btn-primary" onclick="vm.showDetail(\''
													+ row.id
													+ '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;查看</a>';
										}
										return '';
									}
								}, ],
						viewrecords : true,
						height : 'auto',
						rowNum : 10,
						rowList : [ 10, 30, 50 ],
						rownumbers : true,
						rownumWidth : 25,
						autowidth : true,
						multiselect : true,
						cellEdit : true,
						// cellurl: baseURL +"touch/banner/updateSortNum",
						pager : "#jqGridPager",
						jsonReader : {
							root : "page.list",
							page : "page.currPage",
							total : "page.totalPage",
							records : "page.totalCount"
						},
						prmNames : {
							page : "page",
							rows : "limit",
							order : "order"
						},
						gridComplete : function() {
							// 隐藏grid底部滚动条
							$("#jqGrid").closest(".ui-jqgrid-bdiv").css({
								"overflow-x" : "hidden"
							});
						},
						loadComplete : function(res) {
							// res是服务器返回的数据
							vm.configList = res.config;
						},
						beforeSubmitCell : function(rowid, cellname, value,
								iRow, iCol) {
							// 传递参数
							var platform = $("#jqGrid").getCell(rowid,
									'platform');
							var data = {
								'pid' : rowid,
								'sortNum' : value,
								'platform' : platform
							};
							return data;
						},
						afterSubmitCell : function(serverresponse, rowid,
								cellname, value, iRow, iCol) {
							// 修改失败
							if (serverresponse.responseJSON.code == 500) {
								alert(serverresponse.responseJSON.msg);
							} else {
								alert('操作成功', function() {
									vm.reload();
								});
							}
						}
					});

	new AjaxUpload('#message_upload', {
		action : baseURL + "touch/fileupload/upload?modularName=message",
		name : 'file',
		autoSubmit : true,
		responseType : "json",
		onSubmit : function(file, extension) {
			if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension
					.toLowerCase()))) {
				alert('只支持jpg、png、gif格式的图片！');
				return false;
			}
		},
		onComplete : function(file, r) {
			if (r.code == 0) {
				vm.message.coversrc = r.fileName;
				/* vm.banner.oriFileName = r.oriFileName */;
				/*
				 * $("#banner_img").removeAttr("width");
				 * $("#banner_img").removeAttr("height");
				 */$("#message_img").attr("src", r.url);
				alert("图片上传成功");
			} else {
				alert(r.msg);
			}
		}
	});
	

});
var hasPermission;
var companyList = getDictList("COMPANY");

var vm = new Vue({
	el : '#rrapp',
	data : {
		uedata : [],
		ue : "",
		q : {
			title : null
		},
		showList : true,
		title : null,
		companys : companyList,
		configList : {},
		message : {
			status : 1
		},
		acceptorRange : {
			rankmin : 0,
			rankmax : 0,
			pvmin : 0,
			pvmax : 0,
			gpvmin : 0,
			gpvmax : 0
		}
	},
	mounted : function() {
		this.$nextTick(function() {
			vm.ue = UE.getEditor('editor', {
				BaseUrl : '',
				UEDITOR_HOME_URL : '../../statics/ueditor/',
			});
		})
	},
	methods : {

		showDetail : function(pid) {
			vm.showList = false;
			vm.title = "查看";
			vm.getMessage(pid);
			$('#sure').attr('disabled', true);
		},
		query : function() {
			vm.reload();
		},
		add : function() {
			$("#message_img").removeAttr("src");
			vm.showList = false;
			vm.title = "新增";
			// $("#selectType").find("option:selected").removeAttr("selected");
			vm.message = {
				status : 1,
				acc_type : 1
			};
			$('#sure').attr('disabled', false);
			UE.getEditor('editor').addListener("ready", function () {
				// editor准备好之后才可以使用
				UE.getEditor('editor').setContent('');
			}); 
		},
		update : function(pid) {
			vm.showList = false;
			vm.title = "编辑";
			vm.getMessage(pid);
			$('#sure').attr('disabled', false);
		},
		del : function() {
			var pids = getSelectedRows();
			if (pids == null) {
				return;
			}
			var params = {
				"pids" : pids
			};
			confirm("确定要删除选中的未发送的记录？", function() {
				/* vm.updateStatus(pids.toString(), '0'); */
				$.ajax({
					type : "POST",
					url : baseURL + "sys/message/delete",
					contentType : "application/json",
					data : JSON.stringify(params),
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
			});
		},
		send : function() {
			var pids = getSelectedRows();
			if (pids == null) {
				return;
			}
			var params = {
				"pids" : pids
			};
			confirm("确定要发送选中的消息？", function() {
				/* vm.updateStatus(pids.toString(), '0'); */
				$.ajax({
					type : "POST",
					url : baseURL + "sys/message/sendGoogle",
					contentType : "application/json",
					data : JSON.stringify(params),
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
			});
		},
		saveOrUpdate : function() {
			var re = /^[0-9]+$/;
			/*
			 * if(vm.message.rank==null || vm.banner.rank==''){ alert("请填写序号!");
			 * return; }  if(!re.test(vm.banner.rank)){ alert("序号请填写正整数!");
			 * return; } 
			 */
			if (vm.message.title == null || vm.message.title == '') {
				alert("请填写标题！");
				return;
			} else if (vm.message.title.length > 50) {
				alert("标题不能大于50字！");
				return;
			}
			if (vm.message.coversrc == null || vm.message.coversrc == '') {
				alert("请上传图片！");
				return;
			}
			if (vm.message.tag == null || vm.message.tag == '') {
				alert("请填写标签！");
				return;
			}
			if (vm.message.intro == null || vm.message.intro == '') {
				alert("请填写简介！");
				return;
			}

			var content = UE.getEditor('editor').getContent();
			var contentBase64 = encodeBase64(content);
			vm.message.content = contentBase64;
			var url = vm.message.id == null ? "sys/message/save"
					: "sys/message/update";
			if (url == "sys/message/save") {
				if (vm.message.acc_type == 1) {
					vm.message.acceptor = 'all';
				} else if (vm.message.acc_type == 2) {
					vm.message.acceptor = vm.acceptorRange.membs;
				} else {
					var pvRange = "PV[" + vm.acceptorRange.pvmin + ","
							+ vm.acceptorRange.pvmax + "]";
					var gpvRange = "GPV[" + vm.acceptorRange.gpvmin + ","
							+ vm.acceptorRange.gpvmax + "]";
					var rankRange = "会员等级[" + vm.acceptorRange.rankmin + ","
							+ vm.acceptorRange.rankmax + "]";
					var countrys = "国家[" + vm.acceptorRange.country + "]";
					vm.message.acceptor = countrys + " " + pvRange + " "
							+ gpvRange + " " + rankRange;
				}
			}
			$.ajax({
				type : "POST",
				url : baseURL + url,
				contentType : "application/json",
				data : JSON.stringify(vm.message),
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
		getMessage : function(pid) {
			$("#message_img").removeAttr("src");
			$.get(baseURL + "sys/message/info/" + pid, function(r) {
				vm.message = r.message;
				debugger;
				UE.getEditor('editor').setContent(vm.message.content);
				if (r.message.coversrc != null && r.message.coversrc != '') {
					$("#message_img").removeAttr("width");
					$("#message_img").removeAttr("height");
					$("#message_img").attr("src",
							localStorage.fileUrlPath + r.message.coversrc);
				} else {
					/*
					 * $("#banner_img").attr("width", "100px");
					 * $("#banner_img").attr("height", "100px");
					 */
				}
				/*
				 * if(vm.banner.type=='web'){ $("#url").show(); }else{
				 * $("#selectType").find("option[value='"+vm.message.value+"']").attr("selected",true);
				 * $("#url").hide(); }
				 */
			});
		},
		reload : function() {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
				postData : {
					'title' : vm.q.title
				},
				page : page
			}).trigger("reloadGrid");
		}
	}
});

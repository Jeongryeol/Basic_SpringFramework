<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	Integer total = null;
	total = (Integer)session.getAttribute("total");
	int ctotal = 0;
	//out.print("��ü ���ڵ� ��:"+total);
	//��Ű�� ����� ���� �ҷ�����
	Cookie[] cs = request.getCookies();
	for(int i=0;i<cs.length;i++){
		String cName = cs[i].getName();
		//out.print("��Ű�̸�:"+cName);
		if("ctotal".equals(cName)){
			ctotal = Integer.parseInt(cs[i].getValue());
		}
	}	
	//out.print("��ü ���ڵ� ��[��Ű��]:"+ctotal);
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>�۸��</title>
<!-- ���� �ڵ� includeó�� -->
<%@ include file="../../../include/commonUI.jsp" %>
<script type="text/javascript">
	var g_total=0;
	//��۾��� �� ��
	function repleForm(pb_no, pb_group, pb_pos, pb_step){
		alert("repleFormȣ�� ����"+pb_no+","+pb_group+","+pb_pos+","+pb_step);
		$("#b_no").val(pb_no);
		$("#b_group").val(pb_group);
		$("#b_pos").val(pb_pos);
		$("#b_step").val(pb_step);
		$("#dl_read").dialog('close');
		$("#dl_boardInsert").dialog('open');
	}
	//�� ������ �� - DB�� �¿��� ��.
	function updateForm(pb_no){
		//alert("updateFormȣ�� ����");
		$("#dl_read").dialog('close');
        $("#dl_update").dialog({
    		href:'./mvcBoard.bd?gubun=getBoardList&one=update&b_no='+pb_no,
        });
		$("#dl_update").dialog('open');
	}
	//�ۼ���  ó���ϱ�
	function updateAction(){
		$("#f_update").attr("method","post");
		$("#f_update").attr("action","./mvcBoard.bd");
		$("#f_update").submit();//�� �� ������ ������ �Ͼ
	}
	//�ۻ����� ��
	function deleteForm(){
		alert("deleteFormȣ�� ����");
	}
	//�ۻ󼼺��⿡�� �۸�� ���ư���
	function dg_listReload(){
		//alert("dg_listReloadȣ�� ����");
		$("#dl_read").dialog('close');
		$("#dg_list").datagrid({
		    url:'./mvcBoard.bd?gubun=getBoardList'	
		});
	}
	function bSearch(){
	//������ ��� �̹� DOM������ �Ϸ�Ǿ��� �� �׼��� �̹� ź ���°� �ǰ� �� ����� jsonBoardList.jsp�� ������ ���´�.
/* 		$("#f_search").attr("method","get");
		$("#f_search").attr("action","./mvcBoard.bd?gubun=getBoardList");
		$("#f_search").submit(); */
		$("#dg_list").datagrid({
		    url:'./mvcBoard.bd?gubun=getBoardList&cb_type='+$("#cb_type").val()+'&sb_keyword='+$("#sb_keyword").val()		
		});
	}
	//�۾��� ȭ�� ����
	function boardInsForm(){
		$("#dl_boardInsert").dialog('open');
	}
	//�۾��� ��� ó���ϱ�
	function boardInsert(){
		alert("gubun:"+$("#gubun").val());
		$("#f_insert").attr("method","post");
		$("#f_insert").attr("action","./mvcBoard.bd");
		$("#f_insert").submit();//�� �� ������ ������ �Ͼ
	}
	//������ ���̼ǿ��� ������ �̵� ��ư�� Ŭ������ �� ���� ������ �̵�ó�� ����
	function pageMove(pageNumber, pageSize){
		$("#dg_list").datagrid({
		    url:'./mvcBoard.bd?gubun=getBoardList&page='+pageNumber+'&pageSize='+pageSize
		});    		
	}
</script>
</head>
<body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#dg_list").datagrid({
		    url:'./mvcBoard.bd?gubun=getBoardList&page=1&pageSize=5',
		    columns:[[
		        {field:'B_NO',title:'�۹�ȣ',width:100, align:'center'},
		        {field:'B_TITLE',title:'����',width:400, align:'center'},
		        {field:'B_NAME',title:'�̸�',width:100, align:'center'},
		        {field:'B_FILE',title:'÷������',width:150, align:'center'},
		        {field:'B_HIT',title:'��ȸ��',width:100, align:'center'},
		    ]],
		/* ������ ��ȸ�� �Ϸ�Ǿ��� �� */
		    onDblClickRow: function(index,row){
		    	//alert("�۹�ȣ : "+row.B_NO);
		    	//���̾� �α� â�� ������� - �ڵ� �߰� - ��ũ��Ʈ�� ó��
		    	//list.jsp������ ���� ���̾�α� ������ ������ �����ϴϱ� - �������� �и��Ǿ� ���� ���� ����
		        $("#dl_read").dialog({
		    		href:'./mvcBoard.bd?gubun=getBoardList&one=one&b_no='+row.B_NO,
		    	    onLoad:function(){
		    	    	//alert("success!!!");
		    	    }
		        });
				$("#dl_read").dialog('open');
			}
		});
		$('#pg_board').pagination({
		    total:<%=ctotal%>
		   ,pageSize:5
		   ,pageList: [5,10,15,20]
		   ,onSelectPage:function(pageNumber, pageSize){//pageNumber:���� ���� �ٶ󺸴� ������, pageSize:���������� �Ѹ� �ο��
			   //alert("pageNumber:"+pageNumber+"\n pageSize:"+ pageSize);
			   pageMove(pageNumber, pageSize);
		   }
		});
	});
</script>
<table align="center" width="900px">
<!-- �˻� ���� ȭ�� �߰��ϱ� -->
<form id="f_search">
	<tr>
		<td>
		<select id="cb_type" class="easyui-combobox" name="cb_type" style="width:200px;">
		    <option value="b_title">����</option>
		    <option value="b_content">����</option>
		    <option value="b_name">�ۼ���</option>
		</select>
		<input id="sb_keyword" name="sb_keyword" class="easyui-searchbox" style="width:300px"
        data-options="searcher:bSearch,prompt:'Please Input Value'"></input>
		</td>
	</tr>
</form>	
<!-- �۾��� ��ư �߰� -->	
	<tr>
		<td align="left">
        <a id="btn_write" href="javascript:boardInsForm()" class="easyui-linkbutton">�۾���</a>
		</td>
	</tr>	
<!-- ���ó�� ȭ�� �߰��ϱ�(datagrid) -->
	<tr>
		<td>
		<table id="dg_list"></table>
		</td>
	</tr>	
<!-- ������ ���̼� ó��(pagination) -->
	<tr>
		<td>
		<div id="pg_board" style="background:#efefef;border:1px solid #ccc;">
		</div>		
		</td>
	</tr>	
</table>
<!-- �ۻ� ���� ���̾�α� ó�� ����[read.jsp] -->
<div id="dl_read" class="easyui-dialog" title="�ۻ󼼺���" style="width:600px;height:350px;"
     data-options="resizable:true,modal:true,closed:true">
</div> 
<!-- �ۻ� ���� ���̾�α� ó��  ��   -->
<!-- 
�۾��� ���̾� �α� ó��(writeForm.jspȭ�� ó��)
ó���� ���� ���·� �ִٰ� �۾��� ��ư�� Ŭ���ϸ� â�� ���.
 -->
<div id="dl_boardInsert" class="easyui-dialog" title="�۾���" style="width:600px;height:350px;"
     data-options="resizable:true,modal:true,closed:true">
 	    <form id="f_insert" method="post" enctype="multipart/form-data">
<!-- 	    <form id="f_insert"> -->
	    <input type="hidden" id="gubun" name="gubun" value="boardInsert">
	    <input type="hidden" id="b_no" name="b_no" value="0">
	    <input type="hidden" id="b_group" name="b_group" value="0">
	    <input type="hidden" id="b_pos" name="b_pos" value="0">
	    <input type="hidden" id="b_step" name="b_step" value="0">
    <table align="center">
	    	<tr>
	    	<td>����</td>
	    	<td><input id="b_title" name="b_title" data-options="width:'250px'" class="easyui-textbox"></td>
	    	</tr>
	    	<tr>
	    	<td>�ۼ���</td>
	    	<td><input id="b_name" name="b_name" class="easyui-textbox"></td>
	    	</tr>
	    	<tr>
	    	<td>����</td>
	    	<td><input id="b_content" name="b_content" data-options="multiline:'true', width:'350px', height:'90px'" class="easyui-textbox"></td>
	    	</tr>
	    	<tr>
	    	<td>��й�ȣ</td>
	    	<td><input id="b_pwd" name="b_pwd" class="easyui-passwordbox"></td>
	    	</tr>
	    	<tr>
	    	<td>÷������</td>
	    	<td><input id="b_file" name="b_file" class="easyui-filebox" style="width:350px"></td>
	    	</tr>
	    		    	
	   </table>
	    	</form>
	    <table align="center">
	    	<tr>
	    	<td>
				<a href="javascript:boardInsert()" class="easyui-linkbutton">����</a>
				<a href="javascript:$('#dl_boardInsert').dialog('close');" class="easyui-linkbutton">�ݱ�</a>
	    	</td>
	    	</tr>
	    </table>
</div> 
<!-- 
�۾��� ���̾� �α� ó��(writeForm.jspȭ�� ó��)
ó���� ���� ���·� �ִٰ� �۾��� ��ư�� Ŭ���ϸ� â�� ���.
 -->
 <!--=================================== �ۼ����ϱ� ȭ�� ����[updateForm.jsp] =================================-->
 <div id="dl_update" class="easyui-dialog" title="�ۼ���" style="width:600px;height:350px;"
     data-options="resizable:true,modal:true,closed:true">
</div> 
 <!--=================================== �ۼ����ϱ� ȭ��  ��   =================================-->
</body>
</html>











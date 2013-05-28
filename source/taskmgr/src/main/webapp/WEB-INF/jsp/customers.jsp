<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url value="/chinguyen/customers/records?_search=false" var="recordsUrl"/>
<c:url value="/chinguyen/customers/create" var="addUrl"/>
<c:url value="/chinguyen/customers/update" var="editUrl"/>
<c:url value="/chinguyen/customers/delete" var="deleteUrl"/>

<html>
<head>
	<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/jquery-ui/pepper-grinder/jquery-ui-1.8.16.custom.css"/>'/>
	<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/ui.jqgrid-4.3.1.css"/>'/>
	<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/style.css"/>'/>
	
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui-1.8.16.custom.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/grid.locale-en-4.3.1.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery.jqGrid.min.4.3.1.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>
	
	<title>User Records</title>
	
	<script type='text/javascript'>
	$(function() {
		$("#grid").jqGrid({
		   	url:'${recordsUrl}',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['Cust No', 'Customer Name', 'Contact Last Name', 'Contact First Name', 'Phone', 'AddressLine', 
		   	          'AddressLine2', 'City', 'State', 'Postal Code', 'Country', 'Sales Employee', 'CreditLimit'],
		   	colModel:[
		   		{name:'customerNumber',index:'customerNumber', width:55, editable:false, editoptions:{readonly:true, size:30}, hidden:true},
		   		{name:'customerName',index:'customerName', width:200, editable:true, editrules:{required:true}, editoptions:{size:30}},
		   		{name:'contactLastName',index:'contactLastName', width:120, editable:true, editrules:{required:true}, editoptions:{size:30}},
		   		{name:'contactFirstName',index:'contactFirstName', width:120, editable:true, editrules:{required:true}, editoptions:{size:30}},
		   		{name:'phone',index:'phone', width:120, editable:true, editrules:{required:true}, editoptions:{size:30}},
		   		{name:'addressLine1',index:'addressLine1', width:200, editable:true, editrules:{required:true}, editoptions:{size:30}},
		   		{name:'addressLine2',index:'addressLine2', width:200, editable:true, editrules:{required:false}, editoptions:{size:30}, hidden:true},
		   		{name:'city',index:'city', width:100, editable:true, editrules:{required:false}, editoptions:{size:30}},
		   		{name:'state',index:'state', width:100, editable:true, editrules:{required:false}, editoptions:{size:30}, hidden:true},
		   		{name:'postalCode',index:'postalCode', width:80, editable:true, editrules:{required:true}, editoptions:{size:30}, hidden:true},
		   		{name:'country',index:'country', width:50, editable:true, editrules:{required:true}, editoptions:{size:30}},
		   		{name:'salesRepEmployee',index:'employee.employeeNumber', width:200, editable:true, editrules:{required:false}, editoptions:{size:30}},
		   		{name:'creditLimit',index:'creditLimit', width:50, editable:true, editrules:{required:false}, editoptions:{size:30}}
		   	],
		   	postData: {},
			rowNum:20,
		   	rowList:[20,50,100],
		   	height: 480,
		   	autowidth: true,
			rownumbers: true,
		   	pager: '#pager',
		   	sortname: 'customerNumber',
		   	grouping:true, 
		   	groupingView : { groupField : ['salesRepEmployee'] },
		    viewrecords: true,
		    sortorder: "asc",
		    caption:"Customer List",
		    emptyrecords: "Empty records",
		    loadonce: false,
		    loadComplete: function() {},
		    jsonReader : {
		        root: "rows",
		        page: "page",
		        total: "total",
		        records: "records",
		        repeatitems: false,
		        cell: "cell",
		        id: "id"
		    }
		});

		$("#grid").jqGrid('navGrid','#pager',
				{edit:true, add:true, del:false, search:false},
				{}, {}, {}, 
				{ 	// search
					sopt:['cn', 'eq', 'ne', 'lt', 'gt', 'bw', 'ew'],
					closeOnEscape: true, 
					multipleSearch: true, 
					closeAfterSearch: true
				}
		);
		
	});

	</script>
</head>

<body>
	<h1 id='banner'>System Records</h1>
	
	<div id='jqgrid'>
		<table id='grid'></table>
		<div id='pager'></div>
	</div>
	
	<div id='msgbox' title='' style='display:none'></div>
</body>
</html>
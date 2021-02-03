<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="View Token Registrations" otherwise="/login.htm" redirect="/module/calculation/calculationRegistrations.list" />
<%@ include file="localHeader.jsp" %>

<c:set var="DO_NOT_INCLUDE_JQUERY" value="true"/>
<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/calculation/jquery-ui/css/redmond/jquery-ui-1.7.2.custom.css"/>
<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/calculation/dataTables/css/page.css"/>
<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/calculation/dataTables/css/table.css"/>
<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/calculation/dataTables/css/custom.css"/>

<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/calculation/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/calculation/jquery-ui/js/jquery-ui-1.7.2.custom.min.js"/>
<openmrs:htmlInclude file='${pageContext.request.contextPath}/moduleResources/calculation/dataTables/jquery.dataTables.min.js'/>

<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery('.calculationTable').dataTable({
		    "bPaginate": true,
		    "iDisplayLength": 15,
		    "bLengthChange": false,
		    "bFilter": true,
		    "bSort": false,
		    "bInfo": true,
		    "bAutoWidth": true,
		    "bSortable": true
		});
	});
</script>
<style>
	.calculationTable th,td {padding-left:10px; padding-right:10px;}
</style>

<h2><spring:message code="calculation.CalculationRegistration.manage.pageTitle" /></h2>

<button onclick="document.location.href='calculationRegistration.form';">
	<spring:message code="calculation.registerCalculationManually"/>
</button>
&nbsp;&nbsp;
<button onclick="document.location.href='calculationAutoRegistration.form';">
	<spring:message code="calculation.registerCalculationsAutomatically"/>
</button>
<br/><br/>

<b class="boxHeader"><spring:message code="calculation.CalculationRegistration.listExistings.boxTitle"/></b>
<div class="box">
	<c:choose>
		<c:when test="${empty calculationRegistrations}">
			<spring:message code="general.none" />
		</c:when>
		<c:otherwise>
			<form method="post">
				<table class="calculationTable">
					<thead>
						<th></th>
						<th><spring:message code="calculation.CalculationRegistration.token"/></th>
						<th><spring:message code="calculation.CalculationRegistration.providerClassName"/></th>
						<th><spring:message code="calculation.CalculationRegistration.calculationName"/></th>
						<th><spring:message code="calculation.CalculationRegistration.configuration"/></th>
					</thead>
					<c:forEach var="calculationRegistration" items="${calculationRegistrations}">
						<tr>
							<td style="white-space:nowrap;">
								<a href="calculationRegistration.form?id=${calculationRegistration.id}" style="text-decoration:none;">
									<img src="<c:url value='/images/edit.gif'/>" border="0"/>
								</a>
								<a href="patientCalculationTest.form?id=${calculationRegistration.id}" style="text-decoration:none;">
									<img src="<c:url value='/images/play.gif'/>" border="0"/>
								</a>								
								<a href="deleteCalculationRegistration.form?id=${calculationRegistration.id}"  style="text-decoration:none;" onclick="return confirm('<spring:message code="calculation.CalculationRegistration.confirmDelete"/>');">
									<img src="<c:url value='/images/trash.gif'/>" border="0"/>
								</a>							
							</td>
							<td><a href="calculationRegistration.form?id=${calculationRegistration.id}"><c:out value="${calculationRegistration.token}" /></a></td>
							<td><c:out value="${calculationRegistration.providerClassName}" /></td>
							<td><c:out value="${calculationRegistration.calculationName}" /></td>
							<td><c:out value="${calculationRegistration.configuration}" /></td>
						</tr>
					</c:forEach>
				</table>
			</form>		
		</c:otherwise>
	</c:choose>
	<br/><br/>
</div>
<br/>

<%@ include file="/WEB-INF/template/footer.jsp"%>
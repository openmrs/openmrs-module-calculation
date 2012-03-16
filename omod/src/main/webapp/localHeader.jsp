<ul id="menu">
	<li class="first">
		<a href="${pageContext.request.contextPath}/admin"><spring:message code="admin.title.short"/></a>
	</li>
	<openmrs:hasPrivilege privilege="View Token Registrations">
		<li>
			<a href="${pageContext.request.contextPath}/module/calculation/calculationRegistrations.list">
				<spring:message code="calculation.CalculationRegistration.listExistings.boxTitle"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
</ul>
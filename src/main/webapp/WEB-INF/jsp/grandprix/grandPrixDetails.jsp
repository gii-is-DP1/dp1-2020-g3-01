<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="team">


	<h2>Grand Prix Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Location</th>

			<td><b><c:out value="${grandPrix.location}" /></b></td>
		</tr>
		<tr>
			<th>Circuit</th>
			<td><c:out value="${grandPrix.circuit}" /></td>
		</tr>
		<tr>
			<th>Laps</th>
			<td><c:out value="${grandPrix.laps}" /></td>
		</tr>
		<tr>
			<th>Distance</th>
			<td><c:out value="${grandPrix.distance}" /></td>
		</tr>
		<tr>
			<th>Day of race</th>
			<td><c:out value="${grandPrix.dayOfRace}" /></td>
		</tr>
	</table>

	<spring:url value="edit" var="editTeamsUrl">
	</spring:url>

	<spring:url value="remove" var="removeTeamsUrl">
	</spring:url>
	
	<div style="width: 100%; display:flex; justify-content:flex-end;">
 	<a href="${fn:escapeXml(editTeamsUrl)}" class="btn btn-default" style="margin-right: 1rem;">Edit GP</a>
				
	<a href="${fn:escapeXml(removeTeamsUrl)}" class="btn btn-default">Remove GP</a>
	</div>
	
			
	<h2>Teams participating in this Grand Prix</h2>
	<%--<table class="table table-striped">
		<c:forEach var="pilot" items="${team.pilot}">

			<tr>
				<td valign="top">
					<dl class="dl-horizontal">
						<dt>Name</dt>
						<dd>
							<c:out value="${pilot.firstName} ${pilot.lastName}" />
						</dd>
						<dt>Birth Date</dt>
						<dd>
							<petclinic:localDate date="${pilot.birthDate}"
								pattern="yyyy-MM-dd" />
						</dd>
						<dt>Residence</dt>
						<dd>
							<c:out value="${pilot.residence}" />
						</dd>
						<dt>Nationality</dt>
						<dd>
							<c:out value="${pilot.nationality}" />
						</dd>
						<dt>DNI</dt>
						<dd>
							<c:out value="${pilot.dni}" />
						</dd>
						<dt>Number</dt>
						<dd>
							<c:out value="${pilot.number}" />
						</dd>
						<dt>Height</dt>
						<dd>
							<c:out value="${pilot.height}" />
						</dd>
						<dt>Weight</dt>
						<dd>
							<c:out value="${pilot.weight}" />
						</dd>
					</dl> 
					
					<spring:url value="pilots/{pilotId}/details" var="showPilot">
					<spring:param name="pilotId" value="${pilot.id}" />
					</spring:url>
					<div style="width: 100%; display:flex; justify-content:flex-end;">
					<a href="${fn:escapeXml(showPilot)}" class="btn btn-default">Show Pilot</a>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table> --%>

	<%-- <spring:url value="pilots/new" var="createPilot">
	</spring:url>
	<a href="${fn:escapeXml(createPilot)}" class="btn btn-default">Add
		Pilot</a> --%>

</petclinic:layout>

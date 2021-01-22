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
	<c:if test="${empty grandPrix.positions}">

		<spring:url value="ranking/new" var="addRanking">
		</spring:url>
		<div style="width: 100%; display: flex; justify-content: flex-end;">
			<a href="${fn:escapeXml(addRanking)}" class="btn btn-default">Add
				Ranking</a>
		</div>

	</c:if>

	<c:if test="${not empty grandPrix.positions}">
		<spring:url value="ranking/all" var="listRanking">

		</spring:url>
		<div style="width: 100%; display: flex; justify-content: flex-end;">
			<a href="${fn:escapeXml(listRanking)}" class="btn btn-default">Show
				Ranking</a>
		</div>
	</c:if>
	<br>


	<spring:url value="/grandprix/{grandPrixId}/edit" var="editTeamsUrl">
	<spring:param name="grandPrixId" value="${grandPrix.id}"/>
	</spring:url>
	
	<div style="width: 100%; display:flex; justify-content:flex-end;">
 	<a href="${fn:escapeXml(editTeamsUrl)}" class="btn btn-default" style="margin-right: 1rem;">Edit GP</a>
				
	<spring:url value="/grandprix/{grandPrixId}/remove" var="removeTeamsUrl">
	<spring:param name="grandPrixId" value="${grandPrix.id}"/>
	</spring:url>
				
	<a href="${fn:escapeXml(removeTeamsUrl)}" class="btn btn-default">Remove GP</a>
	</div>
	
	<spring:url value="/grandprix/{grandPrixId}/addTeam" var="addTeam">
        <spring:param name="grandPrixId" value="${grandPrix.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addTeam)}" class="btn btn-default">Inscribe my Team</a>
 
    <spring:url value="/grandprix/{grandPrixId}/removeTeam" var="removeTeam">
    <spring:param name="grandPrixId" value="${grandPrix.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(removeTeam)}" class="btn btn-default">Remove my Team</a>
    	
    <h1></h1>
	<h2>Teams participating in this Grand Prix</h2>
	<table class="table table-striped">
		<c:forEach var="team" items="${grandPrix.team}">

			<tr>
				<td valign="top">
					<dl class="dl-horizontal">
						<dt>Name</dt>
						<dd>
							<c:out value="${team.name}" />
						</dd>
						<dt>Creation Date</dt>
						<dd>
							<petclinic:Date date="${team.creationDate}"
								pattern="yyyy-MM-dd" />
						</dd>
						<dt>NIF</dt>
						<dd>
							<c:out value="${team.nif}" />
						</dd>
						<dt>Manager</dt>
						<dd>
							<c:out value="${team.manager.firstName} ${team.manager.lastName}" />
						</dd>
						
					</dl> 
					
				</td>
			</tr>
		</c:forEach>
	</table>
	
   <h1></h1>
	<h2>Pilots participating in this Grand Prix</h2>
	<table class="table table-striped">
		<c:forEach var="pilot" items="${grandPrix.pilots}">

			<tr>
				<td valign="top">
					<dl class="dl-horizontal">
						<dt>Name</dt>
						<dd>
							<c:out value="${pilot.firstName} ${pilot.lastName}" />
						</dd>
						<dt>Birth Date</dt>
						<dd>
							<petclinic:localDate date="${pilot.birthDate}" pattern="yyyy-MM-dd" />
						</dd>
						<dt>DNI</dt>
						<dd>
							<c:out value="${pilot.dni}" />
						</dd>
						<dt>Residence</dt>
						<dd>
							<c:out value="${pilot.residence}" />
						</dd>
						
						<dt>Nationality</dt>
						<dd>
							<c:out value="${pilot.nationality}" />
						</dd>
						
					</dl> 
					
				</td>
			</tr>
		</c:forEach>
	</table>


</petclinic:layout>
